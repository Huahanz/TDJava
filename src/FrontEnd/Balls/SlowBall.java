package FrontEnd.Balls;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import Helpers.ImageHelper;

public class SlowBall extends ActiveBall {
	public SlowBall(int x, int y, int XIZE, int YSIZE, int stepLength,
			String imagePath) {
		super(x, y, XIZE, YSIZE, stepLength, imagePath);
	}

	public SlowBall(int x, int y) {
		this(x, y, 20, 25, 10, "/Users/huahan/Pictures/SlowBall.jpg");
	}
	public SlowBall(){
		this(550, 330);
	}
	public void randomWalk() {
		this.x *= 1.1;
		this.y *= 1.2;
	}

	public BufferedImage getImage() {
		if (this.getImagePath() == null)
			return null;
		if (this.image == null) {
			try {
				BufferedImage originalImage = ImageIO.read(new File(this
						.getImagePath()));
				this.image = ImageHelper.resizeImage(40, 40, originalImage,
						originalImage.getType());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return this.image;
	}
}