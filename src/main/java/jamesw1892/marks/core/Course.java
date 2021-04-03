package jamesw1892.marks.core;

public class Course {
    private String name;
    private String description;
    private String targetGrade;
    private Year[] years;

    public Course(String name, String description, String targetGrade, Year[] years) {
        this.setName(name);
        this.setDescription(description);
        this.setTargetGrade(targetGrade);
        this.setYears(years);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public String getTargetGrade() {
        return this.targetGrade;
    }

    public Year[] getYears() {
        return this.years;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setTargetGrade(String targetGrade) {
        this.targetGrade = targetGrade;
    }

    public void setYears(Year[] years) {
        this.years = years;
    }

    /**
     * Get the current mark of this course - the sum of the
     * weighted marks of all years in the course. Between 0 and 1
     */
    public float getMarkCurrent() {
        float mark = 0.0f;
        for (Year year: this.years) {
            mark += year.getMarkCurrent() * year.getWeightOfCoursePercent();
        }
        return mark;
    }

    public String getMarkCurrentStr() {
        return Format.percentageNotNull(this.getMarkCurrent());
    }

    /**
     * Average mark which is null if nothing has been completed yet,
     * otherwise the current mark divided by how much has been completed.
     * Between 0 and 1
     */
    public Float getMarkAverage() {
        float complete = this.getComplete();
        if (complete == 0.0f) {
            return null;
        }
        return this.getMarkCurrent() / complete;
    }

    public String getMarkAverageStr() {
        return Format.percentageNull(this.getMarkAverage());
    }

    /**
     * Weight of the course completed - sum of weighted completeness
     * of all years in the course. Between 0 and 1
     */
    public float getComplete() {
        float complete = 0.0f;
        for (Year year: this.years) {
            complete += year.getComplete() * year.getWeightOfCoursePercent();
        }
        return complete;
    }

    public String getCompleteStr() {
        return Format.percentageNotNull(this.getComplete());
    }

    public String getGradeAverage(GradeBoundary gradeBoundary) {
        return gradeBoundary.getGrade(this.getMarkAverage());
    }
}