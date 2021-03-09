package com.games.pockettanks;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JButton;
import javax.swing.JFrame;

public class Main {

	JButton startButton;
	StartPage sp;
	MainPage mp;
	

	public Main() {

		JFrame frame = new JFrame("Pocket Tank");
		mp = new MainPage();
		
		startButton = new JButton("Start Game");
		startButton.setBounds(500, 450, 200, 50);
		startButton.setFocusable(true);
		startButton.addActionListener((e) -> {
			frame.getContentPane().add(mp);
			startButton.setVisible(false);
			startButton.setFocusable(false);
		});

		sp = new StartPage();
		sp.add(startButton);
		frame.getContentPane().add(sp);

		frame.setBackground(Color.white);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1200, 600);

		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		frame.setLocation(dim.width / 2 - frame.getSize().width / 2, dim.height / 2 - frame.getSize().height / 2);

		frame.setVisible(true);
		frame.setResizable(false);

	}

	public static void main(String[] args) {
		new Main();
	}

}
