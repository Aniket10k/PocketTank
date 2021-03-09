package com.games.pockettanks;

import java.awt.Component;
import java.awt.Graphics;

public class Fire {

	Block bullet;

	public Fire(int xLeft, int yLeft, int xRight, int yRight) {
		super();
		this.bullet = new Block(xLeft, yLeft, 10, 10, "src/bombs/gobline.png");
		this.bullet = new Block(xRight, yRight, 10, 10, "src/bombs/gobline.png");
	}

	public void drawBullet(Graphics g, Component c, int x, int y, boolean show) {

		if (show) {
			bullet.draw(g, x, y, c, show);
		}
	}
}
