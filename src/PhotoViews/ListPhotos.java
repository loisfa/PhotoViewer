package PhotoViews;
import java.awt.Image;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;


public class ListPhotos{

	private ArrayList<String> listUri = new ArrayList<String>();
	private ArrayList<Image> listImage = new ArrayList<Image>();

	public ListPhotos() {
		loadPhotos();
	}

	public void loadPhotos(){
		
		File[] list = listFilesMatching(new File("./photos/"), ".*jpg");
		for(File f: list){
			try {
				Image image = ImageIO.read(f);
				listImage.add(image);
				
				String uri = f.getPath();
				listUri.add(uri);
			}catch(IOException i) {
				i.printStackTrace();
				return;
			}
		}
	}
	
	public static File[] listFilesMatching(File root, String regex) {
	    if(!root.isDirectory()) {
	        throw new IllegalArgumentException(root+" is no directory.");
	    }
	    final Pattern p = Pattern.compile(regex); // careful: could also throw an exception!
	    return root.listFiles(new FileFilter(){
	        public boolean accept(File file) {
	            return p.matcher(file.getName()).matches();
	        }
	    });
	}
	
	// GETTERS
	public ArrayList<Image> getImages() {
		return  listImage;
	}
	public ArrayList<String> getUri() {
		return  listUri;
	}	
}
