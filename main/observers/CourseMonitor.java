package observers;

import model.Course;
import model.Student;
import model.User;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Observable;
import java.util.Observer;

public class CourseMonitor implements Observer {

    //MODIFY: student, studentFile
    //EFFECT: put a new note with the date to student for instructor changes
    @Override
    public void update(Observable o, Object arg) {
        Course course = (Course) o;
        for (User user : course.getStudentsEnrolled().usersHashMap.values()) {
            Student student = (Student) user;
            student.getCourses().courseHashMap.replace(course.getCourseName(), course);
            LinkedHashMap<String, String> notes = student.getNotes();
            notes.put("Instructor Replacement\t" + getDate(), "Dear students in " + course.makeStringKey()
                    + ",\nYour instructor for "
                    + course.getCourseName() + " has been changed to "
                    + course.getInstructor().getName());
            student.setHasNewNote(true);
            student.updateUserToFile();
        }
    }

    private String getDate() {
        LocalDate date = LocalDate.now();
        return date.toString();
    }


}
