package week09.thumbnail_generator;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;


public class ThumbnailGenerator {

	public static void gen(String sorceFile, String destFile, int with, int high) throws IOException {
			File sourceImageFile = new File(sorceFile);
			BufferedImage img = ImageIO.read(sourceImageFile);
			
			Image scaledImg = img.getScaledInstance(with, high, Image.SCALE_SMOOTH);

			BufferedImage thumbnail = new BufferedImage(with, high, BufferedImage.TYPE_INT_RGB);
			thumbnail.createGraphics().drawImage(scaledImg,0,0,null);
			
			ImageIO.write(thumbnail, "jpg", new File(destFile));
	}

	public static void main(String[] args) throws IOException {
		ThumbnailGenerator.gen("/home/angel/images/PA260029.JPG","/home/angel/images/PA260029_resize.jpg" , 150, 150);
	}
	
}
