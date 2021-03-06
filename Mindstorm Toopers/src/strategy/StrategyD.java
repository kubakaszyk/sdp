package strategy;

import geometry.Point;
import geometry.Vector;
import commands.CommandNames;

import world.World;



public class StrategyD {
	//strategy for attacker
	private World w;
	private int State;
	private int strictfrontboundary;
	private int softfrontboundary;
	private int strictbackboundary;
	private int softbackboundary;
	
	
	
	
	public StrategyD(World w)
	{
		this.w = w;
		this.State = 0;
		
		if (w.getDirection())
		{
			strictfrontboundary =76; 
			softfrontboundary =96; 
			strictbackboundary =26; 
			softbackboundary =6; 
		}
		else
		{
			strictfrontboundary = 356;
			softfrontboundary = 336;
			strictbackboundary = 406;
			softbackboundary = 426;
		}
	}
		
	public void setState(int State)
	{
		this.State=State;
	}
	
	public int getState()
	{
		return this.State;
	}
		//State     |Movement
		//0         |intercept the ball
		//1         |catch the ball
		//2         |move to the kick point
		//3			|kick
	
	
	
	public Goal getGoal(Goal lastgoal)
	{
		Goal g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
		switch (this.State)
		{
		case 0:
		{
			Point b = w.getBall().getPos();
			Point r = w.getDefenderPos();
			Point gp;
			
			//vision bug tolerance
			if ((b.getX()==0&&b.getY()==0)||(r.getX()==0&&r.getY()==0))
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,true);
				break;
			}
			
			//intercept at the boundary strategy
			if (w.getDirection())
			{
				//facing right
				//go to the boundary on the far side of the ball in the attacker zone
				if (b.getX()>r.getX())
				{
					gp = new Point(strictbackboundary,b.getY());
				}
				else
				{
					gp = new Point(strictfrontboundary,b.getY());
				}
				
				System.out.println("The ball is at "+b.toString());
				System.out.println("The attacker is at "+r.toString());
				
				if (b.getX()>softbackboundary&&b.getX()<softfrontboundary) {
					g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
					this.State = 1;
					
					break;
				}
				
				g = new Goal(gp, CommandNames.MOVE,false,false);
				
				break;
				
			}
			else
			{
				// facing left
				if (b.getX()>r.getX())
				{
					gp = new Point(strictfrontboundary,b.getY());
				}
				else
				{
					gp = new Point(strictbackboundary,b.getY());
					
				}
				System.out.println("The ball is at "+b.toString());
				System.out.println("The attacker is at "+r.toString());
				
				if (b.getX()>softfrontboundary&&b.getX()<softbackboundary) {
					g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
					this.State = 1;
					
					break;
				}
				
				g = new Goal(gp, CommandNames.MOVE,false,false);
				
				break;				
			}
		
		}
		case 1:
		{
			//if the ball is caught, switch to state 2
			
			//if(w.getBall().iscaught()) 
			//The iscaught can not be implemented now
			//so I wrote a function here for it
			Point r = w.getDefenderPos();
			Vector v = w.getDefenderDir();
			Point b = w.getBallPos();
			
			//vision bug tolerance
			if ((b.getX()==0&&b.getY()==0)||(r.getX()==0&&r.getY()==0))
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,true);
				break;
			}
			
			// if the ball is not in the attacker zone, switch back to interception mode
			if (w.getDirection())
			{
				//facing right
				//go to the boundary on the far side of the ball in the attacker zone

				System.out.println("The ball is at "+b.toString());
				System.out.println("The attacker is at "+r.toString());
				
				if (!(b.getX()>softbackboundary&&b.getX()<softfrontboundary)) {
					g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
					this.State = 0;
					
					break;
				}
				
				
			}
			else
			{
				// facing left
			
				System.out.println("The ball is at "+b.toString());
				System.out.println("The attacker is at "+r.toString());
		
				if (!(b.getX()>softfrontboundary&&b.getX()<softbackboundary)) {
					g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
					this.State = 0;
					
					break;
				}
				
				
			}
			
			
			//temporary function for iscaught
			//when the real one is done, please remove the code below
			Vector rb = new Vector(r, 20, v.getOrientation());
			Point bc = new Point(r.getX()+rb.getX(),r.getY()+rb.getY());
			boolean iscaught = Point.pointDistance(b, bc) < 10;
			
			
			//to here
			if (iscaught)
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
				this.State=2;
				break;
			}
			
			//Point b = w.getBall().getPos();
			//w.getBall().setCaught(true);
			//do catch0
			g = new Goal(b, CommandNames.CATCH,false,false);
			
			break;
			
		}
		case 2:
		{		
			//We now have 2 options for kick positions
			Point r = w.getDefenderPos();
			Point b = w.getBallPos();
			Vector v = w.getDefenderDir();
			
			//vision bug tolerance
			if ((b.getX()==0&&b.getY()==0)||(r.getX()==0&&r.getY()==0))
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,true,false);
				break;
			}
			
			//if ball is not caught, switch back to state 1
			//temporary function for iscaught
			//when the real one is done, please remove the code below
			Vector rb = new Vector(r, 20, v.getOrientation());
			Point bc = new Point(r.getX()+rb.getX(),r.getY()+rb.getY());
			boolean iscaught = Point.pointDistance(b, bc) < 10;
			
			if (!iscaught)
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,false);
				this.State=1;
				break;
			}
			
			
			Point kp;
			System.out.println("kick The attacker is at "+w.getDefender().toString());
			if  (w.getDirection())
			{
				//facing right
				if (Point.pointDistance(w.getOtherAttackerPos(), new Point(150,50))<60)
				{
					kp = new Point(50,50);
					g = new Goal(kp, CommandNames.MOVE,false,false);
					
				}
				else
				{
					kp = new Point(50,170);
					g = new Goal(kp, CommandNames.MOVE,false,false);
					
				}
			
			}
			else
			{
				//facing left
				if (Point.pointDistance(w.getOtherAttackerPos(), new Point(300,50))<60)
				{
					kp = new Point(350,50);
					g = new Goal(kp, CommandNames.MOVE,false,false);
					
				}
				else
				{
					kp = new Point(350,170);
					g = new Goal(kp, CommandNames.MOVE,false,false);
					
				}
			}
			
			// when the robot is close enough to the kick point
			// and has the ball
			// then switch to the kick mode
			// if not, switch back to catch mode
			// and open the catcher
			if (iscaught&&Point.pointDistance(r, kp)<30)
			{
				this.State = 3;
			}
			else
			{
				this.State = 1;
				g = new Goal(new Point(0,0), CommandNames.KICK,false,false);
			}
			
			break;
		}
		case 3:
		{
			Point r = w.getDefenderPos();
			Point b = w.getBallPos();
			Vector v = w.getDefenderDir();
			//vision bug tolerance
			if ((b.getX()==0&&b.getY()==0)||(r.getX()==0&&r.getY()==0))
			{
				g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,true);
				break;
			}
			
			//The point is the point we want to kick the ball to.
			Point goal;
			if (w.getDirection())
			{
				//facing right
				goal = new Point (474,114);
			}
			else
			{
				//facing left
				goal = new Point (0,114);
			}

			g = new Goal(goal, CommandNames.KICK,false,false);
			
			//temporary function for iscaught
			//when the real one is done, please remove the code below
			Vector rb = new Vector(r, 20, v.getOrientation());
			Point bc = new Point(r.getX()+rb.getX(),r.getY()+rb.getY());
			boolean iscaught = Point.pointDistance(b, bc) < 10;
			if (!iscaught)
			{
				this.State = 0;
			}
			break;
			
			
		}
		default:
		{
			// generally won't be called
			// in special situation this will not be called either
			g = new Goal(new Point(0,0), CommandNames.DONOTHING,false,true);
			System.out.println("going default");
			break;
		}
		
		}
		
		
		System.out.println("Goal before judge is "+g.toString());
		
		
		Goal output = judge(lastgoal,g);
		System.out.println("The state is " + this.State);
		System.out.println(output.toString());
		return output;
	}
	
	
	public static Goal judge(Goal currentgoal, Goal newgoalG)
	{
		Goal newgoal = newgoalG;
		if (newgoal.getAbort())
		{
			//send abort command and pass the new goal
			System.out.println("This is an abort goal");
			return newgoal;
		}
		
		else if (newgoal.isNull())
		{
			
			System.out.println("This is a null goal");
			return newgoal;
		}
		
		else 
		{
			// do judge and decide if we should send the command
			// when the command name is different from the last one (except donothing command)
			if (currentgoal.getMove() != newgoal.getMove())
			{
				System.out.println("This is set to be an abort goal, a new command goal will be executed.");
				newgoal.setAbort(true);
				return newgoal;
			}
			else
			{
				// when the command has the same move as the last one, 
				// we compare the goal point where we want the robot to go
				// if that's a close point to the old one
				// do not change the command
				// if not
				// update the old goal
				if (Point.pointDistance(currentgoal.getGoal(), newgoal.getGoal())< 10)
				{
					System.out.println("old goal " + currentgoal.toString());
					System.out.println("new goal " + newgoal.toString());
					System.out.println("This is set to be a null goal");
					
					newgoal.setNull(true);
					return newgoal;
				}
				else
				{
					System.out.println("This is set to be an abort goal");
					newgoal.setAbort(true);
					return newgoal;
				}
			}
				
		}
		

	}
	
}
