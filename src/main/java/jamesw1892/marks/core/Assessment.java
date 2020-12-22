package jamesw1892.marks.core;

public class Assessment {
    private String name;
    private float weightOfModule; // between 0 and 1
    private String notes;
    private Float mark; // null for unknown or not yet completed, between 0 and 1

    public Assessment(String name, float weightOfModule, String notes, Float mark) {
        this.setName(name);
        this.setWeightOfModule(weightOfModule);
        this.setNotes(notes);
        this.setMark(mark);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return this.notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public float getWeightOfModule() {
        return this.weightOfModule;
    }

    public void setWeightOfModule(float weightOfModule) {
        if (weightOfModule < 0 || weightOfModule > 1) {
            throw new IllegalArgumentException("weightOfModule should be between 0 and 1 inclusive");
        }
        this.weightOfModule = weightOfModule;
    }

    public Float getMark() {
        return this.mark;
    }

    public String getMarkStr() {
        return Format.percentageNull(this.getMark());
    }

    public void setMark(Float mark) {
        if (mark != null && (mark < 0 || mark > 1)) {
            throw new IllegalArgumentException("mark should be between 0 and 1 inclusive or null");
        }
        this.mark = mark;
    }

    public String getGrade(GradeBoundary gradeBoundary) {
        return gradeBoundary.getGrade(this.mark);
    }
}
