package Nodes;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.io.Serializable;
import java.util.ArrayList;


public abstract class Node implements Serializable {

	// Node fields
	protected Node parent;
	protected ArrayList<Node> childrenList;
	protected Color fillColor;
	protected Color strokeColor;
	protected AffineTransform affineTransform;
	protected boolean visibility;
	protected int imageWidth, imageHeight;
	protected int sizeStroke=0;

	// parent's fields
	protected Color parentStroke;
	protected Color parentFill;
	protected int parentSizeStroke;
	
	// unimplemented methods
	public abstract int[] getBounds();
	public abstract void addPoint(Point2D point);


	public Node() {
		childrenList = new ArrayList<Node>();
	}	
	
	public void addChildren(Node children) {
		if (!childrenList.contains(children) ) {
			childrenList.add(children);
			children.parent = this;
			children.setParentContext();
		}
		else {
			System.out.println("cannot add this children node : already in the list");
		}
	}	

	public void removeChildren(Node children) {

		if (!childrenList.contains(children) ) {
			System.out.println("Cannot remove this child node : not in the list");
		}
		else {
			childrenList.remove(children);
			children.parent = null;
		}		
	}

	public void setParentContext() {
		if (strokeColor==null) {
			this.strokeColor = this.parent.parentStroke;
		}
		if (fillColor == null) {
			this.fillColor = this.parent.parentFill;
		}
		if (sizeStroke == 0) {
			this.sizeStroke = this.parentSizeStroke;
		}
		setAffineTransformation(new AffineTransform());
		this.imageWidth = this.parent.imageWidth;
		this.imageHeight = this.parent.imageHeight;
	}

	public void paint(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.transform(this.affineTransform);
		
		for (Node child:childrenList) {
			child.paint(g);
		}
	}
	
	
	// SETTERS
	public void setStrokeColor(Color strokeColor) {
		this.strokeColor = strokeColor;
	}
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}
	public void setSizeStroke(int  sizeStroke) {
		this.sizeStroke = sizeStroke;
	}
	public void setAffineTransformation(AffineTransform affineTransform) {
		this.affineTransform = affineTransform;	
	}

	// GETTERS
	public ArrayList<Node> getChildren() {
		return childrenList;
	}
}

