package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import com.inverseinnovations.VisualMALSignatureDesigner.Main;

public class AddTitle extends AddText {
	private static final long serialVersionUID = 1L;
	private int id = 1;//12 titles
	private boolean airType = false;
	private int maxLength = 60;

	public AddTitle(Main Main){
		super("Anime Title", Main);
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @return the airType
	 */
	public boolean getAirType() {
		return airType;
	}
	/**
	 * @return the maxLength
	 */
	public int getMaxLength() {
		return maxLength;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @param airType the airType to set
	 */
	public void setAirType(boolean airType) {
		this.airType = airType;
	}
	/**
	 * @param maxLength the maxLength to set
	 */
	public void setMaxLength(int maxLength) {
		this.maxLength = maxLength;
	}
	@Override
	public JPanel settingsText(){
		JLabel idLab = new JLabel("Anime #:");
		Integer[] idStrings = new Integer[12];
		for(int i = 1;i <= 12;i++){
			idStrings[i-1] = i;
		}
		final JComboBox<Integer> idBox = new JComboBox<Integer>(idStrings);
		idBox.setSelectedIndex(getId()-1);
		idBox.addItemListener(new ItemListener(){
	        public void itemStateChanged(ItemEvent e){
				setId(idBox.getSelectedIndex()+1);
				saveObject();
				Main.ImageWindow.update();
	        }
	    });

		JLabel maxLab = new JLabel("Max Chars:");
		final JSpinner maxSpinner = new JSpinner();
		maxSpinner.setModel(new SpinnerNumberModel(getMaxLength(),1,999,1));
		maxSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setMaxLength((int) maxSpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});

		//JLabel typeLab = new JLabel("Show Type");
		JCheckBox typeBox = new JCheckBox("Show Type");
	    typeBox.setSelected(getAirType());
	    typeBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e){
				JCheckBox source = (JCheckBox)e.getSource();
				boolean bool = source.isSelected();
				setAirType(bool);
				saveObject();
				Main.ImageWindow.update();
			}
		});

		JPanel textPane = new JPanel();
		textPane.setLayout(new BoxLayout(textPane, BoxLayout.LINE_AXIS));
		textPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		textPane.add(Box.createHorizontalGlue());
		textPane.add(idLab);
		textPane.add(Box.createRigidArea(new Dimension(10, 0)));
		textPane.add(idBox);
		textPane.add(Box.createRigidArea(new Dimension(10, 0)));
		textPane.add(maxLab);
		textPane.add(Box.createRigidArea(new Dimension(10, 0)));
		textPane.add(maxSpinner);
		textPane.add(Box.createRigidArea(new Dimension(10, 0)));
		textPane.add(typeBox);
		textPane.add(Box.createHorizontalGlue());

		return textPane;
	}
	@Override
	public BufferedImage generateImage(){
		return Main.sig.makeTitle(getId(), getX(), getY(), getTextFont(), getAlign(), getAngdeg(), getAirType(), getMaxLength());
	}
}
