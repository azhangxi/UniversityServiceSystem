package file;

import model.Course;
import model.Courses;

import java.io.*;


public class CourseFile extends File {

    //MODIFY: this
    //EFFECT: Construct a course file with given pathname
    public CourseFile(String pathname) {
        super(pathname);
    }

    //MODIFY: this
    //EFFECT: put default users into the system
    public boolean initializeTheFile() {
        File file = new File(this.getPath());
        try {
            boolean b = file.createNewFile();
            if (b) {

                Courses courses = new Courses();
                initCoursesInFile(courses);
                writeCourses(courses);
                System.out.println("-----------------Courses Data file initialized!-----------------");
                return true;
            }
        } catch (IOException e) {
            System.out.println("something is wrong with the target reading");
        }
        return false;
    }

    private void initCoursesInFile(Courses courses) {
        courses.courseHashMap.put("CPSC110-101", new Course("CPSC110", 101, 4));
        courses.courseHashMap.putAll(courses.makeMultipleSections(courses.courseHashMap.get("CPSC110-101"), 2));
        courses.courseHashMap.put("CPSC121-101", new Course("CPSC121", 101, 3));
        courses.courseHashMap.putAll(courses.makeMultipleSections(courses.courseHashMap.get("CPSC121-101"), 2));
        courses.courseHashMap.put("CPSC210-101", new Course("CPSC210", 101, 4));
        courses.courseHashMap.putAll(courses.makeMultipleSections(courses.courseHashMap.get("CPSC210-101"), 2));
        courses.courseHashMap.put("CPSC213-101", new Course("CPSC213", 101, 3));
        courses.courseHashMap.putAll(courses.makeMultipleSections(courses.courseHashMap.get("CPSC213-101"), 2));
        courses.courseHashMap.put("CPSC221-101", new Course("CPSC221", 101, 3));
        courses.courseHashMap.putAll(courses.makeMultipleSections(courses.courseHashMap.get("CPSC221-101"), 2));
        courses.courseHashMap.put("CPSC310-101", new Course("CPSC310", 101, 4));
        courses.courseHashMap.putAll(courses.makeMultipleSections(courses.courseHashMap.get("CPSC310-101"), 2));
    }

    //MODIFY: this
    //EFFECT: save given Courses into this file
    public boolean writeCourses(Courses courses) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(new File(this.getPath())));
            oos.writeObject(courses);
            oos.close();
            return true;
        } catch (IOException e) {
            System.out.println("something is wrong with the target writing");
            return false;
        }
    }


    //EFFECT: load all courses from this file
    public Courses readCourses() {
        Courses courses = new Courses();
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(new File(this.getPath())));
            courses = (Courses) ois.readObject();
            ois.close();
        } catch (ClassNotFoundException | IOException e) {
            System.out.println("something is wrong with the target reading");
        }
        return courses;
    }
}
