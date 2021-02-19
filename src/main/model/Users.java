package model;

import exceptions.EmptyException;
import exceptions.ModifyingSuperAdminException;
import exceptions.SystemFullException;
import exceptions.UserDoesNotExistException;
import file.UserFile;
import ui.Main;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

import static model.SuperAdmin.SUPER_ADMIN;
import static model.UsersType.*;

public class Users implements Serializable {
    private static final long serialVersionUID = 7L;
    public LinkedHashMap<String, User> usersHashMap;
    private int maximumUsers;
    private UserFile userFile;
    private UsersType type;

    public Users(UsersType type) {
        this.type = type;
        usersHashMap = new LinkedHashMap<>();
        switch (type) {
            case STUDENT:
                userFile = new UserFile(Main.STU_PATH);
                maximumUsers = 10;
                break;
            case INSTRUCTOR:
                userFile = new UserFile(Main.INS_PATH);
                maximumUsers = 10;
                break;
            default:
                userFile = new UserFile(Main.ADM_PATH);
                maximumUsers = 5;
                break;
        }
    }

    // EFFECT: sout all the students in system
    // No longer using
    public boolean showAllUsers() {
        try {
            if (usersHashMap.isEmpty()) {
                throw new EmptyException();
            }
            return true;
        } catch (EmptyException e) {
            System.out.println("No user found in system\n");
            return false;
        } finally {
            for (User user : usersHashMap.values()) {
                System.out.println(user + "\n");
            }
        }
    }

    // EFFECT: delete All students in file
    // No Longer using
    public void deleteAllUsers() {
        if (type == ADMIN) {
            if (usersHashMap.containsKey("superAdmin") && usersHashMap.size() == 1) {
                try {
                    throw new ModifyingSuperAdminException("The only user is super admin");
                } catch (ModifyingSuperAdminException e) {
                    e.getMessage();
                }
            }
            usersHashMap.clear();
            usersHashMap.put(SUPER_ADMIN.getUserName(), SUPER_ADMIN);
            userFile.writeUsers(this);
            return;
        }

        usersHashMap.clear();
        userFile.writeUsers(this);
    }

    // REQUIRE: Username not registered in system
    // MODIFY: this, userFile
    // EFFECT: Add a new user at the end of users,
    //         return true if added, false if users is full.
    public boolean addNewUser(User user) {
        try {
            checkSystemFull();
            usersHashMap.put(user.getUserName(), user);
            userFile.writeUsers(this);
            return true;
        } catch (SystemFullException e) {
            return false;
        }
    }

    // EFFECT: check if system reached maximum number of users,
    //         throw systemFullException if true
    private void checkSystemFull() throws SystemFullException {
        if (usersHashMap.size() >= maximumUsers) {
            throw new SystemFullException();
        }
    }

    // MODIFY: this, userFile
    // EFFECT: delete a user form system,
    //         return true if delete successfully, false if no such user in system.
    public boolean deleteUser(User user) {
        try {
            if (user == SUPER_ADMIN) {
                throw new ModifyingSuperAdminException("Warning! Trying to modify Super Administrator");
            } else {
                if (!usersHashMap.containsKey(user.getUserName())) {
                    throw new UserDoesNotExistException("User Does Not Exist");
                }
                deleteAction(user);
            }
            userFile.writeUsers(this);
            return true;
        } catch (ModifyingSuperAdminException e) {
            System.exit(0);
            return false;
        } catch (UserDoesNotExistException e) {
            return false;
        }
    }

    private void deleteAction(User user) {
        List<Course> listToDrop = new ArrayList<>();
        if (type == STUDENT) {
            Student student = (Student) user;
            listToDrop.addAll(student.getCourses().courseHashMap.values());
            for (int i = 0; i < listToDrop.size(); i++) {
                student.dropCourse(listToDrop.get(i).makeStringKey(), listToDrop.get(i));
            }
        } else if (type == INSTRUCTOR) {
            Instructor instructor = (Instructor) user;
            listToDrop.addAll(instructor.getCoursesTeaching().courseHashMap.values());
            for (int i = 0; i < listToDrop.size(); i++) {
                instructor.deleteCourseTeaching(listToDrop.get(i).makeStringKey());
            }
        }

        usersHashMap.remove(user.getUserName());
    }

    //Auto Generated equals and hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Users)) {
            return false;
        }
        Users users = (Users) o;
        return Objects.equals(usersHashMap, users.usersHashMap)
                && getType() == users.getType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(usersHashMap, getType());
    }

    public UsersType getType() {
        return type;
    }

//    @Override
//    public String toString() {
//        StringBuilder usersString = new StringBuilder();
//        for (User user : usersHashMap.values()) {
//            usersString.append(user.toString()).append("\n");
//        }
//        return usersString.toString();
//    }
}
