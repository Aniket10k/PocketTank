package com.games.pockettanks;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.geom.AffineTransform;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Block extends Rectangle {

	private static final long serialVersionUID = 1L;

	Image pic;
	
	Rectangle rectangle;

	public Block(int x, int y, int w, int h, String path) {
		this.x = x;
		this.y = y;
		this.width = w;
		this.height = h;
		this.rectangle = new Rectangle(x, y, w, h);
		try {
			pic = ImageIO.read(new File(path));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g, Component c) {
		g.drawImage(pic, x, y, width, height, c);
	}

	public void draw(Graphics g, int xPos, int yPos, Component c) {
		g.drawImage(pic, xPos, yPos, width, height, c);
	}

	public void draw(Graphics g, int xPos, int yPos, int rotX, int rotY, Component c, double angle) {

		pic = pic.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		Graphics2D g2d = (Graphics2D) g;
		AffineTransform at = AffineTransform.getTranslateInstance(xPos, yPos); // top left point image.
		at.rotate(Math.toRadians(angle), rotX, rotY); // relative position acrpss which rotation will happen.

		g2d.drawImage(pic, at, null);
	}

	public void draw(Graphics g, int x, int y, Component c, boolean show) {
		g.drawImage(pic, x, y, width, height, c);
	}

}
