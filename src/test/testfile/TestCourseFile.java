package testfile;

import file.CourseFile;
import model.Course;
import model.Courses;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import static org.junit.jupiter.api.Assertions.*;

public class TestCourseFile {
    CourseFile file;
    CourseFile ioExceptionFile;
    Courses courses;

    @BeforeEach
    public void runBefore() {
        Main.initializeTheSystem();
        file = new CourseFile("data/TestCourseFile");
        ioExceptionFile = new CourseFile("dobulidabulibu/aHahaThisIsAWrongPath/IOExceptionFile");
        file.initializeTheFile();
        courses = new Courses();
    }

    @AfterEach
    public void runAfter() {
        file.delete();
    }

    @Test
    public void testInitializeTheFile() {
        file.delete();
        assertFalse(file.exists());
        assertFalse(courses.courseHashMap.containsKey("CPSC110-101"));

        assertTrue(file.initializeTheFile());

        assertTrue(file.exists());
        assertTrue(file.readCourses().courseHashMap.containsKey("CPSC110-101"));
        assertFalse(file.initializeTheFile());

        assertFalse(ioExceptionFile.initializeTheFile());

    }

    @Test
    public void testReadCourses() {
        assertEquals(0, courses.courseHashMap.size());
        assertFalse(courses.courseHashMap.containsKey("CPSC110-101"));
        assertFalse(courses.courseHashMap.containsKey("CPSC121-101"));
        assertFalse(courses.courseHashMap.containsKey("CPSC210-101"));
        assertFalse(courses.courseHashMap.containsKey("CPSC213-101"));
        assertFalse(courses.courseHashMap.containsKey("CPSC221-101"));
        assertFalse(courses.courseHashMap.containsKey("CPSC310-101"));


        courses = file.readCourses();

        assertEquals(18, courses.courseHashMap.size());
        assertTrue(courses.courseHashMap.containsKey("CPSC110-101"));
        assertTrue(courses.courseHashMap.containsKey("CPSC121-101"));
        assertTrue(courses.courseHashMap.containsKey("CPSC210-101"));
        assertTrue(courses.courseHashMap.containsKey("CPSC213-101"));
        assertTrue(courses.courseHashMap.containsKey("CPSC221-101"));
        assertTrue(courses.courseHashMap.containsKey("CPSC310-101"));
        assertFalse(courses.courseHashMap.containsKey("testCourse100"));

        assertEquals(new Courses(), ioExceptionFile.readCourses());
    }

    @Test
    public void testWriteCourses() {
        courses = file.readCourses();
        assertEquals(18, courses.courseHashMap.size());
        assertTrue(courses.courseHashMap.containsKey("CPSC110-101"));
        assertTrue(courses.courseHashMap.containsKey("CPSC121-101"));
        assertTrue(courses.courseHashMap.containsKey("CPSC210-101"));
        assertTrue(courses.courseHashMap.containsKey("CPSC213-101"));
        assertTrue(courses.courseHashMap.containsKey("CPSC221-101"));
        assertTrue(courses.courseHashMap.containsKey("CPSC310-102"));
        assertFalse(courses.courseHashMap.containsKey("TestCourse0001"));
        assertFalse(courses.courseHashMap.containsKey("TestCourse0002"));

        Course course1 = new Course("TestCourse0001", 0, 0);
        Course course2 = new Course("TestCourse0002", 0, 0);
        courses.courseHashMap.put("TestCourse0001", course1);
        courses.courseHashMap.put("TestCourse0002", course2);
        file.writeCourses(courses);
        courses = file.readCourses();
        assertEquals(20, courses.courseHashMap.size());
        assertTrue(courses.courseHashMap.containsKey("CPSC110-101"));
        assertTrue(courses.courseHashMap.containsKey("CPSC210-101"));
        assertTrue(courses.courseHashMap.containsKey("CPSC310-101"));
        assertTrue(courses.courseHashMap.containsKey("TestCourse0001"));
        assertTrue(courses.courseHashMap.containsKey("TestCourse0002"));

        assertFalse(ioExceptionFile.writeCourses(courses));
    }
}

