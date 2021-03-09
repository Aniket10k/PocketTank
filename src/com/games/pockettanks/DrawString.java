package com.games.pockettanks;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class DrawString {

	public static void drawString(Graphics g, int x, int y, String str) {
		Font font = new Font("monospace", Font.BOLD, 15);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(str, x, y);
	}

	public static void drawString(Graphics g, int x, int y, int str) {
		Font font = new Font("monospace", Font.BOLD, 15);
		g.setColor(Color.WHITE);
		g.setFont(font);
		g.drawString(String.valueOf(str), x, y);
	}

}
