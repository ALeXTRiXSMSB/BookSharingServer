package serverProgramm;

import database.DatabaseServer;
import databaseObjects.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable {

    private static final int port = 50000;
    private Socket threadSocket;

    public Server(Socket uebergabeSocket) {
        this.threadSocket = uebergabeSocket;
    }

    public static void main(String[] args0) {
        try {
            ServerSocket server = new ServerSocket(port);
            while (true) {
                Socket socket = server.accept();
                Server serverThread = new Server(socket);
                serverThread.run();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public void run() {
        try {
            ObjectInputStream ois = new ObjectInputStream(this.threadSocket.getInputStream());
            ObjectOutputStream oos = new ObjectOutputStream(this.threadSocket.getOutputStream());
            DatabaseServer db = new DatabaseServer();
            Object inputfromClient = ois.readObject();
            if (inputfromClient.getClass() == LoginClient.class) {
                LoginClient login = (LoginClient) inputfromClient;
                if (db.findUser(login.getUsername(), login.getPassword())) {
                    User returnUser = db.findUser(login.getUsername());
                    oos.writeObject(returnUser);
                    oos.flush();
                }
            }
            if(inputfromClient.getClass() == CreateUser.class){
                CreateUser create = (CreateUser) inputfromClient;
                User tmp = db.findUser(create.getUsername());
                if(tmp == null){
                    db.createUser(new User(create.getUsername(),create.getPassword()));
                }
                else{
                    System.out.println("User ist schon da");
                }
            }
            ois.close();
            db.getDatabase().close();
            this.threadSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}


