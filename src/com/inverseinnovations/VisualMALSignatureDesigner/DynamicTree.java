package com.inverseinnovations.VisualMALSignatureDesigner;

import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.event.TreeModelEvent;
import javax.swing.event.TreeModelListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.MutableTreeNode;
import javax.swing.tree.TreePath;
import javax.swing.tree.TreeSelectionModel;

import com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock.*;

/*
 * Copyright (c) 1995 - 2008 Sun Microsystems, Inc. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met: -
 * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer. - Redistributions in binary
 * form must reproduce the above copyright notice, this list of conditions and
 * the following disclaimer in the documentation and/or other materials provided
 * with the distribution. - Neither the name of Sun Microsystems nor the names
 * of its contributors may be used to endorse or promote products derived from
 * this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
public class DynamicTree extends JPanel {
	private static final long serialVersionUID = 1L;
	public Main Main;
	protected BuildingBlock rootNode;
	protected DefaultTreeModel treeModel;
	protected JTree tree;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();

	public DynamicTree(Main Main) {
		super(new GridLayout(1, 0));

		this.Main = Main;
		rootNode = new InitSignature(Main);
		treeModel = new DefaultTreeModel(rootNode);

		tree = new JTree(treeModel);
		tree.setEditable(true);
		tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
		tree.setShowsRootHandles(true);

		JScrollPane scrollPane = new JScrollPane(tree);
		add(scrollPane);
	}

	/** Remove all nodes except the root node. */
	public void clear() {
		rootNode.removeAllChildren();
		treeModel.reload();
	}

	public BuildingBlock getRootNode(){
		return rootNode;
	}
	/** Get the currently selected node. */
	public BuildingBlock getCurrentNode() {
		TreePath currentSelection = tree.getSelectionPath();
		if (currentSelection != null) {
			BuildingBlock currentNode = (BuildingBlock) (currentSelection.getLastPathComponent());
			return currentNode;
		}
		return null;
	}

	/** Remove the currently selected node. */
	public void removeCurrentNode() {
		TreePath currentSelection = tree.getSelectionPath();
		if (currentSelection != null) {
			DefaultMutableTreeNode currentNode = (DefaultMutableTreeNode) (currentSelection.getLastPathComponent());
			MutableTreeNode parent = (MutableTreeNode) (currentNode.getParent());
			if (parent != null) {
				treeModel.removeNodeFromParent(currentNode);
				return;
			}
		}

		// Either there was no selection, or the root was selected.
		toolkit.beep();
	}

	/** Add child to the currently selected node. */
	//public DefaultMutableTreeNode addObject(Object child) {
	public void addObject(BuildingBlock child) {
		BuildingBlock parentNode = null;
		TreePath parentPath = tree.getSelectionPath();

		if (parentPath == null) {
			parentNode = rootNode;
		} else {
			parentNode = (BuildingBlock) (parentPath.getLastPathComponent());
		}

		addObject(parentNode, child, true);
	}

	//public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,Object child) {
	public void addObject(BuildingBlock parent,BuildingBlock child) {
		addObject(parent, child, false);
	}

	//public DefaultMutableTreeNode addObject(DefaultMutableTreeNode parent,Object child, boolean shouldBeVisible) {
	public void addObject(BuildingBlock parent,BuildingBlock child, boolean shouldBeVisible) {
		//DefaultMutableTreeNode childNode = new DefaultMutableTreeNode(child);

		if (parent == null) {
			parent = rootNode;
		}

		// It is key to invoke this on the TreeModel, and NOT DefaultMutableTreeNode
		treeModel.insertNodeInto(child, parent, parent.getChildCount());

		// Make sure the user can see the lovely new node.
		if (shouldBeVisible) {
			tree.scrollPathToVisible(new TreePath(child.getPath()));
		}
		//return child;
	}

	/**
	 * Moves the currently selected Block up within the same parent Block
	 */
	public void moveNodeUp(){
		BuildingBlock node = getCurrentNode();
		if(node != null){
			if(node != rootNode){
				int index = node.getParent().getIndex(node);
				// if selected node is first, return (can't move it up)
				if(index > 0) {
					treeModel.insertNodeInto(node, (BuildingBlock)node.getParent(), index-1);    // move the node
					// reload revalidates the look of the JTree on screen
					treeModel.reload();
					Main.ImageWindow.update();
					tree.setSelectionPath(new TreePath(node.getPath()));//reselect the block
				}
			}
		}
	}
	/**
	 * Moves the currently selected Block down within the same parent Block
	 */
	public void moveNodeDown(){
		BuildingBlock node = getCurrentNode();
		if(node != null){
			if(node != rootNode){
				int index = node.getParent().getIndex(node);
				// if selected node is not the last
				//if(index > 0) {
				if(index < (node.getParent().getChildCount()-1)) {
					treeModel.insertNodeInto(node, (BuildingBlock)node.getParent(), index+1);    // move the node
					// reload revalidates the look of the JTree on screen
					treeModel.reload();
					Main.ImageWindow.update();
					tree.setSelectionPath(new TreePath(node.getPath()));//reselect the block
				}
			}
		}
	}

	class MyTreeModelListener implements TreeModelListener {
		public void treeNodesChanged(TreeModelEvent e) {
			DefaultMutableTreeNode node;
			node = (DefaultMutableTreeNode) (e.getTreePath().getLastPathComponent());

			/*
			 * If the event lists children, then the changed node is the child of the
			 * node we've already gotten. Otherwise, the changed node and the
			 * specified node are the same.
			 */

			int index = e.getChildIndices()[0];
			node = (DefaultMutableTreeNode) (node.getChildAt(index));

			System.out.println("The user has finished editing the node.");
			System.out.println("New value: " + node.getUserObject());
		}

		public void treeNodesInserted(TreeModelEvent e) {
		}

		public void treeNodesRemoved(TreeModelEvent e) {
		}

		public void treeStructureChanged(TreeModelEvent e) {
		}
	}
}
