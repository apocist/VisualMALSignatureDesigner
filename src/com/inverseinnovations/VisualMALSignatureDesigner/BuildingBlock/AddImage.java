package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import com.inverseinnovations.VisualMALSignatureDesigner.Main;

public class AddImage extends BuildingBlock {
	private static final long serialVersionUID = 1L;
	private String filename = "";
	protected int x = 0;
	protected int y = 0;



	public AddImage(Main Main){
		super("Image", Main);
	}
	public AddImage(String name,Main Main){
		super(name, Main);
	}
	/**
	 * @return the filename
	 */
	public String getFilename() {
		return filename;
	}
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}
	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}
	/**
	 * @param filename the filename to set
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}
	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}
	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}
	protected JPanel settingsImage(final JDialog owner){

		//Image
		final JTextField imageField = new JTextField();imageField.setText(getFilename());imageField.setEditable(false);
		final JLabel imageLabel = new JLabel();
		imageLabel.setSize(100, 100);
		imageLabel.setIcon(new ImageIcon(Main.sig.makeImage(100, 100)));
		if(!getFilename().equals("")){
			imageLabel.setIcon(new ImageIcon(Main.sig.filter.perspective(Main.sig.loadImage(getFilename()), 0, 0, 100, 0, 100, 100, 0, 100)));
			//Main.sig.filter.perspective(Main.sig.loadImage(getFilename()), 0, 0, 200, 0, 200, 200, 0, 200);
		}

		final JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "/fonts");
		FileFilter filter = new FileNameExtensionFilter("Image file", new String[] {"jpg", "jpeg","gif","png","bmp"});
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);

		final JButton imageBut = new JButton("...");
		imageBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				File dir = new File(System.getProperty("user.dir") + "/images");
				if(dir.isDirectory() && dir.canRead()){
					fc.setCurrentDirectory(dir);
					int returnVal = fc.showOpenDialog(owner);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						if(file.isFile()){
							if(file.getParentFile().compareTo(dir) == 0){//is in the Fonts folder already
								//TODO add BufferedImage to box
								setFilename(file.getName());
								saveObject();
								imageLabel.setIcon(new ImageIcon(Main.sig.filter.perspective(Main.sig.loadImage(getFilename()), 0, 0, 100, 0, 100, 100, 0, 100)));
								imageField.setText(getFilename());
							}
							else{//not in images folder
								System.out.println("not in folder, copying...");
								try {
									Files.copy(file.toPath(), dir.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
									//TODO add BufferedImage to box
									setFilename(file.getName());
									saveObject();
									imageLabel.setIcon(new ImageIcon(Main.sig.filter.perspective(Main.sig.loadImage(getFilename()), 0, 0, 200, 0, 200, 200, 0, 200)));
									imageField.setText(getFilename());
									System.out.println("Copied.");
								}
								catch (IOException e1) {
									System.out.println("!!COPY FAILED!!");
								}
							}
							Main.ImageWindow.update();
						}
					}
				}
				else{//no access to /fonts
					JOptionPane.showMessageDialog(owner, "ERROR: Images folder does not exist, not installed correctly.");
				}
			}
		});

		JPanel imgPane = new JPanel();
		imgPane.setLayout(new BoxLayout(imgPane, BoxLayout.LINE_AXIS));
		imgPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		imgPane.add(Box.createHorizontalGlue());
		imgPane.add(imageLabel);
		imgPane.add(Box.createRigidArea(new Dimension(10, 0)));
		imgPane.add(imageBut);
		imgPane.add(Box.createHorizontalGlue());

		JPanel totalPane = new JPanel();
		totalPane.setLayout(new BoxLayout(totalPane, BoxLayout.Y_AXIS));
		totalPane.add(Box.createVerticalGlue());
		totalPane.add(imgPane);
		totalPane.add(Box.createRigidArea(new Dimension(0, 3)));
		totalPane.add(imageField);
		totalPane.add(Box.createVerticalGlue());

		return totalPane;
	}
	@Override
	public JDialog settingsDialog(final Frame owner){
		final String oldname = getName();
		final String oldfilename = getFilename();
		final int oldx = getX();
		final int oldy = getY();

		final JDialog d = new JDialog(owner, "Image Settings", true);

		//Name
		JLabel text = new JLabel("Name:");
		final JTextField named = new JTextField();named.setSize(100, 10);named.setText(getName());

		JPanel namePane = new JPanel();
		namePane.setLayout(new BoxLayout(namePane, BoxLayout.LINE_AXIS));
		namePane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		namePane.add(Box.createHorizontalGlue());
		namePane.add(text);
		namePane.add(Box.createRigidArea(new Dimension(10, 0)));
		namePane.add(named);
		namePane.add(Box.createHorizontalGlue());




		//Position
		JLabel xLab = new JLabel("X:");JLabel yLab = new JLabel("Y:");
		final JSpinner xSpinner = new JSpinner();
		xSpinner.setModel(new SpinnerNumberModel(getX(),-9999,9999,1));
		xSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setX((int) xSpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});
		final JSpinner ySpinner = new JSpinner();
		ySpinner.setModel(new SpinnerNumberModel(getY(),-9999,9999,1));
		ySpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setY((int) ySpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});


		JPanel posPane = new JPanel();
		posPane.setLayout(new BoxLayout(posPane, BoxLayout.LINE_AXIS));
		posPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		posPane.add(Box.createHorizontalGlue());
		posPane.add(xLab);
		posPane.add(Box.createRigidArea(new Dimension(10, 0)));
		posPane.add(xSpinner);
		posPane.add(Box.createRigidArea(new Dimension(10, 0)));
		posPane.add(yLab);
		posPane.add(Box.createRigidArea(new Dimension(10, 0)));
		posPane.add(ySpinner);
		posPane.add(Box.createHorizontalGlue());

		//Content
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(Box.createVerticalGlue());
		contentPanel.add(settingsImage(d));
		contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		contentPanel.add(posPane);
		contentPanel.add(Box.createVerticalGlue());

		//OK / Cancel
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//save and close
				if(named.getText() != ""){setName(named.getText());}
				setX((int) xSpinner.getValue());
				setY((int) ySpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
				d.dispose();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setName(oldname);
				setFilename(oldfilename);///XXX do i need to remove?
				setX(oldx);
				setY(oldy);
				saveObject();
				Main.ImageWindow.update();
				d.dispose();
			}
		});

		JPanel buttonPane = new JPanel();
		buttonPane.setLayout(new BoxLayout(buttonPane, BoxLayout.LINE_AXIS));
		buttonPane.setBorder(BorderFactory.createEmptyBorder(10, 3, 3, 3));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(okButton);
		buttonPane.add(Box.createRigidArea(new Dimension(25, 0)));
		buttonPane.add(cancelButton);
		buttonPane.add(Box.createHorizontalGlue());

		Container contentPane = d.getContentPane();
		contentPane.add(namePane, BorderLayout.NORTH);
		contentPane.add(contentPanel, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);

		d.setLocationRelativeTo(owner);
		d.pack();
		d.setVisible(true);
		return d;
	}
	@Override
	public BufferedImage display(BufferedImage image){
		//if(!getFilename().equals("")){
			image = generateImage();
			if(getChildCount() > 0){
				//send this to each child to rerender(filter)
				for(int i = 0; i< getChildCount(); i++){
					if(!((BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject()).isFilter()){
						image = Main.sig.filter.composite(image, ((BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject()).display(image), 0, 0);
					}
					else{//if filter
						image = ((BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject()).display(image);
					}
				}
			}
			//put image on the sig
			image = Main.sig.filter.composite(Main.sig.makeImage(Main.sig.getWidth(), Main.sig.getHeight()), image, getX(), getY());
			//return final image
			return image;
		//}
		//return null;
	}
	protected BufferedImage generateImage(){
		if(!getFilename().equals("")){
			return Main.sig.loadImage(getFilename());
		}
		return Main.sig.makeImage(Main.sig.getWidth()*2, Main.sig.getHeight()*2);
	}
}
