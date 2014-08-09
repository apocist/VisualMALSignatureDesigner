package com.inverseinnovations.VisualMALSignatureDesigner.BuildingBlock;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.colorchooser.AbstractColorChooserPanel;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;

import com.inverseinnovations.MALSignatureDesignerLite.TextFont;
import com.inverseinnovations.VisualMALSignatureDesigner.Main;

public class AddText extends BuildingBlock {
	private static final long serialVersionUID = 1L;
	protected String text = "";
	protected int x = 20;
	protected int y = 20;
	protected TextFont textFont;//String fontName, String style, int size, String rgbColor
	protected String align = "left";
	protected int angdeg = 0;


	public AddText(Main Main){
		super("Text", Main);
		setTextFont(Main.sig.newFont(UIManager.getDefaults().getFont("TabbedPane.font").getFamily(), "plain", 12, "#000000"));
		setX(20);setY(20);
	}
	public AddText(String name,Main Main){
		super(name, Main);
		setTextFont(Main.sig.newFont(UIManager.getDefaults().getFont("TabbedPane.font").getFamily(), "plain", 12, "#000000"));
		setX(20);setY(20);
	}

	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}

	/**
	 * @return the textFont
	 */
	public TextFont getTextFont() {
		return textFont;
	}
	/**
	 * @return the align
	 */
	public String getAlign() {
		return align;
	}
	/**
	 * @return the angle degree
	 */
	public int getAngdeg() {
		return angdeg;
	}
	/**
	 * @param text the text to display
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param textFont the textFont to set
	 */
	public void setTextFont(TextFont textFont) {
		this.textFont = textFont;
	}
	/**
	 * @param align the alignment to set
	 */
	public void setAlign(String align) {
		this.align = align;
	}
	/**
	 * @param angdeg the angle degrees to set
	 */
	public void setAngdeg(int angdeg) {
		this.angdeg = angdeg;
	}
	@Override
	public Icon getIcon() {
		return new ImageIcon(System.getProperty("user.dir") + "/system/textIcon.png");
	}
	protected JPanel settingsText(){
		JLabel textLab = new JLabel("Text:");
		final JTextField textField = new JTextField();textField.setText(getText());
		textField.getDocument().addDocumentListener(new DocumentListener() {
			public void changedUpdate(DocumentEvent e) {
				setText(textField.getText());
				saveObject();
				Main.ImageWindow.update();
			}
			public void removeUpdate(DocumentEvent e) {
				setText(textField.getText());
				saveObject();
				Main.ImageWindow.update();
			}
			public void insertUpdate(DocumentEvent e) {
				setText(textField.getText());
				saveObject();
				Main.ImageWindow.update();
			}
		});

		JPanel textPane = new JPanel();
		textPane.setLayout(new BoxLayout(textPane, BoxLayout.LINE_AXIS));
		textPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		textPane.add(Box.createHorizontalGlue());
		textPane.add(textLab);
		textPane.add(Box.createRigidArea(new Dimension(10, 0)));
		textPane.add(textField);
		textPane.add(Box.createHorizontalGlue());

		return textPane;
	}
	protected JDialog settingsTextFont(JDialog owner){
		final JDialog d = new JDialog(owner, "Font Settings", true);
		final JFileChooser fc = new JFileChooser(System.getProperty("user.dir") + "/fonts");
		FileFilter filter = new FileNameExtensionFilter("TrueType font", new String[] {"ttf"});
		fc.addChoosableFileFilter(filter);
		fc.setFileFilter(filter);


		//Font
		//JLabel fontLab = new JLabel("Font:");
		final JComboBox<String> fontBox = new JComboBox<String>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());fontBox.setEditable(false);if(getTextFont() != null){fontBox.addItem(getTextFont().getFontname());fontBox.setSelectedItem(getTextFont().getFontname());}

		final JButton fontBut = new JButton("...");


		JPanel fontPane = new JPanel();
		fontPane.setLayout(new BoxLayout(fontPane, BoxLayout.LINE_AXIS));
		fontPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		fontPane.add(Box.createHorizontalGlue());
		//fontPane.add(fontLab);
		//fontPane.add(Box.createRigidArea(new Dimension(10, 0)));
		fontPane.add(fontBox);
		fontPane.add(Box.createRigidArea(new Dimension(10, 0)));
		fontPane.add(fontBut);
		fontPane.add(Box.createHorizontalGlue());

		//Size Style
		JLabel sizeLab = new JLabel("Size:");JLabel styleLab = new JLabel("Style:");
		final JSpinner sizeSpinner = new JSpinner();
		sizeSpinner.setModel(new SpinnerNumberModel(12,1,999,1));if(getTextFont() != null){sizeSpinner.setValue(getTextFont().getSize());}
		String[] styleStrings = { "Plain", "Bold", "Italic"};
		final JComboBox<String> styleBox = new JComboBox<String>(styleStrings);
		styleBox.setSelectedIndex(0);
		if(getTextFont() != null){
			switch(getTextFont().getStyle().toLowerCase()){
			case "bold":styleBox.setSelectedIndex(1);break;
			case "italic":styleBox.setSelectedIndex(2);break;
			default:styleBox.setSelectedIndex(0);break;
			}
		}

		JPanel sizePane = new JPanel();
		sizePane.setLayout(new BoxLayout(sizePane, BoxLayout.LINE_AXIS));
		sizePane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		sizePane.add(Box.createHorizontalGlue());
		sizePane.add(sizeLab);
		sizePane.add(Box.createRigidArea(new Dimension(10, 0)));
		sizePane.add(sizeSpinner);
		sizePane.add(Box.createRigidArea(new Dimension(10, 0)));
		sizePane.add(styleLab);
		sizePane.add(Box.createRigidArea(new Dimension(10, 0)));
		sizePane.add(styleBox);
		sizePane.add(Box.createHorizontalGlue());

		//Color
		final JTextField colorField = new JTextField();colorField.setText("#000000");if(getTextFont() != null){colorField.setText(getTextFont().getHexColor());}
		JButton colorBut = new JButton("Color:");

		fontBox.addItemListener(new ItemListener(){
	        public void itemStateChanged(ItemEvent e){
	        	setTextFont(Main.sig.newFont((String)fontBox.getSelectedItem(), ((String)styleBox.getSelectedItem()).toLowerCase(), (int)sizeSpinner.getValue(), colorField.getText()));
				saveObject();
				Main.ImageWindow.update();
	        }
	    });
		fontBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				File dir = new File(System.getProperty("user.dir") + "/fonts");
				if(dir.isDirectory() && dir.canRead()){
					fc.setCurrentDirectory(dir);
					int returnVal = fc.showOpenDialog(d);
					if (returnVal == JFileChooser.APPROVE_OPTION) {
						File file = fc.getSelectedFile();
						if(file.isFile()){
							String extension = "";
							int i = file.getName().lastIndexOf('.');
							if (i > 0) {
								extension = file.getName().substring(i+1);
							}
							if(extension.equalsIgnoreCase("ttf")){
								if(file.getParentFile().compareTo(dir) == 0){//is in the Fonts folder already
									//set the name
									//fontField.setText(file.getName());

									fontBox.addItem(file.getName());
									fontBox.setSelectedItem(file.getName());
								}
								else{//not in fonts folder
									//copy to fonts folder //TODO check
									System.out.println("not in folder, copying...");
									try {
										Files.copy(file.toPath(), dir.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);
										//fontField.setText(file.getName());
										fontBox.addItem(file.getName());
										fontBox.setSelectedItem(file.getName());
										System.out.println("Copied.");
									}
									catch (IOException e1) {
										System.out.println("!!COPY FAILED!!");
									}
									setTextFont(Main.sig.newFont((String)fontBox.getSelectedItem(), ((String)styleBox.getSelectedItem()).toLowerCase(), (int)sizeSpinner.getValue(), colorField.getText()));
									saveObject();
									Main.ImageWindow.update();
								}
							}
						}
					}
				}
				else{//no access to /fonts
					JOptionPane.showMessageDialog(d, "ERROR: Fonts folder does not exist, not installed correctly.");
				}
			}
		});
		styleBox.addItemListener(new ItemListener(){
	        public void itemStateChanged(ItemEvent e){
	        	setTextFont(Main.sig.newFont((String)fontBox.getSelectedItem(), ((String)styleBox.getSelectedItem()).toLowerCase(), (int)sizeSpinner.getValue(), colorField.getText()));
				saveObject();
				Main.ImageWindow.update();
	        }
	    });
		colorBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				final JDialog colorD = new JDialog(d, "Font Color", true);

				final JColorChooser cc = new JColorChooser();
				//choose only HBV
				if(getTextFont() != null){
					cc.setColor(getTextFont().getColor());
				}
				else{
					cc.setColor(0, 0, 0);
				}
				AbstractColorChooserPanel[] colorpanels = cc.getChooserPanels();
				AbstractColorChooserPanel[] newcolorPanel = new AbstractColorChooserPanel[1];
				for (AbstractColorChooserPanel accp : colorpanels) {
					if(accp.getDisplayName().equals("HSV")) {
						newcolorPanel[0] = accp;
					}
				}
				cc.setChooserPanels(newcolorPanel);
				cc.setPreviewPanel(new JPanel());
				cc.getSelectionModel().addChangeListener(new ChangeListener(){
					public void stateChanged(ChangeEvent e){
						colorField.setText(Main.toHexString(cc.getColor()));
						setTextFont(Main.sig.newFont((String)fontBox.getSelectedItem(), ((String)styleBox.getSelectedItem()).toLowerCase(), (int)sizeSpinner.getValue(), Main.toHexString(cc.getColor())));
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
						colorField.setText(Main.toHexString(cc.getColor()));
						setTextFont(Main.sig.newFont((String)fontBox.getSelectedItem(), ((String)styleBox.getSelectedItem()).toLowerCase(), (int)sizeSpinner.getValue(), Main.toHexString(cc.getColor())));
						colorD.dispose();
					}
				});
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener(){
					public void actionPerformed(ActionEvent e){
						colorD.dispose();
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

				Container contentPane = colorD.getContentPane();
				contentPane.add(colorPane, BorderLayout.CENTER);
				contentPane.add(buttonPane, BorderLayout.PAGE_END);

				colorD.setLocationRelativeTo(d);
				colorD.pack();
				colorD.setVisible(true);
			}
		});
		sizeSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setTextFont(Main.sig.newFont((String)fontBox.getSelectedItem(), ((String)styleBox.getSelectedItem()).toLowerCase(), (int)sizeSpinner.getValue(), colorField.getText()));
				saveObject();
				Main.ImageWindow.update();
			}
		});

		JPanel colorPane = new JPanel();
		colorPane.setLayout(new BoxLayout(colorPane, BoxLayout.LINE_AXIS));
		colorPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		colorPane.add(Box.createHorizontalGlue());
		colorPane.add(colorBut);
		colorPane.add(Box.createRigidArea(new Dimension(10, 0)));
		colorPane.add(colorField);
		colorPane.add(Box.createHorizontalGlue());

		//Content
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(Box.createVerticalGlue());
		contentPanel.add(fontPane);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		contentPanel.add(sizePane);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		contentPanel.add(colorPane);
		contentPanel.add(Box.createVerticalGlue());

		//OK / Cancel
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//save and close
				if(fontBox.getSelectedItem() != null){
					setTextFont(Main.sig.newFont((String)fontBox.getSelectedItem(), ((String)styleBox.getSelectedItem()).toLowerCase(), (int)sizeSpinner.getValue(), colorField.getText()));
				}
				saveObject();
				Main.ImageWindow.update();
				d.dispose();
			}
		});
		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
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
		//contentPane.add(namePane, BorderLayout.NORTH);
		contentPane.add(contentPanel, BorderLayout.CENTER);
		contentPane.add(buttonPane, BorderLayout.PAGE_END);

		//d.setPreferredSize(new Dimension(400,130));

		d.setLocationRelativeTo(owner);
		d.pack();
		d.setVisible(true);
		return d;
	}
	@Override
	public JDialog settingsDialog(final Frame owner){
		final String oldname = getName();
		final String oldtext = getText();;
		final int oldx = getX();
		final int oldy = getY();
		final TextFont oldtextFont = getTextFont();
		final String oldalign = getAlign();
		final int oldangdeg = getAngdeg();

		final JDialog d = new JDialog(owner, getClass().getSimpleName()+" Settings", true);
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

		//Alignment / Font


		String[] alignStrings = { "LEFT", "CENTER", "RIGHT"};
		final JComboBox<String> alignBox = new JComboBox<String>(alignStrings);alignBox.setEditable(false);
		switch(getAlign()){
		case "left":alignBox.setSelectedIndex(0);break;
		case "center":alignBox.setSelectedIndex(1);break;
		case "right":alignBox.setSelectedIndex(2);break;
		}
		alignBox.addItemListener(new ItemListener(){
	        public void itemStateChanged(ItemEvent e){
	        	switch(alignBox.getSelectedIndex()){
				case 1:setAlign("center");break;
				case 2:setAlign("right");break;
				default:setAlign("left");break;
				}
				saveObject();
				Main.ImageWindow.update();
	        }
	    });

		final JButton fontBut = new JButton("Font");
		fontBut.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				settingsTextFont(d);
			}
		});

		JPanel textPane = new JPanel();
		textPane.setLayout(new BoxLayout(textPane, BoxLayout.LINE_AXIS));
		textPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		textPane.add(Box.createHorizontalGlue());
		textPane.add(Box.createRigidArea(new Dimension(10, 0)));
		textPane.add(fontBut);
		textPane.add(Box.createRigidArea(new Dimension(10, 0)));
		textPane.add(alignBox);
		textPane.add(Box.createHorizontalGlue());

		//Position
		JLabel xLab = new JLabel("X:");JLabel yLab = new JLabel("Y:");
		final JSpinner xSpinner = new JSpinner();
		xSpinner.setModel(new SpinnerNumberModel(getX(),-9999,9999,1));
		xSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setX((int) xSpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});
		final JSpinner ySpinner = new JSpinner();
		ySpinner.setModel(new SpinnerNumberModel(getY(),-9999,9999,1));
		ySpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				setY((int) ySpinner.getValue());
				saveObject();
				Main.ImageWindow.update();
			}
		});


		JPanel posPane = new JPanel();
		posPane.setLayout(new BoxLayout(posPane, BoxLayout.LINE_AXIS));
		posPane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		posPane.add(Box.createHorizontalGlue());
		posPane.add(xLab);
		posPane.add(Box.createRigidArea(new Dimension(10, 0)));
		posPane.add(xSpinner);
		posPane.add(Box.createRigidArea(new Dimension(10, 0)));
		posPane.add(yLab);
		posPane.add(Box.createRigidArea(new Dimension(10, 0)));
		posPane.add(ySpinner);
		posPane.add(Box.createHorizontalGlue());

		//Angle
		JLabel angleLab = new JLabel("Angle:");
		final JSpinner angleSpinner = new JSpinner();
		angleSpinner.setModel(new SpinnerNumberModel(getAngdeg(),0,359,1));
		//mySpinner.setEditor(new JSpinner.NumberEditor(mySpinner,"##.#"));
		final JSlider angleSlider = new JSlider(JSlider.HORIZONTAL, 0, 359, getAngdeg());angleSlider.setSize(100, 10);
		angleSlider.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSlider source = (JSlider)e.getSource();
				int ang = source.getValue();
				angleSpinner.setValue(ang);
				//if (!source.getValueIsAdjusting()) {
					setAngdeg((int) angleSpinner.getValue());
					saveObject();
					Main.ImageWindow.update();
				//}
			}
		});
		angleSpinner.addChangeListener(new ChangeListener(){
			public void stateChanged(ChangeEvent e){
				JSpinner source = (JSpinner)e.getSource();
				int ang = (int) source.getValue();
				angleSlider.setValue(ang);
			}
		});

		JPanel anglePane = new JPanel();
		anglePane.setLayout(new BoxLayout(anglePane, BoxLayout.LINE_AXIS));
		anglePane.setBorder(BorderFactory.createEmptyBorder(15, 25, 3, 25));
		anglePane.add(Box.createHorizontalGlue());
		anglePane.add(angleLab);
		anglePane.add(Box.createRigidArea(new Dimension(10, 0)));
		anglePane.add(angleSlider);
		anglePane.add(Box.createRigidArea(new Dimension(10, 0)));
		anglePane.add(angleSpinner);
		anglePane.add(Box.createHorizontalGlue());

		//Content
		JPanel contentPanel = new JPanel();
		contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
		contentPanel.add(Box.createVerticalGlue());
		contentPanel.add(settingsText());//Dynamic
		contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
		contentPanel.add(textPane);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		contentPanel.add(posPane);
		contentPanel.add(Box.createRigidArea(new Dimension(0, 10)));
		contentPanel.add(anglePane);
		contentPanel.add(Box.createVerticalGlue());

		//OK / Cancel
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				//save and close
				if(named.getText() != ""){setName(named.getText());}
				//setText(textField.getText());
				switch(alignBox.getSelectedIndex()){
				case 1:setAlign("center");break;
				case 2:setAlign("right");break;
				default:setAlign("left");break;
				}
				//save textfont(done in its own dialog)
				setX((int) xSpinner.getValue());
				setY((int) ySpinner.getValue());
				setAngdeg((int) angleSpinner.getValue());
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
				setText(oldtext);///XXX do i need to remove?
				setX(oldx);
				setY(oldy);
				setTextFont(oldtextFont);
				setAlign(oldalign);
				setAngdeg(oldangdeg);

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
	public BufferedImage display(BufferedImage image){
		if(text != null && textFont != null){
			image = generateImage();
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
		JOptionPane.showMessageDialog(null, "ERROR: No Font was choosen!");
		return null;
	}
	protected BufferedImage generateImage(){
		return Main.sig.makeText(getText(),  getX(), getY(), getTextFont(), getAlign(), getAngdeg());
	}
	@Override
	public String generateScript(){
		return "sig.makeText(\""+getText()+"\", "+getX()+", "+getY()+", "+generateFont()+", "+getAlign()+", "+getAngdeg()+")";
	}
	protected String generateFont(){
		return "sig.newFont(\""+getTextFont().getFontname()+"\", \""+getTextFont().getStyle()+"\", "+getTextFont().getSize()+", \""+getTextFont().getHexColor()+"\")";
	}
}
