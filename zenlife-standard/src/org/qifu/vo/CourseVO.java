package org.qifu.vo;

import org.qifu.base.model.YesNo;
import org.qifu.po.ZlCourse;
import org.qifu.po.ZlCourseRead;

public class CourseVO implements java.io.Serializable {
	private static final long serialVersionUID = -2046373690311376834L;
	
	private ZlCourse course;
	private ZlCourseRead courseRead;
	private String read = YesNo.NO;
	
	public ZlCourse getCourse() {
		return course;
	}
	
	public void setCourse(ZlCourse course) {
		this.course = course;
	}
	
	public ZlCourseRead getCourseRead() {
		return courseRead;
	}
	
	public void setCourseRead(ZlCourseRead courseRead) {
		this.courseRead = courseRead;
	}

	public String getRead() {
		return read;
	}

	public void setRead(String read) {
		this.read = read;
	}
	
}
