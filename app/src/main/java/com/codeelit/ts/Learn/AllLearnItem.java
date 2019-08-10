package com.codeelit.ts.Learn;

import java.util.ArrayList;

public class AllLearnItem {
    private String CDescription;
    private String CimageUri;
    private String CourseTitle;
    private String Dificulty;
    private String pre;

    public AllLearnItem() {
    }

    public AllLearnItem(String courseTitle, String CDescription2, String dificulty, String pre2, String cimageUri) {
        this.CourseTitle = courseTitle;
        this.CDescription = CDescription2;
        this.Dificulty = dificulty;
        this.pre = pre2;
        this.CimageUri = cimageUri;
    }

    public String getCourseTitle() {
        return this.CourseTitle;
    }

    public void setCourseTitle(String courseTitle) {
        this.CourseTitle = courseTitle;
    }

    public String getCDescription() {
        return this.CDescription;
    }

    public void setCDescription(String CDescription2) {
        this.CDescription = CDescription2;
    }

    public String getDificulty() {
        return this.Dificulty;
    }

    public void setDificulty(String dificulty) {
        this.Dificulty = dificulty;
    }

    public String getPre() {
        return this.pre;
    }

    public void setPre(String pre2) {
        this.pre = pre2;
    }

    public String getCimageUri() {
        return this.CimageUri;
    }

    public void setCimageUri(String cimageUri) {
        this.CimageUri = cimageUri;
    }

    public static final ArrayList<AllLearnItem> getTestingList() {
        ArrayList<AllLearnItem> Courses = new ArrayList<>();
        Courses.add(0, new AllLearnItem(
                "Basic Aptitude",
                "This Test is for Basic Aptitude",
                "Beginner",
                "20Min",
                "https://firebasestorage.googleapis.com/v0/b/my-awesome-app-165f8.appspot.com/o/BeginnersLIstImages%2Fjava_img.jpg?alt=media&token=1373fbf5-83ef-4c43-902a-36f783f1c18b"));

        Courses.add(1, new AllLearnItem(
                "Advance Aptitude",
                "This Test is for Advance Aptitude",
                "Beginner",
                "20Min",
                "https://firebasestorage.googleapis.com/v0/b/my-awesome-app-165f8.appspot.com/o/kotl.png?alt=media&token=d1e8f461-84c3-4ff9-8c58-eb94d33f3fc6"));

        Courses.add(2, new AllLearnItem(
                "Technical Aptitude",
                "This Test is for Technical Aptitude",
                "Intermediate",
                "20Min",
                "https://firebasestorage.googleapis.com/v0/b/my-awesome-app-165f8.appspot.com/o/BeginnersLIstImages%2Fandroidmaterialdesignsii.png?alt=media&token=a056f169-110a-4cb9-9ee5-33ae6f721b7b"));

        Courses.add(3, new AllLearnItem(
                "Beginners Programming",
                "This Test is for Beginners Programming",
                "Intermediate",
                "20Min",
                "https://firebasestorage.googleapis.com/v0/b/my-awesome-app-165f8.appspot.com/o/BeginnersLIstImages%2F49._updato_screens.png?alt=media&token=38d76383-3b5b-4e29-81b6-391cec81225d"));

        Courses.add(4, new AllLearnItem(
                "Advance Programming",
                "This Test is for Advance Programming",
                "Advance",
                "20Min",
                "https://firebasestorage.googleapis.com/v0/b/my-awesome-app-165f8.appspot.com/o/BeginnersLIstImages%2Fandroid-1635207_1280.png?alt=media&token=6074e07f-989c-421d-87d6-196086a5afda"));
        return Courses;
    }
}
