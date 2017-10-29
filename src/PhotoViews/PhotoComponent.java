package PhotoViews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.Serializable;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

import Nodes.EllipseNode;
import Nodes.Node;
import Nodes.Panel;
import Nodes.PathNode;
import Nodes.RectNode;
import Nodes.RootNode;
import Nodes.TextNode;


@SuppressWarnings("serial")
public class PhotoComponent extends JComponent implements Serializable {

	private boolean isFlipped;

	private MyImage image;
	
	private RootNode rootNode;
	private Panel panel;
	private int offsetX, offsetY;
	private AffineTransform affineTransform;

	private boolean isAlreadyOneClick;
	private boolean drawsPath;
	private Node currentForm;
	private TextNode currentText;
	private Color strokeColor = Color.red;
	private Color fillColor = Color.red;
	private String fontStyle="TimesRoman";
	private int fontSize=18;
	private double zoom=1.;
	private int sizeStroke=1;
	private int typeDrawing=0;


	public PhotoComponent(Color strokeColor, Color fillColor, int sizeStroke) 
	{		
		super();
		
		this.strokeColor = strokeColor;
		this.fillColor = fillColor;
		this.sizeStroke = sizeStroke;

		// import image
		this.image = new MyImage();
		String uri = "./photos/city1.jpg";
		this.loadImage(uri);
		// this.setImage(this.image.getImage());
		affineTransform = new AffineTransform();		

		// add listeners
		this.addMouseListener(new myMouseListener());
		this.addMouseMotionListener(new myMouseMotionListener());
		this.setFocusable(true);
		this.addKeyListener(new myKeyListener());
	}
	
	public void loadImage(String uri) {
		try {
			
			BufferedImage buffImage = ImageIO.read(new File(uri));
			this.image.setImage(buffImage);
			this.setSize(this.image.getWidth(), this.image.getHeight());
			this.setPreferredSize(new Dimension (this.image.getWidth(), this.image.getHeight()));
			this.repaint();
			this.initSceneGraph();
			
		} catch (Exception e) {
			
			e.printStackTrace();
			System.out.println("Image "+uri+" not caught");   
			
		}
	}


	private void initSceneGraph() {		
		System.out.println("initScene");
		rootNode = new RootNode(this.image.getWidth(), this.image.getHeight(), strokeColor, fillColor);
		panel = new Panel(rootNode);
		drawsPath = false;
		this.add(panel);		
	}


	// MOUSE LISTENERS TO DRAW THE SHAPE NODES (PATH, RECT, ELLIPSE)
	public class myMouseMotionListener implements MouseMotionListener {

		public myMouseMotionListener() {}

		public void mouseDragged(MouseEvent arg0) {
			
			//System.out.println("Dragging the mouse");

			// IF THE IMAGE IS FLIPPED
			if (isFlipped == true) {
				int xPos = arg0.getX()-offsetX;
				int yPos = arg0.getY()-offsetY;

				// CONSTRUCT THE SHAPE IF NOT EXISTING YET
				if (drawsPath == false) {

					// IF THE CURSOR IN ON THE IMAGE ZONE
					if (0<xPos && xPos < image.getWidth() &&
							0<yPos && yPos < image.getHeight())
					{
						// ACCORDING TO THE TYPE OF SHAPE (CHOSEN BY THE USER)
						if (typeDrawing==0) {
							currentForm = new PathNode(xPos/zoom, yPos/zoom,
									(int) image.getWidth(), (int) image.getHeight()/*, sizeStroke*/);
						} else if (typeDrawing==1) {
							currentForm = new RectNode(xPos/zoom, yPos/zoom,
									(int) image.getWidth(), (int) image.getHeight()/*, sizeStroke*/);
						} else if (typeDrawing==2) {
							currentForm = new EllipseNode(xPos/zoom, yPos/zoom,
									(int) image.getWidth(), (int) image.getHeight()/*, sizeStroke*/);
						}
						currentForm.setStrokeColor(strokeColor);
						currentForm.setFillColor(fillColor);
						currentForm.setSizeStroke(sizeStroke);
						rootNode.addChildren(currentForm);
						drawsPath = true;

						// DISABLE WRITING AND WRITING DRAWING AT SAME TIME
						if (currentText != null) {							
							currentText.canWriteText(false);
						}
					}

					// ELSE IF THE SHAPE ALREADY EXIST, UPDATE IT
				} else if (drawsPath == true) {

					Point2D currentPoint = new Point2D.Double(xPos/zoom, yPos/zoom);
					if (xPos < image.getWidth() && yPos < image.getHeight())
					{
						currentForm.addPoint(currentPoint);
						repaint();
					}
				}		
			}
		}

		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}
	}


	// MOUSE LISTENER TO FLIP THE IMAGE AND PLACE THE TEXT NODES
	public class myMouseListener implements MouseListener {

		public myMouseListener() {}

		public void mouseClicked(final MouseEvent arg0) {
			// TODO Auto-generated method stub
			requestFocus();

			int xPos = arg0.getX();
			int yPos = arg0.getY();

			// IMPLEMENT SIMPLE AND DOUBLE CLICK				
			if (isAlreadyOneClick == true && 
					0<xPos-offsetX && xPos-offsetX<getWidth() &&
					0<yPos-offsetY && yPos-offsetY<getHeight()) {
				isAlreadyOneClick = false;
				flipImage();
				return;

			} else {
				if (isAlreadyOneClick = true && isFlipped == true && 
						0<xPos-offsetX && xPos-offsetX<getWidth() &&
						0<yPos-offsetY && yPos-offsetY<getHeight()) {

					if (currentText!=null) {
						currentText.canWriteText(false);
					}
					currentText = new TextNode((xPos-offsetX)/zoom, (yPos-offsetY)/zoom, fontStyle, fontSize);
					currentText.setStrokeColor(strokeColor);
					currentText.setFillColor(fillColor);
					repaint();
					rootNode.addChildren(currentText);
				}

				isAlreadyOneClick = true;
				Timer t = new Timer("doubleClickTimer", false);
				t.schedule(new TimerTask() {
					@Override
					public void run() {						
						isAlreadyOneClick = false;
						System.out.println("stop dble click");
					}
				}, 300);
			}
		}

		public void mouseEntered(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mouseExited(MouseEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void mousePressed(MouseEvent arg0) {
			// TODO Auto-generated method stub		

		}

		public void mouseReleased(MouseEvent arg0) {
			// TODO Auto-generated method stub
			drawsPath = false;
			//save();
		}
	}


	// SETTERS
	public void setStrokeColor(Color color) {
		this.strokeColor = color;
	}
	public void setFillColor(Color color) {
		this.fillColor = color;
	}

	public void setFontSize(int fontSize) {
		this.fontSize = fontSize;
	}
	public void setFontStyle(String fontStyle) {
		this.fontStyle = fontStyle;
	}
	public void setSizeStroke(int sizeStroke) {
		this.sizeStroke = sizeStroke;
	}
	public void setZoom(int intZoom) {
		this.zoom = (double) intZoom/100;
		this.image.setWidth( (int) Math.round(this.image.getOriginalWidth()*zoom) );
		this.image.setHeight( (int) Math.round(this.image.getOriginalHeight()*zoom) );
		repaint();
	}
	public void setTypeDrawing(String typeDrawing) {
		if (typeDrawing == "stroke") {
			this.typeDrawing = 0;
		}	else if (typeDrawing == "rect") {
			this.typeDrawing = 1;
		} 		else if (typeDrawing == "ellipse") {
			this.typeDrawing = 2;
		}
	}



	public void paintComponent(Graphics g) {
		super.paintComponent(g);  
		setAffineTransform();

		if (this.image.isImage()==true) {
			if (isFlipped==false) {	
				Graphics2D g2 = (Graphics2D) g;	
				// ALPHA
				// int rule = AlphaComposite.SRC_OVER;
				// Composite comp = AlphaComposite.getInstance(rule , 0.9f );
				// ((Graphics2D) g).setComposite(comp );

				g.clearRect(0,0,this.getWidth(),this.getHeight());				 
				g2 = setGradient(g);
				g2.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.drawImage(this.image.getImage(), offsetX, offsetY,
						(int) Math.round(this.image.getWidth()), 
						(int) Math.round(this.image.getHeight()), this);

			} else if (isFlipped==true) {
				Graphics2D g2 = (Graphics2D) g;	
				// int rule = AlphaComposite.SRC_OVER;
				// Composite comp = AlphaComposite.getInstance(rule , 0.9f );
				// ((Graphics2D) g).setComposite(comp );

				g.clearRect(0,0,this.getWidth(),this.getHeight());				 
				g2 = setGradient(g);
				g2.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.setColor(Color.WHITE);
				g.fillRect(offsetX,offsetY,
						(int) Math.round(this.image.getWidth()),
						(int) Math.round(this.image.getHeight()));
				rootNode.paint(g);
			}
		} else if (this.image.isImage() == false) {
			g.clearRect(0,0,this.getWidth(),this.getHeight());				 
			Graphics2D g2 = (Graphics2D) g;				
			g2 = setGradient(g);
			g2.fillRect(0, 0, this.getWidth(), this.getHeight());
		}

	}

	private Graphics2D setGradient(Graphics g) {

		Point2D start = new Point2D.Float(0, 0);
		Point2D end = new Point2D.Float(0, this.getHeight());
		float[] dist = {0.0f, 0.4f, 0.7f, 1.0f};
		Color color1 = new Color(200,200,220);
		Color color2 = new Color(245,245,255);
		Color color3 = new Color(140,140,200);
		Color color4 = new Color(130,130,150);
		Color[] colors = {color1, color2, color3, color4};
		LinearGradientPaint p =
				new LinearGradientPaint(start, end, dist, colors);		 
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(p);
		return g2;
	}

	private void setAffineTransform() {

		affineTransform = new AffineTransform();
		affineTransform.translate((this.getWidth()-this.image.getWidth())/2, 
				(this.getHeight()-this.image.getHeight())/2);
		affineTransform.scale(zoom, zoom);
		System.out.println(affineTransform);
		System.out.println(rootNode);
		rootNode.setAffineTransformation(affineTransform);
		offsetX = (int) affineTransform.getTranslateX();
		offsetY = (int) affineTransform.getTranslateY();
	}
	
	public void deleteImage() {
		this.image.deleteImage();
		this.isFlipped=false;
		repaint();
	}


	private void flipImage() {
		if (isFlipped==true) {
			isFlipped=false;
		}
		else if (isFlipped==false && this.image.isImage()==true) {
			isFlipped=true;
		}
		this.revalidate();
		this.repaint();
	}


	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub

	}

	public Dimension getPreferredScrollableViewportSize() {
		// TODO Auto-generated method stub
		return null;
	}

	public int getScrollableBlockIncrement(Rectangle arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	public boolean getScrollableTracksViewportHeight() {
		// TODO Auto-generated method stub
		return false;
	}

	public boolean getScrollableTracksViewportWidth() {
		// TODO Auto-generated method stub
		return false;
	}

	public int getScrollableUnitIncrement(Rectangle arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		return 0;
	}


	public void keyPressed(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}


	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub

	}	

	public class myKeyListener implements KeyListener {

		public myKeyListener() {}

		public void keyPressed(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void keyReleased(KeyEvent arg0) {
			// TODO Auto-generated method stub

		}

		public void keyTyped(KeyEvent arg0) {
			// TODO Auto-generated method stub
			if (isFlipped == true && currentText!=null) {
				currentText.addText(arg0.getKeyChar());
				repaint();
			}
		} 
	}

}
