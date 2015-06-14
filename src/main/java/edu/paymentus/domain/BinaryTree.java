package edu.paymentus.domain;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {

	private Node root;

	public BinaryTree() {
	}

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

	public void insert(int value) {
		if (root == null) {
			System.out.println("rootNode: " + value);
			root = new Node(value);
		}else{
			insert(root, value);
		}
		
	}

	public void insert(Node root, int value) {
		Queue<Node> queue = new LinkedList<>();
		queue.offer(root);
		while (!queue.isEmpty()) {
			Node temp = queue.poll();
			if (temp.getLeft() != null) {
				queue.offer(temp.getLeft());
			} else {
				temp.setLeft(new Node(value));
				System.out.println("  Inserted " + value + " to left of "
						+ temp.getValue());
				return;
			}
			if (temp.getRight() != null) {
				queue.offer(temp.getRight());
			} else {
				temp.setRight(new Node(value));
				System.out.println("  Inserted " + value + " to right of "
						+ temp.getValue());
				return;
			}
		}
	}

	
	public String breadthSearch(int value) {
		Queue<Node> queue = new LinkedList<Node>() ;
	    int level = 0;
	    int nodeCount = 0;
	    StringBuffer result = new StringBuffer("Levels: ");
		if (root == null)
	        return "empty";
	    queue.clear();
	    queue.add(root);
	    while(!queue.isEmpty()){
	        Node node = queue.remove();
	        nodeCount += 1;
	        if(nodeCount == (int) Math.pow(2.0, (level + 1.0))){
	        	level +=1;	
	        }
	        if(node.getValue() == value){
	        	result.append((level +1) + ",");	
	        }
	        
	        
	        if(node.getLeft() != null) queue.add(node.getLeft());
	        if(node.getRight() != null) queue.add(node.getRight());
	    }
	    result.delete(result.length() - 1, result.length());
	    System.out.print(result);
		return result.toString();

	}
}
