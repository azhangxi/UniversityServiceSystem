package model;

import file.CourseFile;
import file.UserFile;

import java.io.Serializable;
import java.util.Objects;

import static ui.Main.*;

public abstract class User implements Serializable {
    private static final long serialVersionUID = 0L;
    UserFile studentFile = new UserFile(STU_PATH);
    CourseFile courseFile = new CourseFile(CRS_PATH);
    UserFile instructorFile = new UserFile(INS_PATH);
    UserFile adminFile = new UserFile(ADM_PATH);

    String userName;
    String passWord;
    String name;
    String gender;
    int age;

    public User(String userName, String passWord, String name, String gender, int age) {
        this.userName = userName;
        this.passWord = passWord;
        this.name = name;
        this.gender = gender;
        this.age = age;
    }


    public abstract void updateUserToFile();

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public abstract String toString();

    //auto generated equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User user = (User) o;
        return getAge() == user.getAge()
                && Objects.equals(getUserName(), user.getUserName())
                && Objects.equals(getPassWord(), user.getPassWord())
                && Objects.equals(getName(), user.getName())
                && Objects.equals(getGender(), user.getGender());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUserName(), getPassWord(), getName(), getGender(), getAge());
    }

    public abstract UsersType getMyType();
}
