package edu.paymentus.UI;

import java.util.Arrays;
import java.util.List;

import javax.annotation.PostConstruct;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;

import edu.paymentus.domain.BinaryTree;

@SpringView(name = BinaryTreeView.VIEW_NAME)
public class BinaryTreeView extends FormLayout implements View {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5593268713652809714L;
	public static final String VIEW_NAME = "BTree";
	public static final String DISPLAY_NAME = "BTree";
	
	private TextField tree = new TextField("tree");
	private TextField searchNumber = new TextField("Search Number");
	
	private TextArea result = new TextArea();
	private Button buildTree = new Button("Build Tree");
	private Button search = new Button("Search");
	
	private BinaryTree btree = new BinaryTree();
	
	@PostConstruct
	void init(){
    	
		configureComponents();
		buildLayout();
	}
	
	private void configureComponents() {
		buildTree.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				System.out.println("click");
				buildBinaryTree(tree.getValue());
				
			}


			
		});
		
		search.addClickListener(new ClickListener(){

			@Override
			public void buttonClick(ClickEvent event) {
				searchTree(searchNumber.getValue());
				
			}
			
		});
		
		
	}
	

	private void buildLayout() {

		addComponents(tree, buildTree,searchNumber, search, result);
		
	}
	
	private void buildBinaryTree(String value) {
		btree= new BinaryTree();
		List<String> strNodes = Arrays.asList(value.split(","));
		for(String node : strNodes){
			btree.insert(Integer.parseInt(node));
		}
		
	}
	
	private void searchTree(String value) {
	    result.setValue(btree.breadthSearch(Integer.parseInt(value)));
	    
		
	}
	
	@Override
	public void enter(ViewChangeEvent event) {
		// TODO Auto-generated method stub
		
	}
}
