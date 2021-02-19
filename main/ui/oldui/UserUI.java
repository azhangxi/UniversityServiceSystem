//package ui.oldui;
//
//import file.CourseFile;
//import file.UserFile;
//import model.Courses;
//import model.Users;
//import ui.Main;
//
//public abstract class UserUI {
//    Input inp = new Input();
//    UserFile studentFile = new UserFile(Main.STU_PATH);
//    UserFile instructorFile = new UserFile(Main.INS_PATH);
//    UserFile adminFile = new UserFile(Main.ADM_PATH);
//    CourseFile courseFile = new CourseFile(Main.CRS_PATH);
//    Users students;
//    Users instructors;
//    Users admins;
//    Courses courses;
//
//    // EFFECT: let user set password,
//    //         return password;
//    String enterPassword() {
//        while (true) {
//            // prompt to enter the password twice，if enters different passwords, prompt again
//            System.out.println("Please enter your password:");
//            String passWord1 = inp.inputString();
//            System.out.println("Please enter your password again:");
//            String passWord2 = inp.inputString();
//            // check if passwords are the same
//            if (passWord1.equals(passWord2)) {
//                return passWord1;
//            } else {
//                System.out.println("You entered different passwords second time, please enter again...");
//            }
//        }
//    }
//
//    // EFFECT: let user type name and return it.
//    String enterName() {
//        System.out.println("Please enter your name:");
//        return inp.inputString();
//    }
//
//    // REQUIRE: input must be M or F ignore case;
//    // EFFECT: let user set gender;
//    //         return gender
//    String enterGender() {
//        while (true) {
//            System.out.println("Please enter your gender: M/F");
//            String gender = inp.inputString().toUpperCase();
//            if (gender.equals("M") || gender.equals("F")) {
//                return gender;
//            } else {
//                System.out.println("Input error...");
//            }
//        }
//    }
//
//    //EFFECT: let user tpye age and return it.
//    int enterAge() {
//        System.out.println("Please enter your age：");
//        int age;
//        while (true) {
//            age = inp.inputInt();
//            if (age >= 0) {
//                return age;
//            } else {
//                System.out.println("Your age is below 0, please enter a valid age.");
//            }
//        }
//    }
//}
