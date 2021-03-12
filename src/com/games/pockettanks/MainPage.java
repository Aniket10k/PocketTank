package com.games.pockettanks;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class MainPage extends JPanel implements KeyListener, ActionListener, ChangeListener {

	private static final long serialVersionUID = 1L;

	JButton leftFire, rightFire, start, refresh, restart;
	int msg;
	AudioInputStream launch;
	Image background, blackBackground;
	JSlider leftSlider, rightSlider;

	City city;
	Tanks tanks;
	Fire fire;

	int points;

	int xLeft, yLeft, xRight, yRight, xBulletLeft, yBulletLeft, xBulletRight, yBulletRight;
	int leftAngle, rightAngle;
	boolean showBulletLeft, showBulletRight;
	int leftVelocity, rightVelocity;

	int chance;
	int scoreLeft, scoreRight;

	public MainPage() {

		setLayout(null);

		points = 20;

		chance = -2;
		msg = 0;

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
		restart = new JButton("restart");
		leftSlider = new JSlider(JSlider.HORIZONTAL, 0, 150, 75);
		rightSlider = new JSlider(JSlider.HORIZONTAL, 0, 150, 75);

		leftFire.setBounds(275, 510, 80, 30);
		rightFire.setBounds(875, 510, 80, 30);
		start.setBounds(500, 300, 200, 50);
		refresh.setBounds(100, 0, 100, 20);
		restart.setBounds(0, 0, 100, 20);
		leftSlider.setBounds(100, 460, 300, 30);
		rightSlider.setBounds(700, 460, 300, 30);

		leftSlider.setMinorTickSpacing(1);
		leftSlider.setMajorTickSpacing(10);
		leftSlider.setSnapToTicks(true);
		leftSlider.setPaintLabels(true);
		leftSlider.setFont(new Font("monospace", Font.ITALIC, 10));

		rightSlider.setMinorTickSpacing(1);
		rightSlider.setMajorTickSpacing(10);
		rightSlider.setPaintLabels(true);
		rightSlider.setSnapToTicks(true);
		rightSlider.setFont(new Font("monospace", Font.ITALIC, 10));

		try {
			blackBackground = ImageIO.read(Main.class.getResource("/resources/backgrounds/black.jpeg"));
			background = ImageIO.read(Main.class.getResource("/resources/backgrounds/redsky.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			launch = AudioSystem.getAudioInputStream(Main.class.getResource("/resources/sound/launch.wav"));
		} catch (UnsupportedAudioFileException | IOException e) {
			e.printStackTrace();
		}
		city = new City();
		tanks = new Tanks(xLeft, yLeft, xRight, yRight);
		fire = new Fire(xBulletLeft, yBulletLeft, xBulletRight, yBulletRight);

		this.addKeyListener(this);

		leftFire.addActionListener(this);
		rightFire.addActionListener(this);
		start.addActionListener(this);
		refresh.addActionListener(this);
		restart.addActionListener(this);
		leftSlider.addChangeListener(this);
		rightSlider.addChangeListener(this);

		leftFire.setFocusable(true);
		rightFire.setFocusable(true);
		start.setFocusable(true);
		refresh.setFocusable(false);
		restart.setFocusable(false);
		leftSlider.setFocusable(false);
		rightSlider.setFocusable(false);
		this.setFocusable(true);

		add(leftFire);
		add(rightFire);
		add(start);
		add(refresh);
		add(restart);
		add(leftSlider);
		add(rightSlider);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, this.getWidth(), 480, null);
		g.drawImage(blackBackground, 0, 450, this.getWidth(), this.getHeight(), null);
		g.drawLine(600, 450, 600, this.getHeight());
		drawString(g);
		drawString(g, "A", "B");
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

	private void drawString(Graphics g, String A, String B) {
		if (msg == 0) {
			String m = "Fix your positions!!";
			DrawString.drawString(g, 600 - m.length()*3, 100, m);
		} else if (msg == 1) {
			String m = A + " it is your change to destroy your opponent!!";
			DrawString.drawString(g, 600 - m.length()*3, 100, m);
		} else if (msg == -1) {
			String m = B + " fire and take revenge !!";
			DrawString.drawString(g, 600 - m.length()*3, 100, m);
		}
	}

	private void drawString(Graphics g) {
		DrawString.drawString(g, 30, 480, "Speed : ");
		DrawString.drawString(g, 630, 480, "Speed : ");
		DrawString.drawString(g, 450, 480, "Life : ");
		DrawString.drawString(g, 500, 480, scoreLeft);
		DrawString.drawString(g, 1050, 480, "Life : ");
		DrawString.drawString(g, 1100, 480, scoreRight);
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
			leftFireClick();
		} else if (e.getKeyCode() == KeyEvent.VK_ENTER && chance == 1) {
			rightFireClick();
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {

	}

	@Override
	public void keyTyped(KeyEvent e) {

	}

	@Override
	public void stateChanged(ChangeEvent e) {
		JSlider source = (JSlider) e.getSource();
		if (source == leftSlider) {
			System.out.println("leftSlider");
			// if (!source.getValueIsAdjusting()) {
			leftVelocity = (int) source.getValue();
			leftSlider.setFocusable(false);
			// }
		} else if (source == rightSlider) {
			System.out.println("rightSlider");
			// if (!source.getValueIsAdjusting()) {
			rightVelocity = (int) source.getValue();
			rightSlider.setFocusable(false);
			// }
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == start) {
			repaint();
			leftSlider.setValue(leftSlider.getValue() + 1);
			leftSlider.setValue(leftSlider.getValue() - 1);
			rightSlider.setValue(rightSlider.getValue() + 1);
			rightSlider.setValue(rightSlider.getValue() - 1);
			System.out.println("start");
			chance = (int) Math.round(Math.random());
			start.setVisible(false);
			if (chance == 0) {
				msg = 1;
			} else if (chance == 1) {
				msg = -1;
			}
			repaint();
		}
		if (e.getSource() == leftFire && chance == 0) {
			System.out.println("leftFire");
			leftFireClick();
			leftFire.setFocusable(false);
		} else if (e.getSource() == rightFire && chance == 1) {
			System.out.println("rightFire");
			rightFireClick();
			rightFire.setFocusable(false);
		} else if (e.getSource() == refresh) {
			System.out.println("refresh");
			repaint();
		} else if (e.getSource() == restart) {
			System.out.println("restart");
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
		msg = 3;
		new Thread(() -> {
			while (true) {
				yBulletLeft = projectileLeft(xBulletLeft, xLeft + 40, leftVelocity, -leftAngle);

				if (xBulletLeft > this.getWidth() || yBulletLeft > 450 || city.collide(xBulletLeft, yBulletLeft)) {
					showBulletLeft = false;
					break;
				}

				if (tanks.collide(xRight, yRight, xBulletLeft, yBulletLeft)) {
					scoreRight -= points;
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
			msg = -1;
		}).start();

	}

	public void rightFireClick() {
		showBulletRight = true;
		xBulletRight = xRight + 45;
		chance = -1;
		msg = 3;
		new Thread(() -> {
			while (true) {
				yBulletRight = projectileRight(xBulletRight, xRight + 45, rightVelocity, 180 - rightAngle);

				if (xBulletRight < 0 || yBulletRight > 450 || city.collide(xBulletRight, yBulletRight)) {
					showBulletRight = false;
					break;
				}

				if (tanks.collide(xLeft, yLeft, xBulletRight, yBulletRight)) {
					scoreLeft -= points;
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
			msg = 1;
		}).start();

	}

}
