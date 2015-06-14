package edu.paymentus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.paymentus.dao.CourseDao;
import edu.paymentus.domain.Course;
import edu.paymentus.domain.Student;

@Service
public class CourseServiceImpl implements CourseService {

	@Autowired
	private CourseDao courseDao;
	
	@Override
	public List<Course> findCourse(String text) {
		List<Course> courses;
		if(text == null || text == ""){
			courses = courseDao.findAll();
		}else{
			courses = courseDao.search(text);
		}
		return courses;
	}

	@Override
	public Course getCourseById(long id) {
		return courseDao.findById(id);
	}

	@Override
	public List<Course> findAll() {
		return courseDao.findAll();
	}

	@Override
	public void save(Course course) {
		if(course.getId() == 0){
			course.setId(courseDao.getPKFromSeq());
			courseDao.insert(course.getFields());	
		}
		courseDao.update(course.getFields());
		
	}
	
	@Override
	public List<Course> getCoursesForStudent(Student student){
		return courseDao.getCoursesforStudent(student.getId());
	}
	
	@Override
	public void enrollStudent(Course course, Student student){
		courseDao.enrollStudentInCourse(course,student);
	}

	@Override
	public void dropCourseForStudent(Course course, Student student) {
		courseDao.dropCourseForStudent(course, student);
	}	
	
	@Override
	public List<Course> findAllCoursesNotEnrolled(Student student){
		return courseDao.findAllCoursesNotEnrolled(student);
	}

	@Override
	public void deleteCourse(Course selectedCourse) {
		courseDao.delete(selectedCourse.getId());
	}
}
