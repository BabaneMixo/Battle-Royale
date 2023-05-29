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
    public static final String RED = "\033[0;31m";     // RED
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String username;
    private JSONObject requestMsg = new JSONObject();
    private JSONObject responseMsg = new JSONObject();
    public static final String GREEN_BOLD_BRIGHT = "\033[1;92m"; // GREEN
    public static final String RED_BOLD_BRIGHT = "\033[1;91m";   // RED
    public static final String GREEN_UNDERLINED = "\033[4;32m";  // GREEN
    public static final String BLUE_BOLD_BRIGHT = "\033[1;94m";  // BLUE

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
            bufferedWriter.newLine();
            bufferedWriter.flush();


            try (Scanner sc = new Scanner(System.in)) {
                while (socket.isConnected()) {
                    Thread.sleep(1000);
                    System.out.println(RED_BOLD_BRIGHT+"What do you want to do next "+username+":?");
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
            System.out.println(GREEN_BOLD_BRIGHT+"WELCOME TO BATTLE ROYALE\n");
           System.out.println(RED_BOLD_BRIGHT+"What do you want to name your avatar?");
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

    public static void welcome() throws InterruptedException {
        Thread.sleep(700);
        System.out.println(BLUE_BOLD_BRIGHT+"==================== WELCOME =====================");
        Thread.sleep(700);
        System.out.println("====================== TO ========================");
        Thread.sleep(700);
        System.out.println("===================== BATTLE =====================");
        Thread.sleep(700);
        System.out.println("===================== ROYALE =====================");
        Thread.sleep(700);
        System.out.println("\n ********Choose AVATAR type and Launch*********");
        Thread.sleep(700);
        System.out.println(" -------------------------------------------------");
        Thread.sleep(700);
        System.out.println(" - Kind         - Distance (steps) -      Shots  -");
        Thread.sleep(700);
        System.out.println(" -------------------------------------------------");
        Thread.sleep(700);
        System.out.println(" - 1 Sniper     -       5          -        1    -");
        Thread.sleep(700);
        System.out.println(" -------------------------------------------------");
        Thread.sleep(700);
        System.out.println(" - 2 AK-47      -       4          -        2    -");
        Thread.sleep(700);
        System.out.println(" -------------------------------------------------");
        Thread.sleep(700);
        System.out.println(" - 3 Steyr AUG  -       3          -        3    -");
        Thread.sleep(700);
        System.out.println(" -------------------------------------------------");
        Thread.sleep(700);
        System.out.println(" - 4 M4 carbine -       2          -        4    -");
        Thread.sleep(700);
        System.out.println(" -------------------------------------------------");
        Thread.sleep(700);
        System.out.println(" - 5 Pistol     -       1          -        5    -");
        Thread.sleep(700);
        System.out.println(" -------------------------------------------------");
        System.out.println(" -------------------------------------------------\n");


    }

}

