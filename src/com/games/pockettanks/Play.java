package com.games.pockettanks;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Stroke;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.Timer;

public class Play extends JFrame implements KeyListener {

	private static final long serialVersionUID = 1L;
	private int position1, position2;
	private double angle1, angle2;
	private BufferedImage img1, img4, img6, img7, img2, img3, img5;
	private JButton b1, b2;
	private int score, score1;

	private int xPos, xPos2, yPos, yPos2, count1, count2;
	private String player1 = "Aniket";
	private String player2 = "Ajinkya";
	private boolean bool1, bool2;

	public Play(String p, String q) throws Exception {
		player1 = p;
		player2 = q;
		setLayout(null);

		setDefaultCloseOperation(EXIT_ON_CLOSE);
		String soundName = "/resources/sound/d.wav";
		String sound2 = "/resources/sound/sound1.wav";
		AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(soundName).getAbsoluteFile());
		AudioInputStream audioInputStream2 = AudioSystem.getAudioInputStream(new File(sound2).getAbsoluteFile());
		Clip clip = AudioSystem.getClip();
		Clip clip2 = AudioSystem.getClip();
		clip.open(audioInputStream);
		clip2.open(audioInputStream2);
		clip.start();
		count1 = 0;
		count2 = 0;
		position1 = 0;
		position2 = 1250;
		score = 0;
		score1 = 0;
		angle1 = 0;
		angle2 = 180;
		b1 = new JButton("Fire");
		b2 = new JButton("Fire");
		
		b1.setFont(new Font("Times New Roman", 25, 20));
		b2.setFont(new Font("Times New Roman", 25, 20));
		b1.setBounds(60, 700, 100, 30);
		b2.setBounds(1200, 700, 100, 30);
		add(b1);
		add(b2);
		b1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clip2.start();
				clip2.setMicrosecondPosition(0);
				count1++;
				xPos = position1 + 58;
				Timer timer = new Timer(5, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (xPos < 1400) {
							xPos += 1;
							yPos = (int) (607 - (xPos - (58 + position1)) * Math.tan(Math.toRadians(angle1))
									+ ((9.81 * (xPos - (58 + position1)) * (xPos - (58 + position1))) / (2 * 12500
											* Math.cos(Math.toRadians(angle1)) * Math.cos(Math.toRadians(angle1)))));
						} else {
							bool1 = false;
							repaint();
							((Timer) e.getSource()).stop();
						}
						bool1 = true;
						repaint();
					}
				});
				timer.setRepeats(true);
				timer.setCoalesce(false);
				timer.start();
			}
		});
		b2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				count2++;
				clip2.setMicrosecondPosition(0);
				clip2.start();
				xPos2 = -position2 - 48;
				Timer timer = new Timer(5, new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if (xPos2 <= 0) {
							xPos2 += 1;
							yPos2 = (int) (607 + (xPos2 + position2 + 48) * Math.tan(Math.toRadians(angle2))
									+ ((9.81 * (xPos2 + position2 + 48) * (xPos2 + position2 + 48)) / (2 * 12500
											* Math.cos(Math.toRadians(angle2)) * Math.cos(Math.toRadians(angle2)))));
						} else {
							bool2 = false;
							repaint();
							((Timer) e.getSource()).stop();
						}
						bool2 = true;
						repaint();
					}
				});
				timer.setRepeats(true);
				timer.setCoalesce(false);
				timer.start();
			}
		});
		img1 = ImageIO.read(new File("/resources/images/tankfinal.png"));
		img2 = ImageIO.read(new File("/resources/images/tankfinal2.png"));
		img3 = ImageIO.read(new File("/resources/images/finalgun1.png"));
		img4 = ImageIO.read(new File("/resources/images/finalgun2.png"));
		img5 = ImageIO.read(new File("/resources/images/finalground1.png"));
		img6 = ImageIO.read(new File("/resources/images/hill.png"));
		img7 = ImageIO.read(new File("/resources/images/back4.png"));
		addKeyListener(this);
		b1.setFocusable(true);
		b2.setFocusable(true);
		setLayout(null);
		setSize(1500, 900);
		setVisible(true);
		setResizable(true);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_D) {
			// position1=position1+1;
			if (position1 < 226) {
				position1 = position1 + 1;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_A) {
			position1 = position1 - 1;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (position2 > 1070) {
				position2 = position2 - 1;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (position2 < 1300) {
				position2 = position2 + 1;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_1) {
			angle1 = angle1 - 1;
		} else if (e.getKeyCode() == KeyEvent.VK_2) {
			angle1 = angle1 + 1;
		} else if (e.getKeyCode() == KeyEvent.VK_0) {
			angle2 = angle2 - 1;
		} else if (e.getKeyCode() == KeyEvent.VK_9) {
			angle2 = angle2 + 1;
		}
		repaint();
	}

	public void paint(Graphics g) {

		super.paint(g);
		if (bool1) {
			g.setColor(Color.red);
			g.fillOval(xPos, yPos, 10, 10);
		}
		if (bool2) {
			g.setColor(Color.black);
			g.fillOval(Math.abs(xPos2), yPos2, 10, 10);
		}
		g.setColor(Color.blue);
		g.drawImage(img7, 0, 0, this);
		g.drawImage(img5, 0, 645, this);
		g.drawImage(img6, 300, 315, this);
		g.drawImage(img1, position1, 600, this);
		g.drawImage(img2, position2, 600, this);
		g.drawImage(img3, (position1 + 58), 600, this);

		Graphics2D g2d = (Graphics2D) g.create();

		Stroke dashed = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[] { 4 }, 0);
		g2d.setStroke(dashed);
		g2d.drawLine(position1 + 58, 607, (int) (position1 + 58 + 250 * Math.cos(Math.toRadians(-angle1))),
				(int) (607 + 250 * Math.sin(Math.toRadians(-angle1))));
		g2d.drawLine(position2 + 45, 607, (int) (position2 + 45 + 250 * Math.cos(Math.toRadians(-angle2))),
				(int) (607 + 250 * Math.sin(Math.toRadians(-angle2))));
		g2d.dispose();
		g.drawImage(img4, (position2 - 42), 600, this);
		if (((xPos >= 325 && xPos <= 1052) && (yPos >= 607 && yPos <= 644))
				|| ((xPos >= 359 && xPos <= 924) && (yPos >= 505 && yPos <= 607))
				|| ((xPos >= 454 && xPos <= 841) && (yPos >= 444 && yPos <= 505))
				|| ((xPos >= 578 && xPos <= 794) && (yPos >= 420 && yPos <= 444))
				|| ((xPos >= 601 && xPos <= 716) && (yPos >= 329 && yPos <= 420))) {
			xPos = 1444;
			yPos = 1444;

		}

		if (((Math.abs(xPos2) >= 325 && Math.abs(xPos2) <= 1052) && (Math.abs(yPos2) >= 607 && Math.abs(yPos2) <= 644))
				|| ((Math.abs(xPos2) >= 359 && Math.abs(xPos2) <= 924)
						&& (Math.abs(yPos2) >= 505 && Math.abs(yPos2) <= 607))
				|| ((Math.abs(xPos2) >= 454 && Math.abs(xPos2) <= 841)
						&& (Math.abs(yPos2) >= 444 && Math.abs(yPos2) <= 505))
				|| ((Math.abs(xPos2) >= 578 && Math.abs(xPos2) <= 794)
						&& (Math.abs(yPos2) >= 420 && Math.abs(yPos2) <= 444))
				|| ((Math.abs(xPos2) >= 601 && Math.abs(xPos2) <= 716)
						&& (Math.abs(yPos2) >= 329 && Math.abs(yPos2) <= 420))) {
			xPos2 = 1444;
			yPos2 = 1444;

		}
		// score
		g.setColor(Color.RED);
		g.setFont(new Font("Latin", 100, 25));
		String s = Integer.toString(score);
		g.drawString(player1, 50, 150);
		g.drawString("SCORE:" + s, 50, 100);

		if (new Rectangle(xPos, yPos, 15, 15).intersects(new Rectangle(position2, 600, 100, 50))) {
			g.setColor(Color.RED);
			score++;
		}

		g.setColor(Color.RED);
		g.setFont(new Font("Latin", 100, 25));
		String s1 = Integer.toString(score1);
		g.drawString(player2, 1200, 150);
		g.drawString("SCORE:" + s1, 1200, 100);
		if (new Rectangle(Math.abs(xPos2), yPos2, 15, 15).intersects(new Rectangle(position1, 600, 100, 50))) {
			g.setColor(Color.black);
			score1++;
		}
		if (yPos > 645 && xPos > 800) {
			xPos = 1440;
			yPos = 1440;
		}
		if (Math.abs(yPos2) > 645 && Math.abs(xPos2) < 800) {
			xPos2 = 1440;
			yPos2 = 1440;
		}
		try {
			Thread.sleep(100);
		} catch (Exception e) {
			System.out.println("Exception : " + e);
		}
		if (count1 >= 5 && count2 >= 5 && ((Math.abs(xPos2) <= 0 || yPos2 >= 645) || (xPos >= 1400 || yPos >= 645))) {
			if (score > score1) {
				g.setColor(Color.red);
				g.setFont(new Font("MarkerFelt-Thin", 500, 100));
				g.drawString("Game Over!", 400, 350);
				g.setColor(Color.yellow);
				g.setFont(new Font("Times New Roman", 500, 50));
				g.drawString("WINNER:" + player1, 500, 500);
			} else if (score < score1) {
				g.setColor(Color.red);
				g.setFont(new Font("MarkerFelt-Thin", 500, 100));
				g.drawString("Game Over!", 400, 350);
				g.setColor(Color.yellow);
				g.setFont(new Font("Times New Roman", 500, 50));
				g.drawString("WINNER:" + player2, 500, 500);
			} else {
				g.setColor(Color.red);
				g.setFont(new Font("MarkerFelt-Thin", 500, 100));
				g.drawString("Game Over!", 400, 350);
				g.setColor(Color.blue);
				g.setFont(new Font("Times New Roman", 500, 50));
				g.drawString("It's a tie! Let's give an another try!", 300, 500);
			}

		}
	}

	public void test(String p, String q) throws Exception {
		new Play(p, q);
	}

	@Override
	public void keyReleased(KeyEvent arg0) {

	}

	@Override
	public void keyTyped(KeyEvent arg0) {

	}

}
