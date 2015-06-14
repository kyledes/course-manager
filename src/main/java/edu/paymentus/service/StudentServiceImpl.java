package edu.paymentus.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.paymentus.dao.StudentDao;
import edu.paymentus.domain.Course;
import edu.paymentus.domain.Student;

@Service
public class StudentServiceImpl implements StudentService {
	
	@Autowired
	private StudentDao studentDao;

	@Override
	public Student getStudentById(long id) {
		return studentDao.findById(id);
	}

	@Override
	public List<Student> findAll() {
		return studentDao.findAll();
	}

	@Override
	public void save(Student student) {
		if(student.getId() == 0){
			student.setId(studentDao.getPKFromSeq());
			studentDao.insert(student.getFields());	
		}
		studentDao.update(student.getFields());
		
	}

	@Override
	public void deleteStudent(Student student) {
		studentDao.delete(student.getId());
	}

	@Override
	public List<Student> getStudentByCourse(Course course) {
		return studentDao.getStudentsByCourse(course);
	}


}
