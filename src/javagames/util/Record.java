package javagames.util;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import game.*;
//记录双方分数
public class Record {
	private GameInterface gi;
	public Record(GameInterface gi)
	{
		this.gi = gi;
	}
	public void drawRecord(Graphics g)
	{
		Font font = new Font("arial",Font.ITALIC,25);
		g.setFont(font);
		g.setColor(Color.white);
		int record_x = 600,record_y = 20,record_h = 20;
		for(int i = 0;i < gi.tanks.size();i++)
		{
			String s = "player"+gi.tanks.get(i).getTank_id()+" ATK:"+gi.tanks.get(i).getTank_ATK()
					+" HP:"+gi.tanks.get(i).Get_hp();
			g.drawString(s, record_x, record_y + i * record_h);
		}
	}
}
