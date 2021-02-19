package model;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;

import static model.UsersType.INSTRUCTOR;

public class Instructor extends User implements Serializable {
    private static final long serialVersionUID = 4L;
    private Courses coursesTeaching;

    //EFFECT: Construct a new Instructor with no courses teaching
    public Instructor(String userName, String passWord, String name, String gender, int age) {
        super(userName, passWord, name, gender, age);
        coursesTeaching = new Courses();
    }

    //EFFECT: Construct a new Instructor with courses teaching
    public Instructor(String userName, String passWord, String name, String gender, int age, Courses coursesTeaching) {
        super(userName, passWord, name, gender, age);
        this.coursesTeaching = coursesTeaching;
    }

    // MODIFY: this
    // EFFECT: add a new course for the instructor if not already exist,
    public void addNewCourseTeaching(String courseName) {
        if (!coursesTeaching.courseHashMap.containsKey(courseName)) {
            Course course = courseFile.readCourses().courseHashMap.get(courseName);

            coursesTeaching.courseHashMap.put(courseName, course);
            course.changeInstructor(this);
            updateUserToFile();
        }
    }

    // MODIFY: this
    // EFFECT: remove a course for the instructor if exist,
    public void deleteCourseTeaching(String courseKey) {
        if (coursesTeaching.courseHashMap.containsKey(courseKey)) {
            Course course = coursesTeaching.courseHashMap.get(courseKey);
            coursesTeaching.courseHashMap.remove(courseKey);
            if (course.getInstructor() != null) {
                course.removeInstructor();
            }
        }
        updateUserToFile();
    }

    //MODIFY: this, instructorFile
    //EFFECT: update changed information to file
    @Override
    public void updateUserToFile() {
        Users instructors = instructorFile.readUsers();
        instructors.usersHashMap.replace(userName, this);
        instructorFile.writeUsers(instructors);
    }

    //MODIFY: student, studentFile
    //EFFECT: give students in courseToNote a new note with title and note.
    public void postNote(String title, String note, Course courseToNote) {
        for (User user : courseToNote.getStudentsEnrolled().usersHashMap.values()) {
            Student student = (Student)user;
            student.getNotes().put(title + "\t" + getDate(), note);
            student.setHasNewNote(true);
            student.updateUserToFile();
        }
    }

    private String getDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }

    //Auto Generated getter, setter, toString, equals, and hashCode
    public Courses getCoursesTeaching() {
        return coursesTeaching;
    }

    public void setCoursesTeaching(Courses coursesTeaching) {
        this.coursesTeaching = coursesTeaching;
    }

    @Override
    public String toString() {
        return "Instructor{"
                + "userName='" + userName + '\''
                + ", passWord='" + passWord + '\''
                + ", name='" + name + '\''
                + ", gender='" + gender + '\''
                + ", age=" + age
                + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Instructor)) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        Instructor instructor = (Instructor) o;
        return Objects.equals(getCoursesTeaching(), instructor.getCoursesTeaching());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getCoursesTeaching());
    }


    //EFFECT: return INSTRUCTOR
    @Override
    public UsersType getMyType() {
        return INSTRUCTOR;
    }



}
