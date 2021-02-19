package testmodel;

import file.UserFile;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import static model.UsersType.*;
import static org.junit.jupiter.api.Assertions.*;

public class TestUsers {
    Users students;
    Users teachers;
    Users admins;
    UserFile studentFile;
    UserFile teacherFile;
    UserFile adminFile;


    @BeforeEach
    public void runBefore() {
        Main.initializeTheSystem();
        studentFile = new UserFile(Main.STU_PATH);
        teacherFile = new UserFile(Main.INS_PATH);
        adminFile = new UserFile(Main.ADM_PATH);
        if (studentFile.exists()) {
            studentFile.delete();
        }
        if (teacherFile.exists()) {
            teacherFile.delete();
        }
        if (adminFile.exists()) {
            adminFile.delete();
        }
        teacherFile.initializeTheFile();
        teachers = teacherFile.readUsers();
        studentFile.initializeTheFile();
        students = studentFile.readUsers();
        adminFile.initializeTheFile();
        admins = adminFile.readUsers();
    }

    @AfterEach
    public void runAfter() {
        studentFile.delete();
        studentFile.initializeTheFile();
        teacherFile.delete();
        teacherFile.initializeTheFile();
        adminFile.delete();
        adminFile.initializeTheFile();
    }


    @Test
    public void testAddNewUser() {
        User stu1 = new Student("test1", "test1", "test1", "M", 20);
        User stu2 = new Student("test2", "test2", "test2", "M", 20);
        User stu3 = new Student("test3", "test3", "test3", "M", 20);
        User stu4 = new Student("test4", "test4", "test4", "M", 20);
        User stu5 = new Student("test5", "test5", "test5", "M", 20);
        User stu6 = new Student("test6", "test6", "test6", "M", 20);
        User t1 = new Instructor("test1", "test1", "test1", "M", 20);
        User a1 = new Admin("test1", "", "", "", 0);
        User a2 = new Admin("test2", "", "", "", 0);
        User a3 = new Admin("test3", "", "", "", 0);
        User a4 = new Admin("test4", "", "", "", 0);


        assertTrue(students.addNewUser(stu1));
        assertTrue(students.addNewUser(stu2));
        assertTrue(students.addNewUser(stu3));
        assertTrue(students.addNewUser(stu4));
        assertTrue(students.addNewUser(stu5));
        assertFalse(students.addNewUser(stu6));
        assertTrue(teachers.addNewUser(t1));
        assertEquals(10, students.usersHashMap.size());
        assertEquals(6, teachers.usersHashMap.size());

        assertTrue(admins.addNewUser(a1));
        assertTrue(admins.addNewUser(a2));
        assertTrue(admins.addNewUser(a3));
        assertFalse(admins.addNewUser(a4));
        assertEquals(5, admins.usersHashMap.size());
    }

    @Test
    public void testDeleteAllUsers() {
        assertNotEquals(0, studentFile.readUsers().usersHashMap.size());
        students.deleteAllUsers();
        assertEquals(0, studentFile.readUsers().usersHashMap.size());

        assertNotEquals(0, teacherFile.readUsers().usersHashMap.size());
        teachers.deleteAllUsers();
        assertEquals(0, teacherFile.readUsers().usersHashMap.size());

        assertTrue(adminFile.readUsers().usersHashMap.size() > 1);
        admins.deleteAllUsers();
        assertEquals(1, adminFile.readUsers().usersHashMap.size());
    }

    @Test
    public void testDeleteAllUsersExcCaught() {
        admins.usersHashMap.remove("defaultAdmin");
        assertEquals(1, admins.usersHashMap.size());
        admins.deleteAllUsers();
    }

    @Test
    public void testShowAllUsers() {
        assertTrue(students.showAllUsers());
        assertFalse(new Users(STUDENT).showAllUsers());
        assertTrue(teachers.showAllUsers());
        assertFalse(new Users(INSTRUCTOR).showAllUsers());
        assertTrue(admins.showAllUsers());
        assertFalse(new Users(ADMIN).showAllUsers());
    }

    @Test
    public void testDeleteUserExcNotCaught() {
        User stu1 = new Student("test1", "test1", "test1", "M", 20);
        User t1 = new Instructor("test1", "test1", "test1", "M", 40, new Courses());
        User a1 = new Admin("test1", "", "", "", 0);
        assertFalse(students.deleteUser(stu1));
        assertFalse(studentFile.readUsers().usersHashMap.containsValue(stu1));

        assertFalse(teachers.deleteUser(t1));
        assertFalse(teacherFile.readUsers().usersHashMap.containsValue(t1));

        assertFalse(admins.deleteUser(a1));
        assertFalse(adminFile.readUsers().usersHashMap.containsValue(a1));


        students.usersHashMap.put(stu1.getUserName(), stu1);
        studentFile.writeUsers(students);

        teachers.usersHashMap.put(t1.getUserName(), t1);
        teacherFile.writeUsers(teachers);

        admins.usersHashMap.put(a1.getUserName(), a1);
        adminFile.writeUsers(admins);

        assertTrue(studentFile.readUsers().usersHashMap.containsValue(stu1));
        assertTrue(students.deleteUser(stu1));
        assertFalse(studentFile.readUsers().usersHashMap.containsValue(stu1));

        assertTrue(teacherFile.readUsers().usersHashMap.containsValue(t1));
        assertTrue(teachers.deleteUser(t1));
        assertFalse(teacherFile.readUsers().usersHashMap.containsValue(t1));

        assertTrue(adminFile.readUsers().usersHashMap.containsValue(a1));
        assertTrue(admins.deleteUser(a1));
        assertFalse(adminFile.readUsers().usersHashMap.containsValue(a1));
    }

    @Test
    public void testDeleteUserExcCaught() {
        assertEquals(2, adminFile.readUsers().usersHashMap.size());
        admins.deleteUser(adminFile.readUsers().usersHashMap.get("superAdmin"));
    }

    @Test
    public void testGetType() {
        assertEquals(ADMIN, admins.getType());
    }
}
