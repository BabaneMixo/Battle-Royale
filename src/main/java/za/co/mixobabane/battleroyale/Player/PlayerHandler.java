package za.co.mixobabane.battleroyale.Player;


import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.Avatar;
import za.co.mixobabane.battleroyale.World.World;
import za.co.mixobabane.battleroyale.Commands.Commands;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class PlayerHandler implements Runnable {

    public static ArrayList<PlayerHandler> players = new ArrayList<>();
    public static ArrayList<String> playersList = new ArrayList<>();
    public static ArrayList<Avatar> avatars = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;



    public PlayerHandler(Socket socket){

        try{
            this.socket = socket;
            this.bufferedWriter  = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream() ));
            this.bufferedReader  = new BufferedReader(new InputStreamReader(socket.getInputStream() ));

            this.clientUsername = bufferedReader.readLine();
            players.add(this);
            playersList.add(clientUsername);


        }catch (IOException e){
            closeEverything(socket, bufferedReader,bufferedWriter);
        }
    }


    @Override
    public void run() {
        String messageFromClient;
        while(socket.isConnected()){
            try{
                World world = new World(avatars);
                messageFromClient = bufferedReader.readLine();
                System.out.println("Client says: " + messageFromClient);
                JSONObject jsonObject = new JSONObject(messageFromClient);
                Commands command = (Commands) Commands.makeCommand(jsonObject);


                if (avatars.size()==0 || jsonObject.get("command").equals("launch")
                && !playersList.contains(clientUsername)) {

                    Avatar avatar = new Avatar(jsonObject.get("name").toString(),
                            jsonObject.getJSONArray("arguments"),avatars);

                    if (avatar.getRobotName().equals(jsonObject.get("name"))){
                        avatar.handleCommand(command);
                        avatars.add(avatar);
                        world.addRobotList(avatars);
                        broadCastMessage(avatar.setUserOutput().toString(4));
                        broadCastMessage(world.getRobots().toString());
                    }else {
                        broadCastMessage("Avatar name has already been used.");
                        continue;
                    }

                // }else if (robots.size()>0 && jsonObject.get("command").equals("robots")) {
                //     for (Avatar avatar : robots) {

                //         avatar.handleCommand(command);
                //         broadCastMessage(avatar.setUserOutput().toString());
                //     }

                }else if (avatars.size()>0 && !jsonObject.get("command").equals("launch")) {
                for (Avatar avatar : avatars) {
                    if (avatar.getRobotName().equals(jsonObject.get("name"))) {
                        avatar.handleCommand(command);
                        broadCastMessage(avatar.setUserOutput().toString());
                    }
                }
            }

            }catch (IOException e){
                System.out.println((e.getMessage()));
                break;}
            
        }

    }

    public void broadCastMessage(String messageToSend){
        for(PlayerHandler clientHandler: players){
            try{

                if (clientHandler.clientUsername.equals(clientUsername)){
                    clientHandler.bufferedWriter.write(messageToSend);
                    clientHandler.bufferedWriter.newLine();
                    clientHandler.bufferedWriter.flush();}

            }catch (IOException e){
            closeEverything(socket, bufferedReader,bufferedWriter);
            break;}
        }
    }
    public void removeClientHandler(){
        players.remove(this);
//        broadCastMessage(clientUsername);
    }

    public void closeEverything( Socket socket, BufferedReader bufferedReader,BufferedWriter bufferedWriter){
        removeClientHandler();
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

}


