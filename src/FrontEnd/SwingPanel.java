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
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.image.AffineTransformOp;
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
import Helpers.TestHelper;
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
				if (ball instanceof StalkBulletBall) {
					g2.setColor(Color.blue);
				}
				BufferedImage image = ball.getImage();
				if (image != null) {
					int paintX = ball.getX() - Config.DragonImageSize;
					int paintY = ball.getY() - Config.DragonImageSize * 2;
					paintX = Math.max(0,
							Math.min(Config.defaultOneSlotWidth, paintX));
					paintY = Math.max(0,
							Math.min(Config.defaultOneSlotHeight, paintY));
					g.drawImage(image, paintX, paintY, null);
				}
				BufferedImage healthImage = null;
				if (ball instanceof DragonBall) {
					healthImage = ((DragonBall) ball).getHealthImage();
					if (healthImage != null) {
						int paintX = ball.getX() - Config.DragonImageSize;
						int paintY = ball.getY() - Config.DragonImageSize * 2
								- 5;
						paintX = Math.max(0,
								Math.min(Config.defaultOneSlotWidth, paintX));
						paintY = Math.max(0,
								Math.min(Config.defaultOneSlotHeight, paintY));
						g.drawImage(healthImage, paintX, paintY, null);
					}
				}
				if (ball instanceof TowerBall) {
					if (((TowerBall) ball).createFlag == 0) {
						int paintX = ball.getX() + Config.DragonImageSize;
						int paintY = ball.getY() + Config.DragonImageSize - 5;
						paintX = Math.max(0,
								Math.min(Config.defaultOneSlotWidth, paintX));
						paintY = Math.max(0,
								Math.min(Config.defaultOneSlotHeight, paintY));
						((Graphics2D) g).setComposite(AlphaComposite
								.getInstance(AlphaComposite.SRC_OVER, 0.2f));
						g.drawOval(paintX - ((TowerBall) ball).getScope(),
								paintY - ((TowerBall) ball).getScope(),
								2 * ((TowerBall) ball).getScope(),
								2 * ((TowerBall) ball).getScope());
						g.fillOval(paintX - ((TowerBall) ball).getScope(),
								paintY - ((TowerBall) ball).getScope(),
								2 * ((TowerBall) ball).getScope(),
								2 * ((TowerBall) ball).getScope());
						((TowerBall) ball).createFlag = 1;
					}
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
			originalBackgroundImage = ImageIO.read(new File(
					Config.backgroundImagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (originalBackgroundImage != null) {
			backgroundImage = ImageHelper.resizeImage(Config.slotWidth,
					Config.slotHeight, originalBackgroundImage,
					originalBackgroundImage.getType());
		}
		for (int i = 0; i < Config.defaultOneSlotWidth; i += Config.slotWidth) {
			for (int j = 0; j < Config.defaultOneSlotHeight; j += Config.slotHeight) {
				if (backgroundImage != null) {
					g2d.drawImage(backgroundImage, i, j, null);
				}
				if ((i + Config.slotWidth) >= Config.defaultOneSlotWidth
						&& (j + Config.slotHeight) >= Config.defaultOneSlotHeight) {
					BufferedImage originalDestinationImage = null;
					BufferedImage destinationImage = null;
					try {
						originalDestinationImage = ImageIO.read(new File(
								Config.destinationImagePath));
					} catch (IOException e) {
						e.printStackTrace();
					}
					if (originalBackgroundImage != null) {
						destinationImage = ImageHelper.resizeImage(
								Config.slotWidth, Config.slotHeight,
								originalDestinationImage,
								originalDestinationImage.getType());
					}
					g2d.drawImage(destinationImage, i, j, null);
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
		if (originalBackgroundImage != null) {
			wallImage = ImageHelper.resizeImage(Config.slotWidth,
					Config.slotHeight, originalWallImage,
					originalWallImage.getType());
		}
		int[][] currentMap = GameInfo.currentMap;
		for (int i = 0; i < currentMap.length; i++) {
			for (int j = 0; j < currentMap[0].length; j++) {
				if (currentMap[i][j] == 0) {
				} else if (currentMap[i][j] == 1) {
					int height = Config.slotHeight;
					int width = Config.slotWidth;
					g2d.drawImage(wallImage, width * j, height * i, null);
				} else if (currentMap[i][j] < Config.TowerNumber) {
					int height = Config.slotHeight;
					int width = Config.slotWidth;
					g.drawImage(this.getMapImage(currentMap[i][j]), width * j,
							height * i, null);
				}
			}
		}
	}

	public BufferedImage getMapImage(int x) {
		if (x < 0 || x / 10 > MapData.mapImagePath.length)
			return null;
		String imagePath = MapData.mapImagePath[x / 10];
		if (imagePath != null && imagePath.length() > 0) {
			try {
				BufferedImage originalImage = ImageIO.read(new File(imagePath));
				BufferedImage resizedImage = ImageHelper.resizeImage(
						Config.slotWidth, Config.slotHeight, originalImage,
						originalImage.getType());
				if (x % 10 != 0) {
					return this.rotateimage(resizedImage, (x % 10) * 45);
				}
				return resizedImage;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public BufferedImage rotateimage(BufferedImage image, int angle) {
		double rotationRequired = Math.toRadians(angle);
		double locationX = image.getWidth() / 2;
		double locationY = image.getHeight() / 2;
		AffineTransform tx = AffineTransform.getRotateInstance(
				rotationRequired, locationX, locationY);
		AffineTransformOp op = new AffineTransformOp(tx,
				AffineTransformOp.TYPE_BILINEAR);
		return op.filter(image, null);
	}
}