import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Bullet {

	public static final int XSPEED=PropertyMgr.getProperty("bulletSpeed");//子弹的移动速度
	public static final int YSPEED=PropertyMgr.getProperty("bulletSpeed");//子弹的移动速度
	
	public static final int XSIZE=15;//子弹大小
	public static final int YSIZE=15;//子弹大小
	
	private int hitBlood=PropertyMgr.getProperty("hitBlood");;//受到攻击后所减的血量
	
	private boolean live=true;
	
	int x,y;
	dirs dir;
	
	Tank tank;
	TankWarManager twm;
	Wall wall;
	
	private boolean Good=false;
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();//和操作系统处理的工具'
	static Image[] bulletImages=null;
	private static Map<String,Image> imgs=new HashMap<String,Image>();
	static {
		bulletImages= new Image[] {//先获取类装载器，然后再在这个里面拿取想要的文件
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileU.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileD.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileL.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileR.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileLU.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileLD.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileRU.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/missileRD.gif"))
			};
		
		imgs.put("D", bulletImages[0]);
		imgs.put("U", bulletImages[1]);
		imgs.put("L", bulletImages[2]);
		imgs.put("R", bulletImages[3]);
		imgs.put("LU", bulletImages[4]);
		imgs.put("LD", bulletImages[5]);
		imgs.put("RU", bulletImages[6]);
		imgs.put("RD", bulletImages[7]);
	}
	
	public boolean isGood() {
		return Good;
	}

	public void setGood(boolean good) {
		Good = good;
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public Bullet(int x, int y,Tank tank) {
		this.x = x;
		this.y = y;
		this.tank=tank;
		dir=tank.PaoTongDir;
		if(tank.isGood()==true)	setGood(true);
		else setGood(false);
		
	}

	public Bullet(int x, int y, Tank tank, TankWarManager twm) {
		this.x = x;
		this.y = y;
		this.tank = tank;
		dir=tank.PaoTongDir;
		this.twm = twm;
		
		if(tank.isGood()==true)	setGood(true);
		else setGood(false);
	}

	public void draw(Graphics g) {
		move();
		
		/*Color c=g.getColor();
		if(tank.isGood())	{
			this.setGood(true);
			g.setColor(Color.RED);
		}
		else {
			this.setGood(false);
			g.setColor(Color.BLACK);
		}
		g.fillOval(x, y, XSIZE, YSIZE);
		g.setColor(c);*/
		
		switch (dir) {
		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;
		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;
		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;
		case LU:
			g.drawImage(imgs.get("LU"), x, y, null);
			break;
		case LD:
			g.drawImage(imgs.get("LD"), x, y, null);
			break;
		case RU:
			g.drawImage(imgs.get("RU"), x, y, null);
			break;
		case RD:
			g.drawImage(imgs.get("RD"), x, y, null);
			break;
	}
		
		
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, bulletImages[0].getWidth(null), bulletImages[0].getWidth(null));
	}
	

	
	
	public boolean hitTank(Tank t) {
		if(this.getRect().intersects(t.getRect()) && t.isLive()) {
			if(t.isGood()==true && this.isGood()==false) {
				twm.arr.remove(this);
				t.life=t.life-hitBlood;
				if(t.life<=0) {
					this.live = false;
					t.setLive(false);
				}
				twm.exp_arr.add(new Explode(this.x, this.y,this));
				return true;
			}else if(t.isGood()==false && this.isGood()==true){
				twm.arr.remove(this);
				twm.tank_arr.remove(t);
				this.live = false;
				t.setLive(false);
				twm.exp_arr.add(new Explode(this.x, this.y,this));
			}
			
		}
		return false;
	}
	
	private void move() {
		
		
		switch (dir) {
			case U:
				y-=YSPEED;
				break;
			case D:
				y+=YSPEED;
				break;
			case L:
				x-=XSPEED;
				break;
			case R:
				x+=XSPEED;
				break;
			case LU:
				x-=XSPEED;
				y-=YSPEED;
				break;
			case LD:
				x-=XSPEED;
				y+=YSPEED;
				break;
			case RU:
				x+=XSPEED;
				y-=YSPEED;
				break;
			case RD:
				x+=XSPEED;
				y+=YSPEED;
				break;
		}
		
		if(x < 0 || y < 0 || x > TankWarManager.XSIZE || y > TankWarManager.YSIZE) {//子弹出界移除
			live = false;
			twm.arr.remove(this);
		}
		
		//hitWall(this);
		
		
	}

	boolean hitWall(Wall w) {

		if(this.getRect().intersects(w.getRect()) && this.isLive()) {
			if(isGood()==true) {
				return true;
			}else{
				twm.arr.remove(this);

			}
			
		}
		return false;
		
	}
	
}
