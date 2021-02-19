//package ui.oldui;
//
//import exceptions.UserDoesNotExistException;
//import model.Student;
//import model.User;
//import model.Users;
//import ui.Main;
//
//import static model.UsersType.STUDENT;
//
//public class AdminUI extends UserUI {
//
//    // EFFECT: check if the user has access to admin ui, and jump to the right ui for user
//    public void login() {
//        System.out.println("Please enter username:\t");
//        String inputUserName = inp.inputString();
//        System.out.println("Please enter password:\t");
//        String inputPassword = inp.inputString();
//        admins = adminFile.readUsers();
//        if (inputUserName.equals("superAdmin")
//                && inputPassword.equals(admins.usersHashMap.get("superAdmin").getPassWord())) {
//            superAdminUI();
//        } else if (admins.usersHashMap.containsKey(inputUserName)
//                && admins.usersHashMap.get(inputUserName).getPassWord().equals(inputPassword)) {
//            ui();
//        } else {
//            System.out.println("Login failed...");
//            Main.ui();
//        }
//    }
//
//    // EFFECT: choose the right manage method for user
//    private void ui() {
//        System.out.println("\n1: Manage Students\t2: Manage Teachers\tOther Keys: Logout");
//        String input = inp.inputString();
//        if (input.equals("1")) {
//            manageStudents();
//        } else if (input.equals("2")) {
//            manageTeachers();
//        } else {
//            Main.ui();
//        }
//    }
//
//    // EFFECT: go to the right manage method for superAdministrator
//    private void superAdminUI() {
//        System.out.println("\n1: Manage Students\t2: Manage Teachers\t3: Manage Administrators\tOther Keys: Logout");
//        String input = inp.inputString();
//        if (input.equals("1")) {
//            manageStudents();
//        } else if (input.equals("2")) {
//            manageTeachers();
//        } else if (input.equals("3")) {
//            manageAdmins();
//        } else {
//            Main.ui();
//        }
//    }
//
//    // EFFECT: To do series of operation with students go to the right operation for user
//    private void manageStudents() {
//        students = studentFile.readUsers();
////        instructors = instructorFile.readUsers();
//        System.out.println("\n1: Show all students\t2: Delete all\t3: Show/modify specific student\tOther Keys: ui");
//        String input = inp.inputString();
//        if (input.equals("1")) {
//            students.showAllUsers();
////            manageStudents();
//        } else if (input.equals("2")) {
//            deleteAllStudents();
//        } else if (input.equals("3")) {
//            System.out.println("1: Show,\tother keys: Modify");
//            if (inp.inputString().equals("1")) {
//                showSpecificStudents();
//            } else {
//                modifyStudent(studentFile.readUsers());
//            }
//            manageStudents();
//        }
//    }
//
//    private void deleteAllStudents() {
//        students.deleteAllUsers();
//        manageStudents();
//    }
//
//    private void showSpecificStudents() {
//        try {
//            getSpecificStudents(studentFile.readUsers());
//        } catch (UserDoesNotExistException e) {
//            System.out.println("No such student found");
//        }
//
//
//    }
//
//    private void showStudentsHelper(Users students) {
//        System.out.println("Specific students:\n");
//        for (User student : students.usersHashMap.values()) {
//            System.out.println(student);
//        }
//    }
//
//    // EFFECT: Let user choose what to modify
//    private void modifyStudent(Users students) {
//        try {
//            Users specificStudents = getSpecificStudents(students);
//            whatToModify(specificStudents);
//        } catch (UserDoesNotExistException e) {
//            System.out.println("No such student found");
//        }
//    }
//
//    // EFFECT: get user's instruction
//    private void whatToModify(Users specificStudents) {
//        System.out.println("\n0: deleteStudent\t1: password\t2: Name\t3: Gender\t"
//                + "4: Age\t5: Add/Drop Course\t6: update course grade");
//        String input = inp.inputString();
//        if (input.equals("0")) {
//            deleteStudent(specificStudents);
//        } else if (input.equals("1")) {
//            modifyStudentPassword(specificStudents);
//        } else if (input.equals("2")) {
//            modifyStudentName(specificStudents);
//        } else if (input.equals("3")) {
//            modifyStudentGender(specificStudents);
//        } else if (input.equals("4")) {
//            modifyStudentAge(specificStudents);
//        } else if (input.equals("5")) {
//            addOrDropCourseForStudents(specificStudents);
//        }
//    }
//
//    // MODIFY: students
//    // EFFECT: helper method
//    private void deleteStudent(Users specificStudents) {
//        for (User student : specificStudents.usersHashMap.values()) {
//            students.deleteUser(student);
//        }
//    }
//
//    // MODIFY: studentFile
//    // EFFECT: Change the password and save into file
//    private void modifyStudentPassword(Users students) {
//        for (User student : students.usersHashMap.values()) {
//            System.out.println(student.getUserName() + "'s New Password:");
//            String newpw = inp.inputString();
//            student.setPassWord(newpw);
//            Users studentsInFile = studentFile.readUsers();
//            studentsInFile.usersHashMap.replace(student.getUserName(), student);
//            studentFile.writeUsers(studentsInFile);
//            System.out.println("Password changed to " + newpw);
//        }
//    }
//
//    // MODIFY: studentFile
//    // EFFECT: Change the name and save into file
//    private void modifyStudentName(Users students) {
//        for (User student : students.usersHashMap.values()) {
//            System.out.println(student.getUserName() + "'s New Name:");
//            String newName = inp.inputString();
//            student.setName(newName);
//            Users studentsInFile = studentFile.readUsers();
//            studentsInFile.usersHashMap.replace(student.getUserName(), student);
//            studentFile.writeUsers(studentsInFile);
//            System.out.println("Name changed to " + newName);
//        }
//    }
//
//    // MODIFY: studentFile
//    // EFFECT: Change the gender and save into file
//    private void modifyStudentGender(Users students) {
//        for (User student : students.usersHashMap.values()) {
//            System.out.println(student.getUserName() + "'s New Gender:");
//            String newGender = inp.inputString();
//            student.setGender(newGender);
//            Users studentsInFile = studentFile.readUsers();
//            studentsInFile.usersHashMap.replace(student.getUserName(), student);
//            studentFile.writeUsers(studentsInFile);
//            System.out.println("Gender changed to " + newGender);
//        }
//    }
//
//    // MODIFY: studentFile
//    // EFFECT: Change the age and save into file
//    private void modifyStudentAge(Users students) {
//        for (User student : students.usersHashMap.values()) {
//            System.out.println(student.getUserName() + "'s New Age:");
//            int newAge = inp.inputInt();
//            student.setAge(newAge);
//            Users studentsInFile = studentFile.readUsers();
//            studentsInFile.usersHashMap.replace(student.getUserName(), student);
//            studentFile.writeUsers(studentsInFile);
//            System.out.println("Age changed to " + newAge);
//        }
//    }
//
//    // MODIFY: studentFile
//    // EFFECT: Add or drop a course for students
//    private void addOrDropCourseForStudents(Users students) {
//        for (User stu: students.usersHashMap.values()) {
//            Student student = (Student) stu;
//            System.out.println(student.getUserName() + "'s Course to add or drop:");
//            String courseName = inp.inputString();
//            student.addOrDropCourse(courseName);
//        }
//    }
//
//
//    // EFFECT: search for students fits the right conditions and return the students.
//    //         return original students if no such student is found.
//    private Users getSpecificStudents(Users students) throws UserDoesNotExistException {
//        int sc0 = gssOutput();
//        Users specificStudents = students;
//        try {
//            specificStudents = tryBody(sc0, students);
//            if (specificStudents.usersHashMap.containsValue(null)) {
//                throw new UserDoesNotExistException();
//            }
//            showStudentsHelper(specificStudents);
//            System.out.println("Do you want to search again in current students? yes/other keys for no:");
//            String input = inp.inputString();
//            if (input.equalsIgnoreCase("yes")) {
//                specificStudents = getSpecificStudents(specificStudents);
//            }
//        } catch (NullPointerException e) {
//            System.out.println("Null Pointer Occurred");
//        }
//        return specificStudents;
//    }
//
//    // EFFECT: helper method, sout operations.
//    private int gssOutput() {
//        System.out.println("——Please select a method of searching——");
//        System.out.println("——Enter 1 for students' username search(You may modify students' information)——");
//        System.out.println("——Enter 2 for students' name search——");
//        System.out.println("——Enter 3 for students' gender search——");
//        System.out.println("——Enter 4 for students' score search——");
//        System.out.println("——Enter 5 for students' age search——");
//        System.out.println("——Enter 6 for students' in specific course search——");
//        System.out.println("——Enter 7 for students' credit search——");
//        return inp.inputInt();
//    }
//
//    // EFFECT: helper method, get user input
//    private Users tryBody(int sc, Users students) {
//        if (sc == 1) {
//            return searchByUsername(students);
//        } else if (sc == 2) {
//            return searchByName(students);
//        } else if (sc == 3) {
//            return searchByGender(students);
//        } else if (sc == 5) {
//            return searchByAge(students);
//        } else if (sc == 6) {
//            return searchByCourse(students);
//        } else if (sc == 7) {
//            return searchBycredit(students);
//        } else {
//            System.out.println("Input error");
//            return tryBody(sc, students);
//        }
//    }
//
//    // EFFECT: get students fit right condition
//    private Users searchByUsername(Users students) {
//        System.out.println("Please enter the student's username：");
//        String userName = inp.inputString();
//        User stu = students.usersHashMap.get(userName);
//        Users tempStudents = new Users(STUDENT);
//        tempStudents.usersHashMap.put(userName, stu);
//        return tempStudents;
//    }
//
//    // EFFECT: get students fit right condition
//    private Users searchByName(Users students) {
//        System.out.println("Please enter the student's name：");
//        String name = inp.inputString();
//        Users students0 = new Users(STUDENT);
//        for (User student : students.usersHashMap.values()) {
//            if (student.getName().equalsIgnoreCase(name)) {
//                students0.usersHashMap.put(student.getName(), student);
//            }
//        }
//        return students0;
//    }
//
//    // EFFECT: get students fit right condition
//    private Users searchByGender(Users students) {
//        System.out.println("Please enter the gender to search：(M/F)");
//        String gender = inp.inputString();
//        Users students0 = new Users(STUDENT);
//        for (String userName : students.usersHashMap.keySet()) {
//            Student stu0 = (Student) students.usersHashMap.get(userName);
//            if (stu0.getGender().equals(gender)) {
//                students0.usersHashMap.put(userName, stu0);
//            }
//        }
//        return students0;
//    }
//
//    // EFFECT: get students fit right condition
//    private Users searchByAge(Users students) {
//        System.out.println("Please enter the lower bound of age：");
//        int lowBoundary = inp.inputInt();
//        System.out.println("Please enter the higher bound of age：");
//        int highBoundary = inp.inputInt();
//        Users students0 = new Users(STUDENT);
//        for (String userName : students.usersHashMap.keySet()) {
//            Student stu0 = (Student) students.usersHashMap.get(userName);
//            if (stu0.getAge() >= lowBoundary && stu0.getAge() <= highBoundary) {
//                students0.usersHashMap.put(userName, stu0);
//            }
//        }
//        return students0;
//    }
//
//    // EFFECT: get students fit right condition
//    private Users searchByCourse(Users students) {
//        System.out.println("Please enter the course you are looking for：");
//        String course = inp.inputString();
//        Users students0 = new Users(STUDENT);
//        for (String userName : students.usersHashMap.keySet()) {
//            Student stu0 = (Student) students.usersHashMap.get(userName);
//            if (stu0.getCourses().courseHashMap.containsKey(course)) {
//                students0.usersHashMap.put(userName, stu0);
//            }
//        }
//        return students0;
//    }
//
//    // EFFECT: get students fit right condition
//    private Users searchBycredit(Users students) {
//        System.out.println("Please enter the lower bound of credit：");
//        int lowBoundary = inp.inputInt();
//        System.out.println("Please enter the higher bound of credit：");
//        int highBoundary = inp.inputInt();
//        Users students0 = new Users(STUDENT);
//        for (String userName : students.usersHashMap.keySet()) {
//            Student stu0 = (Student) students.usersHashMap.get(userName);
//            if (stu0.getCredit() >= lowBoundary && stu0.getCredit() <= highBoundary) {
//                students0.usersHashMap.put(userName, stu0);
//            }
//        }
//        return students0;
//    }
//
//    //TODO;
//
//    // EFFECT: To do series of operation with teachers go to the right operation for user
//    private void manageTeachers(){
//        //stub;
//    }
//
////    // EFFECT: get teacher fit right condition
////    public Instructor searchByUsername(Teachers teachers) {
////        System.out.println("Please enter the teacher's username：");
////        String userName = inp.inputString();
////        Instructor teacher = teachers.teacherHashMap.get(userName);
////        System.out.println(teacher);
////        return teacher;
////    }
////
////
////    // EFFECT: get teachers fit right condition
////    public Teachers searchByName(Teachers teachers) {
////        System.out.println("Please enter the teacher's username：");
////        String name = inp.inputString();
////        Teachers teachers0 = new Teachers();
////        for (Instructor teacher : teachers.teacherHashMap.values()) {
////            if (teacher.getName().equalsIgnoreCase(name)) {
////                System.out.println(teacher);
////                teachers0.teacherHashMap.put(teacher.getUserName(), teacher);
////            }
////        }
////        return teachers0;
////    }
//
//    // EFFECT: To do series of operation with admins go to the right operation for user
//    private void manageAdmins() {
//        //stub;
//    }
//
//
//
//
//
//
//
//
//
//
//
//
//
//}
