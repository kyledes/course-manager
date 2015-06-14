package edu.paymentus.UI;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import edu.paymentus.domain.Course;
import edu.paymentus.domain.Student;
import edu.paymentus.service.CourseService;
import edu.paymentus.service.StudentService;

@SpringView(name = StudentsEnrolledView.VIEW_NAME)
public class StudentsEnrolledView extends VerticalLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4401396211795538132L;
	public static final String VIEW_NAME = "StudentsEnrolled";
	public static final String DISPLAY_NAME = "StudentsEnrolled";

	@Autowired
	private CourseService courseService;

	@Autowired
	private StudentService studentService;

	private Grid studentList = new Grid();
	private Student selectedStudent;
	private Course course;

	private Label courseListed = new Label();

	@PostConstruct
	private void init() {
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {

		studentList.setContainerDataSource(new BeanItemContainer<Student>(
				Student.class));
		studentList.setColumnOrder("firstName", "lastName", "email");
		studentList.removeColumn("id");
		studentList.removeColumn("fields");
		studentList.setSelectionMode(Grid.SelectionMode.SINGLE);

		studentList.addSelectionListener(new SelectionListener() {
			@Override
			public void select(SelectionEvent event) {
				selectedStudent = (Student) studentList.getSelectedRow();
			}
		});

	}

	private void buildLayout() {
		HorizontalLayout title = new HorizontalLayout(courseListed);
		
		VerticalLayout left = new VerticalLayout(title, studentList);
		left.setSizeFull();
		studentList.setSizeFull();
		left.setExpandRatio(studentList, 1);

		addComponent(left);

	}

	@Override
	public void enter(ViewChangeEvent event) {
		Long courseId = Long.parseLong(event.getParameters());
		course = courseService.getCourseById(courseId);

		if (course == null) {
			getUI().getNavigator().navigateTo(CourseView.VIEW_NAME);
		}
		courseListed.setValue(course.getCourseName());

		refreshCourses();
	}

	private void refreshCourses() {
		studentList.setContainerDataSource(new BeanItemContainer<Student>(
				Student.class, studentService.getStudentByCourse(course)));

	}

}
