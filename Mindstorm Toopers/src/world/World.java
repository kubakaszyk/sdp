package world;

import java.awt.Image;
import java.awt.image.BufferedImage;

import javax.swing.SwingUtilities;

import vision.VisionRunner;
import geometry.Point;
import geometry.Vector;
import geometry.Area;

/* This class is meant to be used as a helper in the imageprocessor
 * makes everithing more object oriented
 */

public class World {
	
	// image generated only once
	private Image img;
	
	// Object used to represent the ball and our robots.
	private Ball ballObject ;
	private Robot attacker,defender;
	
	// use the get methods for the other team's robots
	private Robot otherAttacker, otherDefender;
		
	// These points represent the locations of the robots.
	private  Point yellowLeft , yellowRight;
	private  Point blueLeft , blueRight;
	// The location of the ball.
	private Point ball;
	
	// The location of the mouse pointer.
	private Point mouse;
	
	private int pitchWidth, pitchHeight, pitchCentre;
	private Vector yellowLeftDir, yellowRightDir, blueLeftDir, blueRightDir;
	// The pixel values of the left and top edges of the pitch
	private int pitchLeft, pitchTop;
	// These are the raw pixel values for the pitch boundaries.
	private int firstSectionBoundary, secondSectionBoundary, thirdSectionBoundary;
	// These are the boundary values in the coordinate system.
	private int firstBoundary, secondBoundary, thirdBoundary;
	// A flag used to tell the robots when vision set-up is done.
	private Boolean ready;
	
	public Boolean getColor() {
		return color;
	}

	public void setColor(Boolean color) {
		this.color = color;
	}

	public void setDirection(Boolean direction) {
		this.direction = direction;
	}

	private int gridSize;
	
	private Boolean color;
	private Boolean direction;
	
	private int gridConstant;
	
	private double initialorientation;
	
	// Used to track the direction of ball movement.
	private Point lastBallLocation;
	private int count;
	
	//TODO: For vision. Maximum dimensions of the pitch! you might want to make these constants.
	private double maxY, minY, maxX, minX;
	//TODO: For Aris: finish the Area definition
	private Area areaA , areaB, areaC, areaD;
	// private double ballSpeedX, ballSpeedY;
	// add everything for the pitch here
	
	// Color - true means yellow, false means blue
	// Direction - true mean right, false means left
	public World(Boolean color, Boolean direction)
	{
		// initialize the world here
		runVision(this);
		this.color = color;
		this.direction = direction;
		Point start = new Point(10,10);
		Vector startVector = new Vector(start, new Point(11,10));
		ballObject = new Ball(start);
		yellowLeftDir = startVector;
		attacker = new Robot(start, new Vector(start,start));
		defender = new Robot(start, new Vector(start,start));
		
		// not sure if the other team's robots are initialized the same way
		otherAttacker = new Robot(start, new Vector(start,start));
		otherDefender = new Robot(start, new Vector(start,start));
		ready = false;
		lastBallLocation = start;
		gridConstant = 474;
		count = 0;
		
		mouse = new Point(0,0);
	}
	
	public void runVision(final World world){
        SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                        new VisionRunner(world);
                }
        });
	}
	
	// methods for ball
	public void setBallXY (Point ballXY)
	{
		int x = (int) ((gridConstant * (ballXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (ballXY.getY() - this.pitchTop)) / this.pitchWidth);
		ballObject.setPos(new Point(x,y));
		this.ball = new Point(x,y);
	}
	
	public void setBallDirection (){
		if (count == 10){
			Vector direction = new Vector(lastBallLocation,ballObject.getPos());
			ballObject.setDir(direction);
			if (Math.abs(direction.getX() + direction.getY()) > 2){
				ballObject.setMoving(true);
			} else {
				ballObject.setMoving(false);
			}
			lastBallLocation = ballObject.getPos();
			count = 0;
		} else {
			count++;
		}
	}
	
	public Vector getBallDirection(){
		return ballObject.getDir();
	}
	
	public Point getBallPos ()
	{
		return this.ball;
	}

	public Ball getBall(){
		return this.ballObject;
	}
	public Robot getAttacker()
	{
		return this.attacker;
	}
	
	public Robot getDefender()
	{
		return this.defender;
	}
	
	public Robot getOtherAttacker()
	{
		return this.otherAttacker;
	}
	
	public Robot getOtherDefender()
	{
		return this.otherDefender;
	}
	
	
	// use this method after processing to track robots
	public void setRobots()
	{
		if(color)
		{
			if(direction)
			{
				otherAttacker.setPos(blueLeft);
				otherDefender.setPos(blueRight);
				attacker.setPos(yellowRight);
				defender.setPos(yellowLeft);
				
				otherAttacker.setDir(blueLeftDir);
				otherDefender.setDir(blueRightDir);
				attacker.setDir(yellowRightDir);
				defender.setDir(yellowLeftDir);
			}
			else
			{
				otherAttacker.setPos(blueRight);
				otherDefender.setPos(blueLeft);
				attacker.setPos(yellowLeft);
				defender.setPos(yellowRight);
				
				otherAttacker.setDir(blueRightDir);
				otherDefender.setDir(blueLeftDir);
				attacker.setDir(yellowLeftDir);
				defender.setDir(yellowRightDir);
			}
		}
		else
		{
			if(direction)
			{
				otherAttacker.setPos(yellowLeft);
				otherDefender.setPos(yellowRight);
				attacker.setPos(blueRight);
				defender.setPos(blueLeft);
				
				otherAttacker.setDir(yellowLeftDir);
				otherDefender.setDir(yellowRightDir);
				attacker.setDir(blueRightDir);
				defender.setDir(blueLeftDir);
			}
			else
			{
				otherAttacker.setPos(yellowRight);
				otherDefender.setPos(yellowLeft);
				attacker.setPos(blueLeft);
				defender.setPos(blueRight);
				
				otherAttacker.setDir(yellowRightDir);
				otherDefender.setDir(yellowLeftDir);
				attacker.setDir(blueLeftDir);
				defender.setDir(blueRightDir);
			}
		}
	}
	
	// methods for yellow robot LEFT
	public void setYellowLeft (Point yellowLeftXY)
	{
		int x = (int) ((gridConstant * (yellowLeftXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (yellowLeftXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.yellowLeft = new Point(x,y);
	}
	
	public void setVectorYellowLeft(Point dot)
	{
		int x = (int) ((gridConstant * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (dot.getY() - this.pitchTop)) / this.pitchWidth);
		this.setyLeft(new Vector(new Point(x,y), yellowLeft));
	}
	
	public void setVectorYellowRight(Point dot)
	{
		int x = (int) ((gridConstant * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (dot.getY() - this.pitchTop)) / this.pitchWidth);
		this.setyRight(new Vector(new Point(x,y), yellowRight));
	}
	
	public Point getYellowLeft()
	{
		return this.yellowLeft;
	}
	
	// methods for yellow robot RIGHT
	
	public void setYellowRight (Point yellowRightXY)
	{
		int x = (int) ((gridConstant * (yellowRightXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (yellowRightXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.yellowRight = new Point(x,y);
	}
	
	public Point getYellowRight()
	{
		return this.yellowRight;
	}

	// methods for blue robot LEFT
	public void setBlueLeft (Point blueLeftXY)
	{
		int x = (int) ((gridConstant * (blueLeftXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (blueLeftXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.blueLeft = new Point(x,y);
	}
	
	public void setVectorBlueLeft(Point dot)
	{
		int x = (int) ((gridConstant * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (dot.getY() - this.pitchTop)) / this.pitchWidth);
		this.setbLeft(new Vector(new Point(x,y), blueLeft));
	}
	
	public Point getBlueLeft()
	{
		return this.blueLeft;
	}
	
	// methods for blue robot RIGHT
	
	public void setBlueRight (Point blueRightXY)
	{
		int x = (int) ((gridConstant * (blueRightXY.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (blueRightXY.getY() - this.pitchTop)) / this.pitchWidth);
		this.blueRight = new Point(x,y);
	}
	
	public void setVectorBlueRight(Point dot)
	{
		int x = (int) ((gridConstant * (dot.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (dot.getY() - this.pitchTop)) / this.pitchWidth);
		this.setbRight(new Vector(new Point(x,y), blueRight));
	}
	
	public Point getBlueRight()
	{
		return this.blueRight;
	}
	
	public Point getAttackerPos(){
		return attacker.getPos();
	}
	
	public Point getOtherAttackerPos(){
		return otherAttacker.getPos();
	}
	
	public Point getDefenderPos(){
		return defender.getPos();
	}
	
	public Point getOtherDefenderPos(){
		return otherDefender.getPos();
	}
	
	public Vector getAttackerDir(){
		return attacker.getDir();
	}
	
	public Vector getOtherAttackerDir(){
		return otherAttacker.getDir();
	}
	
	public Vector getDefenderDir(){
		return defender.getDir();
	}
	
	public Vector getOtherDefenderDir(){
		return otherDefender.getDir();
	}
		
	// methods for the video image
	public void setImage(Image img)
	{
		this.img = img;
	}
	
	public Image getImage()
	{
		return this.img;
	}

	public void setWidth(int width) {
		this.maxX = gridConstant;
		this.pitchWidth = width;
	}

	public int getWidth() {
		return pitchWidth;
	}

	public void setHeight(int height) {
		if ((this.pitchWidth / gridConstant) != 0){
			this.maxY = height / (this.pitchWidth / gridConstant);
		} else {
			maxY = 350;
		}
		this.pitchHeight = height;
	}

	public int getHeight() {
		return pitchHeight;
	}

	public void setPitchCentre(int pitchCentre) {
		this.pitchCentre = pitchCentre;
	}

	public int getPitchCentre() {
		return pitchCentre;
	}
	
	public Area getOurDefenderArea(){ //TODO!!
		return areaA;
	}

	public double getMaxX() {
		return maxX;
	}

	public void setMaxX(double maxX) {
		this.maxX = maxX;
	}

	public double getMaxY() {
		return maxY;
	}

	public void setMaxY(double maxY) {
		this.maxY = maxY;
	}

	public double getMinY() {
		return minY;
	}

	public void setMinY(double minY) {
		this.minY = minY;
	}

	public double getMinX() {
		return minX;
	}

	public void setMinX(double minX) {
		this.minX = minX;
	}

	public void setyLeft(Vector yLeft) {
		this.yellowLeftDir = yLeft;
	}

	public Vector getyLeft() {
		return yellowLeftDir;
	}

	public void setyRight(Vector yRight) {
		this.yellowRightDir = yRight;
	}

	public Vector getyRight() {
		return yellowRightDir;
	}

	public void setbLeft(Vector bLeft) {
		this.blueLeftDir = bLeft;
	}

	public Vector getbLeft() {
		return blueLeftDir;
	}

	public void setbRight(Vector bRight) {
		this.blueRightDir = bRight;
	}

	public Vector getbRight() {
		return blueRightDir;
	}
	
	public void setPitchLeft(int pitchLeft) {
		this.minX = 0;
		this.pitchLeft = pitchLeft;
	}

	public int getPitchLeft() {
		return pitchLeft;
	}

	public void setPitchTop(int pitchTop) {
		this.minY = 0;
		this.pitchTop = pitchTop;
	}

	public int getPitchTop() {
		return pitchTop;
	}
	
	public void setIO(double o)
	{
		this.initialorientation = o;
	}
	
	public double getIO()
	{
		return this.initialorientation;
	}
	
	public boolean getDirection() {
		return this.direction;
	}

	public void setFirstSectionBoundary(int firstSectionBoundary) {
		this.firstSectionBoundary = firstSectionBoundary;
		this.setFirstBoundary((gridConstant * (firstSectionBoundary - pitchLeft)) / pitchWidth);
	}

	public int getFirstSectionBoundary() {
		return firstSectionBoundary;
	}

	public void setSecondSectionBoundary(int secondSectionBoundary) {
		this.secondSectionBoundary = secondSectionBoundary;
		this.setSecondBoundary((gridConstant * (secondSectionBoundary - pitchLeft)) / pitchWidth);
	}

	public int getSecondSectionBoundary() {
		return secondSectionBoundary;
	}

	public void setThirdSectionBoundary(int thirdSectionBoundary) {
		this.thirdSectionBoundary = thirdSectionBoundary;
		this.setThirdBoundary((gridConstant * (thirdSectionBoundary - pitchLeft)) / pitchWidth);
	}

	public int getThirdSectionBoundary() {
		return thirdSectionBoundary;
	}

	public void setReady(Boolean ready) {
		this.ready = ready;
	}

	public Boolean getReady() {
		return ready;
	}

	public void setFirstBoundary(int firstBoundary) {
		this.firstBoundary = firstBoundary;
	}

	public int getFirstBoundary() {
		return firstBoundary;
	}

	public void setSecondBoundary(int secondBoundary) {
		this.secondBoundary = secondBoundary;
	}

	public int getSecondBoundary() {
		return secondBoundary;
	}

	public void setThirdBoundary(int thirdBoundary) {
		this.thirdBoundary = thirdBoundary;
	}

	public int getThirdBoundary() {
		return thirdBoundary;
	}

	public void setMouse(Point mouse) {
		int x = (int) ((gridConstant * (mouse.getX() - this.pitchLeft)) / this.pitchWidth);
		int y = (int) ((gridConstant * (mouse.getY() - this.pitchTop)) / this.pitchWidth);
		this.mouse = new Point(x,y);
	}

	public Point getMouse() {
		return mouse;
	}
	
	/*
	 * 	public void setBallSpeed(double x, double y)
	{
		this.ballSpeedX = x;
		this.ballSpeedY = y;
	}
	
	public double getBallSpeedX()
	{
		return this.ballSpeedX;
	}
	
	public double getBallSpeedY()
	{
		return this.ballSpeedY;
	}
	 */

}
