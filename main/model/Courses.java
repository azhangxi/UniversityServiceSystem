package model;

import exceptions.SystemFullException;
import file.CourseFile;
import ui.Main;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Objects;

public class Courses implements Serializable {
    private static final long serialVersionUID = 3L;
    private static final int MAXIMUM_COURSES_IN_SYSTEM = 22;
    public LinkedHashMap<String, Course> courseHashMap;
    private CourseFile courseFile = new CourseFile(Main.CRS_PATH);

    // EFFECT: initializes each newly created Courses as a empty HashMap
    public Courses() {
        courseHashMap = new LinkedHashMap<>();
    }

    // EFFECT: duplicate the course with section number increment amount times
    public HashMap<String, Course> makeMultipleSections(Course course, int amount) {
        HashMap<String, Course> courses = new HashMap<>();
        int section = course.getSection();
        for (int i = 1; i <= amount; i++) {
            Course newCourse = new Course(course.getCourseName(), section + i, course.getCredit());
            courses.put(newCourse.makeStringKey(), newCourse);
        }
        return courses;
    }

    // MODIFY: this, courseFile
    // EFFECT: Add a new course at the end of courseHashMap,
    //         return true if added, false if courseHashMap is full.
    public boolean addNewCourse(Course course) {
        try {
            if (courseHashMap.size() >= MAXIMUM_COURSES_IN_SYSTEM) {
                throw new SystemFullException();
            }
            courseHashMap.put(course.getCourseName(), course);
            courseFile.writeCourses(this);
            return true;
        } catch (SystemFullException e) {
            return false;
        }
    }

    // MODIFY: this, courseFile
    // EFFECT: delete a course form system,
    //         return true if delete successfully, false if no such course in system.
    public boolean deleteCourse(Course course) {
        try {
            Course courseInFile = courseHashMap.get(course.getCourseName());
            courseHashMap.remove(courseInFile.getCourseName());
            courseFile.writeCourses(this);
            return true;
        } catch (NullPointerException e) {
            System.out.println("The course is not in system");
            return false;
        }
    }

    //Auto Generated equals, hashCode, and toString.
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Courses courses = (Courses) o;
        return Objects.equals(courseHashMap, courses.courseHashMap);
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseHashMap);
    }

    @Override
    public String toString() {
        return "Courses{"  + courseHashMap + '}';
    }
}
