package game;

import java.awt.Color;
import javagames.util.*;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;

import javax.swing.*;

public class GameInterface extends JPanel{
	public LinkedList<Bullet> bullets;
	public Bullet test_bullet;
	public Wall wall;
	public Record record;
	public ArrayList<Tank> tanks;
//	Vector<Bullet> bullets1;
	public Map map;
	public FrameRate framerate;
	public JFrame jf;
	public int game_interface_wdith = 880;
	public int game_interface_height = 880;
	//注册坦克
//	public Tank tank0;
//	public Tank tank1;
	//构造函数
	public GameInterface()
	{
		wall = new Wall();
		test_bullet = new Bullet();
		map = new Map(this);
		framerate = new FrameRate();
		bullets = new LinkedList<>();
		tanks = new ArrayList<>();
		record = new Record(this);
		//创建坦克实例化对象
		
		tanks.add(new Tank(this,163,790,0,5,5,map,3));
		tanks.add(new Tank(this,500,790,1,500,500,map,3));
		tanks.add(new Tank(this,500,0,2,50,50,map,2));
		tanks.add(new Tank(this,200,0,3,50,50,map,2));
		jf = new JFrame("TankBattle");
		jf.setSize(this.game_interface_wdith, this.game_interface_height + 32);
	    jf.setLocationRelativeTo(null);
	    jf.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    jf.setLayout(null);
	    this.setLayout(null);
	    this.setBackground(Color.black);
	    framerate.initialize();
	    this.setSize(this.game_interface_wdith,this.game_interface_height);
	    jf.setResizable(false);
	    
		jf.add(this);
		
		//注册键盘监听事件
		
		jf.addKeyListener(tanks.get(1));
		jf.addKeyListener(tanks.get(0));
//		this.repaint();
		jf.setVisible(true);
		//子弹线程
		new Thread()
		{
			public void run()
			{
				while(true)
				{
					
					try
					{
						Thread.sleep(10);
						
						//子弹移动
//						int len = bullets.size();
//						for(int i = 0;i < len;i++)
//						{
//							Bullet bullet = bullets.get(i);
//							bullet.bulletFly();
//							int bx,by;
//							bx = bullet.GetBx();
//							by = bullet.GetBy();
//							if(bx<0 || bx > game_interface_wdith || by < 0 || by > game_interface_height)
//							{
//								
//								bullets.remove(i);
//								--len;
//								--i;
//							}
//						}
						Iterator<Bullet> it = bullets.iterator();
				        while (it.hasNext()) {
				        	Bullet bullet = (Bullet) it.next();
				        	bullet.bulletFly();
				        	if(!bullet.GetLife())
				        	{
				        		continue;
				        	}
				        	int bx,by,atk,bh,bw;
							bx = bullet.GetBx();
							by = bullet.GetBy();
							bw = bullet.GetBw();
							bh = bullet.GetBh();
							atk = bullet.GetATK();
							int tank_id = bullet.GetTank_id();
							int id = bullet.GetTank_id();
//							System.out.println("atk ="+atk);
				        	if(IsMiss(bx, by, atk,tank_id,id))
				        	{
				        		
//								tanks.get(id).SetMagazine();   //这里和判断子弹是否越界函数重复
				        		
				        		bullet.SetIsLife(false);
				        	}
				        	
				        	switch(bullet.GetBd())
				        	{
				        	case 'w':
				        		if(IsHitWall(bx, by) || IsHitWall(bx + bw, by))
				        		{
				        			bullet.SetIsLife(false);
				        		}
				        	case 'a':
				        		if(IsHitWall(bx, by) || IsHitWall(bx, by + bh))
				        		{
				        			bullet.SetIsLife(false);
				        		}
				        	case 's':
				        		if(IsHitWall(bx, by + bh) || IsHitWall(bx + bw, by + bh))
				        		{
				        			bullet.SetIsLife(false);
				        		}
				        	case 'd':
				        		if(IsHitWall(bx + bw, by) || IsHitWall(bx + bw, by + bh))
				        		{
				        			bullet.SetIsLife(false);
				        		}
				        	}
				        }
//						System.out.println("移动成功！");
					}catch(Exception e)
					{
						e.printStackTrace();
//						System.out.println("移动失败！");
					}
					
				}
				
			}
		}.start();
		
		//注册坦克线程
		Thread thread_tank0 =new Thread(tanks.get(0));
		thread_tank0.start();
		Thread thread_tank1 =new Thread(tanks.get(1));
		thread_tank1.start();
		Thread thread_tank2 =new Thread(tanks.get(2));
		thread_tank2.start();
		Thread thread_tank3 =new Thread(tanks.get(3));
		thread_tank3.start();
	}
	//绘制面板
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		map.DrawMap(g);
		framerate.calculate();
		g.setColor(Color.white);
		g.drawString( framerate.getFrameRate(), 30, 30 );
		Iterator<Bullet> it = bullets.iterator();
        while (it.hasNext()) {
        	Bullet bullet = (Bullet) it.next();
        	bullet.drawBullet(g);
        	int bx,by;
			bx = bullet.GetBx();
			by = bullet.GetBy();
			if(bx<0 || bx > game_interface_wdith || by < 0 || by > game_interface_height)
			{
				int id = bullet.GetTank_id();
				tanks.get(id).SetMagazine();
				it.remove();
				
			}
        }
        //绘制记分板
        record.drawRecord(g);
		//绘制坦克
		tanks.get(0).drawTank(g);
		tanks.get(1).drawTank(g);
		tanks.get(2).drawTank(g);
		tanks.get(3).drawTank(g);
		this.repaint();
		
	}
	//判断子弹是否击中坦克
	public boolean IsMiss(int bx,int by,int atk,int tank_id,int id)           //如果击中返回true
	{
		for(int i = 0;i < tanks.size();i++)
		{
			int tx = tanks.get(i).Get_tx();
			int ty = tanks.get(i).Get_ty();
			int tw = tanks.get(i).Get_tw();
			int th = tanks.get(i).Get_th();
			int thp = tanks.get(i).Get_hp();
			int other_tank_id = tanks.get(i).getTank_id();
			if(bx > tx && bx < tx + tw && by > ty && by < ty + th && thp > 0)
			{
//				System.out.println("坦克被击中");
				if(!(tank_id > 1 && other_tank_id > 1))
				{
					if(tanks.get(i).Set_hp(atk))
					tanks.get(id).setATK(5);
					
				return true;
				}
				
			}
		}
		
		return false;

	}
	//判断子弹是否击中墙体
	public boolean IsHitWall(int next_location_x,int next_location_y)
	{
		int tx,ty,tw,th,thp,i,j;
		if(next_location_y % 40 == 0 && next_location_y != 0)
			next_location_y -= 1;
		if(next_location_x % 40 == 0 && next_location_x != 0)
			next_location_x -= 1;
		i = next_location_y / 40;
		j = next_location_x / 40;
		if(i == 22 || j == 22)return true;
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
		if ((next_location_x >= tx && next_location_x <= tx + tw && next_location_y >= ty && next_location_y <= ty + th && thp > 0)||
				(next_location_x + this.test_bullet.bw >= tx && next_location_x + this.test_bullet.bw <= tx + tw && next_location_y >= ty && next_location_y <= ty + th && thp > 0)||
				(next_location_x >= tx && next_location_x <= tx + tw && next_location_y + this.test_bullet.bh>= ty && next_location_y + this.test_bullet.bh <= ty + th && thp > 0)||
				(next_location_x + this.test_bullet.bw >= tx && next_location_x + this.test_bullet.bw <= tx + tw && next_location_y + this.test_bullet.bh>= ty && next_location_y + this.test_bullet.bh <= ty + th && thp > 0))
		{
			map.SetMap(i, j);
			return true;
		}
		return false;
	}
}
