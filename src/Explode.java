import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Explode {

	boolean live = true;
	int x, y;
	TankWarManager twm;
	Bullet bullet;

	int step = 0;

	private static boolean init = false;

	private static Toolkit tk = Toolkit.getDefaultToolkit();// 和操作系统处理的工具

	private static Image[] imgs = { // 先获取类装载器，然后再在这个里面拿取想要的文件
			tk.getImage(Explode.class.getClassLoader().getResource("images/0.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/1.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/2.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/3.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/4.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/5.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/6.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/7.gif")),
			tk.getImage(Explode.class.getClassLoader().getResource("images/8.gif")), };

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Explode(int x, int y, Bullet bullet) {
		this.x = bullet.x;
		this.y = bullet.y;
		this.bullet = bullet;
	}

	public void draw(Graphics g) {

		if (!init) {
			for (int j = 0; j < imgs.length; j++) {
				g.drawImage(imgs[j], -100, -100, null);
			}
			init = true;
		}

		if (isLive()) {
			g.drawImage(imgs[step], x, y, null);
			step++;
			if (step >= imgs.length) {
				setLive(false);
				step = 0;
			}
		}

	}

}
