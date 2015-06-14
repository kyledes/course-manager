package edu.paymentus.dao;

import java.util.List;

import edu.paymentus.domain.Course;
import edu.paymentus.domain.Student;

public interface StudentDao extends GenericDao<Student, Long> {

	long getPKFromSeq();

	List<Student> getStudentsByCourse(Course course);


}
