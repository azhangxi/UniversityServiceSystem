package ui;

import file.CourseFile;
import file.UserFile;
import ui.window.MainWindow;

import java.io.File;

/**************************************************************************
 *
 * Copyright (C) University Service System - All Rights Reserved
 *
 * Unauthorized copying of this file, via any medium is strictly prohibited
 * Proprietary and confidential
 *
 * Written by Xi(Albert) Zhang [albert0223@icloud.com], 2019 [SEPT]
 *
 **************************************************************************/

public class Main {
//    public static final String STU_PATH = "data/Students";
//    public static final String CRS_PATH = "data/Courses";
//    public static final String INS_PATH = "data/Instructor";
//    public static final String ADM_PATH = "data/Admins";
    public static String path;
    public static String STU_PATH;
    public static String CRS_PATH;
    public static String INS_PATH;
    public static String ADM_PATH;
    public static MainWindow MAIN_WINDOW;
//    private static StudentUI studentUI;
//    private static TeacherUI teacherUI;
//    private static AdminUI adminUI;

    //EFFECT: ?Enjoy?
    public static void main(String[] args) {
        initializeTheSystem();
        MAIN_WINDOW = new MainWindow();
//        MAIN_WINDOW.initializeWindow();
//        System.out.println("Welcome to the University Service System!\n"
//                +          "~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n");
//        ui();
    }

//    public static void ui() {
//        while (true) {
//            System.out.println("Please enter:\n1:Student \t2:Instructor \t3:Admin Login \t0:Exit");
//            Input input = new Input();
//            String inp = input.inputString();
//            if (inp.equals("1")) {
//                studentUI.ui();
//            } else if (inp.equals("2")) {
//                teacherUI.ui();
//            } else if (inp.equals("3")) {
//                adminUI.login();
//            } else if (inp.equals("0")) {
//                System.out.println("Thank you for using Student Management system ~\n");
//                System.exit(0);
//            } else {
//                System.out.println("Input Error, please enter again...");
//            }
//        }
//    }

    //MODIFY: this
    //EFFECT: setup the files
    public static void initializeTheSystem() {
        path = new File("").getAbsolutePath() + "/";
        STU_PATH = path + "data/Students";
        CRS_PATH = path + "data/Courses";
        INS_PATH = path + "data/Instructors";
        ADM_PATH = path + "data/Admins";
        new CourseFile(CRS_PATH).initializeTheFile();
        new UserFile(INS_PATH).initializeTheFile();
        new UserFile(STU_PATH).initializeTheFile();
        new UserFile(ADM_PATH).initializeTheFile();
//        studentUI = new StudentUI();
//        teacherUI = new TeacherUI();
//        adminUI = new AdminUI();
    }



}
