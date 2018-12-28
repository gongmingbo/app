package app.entity.coursetime;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class CourseTemplate {
	private  String id;//编号
	private  String name; // 课程名称
	private  String teacher;//教师

	@Override
	public String toString() {
		return "CourseTemplate{" +
				"id='" + id + '\'' +
				", name='" + name + '\'' +
				", teacher='" + teacher + '\'' +
				", classNo=" + Arrays.toString(classNo) +
				", dayOfWeek=" + dayOfWeek +
				", weeks=" + Arrays.toString(weeks) +
				", classroom='" + classroom + '\'' +
				", reminder='" + reminder +'\'' +
				", type='" + type + '\'' +
				", state=" + state +
				'}';
	}

	private  int [] classNo;//上课节数
	private  int dayOfWeek;//星期数
	private  int [] weeks;//上课周数（1，2，3）
	private  String classroom;//教室
	private  String reminder;//提示时间（正整数）
	private  String type;//类型(必修，选修，"")
	private int state;//是否删除（默认为0，1为删除）

	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTeacher() {
		return teacher;
	}
	public void setTeacher(String teacher) {
		this.teacher = teacher;
	}
	public int[] getClassNo() {
		return classNo;
	}
	public void setClassNo(int[] classNo) {
		this.classNo = classNo;
	}
	public int getDayOfWeek() { return dayOfWeek; }
	public void setDayOfWeek(int dayOfWeek) { this.dayOfWeek = dayOfWeek; }
	public int[] getWeeks() { return weeks; }
	public void setWeeks(int[] weeks) { this.weeks = weeks; }
	public String getClassroom() {
		return classroom;
	}
	public void setClassroom(String classroom) {
		this.classroom = classroom;
	}
	public String getReminder() {return reminder; }
	public void setReminder(String reminder) {
		this.reminder = reminder;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getState() { return state; }
	public void setState(int state) { this.state = state; }
}
