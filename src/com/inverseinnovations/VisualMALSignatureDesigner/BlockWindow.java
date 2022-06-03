package com.inverseinnovations.VisualMALSignatureDesigner;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.WindowConstants;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock.*;
import com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock.Filter.*;

public class BlockWindow {
	public final Main Main;

	public DynamicTree blocks;
	private JFrame blockFrame;
	private BuildingBlock blockClipboard;

	public BlockWindow(final Main Main){
		this.Main = Main;
		create();
	}

	public void create(){
		SwingUtilities.invokeLater(new Runnable(){
			public void run(){
				blockFrame = new JFrame("Tree Demo");
				blockFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
				blocks = new DynamicTree(Main);

				//Menu
				JMenuBar menuBar = new JMenuBar();//Create the menu bar.

				//File menu
				JMenu menu = new JMenu("File");//Build the first menu.

				//a group of JMenuItems
				JMenuItem menuItem = new JMenuItem("New");
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						int dialogResult = JOptionPane.showConfirmDialog (null, "Clear the current Signature? (Might not be saved)","Warning",JOptionPane.YES_NO_OPTION);
						if(dialogResult == JOptionPane.YES_OPTION){
							blocks.clear();
						}
					}
				});
				menu.add(menuItem);
				menuItem = new JMenuItem("Open");
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						final JFileChooser fc = new JFileChooser(System.getProperty("user.dir"));
						FileFilter filter = new FileNameExtensionFilter("VisualMSD file", new String[] {"vmsd"});
						fc.addChoosableFileFilter(filter);
						fc.setFileFilter(filter);

						File dir = new File(System.getProperty("user.dir"));
						if(dir.isDirectory() && dir.canRead()){
							fc.setCurrentDirectory(dir);
							int returnVal = fc.showOpenDialog(blockFrame);
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								File file = fc.getSelectedFile();
								if(file.isFile()){
									//load file
									BuildingBlock block = null;
									try {
										block = blocks.bytesToNode(Files.readAllBytes(Paths.get(file.getPath())));
									} catch (IOException e1) {JOptionPane.showMessageDialog(blockFrame, "ERROR: Cannot load file!");}
									if(block != null){
										blocks.setRootNode(block);
									}
								}
							}
						}
					}
				});
				menu.add(menuItem);
				menuItem = new JMenuItem("Save");
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						final JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "sig.vmsd");
						FileFilter filter = new FileNameExtensionFilter("VisualMSD file", new String[] {"vmsd"});
						fc.setSelectedFile(new File("sig.vmsd"));
						fc.addChoosableFileFilter(filter);
						fc.setFileFilter(filter);

						File dir = new File(System.getProperty("user.dir"));
						if(dir.isDirectory() && dir.canWrite()){
							fc.setCurrentDirectory(dir);
							int returnVal = fc.showSaveDialog(blockFrame);
							if (returnVal == JFileChooser.APPROVE_OPTION) {
								File file = fc.getSelectedFile();
								BuildingBlock block = blocks.getRootNode();
								if(file.exists()){
									if(file.canWrite()){
										int dialogResult = JOptionPane.showConfirmDialog (null, "Overwrite the existing file?","Warning",JOptionPane.YES_NO_OPTION);
											if(dialogResult == JOptionPane.YES_OPTION){
											try {
												Files.write(Paths.get(file.getPath()), blocks.nodeToBytes(block));
												JOptionPane.showMessageDialog(null, "Saved");
											} catch (IOException e1) {JOptionPane.showMessageDialog(null, "ERROR: Cannot save file!");}
										}
									}else{JOptionPane.showMessageDialog(null, "File is not rewritable");}
								}
								else{
									try {
										Files.write(Paths.get(file.getPath()), blocks.nodeToBytes(block));
										JOptionPane.showMessageDialog(null, "Signature Saved");
									} catch (IOException e1) {JOptionPane.showMessageDialog(blockFrame, "ERROR: Cannot save file!");}
								}
							}
						}else{JOptionPane.showMessageDialog(null, "Dir is not writable");}
					}
				});
				menu.add(menuItem);
				menuItem = new JMenuItem("Generate Script");
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						final JDialog d = new JDialog(blockFrame, "MSD Script", true);

						JTextArea textArea = new JTextArea(20, 100);
						JScrollPane textscrollPane = new JScrollPane(textArea);
						textArea.setEditable(false);
						final String script = blocks.getRootNode().createScript(null)+"sig.saveSignature(\"image.png\");";
						textArea.setText(script);
						//save button
						JButton saveBut = new JButton("Save to sig.ini");
						saveBut.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e){
								//save script
								PrintWriter writer;
								try {
									writer = new PrintWriter("sig.ini", "UTF-8");
									writer.print(script);
									writer.close();
								}
								catch (Exception e2) {
								}
								finally{

								}
							}
						});

						d.getContentPane().add(saveBut, BorderLayout.NORTH);
						d.getContentPane().add(textscrollPane, BorderLayout.CENTER);

						d.pack();
						d.setVisible(true);
					}
				});
				menu.add(menuItem);

				menuBar.add(menu);

				//Edit menu
				menu = new JMenu("Edit");//Build the first menu.

				//a group of JMenuItems
				menuItem = new JMenuItem("Cut");
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,KeyEvent.ALT_DOWN_MASK));
				menuItem.setMnemonic(KeyEvent.VK_X);
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(blocks.getCurrentNode() != null){
							if(blocks.getCurrentNode() != blocks.getRootNode()){
								blockClipboard = blocks.cloneNode(blocks.getCurrentNode());
								blocks.removeCurrentNode();
								Main.ImageWindow.update();
							}else{blockClipboard = null;}
						}else{blockClipboard = null;}
					}
				});
				menu.add(menuItem);
				menuItem = new JMenuItem("Copy");
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,KeyEvent.ALT_DOWN_MASK));
				menuItem.setMnemonic(KeyEvent.VK_C);
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(blocks.getCurrentNode() != null){
							if(blocks.getCurrentNode() != blocks.getRootNode()){
								blockClipboard = blocks.cloneNode(blocks.getCurrentNode());
							}else{blockClipboard = null;}
						}else{blockClipboard = null;}
					}
				});
				menu.add(menuItem);
				menuItem = new JMenuItem("Paste");
				menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V,KeyEvent.ALT_DOWN_MASK));
				menuItem.setMnemonic(KeyEvent.VK_V);
				menuItem.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(blockClipboard != null){
							blocks.addObject(blocks.cloneNode(blockClipboard));
							Main.ImageWindow.update();
						}
					}
				});
				menu.add(menuItem);

				menuBar.add(menu);

				//divides item to sides
				menuBar.add(Box.createGlue());//divides item to sides

				JButton menuBut = new JButton(new ImageIcon(Main.class.getResource("resources/addBut.png")));
				menuBut.setMargin(new Insets(0,0,0,0));menuBut.setBorder(null);menuBut.setBorderPainted(false);menuBut.setContentAreaFilled(false);menuBut.setPreferredSize(new Dimension(16,16));
				menuBut.setToolTipText("Add a new Block/Filter");
				menuBut.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(blocks.getCurrentNode() == null){
							createBlockSelectionDialog();
						}
						else if(blocks.getCurrentNode().ISPARENTABLE){
							createBlockSelectionDialog();
						}
					}
				});
				menuBar.add(menuBut);

				menuBut = new JButton(new ImageIcon(Main.class.getResource("resources/editBut.png")));
				menuBut.setMargin(new Insets(0,0,0,0));menuBut.setBorder(null);menuBut.setBorderPainted(false);menuBut.setContentAreaFilled(false);menuBut.setPreferredSize(new Dimension(16,16));
				menuBut.setToolTipText("Edit the selected Block/Filter");
				menuBut.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(blocks.getCurrentNode() == null){
							blocks.rootNode.settingsDialog(blockFrame);
						}
						else{
							blocks.getCurrentNode().settingsDialog(blockFrame);
						}
					}
				});
				menuBar.add(menuBut);

				menuBut = new JButton(new ImageIcon(Main.class.getResource("resources/upBut.png")));
				menuBut.setMargin(new Insets(0,0,0,0));menuBut.setBorder(null);menuBut.setBorderPainted(false);menuBut.setContentAreaFilled(false);menuBut.setPreferredSize(new Dimension(16,16));
				menuBut.setToolTipText("Move the Block/Filter up");
				menuBut.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						blocks.moveNodeUp();
					}
				});
				menuBar.add(menuBut);

				menuBut = new JButton(new ImageIcon(Main.class.getResource("resources/downBut.png")));
				menuBut.setMargin(new Insets(0,0,0,0));menuBut.setBorder(null);menuBut.setBorderPainted(false);menuBut.setContentAreaFilled(false);menuBut.setPreferredSize(new Dimension(16,16));
				menuBut.setToolTipText("Move the Block/Filter down");
				menuBut.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						blocks.moveNodeDown();
					}
				});
				menuBar.add(menuBut);

				menuBut = new JButton(new ImageIcon(Main.class.getResource("resources/trashBut.png")));
				menuBut.setMargin(new Insets(0,0,0,0));menuBut.setBorder(null);menuBut.setBorderPainted(false);menuBut.setContentAreaFilled(false);menuBut.setPreferredSize(new Dimension(16,16));
				menuBut.setToolTipText("Delete the selected Block/Filter");
				menuBut.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						if(blocks.getCurrentNode() != null){
							if(blocks.getCurrentNode() != blocks.getRootNode()){
								int dialogResult = JOptionPane.showConfirmDialog (null, "Delete the selected Block?","Warning",JOptionPane.YES_NO_OPTION);
								if(dialogResult == JOptionPane.YES_OPTION){
									blocks.removeCurrentNode();
									Main.ImageWindow.update();
								}
							}
						}
					}
				});
				menuBar.add(menuBut);


				blockFrame.setJMenuBar(menuBar);

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

		//which has the logest name...?
		BuildingBlock longestBlock = new AddThumbnail(Main);

		//show block selection panel
		BuildingBlock[] blockTypes = {
				new AddBackground(Main),
				new AddText(Main),
				new AddTitle(Main),
				new AddStatus(Main),
				new AddEpisodes(Main),
				new AddTime(Main),
				new AddImage(Main),
				new AddThumbnail(Main),
				new AddEmptyImage(Main)
		};
		final JList<BuildingBlock> blockList = new JList<BuildingBlock>(blockTypes); //data has type Object[]
		blockList.setPrototypeCellValue(longestBlock);
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
		BuildingBlock[] filterTypes = {
				new FilterBlurGaussian(Main),
				new FilterBlurSimple(Main),
				new FilterBlurVariable(Main),
				new FilterChrome(Main),
				new FilterEmboss(Main),
				new FilterGlowInner(Main),
				new FilterOpacity(Main),
				new FilterPerspective(Main),
				new FilterResize(Main),
				new FilterRotate(Main),
				new FilterShadow(Main),
				new FilterShadowSimple(Main),
				new FilterSmear(Main),
				new FilterSolarize(Main),
				new FilterSparkle(Main),
				new FilterSphere(Main),
				new FilterUnsharp(Main)
		};
		final JList<BuildingBlock> filterList = new JList<BuildingBlock>(filterTypes); //data has type Object[]
		filterList.setPrototypeCellValue(longestBlock);
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
				JList<BuildingBlock> selectedList;
				if(tabbedPane.getSelectedIndex() == 1){selectedList = filterList;}
				else{selectedList = blockList;}
				if(selectedList.getSelectedValue() != null){
					BuildingBlock child = selectedList.getSelectedValue();
					blocks.addObject(child);
					d.dispose();
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

}
