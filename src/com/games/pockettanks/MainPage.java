package com.games.pockettanks;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainPage extends JPanel implements KeyListener, ActionListener {

	private static final long serialVersionUID = 1L;

	JButton leftFire, rightFire, start, refresh;
	JTextField startPlayer, startMsg;
	AudioInputStream launch;
	Image background, blackBackground;

	City city;
	Tanks tanks;
	Fire fire;

	int xLeft, yLeft, xRight, yRight, xBulletLeft, yBulletLeft, xBulletRight, yBulletRight;
	int leftAngle, rightAngle;
	boolean showBulletLeft, showBulletRight;

	int chance;
	int scoreLeft, scoreRight;

	public MainPage() {

		setLayout(null);

		chance = -2;

		xLeft = 20;
		yLeft = 400;

		xRight = 880;
		yRight = 400;

		leftAngle = 0;
		rightAngle = 0;

		xBulletLeft = xLeft + 40;
		xBulletRight = xRight + 45;

		yBulletLeft = projectileLeft(xBulletLeft, xLeft + 40, 80, -leftAngle);
		yBulletRight = projectileRight(xBulletRight, xRight + 45, 80, 180 - leftAngle);

		showBulletLeft = false;
		showBulletRight = false;

		scoreLeft = 100;
		scoreRight = 100;

		leftFire = new JButton("fire");
		rightFire = new JButton("fire");
		start = new JButton("start");
		refresh = new JButton("refresh");
		startMsg = new JTextField("Setup your tanks then press start");

		leftFire.setBounds(275, 510, 80, 30);
		rightFire.setBounds(875, 510, 80, 30);
		start.setBounds(500, 300, 200, 50);
		startMsg.setBounds(500, 50, 250, 30);
		refresh.setBounds(this.getWidth() - 80, 10, 50, 20);
		
		try {
			blackBackground =  ImageIO.read(new File("backgrounds/black.jpeg"));
			background = ImageIO.read(new File("backgrounds/redsky.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			launch = AudioSystem.getAudioInputStream(new File("sound/launch.wav"));
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		city = new City();
		tanks = new Tanks(xLeft, yLeft, xRight, yRight);
		fire = new Fire(xBulletLeft, yBulletLeft, xBulletRight, yBulletRight);

		addKeyListener(this);
		setFocusable(true);

		leftFire.addActionListener(this);
		rightFire.addActionListener(this);
		start.addActionListener(this);
		refresh.addActionListener(this);

		leftFire.setFocusable(true);
		rightFire.setFocusable(true);
		start.setFocusable(true);
		startMsg.setFocusable(true);
		refresh.setFocusable(true);

		add(leftFire);
		add(rightFire);
		add(start);
		add(startMsg);
		add(refresh);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0 ,0, this.getWidth(), 480, null);
		g.drawImage(blackBackground, 0 ,450, this.getWidth(), this.getHeight(), null);
		// g.drawLine(600, 0, 600, this.getHeight());
		DrawString.drawString(g, 275, 500, "Life : ");
		DrawString.drawString(g, 325, 500, scoreLeft);
		DrawString.drawString(g, 875, 500, "Life : ");
		DrawString.drawString(g, 925, 500, scoreRight);
		city.drawCity(g, this);
		tanks.drawLeftTank(g, xLeft, yLeft, this, leftAngle);
		tanks.drawRightTank(g, xRight, yRight, this, rightAngle);
		fire.drawBullet(g, this, xBulletLeft, yBulletLeft, showBulletLeft);
		fire.drawBullet(g, this, xBulletRight, yBulletRight, showBulletRight);
		if (scoreRight <= 0) {
			DrawString.drawString(g, 200, 50, "Congratualations!!");
			DrawString.drawString(g, 800, 50, "You have to take revenge!!");
		} else if (scoreLeft <= 0) {
			DrawString.drawString(g, 200, 50, "You have to take revenge!!");
			DrawString.drawString(g, 800, 50, "Congratualations!!");
		}
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_LEFT && (chance == 1 || chance == -2) && xRight > 825) {
			xRight = xRight - 2;
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT && (chance == 1 || chance == -2)
				&& xRight + 100 < this.getWidth()) {
			xRight = xRight + 2;
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_D && (chance == 0 || chance == -2) && xLeft + 100 < 335) {
			xLeft = xLeft + 2;
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_A && (chance == 0 || chance == -2) && xLeft > 0) {
			xLeft = xLeft - 2;
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_UP && rightAngle <= 90 && (chance == 1 || chance == -2)) {
			rightAngle = rightAngle + 1;
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN && rightAngle >= 0 && (chance == 1 || chance == -2)) {
			rightAngle = rightAngle - 1;
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_W && leftAngle >= -90 && (chance == 0 || chance == -2)) {
			leftAngle = leftAngle - 1;
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_S && leftAngle <= 0 && (chance == 0 || chance == -2)) {
			leftAngle = leftAngle + 1;
			repaint();
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE && chance == 0) {
			showBulletLeft = true;
			xBulletLeft = xLeft + 40;
			chance = -1;
			new Thread(() -> {
				while (true) {
					yBulletLeft = projectileLeft(xBulletLeft, xLeft + 40, 80, -leftAngle);

					if (xBulletLeft > this.getWidth() || yBulletLeft > 450 || city.collide(xBulletLeft, yBulletLeft)) {
						showBulletLeft = false;
						break;
					}

					if (tanks.collide(xRight, yRight, xBulletLeft, yBulletLeft)) {
						scoreRight -= 100;
						showBulletLeft = false;
						break;
					}

					repaint();
					xBulletLeft += 1;
					try {
						Thread.sleep(20);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				if (scoreRight <= 0) {
					chance = -1;
				} else {
					chance = 1;
				}
			}).start();

		} else if (e.getKeyCode() == KeyEvent.VK_ENTER && chance == 1) {
			showBulletRight = true;
			xBulletRight = xRight + 45;
			chance = -1;
			new Thread(() -> {
				while (true) {
					yBulletRight = projectileRight(xBulletRight, xRight + 45, 80, 180 - rightAngle);

					if (xBulletRight < 0 || yBulletRight > 450 || city.collide(xBulletRight, yBulletRight)) {
						showBulletRight = false;
						break;
					}

					if (tanks.collide(xLeft, yLeft, xBulletRight, yBulletRight)) {
						scoreLeft -= 100;
						showBulletRight = false;
						break;
					}

					repaint();
					xBulletRight -= 1;
					try {
						Thread.sleep(20);
					} catch (InterruptedException e1) {
						e1.printStackTrace();
					}
				}
				if (scoreLeft <= 0) {
					chance = -1;
				} else {
					chance = 0;
				}
			}).start();

		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			chance = (int) Math.round(Math.random());
			startMsg.setVisible(false);
			start.setVisible(false);
			startPlayer = (chance == 0) ? new JTextField("player A will start the game")
					: new JTextField("player B will start the game");
			startPlayer.setBounds(500, 50, 200, 30);
			this.add(startPlayer);
			startPlayer.setVisible(true);
			startPlayer.setFocusable(true);
			new Thread(() -> {
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
				startPlayer.setVisible(false);
			}).start();
		}
		if (e.getSource() == leftFire && chance == 0) {
			leftFireClick();
		} else if (e.getSource() == rightFire && chance == 1) {
			rightFireClick();
		} else if(e.getSource() == refresh) {
			repaint();
		}
	}

	public int projectileLeft(int x, int x1, double u, int angle) {
		return (int) (-(x - x1) * Math.tan(Math.toRadians(angle)) + (20 * ((x - x1) * (x - x1))
				/ (2 * u * u * Math.cos(Math.toRadians(angle))) * Math.cos(Math.toRadians(angle))) + 430);
	}

	public int projectileRight(int x, int x1, double u, int angle) {
		return (int) (-(x - x1) * Math.tan(Math.toRadians(angle)) + (20 * ((x - x1) * (x - x1))
				/ (2 * u * u * Math.cos(Math.toRadians(angle))) * Math.cos(Math.toRadians(angle))) + 430);
	}

	public void leftFireClick() {
		showBulletLeft = true;
		xBulletLeft = xLeft + 40;
		chance = -1;
		new Thread(() -> {
			while (true) {
				yBulletLeft = projectileLeft(xBulletLeft, xLeft + 40, 80, -leftAngle);

				if (xBulletLeft > this.getWidth() || yBulletLeft > 450 || city.collide(xBulletLeft, yBulletLeft)) {
					showBulletLeft = false;
					break;
				}

				if (tanks.collide(xRight, yRight, xBulletLeft, yBulletLeft)) {
					scoreRight -= 100;
					showBulletLeft = false;
					break;
				}

				repaint();
				xBulletLeft += 1;
				try {
					Thread.sleep(20);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			if (scoreRight <= 0) {
				chance = -1;
			} else {
				chance = 1;
			}
		}).start();
		leftFire.setFocusable(false);
	}

	public void rightFireClick() {
		showBulletRight = true;
		xBulletRight = xRight + 45;
		chance = -1;
		new Thread(() -> {
			while (true) {
				yBulletRight = projectileRight(xBulletRight, xRight + 45, 80, 180 - rightAngle);

				if (xBulletRight < 0 || yBulletRight > 450 || city.collide(xBulletRight, yBulletRight)) {
					showBulletRight = false;
					break;
				}

				if (tanks.collide(xLeft, yLeft, xBulletRight, yBulletRight)) {
					scoreLeft -= 100;
					showBulletRight = false;
					break;
				}

				repaint();
				xBulletRight -= 1;
				try {
					Thread.sleep(20);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
			if (scoreLeft <= 0) {
				chance = -1;
			} else {
				chance = 0;
			}
		}).start();
		rightFire.setFocusable(false);
	}

}
