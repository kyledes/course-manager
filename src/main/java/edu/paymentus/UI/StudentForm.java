package edu.paymentus.UI;

import com.vaadin.data.Validator;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.RegexpValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.event.ShortcutAction;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Notification.Type;
import com.vaadin.ui.TextField;
import com.vaadin.ui.themes.ValoTheme;
import edu.paymentus.domain.Student;

public class StudentForm extends FormLayout implements ClickListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1483780566604213373L;
	private Button save = new Button("Save", this);
	private Button cancel = new Button("Cancel", this);
	private TextField firstName = new TextField("First name");
	private TextField lastName = new TextField("Last name");
	private TextField email = new TextField("Email");
	private Button addCourse = new Button("View/Add Course", this);

	
	private Student student;
	private StudentView studentView;

	private BeanFieldGroup<Student> formFieldBindings;

	public StudentForm(StudentView studentView) {
		this.studentView = studentView;
		configureComponents();
		buildLayout();
	}

	private void buildLayout() {
		setSizeUndefined();
		setMargin(true);
		HorizontalLayout actions = new HorizontalLayout(save, cancel);
		actions.setSpacing(true);
		addComponents(actions, firstName, lastName, email, addCourse);

	}

	private void configureComponents() {
		save.setStyleName(ValoTheme.BUTTON_PRIMARY);
		save.setClickShortcut(ShortcutAction.KeyCode.ENTER);
		setVisible(false);
		
		Validator stringLength = new StringLengthValidator(" Field must be 1-50 letters (was {0})",1,50, true);
		Validator alphaOnly = new RegexpValidator("^[a-zA-z]*$", "Alphbetic chracters only");
		
		firstName.addValidator(stringLength);
		firstName.addValidator(alphaOnly);
		
		lastName.addValidator(stringLength);
		lastName.addValidator(alphaOnly);
		
		email.addValidator(new EmailValidator("invalid email address"));
	}

	void edit(Student student) {
		this.student = student;
		
		if (student != null) {
			formFieldBindings = BeanFieldGroup.bindFieldsBuffered(student, this);
			firstName.focus();
		}
		setVisible(student != null);
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
				getUI().getStudentService().save(student);;
				String msg = String.format("Saved '%s %s'.", student.getFirstName(), student.getLastName());
				Notification.show(msg, Type.TRAY_NOTIFICATION);
				this.studentView.refreshStudents();
			} catch (FieldGroup.CommitException e) {
				// Validation exceptions could be shown here
			}
		} else if (event.getButton() == cancel) {
			// Place to call business logic.
			Notification.show("Cancelled", Type.TRAY_NOTIFICATION);
			this.studentView.getStudentList().select(null);
			
		} else if (event.getButton() == addCourse){
//			System.out.println("in add course");
			Student selectedStudent = studentView.getSelectedStudent();
			if(selectedStudent == null || selectedStudent.getId() == 0){
				Notification.show("Please Select A Student", Type.TRAY_NOTIFICATION);
			}else{
				getUI().getNavigator().navigateTo(AddStudentToCourseView.VIEW_NAME +"/" + selectedStudent.getId());	
			}
			
		}
	}

}