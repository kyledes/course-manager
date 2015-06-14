package edu.paymentus;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Test;

import edu.paymentus.domain.BinaryTree;
import edu.paymentus.domain.Node;

public class BtreeTest {

	
	@Test
	public void TestBTree(){
	
		BinaryTree tree = new BinaryTree();
		
		tree.insert(4);
		tree.insert(5);
		tree.insert(9);
		tree.insert(8);
		tree.insert(4);
		tree.insert(6);
		tree.insert(5);
		tree.insert(2);
		tree.insert(7);
		tree.insert(8);
		
		assertEquals("Levels: 1,3", tree.breadthSearch(4));
		
	}
	
}
