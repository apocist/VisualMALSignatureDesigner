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

public class FilterGlowInner extends Filter {
	private static final long serialVersionUID = 1L;
	private double amount = 0;

	public FilterGlowInner(Main Main){
		super("Glow Inner", Main);
	}

	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}

	@Override
	public JDialog settingsDialog(final Frame owner){
		final String oldname = getName();
		final double oldamount = getAmount();

		final JDialog d = new JDialog(owner, "Glow Inner Settings", true);
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

		//glow amount
		JLabel glowLab = new JLabel("Amount:");
		final JSpinner glowSpinner = new JSpinner();
		glowSpinner.setValue(getAmount());
		glowSpinner.setModel(new SpinnerNumberModel(getAmount(),-5.0,10.0,0.025));glowSpinner.setSize(60, 10);
		glowSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSpinner source = (JSpinner)e.getSource();
				setAmount((double) source.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});

		JPanel glowPane = new JPanel();
		glowPane.setLayout(new BoxLayout(glowPane, BoxLayout.LINE_AXIS));
		glowPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		glowPane.add(Box.createHorizontalGlue());
		glowPane.add(glowLab);
		glowPane.add(Box.createRigidArea(new Dimension(20, 0)));
		glowPane.add(glowSpinner);
		glowPane.add(Box.createHorizontalGlue());

		//Content
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(Box.createVerticalGlue());
		contentPanel.add(glowPane);
		contentPanel.add(Box.createVerticalGlue());

		//OK / Cancel
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//save and close
				if(named.getText() != ""){setName(named.getText());}
				setAmount((double) glowSpinner.getValue());
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
				setAmount(oldamount);
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
		return Main.sig.filter.glowInner(image, getAmount());
	}
}
