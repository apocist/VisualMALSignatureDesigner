package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock;

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
import javax.swing.tree.DefaultMutableTreeNode;

import com.inverseinnovations.VisualMALSignatureDesigner.Main;

public class InitSignature extends BuildingBlock {
	private static final long serialVersionUID = 1L;
	public String username;
	private int width = 600;
	private int height = 110;


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

	public InitSignature(Main Main){
		super("Signature", Main);
	}

	@Override
	public JDialog settingsDialog(Frame owner){
		final String oldname = getName();
		final int oldwidth = getWidth();
		final int oldheight = getHeight();

		final JDialog d = new JDialog(owner, "Signature Settings", true);

		JLabel text = new JLabel("Name:");
		final JTextField named = new JTextField();named.setSize(100, 10);named.setText(getName());

		//Name
		JPanel namePane = new JPanel();
		namePane.setLayout(new BoxLayout(namePane, BoxLayout.LINE_AXIS));
		namePane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		namePane.add(Box.createHorizontalGlue());
		namePane.add(text);
		namePane.add(Box.createRigidArea(new Dimension(10, 0)));
		namePane.add(named);
		namePane.add(Box.createHorizontalGlue());

		//Width / Height
		JLabel widthLab = new JLabel("Width:");JLabel heightLab = new JLabel("Height:");
		final JSpinner widSpinner = new JSpinner();
		widSpinner.setModel(new SpinnerNumberModel(getWidth(),1,9999,1));
		final JSpinner heiSpinner = new JSpinner();
		heiSpinner.setModel(new SpinnerNumberModel(getHeight(),1,9999,1));

		JPanel sizePane = new JPanel();
		sizePane.setLayout(new BoxLayout(sizePane, BoxLayout.LINE_AXIS));
		sizePane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		sizePane.add(Box.createHorizontalGlue());
		sizePane.add(widthLab);
		sizePane.add(Box.createRigidArea(new Dimension(10, 0)));
		sizePane.add(widSpinner);
		sizePane.add(Box.createRigidArea(new Dimension(10, 0)));
		sizePane.add(heightLab);
		sizePane.add(Box.createRigidArea(new Dimension(10, 0)));
		sizePane.add(heiSpinner);
		sizePane.add(Box.createHorizontalGlue());

		//OK / Cancel
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//save and close
				if(named.getText() != ""){setName(named.getText());}
				setWidth((int) widSpinner.getValue());
				setHeight((int) heiSpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
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
		buttonPane.setBorder(BorderFactory.createEmptyBorder(0, 3, 3, 3));
		buttonPane.add(Box.createHorizontalGlue());
		buttonPane.add(okButton);
		buttonPane.add(Box.createRigidArea(new Dimension(25, 0)));
		buttonPane.add(cancelButton);
		buttonPane.add(Box.createHorizontalGlue());

		Container contentPane = d.getContentPane();
		contentPane.add(namePane, BorderLayout.NORTH);
		contentPane.add(sizePane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);

		d.setLocationRelativeTo(owner);
		d.pack();
		d.setVisible(true);
		return d;
	}
	@Override
	public BufferedImage display(BufferedImage image){
		Main.sig.initDemoSignature(getWidth(),getHeight());
		image = Main.sig.getSigImage();
		if(getChildCount() > 0){
			//send this to each child to rerender(filter)
			for(int i = 0; i< getChildCount(); i++){
				//System.out.println(" - "+((DefaultMutableTreeNode) getChildAt(i)).getUserObject().getClass().getSimpleName());//XXX
				BuildingBlock block = (BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject();
				if(block != null){
					image = block.display(image);//TODO
					Main.sig.addImage(image, 0, 0);
				}
				else{System.out.println("DANGER NULL BLOCK!");}
			}
		}
		//return final image
		return Main.sig.getSigImage();
	}
}
