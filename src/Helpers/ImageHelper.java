package Helpers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public class ImageHelper{
    public static BufferedImage resizeImage(int width, int height, BufferedImage originalImage, int type){
    	if(width == 0)
    		width = 1;
		BufferedImage resizedImage = new BufferedImage(width, height, type);
		Graphics2D g = resizedImage.createGraphics();
		g.drawImage(originalImage, 0, 0, width, height, null);
		g.dispose();
	 
		return resizedImage;
    }
	
}