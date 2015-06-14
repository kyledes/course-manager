package edu.paymentus.UI;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.event.SelectionEvent;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.Grid;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.VerticalLayout;

import edu.paymentus.domain.Course;
import edu.paymentus.service.CourseService;

@SpringView(name = CourseView.VIEW_NAME)
public class CourseView extends VerticalLayout implements View  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8622365564603552443L;
	public static final String VIEW_NAME = "Course";
	public static final String DISPLAY_NAME = "Course";

	@Autowired
	private CourseService courseService;
	
	private Course selectedCourse;
	
	private Grid courseList = new Grid();
	private Button newCourse = new Button("New Course");
	private Button deleteCourse = new Button("Delete Course");
	private Button viewEnrolled = new Button("Enrolled Students");

	private CourseForm courseForm = new CourseForm(this);
	
    @PostConstruct
    private void init() {
    	
		configureComponents();
		buildLayout();

    }

	private void configureComponents() {
        setMargin(true);
        setSpacing(true);
		newCourse.addClickListener(new ClickListener() {
			@Override
			public void buttonClick(ClickEvent event) {
				courseForm.edit(new Course());
			}
		});		
		
		deleteCourse.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				if(selectedCourse != null){
					courseService.deleteCourse(selectedCourse);
					refreshCourses();
				}
				
			}
			
		});
		
		viewEnrolled.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				if(selectedCourse != null){
					getUI().getNavigator().navigateTo(StudentsEnrolledView.VIEW_NAME +"/" + selectedCourse.getId());	
				}else{
					Notification.show("select a course", Type.TRAY_NOTIFICATION);
				}
					
				
			}
			
		});
		
		courseList.setContainerDataSource(new BeanItemContainer<Course>(
				Course.class));
		courseList.setColumnOrder("courseName","location","credit");
		courseList.removeColumn("id");
		courseList.removeColumn("fields");
		courseList.setSelectionMode(Grid.SelectionMode.SINGLE);
		
		courseList.addSelectionListener(new SelectionListener() {
			@Override
			public void select(SelectionEvent event) {
				selectedCourse = (Course) courseList.getSelectedRow();
				courseForm.edit(selectedCourse);
			}
		});
		refreshCourses();
	}
	
	private void buildLayout() {
		HorizontalLayout title = new HorizontalLayout(new Label("Courses"));
		
		VerticalLayout left = new VerticalLayout(title, courseList);
		left.setSizeFull();
		courseList.setSizeFull();
		left.setExpandRatio(courseList, 1);
		HorizontalLayout courseLayout = new HorizontalLayout(left, courseForm);
		courseLayout.setSizeFull();
		courseLayout.setExpandRatio(left, 1);
		HorizontalLayout actions = new HorizontalLayout( newCourse, deleteCourse, viewEnrolled);
		
		VerticalLayout mainLayout = new VerticalLayout(courseLayout, actions);
		
		mainLayout.setSizeFull();
		
		addComponent(mainLayout);
	}
	
	public void refreshCourses() {
		courseList.setContainerDataSource(new BeanItemContainer<Course>(
				Course.class, courseService.findAll()));

	}

	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}

	public Grid getCourseList() {	
		return this.courseList;
	}

}
