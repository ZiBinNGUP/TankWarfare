package game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

public class Wall {
	private int wall_x;
	private int wall_y;
	private int wall_width = 40;
	private int wall_height = 40;
	private GameInterface gi;
	public Wall()
	{
		
	}
	public Wall(GameInterface gi)
	{
		this.gi = gi;
	}
	public void DrawWall(Graphics g,int wall_x,int wall_y)
	{
		wall_x *= 40;
		wall_y *= 40;
		//TODO 墙体图片需要修改
		Image image = Toolkit.getDefaultToolkit().getImage("wall.jpg");
		g.drawImage(image, wall_x, wall_y, wall_width, wall_height, gi);
	}
	public int GetWall_width()
	{
		return wall_width;
	}
	public int GetWall_height()
	{
		return wall_height;
	}
}
