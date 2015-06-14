package edu.paymentus.service;

import java.util.List;

import edu.paymentus.domain.Course;
import edu.paymentus.domain.Student;

public interface CourseService {

	List<Course> findCourse(String text);

	Course getCourseById(long id);

	List<Course> findAll();

	void save(Course course);

	List<Course> getCoursesForStudent(Student student);

	void enrollStudent(Course course, Student student);
	
	void dropCourseForStudent(Course course, Student student);

	List<Course> findAllCoursesNotEnrolled(Student student);

	void deleteCourse(Course selectedCourse);

}
