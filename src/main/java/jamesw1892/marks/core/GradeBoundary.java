package jamesw1892.marks.core;

import java.util.TreeSet;

public class GradeBoundary {
    private TreeSet<Grade> grades;

    public GradeBoundary(Grade[] grades) {
        this.grades = new TreeSet<>();
        for (Grade grade: grades) {
            this.grades.add(grade);
        }
    }

    public GradeBoundary(TreeSet<Grade> grades) {
        this.grades = grades;
    }

    public String getGrade(Float markPercent) {
        if (markPercent == null) {
            return "Unknown";
        }

        Grade grade = this.grades.floor(new Grade("", markPercent));
        if (grade == null) {
            return "Fail";
        }
        return grade.getName();
    }

    public TreeSet<Grade> getGrades() {
        return this.grades;
    }
}
