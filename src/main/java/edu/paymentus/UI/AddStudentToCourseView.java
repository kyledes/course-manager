package edu.paymentus.UI;

import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Sizeable;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;

import edu.paymentus.domain.Course;
import edu.paymentus.domain.Student;
import edu.paymentus.service.CourseService;
import edu.paymentus.service.StudentService;

@SpringView(name = AddStudentToCourseView.VIEW_NAME)
public class AddStudentToCourseView extends VerticalLayout implements View, ClickListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5335906515450125377L;
	public static final String VIEW_NAME = "ViewCourses";
	public static final String DISPLAY_NAME = "View Courses";

	@Autowired
	private CourseService courseService;
	
	@Autowired
	private StudentService studentService;
	
	private Student student;
	private Course selectedCourseToAdd;
	private Course selectedCourseToRemove;
	
	private List<Course> availableCourses;
	private List<Course> enrolledCourses;
	
	private TextField search = new TextField();
	private Grid courseList = new Grid();
	private Button add = new Button("Enroll Student");
	private Button remove = new Button("Drop Course");
	private Grid enrolledList = new Grid();
	private Label stu = new Label(); 
    
	@PostConstruct
	void init() {
		configureComponents();
		buildLayout();
	}

	private void configureComponents() {
		setMargin(true);
		setSpacing(true);
		
		courseList.setContainerDataSource(new BeanItemContainer<Course>(Course.class));
		courseList.removeColumn("id");
		courseList.removeColumn("fields");
		courseList.setSelectionMode(Grid.SelectionMode.SINGLE);
		
		courseList.addSelectionListener(new SelectionListener() {
			@Override
			public void select(SelectionEvent event) {
				selectedCourseToAdd = (Course) courseList.getSelectedRow();
				add.setEnabled(true);
			}
		});
		
		
		search.setInputPrompt("Filter Courses...");
		search.addTextChangeListener(new TextChangeListener() {
			@Override
			public void textChange(TextChangeEvent event) {
				refreshCourses(event.getText());
			}

		});
		
		enrolledList.setContainerDataSource(new BeanItemContainer<Course>(Course.class));
		enrolledList.removeColumn("id");
		enrolledList.removeColumn("fields");
		
		enrolledList.addSelectionListener(new SelectionListener(){

			@Override
			public void select(SelectionEvent event) {
				selectedCourseToRemove = (Course) enrolledList.getSelectedRow();
				remove.setEnabled(true);		
			}
		
		});
						
		add.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				if(selectedCourseToAdd != null ){
					System.out.println("*Course: " + selectedCourseToAdd.getId() + " *Student: " + student.getId());
					courseService.enrollStudent(selectedCourseToAdd, student);
					refreshCourses(search.getValue());					
				}

				
			}
			
		});

		remove.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				if(selectedCourseToRemove != null){
					courseService.dropCourseForStudent(selectedCourseToRemove, student);
					refreshCourses(search.getValue());					
				}

			}
			
		});
	}

	private void buildLayout() {
		
		search.setSizeFull();
		courseList.setSizeFull();
		courseList.setWidth("100%");
		stu.setSizeFull();
		enrolledList.setSizeFull();
		enrolledList.setWidth("100%");
		
		HorizontalLayout row1 = new HorizontalLayout(search, stu);
		HorizontalLayout row2 = new HorizontalLayout(courseList, enrolledList);
		HorizontalLayout row3 = new HorizontalLayout(add, remove);
		
		VerticalLayout main = new VerticalLayout(row1, row2, row3);
		
		row1.setSizeFull();
		row1.setSpacing(true);
		
		row2.setSizeFull();
		row2.setSpacing(true);
		
		row3.setSizeFull();
		row3.setSpacing(true);
		addComponent(main);
	/*	VerticalLayout left = new VerticalLayout(search, courseList, add);
		add.setEnabled(false);
		left.setSizeFull();
		//VerticalLayout center = new VerticalLayout(add,remove);
		
		VerticalLayout right = new VerticalLayout(stu, enrolledList, remove);
		right.setSizeFull();
		
		HorizontalLayout main = new HorizontalLayout(left, right);
		main.setSizeFull();
		VerticalLayout stuname = new VerticalLayout(main);
		addComponent(stuname);*/
		
	}

	@Override
	public void enter(ViewChangeEvent event) {
		Long studentId = Long.parseLong(event.getParameters());
		this.student = studentService.getStudentById(studentId);
		if(student == null){
		  getUI().getNavigator().navigateTo(StudentView.VIEW_NAME);	
		}
		stu.setValue(student.getFirstName() + " " + student.getLastName());
		
		refreshCourses("");

	}

	private void refreshCourses(String text) {
		availableCourses = courseService.findAllCoursesNotEnrolled(student);
		enrolledCourses = courseService.getCoursesForStudent(student);
		courseList.setContainerDataSource(new BeanItemContainer<Course>(Course.class, availableCourses));
		enrolledList.setContainerDataSource(new BeanItemContainer<Course>(Course.class, enrolledCourses));

	}

	@Override
	public void buttonClick(ClickEvent event) {
		// TODO Auto-generated method stub
		
	}
}
