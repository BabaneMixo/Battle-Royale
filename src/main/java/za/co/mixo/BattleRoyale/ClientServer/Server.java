package za.co.mixo.BattleRoyale.ClientServer;


import za.co.mixo.BattleRoyale.Player;
import za.co.mixo.BattleRoyale.world.World;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Server {

    private ServerSocket serverSocket;
    private ClientHandler clientHandler;

    public Server(ServerSocket serverSocket){

        this.serverSocket = serverSocket;
    }

    /**
     * This method starts the server and waits for client connections.
     */
    public void startServer(){
        System.out.println("Server running & waiting for client connections.");

        try {

            while (!serverSocket.isClosed()) {

                Socket socket = serverSocket.accept();
                System.out.println("Connection: " + socket);
                System.out.println("New Robot connected");
                clientHandler = new ClientHandler(socket);
                Thread thread = new Thread(clientHandler);
                thread.start();

            }

            }catch(IOException e){
            closeServerSocket();
        }

    }

    /**
     * This method gets commands from the server and sends relevant output.*/
    public void getServerCommands(){
        Scanner scanner = new Scanner(System.in);
        World world = new World();
        new Thread(() -> {

                while (!serverSocket.isClosed()) {
                    ArrayList<Player> robots = clientHandler.players;
                    ArrayList<String> players = clientHandler.playersList;
                    String command = scanner.nextLine();
                    if (command.equalsIgnoreCase("robots")) {
                        for (Player player : robots){
                            System.out.println(player.getRobots());
                        }

                    } else if (command.equalsIgnoreCase("dump")) {
                        for (Player player : robots){
                            System.out.println(player.getRobots());
                        }
                        world.showObstacles();

                    } else if (command.equalsIgnoreCase("quit")) {
                        System.out.println("Robots have been removed from world.");
                        robots.clear();
                        players.clear();

                    }
                }
        }).start();
    }

    public void closeServerSocket(){
        try{
            if (serverSocket != null){
                serverSocket.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(1538);
        Server server = new Server(serverSocket);
        server.getServerCommands();
        server.startServer();

    }
}