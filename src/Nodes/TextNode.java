package Nodes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;


public class TextNode extends Node {

	private int x;
	private int y;
	private ArrayList<String> textList; // each element of the list is a line of the text
	private int lineHeight; // vertical distance between two lines
	private boolean writeText;
	private String fontStyle;
	private int fontSize;

	
	public TextNode(double x, double y, String fontStyle, int fontSize) {
		super();	
		this.x = (int) x;
		this.y = (int) y;
		this.canWriteText(true);
		this.fontStyle = fontStyle;
		this.fontSize = fontSize;
		this.lineHeight = 20*fontSize/18;
		textList = new ArrayList<String>();
		textList.add("");
	}
	

	public void addText(char s) { // adds the character at the end of the last line
		if (writeText==true) {
			String lastLine = textList.get(textList.size()-1);
			lastLine += s;		
			textList.set(textList.size()-1, lastLine); 
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

		g.setFont(new Font(fontStyle, Font.PLAIN, fontSize)); 
		Graphics2D g2 = (Graphics2D) g;
		g2.setPaint(fillColor);
		int linePosX=0;
		int linePosY=0;

		for (int i=0; i<textList.size(); i++) {			
			String line = textList.get(i);			
			linePosX = x + g.getFontMetrics().stringWidth(line); 
			// x pos of the cursor
			linePosY = y + lineHeight*i; 
			// y pos of the cursor

			// IF THE LINE IS STILL ON THE WHITE PAGE
			if (linePosX < imageWidth && linePosY < imageHeight) {
				
				g.drawString(line, x, y + lineHeight*i);

			} 
			// ELSE IF THE LINE IS NO LONGER ON THE WHITE PAGE
			else if (linePosX >= imageWidth) {	
				
				String fullLine = line.substring(0, line.length()-1);
				// put off the last character of the last line...				
				String newLine = line.substring(line.length()-1, line.length());
				// ... and puts it on the following line
				textList.set(i, fullLine);
				textList.add(newLine);
				this.paint(g);				
			}
		}

		// DRAWS THE CURSOR IF ELEMENT IS ACTIVE
		if (writeText==true) {
			g2.setPaint(new Color(50,50,50));
			g.fillRect(linePosX+3, linePosY-14, 2, 14);		
			g.fillRect(linePosX, linePosY-14, 8, 1);		
			g.fillRect(linePosX, linePosY, 8, 1);		
		}
	}

	public void canWriteText(boolean writeText) {
		this.writeText = writeText;
	}

	@Override
	public void addPoint(Point2D point) {
		// TODO Auto-generated method stub
		
	}
}



