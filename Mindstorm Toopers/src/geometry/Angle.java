package geometry;

import java.lang.Math;


public class Angle{

	
	public static double toRange2PI(double angle){
		while(angle<=0 || angle> 2*Math.PI){

		if(angle<=0){
		angle += 2*Math.PI;}

		if(angle > 2*Math.PI){
		angle -= 2*Math.PI;
		}
		}
		return angle;
		}
	
	public static double toRangePI(double angle){
		while(angle<=0 || angle> Math.PI){
			
			if(angle<=0){
				angle += Math.PI;}
		
			if(angle > Math.PI){
				angle -= Math.PI;
			}
		}
		return angle;
	}
	public static double toRangeMinusPI(double angle){
		while(angle<=-1*Math.PI || angle> Math.PI){
			
			if(angle<=0){
				angle += 2*Math.PI;}
		
			if(angle > 2*Math.PI){
				angle -= 2*Math.PI;
			}
		}
		return angle;
	}
	public static double to255(double angle){
		return angle = angle*255/(2*Math.PI);
		
	}
	
	public static boolean sameAngle(double a1, double a2){
		return toRange2PI(a1)==toRange2PI(a2);
	}
	
	public static void main (String[] args){
		int i = -10;
		while(i<=10){
			double angle = i*Math.PI/4;
			System.out.println(angle +"\t"+ (Math.toDegrees(angle))+"\t"+toRange2PI(angle)+"\t"+toRangePI(angle));
			i++;
			
		}
	}
	
}