package za.co.mixobabane.battleroyale.Player;

import org.json.JSONArray;
import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.Request;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private JSONObject requestMsg = new JSONObject();
    private JSONObject responseMsg = new JSONObject();


    public Player(Socket socket, String username) {
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
                    Thread.sleep(1000);
                    System.out.println("What do you want to do next?: ");
                    String input = sc.nextLine();                
                    JSONArray arguments = new JSONArray();
                    String command = input.split(" ")[0];
                    String[] arrayList = input.split(" ");
                    if (arrayList.length>=2){
                        arguments.put(input.split(" ")[1]);
                    }
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

    public static void main(String[] args) throws IOException {
        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Welcome to Battle Royale\n");
           System.out.println("What do you want to name your avatar?");
            String username = sc.nextLine();

            Thread.sleep(1000);
            welcome();

            Socket socket = new Socket("localhost",5355);
            Player player = new Player(socket,username);
            player.listenForMessage();
            player.sendMessage();


        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void welcome(){


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

}

