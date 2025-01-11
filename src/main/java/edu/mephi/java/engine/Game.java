package edu.mephi.java.engine;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

// TODO допишите все необходимые сущности для игры
public class Game extends JPanel implements ActionListener {
	private final int WIDTH = 600;
	private final int HEIGHT = 600;
	private boolean gameOver = false;
	private final GameControl mainControl;
	private final Timer timer;

	public Game() {
		setPreferredSize(new Dimension(WIDTH , HEIGHT));
		//setBackground(Color.BLACK);
		setFocusable(true);
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				System.out.println("You pressed " + e.getKeyChar() + " key!");
			}
		});
		timer = new Timer(100, this);
		timer.start();

		mainControl = new GameControl();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	}

	public void restart() {
		timer.start();
		repaint();
	}

	public GameControl getMainControl() {
		return mainControl;
	}

	public boolean isGameOver() {
		return gameOver;
	}

}
