package com.exercise.scregistration.db.model;

public class StudentCourse {
	
	private int studentId;
	private int courseId;
	
	private String studentName;
	
	private String courseName;
	
	public int getStudentId() {
		return studentId;
	}
	public void setStudentId(int studentId) {
		this.studentId = studentId;
	}
	public int getCourseId() {
		return courseId;
	}
	public void setCourseId(int courseId) {
		this.courseId = courseId;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getCourseName() {
		return courseName;
	}
	public void setCourseName(String courseName) {
		this.courseName = courseName;
	}
	@Override
	public String toString() {
		return "StudentCourse [studentId=" + studentId + ", courseId=" + courseId + "]";
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + courseId;
		result = prime * result + studentId;
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
		if (!(obj instanceof StudentCourse)) {
			return false;
		}
		StudentCourse other = (StudentCourse) obj;
		if (courseId != other.courseId) {
			return false;
		}
		if (studentId != other.studentId) {
			return false;
		}
		return true;
	}
	


}
