package Nodes;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;


public class PathNode extends Node {
	
	GeneralPath generalPath = new GeneralPath();
	
	
	public PathNode(double x, double y, double imageWidth, double imageHeight) {
		super();
		generalPath.moveTo(x,y);
	}

	public void addPoint(Point2D point) {
		int x = (int) point.getX();
		int y = (int) point.getY();

		if ((0<x && x<imageWidth) && (0<y && y<imageHeight))
		{
			generalPath.lineTo(x,y);
		} else {
			generalPath.moveTo(x,y);
		}
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
		g2.setStroke(new BasicStroke((float) sizeStroke, BasicStroke.CAP_ROUND, 1));
		g2.setPaint(strokeColor);
		g2.draw(generalPath);
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
	}
	
}

