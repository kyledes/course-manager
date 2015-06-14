package edu.paymentus.service;

import java.util.List;

import edu.paymentus.domain.Course;
import edu.paymentus.domain.Student;

public interface StudentService {

	public Student getStudentById(long id);
	public List<Student> findAll();
	public void save(Student student);
	public void deleteStudent(Student selectedStudent);
	public List<Student> getStudentByCourse(Course course);
}
