//package ui.oldui;
//
//import model.*;
//import ui.Main;
//
//import static model.UsersType.STUDENT;
//
//public class StudentUI extends UserUI {
//
//    // EFFECT: Get user input, to register, login or return to main ui.
//    public void ui() {
//        System.out.println("\n\n\nWelcome, please choose your operation:");
//        System.out.println("1: Register\t2: Login\t other key: Main Menu");
//        Input input = new Input();
//        String inp = input.inputString();
//        if (inp.equals("1")) {
//            register();
//            ui();
//        } else if (inp.equals("2")) {
//            login();
//        }
//        Main.ui();
//    }
//
//    private void ui(String userName) {
//        courses = courseFile.readCourses();
//        students = studentFile.readUsers();
//        System.out.println("Welcome to student ui, " + students.usersHashMap.get(userName).getName());
//        while (true) {
//            String input = selectOperation();
//            if (input.equals("1")) {
//                modifyPassWord(userName);
//            } else if (input.equals("2")) {
//                addOrDropCourse(userName);
//            } else if (input.equals("3")) {
//                Student student = (Student) students.usersHashMap.get(userName);
//                System.out.println(student.toString());
//            } else {
//                Main.ui();
//            }
//        }
//    }
//
//    // MODIFY: studentFile
//    // EFFECT: create a new student in File with various information provided.
//    private void register() {
//        if (studentFile.readUsers() != null) {
//            students = studentFile.readUsers();
//        } else {
//            students = new Users(STUDENT);
//        }
//        String userName = enterUsername();
//        String passWord = enterPassword();
//        // prompt to enter name
//        String name = enterName();
//        // prompt to enter the gender, only M and F are allowed, ignore case.
//        String gender = enterGender();
//        // prompt to enter the age
//        int age = enterAge();
//        // save the student information
//        saveStudent(students, userName, passWord, name, gender, age);
//    }
//
//    // EFFECT: helper method for register to get username
//    private String enterUsername() {
//        while (true) {
//            System.out.println("Please enter your username(The length should be 4-16 characters)"
//                    + "\nOr 'esc' to return to main menu：");
//            String userName = inp.inputString();
//            if (userName.equalsIgnoreCase("esc")) {
//                Main.ui();
//            }
//            if (checkUserName(userName)) {
//                System.out.println("The username is free to register！");
//                return userName;
//            }
//        }
//    }
//
//    // EFFECT: helper method for enterUsername to check if new username is valid
//    private boolean checkUserName(String userName) {
//        if (userName.length() < 4) {
//            System.out.print("The Username " + userName + " is too short");
//            return false;
//        } else if (userName.length() >= 16) {
//            System.out.println("The Username " + userName + " is too long");
//            return false;
//        } else if (students.usersHashMap.containsKey(userName)) {
//            System.out.println("The Username " + userName + " is registered, choose another username by ");
//            return false;
//        }
//        return true;
//    }
//
//    // Modify: students, Files
//    // EFFECT: helper method for register to save student to file
//    private void saveStudent(Users students,
//                             String userName, String password, String name, String gender, int age) {
//        User student = new Student(userName, password, name, gender, age);
//        students.addNewUser(student);
//        System.out.println("Registration succeed!");
//    }
//
//    // Effect: Check student's username and password matches their profile;
//    //         go to loginSucceedOrFailed
//    private void login() {
//        students = studentFile.readUsers();
//        // check if the username exist in system or go back to ui
//        String userName = checkLoginUsername();
//        // Password verification, entering wrong password 3 times consecutively will lead to deletion of the account.
//        int j = 3;
//        while (j > 0) {
//            System.out.println("entering wrong password 3 times consecutively will lead to deletion of the account."
//                    + "\nPassword:");
//
//            String passWord = inp.inputString();
//            if (students.usersHashMap.get(userName).getPassWord().equals(passWord)) {
//                break;// login succeed
//            }
//            System.out.println("incorrect password, you have " + (j - 1)
//                    + " attempts left...\n");// incorrect password
//            j--;
//        }
//        // check if 3 attempts are used.
//        if (loginSucceedOrFailed(j, students, userName)) {
//            ui(userName);
//        } else {
//            Main.ui();
//        }
//    }
//
//    //EFFECT: helper method for login
//    private String checkLoginUsername() {
//        while (true) {
//            System.out.println("\nUsername\t(enter esc to return to main ui):");
//            String userName = inp.inputString();
//            if (userName.equalsIgnoreCase("esc")) {
//                Main.ui();
//            }
//            if (students.usersHashMap.containsKey(userName)) {
//                return userName;
//            } else {
//                System.out.println("Username does not exist, re-type");
//            }
//        }
//    }
//
//    // MODIFY: students0
//    // EFFECT: remove the student account or go to modifyPassWord
//    private boolean loginSucceedOrFailed(int j, Users students, String userName) {
//        if (j == 0) {
//            students.usersHashMap.remove(userName);// remove the account
//            System.out.println("Login failed... The account has been deleted");
//            return false;
//        } else {
//            System.out.println("Login succeed！");
//            return true;
//        }
//    }
//
//    // EFFECT: get user's operation and return it to ui
//    private String selectOperation() {
//        System.out.println("\nPlease select your operation: 1: Modify Password\t"
//                + "2: Add or Drop Courses\t" + "3: View Personal Profile\t"
//                + "other key: log out and go back to main menu");
//        return inp.inputString();
//    }
//
//    // MODIFY: studentFile
//    // EFFECT:
//    private void modifyPassWord(String userName) {
//        Users students = studentFile.readUsers();
//        boolean flag = true;
//        while (flag) {
//            flag = subModifyPassWord(students, userName);
//        }
//    }
//
//    private boolean subModifyPassWord(Users students, String userName) {
//        String oldpw = enterOldPassword();
//        if (oldpw.equalsIgnoreCase("esc")) {
//            ui(userName);
//        } else if (students.usersHashMap.get(userName).getPassWord().equals(oldpw)) {
//            System.out.println("New password:");
//            String newpw = inp.inputString();
//            // check if the new password is the same as the old one
//            if (oldpw.equals(newpw)) {
//                System.out.println("New password is the same as the old password，please enter a new password...");
//            } else {
//                // update the new password to server
//                students.usersHashMap.get(userName).setPassWord(newpw);
//                studentFile.writeUsers(students);
//                System.out.println("Password changed...");
//                return false;
//            }
//        } else {
//            System.out.println("incorrect password\n");
//        }
//        return true;
//    }
//
//    // helper method for entering old password
//    private String enterOldPassword() {
//        System.out.println("Old password:\nOr enter esc to return to ui");
//        return inp.inputString();
//    }
//
//    // EFFECT: add or drop a course for the student
//    private void addOrDropCourse(String userName) {
//        for (Course c : courses.courseHashMap.values()) {
//            System.out.println(c);
//        }
//        System.out.println("enter one of the course name from above");
//        String courseName = inp.inputString();
//        Student student = (Student) students.usersHashMap.get(userName);
//        student.addOrDropCourse(courseName);
//    }
//}
