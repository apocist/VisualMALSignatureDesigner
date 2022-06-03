package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.inverseinnovations.VisualMALSignatureDesigner.Main;

public class AddEpisodes extends AddText {
	private static final long serialVersionUID = 1L;
	private int id = 1;//12 titles
	private int style = 1;

	public AddEpisodes(Main Main){
		super("Anime Episodes", Main);
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @return the style
	 */
	public int getStyle() {
		return style;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @param style the style to set
	 */
	public void setStyle(int style) {
		this.style = style;
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

		JLabel styleLab = new JLabel("Style:");
		String[] styleStrings = { "3/54", "3 of 54","3 - 54","3","54"};
		final JComboBox<String> styleBox = new JComboBox<String>(styleStrings);styleBox.setEditable(false);
		styleBox.setSelectedIndex(getStyle()-1);

		styleBox.addItemListener(new ItemListener(){
	        public void itemStateChanged(ItemEvent e){
	        	setStyle(styleBox.getSelectedIndex()+1);
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
		textPane.add(styleLab);
		textPane.add(Box.createRigidArea(new Dimension(10, 0)));
		textPane.add(styleBox);
		textPane.add(Box.createHorizontalGlue());

		return textPane;
	}

	@Override
	public BufferedImage generateImage(){
		return Main.sig.makeEpisodes(getId(), getX(), getY(), getTextFont(), getAlign(), getAngdeg(), getStyle());
	}
	@Override
	public String generateScript(){
		return "sig.makeEpisodes("+getId()+", "+getX()+", "+getY()+", "+generateFont()+", \""+getAlign()+"\", "+getAngdeg()+", "+getStyle()+")";
	}
}
