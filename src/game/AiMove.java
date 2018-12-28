package game;

public class AiMove implements Runnable{
	private Tank tank;
	public AiMove(Tank tank)
	{
		this.tank = tank;
	}
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true)
		{
//			System.out.println("============");
			int num =(int)(Math.random() * 5);
//			int num = 1;
			int time_interval = 1000;
			if(tank.getTank_id() > 1)
			{try {
				switch(num)
				{
				case 0:tank.setCt_w(true);break;
				case 1:tank.setCt_a(true);break;
				case 2:tank.setCt_s(true);break;
				case 3:tank.setCt_d(true);break;
				case 4:tank.setFire(true);
				time_interval = 10;
				break;
				}
				
					Thread.sleep(time_interval);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println("================================");
					e.printStackTrace();
				}		
				
			}
			tank.setCt_w(false);
			tank.setCt_a(false);
			tank.setCt_s(false);
			tank.setCt_d(false);
			tank.setFire(false);
		}
	}
	
}
