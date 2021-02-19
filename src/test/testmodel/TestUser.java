package testmodel;

import model.Instructor;
import model.Student;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import static org.junit.jupiter.api.Assertions.*;

public class TestUser {
    public User student;
    public User instructor;

    @BeforeEach
    public void runBefore() {
        Main.initializeTheSystem();
        student = new Student("T1","T1","T1","T1",0);
        instructor = new Instructor("T1", "T1","T1","T1",0);
    }

    @Test
    public void testSetUserName() {
        student.setUserName("T0");
        assertEquals("T0", student.getUserName());
        instructor.setUserName("T0");
        assertEquals("T0", instructor.getUserName());

    }

    @Test
    public void testSetPassword() {
        student.setPassWord("T0");
        assertEquals("T0", student.getPassWord());
        instructor.setPassWord("T0");
        assertEquals("T0", instructor.getPassWord());

    }

    @Test
    public void testSetName() {
        student.setName("T0");
        assertEquals("T0", student.getName());
        instructor.setName("T0");
        assertEquals("T0", instructor.getName());
    }

    @Test
    public void testSetGender() {
        student.setGender("T0");
        assertEquals("T0", student.getGender());
        instructor.setGender("T0");
        assertEquals("T0", instructor.getGender());
    }

    @Test
    public void testSetAge() {
        student.setAge(1);
        assertEquals(1, student.getAge());
        instructor.setAge(1);
        assertEquals(1, instructor.getAge());
    }

    @Test
    public void testEquals() {
        assertTrue(instructor.equals(instructor));
        assertFalse(instructor.equals(student));
        assertFalse(instructor.equals(null));


        User user0 = new Student("T1","T1","T1","T1",0);
        User user1 = new Student("","T1","T1","T1",0);
        User user2 = new Student("T1","","T1","T1",0);
        User user3 = new Student("T1","T1","","T1",0);
        User user4 = new Student("T1","T1","T1","",0);
        User user5 = new Student("T1","T1","T1","T1",1);

        assertTrue(student.equals(user0));
        assertFalse(student.equals(user1));
        assertFalse(student.equals(user2));
        assertFalse(student.equals(user3));
        assertFalse(student.equals(user4));
        assertFalse(student.equals(user5));
    }

    @Test
    public void testHashcode() {
        User newTeacher = instructor;
        User newStudent = student;
        assertEquals(instructor.hashCode(), newTeacher.hashCode());
        assertEquals(student.hashCode(), newStudent.hashCode());
    }

}
