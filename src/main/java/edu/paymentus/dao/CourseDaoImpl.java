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
public class CourseDaoImpl extends GenericDaoImpl<Course, Long> implements CourseDao {
	
	private static final String FINDBYID = "select course_id, courseName, location, credit from course where course_id = ?";
	private static final String FINDALL ="select course_id, courseName, location, credit from course";
	private static final String INSERT = "insert into course ( courseName, location, credit, course_id) VALUES (?, ?, ?, ?)";
	private static final String UPDATE = "update course set courseName = ?, location = ?, credit = ? where course_id = ?";
	private static final String DELETE = "delete from course where course_id = ?";
	private static final String LIKE = "select course_id, courseName, location, credit from course where courseName like ?";
	private static final String FINDALLBYSTUDENT ="select course.course_id, courseName, location, credit from course "
			                                    + " inner join studentcourses on course.course_id = studentcourses.course_id  "
			                                    + "where studentcourses.student_id = ? ";
	private static final String ENROLLSTUDENT ="insert into studentcourses (course_id, student_id) values (?, ?)";
	private static final String FINDALLNOTENROLLED = "select course.course_id, courseName, location, credit from course where course_id not in( select course_id from studentcourses where student_id = ?)";
	private static final String FINDALLNOTENROLLEDLIKE = "select course.course_id, courseName, location, credit from course where course.courseName like ? and course_id not in ( select course_id from studentcourses where student_id = ?)";
	private static final String DROPCOURSE = "delete from studentcourses where course_id = ? and student_id = ?";
	
	private static RowMapper<Course> rowMapper =  new RowMapper<Course>(){
			@Override
			public Course mapRow(ResultSet resultSet, int rowNum) throws SQLException {
				return new Course(resultSet.getLong("course_id"), resultSet.getString("courseName"), resultSet.getString("location"), 
						resultSet.getInt("credit"));
			}
		};
	
	
	@Autowired
    private JdbcTemplate jdbcTemplate;
	
	
	@PostConstruct
	private void init(){
		super.init(rowMapper, jdbcTemplate);
		
	}
	@Override
	public List<Course> search(String text) {
		text = "%" + text + "%";
		return jdbcTemplate.query(LIKE, rowMapper, new Object[]{ text});
	}
	
	@Override
	public long getPKFromSeq() {
		List<Long> pk = jdbcTemplate.query("select coursemanager.courseid_seq.nextval coursepk from dual", new RowMapper<Long>(){

			@Override
			public Long mapRow(ResultSet resultSet, int arg1) throws SQLException {
				
				return resultSet.getLong("coursepk");
			}
			
				
		});
		return pk.get(0);
	}
	
	@Override
	public List<Course> getCoursesforStudent(Long studentId) {
		Object[] params = { studentId };
		return jdbcTemplate.query(FINDALLBYSTUDENT, rowMapper, params);
	}
	@Override
	public void enrollStudentInCourse(Course course, Student student) {
		Object[] params = {course.getId(), student.getId()};
		jdbcTemplate.update(ENROLLSTUDENT, params);
	}
	@Override
	public void dropCourseForStudent(Course course, Student student) {
		Object[] params = {course.getId(), student.getId()};
		jdbcTemplate.update(DROPCOURSE, params);
		
	}

	@Override
	public List<Course> findAllCoursesNotEnrolled(Student student, String text){
		
		String sql;
		Object[] params;
		
		if(text == null || text.equalsIgnoreCase("")){
			sql = FINDALLNOTENROLLED;
			params = new Object[] {student.getId()};
		}else{
			text = "%" + text + "%";
			sql = FINDALLNOTENROLLEDLIKE;
			params = new Object[]{text, student.getId()};	
		}
		
		return jdbcTemplate.query(sql,  rowMapper, params);
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
