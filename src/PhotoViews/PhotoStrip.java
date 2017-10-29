package PhotoViews;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;


public class PhotoStrip extends JPanel {


	private ArrayList<Image> listIcon = new ArrayList<Image>();
	private ArrayList<JButton> listButton = new ArrayList<JButton>();
	private final int HEIGHT_ICON=50;
	private int width=0;
	private PhotoComponent photoComponent;
	ListPhotos listPhotos;
	ArrayList<Image> listImage;
	ArrayList<String> listUri;

	public PhotoStrip(PhotoComponent photoComponent) {
		super();
		this.setLayout(new FlowLayout());
		this.photoComponent = photoComponent;

		init();		
		//this.setPreferredSize(new Dimension(50,50));
	}

	public void init() {
		listPhotos = new ListPhotos();
		listImage = listPhotos.getImages();
		listUri = listPhotos.getUri();


		if (listImage!=null) {
			for(int i=0; i<listImage.size(); i++)
				try {
					addIcon(listImage.get(i), listUri.get(i));
				} catch (Exception e) {
					e.printStackTrace();
				}
		}
	}

	public void addIcon(Image image, final String uri) {
		listIcon.add(image);
		float sizeX = (image.getWidth(this) * HEIGHT_ICON / image.getHeight(this));
		float sizeY = HEIGHT_ICON;
		Image resizedImage = image.getScaledInstance(
				(int) sizeX, (int) sizeY,  java.awt.Image.SCALE_SMOOTH ) ; 
		JButton button = new JButton(new ImageIcon(resizedImage));
		button.setSize(new Dimension((int) sizeX, (int) sizeY));
		button.setSize(new Dimension((int) sizeX, (int) sizeY));
		listButton.add(button);
		//setButtons();
		this.add(button);
		this.width += button.getWidth()+35;
		this.setSize(width, HEIGHT_ICON+10);
		this.setPreferredSize(new Dimension(width, HEIGHT_ICON+10));


		button.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub
				photoComponent.loadImage(uri);
			}
		});
	}



	@Override
	protected void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		Graphics2D g2 = setGradient( g) ;
		//g.setColor(new Color(40,20,50));
		g2.fillRect(0,0,getWidth(),getHeight());

	}

	private Graphics2D setGradient(Graphics g) {

		Point2D start = new Point2D.Float(0, 0);
		Point2D end = new Point2D.Float(0, this.getHeight());
		float[] dist = {0.0f, 0.4f, 0.7f, 1.0f};
		Color color1 = new Color(20,20,50);
		Color color2 = new Color(100,100,120);
		Color color3 = new Color(140,140,200);
		Color color4 = new Color(130,130,150);
		Color[] colors = {color1, color2, color3, color4};
		LinearGradientPaint p =
				new LinearGradientPaint(start, end, dist, colors);		 
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(p);
		return g2;
	}

	/*
public class BlankButton extends JButton {

	private ImageIcon image;
	private PhotoBrowser observer;

	public BlankButton(ImageIcon image, PhotoBrowser observer) {
		this.image = image;
		this.observer = observer;
	}
	public void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g;
		//g.clearRect(0, 0, this.getWidth(), this.getHeight());
		AffineTransform transform=  new AffineTransform();
		transform.scale(1,1);
		g2.setTransform(transform);
		g2.drawImage(image.getImage(), 0, 0, observer);
	}
}*/


	/*
public void  setButtons() {
	listButton = new ArrayList<JButton>();
	for (Image icon:listIcon) {
	}		


	for (JButton button:listButton) {
		this.add(button);
	}
}*/


}



