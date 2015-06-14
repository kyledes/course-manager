package edu.paymentus;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import edu.paymentus.dao.StudentDao;
import edu.paymentus.domain.Student;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CourseManagerApplication.class)
@WebAppConfiguration
public class CourseManagerApplicationTests {

	@Autowired
	private StudentDao studentDao;
	
	@Test
	public void contextLoads() {
	}

	@Test 
	public void testGetStudent(){
		assertEquals("Ricky", studentDao.findById(2l).getFirstName());
	}
	
	@Test
	public void testFindAll(){
		List<Student> students = studentDao.findAll();
		
		assertEquals(19, students.size());
	}
	
	@Test
	public void testInsert(){
		studentDao.insert(new Object[] { "blah", "blah", "emailblah", 20});
		assertEquals("blah", studentDao.findById(20l).getFirstName());
	}
}
