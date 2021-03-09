package com.games.pockettanks;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;

public class City {

	Block[] blocks;

	public City() {
		blocks = new Block[15];
	}

	public void drawCity(Graphics g, Component c) {

		g.drawLine(0, 450, c.getWidth(), 450);

		blocks[10] = new Block(650, 175, 75, 275, "buildings/building11.png");
		blocks[10].draw(g, c);

		blocks[11] = new Block(550, 150, 75, 300, "buildings/building12.png");
		blocks[11].draw(g, c);

		blocks[12] = new Block(400, 200, 75, 250, "buildings/building13.png");
		blocks[12].draw(g, c);

		blocks[13] = new Block(700, 200, 75, 250, "buildings/building14.png");
		blocks[13].draw(g, c);

		blocks[14] = new Block(450, 200, 75, 250, "buildings/building15.png");
		blocks[14].draw(g, c);

		blocks[2] = new Block(370, 300, 75, 150, "buildings/building2.png");
		blocks[2].draw(g, c);

		blocks[4] = new Block(480, 300, 75, 150, "buildings/building4.png");
		blocks[4].draw(g, c);

		blocks[6] = new Block(550, 300, 75, 150, "buildings/building7.png");
		blocks[6].draw(g, c);

		blocks[9] = new Block(750, 300, 75, 150, "buildings/building10.png");
		blocks[9].draw(g, c);

		blocks[7] = new Block(590, 300, 75, 150, "buildings/building8.png");
		blocks[7].draw(g, c);

		blocks[1] = new Block(335, 350, 50, 100, "buildings/building1.png");
		blocks[1].draw(g, c);

		blocks[0] = new Block(735, 350, 50, 100, "buildings/building4.png");
		blocks[0].draw(g, c);
		
		blocks[3] = new Block(425, 350, 75, 100, "buildings/building3.png");
		blocks[3].draw(g, c);

		blocks[5] = new Block(500, 350, 75, 100, "buildings/building5.png");
		blocks[5].draw(g, c);

		blocks[8] = new Block(650, 350, 75, 100, "buildings/building9.png");
		blocks[8].draw(g, c);

	}

	public boolean collide(int x, int y) {

		Rectangle bomb = new Rectangle(x, y, 10, 10);

		for (Block block : blocks) {
			if (block.rectangle.intersects(bomb)) {
				return true;
			}
		}

		return false;
	}
}
