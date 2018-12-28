package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Bullet {
	public GameInterface game;
	private int bullet_speed = 6;
	public int bx,by,bh = 20,bw = 20;
	private int ATK;
	private char bd;
	private boolean is_life = true;
	private int tank_id;
	private Image bullet_image_w = Toolkit.getDefaultToolkit().getImage("bullet0_w.jpg");
	private Image bullet_image_a = Toolkit.getDefaultToolkit().getImage("bullet0_a.jpg");
	private Image bullet_image_s = Toolkit.getDefaultToolkit().getImage("bullet0_s.jpg");
	private Image bullet_image_d = Toolkit.getDefaultToolkit().getImage("bullet0_d.jpg");
	private Image bullet_image = null;
	//构造函数 初始化子弹的各个属性
	public Bullet()
	{
		
	}
	public Bullet(GameInterface game,int bx,int by,char bd,int atk,int tank_id)
	{
//		System.out.println("bullet_atk = "+atk);
		this.tank_id = tank_id;
		this.ATK = atk;
		this.game = game;
		this.bx = bx;
		this.by = by;
		this.bd = bd;
		switch(bd)
		{
		case 'w':
			bullet_image = bullet_image_w;
			break;
		case 'a':
			bullet_image = bullet_image_a;
			break;
		case 's':
			bullet_image = bullet_image_s;
			break;
		case 'd':
			bullet_image = bullet_image_d;
			break;
		}
	}
	//绘制子弹
	public void drawBullet(Graphics g)
	{
		if(this.is_life)
		{
//			System.out.println("is_life = "+this.is_life);
			g.drawImage(bullet_image, bx, by, bw, bh, this.game);
		}
//		else System.out.println("is_life 设置为"+is_life);
		
	}
	//子弹射出
	public void bulletFly()
	{
		switch(bd)
		{
		case 'w':
			by -= bullet_speed;
			break;
		case 'a':
			bx -= bullet_speed;
			break;
		case 's':
			by += bullet_speed;
			break;
		case 'd':
			bx += bullet_speed;
			break;
		}
	}
	public int GetBx()
	{
		return this.bx;
	}
	public int GetBy()
	{
		return this.by;
	}
	public int GetATK()
	{
		return this.ATK;
	}
	public int GetBw()
	{
		return this.bw;
	}
	public int GetBh()
	{
		return this.bh;
	}
	public boolean GetLife()
	{
		return this.is_life;
	}
	public void SetIsLife(boolean b)
	{
		
		this.is_life = b;
//		System.out.println("is_life 设置为"+is_life);
	}
	public int GetBd()
	{
		return bd;
	}
	public int GetTank_id()
	{
		return this.tank_id;
	}
}
