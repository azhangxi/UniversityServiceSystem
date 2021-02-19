package model;

import java.io.Serializable;

import static model.UsersType.ADMIN;

public class Admin extends User implements Serializable {
    private static final long serialVersionUID = 1L;

    //MODIFY: this
    //EFFECT: Construct a administrator with given information, and determine
    public Admin(String userName, String passWord, String name, String gender, int age) {
        super(userName, passWord, name, gender, age);
    }

    //MODIFY: this, adminFile
    //EFFECT: update this admin with new information to the file
    @Override
    public void updateUserToFile() {
        Users admins = adminFile.readUsers();
        admins.usersHashMap.replace(userName, this);
        adminFile.writeUsers(admins);
    }


    // Auto generated
    @Override
    public String toString() {
        return "Admin{"
                + ", name='" + name + '\''
                + ", userName='" + userName + '\''
                + ", passWord='" + passWord + '\''
                + ", gender='" + gender + '\''
                + ", age=" + age
                + '}';
    }

    //EFFECT: return the UsersType of this user
    @Override
    public UsersType getMyType() {
        return ADMIN;
    }
}
