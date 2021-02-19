package model;

import exceptions.EmptyException;

import java.io.Serializable;
import java.util.LinkedHashMap;

import static model.UsersType.STUDENT;

public class Student extends User implements Serializable {
    protected static final long serialVersionUID = 5L;
    private Courses courses;
    private LinkedHashMap<String, String> notes;
    private boolean hasNewNote;

    public Student(String userName, String passWord, String name, String gender,
                   int age) {
        super(userName, passWord, name, gender, age);
        notes = new LinkedHashMap<>();
        courses = new Courses();
        hasNewNote = false;
    }

    public Student(String userName, String passWord, String name, String gender,
                    int age, Courses courses, LinkedHashMap<String, String> notes, boolean hasNewNote) {
        super(userName, passWord, name, gender, age);
        this.courses = courses;
        this.notes = notes;
        this.hasNewNote = hasNewNote;
    }

    // MODIFY: this, studentFile
    // EFFECT: Add new course or remove a course from a student
    //         return true if added or dropped;
    public boolean addOrDropCourse(String courseKey) {
        if (courseFile.readCourses().courseHashMap.containsKey(courseKey)) {
            Course course = courseFile.readCourses().courseHashMap.get(courseKey);
            if (courses.courseHashMap.containsKey(courseKey)) {
                dropCourse(courseKey, course);
            } else {
                addCourse(courseKey, course);
            }
            updateUserToFile();
            return true;
        }
        return false;
    }

    void dropCourse(String courseKey, Course course) {
        if (course.getStudentsEnrolled().usersHashMap.containsKey(userName)) {
            course.removeStudent(this);
        }
        courses.courseHashMap.remove(courseKey);
    }

    private void addCourse(String courseKey, Course course) {
        courses.courseHashMap.put(courseKey, courseFile.readCourses().courseHashMap.get(courseKey));
        if (!course.getStudentsEnrolled().usersHashMap.containsKey(userName)) {
            course.addNewStudent(this);
        }
    }

    //MODIFY: studentFile, this
    //EFFECT: save changed information to studentFile
    @Override
    public void updateUserToFile() {
        Users students = studentFile.readUsers();
        students.usersHashMap.replace(userName, this);
        studentFile.writeUsers(students);
    }

    /*
     * Generated Getters and Setters
     */

    public Courses getCourses() {
        return courses;
    }

    public LinkedHashMap<String, String> getNotes() {
        return notes;
    }

    public boolean hasNewNote() {
        return hasNewNote;
    }

    public void setHasNewNote(boolean hasNewNote) {
        this.hasNewNote = hasNewNote;
    }

    // EFFECT: compute the credit of the student and return it,
    //         if the student takes no courses, throw EmptyException and return 0.
    public int getCredit() {
        int tempCredit = 0;
        try {
            if (courses.courseHashMap.isEmpty()) {
                throw new EmptyException();
            }
            for (Course c : courses.courseHashMap.values()) {
                tempCredit += c.getCredit();
            }
            return tempCredit;
        } catch (EmptyException e) {
            return 0;
        }
    }

    /*
     * Generated toString()
     */
    @Override
    public String toString() {
        return "\nUsername:\t" + userName
                + "\nPassword:\t" + passWord
                + "\nName:\t\t" + name
                + "\nGender:\t\t" + gender
                + "\nAge:\t\t" + age
                + "\nCourses:\t" + courses
                + "\nCredit:\t\t" + getCredit();
    }

    @Override
    public UsersType getMyType() {
        return STUDENT;
    }




}
