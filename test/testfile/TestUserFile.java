package testfile;

import file.UserFile;
import model.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import static model.UsersType.*;
import static org.junit.jupiter.api.Assertions.*;


public class TestUserFile {
    UserFile studentFile;
    UserFile teacherFile;
    UserFile adminFile;
    UserFile ioExceptionFile;
    Users teachers;
    Users students;
    Users admins;
    Courses courses;

    @BeforeEach
    public void runBefore() {
        Main.initializeTheSystem();
        studentFile = new UserFile(Main.STU_PATH);
        teacherFile = new UserFile(Main.INS_PATH);
        adminFile = new UserFile(Main.ADM_PATH);
        ioExceptionFile = new UserFile("dablidobulibu/aHahaThisIsAWrongPath/IOExceptionFile/Students");

        studentFile.initializeTheFile();
        teacherFile.initializeTheFile();
        adminFile.initializeTheFile();

        students = new Users(STUDENT);
        teachers = new Users(INSTRUCTOR);
        admins = new Users(ADMIN);
        courses = new Courses();

    }

    @AfterEach
    public void runAfter() {
        studentFile.delete();
        teacherFile.delete();
        adminFile.delete();
    }

    @Test
    public void testInitializeTheFileForAdmin() {
        adminFile.delete();
        assertFalse(adminFile.exists());
        assertFalse(admins.usersHashMap.containsKey("superAdmin"));

        assertTrue(adminFile.initializeTheFile());

        assertTrue(adminFile.exists());
        assertTrue(adminFile.readUsers().usersHashMap.containsKey("superAdmin"));
        assertFalse(adminFile.initializeTheFile());

        assertFalse(ioExceptionFile.initializeTheFile());
    }

    @Test
    public void testInitializeTheFileForStudent() {
        studentFile.delete();
        assertFalse(studentFile.exists());
        assertFalse(students.usersHashMap.containsKey("defaultStudent1"));

        assertTrue(studentFile.initializeTheFile());

        assertTrue(studentFile.exists());
        assertTrue(studentFile.readUsers().usersHashMap.containsKey("defaultStudent1"));
        assertFalse(studentFile.initializeTheFile());

        assertFalse(ioExceptionFile.initializeTheFile());
    }

    @Test
    public void testInitializeTheFileForTeacher() {
        teacherFile.delete();
        assertFalse(teacherFile.exists());
        assertFalse(teachers.usersHashMap.containsKey("defaultInstructor1"));

        assertTrue(teacherFile.initializeTheFile());

        assertTrue(teacherFile.exists());
        assertTrue(teacherFile.readUsers().usersHashMap.containsKey("defaultInstructor1"));
        assertFalse(teacherFile.initializeTheFile());

        assertFalse(ioExceptionFile.initializeTheFile());
    }

    @Test
    public void testWriteUsersTeachers() {
        teachers = teacherFile.readUsers();
        assertEquals(5, teachers.usersHashMap.size());
        assertTrue(teachers.usersHashMap.containsKey("defaultInstructor1"));
        assertTrue(teachers.usersHashMap.containsKey("defaultInstructor2"));
        assertTrue(teachers.usersHashMap.containsKey("defaultInstructor3"));
        assertTrue(teachers.usersHashMap.containsKey("defaultInstructor4"));
        assertTrue(teachers.usersHashMap.containsKey("defaultInstructor5"));
        assertFalse(teachers.usersHashMap.containsKey("testUser0001"));
        assertFalse(teachers.usersHashMap.containsKey("testUser0002"));

        User teacher1 = new Instructor("testUser0001", "testUser0001", "testUser0001",
                "M", 23, courses);
        User teacher2 = new Instructor("testUser0002", "testUser0002", "testUser0002",
                "F", 22, courses);
        teachers.usersHashMap.put("testUser0001", teacher1);
        teachers.usersHashMap.put("testUser0002", teacher2);
        teacherFile.writeUsers(teachers);
        teachers = teacherFile.readUsers();
        assertEquals(7, teachers.usersHashMap.size());
        assertTrue(teachers.usersHashMap.containsKey("defaultInstructor1"));
        assertTrue(teachers.usersHashMap.containsKey("defaultInstructor3"));
        assertTrue(teachers.usersHashMap.containsKey("defaultInstructor5"));
        assertTrue(teachers.usersHashMap.containsKey("testUser0001"));
        assertTrue(teachers.usersHashMap.containsKey("testUser0002"));

        assertFalse(ioExceptionFile.writeUsers(teachers));
    }

    @Test
    public void testReadUsersTeachers() {
        assertEquals(0, teachers.usersHashMap.size());
        assertFalse(teachers.usersHashMap.containsKey("defaultInstructor1"));
        assertFalse(teachers.usersHashMap.containsKey("defaultInstructor2"));
        assertFalse(teachers.usersHashMap.containsKey("defaultInstructor3"));
        assertFalse(teachers.usersHashMap.containsKey("defaultInstructor4"));
        assertFalse(teachers.usersHashMap.containsKey("defaultInstructor5"));

        teachers = teacherFile.readUsers();

        assertEquals(5, teachers.usersHashMap.size());
        assertTrue(teachers.usersHashMap.containsKey("defaultInstructor1"));
        assertTrue(teachers.usersHashMap.containsKey("defaultInstructor2"));
        assertTrue(teachers.usersHashMap.containsKey("defaultInstructor3"));
        assertTrue(teachers.usersHashMap.containsKey("defaultInstructor4"));
        assertTrue(teachers.usersHashMap.containsKey("defaultInstructor5"));
        assertFalse(teachers.usersHashMap.containsKey("testUser0001"));
    }

    @Test
    public void testReadUsersStudents() {
        assertEquals(0, students.usersHashMap.size());
        assertFalse(students.usersHashMap.containsKey("defaultStudent1"));
        assertFalse(students.usersHashMap.containsKey("defaultStudent2"));
        assertFalse(students.usersHashMap.containsKey("defaultStudent3"));
        assertFalse(students.usersHashMap.containsKey("defaultStudent4"));
        assertFalse(students.usersHashMap.containsKey("defaultStudent5"));

        students = studentFile.readUsers();

        assertEquals(5, students.usersHashMap.size());
        assertTrue(students.usersHashMap.containsKey("defaultStudent1"));
        assertTrue(students.usersHashMap.containsKey("defaultStudent2"));
        assertTrue(students.usersHashMap.containsKey("defaultStudent3"));
        assertTrue(students.usersHashMap.containsKey("defaultStudent4"));
        assertTrue(students.usersHashMap.containsKey("defaultStudent5"));
        assertFalse(students.usersHashMap.containsKey("testUser100"));

        assertEquals(new Users(STUDENT), ioExceptionFile.readUsers());
    }

    @Test
    public void testWriteUsersStudents() {
        students = studentFile.readUsers();
        assertEquals(5, students.usersHashMap.size());
        assertTrue(students.usersHashMap.containsKey("defaultStudent1"));
        assertTrue(students.usersHashMap.containsKey("defaultStudent2"));
        assertTrue(students.usersHashMap.containsKey("defaultStudent3"));
        assertTrue(students.usersHashMap.containsKey("defaultStudent4"));
        assertTrue(students.usersHashMap.containsKey("defaultStudent5"));
        assertFalse(students.usersHashMap.containsKey("testUser0001"));
        assertFalse(students.usersHashMap.containsKey("testUser0002"));

        User stu1 = new Student("testUser0001", "testUser0001", "testUser0001",
                "M", 23);
        User stu2 = new Student("testUser0002", "testUser0002", "testUser0002",
                "F", 22);
        students.usersHashMap.put("testUser0001", stu1);
        students.usersHashMap.put("testUser0002", stu2);
        studentFile.writeUsers(students);
        students = studentFile.readUsers();
        assertEquals(7, students.usersHashMap.size());
        assertTrue(students.usersHashMap.containsKey("defaultStudent1"));
        assertTrue(students.usersHashMap.containsKey("defaultStudent2"));
        assertTrue(students.usersHashMap.containsKey("defaultStudent3"));
        assertTrue(students.usersHashMap.containsKey("testUser0001"));
        assertTrue(students.usersHashMap.containsKey("testUser0002"));

        assertFalse(ioExceptionFile.writeUsers(students));
    }


    @Test
    public void testReadAdmins() {
        assertEquals(0, admins.usersHashMap.size());
        assertFalse(admins.usersHashMap.containsKey("superAdmin"));
        assertFalse(admins.usersHashMap.containsKey("defaultAdmin"));

        admins = adminFile.readUsers();

        assertEquals(2, admins.usersHashMap.size());
        assertTrue(admins.usersHashMap.containsKey("superAdmin"));
        assertTrue(admins.usersHashMap.containsKey("defaultAdmin"));
        assertFalse(admins.usersHashMap.containsKey("Dr. Hutchinson"));
        assertFalse(admins.usersHashMap.containsKey("Lincoln"));
    }

    @Test
    public void testWriteAdmins() {
        admins = adminFile.readUsers();
        assertEquals(2, admins.usersHashMap.size());
        assertTrue(admins.usersHashMap.containsKey("superAdmin"));
        assertTrue(admins.usersHashMap.containsKey("defaultAdmin"));
        assertFalse(admins.usersHashMap.containsKey("Hutchinson"));
        assertFalse(admins.usersHashMap.containsKey("Lincoln"));


        User admin1 = new Admin("Hutchinson", "Hutchinson", "Hutchinson","M",27);
        User admin2 = new Admin("Lincoln", "Lincoln", "Lincoln","M",21);
        admins.usersHashMap.put("Hutchinson", admin1);
        admins.usersHashMap.put("Lincoln", admin2);
        adminFile.writeUsers(admins);
        admins = adminFile.readUsers();
        assertEquals(4, admins.usersHashMap.size());
        assertTrue(admins.usersHashMap.containsKey("superAdmin"));
        assertTrue(admins.usersHashMap.containsKey("defaultAdmin"));
        assertTrue(admins.usersHashMap.containsKey("Hutchinson"));
        assertTrue(admins.usersHashMap.containsKey("Lincoln"));

        // test catch clause
        assertFalse(ioExceptionFile.writeUsers(admins));
    }

}
