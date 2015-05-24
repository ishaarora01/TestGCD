package org.isha.util;

public class Util {
	
	public static int calculateGcd(int number1, int number2)
	{
		if(number2 == 0){
            return number1;
        }
        return calculateGcd(number2, number1%number2);
	}
	
	public static boolean isNumeric(String input1, String input2)
	{
		if(input1.matches("\\d+") && input2.matches("\\d+"))
			return true;
		
		return false;
	}
	
	public static boolean isInt(String input1, String input2)
	{
		try{
			Integer.parseInt(input1);
			Integer.parseInt(input2);
		}catch(NumberFormatException e)
		{
			return false;
		}
		
		return true;
	}

}
