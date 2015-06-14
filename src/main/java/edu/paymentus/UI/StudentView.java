package edu.paymentus.UI;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.EventRouter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import edu.paymentus.domain.Student;
import edu.paymentus.service.StudentService;

@SpringView(name = StudentView.VIEW_NAME)
public class StudentView extends VerticalLayout implements View {//, ViewChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1751965457367687074L;
	public static final String VIEW_NAME = "Student";
	public static final String DISPLAY_NAME = "Student";

	@Autowired
	private StudentService studentService;
	
	private Student selectedStudent;
	
	private Grid studentList = new Grid();
	private Button newStudent = new Button("New Student");
	private Button deleteStudent = new Button("Delete Student");

	private StudentForm studentForm = new StudentForm(this);

	@PostConstruct
	void init() {
		setMargin(true);
		setSpacing(true);
		newStudent.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				studentForm.edit(new Student());
			}
		});
		
		deleteStudent.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				if(selectedStudent != null){
					studentService.deleteStudent(selectedStudent);
					refreshStudents();
				}
				
			}
			
		});

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
				studentForm.edit(selectedStudent);
			}
		});
	
		refreshStudents();
		buildLayout();
	}

	private void buildLayout() {
		HorizontalLayout label = new HorizontalLayout(new Label("Students"));
		label.setWidth("100%");
		HorizontalLayout actions = new HorizontalLayout(newStudent, deleteStudent);
		VerticalLayout left = new VerticalLayout(label, studentList, actions);
		left.setSizeFull();
		studentList.setSizeFull();
		left.setExpandRatio(studentList, 1);
		HorizontalLayout mainLayout = new HorizontalLayout(left, studentForm);
		mainLayout.setSizeFull();
		mainLayout.setExpandRatio(left, 1);
		addComponent(mainLayout);
	}

	public void refreshStudents() {
		studentList.setContainerDataSource(new BeanItemContainer<Student>(
				Student.class, studentService.findAll()));

	}

	@Override
	public void enter(ViewChangeEvent event) {}

	public Grid getStudentList() {
		return this.studentList;
	}

	public Student getSelectedStudent() {
		return this.selectedStudent;
	}

}