package FrontEnd;

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
import javax.swing.JPanel;

import Controller.PostMan;
import FrontEnd.Balls.*;
import Helpers.Config;
import Helpers.GameManager;
import Helpers.TestHelper;

public class SwingFrame extends JFrame {

	private static String buttonName = null;

	public static void main(String[] args) {
		JFrame frame = new SwingFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	public SwingFrame() {
		GameInfo.load(this);
	}

	protected void addComponents() {
		JPanel buttonPanel = new JPanel();
		for (final String buttonName : Config.activeballButtons) {
			addButton(buttonPanel, buttonName + "Ball", new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					SwingFrame.buttonName = buttonName;
				} 
			});
		}
		for (final String buttonName : Config.towerButtons) {
			addButton(buttonPanel, buttonName, new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					SwingFrame.buttonName = buttonName;
				} 
			});
		}

		for (final String buttonName : Config.otherButtons) {
			if(buttonName.equals("Start")){
				addButton(buttonPanel, buttonName, new ActionListener() {
					public void actionPerformed(ActionEvent event) {
						GameInfo.startTD();
					} 
				});
				continue;
			}
			addButton(buttonPanel, buttonName, new ActionListener() {
				public void actionPerformed(ActionEvent event) {
					SwingFrame.buttonName = buttonName;
				} 
			});
		}

		add(buttonPanel, BorderLayout.SOUTH);
		addMouseListener(new MouseHandler());
		addMouseMotionListener(new MouseMotionHandler());
	}

	private void addButton(Container c, String title, ActionListener listener) {
		JButton button = new JButton(title);
		c.add(button);
		button.addActionListener(listener);
	}

//	private void lazyAddMouseListener() {
//		Toolkit.getDefaultToolkit().addAWTEventListener(new AWTEventListener() {
//			public void eventDispatched(AWTEvent event) {
//				if (event instanceof MouseEvent) {
//					MouseEvent evt = (MouseEvent) event;
//					if (evt.getID() == MouseEvent.MOUSE_CLICKED) {
//						if (buttonName == null)
//							return;
//						GameManager gameManager = GameManager.getInstance();
//						gameManager.addBall(buttonName, (int) evt.getPoint()
//								.getX(), (int) evt.getPoint().getY());
//
//					}
//				}
//			}
//		}, AWTEvent.MOUSE_EVENT_MASK);
//
//	}

	private class MouseHandler extends MouseAdapter {
		public void mousePressed(MouseEvent event) {
			if(SwingFrame.buttonName != null){
				GameManager gameManager = new GameManager();
				gameManager.addBall(SwingFrame.buttonName, event.getX(), event.getY());
			}
		}

		public void mouseClicked(MouseEvent event) {
			MouseEvent evt = (MouseEvent) event;
			System.out.println((int) evt.getPoint().getX() + "   "  +  (int) evt.getPoint().getY());
		}
	}

	private class MouseMotionHandler implements MouseMotionListener {
		public void mouseMoved(MouseEvent event) {
		}

		public void mouseDragged(MouseEvent event) {
		}
	}

}