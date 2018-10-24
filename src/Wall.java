import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;

public class Wall {

	int x, y;
	int XSIZE = 300, YSIZE = 120;

	TankWarManager twm;

	public Wall(int x, int y, TankWarManager twm) {
		super();
		this.x = x;
		this.y = y;
		this.twm = twm;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(x, y, XSIZE, YSIZE);
		g.setColor(c);
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, XSIZE, YSIZE);
	}

}
