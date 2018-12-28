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
	//ע��̹��
//	public Tank tank0;
//	public Tank tank1;
	//���캯��
	public GameInterface()
	{
		wall = new Wall();
		test_bullet = new Bullet();
		map = new Map(this);
		framerate = new FrameRate();
		bullets = new LinkedList<>();
		tanks = new ArrayList<>();
		record = new Record(this);
		//����̹��ʵ��������
		
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
		
		//ע����̼����¼�
		
		jf.addKeyListener(tanks.get(1));
		jf.addKeyListener(tanks.get(0));
//		this.repaint();
		jf.setVisible(true);
		//�ӵ��߳�
		new Thread()
		{
			public void run()
			{
				while(true)
				{
					
					try
					{
						Thread.sleep(10);
						
						//�ӵ��ƶ�
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
				        		
//								tanks.get(id).SetMagazine();   //������ж��ӵ��Ƿ�Խ�纯���ظ�
				        		
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
//						System.out.println("�ƶ��ɹ���");
					}catch(Exception e)
					{
						e.printStackTrace();
//						System.out.println("�ƶ�ʧ�ܣ�");
					}
					
				}
				
			}
		}.start();
		
		//ע��̹���߳�
		Thread thread_tank0 =new Thread(tanks.get(0));
		thread_tank0.start();
		Thread thread_tank1 =new Thread(tanks.get(1));
		thread_tank1.start();
		Thread thread_tank2 =new Thread(tanks.get(2));
		thread_tank2.start();
		Thread thread_tank3 =new Thread(tanks.get(3));
		thread_tank3.start();
	}
	//�������
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
        //���ƼǷְ�
        record.drawRecord(g);
		//����̹��
		tanks.get(0).drawTank(g);
		tanks.get(1).drawTank(g);
		tanks.get(2).drawTank(g);
		tanks.get(3).drawTank(g);
		this.repaint();
		
	}
	//�ж��ӵ��Ƿ����̹��
	public boolean IsMiss(int bx,int by,int atk,int tank_id,int id)           //������з���true
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
//				System.out.println("̹�˱�����");
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
	//�ж��ӵ��Ƿ����ǽ��
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
