package za.co.mixo.BattleRoyale.ClientServer;

import org.json.JSONArray;

import za.co.mixo.BattleRoyale.Request;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;


    public Client(Socket socket, String username) {
        try {
            this.socket = socket;
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            this.bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            this.username = username;

        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }
    }

    /**
     * This method sends request message to the world.
     */
    public void sendMessage() {
        try {
            System.out.println("Welcome " + username);
            bufferedWriter.newLine();
            bufferedWriter.flush();


            try (Scanner sc = new Scanner(System.in)) {
                while (socket.isConnected()) {
                    Thread.sleep(1000);
                    System.out.println("Enter command to run: ");
                    String command = sc.nextLine().toLowerCase();

                    if (command.equals("launch")){
                        robotKind();
                    }
                    System.out.println("Command arguments: ");
                    JSONArray arguments = new JSONArray(sc.nextLine());

                    Request request = new Request(username,command,arguments);

                    bufferedWriter.write(request.toJsonString(username, command, arguments));
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            System.out.close();


        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }

    /**
     * This method listens for messages from the world
     */
    public void listenForMessage(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                String msgFromWorld;
                while (socket.isConnected()){
                    try{
                        msgFromWorld = bufferedReader.readLine();
                        System.out.println(msgFromWorld);

                    }catch (IOException e) {
                        closeEverything(socket, bufferedReader, bufferedWriter);
                    }
                }
            }
        }).start();
    }


    public void closeEverything( Socket socket, BufferedReader bufferedReader,BufferedWriter bufferedWriter){

        try{
            if (bufferedReader != null){
                bufferedReader.close();
            }
            if (bufferedWriter != null){
                bufferedWriter.close();
            }
            if (socket != null){
                socket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * This prints out a menu of robot kinds a client can launch
     */
    public void robotKind(){
        System.out.println("\n ------------------Choose your kind----------------");
        System.out.println(" --------------------------------------------------");
        System.out.println(" -  Kind         | Distance (steps) |      Shots  -");
        System.out.println(" --------------------------------------------------");
        System.out.println(" - 1. Sniper     |       5          |        1    -");
        System.out.println(" --------------------------------------------------");
        System.out.println(" - 2. AK-47      |       4          |        2    -");
        System.out.println(" --------------------------------------------------");
        System.out.println(" - 3. Steyr AUG  |       3          |        3    -");
        System.out.println(" --------------------------------------------------");
        System.out.println(" - 4. M4 carbine |       2          |        4    -");
        System.out.println(" --------------------------------------------------");
        System.out.println(" - 5. Pistol     |       1          |        5    -");
        System.out.println(" --------------------------------------------------");
        System.out.println(" --------------------------------------------------\n");
    }


    public static void main(String[] args) throws IOException {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Welcome to Robot Worlds\n");
            System.out.println("What do you want to name your robot?");
            String username = sc.nextLine();
            Socket socket = new Socket("20.20.15.181",1538);
            Client client = new Client(socket,username);
            client.listenForMessage();
            client.sendMessage();


        }
    }

}