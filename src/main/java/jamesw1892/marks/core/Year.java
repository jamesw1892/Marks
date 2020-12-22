package jamesw1892.marks.core;

public class Year {
    private int number;
    private String personalTutor;
    private float weightOfCourse; // between 0 and 1 inclusive
	private Module[] modules;

	public Year(int number, String personalTutor, float weightOfCourse, Module[] modules) {
		this.setNumber(number);
		this.setPersonalTutor(personalTutor);
		this.setWeightOfCourse(weightOfCourse);
		this.setModules(modules);
	}

	public int getNumber() {
		return this.number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
	public String getPersonalTutor() {
		return this.personalTutor;
	}
	public void setPersonalTutor(String personalTutor) {
		this.personalTutor = personalTutor;
	}
	public float getWeightOfCoursePercent() {
		return this.weightOfCourse;
	}
	public void setWeightOfCourse(float weightOfCourse) {
		if (weightOfCourse < 0 || weightOfCourse > 1) {
			throw new IllegalArgumentException("weightOfCourse must be between 0 and 1 inclusive");
		}
		this.weightOfCourse = weightOfCourse;
	}
	public Module[] getModules() {
		return this.modules;
	}
	public void setModules(Module[] modules) {
		this.modules = modules;
	}

	/**
	 * Get the current mark of this year - the sum of the
	 * weighted marks of all modules in the year. Between 0 and 1
	 */
	public float getMarkCurrent() {
		float mark = 0.0f;
		for (Module module: this.modules) {
			mark += module.getMarkCurrent() * this.getModuleWeight(module);
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
	 * The weight of a module is its proportion of the year's CATS.
	 * Between 0 and 1
	 */
	public float getModuleWeight(Module module) {
		return module.getCATS() / this.getCATS();
	}

	/**
	 * Weight of the year completed - sum of weighted completeness
	 * of all modules in the year. Between 0 and 1
	 */
	public float getComplete() {
		float complete = 0.0f;
		for (Module module: this.modules) {
			complete += module.getComplete() * this.getModuleWeight(module);
		}
		return complete;
	}

	/**
	 * Get the total CATS in this year which is the
	 * sum of the CATS of all modules in this year
	 */
	public int getCATS() {
		int total = 0;
		for (Module module: this.modules) {
			total += module.getCATS();
		}
		return total;
	}

	public String getGradeAverage(GradeBoundary gradeBoundary) {
		return gradeBoundary.getGrade(this.getMarkAverage());
	}
}
