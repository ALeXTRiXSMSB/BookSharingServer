package database;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectSet;
import databaseObjects.*;
import java.util.List;


public class DatabaseServer {

    private ObjectContainer database;

    public DatabaseServer() {
        this.database = Db4oEmbedded.openFile(Db4oEmbedded.newConfiguration(),"BookServer.store");
    }

    public void createUser(User newUser){
        this.database.store(newUser);
    }

    public void updateUser(User exsistUser){
        ObjectSet result = this.database.queryByExample(findUser(exsistUser.getUniqueName()));
        User found = (User)result.next();
        if(found != null){
            System.out.println("Der User: " + exsistUser.getUniqueName() + " ist up-to-date");
            this.database.store(found);
        }
        else{
            System.out.println("User nicht gefunden");
        }
    }

    public boolean findUser(String username, String password){
        boolean foundFlag = false;
        List<User> allusers = getAllUser();
        for(User tmp : allusers){
            if(tmp.getUniqueName().equals(username) && tmp.getPassword().equals(password)){
                foundFlag = true;
            }
        }
        System.out.println(foundFlag);
        return foundFlag;
    }

    public User findUser(String username){
        List<User> allUsers = getAllUser();
        User rueckgabe = null;
        for(User tmp : allUsers){
            if(tmp.getUniqueName().equals(username)){
                System.out.println("User gefunden");
                rueckgabe = tmp;
            }
        }
        return rueckgabe;
    }

    public void deleteUser(User exsistUser){
        ObjectSet result = this.database.queryByExample(findUser(exsistUser.getUniqueName()));
        User found = (User)result.next();
        if(found != null){
            System.out.println("Der User: " + found.getUniqueName() + " wird gelöscht");
            this.database.delete(found);
        }
        else{
            System.out.println("User nicht gefunden");
        }
    }

    public void deleteThread(User exsistUser, int threadID){
        ObjectSet result = this.database.queryByExample(findUser(exsistUser.getUniqueName()));
        User found = (User)result.next();
        if(found != null){
            System.out.println("Der User: " + found.getUniqueName() + " wurde gefunden der Thread mit der ID: "+threadID+" wird gelöscht");
            this.database.store(found);
        }
    }

    private List<User> getAllUser(){
        User empty = new User(null,null);
        return database.queryByExample(empty);
    }

    public ObjectContainer getDatabase() {
        return database;
    }

    public void setDatabase(ObjectContainer database) {
        this.database = database;
    }
}
