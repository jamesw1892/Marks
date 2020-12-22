package jamesw1892.marks.core;

import java.text.DecimalFormat;

public class Course {
    private String name;
    private String description;
    private Year[] years;

    public Course(String name, String description, Year[] years) {
        this.setName(name);
        this.setDescription(description);
        this.setYears(years);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
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
        return String.valueOf(this.getComplete() * 100) + "%";
    }

    public String getMarkAverageStr() {
        Float averageMark = this.getMarkAverage();
        if (averageMark == null) {
            return "Unknown";
        }
        DecimalFormat decimalFormat = new DecimalFormat("0%");
        return String.valueOf(decimalFormat.format(averageMark * 100));
    }

    public String getGradeAverage(GradeBoundary gradeBoundary) {
        return gradeBoundary.getGrade(this.getMarkAverage());
    }
}