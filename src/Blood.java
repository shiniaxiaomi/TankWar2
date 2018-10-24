import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Blood {

	int x, y, w, h;
	TankWarManager twm;

	boolean live = true;
	int step = 0;

	private int[][] pos = { // 二维坐标
			{ 350, 300 }, { 360, 300 }, { 375, 275 }, { 400, 200 }, { 360, 270 }, { 365, 290 }, { 340, 280 } };

	public Blood() {
		x = pos[0][0];
		y = pos[0][1];
		w = 15;
		h = 15;

	}

	public void draw(Graphics g) {
		move();
		Color c = g.getColor();
		g.setColor(Color.MAGENTA);
		g.fillRect(x, y, w, h);
		g.setColor(c);
	}

	void move() {
		step++;
		if (step == pos.length) {
			step = 0;
		}
		x = pos[step][0];
		y = pos[step][1];

	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isLive() {
		return live;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, w, h);
	}

}
