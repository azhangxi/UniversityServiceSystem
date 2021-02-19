package file;

import model.*;
import ui.Main;

import java.io.*;

import static model.SuperAdmin.SUPER_ADMIN;
import static model.UsersType.*;


public class UserFile extends File {
    private UsersType type;
    private Users users;


    //MODIFY: this
    //EFFECT: Construct a user file with given pathname, and determine the type of users in it by the pathname
    public UserFile(String pathname) {
        super(pathname);
        if (pathname.endsWith("Students")) {
            type = STUDENT;
        } else if (pathname.endsWith("Instructors")) {
            type = INSTRUCTOR;
        } else if (pathname.endsWith("Admins")) {
            type = ADMIN;
        }
    }


    //MODIFY: this
    //EFFECT: save default users into this file
    public boolean initializeTheFile() {
        File file = new File(getPath());
        try {
            boolean b = file.createNewFile();
            if (b) {
                writeUsers(initializeTheUsers());
                System.out.println("----------------" + type + "'s Data file initialized!----------------");
                return true;
            }
        } catch (IOException e) {
            System.out.println("Something is wrong with I/O");
        }
        return false;
    }

    //MODIFY: this
    //EFFECT: save given users into the file
    public boolean writeUsers(Users users) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(getPath())));
            oos.writeObject(users);
            oos.close();
            return true;
        } catch (IOException e) {
            System.out.println("something is wrong with the target writing");
            return false;
        }
    }

    //EFFECT: read the given users from the file
    public Users readUsers() {
        users = new Users(type);
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(getPath())));
            users = (Users) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    private Users initializeTheUsers() {
        switch (type) {
            case STUDENT:
                users = initStudentsInFile();
                break;
            case INSTRUCTOR:
                users = initInstructorsInFile();
                break;
            default:
                users = initAdminsInFile();
                break;
        }
        return users;
    }

    private Users initStudentsInFile() {
        users = new Users(type);
        users.usersHashMap.put("defaultStudent1", new Student("defaultStudent1", "defaultStudent1",
                "defaultStudent1", "Male", 18));
        users.usersHashMap.put("defaultStudent2", new Student("defaultStudent2", "defaultStudent2",
                "defaultStudent2", "Male", 27));
        users.usersHashMap.put("defaultStudent3", new Student("defaultStudent3", "defaultStudent3",
                "defaultStudent3", "Male", 36));
        users.usersHashMap.put("defaultStudent4", new Student("defaultStudent4", "defaultStudent4",
                "defaultStudent4", "Male", 45));
        users.usersHashMap.put("defaultStudent5", new Student("defaultStudent5", "defaultStudent5",
                "defaultStudent5", "Male", 54));

        return users;
    }

    private Users initAdminsInFile() {
        users = new Users(type);
        users.usersHashMap.put("defaultAdmin", new Admin("defaultAdmin", "defaultAdmin",
                "defaultAdmin","Male",70));
        users.usersHashMap.put("superAdmin", SUPER_ADMIN);
        return users;
    }

    private Users initInstructorsInFile() {
        users = new Users(type);
        Courses courses = new Courses();
        CourseFile courseFile = new CourseFile(Main.CRS_PATH);
        courses.courseHashMap.put("CPSC110-101", courseFile.readCourses().courseHashMap.get("CPSC110-101"));
        users.usersHashMap.put("defaultInstructor1", new Instructor("defaultInstructor1",
                "defaultInstructor1", "defaultInstructor1", "Male", 53, courses));
        users.usersHashMap.put("defaultInstructor2", new Instructor("defaultInstructor2",
                "defaultInstructor2", "defaultInstructor2", "Male", 35, courses));
        users.usersHashMap.put("defaultInstructor3", new Instructor("defaultInstructor3",
                "defaultInstructor3", "defaultInstructor3", "Male", 59, courses));
        users.usersHashMap.put("defaultInstructor4", new Instructor("defaultInstructor4",
                "defaultInstructor4", "defaultInstructor4", "Male", 42, courses));
        users.usersHashMap.put("defaultInstructor5", new Instructor("defaultInstructor5",
                "defaultInstructor5", "defaultInstructor5", "Male", 29, courses));

        return users;
    }

}
