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
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.inverseinnovations.VisualMALSignatureDesigner.Main;

public class FilterShadow extends Filter {
	private static final long serialVersionUID = 1L;
	private int x = 10;
	private int y = -10;
	private int opacity = 50;
	private int radius = 3;

	public FilterShadow(Main Main){
		super("Shadow", Main);
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
	 * @return the opacity
	 */
	public int getOpacity() {
		return opacity;
	}
	/**
	 * @return the radius
	 */
	public int getRadius() {
		return radius;
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
	/**
	 * @param opacity the opacity to set
	 */
	public void setOpacity(int opacity) {
		this.opacity = opacity;
	}
	/**
	 * @param radius the radius to set
	 */
	public void setRadius(int radius) {
		this.radius = radius;
	}

	@Override
	public JDialog settingsDialog(final Frame owner){
		final String oldname = getName();
		final int oldx = getX();
		final int oldy = getY();
		final int oldopacity = getOpacity();
		final int oldradius = getRadius();

		final JDialog d = new JDialog(owner, "Shadow Settings", true);
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

		//x - y
		JLabel xLab = new JLabel("X Offset: ");
		final JSpinner xSpinner = new JSpinner();
		xSpinner.setModel(new SpinnerNumberModel(getX(),-9999,9999,1));
		xSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setX((int)xSpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});
		JLabel yLab = new JLabel("Y Offset: ");
		final JSpinner ySpinner = new JSpinner();
		ySpinner.setModel(new SpinnerNumberModel(getY(),-9999,9999,1));
		ySpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setY((int)ySpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});

		JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.LINE_AXIS));
		topPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		topPane.add(Box.createHorizontalGlue());
		topPane.add(xLab);
		topPane.add(Box.createRigidArea(new Dimension(5, 0)));
		topPane.add(xSpinner);
		topPane.add(Box.createRigidArea(new Dimension(15, 0)));
		topPane.add(yLab);
		topPane.add(Box.createRigidArea(new Dimension(5, 0)));
		topPane.add(ySpinner);
		topPane.add(Box.createHorizontalGlue());


		//Radius / Opacity
		JLabel radLab = new JLabel("Radius: ");
		final JSpinner radSpinner = new JSpinner();
		radSpinner.setModel(new SpinnerNumberModel(getRadius(),0,9999,1));
		radSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setRadius((int)radSpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});

		JLabel opLab = new JLabel("Opacity:");
		final JSpinner opSpinner = new JSpinner();
		opSpinner.setModel(new SpinnerNumberModel(getOpacity(),0,100,1));
		//mySpinner.setEditor(new JSpinner.NumberEditor(mySpinner,"##.#"));
		final JSlider opSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, getOpacity());opSlider.setSize(100, 10);
		opSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				int ang = source.getValue();
				opSpinner.setValue(ang);
				//if (!source.getValueIsAdjusting()) {
					setOpacity((int) opSpinner.getValue());
					saveObject();
					Main.ImageWindow.update();
				//}
			}
		});
		opSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSpinner source = (JSpinner)e.getSource();
				int ang = (int) source.getValue();
				opSlider.setValue(ang);
			}
		});

		JPanel opPane = new JPanel();
		opPane.setLayout(new BoxLayout(opPane, BoxLayout.LINE_AXIS));
		opPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		opPane.add(Box.createHorizontalGlue());
		opPane.add(radLab);
		opPane.add(Box.createRigidArea(new Dimension(5, 0)));
		opPane.add(radSpinner);
		opPane.add(Box.createRigidArea(new Dimension(10, 0)));
		opPane.add(opLab);
		opPane.add(Box.createRigidArea(new Dimension(5, 0)));
		opPane.add(opSlider);
		opPane.add(Box.createRigidArea(new Dimension(10, 0)));
		opPane.add(opSpinner);
		opPane.add(Box.createHorizontalGlue());

		//Content
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(Box.createVerticalGlue());
		contentPanel.add(topPane);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		contentPanel.add(opPane);
		contentPanel.add(Box.createVerticalGlue());

		//OK / Cancel
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//save and close
				if(named.getText() != ""){setName(named.getText());}
				setX((int)xSpinner.getValue());
				setY((int)ySpinner.getValue());
				setRadius((int)radSpinner.getValue());
				setOpacity((int) opSpinner.getValue());
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
				setX(oldx);
				setY(oldy);
				setRadius(oldradius);
				setOpacity(oldopacity);
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
	protected BufferedImage generateImage(BufferedImage image){
		return Main.sig.filter.shadow(image, getRadius(), getX(), getY(), getOpacity());
	}
	@Override
	public String generateScript(String filteronly){
		return "filter.shadow("+filteronly+", "+getRadius()+", "+getX()+", "+getY()+", "+getOpacity()+")";
	}
}
