package world;
import geometry.Point;
import geometry.Vector;

public class Ball {

	private Point position;
	private Vector orientation;
	private int speed;
	private boolean moving,caught;
		
	public Ball(Point position) 
	{
		this.position = position;
		this.speed = 0;
		this.moving = false;
	}

	
	public boolean isMoving()
	{
		return moving;
	}
	
	public void setMoving (boolean moving)
	{
		this.moving = moving;
	}
	
	public Point getPos() 
	{
		return position;
	}
	
	public void setPos(Point position) 
	{
		 this.position = position;
	}
	
	public Vector getDir()
	{
		return orientation;
	}
	
	public void setDir(Vector direction)
	{
		this.orientation = direction;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public boolean iscaught()
	{
		return caught;
	}
	
	public void setCaught(boolean caught) 
	{ 
		this.caught = caught; 
	}
	
}
