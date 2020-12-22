package jamesw1892.marks.core;

public class Module {
    private String name;
    private int CATS; // used for relative weights
    private String[] lecturers;
    private String description;
    private Assessment[] assessments;

    public Module(String name, int CATS, String[] lecturers, String description, Assessment[] assessments) {
        this.setName(name);
        this.setCATS(CATS);
        this.setLecturers(lecturers);
        this.setDescription(description);
        this.setAssessments(assessments);
    }

    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getCATS() {
        return this.CATS;
    }
    public void setCATS(int CATS) {
        if (CATS < 0) {
            throw new IllegalArgumentException("CATS should not be negative");
        }
        this.CATS = CATS;
    }
    public String[] getLecturers() {
        return this.lecturers;
    }
    public void setLecturers(String[] lecturers) {
        this.lecturers = lecturers;
    }
    public String getDescription() {
        return this.description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Assessment[] getAssessments() {
        return this.assessments;
    }
    public void setAssessments(Assessment[] assessments) {
        this.assessments = assessments;
    }

    /**
     * Get the current mark - the sum of the weighted marks
     * of all assessments in the module. Between 0 and 1
     */
    public float getMarkCurrent() {
        float mark = 0.0f;
        for (Assessment assessment: this.assessments) {

            // if mark is null, it hasn't been completed yet so is
            // treated as 0 (so nothing is added)
            Float assessmentMark = assessment.getMark();
            if (assessmentMark != null) {
                mark += assessmentMark * assessment.getWeightOfModule();
            }
        }
        return mark;
    }

    public String getMarkCurrentStr() {
        return Format.percentageNotNull(this.getMarkCurrent());
    }

    /**
     * We say the average mark is null if nothing has been completed yet
     * otherwise it is the current mark divided by how much has been completed.
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
     * Weight of module completed - sum of weights of completed assessments
     * in the module. Between 0 and 1
     */
    public float getComplete() {
        float complete = 0.0f;
        for (Assessment assessment: this.assessments) {
            if (assessment.getMark() != null) {
                complete += assessment.getWeightOfModule();
            }
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