import java.awt.Color;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

public class BadGuy {
	private Rectangle hitbox;
	private int[] wayX, wayY;
	private int speed, leg, lives, targetX, targetY;
	private Color color;
	private int radius;

	public BadGuy(int x, int y, int[] locX, int[] locY) {
		hitbox = new Rectangle(x, y, 25, 25);
		radius = 25;
		speed = 350;
		wayX = locX;
		wayY = locY;
		leg = 0;
		lives = 1;

		float r = 0, g = 0, b = 0;
		while (r + g + b < 1) {
			r = (float) (Math.random());
			g = (float) (Math.random());
			b = (float) (Math.random());
		}
		color = new Color(r, g, b);
		targetX = wayX[0];
		targetY = wayY[0];
	}

	public Color getColor() {
		return color;
	}

	public int getX() {
		return hitbox.x;
	}

	public int getY() {
		return hitbox.y;
	}

	public int getCenterX() {
		return hitbox.x + radius;
	}

	public int getCenterY() {
		return hitbox.y + radius;
	}

	public int getWidth() {
		return radius * 2;
	}

	public int getHeight() {
		return radius * 2;
	}

	public void setTarget(int x, int y) {
		targetX = x - radius;
		targetY = y - radius;
	}

	public void updated(double deltaTime) {

		int deltaX = targetX - hitbox.x;
		int deltaY = targetY - hitbox.y;

		if (deltaX == 0 && deltaY == 0) {
			leg++;
			if (leg < wayX.length) {
				targetX = wayX[leg];
				targetY = wayY[leg];
			}
			return;
		}

		double theta = Math.atan2(deltaY, deltaX);

		double x = speed * Math.cos(theta) * deltaTime;
		double y = speed * Math.sin(theta) * deltaTime;

		if (Math.abs(deltaX) - Math.abs(x) < 0 && Math.abs(deltaY) - Math.abs(y) < 0) {
			hitbox.x = targetX;
			hitbox.y = targetY;
			leg++;
			if (leg < wayX.length) {
				targetX = wayX[leg];
				targetY = wayY[leg];
			}
		} else {
			hitbox.x += x;
			hitbox.y += y;
		}

	}

	public boolean isDead() {
		if (leg == wayX.length)
			return true;
		return false;
	}

	public boolean didHit(int x, int y, int r) {

		int totalRadius = this.radius + radius;
		int deltaX = x - this.getCenterX();
		int deltaY = y - this.getCenterY();
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);

		if (totalRadius < distance) {
			return false;
		}
		return true;

	}
}
