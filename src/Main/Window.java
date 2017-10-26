package Main;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Hashtable;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButtonMenuItem;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.JToggleButton;
import javax.swing.JToolBar;
import javax.swing.SpinnerModel;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import PhotoViews.PhotoBrowser;
import PhotoViews.PhotoComponent;
import PhotoViews.PhotoStrip;


@SuppressWarnings("serial")
public class Window extends JFrame {

	/// MENU
	private JMenuBar menuBar = new JMenuBar();
	private JMenu menuFile = new JMenu("File");
	private JMenu menuView = new JMenu("View");

	private JMenuItem menuItemImport = new JMenuItem("Import");
	private JMenuItem menuItemDelete = new JMenuItem("Delete");
	private JMenuItem menuItemQuit = new JMenuItem("Quit");

	private JRadioButtonMenuItem menuItemPhotoViewer = new JRadioButtonMenuItem("Photo viewer");
	private JRadioButtonMenuItem menuItemBrowser = new JRadioButtonMenuItem("Browser");
	private JRadioButtonMenuItem menuItemSplitMode = new JRadioButtonMenuItem("Split Mode");


	/// CENTER COMPONENT
	private PhotoComponent photoComponent;
	private JScrollPane scrollPane;
	private JPanel panelCenter = new JPanel();
	private PhotoStrip photoStrip;
	private PhotoBrowser photoBrowser;
	private JScrollPane scrollPane2;
	private JScrollPane scrollPane3;
	// for serialization
	//private ObjectOutputStream oos = null;
	//private ObjectInputStream ois;


	// TOOL BAR (CATEGORY)
	private JToolBar toolBar = new JToolBar("", JToolBar.VERTICAL);
	private JLabel labelCategory = new JLabel("    Category");
	private JToggleButton toogleButton1 = new JToggleButton("Family");
	private JToggleButton toogleButton2 = new JToggleButton("Vacation");
	private JToggleButton toogleButton3 = new JToggleButton("School");


	// TOOL BAR (TOOLS)
	private JToolBar toolBar2 = new JToolBar("", JToolBar.VERTICAL);
	// type of drawing
	private JLabel labelTypeDrawing = new JLabel(" Type of drawing");
	private JToggleButton buttonStroke = new JToggleButton("Stroke");
	private JToggleButton buttonRect = new JToggleButton("Rectangle");
	private JToggleButton buttonEllipse = new JToggleButton("Ellipse");	
	// fill color
	private JLabel labelFillColor = new JLabel("     Fill color");
	private JButton buttonFillColor = new JButton("");
	private Color fillColor = new Color(100,150,220);
	// stroke color
	private JLabel labelStrokeColor = new JLabel("  Stroke color");
	private JButton buttonStrokeColor = new JButton("");
	private Color strokeColor = new Color(220,100,150);
	// size stroke
	private JLabel labelStrokeSize = new JLabel("   Stroke size");
	private SpinnerModel modelSpinnerStroke = new SpinnerNumberModel(1,0,10,1); 
	private JSpinner spinnerStroke = new JSpinner(modelSpinnerStroke);
	private int sizeStroke = 1;
	// font size
	private JLabel labelFontSize = new JLabel("        Font");
	private SpinnerModel modelspinnerFontSize = new SpinnerNumberModel(18,1,100,1); 
	private JSpinner spinnerFontSize = new JSpinner(modelspinnerFontSize);
	// font style
	private JLabel labelFontStyle = new JLabel("    Font style");
	private String[] fontStrings = { "TimesRoman", "Arial", "Courier"};
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private JComboBox fontList = new JComboBox(fontStrings);
	// zoom
	private JLabel labelZoom = new JLabel("        Zoom");
	private SpinnerModel modelSpinnerZoom = new SpinnerNumberModel(100,20,200,1); 
	private JSpinner spinnerZoom = new JSpinner(modelSpinnerZoom);	
	private JSlider sliderZoom = new JSlider(JSlider.HORIZONTAL, 20, 180, 100);
	@SuppressWarnings("rawtypes")
	private Hashtable labelTable = new Hashtable();
	private JLabel labelValueZoom = new JLabel("100%");


	// BOTTOM STATUS BAR
	private JLabel statusBar = new JLabel("Status bar", SwingConstants.CENTER);



	public Window() {

		super();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		


		// serialization
		/*
		try {
			final FileInputStream fichierIn = new FileInputStream("photoComponent.ser");
			ois = new ObjectInputStream(fichierIn);
			photoComponent = (PhotoComponent) ois.readObject();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			photoComponent = new PhotoComponent();
			scrollPane = new JScrollPane(photoComponent);
			try {
				if (ois != null) {
					ois.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}*/

		init();
		addMenuListeners();

		this.setJMenuBar(menuBar);	
		this.setLayout(new BorderLayout());
		this.getContentPane().add(panelCenter, BorderLayout.CENTER);
		this.getContentPane().add(statusBar, BorderLayout.SOUTH);
		this.getContentPane().add(toolBar, BorderLayout.WEST);
		this.getContentPane().add(toolBar2, BorderLayout.EAST);
		this.setSize(1100,600);
		this.setMinimumSize(new Dimension(500,500));
	}


	@SuppressWarnings("unchecked")
	private void init() {

		// MENU
		// menu file
		menuFile.add(menuItemImport);
		menuFile.add(menuItemDelete);
		menuFile.add(menuItemQuit);
		// menu views
		menuView.add(menuItemPhotoViewer);
		menuView.add(menuItemBrowser);
		menuView.add(menuItemSplitMode);

		menuBar.add(menuFile);
		menuBar.add(menuView);


		// CENTER COMPONENTS
		// photo Component
		photoComponent = new PhotoComponent(strokeColor, fillColor, sizeStroke);		
		scrollPane = new JScrollPane(photoComponent);
		// photo Strip
		photoStrip = new PhotoStrip(photoComponent);
		scrollPane2 = new JScrollPane(photoStrip);
		// photo Browser
		photoBrowser = new PhotoBrowser(photoComponent);
		scrollPane3 = new JScrollPane(photoBrowser);
		photoBrowser.setSize(new Dimension(30,30));

		// Grid Bag Layout
		panelCenter.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 0;
		c.weighty=1;
		c.weightx=1;
		panelCenter.add(scrollPane, c);
		photoStrip.setSize(new Dimension(30,30));
		c.fill = GridBagConstraints.BOTH;
		c.gridx = 0;
		c.gridy = 1;
		c.weighty=0.2;
		c.weightx=1;
		panelCenter.add(scrollPane2, c);

		// TOOL BAR CATEGORY
		setLabelsFont();
		setComponentsSize();
		setOptionsToolBar();
		// category
		toolBar.add(labelCategory);
		toolBar.add(toogleButton1);
		toolBar.add(toogleButton2);
		toolBar.add(toogleButton3);

		// TOOL BAR TOOLS
		// type of drawing
		toolBar2.add(labelTypeDrawing);
		toolBar2.add(buttonStroke);
		toolBar2.add(buttonRect);
		toolBar2.add(buttonEllipse);
		// fill
		toolBar2.add(labelFillColor);
		toolBar2.add(buttonFillColor);
		// stroke
		toolBar2.add(labelStrokeColor);
		toolBar2.add(buttonStrokeColor);
		// font
		toolBar2.add(labelStrokeSize);
		toolBar2.add(spinnerStroke);
		toolBar2.add(labelFontSize);
		toolBar2.add(spinnerFontSize);
		toolBar2.add(fontList);
		// zoom
		toolBar2.add(labelZoom);
		toolBar2.add(sliderZoom);


		// STATUS BAR
		statusBar.setForeground(Color.gray);
		statusBar.setOpaque(true);
		statusBar.setPreferredSize(new Dimension(200,20));

	}

	private void setOptionsToolBar() {
		buttonStroke.setSelected(true);
		buttonStrokeColor.setOpaque(true);
		buttonStrokeColor.setBackground(strokeColor);
		buttonFillColor.setOpaque(true);
		buttonFillColor.setBackground(fillColor);
		sliderZoom.setMajorTickSpacing(25);
		sliderZoom.setPaintTicks(true);		
		labelTable.put( new Integer( 100 ), labelValueZoom);
		sliderZoom.setLabelTable( labelTable );
		sliderZoom.setPaintLabels(true);
	}

	private void setLabelsFont() {
		// all the label titles are ste in bold
		labelCategory.setFont(new Font(labelCategory.getFont().getFontName(), 
				Font.BOLD, labelCategory.getFont().getSize()));
		labelFontSize.setFont(new Font(labelCategory.getFont().getFontName(), 
				Font.BOLD, labelCategory.getFont().getSize()));
		labelFontStyle.setFont(new Font(labelCategory.getFont().getFontName(), 
				Font.BOLD, labelCategory.getFont().getSize()));
		labelStrokeColor.setFont(new Font(labelCategory.getFont().getFontName(), 
				Font.BOLD, labelCategory.getFont().getSize()));
		labelStrokeColor.setFont(new Font(labelCategory.getFont().getFontName(), 
				Font.BOLD, labelCategory.getFont().getSize()));
		labelStrokeSize.setFont(new Font(labelCategory.getFont().getFontName(), 
				Font.BOLD, labelCategory.getFont().getSize()));
		labelZoom.setFont(new Font(labelCategory.getFont().getFontName(), 
				Font.BOLD, labelCategory.getFont().getSize()));
		labelTypeDrawing.setFont(new Font(labelCategory.getFont().getFontName(), 
				Font.BOLD, labelCategory.getFont().getSize()));
		labelFillColor.setFont(new Font(labelCategory.getFont().getFontName(), 
				Font.BOLD, labelCategory.getFont().getSize()));
	}

	private void setComponentsSize() {

		labelCategory.setPreferredSize(new Dimension(100,30));
		buttonStrokeColor.setPreferredSize(new Dimension(30,30));
		buttonFillColor.setPreferredSize(new Dimension(30,30));
		labelTypeDrawing.setPreferredSize(new Dimension (120,30));
		labelStrokeColor.setPreferredSize(new Dimension (100,30));
		labelFillColor.setPreferredSize(new Dimension (100,30));
		labelStrokeSize.setPreferredSize(new Dimension (100,30));
		labelFontSize.setPreferredSize(new Dimension (100,30));
		spinnerFontSize.setPreferredSize(new Dimension (110,25));
		spinnerStroke.setPreferredSize(new Dimension (110,25));
		labelFontStyle.setPreferredSize(new Dimension (100,30));
		labelZoom.setPreferredSize(new Dimension (100,30));
		sliderZoom.setPreferredSize(new Dimension(110,40));
	}


	private void addMenuListeners() {

		menuItemImport.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				JFrame frame = new JFrame("Browser");
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
				JFileChooser fileChooser = new JFileChooser();
				int returnVal = fileChooser.showOpenDialog(frame);
				if (returnVal == JFileChooser.APPROVE_OPTION) {
					File file = fileChooser.getSelectedFile();
					try {
						photoComponent.loadImage(ImageIO.read(file));
						photoStrip.addIcon(ImageIO.read(file), file.getPath());
						photoBrowser.addIcon(ImageIO.read(file), file.getPath());
						revalidate();
						repaint();

					} catch (IOException IOe) {
						IOe.printStackTrace();
						System.out.println("image not caught");  
					}
				}
			}
		}
				);

		menuItemDelete.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				photoComponent.deleteImage();
				JFrame frame = new JFrame("delete message");
				JOptionPane.showMessageDialog(frame,
						"Image deleted");
			}
		}
				);

		menuItemQuit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		}
				);

		menuItemPhotoViewer.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				//panelCenter.setLayout(new GridBagLayout());
				panelCenter.removeAll();

				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.gridx = 0;
				c.gridy = 0;
				c.weighty=1;
				c.weightx=1;
				panelCenter.add(scrollPane, c);
				//panelCenter.remove(photoStrip);

				menuItemBrowser.setSelected(false);
				menuItemSplitMode.setSelected(false);

				revalidate();
				repaint();
			}
		}
				);

		menuItemBrowser.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				panelCenter.removeAll();

				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.gridx = 0;
				c.gridy = 0;
				c.weighty=0.5;
				c.weightx=0.5;
				panelCenter.add(scrollPane3, c);

				revalidate();
				repaint();

				menuItemPhotoViewer.setSelected(false);
				menuItemSplitMode.setSelected(false);

			}
		}
				);


		menuItemSplitMode.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
				panelCenter.removeAll();

				GridBagConstraints c = new GridBagConstraints();
				c.fill = GridBagConstraints.BOTH;
				c.gridx = 0;
				c.gridy = 0;
				c.weighty=1;
				c.weightx=1;
				panelCenter.add(scrollPane, c);


				//photoStrip.setSize(new Dimension(30,30));
				c.fill = GridBagConstraints.BOTH;
				c.gridx = 0;
				c.gridy = 1;
				c.weighty=0.2;
				c.weightx=1;
				panelCenter.add(scrollPane2, c);

				menuItemBrowser.setSelected(false);
				menuItemPhotoViewer.setSelected(false);

				revalidate();
				repaint();
			}
		}
				);

		buttonStrokeColor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				strokeColor = JColorChooser.showDialog((Component) toolBar,
						"Choose Stroke Color", Color.red);
				buttonStrokeColor.setBackground(strokeColor);
				buttonStrokeColor.setForeground(strokeColor);
				photoComponent.setStrokeColor(strokeColor);
			}
		});


		buttonFillColor.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				fillColor = JColorChooser.showDialog((Component) toolBar,
						"Choose Fill Color", Color.blue);
				buttonFillColor.setBackground(fillColor);
				buttonFillColor.setForeground(fillColor);
				photoComponent.setFillColor(fillColor);
			}
		});


		spinnerZoom.addChangeListener( new ChangeListener() {
			@SuppressWarnings("unchecked")
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub  e.getSource());
				int zoom = (Integer) spinnerZoom.getValue();
				labelValueZoom.setText(Integer.toString(zoom)+"%");
				labelTable.remove(new Integer( 100 ), labelValueZoom);
				labelTable.put( new Integer( 100 ), labelValueZoom);
				photoComponent.setZoom(zoom);
			}
		});

		fontList.addItemListener(  new ItemListener() {

			public void itemStateChanged(ItemEvent arg0) {
				// TODO Auto-generated method stub
				String fontStyle = (String) arg0.getItem();
				photoComponent.setFontStyle(fontStyle);//
			}
		});		

		spinnerFontSize.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub
				int fontSize = (Integer) spinnerFontSize.getValue();
				photoComponent.setFontSize(fontSize);//
			}
		});

		spinnerStroke.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub  e.getSource());
				sizeStroke = (Integer) spinnerStroke.getValue();
				photoComponent.setSizeStroke(sizeStroke);
			}
		});

		spinnerZoom.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub  e.getSource());
				int zoom = (Integer) spinnerZoom.getValue();
				sliderZoom.setValue(zoom);
				photoComponent.setZoom(zoom);
			}
		});


		sliderZoom.addChangeListener( new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				// TODO Auto-generated method stub  e.getSource());
				int zoom = (Integer) sliderZoom.getValue();
				spinnerZoom.setValue(zoom);
				photoComponent.setZoom(zoom);
			}
		});


		buttonStroke.addActionListener(new ActionListenerTypeDrawing());
		buttonStroke.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				photoComponent.setTypeDrawing("stroke");
				//spinnerStroke.setEnabled(true);
			}			
		});

		buttonRect.addActionListener(new ActionListenerTypeDrawing());
		buttonRect.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				photoComponent.setTypeDrawing("rect");
				//spinnerStroke.setEnabled(false);
			}			
		});

		buttonEllipse.addActionListener(new ActionListenerTypeDrawing());
		buttonEllipse.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				photoComponent.setTypeDrawing("ellipse");
				//spinnerStroke.setEnabled(false);
			}			
		});

	}

	private class ActionListenerTypeDrawing implements ActionListener {

		public void actionPerformed(ActionEvent arg0) {
			// TODO Auto-generated method stub
			buttonStroke.setSelected(arg0.getSource()==buttonStroke);
			buttonRect.setSelected(arg0.getSource()==buttonRect);
			buttonEllipse.setSelected(arg0.getSource()==buttonEllipse);	

			//save();
			// issues with save: set as comment
		}
	}


	private void save() {

		ObjectOutputStream oos=null;
		try {
			final FileOutputStream fileOut = new FileOutputStream("photoComponent.ser");
			oos = new ObjectOutputStream(fileOut);
			oos.writeObject(photoComponent);
			oos.flush();
		} catch (final java.io.IOException ioE) {
			ioE.printStackTrace();
		} finally {
			System.out.println("in save finally");
			try {
				if (oos != null) {
					oos.close();
				}
			} catch (final IOException ex) {
				ex.printStackTrace();
			}
		}

	}
}


