package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock.Filter;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.inverseinnovations.VisualMALSignatureDesigner.Main;
import com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock.BuildingBlock;

public class FilterRotate extends Filter {
	private static final long serialVersionUID = 1L;
	public boolean ISFILTER = true;
	private double angle = 0;
	private double anchorX = 0;
	private double anchorY = 0;
	private String anchor = "topleft";
	private boolean anchorRel = true;

 	public FilterRotate(Main Main){
		super("Rotate", Main);
	}

	/**
	 * @return the anchorRel
	 */
	public boolean isAnchorRel() {
		return anchorRel;
	}

	/**
	 * @param anchorRel the anchorRel to set
	 */
	public void setAnchorRel(boolean anchorRel) {
		this.anchorRel = anchorRel;
	}

	/**
	 * @return the anchor
	 */
	public String getAnchor() {
		return anchor;
	}

	/**
	 * @param anchor the anchor to set
	 */
	public void setAnchor(String anchor) {
		this.anchor = anchor;
	}

	/**
	 * @return the angle
	 */
	public double getAngle() {
		return angle;
	}

	/**
	 * @return the anchorX
	 */
	public double getAnchorX() {
		return anchorX;
	}

	/**
	 * @return the anchorY
	 */
	public double getAnchorY() {
		return anchorY;
	}

	/**
	 * @param angle the angle to set
	 */
	public void setAngle(double angle) {
		this.angle = angle;
	}

	/**
	 * @param anchorX the anchorX to set
	 */
	public void setAnchorX(double anchorX) {
		this.anchorX = anchorX;
	}

	/**
	 * @param anchorY the anchorY to set
	 */
	public void setAnchorY(double anchorY) {
		this.anchorY = anchorY;
	}

	@Override
	public JDialog settingsDialog(final Frame owner){
		final String oldname = getName();
		final double oldangle = getAngle();
		final double oldanchorx = getAnchorX();
		final double oldanchory = getAnchorY();
		final String oldanchor = getAnchor();
		final boolean oldrel = isAnchorRel();

		final JDialog d = new JDialog(owner, "Rotate Settings", true);

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

		//Angle
		JLabel angleLab = new JLabel("Angle:");
		final JSpinner angleSpinner = new JSpinner();
		angleSpinner.setModel(new SpinnerNumberModel(getAngle(),0,359,1));
		//mySpinner.setEditor(new JSpinner.NumberEditor(mySpinner,"##.#"));
		final JSlider angleSlider = new JSlider(JSlider.HORIZONTAL, 0, 359, (int) getAngle());angleSlider.setSize(100, 10);
		angleSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				int ang = source.getValue();
				angleSpinner.setValue(ang);
				//if (!source.getValueIsAdjusting()) {
					setAngle((int) angleSpinner.getValue());
					saveObject();
					Main.ImageWindow.update();
				//}
			}
		});
		angleSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSpinner source = (JSpinner)e.getSource();
				int ang = (int) source.getValue();
				angleSlider.setValue(ang);
			}
		});

		JPanel anglePane = new JPanel();
		anglePane.setLayout(new BoxLayout(anglePane, BoxLayout.LINE_AXIS));
		anglePane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		anglePane.add(Box.createHorizontalGlue());
		anglePane.add(angleLab);
		anglePane.add(Box.createRigidArea(new Dimension(10, 0)));
		anglePane.add(angleSlider);
		anglePane.add(Box.createRigidArea(new Dimension(10, 0)));
		anglePane.add(angleSpinner);
		anglePane.add(Box.createHorizontalGlue());

		//Anchors
		JLabel anchorLab = new JLabel("ANCHOR  ");
		final JLabel xLab = new JLabel("X:");final JLabel yLab = new JLabel("Y:");
		final JSpinner xSpinner = new JSpinner();
		xSpinner.setModel(new SpinnerNumberModel(getAnchorX(),0,9999,1));
		xSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setAnchorX((double) xSpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});
		final JSpinner ySpinner = new JSpinner();
		ySpinner.setModel(new SpinnerNumberModel(getAnchorY(),0,9999,1));
		ySpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setAnchorY((double) ySpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});

		String[] anchorStrings = { "topleft", "topright", "bottomleft","bottomright","center","left","right","top","bottom"};
		final JComboBox<String> anchorBox = new JComboBox<String>(anchorStrings);anchorBox.setEditable(false);
		anchorBox.setSelectedItem(getAnchor());
		anchorBox.addItemListener(new ItemListener(){
	        public void itemStateChanged(ItemEvent e){
	        	setAnchor((String) anchorBox.getSelectedItem());
				saveObject();
				Main.ImageWindow.update();
	        }
	    });

		final JRadioButton relButton = new JRadioButton("Relative");
	    relButton.setSelected(isAnchorRel());

		final JRadioButton absoButton = new JRadioButton("Absolute");
		absoButton.setSelected(!isAnchorRel());

		relButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(relButton.isSelected()){
					//make absoulte disappear
					absoButton.setSelected(false);
					xLab.setVisible(false);
					yLab.setVisible(false);
					xSpinner.setVisible(false);
					ySpinner.setVisible(false);

					//make drop down visiable
					anchorBox.setVisible(true);
					setAnchorRel(true);
					saveObject();
					Main.ImageWindow.update();
				}
			}
		});
		absoButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				if(absoButton.isSelected()){
					//make drop down disappear
					relButton.setSelected(false);
					anchorBox.setVisible(false);

					//make absoulte visible
					xLab.setVisible(true);
					yLab.setVisible(true);
					xSpinner.setVisible(true);
					ySpinner.setVisible(true);
					setAnchorRel(false);
					saveObject();
					Main.ImageWindow.update();
				}
			}
		});
		xLab.setVisible(!isAnchorRel());yLab.setVisible(!isAnchorRel());
		xSpinner.setVisible(!isAnchorRel());
		ySpinner.setVisible(!isAnchorRel());
		anchorBox.setVisible(isAnchorRel());

		JPanel radioPane = new JPanel();
		radioPane.setLayout(new BoxLayout(radioPane, BoxLayout.LINE_AXIS));
		radioPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		radioPane.add(Box.createHorizontalGlue());
		radioPane.add(relButton);
		radioPane.add(Box.createRigidArea(new Dimension(5, 0)));
		radioPane.add(absoButton);
		radioPane.add(Box.createHorizontalGlue());

		JPanel posPane = new JPanel();
		posPane.setLayout(new BoxLayout(posPane, BoxLayout.LINE_AXIS));
		posPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		posPane.add(Box.createHorizontalGlue());
		posPane.add(anchorLab);
		posPane.add(Box.createRigidArea(new Dimension(5, 0)));
		posPane.add(anchorBox);
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
		contentPanel.add(anglePane);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		contentPanel.add(radioPane);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		contentPanel.add(posPane);
		contentPanel.add(Box.createVerticalGlue());

		//OK / Cancel
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//save and close
				if(named.getText() != ""){setName(named.getText());}
				setAnchorX((double) xSpinner.getValue());
				setAnchorY((double) ySpinner.getValue());
				setAnchor((String) anchorBox.getSelectedItem());
				setAnchorRel(relButton.isSelected());
				saveObject();
				Main.ImageWindow.update();
				d.dispose();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setName(oldname);
				setAngle(oldangle);
				setAnchorX(oldanchorx);
				setAnchorY(oldanchory);
				setAnchor(oldanchor);
				setAnchorRel(oldrel);
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
		if(isAnchorRel()){
			image = Main.sig.filter.rotate(image, getAngle(), getAnchor());
		}
		else{
			image = Main.sig.filter.rotate(image, getAngle(), getAnchorX(), getAnchorY());
		}
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
		return image;
	}
}
