package edu.paymentus.dao;

import java.util.List;

import edu.paymentus.domain.Course;
import edu.paymentus.domain.Student;

public interface CourseDao extends GenericDao<Course, Long> {

	List<Course> search(String text);

	long getPKFromSeq();

	List<Course> getCoursesforStudent(Long studentId);

	void enrollStudentInCourse(Course course, Student student);

	void dropCourseForStudent(Course course, Student student);

	List<Course> findAllCoursesNotEnrolled(Student student);

}
