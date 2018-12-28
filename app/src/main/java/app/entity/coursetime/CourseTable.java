package app.entity.coursetime;

import app.entity.smart.CourseBaseInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class CourseTable {
	public CourseTable() {

	}
	
 public CourseTable(String number) {
		super();
		this.number = number;
	}

 private String number;
 private String numberName;
 private  List<CourseBaseInfo> monday = new ArrayList<>();
 private  List<CourseBaseInfo> tuesday = new ArrayList<>();
 private  List<CourseBaseInfo> wednesday = new ArrayList<>();
 private  List<CourseBaseInfo> thursday = new ArrayList<>();
 private  List<CourseBaseInfo> friday = new ArrayList<>();
 private  List<CourseBaseInfo> saturday = new ArrayList<>();
 private  List<CourseBaseInfo> sunday = new ArrayList<>();
@Override
public String toString() {
	return "CourseTable [number=" + number + ", numberName=" + numberName + ", monday=" + monday + ", tuesday="
			+ tuesday + ", wednesday=" + wednesday + ", thursday=" + thursday + ", friday=" + friday + ", saturday="
			+ saturday + ", sunday=" + sunday + "]";
}

public String getNumber() {
	return number;
}

public void setNumber(String number) {
	this.number = number;
}

public String getNumberName() {
	return numberName;
}

public void setNumberName(String numberName) {
	this.numberName = numberName;
}

public  List<CourseBaseInfo> getMonday() {
	return monday;
}

public void setMonday( List<CourseBaseInfo> monday) {
	this.monday = monday;
}

public  List<CourseBaseInfo> getTuesday() {
	return tuesday;
}

public void setTuesday( List<CourseBaseInfo> tuesday) {
	this.tuesday = tuesday;
}

public  List<CourseBaseInfo> getWednesday() {
	return wednesday;
}

public void setWednesday( List<CourseBaseInfo> wednesday) {
	this.wednesday = wednesday;
}

public  List<CourseBaseInfo> getThursday() {
	return thursday;
}

public void setThursday( List<CourseBaseInfo> thursday) {
	this.thursday = thursday;
}

public  List<CourseBaseInfo> getFriday() {
	return friday;
}

public void setFriday( List<CourseBaseInfo> friday) {
	this.friday = friday;
}

public  List<CourseBaseInfo> getSaturday() {
	return saturday;
}

public void setSaturday( List<CourseBaseInfo> saturday) {
	this.saturday = saturday;
}

public  List<CourseBaseInfo> getSunday() {
	return sunday;
}

public void setSunday( List<CourseBaseInfo> sunday) {
	this.sunday = sunday;
}

 
 
}
