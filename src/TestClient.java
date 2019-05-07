import databaseObjects.User;
import serverProgramm.LoginClient;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class TestClient {

    public static void main(String[] args0){
        try{
            Socket s = new Socket("127.0.0.1",50000);
            ObjectOutputStream oos = new ObjectOutputStream(s.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
            oos.writeObject(new LoginClient("Alexander","passwort"));
            oos.flush();
            User tmp;
            while(true){
                tmp = (User)ois.readObject();
                if(tmp != null){
                    System.out.println(tmp.getUniqueName());
                    break;
                }
            }

        }catch(IOException | ClassNotFoundException e){
            e.printStackTrace();
        }

    }
}
