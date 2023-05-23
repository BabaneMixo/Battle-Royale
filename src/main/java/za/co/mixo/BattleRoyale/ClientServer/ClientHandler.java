package za.co.mixo.BattleRoyale.ClientServer;

import org.json.JSONObject;
import za.co.mixo.BattleRoyale.Player;
import za.co.mixo.BattleRoyale.Status;
import za.co.mixo.BattleRoyale.commands.Commands;
import za.co.mixo.BattleRoyale.world.World;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientHandler implements Runnable {

    public static ArrayList<ClientHandler> clientHandlers = new ArrayList<>();
    public static ArrayList<String> playersList = new ArrayList<>();
    public static ArrayList<Player> players = new ArrayList<>();
    private Socket socket;
    private BufferedReader bufferedReader;
    private BufferedWriter bufferedWriter;
    private String clientUsername;



    public ClientHandler(Socket socket){

        try{
            this.socket = socket;
            this.bufferedWriter  = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream() ));
            this.bufferedReader  = new BufferedReader(new InputStreamReader(socket.getInputStream() ));

            this.clientUsername = bufferedReader.readLine();
        }catch (IOException e){
            closeEverything(socket, bufferedReader,bufferedWriter);
        }
    }


    @Override
    public void run() {
        String messageFromClient;

        while(socket.isConnected()){
            try{

                World world = new World(players);
                messageFromClient = bufferedReader.readLine();
                System.out.println("Client says: " + messageFromClient);
                JSONObject jsonObject = new JSONObject(messageFromClient);

                Commands command = Commands.makeCommand(jsonObject);
                String thisCommand = jsonObject.getString("command");
                for (Player player : players) {
                    Status robotStatus = player.getStatus();
                    if (robotStatus == Status.DEAD) {
                        players.remove(player);
                    }
                }
                if(thisCommand.equals("quit") || thisCommand.equals("exit")){
                    for (Player player : players) {
                        if (player.getRobotName().equals(jsonObject.get("name"))){
                            broadCastMessage("Robot left.");
                            players.remove(player);
                            System.out.println("Robot left.");
                            playersList.remove(jsonObject.get("name").toString());

                        }
                    }

                }
                else if (thisCommand.equalsIgnoreCase("launch")

                && !playersList.contains(jsonObject.get("name").toString())) {

                    Player player = new Player(jsonObject.get("name").toString(),
                            jsonObject.getJSONArray("arguments"), players);

                    if (player.getRobotName().equals(jsonObject.get("name"))) {
                        player.handleCommand(command);
                        players.add(player);
                        world.addRobotList(players);
                        broadCastMessage(player.setUserOutput());
                        playersList.add(jsonObject.get("name").toString());
                    }

                }
                else if (players.size()>0 && !thisCommand.equalsIgnoreCase("launch")) {
                for (Player player : players) {
                    Status robotStatus = player.getStatus();
                    if(player.getRobotName().equals(clientUsername)&&robotStatus == Status.DEAD){
                        broadCastMessage("DEAD");

                        players.remove(player);
                    }
                    if (player.getRobotName().equals(jsonObject.get("name")) && thisCommand.equalsIgnoreCase("reload")
                    && players.contains(player)) {
                        player.handleCommand(command);
                        broadCastMessage(player.setUserOutput());
                        Thread.sleep(player.getReloadSeconds()*1000L);
                        player.setShotsRemaining(player.getShots());
                        player.setStatus(Status.NORMAL);
                        JSONObject reload = new JSONObject();
                        player.setMessage(reload.put("message","Done"));
                        broadCastMessage(player.setUserOutput());

                    }else if (player.getRobotName().equals(jsonObject.get("name")) && thisCommand.equalsIgnoreCase("repair")
                    && players.contains(player)) {
                        player.handleCommand(command);
                        broadCastMessage(player.setUserOutput());
                        Thread.sleep(player.getRepairSeconds()*1000L);
                        player.setCurrentShields(player.getShields());
                        player.setStatus(Status.NORMAL);

                        JSONObject reload = new JSONObject();
                        player.setMessage(reload.put("message","Done"));
                        broadCastMessage(player.setUserOutput());
                    }
                    else if (player.getRobotName().equals(jsonObject.get("name"))&& players.contains(player)) {
                        player.handleCommand(command);
                        broadCastMessage(player.setUserOutput());
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

            try{
                    this.bufferedWriter.write(messageToSend);
                    this.bufferedWriter.newLine();
                    this.bufferedWriter.flush();

            }catch (IOException e){
            closeEverything(socket, bufferedReader,bufferedWriter);
            }
    }

    public void removeClientHandler(){
        clientHandlers.remove(this);
        broadCastMessage("Robot left.");
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


