import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;

public class TankWarManager extends Frame {

	public static final int XSIZE = 1000;
	public static final int YSIZE = 800;

	List<Bullet> arr = new ArrayList<Bullet>();
	List<Tank> tank_arr = new ArrayList<Tank>();
	List<Explode> exp_arr = new ArrayList<Explode>();

	Tank myTank = new Tank(XSIZE / 2, YSIZE / 2, this, true);

	Random rnd = new Random();// 产生随机数

	Wall wall_1 = new Wall(200, 200, this);
	Wall wall_2 = new Wall(400, 600, this);
	Blood blood = new Blood();

	public TankWarManager() throws HeadlessException {

		tank_arr.add(myTank);// 加入我方坦克

		int tankInitCount = PropertyMgr.getProperty("tankInitCount");// 从配置文件中获取坦克的初始化数量
		for (int i = 0; i < tankInitCount; i++) {// 加入敌方坦克
			tank_arr.add(new Tank(rnd.nextInt(XSIZE - Tank.XSIZE), rnd.nextInt(YSIZE - Tank.YSIZE), this, false));
		}
	}

	Image offScreenImage = null;// 双缓冲

	public static void main(String[] args) {
		TankWarManager twm = new TankWarManager();
		twm.launch();

	}

	@Override
	public void paint(Graphics g) {
		wall_1.draw(g);
		wall_2.draw(g);
		if (blood.isLive())
			blood.draw(g);

		if (tank_arr.size() == 1) {// 如果敌军全部死亡，再加入10个敌人
			for (int i = 0; i < PropertyMgr.getProperty("reProduceTankCount"); i++) {// 加入敌方坦克
				tank_arr.add(new Tank(rnd.nextInt(XSIZE - Tank.XSIZE), rnd.nextInt(YSIZE - Tank.YSIZE), this, false));
			}
		}

		for (int i = 0; i < tank_arr.size(); i++) {// 坦克
			Tank tank = tank_arr.get(i);
			for (int j = 0; j < tank_arr.size(); j++) {
				Tank tank_1 = tank_arr.get(j);
				if (tank_1 != tank)
					tank.withTank(tank_1);
			}
			tank.withWall(wall_1);
			tank.withWall(wall_2);
			if (tank.isGood())
				tank.eatBlood(blood);
			if (tank.isLive())
				tank.draw(g);
		}

		for (int i = 0; i < arr.size(); i++) {// 显示出子弹
			Bullet b = arr.get(i);
			for (int j = 0; j < tank_arr.size(); j++) {// 判断子弹是否打中了坦克
				Tank tank = tank_arr.get(j);
				b.hitTank(tank);
			}
			b.hitWall(wall_1);
			b.hitWall(wall_2);
			if (b.isLive())
				b.draw(g);
		}

		for (int i = 0; i < exp_arr.size(); i++) {
			Explode e = exp_arr.get(i);
			if (e.isLive())
				e.draw(g);
		}

		Color c = g.getColor();
		g.setColor(Color.RED);
		g.drawString("子弹个数" + arr.size(), 10, 50);
		g.drawString("坦克个数" + tank_arr.size(), 10, 70);
		g.drawString("坦克血量" + myTank.life, 10, 90);
		g.setColor(c);
	}

	public void update(Graphics g) {
		if (offScreenImage == null) {
			offScreenImage = this.createImage(XSIZE, YSIZE);
		}
		Graphics gOffScreen = offScreenImage.getGraphics();
		Color c = gOffScreen.getColor();
		gOffScreen.setColor(Color.BLACK);
		gOffScreen.fillRect(0, 0, XSIZE, YSIZE);
		gOffScreen.setColor(c);
		paint(gOffScreen);
		g.drawImage(offScreenImage, 0, 0, null);
	}

	void launch() {

		this.setSize(XSIZE, YSIZE);
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});// 注册窗口事件监听类
		this.addKeyListener(new keyMonitor());// 注册按键监听类
		this.setResizable(false);
		this.setLocation(300, 200);
		this.setBackground(Color.BLACK);
		this.setVisible(true);

		new Thread(new paintThread()).start();

	}

	class paintThread implements Runnable {
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}
	}

	private class keyMonitor extends KeyAdapter {

		@Override
		public void keyPressed(KeyEvent e) {
			myTank.keyPressed(e);
		}

		@Override
		public void keyReleased(KeyEvent e) {
			myTank.keyReleased(e);
		}

	}

}
