package testmodel;

import file.CourseFile;
import model.Courses;
import model.Instructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Main;

import static model.UsersType.INSTRUCTOR;
import static org.junit.jupiter.api.Assertions.*;

public class TestInstructor {
    Instructor instructor;
    Courses courses;
    CourseFile courseFile;

    @BeforeEach
    public void runBefore() {
        Main.initializeTheSystem();
        courses = new Courses();
        instructor = new Instructor("T1","T1","T1","M",1, courses);
        courseFile = new CourseFile(Main.CRS_PATH);
    }

    @Test
    public void testGetCoursesTeaching() {
        assertEquals(courses, instructor.getCoursesTeaching());
    }

    @Test
    public void testSetCoursesTeaching() {
        assertEquals(courses, instructor.getCoursesTeaching());

        courses = courseFile.readCourses();
        instructor.setCoursesTeaching(courses);

        assertEquals(courses, instructor.getCoursesTeaching());
    }

    @Test
    public void testToString() {
        assertEquals("Instructor{"
                + "userName='" + instructor.getUserName() + '\''
                + ", passWord='" + instructor.getPassWord()+ '\''
                + ", name='" + instructor.getName() + '\''
                + ", gender='" + instructor.getGender() + '\''
                + ", age=" + instructor.getAge()
                + '}', instructor.toString());
    }

    @Test
    public void testEquals() {
        assertFalse(instructor.equals(1));
        Instructor instructor1 = new Instructor("0","T1","T1","M",1, courses);
        assertFalse(instructor.equals(instructor1));
        courses.courseHashMap.put("CPSC110", courseFile.readCourses().courseHashMap.get("CPSC110"));
        Instructor instructor2 = new Instructor("T1","T1","T1","M",1, new Courses());
        assertFalse(instructor.equals(instructor2));
        Instructor instructor3 = new Instructor("T1","T1","T1","M",1, courses);
        assertTrue(instructor.equals(instructor3));
    }

    @Test
    public void testGetMyType() {
        assertEquals(INSTRUCTOR, instructor.getMyType());
    }

    @Test
    public void testAddNewCourseTeachingAndDeleteIt() {
        instructor.addNewCourseTeaching("CPSC110-101");
        assertTrue(instructor.getCoursesTeaching().courseHashMap.containsKey("CPSC110-101"));
        instructor.deleteCourseTeaching("CPSC110-101");
        assertFalse(instructor.getCoursesTeaching().courseHashMap.containsKey("CPSC110-101"));
    }
}
