package citbyui.davisdanny.yahtzee.util;

public class Util {
	
	public static void nyi(String message){
		System.out.println("Not Yet Impletementd: "+message);
	}
	
	public static void error(String expected,String recieved){
		System.out.println("ERROR: Expected "+expected+" recieved: "+recieved);
	}
	
	public static void debug(String message){
		//System.out.println("DEBUG: "+message);
	}
	
	public static int[] diceFromString(String diceString){
		char[] diceChars = diceString.toCharArray();
		int[] dice = new int[5];
		int j = 0;
		for(int i = 1;i<=9;i+=2){
			dice[j] = Character.getNumericValue(diceChars[i]);
			j++;
		}
		return dice;
	}
	
	public static String diceToString(int[] dice){
		String diceString = "{";
		for(int i = 0;i<dice.length-1;i++){
			diceString += dice[i] + ",";
		}
		diceString += dice[dice.length-1]+"}";
		return diceString;
	}
	
	
}
