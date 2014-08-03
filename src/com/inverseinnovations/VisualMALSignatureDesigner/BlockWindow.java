package com.inverseinnovations.VisualMALSignatureDesigner;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.GridLayout;
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
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock.*;

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
				blockFrame.setLocationRelativeTo(null);
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

		//show selection panel
		String[] types = {"Background","Text","Anime Title","Anime Status"};
		final JList<String> list = new JList<String>(types); //data has type Object[]
		list.setPrototypeCellValue(" Anime Status ");
		list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		list.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		list.setVisibleRowCount(-1);
		JScrollPane listScroller = new JScrollPane(list);
		listScroller.setPreferredSize(new Dimension(250, 80));
		listScroller.setAlignmentX(JScrollPane.LEFT_ALIGNMENT);

		JPanel listPane = new JPanel();
		listPane.setLayout(new BoxLayout(listPane, BoxLayout.PAGE_AXIS));
		listPane.add(Box.createRigidArea(new Dimension(0,5)));
		listPane.add(listScroller);
		listPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(list.getSelectedValue() != null){
					BuildingBlock child = new BuildingBlock("Null", Main);
					switch(list.getSelectedValue()){
					case "Background":
						d.dispose();
						child = new AddBackground(Main);
						break;
					case "Text":
						d.dispose();
						child = new AddText(Main);

						break;
					case "Anime Title":
						d.dispose();
						child = new AddTitle(Main);
						break;
					case "Anime Status":
						d.dispose();
						child = new AddStatus(Main);
						break;
					}
					blocks.addObject(child);
					child.settingsDialog(blockFrame);
				}
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
		buttonPane.add(okButton);
		buttonPane.add(Box.createRigidArea(new Dimension(25, 0)));
		buttonPane.add(cancelButton);
		buttonPane.add(Box.createHorizontalGlue());

		Container contentPane = d.getContentPane();
		contentPane.add(listPane, BorderLayout.CENTER);
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
