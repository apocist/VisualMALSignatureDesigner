package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock;

import java.awt.Dimension;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import com.inverseinnovations.VisualMALSignatureDesigner.Main;

public class AddThumbnail extends AddImage {
	private static final long serialVersionUID = 1L;
	private int id = 1;

	public AddThumbnail(Main Main){
		super("Anime Thumbnail", Main);
	}
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	@Override
	public Icon getIcon() {
		return new ImageIcon(System.getProperty("user.dir") + "/system/thumbIcon.png");
	}
	@Override
	protected JPanel settingsImage(final JDialog owner){

		//Image
		final JTextField imageField = new JTextField();imageField.setText(getFilename());imageField.setEditable(false);
		final JLabel imageLabel = new JLabel();
		//imageLabel.setSize(100, 100);
		imageLabel.setIcon(new ImageIcon(Main.sig.loadAnimeThumbnail(getId())));

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
				imageLabel.setIcon(new ImageIcon(Main.sig.loadAnimeThumbnail(getId())));
				Main.ImageWindow.update();
			}
		});


		JPanel imgPane = new JPanel();
		imgPane.setLayout(new BoxLayout(imgPane, BoxLayout.LINE_AXIS));
		imgPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		imgPane.add(Box.createHorizontalGlue());
		imgPane.add(idLab);
		imgPane.add(Box.createRigidArea(new Dimension(10, 0)));
		imgPane.add(idBox);
		imgPane.add(Box.createHorizontalGlue());

		JPanel totalPane = new JPanel();
		totalPane.setLayout(new BoxLayout(totalPane, BoxLayout.Y_AXIS));
		totalPane.add(Box.createVerticalGlue());
		totalPane.add(imgPane);
		totalPane.add(Box.createRigidArea(new Dimension(0, 3)));
		totalPane.add(imageLabel);
		totalPane.add(Box.createVerticalGlue());

		return totalPane;
	}
	@Override
	protected BufferedImage generateImage(){
		return Main.sig.loadAnimeThumbnail(getId());
	}
	@Override
	public String generateScript(){
		return "sig.loadAnimeThumbnail("+getId()+")";
	}
}
