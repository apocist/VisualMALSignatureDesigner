package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock;

import java.awt.Dimension;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import com.inverseinnovations.VisualMALSignatureDesigner.Main;

public class AddEmptyImage extends AddImage {
	private static final long serialVersionUID = 1L;
	private int sizeX = 100;
	private int sizeY = 100;

	public AddEmptyImage(Main Main){
		super("Empty Image", Main);
	}
	/**
	 * @return the sizeX
	 */
	public int getSizeX() {
		return sizeX;
	}
	/**
	 * @return the sizeY
	 */
	public int getSizeY() {
		return sizeY;
	}
	/**
	 * @param sizeX the sizeX to set
	 */
	public void setSizeX(int sizeX) {
		this.sizeX = sizeX;
	}
	/**
	 * @param sizeY the sizeY to set
	 */
	public void setSizeY(int sizeY) {
		this.sizeY = sizeY;
	}
	@Override
	public Icon getIcon() {
		return new ImageIcon(Main.class.getResource("resources/blankIcon.png"));
	}
	@Override
	protected JPanel settingsImage(final JDialog owner){

		//locations
		JLabel sizeLab = new JLabel("Size: ");
		final JSpinner xSpinner = new JSpinner();
		xSpinner.setModel(new SpinnerNumberModel(getSizeX(),1,9999,1));
		xSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setSizeX((int)xSpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});
		final JSpinner ySpinner = new JSpinner();
		ySpinner.setModel(new SpinnerNumberModel(getSizeY(),1,9999,1));
		ySpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setSizeY((int)ySpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});

		JPanel totalPane = new JPanel();
		totalPane.setLayout(new BoxLayout(totalPane, BoxLayout.LINE_AXIS));
		totalPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		totalPane.add(Box.createHorizontalGlue());
		totalPane.add(sizeLab);
		totalPane.add(Box.createRigidArea(new Dimension(10, 0)));
		totalPane.add(xSpinner);
		totalPane.add(Box.createRigidArea(new Dimension(10, 0)));
		totalPane.add(ySpinner);
		totalPane.add(Box.createHorizontalGlue());


		return totalPane;
	}
	@Override
	protected BufferedImage generateImage(){
		return Main.sig.makeImage(getSizeX(), getSizeY());
	}
	@Override
	public String generateScript(){
		return "sig.makeImage("+getSizeX()+", "+getSizeY()+")";
	}
}
