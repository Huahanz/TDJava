package FrontEnd;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JLabel;
import javax.swing.JPanel;

import FrontEnd.Balls.*;
import Helpers.Config;
import Helpers.ImageHelper;
import Helpers.MapData;
import FrontEnd.SwingFrame;

public class SwingPanel extends JPanel {

	public SwingPanel() {
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.paintMap(g);
		
		Graphics2D g2 = (Graphics2D) g;
		for (int i = 0; i < GameInfo.balls.size(); i++) {
			Ball ball = GameInfo.balls.get(i);
			if (ball != null) {
				g2.fill((Shape) ball.getShape());
				BufferedImage image = ball.getImage();
				if (image != null) {
					g.drawImage(image, ball.getX(), ball.getY(), null);
				}
			}
			BufferedImage healthImage = null;
			if(ball instanceof DragonBall){
				healthImage = ((DragonBall) ball).getHealthImage();
				if (healthImage != null) {
					g.drawImage(healthImage, ball.getX(), ball.getY() - 5, null);
				}
			}
			if(ball instanceof TowerBall){
				if(((TowerBall) ball).createFlag == 0){
					((Graphics2D) g).setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.2f));
					g.drawOval(((TowerBall) ball).getX() - ((TowerBall) ball).getScope(), ((TowerBall) ball).getY() - ((TowerBall) ball).getScope(), 2 * ((TowerBall) ball).getScope(), 2 * ((TowerBall) ball).getScope());
					g.fillOval(((TowerBall) ball).getX() - ((TowerBall) ball).getScope(), ((TowerBall) ball).getY() - ((TowerBall) ball).getScope(), 2 * ((TowerBall) ball).getScope(), 2 * ((TowerBall) ball).getScope());
					((TowerBall) ball).createFlag = 1;
				}
			}
		}
		
		SwingFrame.goldLabel.setText("Gold: " + Config.gold);
	}

	public void paintMap(Graphics g) {
		Graphics2D g2d = (Graphics2D) g;
		BufferedImage originalBackgroundImage = null;
		BufferedImage backgroundImage = null;
		try {
			originalBackgroundImage = ImageIO.read(new File(Config.backgroundImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(originalBackgroundImage != null){
			backgroundImage = ImageHelper.resizeImage(Config.slotWidth, Config.slotHeight, originalBackgroundImage, originalBackgroundImage.getType());
		}
		for (int i = 0; i < Config.defaultOneSlotWidth; i += Config.slotWidth) {
			for (int j = 0; j < Config.defaultOneSlotHeight; j += Config.slotHeight){
				if(backgroundImage != null){
					g2d.drawImage(backgroundImage, i, j, null);
				}
			}
		}
		
		BufferedImage originalWallImage = null;
		BufferedImage wallImage = null;
		try {
			originalWallImage = ImageIO.read(new File(Config.wallImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(originalBackgroundImage != null){
			wallImage = ImageHelper.resizeImage(Config.slotWidth, Config.slotHeight, originalWallImage, originalWallImage.getType());
		}
		int[][] currentMap = GameInfo.currentMap;
		for (int i = 0; i < currentMap.length; i++) {
			for (int j = 0; j < currentMap[0].length; j++) {
				if (currentMap[i][j] == 0) {
				} else if (currentMap[i][j] == 1) {
					int height = Config.slotHeight;
					int width = Config.slotWidth;
					g2d.drawImage(wallImage, width * j, height * i, null);
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