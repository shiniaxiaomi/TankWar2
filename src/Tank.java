import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

public class Tank {

	public static final int XSPEED=PropertyMgr.getProperty("tankSpeed");//坦克的移动速度
	public static final int YSPEED=PropertyMgr.getProperty("tankSpeed");//坦克的移动速度
	public static final int XSIZE=50; 
	public static final int YSIZE=50; 
	
	public static final int NOT_MY_SPEED=10; //敌方坦克移动的周期
	
	boolean L=false,U=false,D=false,R=false;
	dirs dir=dirs.STOP;
	dirs PaoTongDir=dirs.U;//炮筒的方向
	
	boolean good=true;
	int life=100;
	
	int num=0;
	
	
	private boolean live=true;
	
	private int pt_x,pt_y;//炮筒的终点位置
	public int x,y;
	private int pre_x,pre_y;//上一次坦克的位置
	
	TankWarManager twm;
	
	private static Toolkit tk=Toolkit.getDefaultToolkit();//和操作系统处理的工具'
	static Image[] tankImages=null;
	private static Map<String,Image> imgs=new HashMap<String,Image>();
	static {
		tankImages= new Image[] {//先获取类装载器，然后再在这个里面拿取想要的文件
				tk.getImage(Explode.class.getClassLoader().getResource("images/tankU.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/tankD.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/tankL.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/tankR.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/tankLU.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/tankLD.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/tankRU.gif")),
				tk.getImage(Explode.class.getClassLoader().getResource("images/tankRD.gif"))
			};
		
		imgs.put("U", tankImages[0]);
		imgs.put("D", tankImages[1]);
		imgs.put("L", tankImages[2]);
		imgs.put("R", tankImages[3]);
		imgs.put("LU", tankImages[4]);
		imgs.put("LD", tankImages[5]);
		imgs.put("RU", tankImages[6]);
		imgs.put("RD", tankImages[7]);
	}

	public boolean isLive() {
		return live;
	}

	public void setLive(boolean live) {
		this.live = live;
	}

	public boolean isGood() {
		return good;
	}

	public void setGood(boolean good) {
		this.good = good;
	}

	public Tank(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public Tank(int x, int y,TankWarManager twm) {
		this.x = x;
		this.y = y;
		this.twm=twm;
	}
	
	

	public Tank(int x, int y, TankWarManager twm,boolean good) {
		this.x = x;
		this.y = y;
		this.twm = twm;
		this.good = good;
	}

	public void draw(Graphics g) {
		move();
		drawPaoTong(g);
		
		
		Color c=g.getColor();
		if(isGood())	{
			g.setColor(Color.RED);
			g.drawRect(x, y, tankImages[0].getWidth(null), tankImages[0].getHeight(null));//画出红色的边框，方便识别
		}
		else g.setColor(Color.BLACK);
		blood(g);//画出血量
		g.setColor(c);

		 
	}
	
	void blood(Graphics g) {
		if(isGood()) {
			g.drawRect(x,y-15,XSIZE,10);
			g.fillRect(x,y-15,life*XSIZE/100, 10);
		}
	}
	
	public Bullet fire() {
		if(isLive())	{
			Bullet b;
			
			if(isGood()) {
				b=new Bullet(x+XSIZE/2-Bullet.XSIZE/2, y+YSIZE/2-Bullet.YSIZE/2,this,twm);
				twm.arr.add(b);
				b=new Bullet(x+XSIZE/2-Bullet.XSIZE/2-Bullet.XSIZE, y+YSIZE/2-Bullet.YSIZE/2-Bullet.XSIZE,this,twm);
				twm.arr.add(b);
				b=new Bullet(x+XSIZE/2-Bullet.XSIZE/2+Bullet.XSIZE, y+YSIZE/2-Bullet.YSIZE/2+Bullet.XSIZE,this,twm);
				twm.arr.add(b);
			}
			else {
				b=new Bullet(x+XSIZE/2-Bullet.XSIZE/2, y+YSIZE/2-Bullet.YSIZE/2,this,twm);
				twm.arr.add(b);
			}
			

			
		}
		return null;
	}
	
	public Bullet spurFire() {
		if(isLive())	{
			Bullet b;
			
			PaoTongDir=dirs.U;
			b=new Bullet(x+XSIZE/2-Bullet.XSIZE/2, y+YSIZE/2-Bullet.YSIZE/2,this,twm);
			twm.arr.add(b);
			
			PaoTongDir=dirs.D;
			b=new Bullet(x+XSIZE/2-Bullet.XSIZE/2, y+YSIZE/2-Bullet.YSIZE/2,this,twm);
			twm.arr.add(b);
			
			PaoTongDir=dirs.L;
			b=new Bullet(x+XSIZE/2-Bullet.XSIZE/2, y+YSIZE/2-Bullet.YSIZE/2,this,twm);
			twm.arr.add(b);
			
			PaoTongDir=dirs.R;
			b=new Bullet(x+XSIZE/2-Bullet.XSIZE/2, y+YSIZE/2-Bullet.YSIZE/2,this,twm);
			twm.arr.add(b);
			
			PaoTongDir=dirs.LU;
			b=new Bullet(x+XSIZE/2-Bullet.XSIZE/2, y+YSIZE/2-Bullet.YSIZE/2,this,twm);
			twm.arr.add(b);
			
			PaoTongDir=dirs.LD;
			b=new Bullet(x+XSIZE/2-Bullet.XSIZE/2, y+YSIZE/2-Bullet.YSIZE/2,this,twm);
			twm.arr.add(b);
			
			PaoTongDir=dirs.RU;
			b=new Bullet(x+XSIZE/2-Bullet.XSIZE/2, y+YSIZE/2-Bullet.YSIZE/2,this,twm);
			twm.arr.add(b);
			
			PaoTongDir=dirs.RD;
			b=new Bullet(x+XSIZE/2-Bullet.XSIZE/2, y+YSIZE/2-Bullet.YSIZE/2,this,twm);
			twm.arr.add(b);
			
		}
		return null;
	}
	
	public Rectangle getRect() {
		return new Rectangle(x, y, tankImages[0].getWidth(null)+10, tankImages[0].getHeight(null)+10);
	}
	
	
	public void drawPaoTong(Graphics g) {
		
		
		
		switch (PaoTongDir) {
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
	

	public void keyPressed(KeyEvent e) {
		int key=e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_W:
				U=true;
				break;
			case KeyEvent.VK_S:
				D=true;	
				break;
			case KeyEvent.VK_A:
				L=true;
				break;
			case KeyEvent.VK_D:
				R=true;
				break;
			case KeyEvent.VK_F2:
				live=true;
				life=100;
				break;
		}

	}
	public void keyReleased(KeyEvent e) {
		int key=e.getKeyCode();
		switch (key) {
			case KeyEvent.VK_W:
				U=false;
				break;
			case KeyEvent.VK_S:
				D=false;	
				break;
			case KeyEvent.VK_A:
				L=false;
				break;
			case KeyEvent.VK_D:
				R=false;
				break;
			case KeyEvent.VK_J://发射子弹
				fire();
				break;
			case KeyEvent.VK_K://超级炮弹
				spurFire();
				break;
			
		}
		
	}
	
	
	
	void move() {
		pre_x=x;
		pre_y=y;
		
		direction();

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
			case STOP:
				break;
		}
		
		
		if(x < 0 || y < 30/2 || x > TankWarManager.XSIZE-Tank.XSIZE/2 || y > TankWarManager.YSIZE-Tank.YSIZE/2) {//防止坦克出界
			x=pre_x;
			y=pre_y;
		}

		
		
	}
	
	public boolean eatBlood(Blood blood) {
		if(this.getRect().intersects(blood.getRect()) && blood.isLive()) {
			blood.setLive(false);
			this.life=100;
			return true;
		}
		return false;
	}
	
	public boolean withTank(Tank t) {
		if(this.getRect().intersects(t.getRect()) && t.isLive()) {
			x=pre_x+twm.rnd.nextInt(XSPEED*2)-XSPEED;
			y=pre_y+twm.rnd.nextInt(YSPEED*2)-YSPEED;
			
			if(x>TankWarManager.XSIZE-30)	x=TankWarManager.XSIZE-30;//防止出界
			else if(x<0)	x=30;
			if(y>TankWarManager.YSIZE-30)	y=TankWarManager.YSIZE-30;
			else if(y<0)	y=30;
			
			dir=dirs.STOP;
			return true;
		}
		return false;
	}
	
	public boolean withWall(Wall w) {
		if(this.getRect().intersects(w.getRect()) && !this.isGood()) {
			x=pre_x;
			y=pre_y;
			
			dir=dirs.STOP;
			return true;
		}
		return false;
	}
	
	void direction() {
		if(isGood()) {
			if(U==true && D==false && L==false && R==false)	dir=dirs.U;
			else if(U==false && D==true && L==false && R==false)	dir=dirs.D;
			else if(U==false && D==false && L==true && R==false)	dir=dirs.L;
			else if(U==false && D==false && L==false && R==true)	dir=dirs.R;
			else if(U==true && D==false && L==true && R==false)		dir=dirs.LU;
			else if(U==false && D==true && L==true && R==false)		dir=dirs.LD;
			else if(U==true && D==false && L==false && R==true)		dir=dirs.RU;
			else if(U==false && D==true && L==false && R==true)		dir=dirs.RD;
			else if(U==false && D==false && L==false && R==false)		dir=dirs.STOP;
			
			if(dir!=dirs.STOP)	PaoTongDir=dir;
		}
		else {
			
			num++;
			if(num==NOT_MY_SPEED)	{
				int dir_=twm.rnd.nextInt(8);
				switch (dir_) {
				case 0://上
					dir=dirs.U;
					break;
				case 1://下
					dir=dirs.D;
					break;
				case 2://左
					dir=dirs.L;
					break;
				case 3://右
					dir=dirs.R;
					break;
				case 4://左上
					dir=dirs.LU;
					break;
				case 5://左下
					dir=dirs.LD;
					break;
				case 6://右上
					dir=dirs.RU;
					break;
				case 7://右下
					dir=dirs.RD;
					break;
				case 8://停
					dir=dirs.STOP;
					break;
				}
				
				if(dir!=dirs.STOP)	PaoTongDir=dir;
				
				
				num=0;
				fire();
			}
			
			
			
			
		}
		
		
		
		
	}
	


	
}
