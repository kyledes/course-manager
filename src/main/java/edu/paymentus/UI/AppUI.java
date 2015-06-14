package edu.paymentus.UI;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.annotations.Theme;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.spring.navigator.SpringViewProvider;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

import edu.paymentus.service.CourseService;
import edu.paymentus.service.StudentService;

@SpringUI
@Theme("valo")
public class AppUI extends UI {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1990113771379119384L;

    @Autowired
    private SpringViewProvider viewProvider;
    
    @Autowired
    private StudentService studentService;
    
    @Autowired
    private CourseService courseService;
    
	@Override
    protected void init(VaadinRequest vaadinRequest) {
		
		final VerticalLayout root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.setSpacing(true);
        setContent(root);
         
        final CssLayout navigationBar = new CssLayout();
        navigationBar.addStyleName(ValoTheme.LAYOUT_COMPONENT_GROUP);
        navigationBar.addComponent(createNavigationButton(StudentView.DISPLAY_NAME, StudentView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton(CourseView.DISPLAY_NAME, CourseView.VIEW_NAME));
        navigationBar.addComponent(createNavigationButton(BinaryTreeView.DISPLAY_NAME, BinaryTreeView.VIEW_NAME));
        root.addComponent(navigationBar);
		
        final Panel viewContainer = new Panel();
        viewContainer.setSizeFull();
        root.addComponent(viewContainer);
        root.setExpandRatio(viewContainer, 1.0f);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);
        navigator.navigateTo(StudentView.DISPLAY_NAME);
		
    }
    private Button createNavigationButton(String caption, final String viewName) {
        Button button = new Button(caption);
        button.addStyleName(ValoTheme.BUTTON_SMALL);
        
        button.addClickListener(new ClickListener(){ 

		    @Override
		    public void buttonClick(ClickEvent event) {
			    getUI().getNavigator().navigateTo(viewName);
			
		    }
		});
        return button;
    }
    
    public StudentService getStudentService(){
    	return studentService;
    }
    
    public CourseService getCourseService(){
    	return courseService;
    }
    
    public ViewProvider getViewProvider(){
		return this.viewProvider;
    	
    }
}