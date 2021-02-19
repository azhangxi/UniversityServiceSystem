package model;

import file.CourseFile;
import observers.CourseMonitor;

import java.io.Serializable;
import java.util.Objects;
import java.util.Observable;

import static model.UsersType.STUDENT;
import static ui.Main.CRS_PATH;

public class Course extends Observable implements Serializable {
    private static final long serialVersionUID = 2L;
    private String courseName;
    private int section;
    private Instructor instructor;
    private int credit;
    private Users studentsEnrolled;
    private CourseFile courseFile = new CourseFile(CRS_PATH);

    //MODIFY: this
    //EFFECT: Construct a new Course with courseName, section and credit, make a new student map as enrolled students
    public Course(String courseName, int section, int credit) {
        this.courseName = courseName;
        this.section = section;
        this.credit = credit;
        studentsEnrolled = new Users(STUDENT);
    }

    //MODIFY: this, courseFile
    //EFFECT: if the student is not yet in this course, add the student in.
    //        if the student's courses map does not contains this course, add this to student's course map
    public void addNewStudent(Student student) {
        if (!studentsEnrolled.usersHashMap.containsKey(student.getUserName())) {
            studentsEnrolled.usersHashMap.put(student.getUserName(), student);
        }
        if (!student.getCourses().courseHashMap.containsKey(courseName)) {
            student.addOrDropCourse(courseName);
        }
        updateCourseToFile();
    }

    //MODIFY: this, courseFile
    //EFFECT: if student's course map still has this, remove from the map.
    //        then remove student from this course
    public void removeStudent(Student student) {
        if (student.getCourses().courseHashMap.containsKey(makeStringKey())) {
            student.addOrDropCourse(courseName);
        }
        studentsEnrolled.usersHashMap.remove(student.getUserName());
        updateCourseToFile();
    }

    //MODIFY: this, courseFile
    //EFFECT: if instructor's coursesTeaching map does not contain this, add to it.
    //        change instructor to given instructor, and notify observers
    public void changeInstructor(Instructor instructor) {
        addObserver(new CourseMonitor());
        this.instructor = instructor;
        if (!instructor.getCoursesTeaching().courseHashMap.containsKey(makeStringKey())) {
            instructor.addNewCourseTeaching(makeStringKey());
        }
        setChanged();
        notifyObservers(this);
        updateCourseToFile();
    }

    //MODIFY: this, courseFile
    //EFFECT: set instructor to null, and if the instructor's coursesTeaching map still has this, remove from it
    public void removeInstructor() {
        if (instructor != null) {
            Instructor tempInstructor = instructor;
            instructor = null;
            if (tempInstructor.getCoursesTeaching().courseHashMap.containsKey(makeStringKey())) {
                tempInstructor.deleteCourseTeaching(makeStringKey());
            }
            updateCourseToFile();
        }
    }

    private void updateCourseToFile() {
        Courses courses = courseFile.readCourses();
        courses.courseHashMap.replace(makeStringKey(), this);
        courseFile.writeCourses(courses);
        if (instructor != null) {
            instructor.getCoursesTeaching().courseHashMap.replace(this.makeStringKey(), this);
            instructor.updateUserToFile();
        }
    }


    // Auto Generated getters, setters, equals, hashCode, and toString
    public String getCourseName() {
        return courseName;
    }

    public int getCredit() {
        return credit;
    }

    public void setCredit(int credit) {
        this.credit = credit;
    }

    public int getSection() {
        return section;
    }

    public Users getStudentsEnrolled() {
        return studentsEnrolled;
    }

    public Instructor getInstructor() {
        return instructor;
    }

    public void setInstructor(Instructor instructor) {
        this.instructor = instructor;
    }

    public String makeStringKey() {
        return courseName + "-" + section;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Course)) {
            return false;
        }
        Course course = (Course) o;
        return getCredit() == course.getCredit()
                && getSection() == course.getSection()
                && Objects.equals(getCourseName(), course.getCourseName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCourseName(), getCredit(), getSection());
    }

    @Override
    public String toString() {
        return "Course{"
                + "courseName='" + courseName + '\''
                + ", section=" + section
                + ", instructor=" + instructor
                + ", credit=" + credit
                + '}';
    }


}
