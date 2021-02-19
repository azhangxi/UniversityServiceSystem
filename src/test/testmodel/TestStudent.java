package testmodel;

import file.CourseFile;
import model.Courses;
import model.Student;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import static model.UsersType.STUDENT;
import static org.junit.jupiter.api.Assertions.*;

public class TestStudent {
    public CourseFile courseFile;
    public Student student;
    public Courses courses;

    @BeforeEach
    public void runBefore() {
        Main.initializeTheSystem();
        courseFile = new CourseFile(Main.CRS_PATH);
        courses = courseFile.readCourses();
        student = new Student("test", "test", "test", "M", 19);
    }

    @Test
    public void testGetCredit() {
        student = new Student("test", "test", "test", "M", 19);
        assertEquals(0, student.getCredit());
    }

    @Test
    public void testEquals() {
        assertTrue(student.equals(student));
        assertFalse(student.equals(courses));

        Student student1 = new Student("test", "test", "test", "M", 19);
        Student student2 = new Student("1", "test", "test", "M", 19);

        assertTrue(student.equals(student1));
        assertFalse(student.equals(student2));

    }

    @Test
    public void testAddOrDropCourse() {
        assertFalse(student.addOrDropCourse("CourseThatDoesNotExist"));
        assertTrue(student.addOrDropCourse("CPSC110-101"));
        assertTrue(student.getCourses().courseHashMap.containsKey("CPSC110-101"));
        assertTrue(student.addOrDropCourse("CPSC110-101"));
    }

    @Test
    public void testNewNote() {
        assertFalse(student.hasNewNote());
        student.setHasNewNote(true);
        assertTrue(student.hasNewNote());
    }

    @Test
    public void testGetCourses() {
        assertEquals(0, student.getCourses().courseHashMap.size());
    }

    @Test
    public void testHashcode() {
        Student student1 = student;
        assertEquals(student.hashCode(), student1.hashCode());
    }


    @Test
    public void testGetMyType() {
        assertEquals(STUDENT, student.getMyType());
    }

}
