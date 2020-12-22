package jamesw1892.marks.core;

public class Grade implements Comparable<Grade> {
    private String name;
    private float minimumPercentRequired; // between 0 and 1 inclusive

	public Grade(String name, float minimumPercentRequired) {
		this.setName(name);
		this.setMinimumPercentRequired(minimumPercentRequired);
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public float getMinimumPercentRequired() {
		return this.minimumPercentRequired;
	}

	public void setMinimumPercentRequired(float minimumPercentRequired) {
		if (minimumPercentRequired < 0 || minimumPercentRequired > 1) {
			throw new IllegalArgumentException("minimumPercentRequired should be between 0 and 1 inclusive");
		}
		this.minimumPercentRequired = minimumPercentRequired;
	}

	public int compareTo(Grade other) {
		return Float.compare(this.minimumPercentRequired, other.minimumPercentRequired);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + Float.floatToIntBits(minimumPercentRequired);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Grade other = (Grade) obj;
		if (Float.floatToIntBits(minimumPercentRequired) != Float.floatToIntBits(other.minimumPercentRequired))
			return false;
		return true;
	}
}
