package commands;

public class Command {
    private int distance;
    private int angledirec;
    private int angle;
    private CommandNames command;
    
    public Command(CommandNames c, int distance, int angle) {
        this.command = c;
        this.distance = distance;
        if (angle < 0){
        	angledirec = 0;
        	this.angle = Math.abs(angle);
        }
        else {
        	angledirec = 1;
        	this.angle = angle;
        }
       
    }

    public CommandNames getCommand() {
        return this.command;
    }
    
    public int getDistance() {
    	//System.out.println("Speed set at " + speed);
        return this.distance;
    }
    
    public int getAngle() {
    	//System.out.println("Dist/Angle set as " + distAngle);
        return this.angle;
    }
    public int getAngleDirec()
    {
    	return this.angledirec;
    }
    public boolean isNothing(){
    	return this.command.equals(CommandNames.DONOTHING);
    }
    
    
}
