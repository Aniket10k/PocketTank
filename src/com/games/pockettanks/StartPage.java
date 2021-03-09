package com.games.pockettanks;

import java.awt.Graphics;

import javax.swing.JPanel;

public class StartPage extends JPanel {

	private static final long serialVersionUID = 1L;


	public StartPage() {
		setLayout(null);
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawString("POCKET TANKS", 200, 50);
		g.drawString("Instructions", 200, 100);
		g.drawString("1. Two player game. Each side has a tank and you have to destroy other tank.", 200, 150);
		g.drawString("2. Before starting the game keep your tanks at correct position. Then press start.", 200, 200);
		g.drawString(
				"3. First player will be selected randomly. Until that player fires other player can not do anything.",
				200, 250);
		g.drawString("4. Choose angle, position and speed and target the opposite tank.", 200, 300);
		g.drawString("5. Destroy the oppsite tank and win the game.", 200, 350);
		g.drawString("6. Press 'start Game' to begin.", 200, 400);
	}
}
