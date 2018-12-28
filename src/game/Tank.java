package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Vector;

public class Tank implements KeyListener,Runnable{
	GameInterface gi;
	private Map map;
	private AiMove aiMove;
	private Wall wall; 
//	private String playerid;
//	private String HP;
	private int tx = 500;
	private int ty = 500;
	private int tw = 70;
	private int th = 70;
	private int magazine = 1;
	private char td = 'w';  //tank方向w
	private boolean ct_w = false,ct_a = false,ct_s = false,ct_d = false; //控制tank移动
	private int ATK;
	private int tank_speed = 2;
	private int tank_hp;
	private int tank_id = 0;
	private boolean fire = false;
	private int time_interval = 500;
	Image tank_image_w = Toolkit.getDefaultToolkit().getImage("tank0_w.jpg");
	Image tank_image_a = Toolkit.getDefaultToolkit().getImage("tank0_a.jpg");
	Image tank_image_s = Toolkit.getDefaultToolkit().getImage("tank0_s.jpg");
	Image tank_image_d = Toolkit.getDefaultToolkit().getImage("tank0_d.jpg");
	Image tank_image = Toolkit.getDefaultToolkit().getImage("tank0_w.jpg");
	//初始化坦克
	public Tank(GameInterface gi,int tx,int ty,int tank_id,int atk,int hp,Map map,int speed)
	{
		aiMove = new AiMove(this);
		this.tank_speed = speed;
		wall = new Wall(gi);
		this.map = map; 
		this.tank_hp = hp;
		this.ATK = atk;
		this.gi = gi;
		this.tx = tx;
		this.ty = ty;
		this.tank_id = tank_id;
		switch(tank_id)
		{
		case 0:
			tank_image_w = Toolkit.getDefaultToolkit().getImage("tank0_w.jpg");
			tank_image_a = Toolkit.getDefaultToolkit().getImage("tank0_a.jpg");
			tank_image_s = Toolkit.getDefaultToolkit().getImage("tank0_s.jpg");
			tank_image_d = Toolkit.getDefaultToolkit().getImage("tank0_d.jpg");
			tank_image = Toolkit.getDefaultToolkit().getImage("tank0_w.jpg");
			break;
		case 1:
			tank_image_w = Toolkit.getDefaultToolkit().getImage("tank0_w.jpg");
			tank_image_a = Toolkit.getDefaultToolkit().getImage("tank0_a.jpg");
			tank_image_s = Toolkit.getDefaultToolkit().getImage("tank0_s.jpg");
			tank_image_d = Toolkit.getDefaultToolkit().getImage("tank0_d.jpg");
			tank_image = Toolkit.getDefaultToolkit().getImage("tank0_w.jpg");
			break;
		}
		if(tank_id>1)
		{
			tank_image_w = Toolkit.getDefaultToolkit().getImage("tank1_w.jpg");
			tank_image_a = Toolkit.getDefaultToolkit().getImage("tank1_a.jpg");
			tank_image_s = Toolkit.getDefaultToolkit().getImage("tank1_s.jpg");
			tank_image_d = Toolkit.getDefaultToolkit().getImage("tank1_d.jpg");
			tank_image = Toolkit.getDefaultToolkit().getImage("tank1_s.jpg");
		}
	}
	//绘制坦克
	public void drawTank(Graphics g)
	{
		if(this.tank_hp > 0)
		{
			g.drawImage(tank_image, tx, ty, tw, th, this.gi);
			g.drawString("PLAYER:"+this.tank_id, tx, ty + th + 5);
			g.drawString("HP:"+this.tank_hp, tx, ty + th + 25);
		}
		
	}
	//坦克移动       
	/*笔记：遇到一个bug是因为调用IsMissWall时传入的参数有误 不应该是坦克左上角的坐标
	 * 而是调用两次IsMissWall 传入两个坐标分别是当前坦克状态的坦克的头的左坐标和右坐标
	*/
	public void move()
	{
		if(this.tank_hp<=0)return;
//		if(tank_id == 3)System.out.println("tna="+this.ct_a);
		if(ct_w)
		{
			int next_location = ty - tank_speed;
			//判断是否越出屏幕
			if(next_location >= 0)
			{
				int i,gi_tanks_size = this.gi.tanks.size();
				//判断是否与坦克发生碰撞
				for( i = 0;i < gi_tanks_size;i++)
				{
					
					if(IsMiss(tx,next_location,this.gi.tanks.get(i)) && i != tank_id)
					break;
				}
				boolean b =map.IsWall(tx, next_location);
				if(i == gi_tanks_size && b && !this.IsMissWall(tx, next_location) && !this.IsMissWall(tx + tw, next_location)&& !this.IsMissWall(tx + tw / 2, next_location))
				{
					this.ty = next_location;
					tank_image = tank_image_w;
					td = 'w';
//					System.out.println("tank_x = "+this.tx+" tank_y = "+this.ty);	
				}
			}
			
		}
		else if(ct_a)
		{
			int next_location = tx - tank_speed;
			//判断是否越出屏幕
			if(next_location >= 0)
			{
				int i,gi_tanks_size = this.gi.tanks.size();
				//判断是否与坦克发生碰撞
				for( i = 0;i < gi_tanks_size;i++)
				{
					if(IsMiss(next_location,ty,this.gi.tanks.get(i))&& i != tank_id)
					{
//						System.out.println("ismiss="+IsMiss(next_location,ty,this.gi.tanks.get(i))+"i = "+i +" tank_id= "+tank_id);
						break;
					}
					
		
				}
				if(i == gi_tanks_size && map.IsWall(next_location, ty) && !this.IsMissWall(next_location,ty) && !this.IsMissWall(next_location,ty + th)&& !this.IsMissWall(next_location,ty + th / 2))
				{
					this.tx = next_location;
					tank_image = tank_image_a;
					td = 'a';
				}
					
			}
			
		}
		else if(ct_s)
		{
			int next_location = ty + tank_speed;
			//判断是否越出屏幕
			if(next_location >= 0 && next_location <= this.gi.game_interface_height - this.th)
			{
				int i,gi_tanks_size = this.gi.tanks.size();
				//判断是否与坦克发生碰撞
				for( i = 0;i < gi_tanks_size;i++)
				{
					if(IsMiss(tx,next_location,this.gi.tanks.get(i))&& i != tank_id)
					break;
				}
				if(i == gi_tanks_size && map.IsWall(tx, next_location) && !this.IsMissWall(tx, next_location + th) && !this.IsMissWall(tx + tw, next_location + th) && !this.IsMissWall(tx + tw / 2, next_location + th))
					{
						this.ty = next_location;
						tank_image = tank_image_s;
						td = 's';
					}
			}
			
		}
		else if(ct_d)
		{
			int next_location = tx + tank_speed;
			//判断是否越出屏幕
			if(next_location >= 0 && next_location <= this.gi.game_interface_wdith - this.tw)
			{
				int i,gi_tanks_size = this.gi.tanks.size();
				//判断是否与坦克发生碰撞
				for( i = 0;i < gi_tanks_size;i++)
				{
					if(IsMiss(next_location,ty,this.gi.tanks.get(i))&& i != tank_id)
					break;
				}
				if(i == gi_tanks_size && map.IsWall(next_location, ty) && !this.IsMissWall(next_location + tw,ty) && !this.IsMissWall(next_location + tw,ty + th)&& !this.IsMissWall(next_location + tw,ty + th / 2))
				{
					this.tx = next_location;
					tank_image = tank_image_d;
					td = 'd';
				}
			}
		}
//		System.out.println("启动！！");
	}
	//键盘事件
	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		int key = e.getKeyCode();
		if(tank_id == 0)
		{
			switch(key)
			{
			case KeyEvent.VK_W:
				this.ct_w = true;
				
//				System.out.println("1111111");
				break;
			case KeyEvent.VK_A:
				this.ct_a = true;
				tank_image = tank_image_a;
				td = 'a';
				break;
			case KeyEvent.VK_S:
				this.ct_s = true;
				tank_image = tank_image_s;
				td = 's';
				
				break;
			case KeyEvent.VK_D:
				this.ct_d = true;
				tank_image = tank_image_d;
				td = 'd';

				break;
			case KeyEvent.VK_J:
				fire = true;
//				if(magazine > 0){
//					System.out.println("i ="+i++);
//					addBullet();
//				magazine--;
//				}
				break;
			}
		}
		else if(tank_id == 1)
		{
			switch(key)
			{
			case KeyEvent.VK_UP:
				this.ct_w = true;
				tank_image = tank_image_w;
				td = 'w';
				break;
			case KeyEvent.VK_LEFT:
				this.ct_a = true;
				tank_image = tank_image_a;
				td = 'a';

				break;
			case KeyEvent.VK_DOWN:
				this.ct_s = true;
				tank_image = tank_image_s;
				td = 's';
				
				break;
			case KeyEvent.VK_RIGHT:
				this.ct_d = true;
				tank_image = tank_image_d;
				td = 'd';
				break;
			case KeyEvent.VK_NUMPAD1:
				fire = true;
//				if(magazine > 0){
////					System.out.println("111111");
//					addBullet();
//				magazine--;
//				}
				break;
			}
//			try {
//				Thread.sleep(100);
//			} catch (InterruptedException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
		}
		
	}
	int i = 0;   //
	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		ct_w = false;ct_a = false;ct_s = false;ct_d = false;
		fire = false;
//		int key = e.getKeyCode();
		
//		if(tank_id == 0 && key == KeyEvent.VK_J)
//		{
//			
//			if(magazine > 0){
//				System.out.println("i ="+i++);
//				addBullet();
//			magazine--;
//			}
//		}
//		else if(tank_id == 1 && )
//		{
//			if(magazine > 0){
////				System.out.println("111111");
//				addBullet();
//			magazine--;
//			}
//		}
	}
	public void shootBullet() throws InterruptedException
	{
		if(tank_hp<=0)return;
		if(fire == true)
		{
//			System.out.println("============");
			if(magazine>0)
			{
				addBullet();
				magazine--;
			
			}
		}
		
	}
	//为坦克添加子弹
	public void addBullet()
	{
		//若修改了子弹的大小，记得修改这里的值
		int half_bullet = 10;
		int bx = tx - half_bullet,by = ty - half_bullet;
		
		switch(td)
		{
		case 'w':
			bx += tw / 2;
			break;
		case 'a':
			by += th / 2 ;
			break;
		case 's':
			bx += tw / 2;
			by += th + half_bullet;
			break;
		case 'd':
			bx += tw + half_bullet;
			by += th / 2;
			break;
		}
		//TODO 子弹的is_life值总是true ???
		Bullet bullet = new Bullet(gi, bx, by, td,this.ATK,this.tank_id);
		bullet.SetIsLife(true);
		this.gi.bullets.add(bullet);
	}
	//坦克线程
	@Override
	public void run() {
		// TODO Auto-generated method stub
//		System.out.println("tank"+this.tank_id+"已启动！");
		if(tank_id > 1)
				{
					Thread t = new Thread(aiMove);
					t.start();
					
				}
		while(true)
		{
			
//			this.gi.jf.addKeyListener(this);
			try {
				
				shootBullet();
				Thread.sleep(10);
				this.move();
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	public int Get_tx()
	{
		return this.tx;
	}
	public int Get_ty()
	{
		return this.ty;
	}
	public int Get_hp()
	{
		return this.tank_hp;
	}
	public boolean Set_hp(int atk)
	{
		this.tank_hp -= atk;
		if(tank_hp <= 0)return true;
		return false;
	}
	public int Get_tw()
	{
		return this.tw;
	}
	public int Get_th()
	{
		return this.th;
	}
	
	// 判断坦克是否发生碰撞
	public boolean IsMiss(int next_location_x,int next_location_y,Tank temp_tank) //如果碰撞返回true 否则返回false
	{
		int tx,ty,tw,th,thp;
		tx = temp_tank.Get_tx();
		ty = temp_tank.Get_ty();
		tw = temp_tank.Get_tw();
		th = temp_tank.Get_th();
		thp = temp_tank.Get_hp();
//		System.out.println("next_location_x = "+next_location_x+" next_location_y + this.ty ="+(next_location_y + this.ty));
//		System.out.println("tx = "+tx+" ty = "+ty+" tx + tw = "+(tx + tw)+" ty + th"+ty + th);
//		System.out.println("this.tx = "+this.tx+" this.ty = "+this.ty);
//		System.out.println("================================================");
		return (next_location_x >= tx && next_location_x <= tx + tw && next_location_y >= ty && next_location_y <= ty + th && thp > 0)||
				(next_location_x + this.tw >= tx && next_location_x + this.tw <= tx + tw && next_location_y >= ty && next_location_y <= ty + th && thp > 0)||
				(next_location_x >= tx && next_location_x <= tx + tw && next_location_y + this.th>= ty && next_location_y + this.th <= ty + th && thp > 0)||
				(next_location_x + this.tw >= tx && next_location_x + this.tw <= tx + tw && next_location_y + this.th>= ty && next_location_y + this.th <= ty + th && thp > 0);
	}
	//判断是否撞墙
	/*
	 * 这两个参数的值分别为对象当前状态的头的左坐标和右坐标
	 */
	public boolean IsMissWall(int next_location_x,int next_location_y) //如果碰撞返回true 否则返回false
	{
		int tx,ty,tw,th,thp,i,j;
		if(next_location_y % 40 == 0 && next_location_y != 0)
			next_location_y -= 1;
		if(next_location_x % 40 == 0 && next_location_x != 0)
			next_location_x -= 1;
		i = next_location_y / 40;
		j = next_location_x / 40;
		tx = j * wall.GetWall_width();
		ty = i * wall.GetWall_height();
		tw = wall.GetWall_width();
		th = wall.GetWall_height();
		thp = map.map[i][j];
//		System.out.println("i = "+i+" j = "+j);
//		System.out.println("next_location_x = "+next_location_x+" next_location_y + this.ty ="+(next_location_y + this.ty));
//		System.out.println("tx = "+tx+" ty = "+ty+" tx + tw = "+(tx + tw)+" ty + th"+(ty + th));
//		System.out.println("this.tx = "+this.tx+" this.ty = "+this.ty);
//		System.out.println("================================================");
		return (next_location_x >= tx && next_location_x <= tx + tw && next_location_y >= ty && next_location_y <= ty + th && thp > 0)||
				(next_location_x + this.tw >= tx && next_location_x + this.tw <= tx + tw && next_location_y >= ty && next_location_y <= ty + th && thp > 0)||
				(next_location_x >= tx && next_location_x <= tx + tw && next_location_y + this.th>= ty && next_location_y + this.th <= ty + th && thp > 0)||
				(next_location_x + this.tw >= tx && next_location_x + this.tw <= tx + tw && next_location_y + this.th>= ty && next_location_y + this.th <= ty + th && thp > 0);
	}
	
	public void SetMagazine()
	{
		this.magazine ++;
		
	}
	public int getTank_id()
	{
		return this.tank_id;
	}
	public int getTank_ATK()
	{
		return this.ATK;
	}
	public void setATK(int i)
	{
		 ATK+=i;
	}
	public void setCt_w(boolean b)
	{
		this.ct_w = b;
	}
	public void setCt_a(boolean b)
	{
		this.ct_a = b;
	}
	public void setCt_s(boolean b)
	{
		this.ct_s = b;
	}
	public void setCt_d(boolean b)
	{
		this.ct_d = b;
	}
	public void setFire(boolean b)
	{
		this.fire = b;
	}
	public int getTime_interval()
	{
		
		return this.time_interval;
	}
}
