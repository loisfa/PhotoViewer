package Nodes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;


public class RectNode extends Node {
		
	private int xInit;
	private int yInit;
	private int x;
	private int y;
	
	public RectNode(double xInit, double yInit, double imageWidth, double imageHeight) {
		super();
		this.xInit = (int) xInit;
		this.yInit = (int) yInit;
	}

	public void addPoint(Point2D point) {
		
		this.x = (int) Math.max(Math.min(point.getX(), imageWidth),0);
		this.y = (int) Math.max(Math.min(point.getY(), imageHeight),0);
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
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(strokeColor);
		g2.setStroke(new BasicStroke((float) sizeStroke, BasicStroke.CAP_ROUND, 1));
		g.drawRect(Math.min(x,xInit), Math.min(y,yInit), Math.abs(x-xInit), Math.abs(y-yInit));
		g2.setPaint(fillColor);
		g.fillRect(Math.min(x,xInit), Math.min(y,yInit), Math.abs(x-xInit), Math.abs(y-yInit));
	}	
}



