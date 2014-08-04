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

public class FilterPerspective extends Filter {
	private static final long serialVersionUID = 1L;
	private float x0 = 0;	private float y0 = 0;
	private float x1 = 100;	private float y1 = 0;
	private float x2 = 100;	private float y2 = 100;
	private float x3 = 0;	private float y3 = 100;

	public FilterPerspective(Main Main){
		super("Perspective", Main);
	}


	/**
	 * @return the x0
	 */
	public float getX0() {
		return x0;
	}


	/**
	 * @return the y0
	 */
	public float getY0() {
		return y0;
	}


	/**
	 * @return the x1
	 */
	public float getX1() {
		return x1;
	}


	/**
	 * @return the y1
	 */
	public float getY1() {
		return y1;
	}


	/**
	 * @return the x2
	 */
	public float getX2() {
		return x2;
	}


	/**
	 * @return the y2
	 */
	public float getY2() {
		return y2;
	}


	/**
	 * @return the x3
	 */
	public float getX3() {
		return x3;
	}


	/**
	 * @return the y3
	 */
	public float getY3() {
		return y3;
	}


	/**
	 * @param x0 and y0 to set
	 */
	public void setTopLeft(float x0, float y0) {
		this.x0 = x0;
		this.y0 = y0;
	}
	/**
	 * @param x1 and y1 to set
	 */
	public void setTopRight(float x1, float y1) {
		this.x1 = x1;
		this.y1 = y1;
	}
	/**
	 * @param x2 and y2 to set
	 */
	public void setBottomRight(float x2, float y2) {
		this.x2 = x2;
		this.y2 = y2;
	}
	/**
	 * @param x3 and y3 to set
	 */
	public void setBottomLeft(float x3, float y3) {
		this.x3 = x3;
		this.y3 = y3;
	}

	@Override
	public JDialog settingsDialog(final Frame owner){
		final String oldname = getName();
		final float oldx0 = getX0();	final float oldy0 = getY0();
		final float oldx1 = getX1();	final float oldy1 = getY1();
		final float oldx2 = getX2();	final float oldy2 = getY2();
		final float oldx3 = getX3();	final float oldy3 = getY3();

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

		//locations
		JLabel topleftLab = new JLabel("Top Left: ");
		final JSpinner x0Spinner = new JSpinner();
		x0Spinner.setModel(new SpinnerNumberModel(getX0(),0,9999,1));
		x0Spinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setTopLeft((float)x0Spinner.getValue(),getY0());
				saveObject();
				Main.ImageWindow.update();
			}
		});
		final JSpinner y0Spinner = new JSpinner();
		y0Spinner.setModel(new SpinnerNumberModel(getY0(),0,9999,1));
		y0Spinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setTopLeft(getX0(),(float)y0Spinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});

		JLabel toprightLab = new JLabel("Top Right: ");
		final JSpinner x1Spinner = new JSpinner();
		x1Spinner.setModel(new SpinnerNumberModel(getX1(),0,9999,1));
		x1Spinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setTopRight((float)x1Spinner.getValue(),getY1());
				saveObject();
				Main.ImageWindow.update();
			}
		});
		final JSpinner y1Spinner = new JSpinner();
		y1Spinner.setModel(new SpinnerNumberModel(getY1(),0,9999,1));
		y1Spinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setTopRight(getX1(),(float)y1Spinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});



		JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.LINE_AXIS));
		topPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		topPane.add(Box.createHorizontalGlue());
		topPane.add(topleftLab);
		topPane.add(Box.createRigidArea(new Dimension(5, 0)));
		topPane.add(x0Spinner);
		topPane.add(y0Spinner);
		topPane.add(Box.createRigidArea(new Dimension(15, 0)));
		topPane.add(toprightLab);
		topPane.add(Box.createRigidArea(new Dimension(10, 0)));
		topPane.add(x1Spinner);
		topPane.add(y1Spinner);
		topPane.add(Box.createHorizontalGlue());

		//TODO add bottom

		//Content
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(Box.createVerticalGlue());
		contentPanel.add(topPane);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		contentPanel.add(bottomPane);
		contentPanel.add(Box.createVerticalGlue());

		//OK / Cancel
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//save and close
				if(named.getText() != ""){setName(named.getText());}
				setTopLeft((float)x0Spinner.getValue(),(float)y0Spinner.getValue());
				setTopRight((float)x1Spinner.getValue(),(float)y1Spinner.getValue());
				//XXX add bottom
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
		image = Main.sig.filter.perspective(image, getX0(), getY0(), getX1(), getY1(), getX2(), getY2(), getX3(), getY3());
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
