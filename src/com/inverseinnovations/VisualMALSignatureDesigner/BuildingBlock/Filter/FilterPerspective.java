package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock.Filter;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.inverseinnovations.VisualMALSignatureDesigner.Main;

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
	public void setTopLeft(double x0, double y0) {
		this.x0 = (float)x0;
		this.y0 = (float)y0;
	}
	/**
	 * @param x1 and y1 to set
	 */
	public void setTopRight(double x1, double y1) {
		this.x1 = (float)x1;
		this.y1 = (float)y1;
	}
	/**
	 * @param x2 and y2 to set
	 */
	public void setBottomRight(double x2, double y2) {
		this.x2 = (float)x2;
		this.y2 = (float)y2;
	}
	/**
	 * @param x3 and y3 to set
	 */
	public void setBottomLeft(double x3, double y3) {
		this.x3 = (float)x3;
		this.y3 = (float)y3;
	}

	@Override
	public JDialog settingsDialog(final Frame owner){
		final String oldname = getName();
		final float oldx0 = getX0();	final float oldy0 = getY0();
		final float oldx1 = getX1();	final float oldy1 = getY1();
		final float oldx2 = getX2();	final float oldy2 = getY2();
		final float oldx3 = getX3();	final float oldy3 = getY3();

		final JDialog d = new JDialog(owner, "Rotate Settings", true);
		Main.ImageWindow.update();

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
		x0Spinner.setModel(new SpinnerNumberModel(getX0(),-9999,9999,1));
		x0Spinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setTopLeft((double)x0Spinner.getValue(),getY0());
				saveObject();
				Main.ImageWindow.update();
			}
		});
		final JSpinner y0Spinner = new JSpinner();
		y0Spinner.setModel(new SpinnerNumberModel(getY0(),-9999,9999,1));
		y0Spinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setTopLeft(getX0(),(double)y0Spinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});

		JLabel toprightLab = new JLabel("Top Right: ");
		final JSpinner x1Spinner = new JSpinner();
		x1Spinner.setModel(new SpinnerNumberModel(getX1(),0,9999,1));
		x1Spinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setTopRight((double)x1Spinner.getValue(),getY1());
				saveObject();
				Main.ImageWindow.update();
			}
		});
		final JSpinner y1Spinner = new JSpinner();
		y1Spinner.setModel(new SpinnerNumberModel(getY1(),0,9999,1));
		y1Spinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setTopRight(getX1(),(double)y1Spinner.getValue());
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

		JLabel bottomleftLab = new JLabel("Bottom Left: ");
		final JSpinner x3Spinner = new JSpinner();
		x3Spinner.setModel(new SpinnerNumberModel(getX3(),0,9999,1));
		x3Spinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setBottomLeft((double)x3Spinner.getValue(),getY3());
				saveObject();
				Main.ImageWindow.update();
			}
		});
		final JSpinner y3Spinner = new JSpinner();
		y3Spinner.setModel(new SpinnerNumberModel(getY3(),0,9999,1));
		y3Spinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setBottomLeft(getX3(),(double)y3Spinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});

		JLabel bottomrightLab = new JLabel("Bottom Right: ");
		final JSpinner x2Spinner = new JSpinner();
		x2Spinner.setModel(new SpinnerNumberModel(getX2(),0,9999,1));
		x2Spinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setBottomRight((double)x2Spinner.getValue(),getY2());
				saveObject();
				Main.ImageWindow.update();
			}
		});
		final JSpinner y2Spinner = new JSpinner();
		y2Spinner.setModel(new SpinnerNumberModel(getY2(),0,9999,1));
		y2Spinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setBottomRight(getX2(),(double)y2Spinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});



		JPanel bottomPane = new JPanel();
		bottomPane.setLayout(new BoxLayout(bottomPane, BoxLayout.LINE_AXIS));
		bottomPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		bottomPane.add(Box.createHorizontalGlue());
		bottomPane.add(bottomleftLab);
		bottomPane.add(Box.createRigidArea(new Dimension(5, 0)));
		bottomPane.add(x3Spinner);
		bottomPane.add(y3Spinner);
		bottomPane.add(Box.createRigidArea(new Dimension(15, 0)));
		bottomPane.add(bottomrightLab);
		bottomPane.add(Box.createRigidArea(new Dimension(10, 0)));
		bottomPane.add(x2Spinner);
		bottomPane.add(y2Spinner);
		topPane.add(Box.createHorizontalGlue());

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
				setTopLeft((double)x0Spinner.getValue(),(double)y0Spinner.getValue());
				setTopRight((double)x1Spinner.getValue(),(double)y1Spinner.getValue());
				setBottomRight((double)x2Spinner.getValue(),(double)y2Spinner.getValue());
				setBottomLeft((double)x3Spinner.getValue(),(double)y3Spinner.getValue());
				saveObject();
				Main.ImageWindow.update();
				Main.BlockWindow.blocks.reload();
				d.dispose();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				setName(oldname);
				setTopLeft(oldx0,oldy0);
				setTopRight(oldx1,oldy1);
				setBottomRight(oldx2,oldy2);
				setBottomLeft(oldx3,oldy3);
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

	/*
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
	}*/
	@Override
	protected BufferedImage generateImage(BufferedImage image){
		return Main.sig.filter.perspective(image, getX0(), getY0(), getX1(), getY1(), getX2(), getY2(), getX3(), getY3());
	}
	@Override
	public String generateScript(String filteronly){
		return "filter.perspective("+filteronly+", "+(int)getX0()+", "+(int)getY0()+", "+(int)getX1()+", "+(int)getY1()+", "+(int)getX2()+", "+(int)getY2()+", "+(int)getX3()+", "+(int)getY3()+")";
	}
}
