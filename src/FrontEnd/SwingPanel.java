package FrontEnd;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import FrontEnd.Balls.*;
import Helpers.Config;
import Helpers.ImageHelper;
import Helpers.MapData;

public class SwingPanel extends JPanel {

	public SwingPanel() {
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		for (Ball ball : GameInfo.balls) {
			g2.fill((Shape) ball.getShape());
			BufferedImage image = ball.getImage();
			if (image != null) {
				g.drawImage(image, ball.getX(), ball.getY(), null);
			}
			BufferedImage healthImage = null;
			if(ball instanceof ActiveBall){
				healthImage = ((ActiveBall) ball).getHealthImage();
				if (healthImage != null) {
					g.drawImage(healthImage, ball.getX(), ball.getY() - 5, null);
				}
			}
		}
		this.paintMap(g);
	}

	public void paintMap(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;

		for (int i = 0; i <= Config.defaultOneSlotHeight; i += Config.slotHeight) {
			g2d.drawLine(0, i, Config.defaultOneSlotWidth, i);
		}

		for (int i = 0; i <= Config.defaultOneSlotWidth; i += Config.slotWidth) {
			g2d.drawLine(i, 0, i, Config.defaultOneSlotHeight);
		}
		int[][] currentMap = GameInfo.currentMap;
		for (int i = 0; i < currentMap.length; i++) {
			for (int j = 0; j < currentMap[0].length; j++) {
				if (currentMap[i][j] == 0) {
				} else if (currentMap[i][j] == 1) {
					int height = Config.slotHeight;
					int width = Config.slotWidth;
					g2d.fillRect(width * j, height * i, width, height);
				} else if (currentMap[i][j] < 10) {
					int height = Config.slotHeight;
					int width = Config.slotWidth;
					g.drawImage(this.getMapImage(currentMap[i][j]), width * j,
							height * i, null);
				}
			}
		}
	}

	public BufferedImage getMapImage(int x) {
		if (x < 0 || x > MapData.mapImagePath.length)
			return null;
		String imagePath = MapData.mapImagePath[x];
		if (imagePath != null) {
			try {
				BufferedImage originalImage = ImageIO.read(new File(imagePath));
				return ImageHelper.resizeImage(Config.slotWidth,
						Config.slotHeight, originalImage,
						originalImage.getType());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}