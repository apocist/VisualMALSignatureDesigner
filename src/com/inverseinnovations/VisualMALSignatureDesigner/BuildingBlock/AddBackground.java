package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.tree.DefaultMutableTreeNode;

import com.inverseinnovations.VisualMALSignatureDesigner.Main;

public class AddBackground extends BuildingBlock {
	private static final long serialVersionUID = 1L;
	private String rgb = "#000000";

	public AddBackground(Main Main){
		super("Background", Main);
	}
	/**
	 * @return the rgb color
	 */
	public String getRgb() {
		return rgb;
	}
	/**
	 * @param rgb the rgb color to set(use #)
	 */
	public void setRgb(String rgb) {
		this.rgb = rgb;
	}
	@Override
	public Icon getIcon() {
		return new ImageIcon(Main.class.getResource("resources/bgIcon.png"));
	}
	@Override
	public JDialog settingsDialog(Frame owner){
		final String oldname = getName();
		final String oldrgb = getRgb();

		final JDialog d = new JDialog(owner, "Background Settings", true);

		JLabel text = new JLabel("Name:");
		final JTextField named = new JTextField();named.setSize(100, 10);named.setText(getName());

		JPanel textPane = new JPanel();
		textPane.setLayout(new BoxLayout(textPane, BoxLayout.LINE_AXIS));
		textPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		textPane.add(Box.createHorizontalGlue());
		textPane.add(text);
		textPane.add(Box.createRigidArea(new Dimension(10, 0)));
		textPane.add(named);
		textPane.add(Box.createHorizontalGlue());

		final JColorChooser cc = new JColorChooser();
		//choose only HBV
		AbstractColorChooserPanel[] colorpanels = cc.getChooserPanels();
		AbstractColorChooserPanel[] newcolorPanel = new AbstractColorChooserPanel[1];
		for (AbstractColorChooserPanel accp : colorpanels) {
            if(accp.getDisplayName().equals("HSV")) {
                newcolorPanel[0] = accp;
            }
        }
		cc.setChooserPanels(newcolorPanel);
		cc.setPreviewPanel(new JPanel());
		try{
			cc.setColor(Color.decode(getRgb()));
		}
		catch(NumberFormatException numE){
		}
		cc.getSelectionModel().addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setRgb(Main.toHexString(cc.getColor()));
				saveObject();
				Main.ImageWindow.update();
			}
		});

		JPanel colorPane = new JPanel();
		colorPane.setLayout(new BoxLayout(colorPane, BoxLayout.PAGE_AXIS));
		colorPane.add(Box.createRigidArea(new Dimension(0,5)));
		colorPane.add(cc);
		colorPane.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//save and close
				if(named.getText() != ""){setName(named.getText());}
				setRgb(Main.toHexString(cc.getColor()));
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
				setRgb(oldrgb);
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
		contentPane.add(textPane, BorderLayout.NORTH);
		contentPane.add(colorPane, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);

		d.setLocationRelativeTo(owner);
		d.pack();
		d.setVisible(true);
		return d;
	}

	@Override
	public BufferedImage display(BufferedImage image){
		image = Main.sig.makeBackground(getRgb());
		if(getChildCount() > 0){
			//send this to each child to rerender(filter)
			for(int i = 0; i< getChildCount(); i++){
				if(!((BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject()).isFilter()){
					image = Main.sig.filter.composite(image, ((BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject()).display(image), 0, 0);
				}
				else{//if filter
					image = ((BuildingBlock) ((DefaultMutableTreeNode) getChildAt(i)).getUserObject()).display(image);
				}
			}
		}
		//return final image
		return image;
	}
	@Override
	public String generateScript(){
		return "sig.makeBackground(\""+getRgb()+")";
	}
}
