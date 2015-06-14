package edu.paymentus.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import edu.paymentus.domain.Course;
import edu.paymentus.domain.Student;

@Repository
public class StudentDaoImpl extends GenericDaoImpl<Student, Long> implements StudentDao {
	
	private static final String FINDBYID = "select student_id, firstName, lastName, email from student where student_id = ?";
	private static final String FINDALL ="select student_id, firstName, lastName, email from student";
	private static final String FINDSTUDENTBYCOURSE = "select student.student_id, firstName, lastName, email from student "
	                                                + "join studentcourses on student.STUDENT_ID = studentcourses.STUDENT_ID where studentcourses.COURSE_ID = ?";
	private static final String INSERT = "insert into student ( firstName, lastName, email, student_id) VALUES (?, ?, ?, ?)";
	private static final String UPDATE = "update student set firstName = ?, lastName = ?, email = ? where student_id = ?";
	private static final String DELETE = "delete from student where student_id = ?";
	
	private static RowMapper<Student> rowMapper =  new RowMapper<Student>(){
			@Override
			public Student mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				return new Student(resultSet.getLong("student_id"), resultSet.getString("firstName"), resultSet.getString("lastName"), 
						resultSet.getString("email"));
			}
		};
	
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	@PostConstruct
	private void init(){
		super.init( rowMapper, jdbcTemplate);
		
	}
	

	@Override
	public long getPKFromSeq() {
		List<Long> pk = jdbcTemplate.query("select studentid_seq.nextval stupk from dual", new RowMapper<Long>(){

			@Override
			public Long mapRow(ResultSet resultSet, int arg1) throws SQLException {
				
				return resultSet.getLong("stupk");
			}
			
				
		});
		return pk.get(0);
	}


	@Override
	public List<Student> getStudentsByCourse(Course course) {
		Object[] params = {course.getId()};
		return jdbcTemplate.query(FINDSTUDENTBYCOURSE, rowMapper, params);
	}


	@Override
	public String getFindByIdSql() {
		return FINDBYID;
	}


	@Override
	public String getFindAllSql() {
		return FINDALL;
	}


	@Override
	public String getInsertSql() {
		return INSERT;
	}


	@Override
	public String getUpdateSql() {
		return UPDATE;
	}


	@Override
	public String getDeleteSql() {
		return DELETE;
	}

}
