package PhotoViews;

import java.awt.Dimension;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class MyImage {
	
	private boolean isImage; // rename : isLoaded() ?
	private Image image;
	private int originalWidth;
	private int originalHeight;
	private int width;
	private int height;
	
	public MyImage() {}
	
	public Image getImage() {
		return this.image;
	}
	public boolean isImage() {
		return isImage;
	}
	public int getWidth() {
		return this.width;
	}
	public int getHeight() {
		return this.height;
	}
	public int getOriginalWidth() {
		return this.originalWidth;
	}
	public int getOriginalHeight() {
		return this.originalHeight;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	public void setHeight(int height) {
		this.height = height;
	}

	public void setImage(BufferedImage buffImage) {
		// a buffered image is necessary to get the real (and updated) dimension
		this.image = buffImage;
		this.isImage = true;
		this.originalWidth = buffImage.getWidth();
		this.originalHeight = buffImage.getHeight();
		this.width = this.originalWidth;
		this.height = this.originalHeight;	
	}
	

	public void deleteImage() {
		this.isImage=false;
	}	
	

}
