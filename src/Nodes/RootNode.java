package Nodes;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;


public class RootNode extends Node {

	public RootNode(int imageWidth, int imageHeight, Color strokeColor, Color fillColor) {		
		super();
		this.strokeColor =strokeColor;
		this.fillColor = fillColor;
		this.affineTransform = new AffineTransform();
		this.imageWidth = imageWidth;
		this.imageHeight = imageHeight;
	}
	
	@Override
	public int[] getBounds() {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
	}

	@Override
	public void addPoint(Point2D point) {
		// TODO Auto-generated method stub
		
	}
	
}
