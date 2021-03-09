package com.games.pockettanks;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

public class Start extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton b1;
	private JButton b2;
	private JTextField t1, t2;
	private BufferedImage img3, img2;

	public Start() throws Exception {
		b1 = new JButton("New Game");
		b2 = new JButton("Exit");
		t1 = new JTextField("Name 1:");
		t2 = new JTextField("Name 2:");
		b1.setFont(new Font("Rockwell", Font.BOLD, 18));
		b2.setFont(new Font("Rockwell", Font.BOLD, 18));
		t1.setFont(new Font("Rockwell", Font.BOLD, 18));
		t2.setFont(new Font("Rockwell", Font.BOLD, 18));
		b1.setBounds(800, 500, 160, 80);
		b2.setBounds(970, 500, 160, 80);
		t1.setBounds(800, 600, 160, 80);
		t2.setBounds(970, 600, 160, 80);
		b1.addActionListener(this);
		b2.addActionListener(this);
		add(b1);
		add(b2);
		add(t1);
		add(t2);
		setLayout(null);
		img3 = ImageIO.read(new File("src/images/smalltank.jpg"));
		img2 = ImageIO.read(new File("src/images/smalltank.jpg"));
		b1.setFocusable(true);
		b2.setFocusable(true);
		setSize(1400, 800);
		setVisible(true);
		setResizable(true);
	}

	public void paint(Graphics g) {
		super.paint(g);

		g.setColor(Color.red);
		g.fillOval(200, 125, 1000, 300);
		g.drawImage(img2, 270, 250, this);
		g.drawImage(img3, 1045, 250, this);
		g.setColor(Color.yellow);
		g.fillOval(150, 50, 180, 90);
		g.fillOval(1055, 50, 180, 90);
		Font font2 = new Font("Algerian", Font.BOLD, 75);
		g.setFont(font2);
		g.setColor(Color.white);
		g.drawString("Pocket Tanks!", 400, 300);

		Font font = new Font("Rockwell", Font.BOLD, 24);
		g.setFont(font);
		g.setColor(Color.red);
		g.drawString("Player1", 200, 100);
		g.drawString("Player2", 1100, 100);
		setBackground(Color.black);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == b1) {
			try {
				String p = t1.getText().substring(7);
				String q = t2.getText().substring(7);
				new Play(p, q).test(p, q);
			} catch (Exception e33) {
				System.out.println("Exception" + e33);
			}
		} else if (e.getSource() == b2) {
			System.exit(0);
		}
	}

	public static void main(String[] args) throws Exception {
		new Start();
	}

}
