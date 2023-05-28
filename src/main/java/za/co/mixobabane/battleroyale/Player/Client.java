package za.co.mixobabane.battleroyale.Player;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.Request;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

import static java.awt.Color.BLUE;
import static java.awt.Color.RED;

public class Client {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private JSONObject requestMsg = new JSONObject();
    private JSONObject responseMsg = new JSONObject();


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

    public void sendMessage() {
        try {
            System.out.println("Welcome " + username);
            bufferedWriter.newLine();
            bufferedWriter.flush();


            try (Scanner sc = new Scanner(System.in)) {
                while (socket.isConnected()) {
                    
                    System.out.println("Enter command to run: ");
                    String command = sc.nextLine();
                    if (command.equals("launch")){

                        System.out.println("\n -------------choose your kind--------------------");
                        System.out.println(" -------------------------------------------------");
                        System.out.println(" - Kind         - Distance (steps) -      Shots  -");
                        System.out.println(" -------------------------------------------------");
                        System.out.println(" - 1 Sniper     -       5          -        1    -");
                        System.out.println(" -------------------------------------------------");
                        System.out.println(" - 2 AK-47      -       4          -        2    -");
                        System.out.println(" -------------------------------------------------");
                        System.out.println(" - 3 Steyr AUG  -       3          -        3    -");
                        System.out.println(" -------------------------------------------------");
                        System.out.println(" - 4 M4 carbine -       2          -        4    -");
                        System.out.println(" -------------------------------------------------");
                        System.out.println(" - 5 Pistol     -       1          -        5    -");
                        System.out.println(" -------------------------------------------------");
                        System.out.println(" -------------------------------------------------\n");

                    }

                    System.out.println("Command arguments: ");
                    JSONArray arguments = new JSONArray(sc.nextLine());

                    Request request = new Request(username,command,arguments);

                    bufferedWriter.write(request.toJsonString(username, command, arguments));
                    bufferedWriter.newLine();
                    bufferedWriter.flush();
                }
            }

            System.out.close();


        } catch (IOException e) {
            closeEverything(socket, bufferedReader, bufferedWriter);
        }

    }
    public void listenForMessage(){
        new Thread(new Runnable(){
            @Override
            public void run() {
                String msgFromWorld;
                while (socket.isConnected()){
                    try{
                        msgFromWorld = bufferedReader.readLine();
                        System.out.println("server says: " + msgFromWorld);

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

    public static void main(String[] args) throws IOException {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Welcome to Battle Royale\n");
//            System.out.println("What do you want to name your avatar?");
            String username = sc.nextLine();

            Socket socket = new Socket("192.168.43.240 ",5355);
            Client client = new Client(socket,username);
            client.listenForMessage();
            client.sendMessage();


        }
    }

    public void welcome(){
        System.out.println();

    }

}

