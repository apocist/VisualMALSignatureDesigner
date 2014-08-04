package com.inverseinnovations.VisualMALSignatureDesigner;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock.*;
import com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock.Filter.*;

public class BlockWindow {
	public final Main Main;

	public DynamicTree blocks;
	private JFrame blockFrame;

	public BlockWindow(final Main Main){
		this.Main = Main;
		create();
	}

	public void create(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				blockFrame = new JFrame("Tree Demo");
				blockFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

				//Menu
				JMenuBar menuBar = new JMenuBar();//Create the menu bar.

				JMenu menu = new JMenu("Edit");//Build the first menu.

				//a group of JMenuItems
				JMenuItem menuItem = new JMenuItem("Move Up");
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						blocks.moveNodeUp();
					}
				});
				menu.add(menuItem);

				menuItem = new JMenuItem("Move Down");
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						blocks.moveNodeDown();
					}
				});
				menu.add(menuItem);

				menuBar.add(menu);

				blockFrame.setJMenuBar(menuBar);
				//Content

				JButton addButton = new JButton("Add");
				addButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(blocks.getCurrentNode() == null){
							createBlockSelectionDialog();
						}
						else if(blocks.getCurrentNode().ISPARENTABLE){
							createBlockSelectionDialog();
						}
					}
				});

				JButton removeButton = new JButton("Remove");
				removeButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(blocks.getCurrentNode() != null){
							if(blocks.getCurrentNode() != blocks.getRootNode()){
								confirmDeleteBlockDialog();
							}
						}
					}
				});

				JButton editButton = new JButton("Edit");
				editButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(blocks.getCurrentNode() == null){
							blocks.rootNode.settingsDialog(blockFrame);
						}
						else{
							blocks.getCurrentNode().settingsDialog(blockFrame);
						}
					}
				});

				JPanel panel = new JPanel(new GridLayout(0, 3));
				panel.add(addButton);
				panel.add(editButton);
				panel.add(removeButton);
				blockFrame.add(panel, BorderLayout.NORTH);

				blocks = new DynamicTree(Main);
				populateTree(blocks);

				// Lay everything out.
					blocks.setPreferredSize(new Dimension(300, 150));
					blockFrame.add(blocks, BorderLayout.CENTER);




				//blockFrame.getContentPane().add(imageLabel, BorderLayout.CENTER);
				blockFrame.pack();
				//blockFrame.setLocationRelativeTo(null);
				Point loc = Main.ImageWindow.getLocation();
				blockFrame.setLocation((int) loc.getX()-400, (int) loc.getY());
				blockFrame.setVisible(true);

			}
		});
	}

	/**
	 * Creates a JDialog allowing user to select what Block to create as a child of the currently selected Block
	 * @return the JDialog window
	 */
	public JDialog createBlockSelectionDialog(){
		final JDialog d = new JDialog(blockFrame, "Add Block", true);

		String longestBlockName = " Anime Thumbnail ";

		//show block selection panel
		String[] blockTypes = {"Background","Text","Image","Anime Thumbnail","Anime Title","Anime Status"};
		final JList<String> blockList = new JList<String>(blockTypes); //data has type Object[]
		blockList.setPrototypeCellValue(longestBlockName);
		blockList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		blockList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		blockList.setVisibleRowCount(-1);
		JScrollPane blockListScroller = new JScrollPane(blockList);
		blockListScroller.setPreferredSize(new Dimension(250, 80));
		blockListScroller.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);

		JPanel blockListPane = new JPanel();
		blockListPane.setLayout(new BoxLayout(blockListPane, BoxLayout.PAGE_AXIS));
		blockListPane.add(Box.createRigidArea(new Dimension(0,5)));
		blockListPane.add(blockListScroller);
		blockListPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		//show filter selection panel
		String[] filterTypes = {"Rotate"};
		final JList<String> filterList = new JList<String>(filterTypes); //data has type Object[]
		filterList.setPrototypeCellValue(longestBlockName);
		filterList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		filterList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		filterList.setVisibleRowCount(-1);
		JScrollPane filterListScroller = new JScrollPane(filterList);
		filterListScroller.setPreferredSize(new Dimension(250, 80));
		filterListScroller.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);

		JPanel filterListPane = new JPanel();
		filterListPane.setLayout(new BoxLayout(filterListPane, BoxLayout.PAGE_AXIS));
		filterListPane.add(Box.createRigidArea(new Dimension(0,5)));
		filterListPane.add(filterListScroller);
		filterListPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		//buttons
		JButton okButton = new JButton("OK");

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				d.dispose();
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 3, 3, 3));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(okButton);
		buttonPane.add(Box.createRigidArea(new Dimension(25, 0)));
		buttonPane.add(cancelButton);
		buttonPane.add(Box.createHorizontalGlue());

		//tabs
		final JTabbedPane tabbedPane = new JTabbedPane();
		tabbedPane.addTab("Blocks", blockListPane);
		tabbedPane.addTab("Filters", filterListPane);

		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				JList<String> selectedList;
				if(tabbedPane.getSelectedIndex() == 1){selectedList = filterList;}
				else{selectedList = blockList;}
				if(selectedList.getSelectedValue() != null){
					BuildingBlock child = new BuildingBlock("Null", Main);
					switch(selectedList.getSelectedValue()){
					case "Background":
						d.dispose();
						child = new AddBackground(Main);
						break;
					case "Text":
						d.dispose();
						child = new AddText(Main);
						break;
					case "Image":
						d.dispose();
						child = new AddImage(Main);
						break;
					case "Anime Thumbnail":
						d.dispose();
						child = new AddThumbnail(Main);
						break;
					case "Anime Title":
						d.dispose();
						child = new AddTitle(Main);
						break;
					case "Anime Status":
						d.dispose();
						child = new AddStatus(Main);
						break;
					case "Rotate":
						d.dispose();
						child = new FilterRotate(Main);
						break;
					}
					blocks.addObject(child);
					child.settingsDialog(blockFrame);
				}
			}
		});
		//content
		Container contentPane = d.getContentPane();
		contentPane.add(tabbedPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);

		d.pack();

		d.setLocationRelativeTo(blockFrame);
		d.setVisible(true);
		return d;
	}

	/**
	 * Creates JDialog confirming to delete the currently selected Block
	 * @return the JDialog window
	 */
	public JDialog confirmDeleteBlockDialog(){
		final JDialog d = new JDialog(blockFrame, "Delete Block", true);

		JPanel textPane = new JPanel();
		textPane.setLayout(new BoxLayout(textPane, BoxLayout.PAGE_AXIS));
		textPane.add(Box.createRigidArea(new Dimension(0,5)));
		textPane.add(new JLabel("Are you sure you wish to Delete this Block?"));
		textPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		JButton deleteButton = new JButton("Delete");
		deleteButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				d.dispose();
				blocks.removeCurrentNode();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				d.dispose();
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 3, 3, 3));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(deleteButton);
		buttonPane.add(Box.createRigidArea(new Dimension(25, 0)));
		buttonPane.add(cancelButton);
		buttonPane.add(Box.createHorizontalGlue());

		Container contentPane = d.getContentPane();
		contentPane.add(textPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);

		d.pack();
		d.setLocationRelativeTo(blockFrame);
		d.setVisible(true);
		return d;
	}

	public void populateTree(DynamicTree treePanel) {

		/*AddText p1 = new AddText(Main.sig),p2 = new AddText(Main.sig), p3 = new AddText(Main.sig);


		treePanel.addObject(null, p1);
		treePanel.addObject(null, p2);
		treePanel.addObject(null, p3);

		treePanel.addObject(p1, new AddBackground(Main.sig));
		treePanel.addObject(p1, new AddText(Main.sig));

		treePanel.addObject(p2, new AddText(Main.sig));*/
	}
}
