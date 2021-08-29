package com.exercise.scregistration.db.model;


public class Course {
	
	private int courseId;
	private String courseName;
	private String courseDetail;
	
	public Course(){
		
	}

	public Course(int courseId, String courseName, String courseDetail) {
		super();
		this.courseId = courseId;
		this.courseName = courseName;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}

	public String getCourseName() {
		return courseName;
	}

	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}

	public String getCourseDetail() {
		return courseDetail;
	}

	public void setCourseDetail(String courseDetail) {
		this.courseDetail = courseDetail;
	}

	@Override
	public String toString() {
		return "Course [courseId=" + courseId + ", courseName=" + courseName + ", courseDetail=" + courseDetail + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courseId;
		result = prime * result + ((courseName == null) ? 0 : courseName.hashCode());
		result = prime * result + ((courseDetail == null) ? 0 : courseDetail.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Course)) {
			return false;
		}
		Course other = (Course) obj;

		return courseId != other.courseId && isEqualName(other) && isEqualDetail(other);
	}

	private boolean isEqualName(Course other) {
		if (courseName == null) {
			if (other.courseName != null) {
				return false;
			}
		} else if (!courseName.equals(other.courseName)) {
			return false;
		}
		return true;
	}

	private boolean isEqualDetail(Course other) {
		if (courseDetail == null) {
			if (other.courseDetail != null) {
				return false;
			}
		} else if (!courseDetail.equals(other.courseDetail)) {
			return false;
		}
		return true;
	}
	

}
