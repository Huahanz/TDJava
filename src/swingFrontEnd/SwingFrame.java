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

import Helpers.Config;
import Helpers.GameManager;
import Helpers.TestHelper;
import Invoke.Setup;
import Request.Requester;
import Simulator.Simulator;

public class SwingFrame extends JFrame {

	public static JLabel goldLabel = null;
	public static JLabel lostLabel = null;
	public static JLabel killDragonLabel = null;

	public SwingFrame() {
		GameInfo.load(this);
	}

	protected void addComponents() {
		JPanel buttonPanel = new JPanel();
		
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

}