package za.co.mixobabane.battleroyale.Player;


import org.json.JSONObject;
import za.co.mixobabane.battleroyale.Avatar.Avatar;
import za.co.mixobabane.battleroyale.Avatar.Status;
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
                String thisCommand = jsonObject.getString("command");

                if(thisCommand.equals("quit") || thisCommand.equals("exit")){
                    for (Avatar avatar : avatars) {
                        if (avatar.getRobotName().equals(jsonObject.get("name"))){
                            broadCastMessage("Avatar left.");
                            avatars.remove(avatar);
                            System.out.println("Avatar left.");
                            playersList.remove(jsonObject.get("name").toString());

                        }
                    }

                }
                else if (thisCommand.equalsIgnoreCase("launch")

                        && !playersList.contains(jsonObject.get("name").toString())) {

                    Avatar avatar = new Avatar(jsonObject.get("name").toString(),
                            jsonObject.getJSONArray("arguments"), avatars);

                    if (avatar.getRobotName().equals(jsonObject.get("name"))) {
                        avatar.handleCommand(command);
                        avatars.add(avatar);
                        world.addRobotList(avatars);
                        broadCastMessage(avatar.setUserOutput());
                        playersList.add(jsonObject.get("name").toString());
                    }

                }
                else if (avatars.size()>0 && !thisCommand.equalsIgnoreCase("launch")) {
                    for (Avatar avatar : avatars) {
                        Status robotStatus = avatar.getStatus();
                        if(avatar.getRobotName().equals(clientUsername)&&robotStatus == Status.DEAD){
                            avatars.remove(avatar);
                            broadCastMessage("DEAD");

                        }
                        if (avatar.getRobotName().equals(jsonObject.get("name")) && thisCommand.equalsIgnoreCase("reload")
                                && avatars.contains(avatar)) {
                            avatar.handleCommand(command);
                            broadCastMessage(avatar.setUserOutput());
                            Thread.sleep(avatar.getReloadSeconds()*1000L);
                            avatar.setShotsRemaining(avatar.getShots());
                            avatar.setStatus(Status.NORMAL);
                            JSONObject reload = new JSONObject();
                            avatar.setMessage(reload.put("message","Done"));
                            broadCastMessage(avatar.setUserOutput());

                        }else if (avatar.getRobotName().equals(jsonObject.get("name")) && thisCommand.equalsIgnoreCase("repair")
                                &&avatars.contains(avatar)) {
                            avatar.handleCommand(command);
                            broadCastMessage(avatar.setUserOutput());
                            Thread.sleep(avatar.getRepairSeconds()*1000L);
                            avatar.setCurrentShields(avatar.getShields());
                            avatar.setStatus(Status.NORMAL);

                            JSONObject reload = new JSONObject();
                            avatar.setMessage(reload.put("message","Done"));
                            broadCastMessage(avatar.setUserOutput());
                        }
                        else if (avatar.getRobotName().equals(jsonObject.get("name"))&&avatars.contains(avatar)) {
                            avatar.handleCommand(command);
                            broadCastMessage(avatar.setUserOutput());
                        }

                    }
                }else if (playersList.contains(jsonObject.get("name").toString())){
                    broadCastMessage("Robot name has already been used.");
                    removeClientHandler();
                    break;
                }

            }catch (IOException e) {
                System.out.println((e.getMessage()));

                break;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }catch (IllegalArgumentException e){
                broadCastMessage("Sorry! I do not understand.");
            }

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


