package com.games.pockettanks;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Tanks extends Rectangle {

	private static final long serialVersionUID = 1L;

	Block tankLeft, tankRight;
	Block cannonLeft, cannonRight;

	public Tanks(int xLeft, int yLeft, int xRight, int yRight) {
		tankLeft = new Block(xLeft, yLeft, 100, 50, "/resources/tanks/leftTankNo.png");
		tankRight = new Block(xRight, yRight, 100, 50, "/resources/tanks/rightTankNo.png");
		cannonLeft = new Block(xLeft + 62, yLeft - 8, 60, 60, "/resources/tanks/leftCanon.png");
		cannonRight = new Block(xRight - 20, yRight - 5, 60, 60, "/resources/tanks/rightCanon.png");
	}

	public void drawLeftTank(Graphics g, int x, int y, Component c, int angle) {
		tankLeft.draw(g, x, y, c);
		cannonLeft.draw(g, x + 62, y - 8, 0, 30, c, angle);
	}

	public void drawRightTank(Graphics g, int x, int y, Component c, int angle) {
		tankRight.draw(g, x, y, c);
		cannonRight.draw(g, x - 20, y - 5, 60, 30, c, angle);
	}

	public boolean collide(int tankX, int tankY, int x, int y) {
		Rectangle bomb = new Rectangle(x, y, 10, 10);
		Rectangle tank = new Rectangle(tankX, tankY, 100, 50);
		return tank.intersects(bomb);

	}

}
