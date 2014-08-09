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

public class FilterBlurVariable extends Filter {
	private static final long serialVersionUID = 1L;
	private int hRad = 1;
	private int vRad = 1;
	private int iterations = 1;

	public FilterBlurVariable(Main Main){
		super("Blur Variable", Main);
	}
	/**
	 * @return the hRad
	 */
	public int gethRad() {
		return hRad;
	}
	/**
	 * @return the vRad
	 */
	public int getvRad() {
		return vRad;
	}
	/**
	 * @return the iterations
	 */
	public int getIterations() {
		return iterations;
	}
	/**
	 * @param hRad the hRad to set
	 */
	public void sethRad(int hRad) {
		this.hRad = hRad;
	}
	/**
	 * @param vRad the vRad to set
	 */
	public void setvRad(int vRad) {
		this.vRad = vRad;
	}
	/**
	 * @param iterations the iterations to set
	 */
	public void setIterations(int iterations) {
		this.iterations = iterations;
	}
	@Override
	public JDialog settingsDialog(final Frame owner){
		final String oldname = getName();
		final int oldhRad = gethRad();
		final int oldvRad = getvRad();
		final int olditerations = getIterations();

		final JDialog d = new JDialog(owner, "Blur Variable Settings", true);
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
		JLabel xLab = new JLabel("Horizontal: ");
		final JSpinner xSpinner = new JSpinner();
		xSpinner.setModel(new SpinnerNumberModel(gethRad(),0,100,1));
		xSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				sethRad((int)xSpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});
		JLabel yLab = new JLabel("Vertical: ");
		final JSpinner ySpinner = new JSpinner();
		ySpinner.setModel(new SpinnerNumberModel(getvRad(),0,100,1));
		ySpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setvRad((int)ySpinner.getValue());
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


		//Iterations
		JLabel iteLab = new JLabel("Iterations: ");
		final JSpinner iteSpinner = new JSpinner();
		iteSpinner.setModel(new SpinnerNumberModel(getIterations(),1,4,1));
		iteSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setIterations((int)iteSpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});


		JPanel opPane = new JPanel();
		opPane.setLayout(new BoxLayout(opPane, BoxLayout.LINE_AXIS));
		opPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		opPane.add(Box.createHorizontalGlue());
		opPane.add(iteLab);
		opPane.add(Box.createRigidArea(new Dimension(150, 0)));
		opPane.add(iteSpinner);
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
				sethRad((int)xSpinner.getValue());
				setvRad((int)ySpinner.getValue());
				setIterations((int)iteSpinner.getValue());
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
				sethRad(oldhRad);
				setvRad(oldvRad);
				setIterations(olditerations);
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
		return Main.sig.filter.blurVariable(image, gethRad(), getvRad(), getIterations());
	}
	@Override
	public String generateScript(String filteronly){
		return "filter.blurVariable("+filteronly+", "+gethRad()+", "+getvRad()+", "+getIterations()+")";
	}
}
