package edu.paymentus.UI;

import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.themes.ValoTheme;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;

import edu.paymentus.domain.Course;
import edu.paymentus.domain.Student;

public class CourseForm extends FormLayout implements ClickListener{
	
	private Button save = new Button("Save", this);
	private Button cancel = new Button("Cancel", this);
	TextField courseName = new TextField("Name");
	TextField location = new TextField("Location");
	TextField credit = new TextField("Credit");
	
	Course course;
	CourseView courseView;
	
	BeanFieldGroup<Course> formFieldBindings;
	
	public CourseForm(CourseView courseView){
		this.courseView = courseView;
		configureComponents();
		buildLayout();
	}

	private void buildLayout() {
		setSizeUndefined();
		setMargin(true);
		HorizontalLayout actions = new HorizontalLayout(save, cancel);
		actions.setSpacing(true);
		addComponents(actions, courseName, location, credit);

	}

	private void configureComponents() {
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		Validator stringLength = new StringLengthValidator(" Field must be 1-50 letters (was {0})",1,50, true);
		Validator alphaNum = new RegexpValidator("^[a-zA-Z0-9_\\-\\s]*$", "alphanumeric characters only");
		
		courseName.addValidator(stringLength);
		courseName.addValidator(alphaNum);
		
		location.addValidator(stringLength);
		location.addValidator(alphaNum);
		
		setVisible(false);

	}
	
	void edit(Course course) {
		this.course = course;
		
		if (course != null) {
			formFieldBindings = BeanFieldGroup.bindFieldsBuffered(course, this);
			courseName.focus();
		}
		setVisible(course != null);
	}
	@Override
	public AppUI getUI() {
		return (AppUI) super.getUI();
	}
	@Override
	public void buttonClick(ClickEvent event) {
		if (event.getButton() == save) {
			try {
				// Commit the fields from UI to DAO
				formFieldBindings.commit();
				// Save DAO to backend with direct synchronous service API
				getUI().getCourseService().save(course);;
				String msg = String.format("Saved '%s'.", course.getCourseName());
				Notification.show(msg, Type.TRAY_NOTIFICATION);
				this.courseView.refreshCourses();
			} catch (FieldGroup.CommitException e) {
				// Validation exceptions could be shown here
			}
		} else if (event.getButton() == cancel) {
			// Place to call business logic.
			Notification.show("Cancelled", Type.TRAY_NOTIFICATION);
			this.courseView.getCourseList().select(null);
			
		}
		
	}

}
