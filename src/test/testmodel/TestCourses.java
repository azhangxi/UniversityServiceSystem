package testmodel;

import file.CourseFile;
import model.Course;
import model.Courses;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import static org.junit.jupiter.api.Assertions.*;

public class TestCourses {
    public Courses courses;
    public CourseFile courseFile;

    @BeforeEach
    public void runBefore() {
        Main.initializeTheSystem();
        courseFile = new CourseFile(Main.CRS_PATH);
        if (courseFile.exists()) {
            courseFile.delete();
            courseFile.initializeTheFile();
        }
        courses = courseFile.readCourses();
    }

    @AfterEach
    public void runAfter() {
        courseFile.delete();
        courseFile.initializeTheFile();
    }


    @Test
    public void testEquals() {
        assertTrue(courses.equals(courses));
        assertFalse(courses.equals(new Main()));
        assertFalse(courses.equals(null));

        Courses courses1 = courseFile.readCourses();
        assertTrue(courses.equals(courses1));
    }

    @Test
    public void testAddNewCourse() {
        assertEquals(18, courses.courseHashMap.size());

        Course c1 = new Course("T1", 0,0);
        Course c2 = new Course("T2", 0,0);
        Course c3 = new Course("T3", 0,0);
        Course c4 = new Course("T4", 0,0);
        Course c5 = new Course("T5", 0,0);

        assertTrue(courses.addNewCourse(c1));
        assertTrue(courses.addNewCourse(c2));
        assertTrue(courses.addNewCourse(c3));
        assertTrue(courses.addNewCourse(c4));
        assertFalse(courses.addNewCourse(c5));
        assertEquals(22, courses.courseHashMap.size());
        assertTrue(courseFile.readCourses().courseHashMap.containsKey("T1"));
    }

    @Test
    public void testDeleteCourse() {
        assertFalse(courseFile.readCourses().courseHashMap.containsKey("CourseThatDoesNotExist"));
        assertFalse(courses.deleteCourse(new Course("CourseThatDoesNotExist",0,0)));

        Course course = new Course("CourseThatExistNow", 0,0);
        courses.courseHashMap.put(course.getCourseName(), course);
        int size = courses.courseHashMap.size();

        assertTrue(courses.deleteCourse(course));
        assertEquals(size - 1, courses.courseHashMap.size());

        assertFalse(courseFile.readCourses().courseHashMap.containsKey("CourseThatExistNow"));
    }

    @Test
    public void testHashcode() {
        Courses courses1 = courses;
        assertEquals(courses.hashCode(), courses1.hashCode());
    }

    @Test
    public void testCourseToString() {
        Course course = courses.courseHashMap.get("CPSC110-101");
        assertEquals("Course{"
                + "courseName='" + course.getCourseName() + '\''
                + ", section=" + course.getSection()
                + ", instructor=" + course.getInstructor()
                + ", credit=" + course.getCredit()
                + '}',
                course.toString());

    }
}
