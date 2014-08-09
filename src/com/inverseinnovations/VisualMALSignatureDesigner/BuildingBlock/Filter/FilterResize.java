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

public class FilterResize extends Filter {
	private static final long serialVersionUID = 1L;
	private int width = 100;
	private int height = 100;

	public FilterResize(Main Main){
		super("Resize", Main);
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}
	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}
	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}
	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public JDialog settingsDialog(final Frame owner){
		final String oldname = getName();
		final int oldwidth = getWidth();
		final int oldheight = getHeight();

		final JDialog d = new JDialog(owner, "Resize Settings", true);
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

		//width height
		JLabel widthLab = new JLabel("Width: ");
		final JSpinner widthSpinner = new JSpinner();
		widthSpinner.setModel(new SpinnerNumberModel(getWidth(),1,9999,1));
		widthSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setWidth((int)widthSpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});
		JLabel heightLab = new JLabel("Height: ");
		final JSpinner heightSpinner = new JSpinner();
		heightSpinner.setModel(new SpinnerNumberModel(getHeight(),1,9999,1));
		heightSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setHeight((int)heightSpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});

		JPanel topPane = new JPanel();
		topPane.setLayout(new BoxLayout(topPane, BoxLayout.LINE_AXIS));
		topPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		topPane.add(Box.createHorizontalGlue());
		topPane.add(widthLab);
		topPane.add(Box.createRigidArea(new Dimension(5, 0)));
		topPane.add(widthSpinner);
		topPane.add(Box.createRigidArea(new Dimension(15, 0)));
		topPane.add(heightLab);
		topPane.add(Box.createRigidArea(new Dimension(5, 0)));
		topPane.add(heightSpinner);
		topPane.add(Box.createHorizontalGlue());


		//Content
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(Box.createVerticalGlue());
		contentPanel.add(topPane);
		contentPanel.add(Box.createVerticalGlue());

		//OK / Cancel
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//save and close
				if(named.getText() != ""){setName(named.getText());}
				setWidth((int)widthSpinner.getValue());
				setHeight((int)heightSpinner.getValue());
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
				setWidth(oldwidth);
				setHeight(oldheight);
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
		return Main.sig.filter.resize(image,getWidth(), getHeight());
	}
	@Override
	public String generateScript(String filteronly){
		return "filter.resize("+filteronly+", "+getWidth()+", "+getHeight()+")";
	}
}
