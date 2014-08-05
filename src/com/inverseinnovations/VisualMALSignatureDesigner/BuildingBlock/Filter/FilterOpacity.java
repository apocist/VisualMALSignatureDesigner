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

public class FilterOpacity extends Filter {
	private static final long serialVersionUID = 1L;
	private double amount = 100;

	public FilterOpacity(Main Main){
		super("Opacity", Main);
	}

	/**
	 * @return the angle
	 */
	public double getAmount() {
		return amount;
	}

	/**
	 * @param angle the angle to set
	 */
	public void setAmount(int amount) {
		this.amount = amount;
	}


	@Override
	public JDialog settingsDialog(final Frame owner){
		final String oldname = getName();
		final double oldamount = getAmount();

		final JDialog d = new JDialog(owner, "Opacity Settings", true);
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

		//Angle
		JLabel amountLab = new JLabel("Opacity:");
		final JSpinner amountSpinner = new JSpinner();
		amountSpinner.setModel(new SpinnerNumberModel((int)getAmount(),0,100,1));
		//mySpinner.setEditor(new JSpinner.NumberEditor(mySpinner,"##.#"));
		final JSlider amountSlider = new JSlider(JSlider.HORIZONTAL, 0, 100, (int) getAmount());amountSlider.setSize(100, 10);
		amountSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				int ang = source.getValue();
				amountSpinner.setValue(ang);
				//if (!source.getValueIsAdjusting()) {
					setAmount((int) amountSpinner.getValue());
					saveObject();
					Main.ImageWindow.update();
				//}
			}
		});
		amountSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSpinner source = (JSpinner)e.getSource();
				int ang = (int) source.getValue();
				amountSlider.setValue(ang);
			}
		});

		JPanel anglePane = new JPanel();
		anglePane.setLayout(new BoxLayout(anglePane, BoxLayout.LINE_AXIS));
		anglePane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		anglePane.add(Box.createHorizontalGlue());
		anglePane.add(amountLab);
		anglePane.add(Box.createRigidArea(new Dimension(10, 0)));
		anglePane.add(amountSlider);
		anglePane.add(Box.createRigidArea(new Dimension(10, 0)));
		anglePane.add(amountSpinner);
		anglePane.add(Box.createHorizontalGlue());

		//Content
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(Box.createVerticalGlue());
		contentPanel.add(anglePane);
		contentPanel.add(Box.createVerticalGlue());

		//OK / Cancel
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//save and close
				if(named.getText() != ""){setName(named.getText());}
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
				setAmount((int)oldamount);
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
		return Main.sig.filter.opacity(image, getAmount());
	}
}
