package swingFrontEnd;

import java.awt.AWTEvent;
import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Toolkit;
import java.awt.event.AWTEventListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import worker.Executor;

import balls.*;

import Controller.PostMan;
import Helpers.Config;
import Helpers.GameManager;
import Helpers.TestHelper;
import Invoke.Setup;
import Request.Requester;
import Simulator.Simulator;

public class SwingFrame extends JFrame {

	private static String buttonName = null;
	public static MouseEvent lastMouseClickedEvent;
	public static JLabel goldLabel = null;
	public static JLabel lostLabel = null;
	public static JLabel killDragonLabel = null;

//	public static void main(String[] args) {
//		JFrame frame = new SwingFrame();
//		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		frame.setVisible(true);
//	}


	public SwingFrame() {
		GameInfo.load(this);
	}

	protected void addComponents() {
		JPanel buttonPanel = new JPanel();
//		for (final String buttonName : Config.activeballButtons) {
//			addButton(buttonPanel, buttonName + "Ball", new ActionListener() {
//				public void actionPerformed(ActionEvent event) {
//					SwingFrame.buttonName = buttonName;
//				}
//			});
//		}
//		for (final String buttonName : Config.towerButtons) {
//			addButton(buttonPanel, buttonName, new ActionListener() {
//				public void actionPerformed(ActionEvent event) {
//					SwingFrame.buttonName = buttonName;
//				}
//			});
//		}
//
//		for (final String buttonName : Config.otherButtons) {
//			if (buttonName.equals("Start")) {
//				addButton(buttonPanel, buttonName, new ActionListener() {
//					public void actionPerformed(ActionEvent event) {
//						GameInfo.startTD();
//					}
//				});
//				continue;
//			}
//			if (buttonName.equals("Hard")) {
//				addButton(buttonPanel, buttonName, new ActionListener() {
//					public void actionPerformed(ActionEvent event) {
//						GameManager.getInstance().hardGenerateDragons(20,
//								20, 0);
//					}
//				});
//				continue;
//			}
//			if (buttonName.equals("Random")) {
//				addButton(buttonPanel, buttonName, new ActionListener() {
//					public void actionPerformed(ActionEvent event) {
//						GameManager.getInstance().randomeGenerateDragons(10);
//					}
//				});
//				continue;
//			}
//			addButton(buttonPanel, buttonName, new ActionListener() {
//				public void actionPerformed(ActionEvent event) {
//					SwingFrame.buttonName = buttonName;
//				}
//			});
//		}
		
		final String startButtonName = Config.startButtonName;
		final String simulatorButtonName = Config.simulatorButtonName;
		
		addButton(buttonPanel, startButtonName, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Setup.startServer();
			}
		});
		
		addButton(buttonPanel, simulatorButtonName, new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Setup.startSimulator();
			}
		});

//		addMouseListener(new MouseHandler());
//		addMouseMotionListener(new MouseMotionHandler());

		JPanel textPanel = new JPanel();
		goldLabel = new JLabel("Gold: " + Config.gold);
		textPanel.add(goldLabel, BorderLayout.CENTER);
		
		lostLabel = new JLabel("LostDragons: " + Config.lostDragon);
		textPanel.add(lostLabel, BorderLayout.CENTER);
		
		killDragonLabel = new JLabel("KillDragons: " + Config.killDragons);
		textPanel.add(killDragonLabel, BorderLayout.CENTER);
		
		JPanel southPanel = new JPanel();
		southPanel.add(buttonPanel, BorderLayout.NORTH);
		southPanel.add(textPanel, BorderLayout.SOUTH);
		add(southPanel, BorderLayout.SOUTH);

	}


	private void addButton(Container c, String title, ActionListener listener) {
		JButton button = new JButton(title);
		c.add(button);
		button.addActionListener(listener);
	}

	private class MouseHandler extends MouseAdapter {
		public void mousePressed(MouseEvent event) {
			if (SwingFrame.buttonName != null) {
				GameManager gameManager = GameManager.getInstance();
				gameManager.addBall(SwingFrame.buttonName, event.getX(),
						event.getY());
			}
		}

		public void mouseClicked(MouseEvent event) {
			SwingFrame.lastMouseClickedEvent = event;
		}

		public void mouseReleased(MouseEvent event) {

		}

	}

	private class MouseMotionHandler implements MouseMotionListener {
		public void mouseMoved(MouseEvent event) {
			// TestHelper.print("moving " + event.getX() + " " + event.getY());

		}

		public void mouseDragged(MouseEvent event) {
			// TestHelper.print("dragging " + event.getX() + " " +
			// event.getY());
			if (SwingFrame.buttonName != null) {
				GameManager gameManager = GameManager.getInstance();
				gameManager.addBall(SwingFrame.buttonName, event.getX(),
						event.getY());
			}
		}
	}

}