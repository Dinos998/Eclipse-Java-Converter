import java.util.*;
import java.time.*;
import java.time.format.DateTimeFormatter;

public class converter
{
	
	static Scanner s = new Scanner(System.in);
	static boolean valid = true;
	static int decimalPlaces = 2;
	static int userTimeZone = setUserTimeZone();
	static String[] abbreviations = new String[] {"mm", "cm", "m", "km", "in", "ft", "yd", "mi"};
	static String[] measurements = new String[] {"Millimetre", "Centimetre", "Metre", "Kilometre", "Inch", "Foot", "Yard", "Mile"};
	static String reset = "\u001B[0m";
	static String red = "\033[0;91m";
	static String yellow = "\033[0;93m";
	static String green = "\033[0;92m";
	static String cyan = "\033[0;96m";
	static String purple = "\033[0;95m";
	static String blue = "\033[0;94m";
	static String gray = "\033[0;90m";
	static String degree = "\u00B0";
	static String bold = "\033[0;1m";
	
	//when color hates me
//	static String reset = "";
//	static String red = "";
//	static String yellow = "";
//	static String green = "";
//	static String cyan = "";
//	static String purple = "";
//	static String blue = "";
//	static String gray = "";
//	static String degree = "";
//	static String bold = "";
	
	public static void main(String[] args)
	{
		int input;
		
		while(true)
		{
			valid = true;
			input = promptInt("Enter a value to start converting:\n"
							+ "0: Temperature\n"
							+ "1: Time\n"
							+ "2: Length\n"
							+ "3: Settings\n"
							+ "4: Exit", 4);
			
			if(!valid)
			{
				System.out.println("\nProgram Stopped");
				System.exit(1);
			}
			else if(input == 0)
				tempConverter();
			else if(input == 1)
				timeConverter();
			else if(input == 2)
				lengthConverter();
			else if(input == 3)
				settings();
			else
				System.out.println(red + "That is not an accepted value. Please input another value.\n\n" + reset);
		}
	}
	
	
	//Temperature Converter main method
	public static void tempConverter()
	{
		int inputVal;
		int outputVal;
		double inputTempNum;
		String inputTemp;
		String outputTemp;
		int[] validInputs = new int[] {-1, 0, 1, 2};
		boolean help;
		
		while(true)
		{
			help = true;
			valid = true;
			inputVal = promptInt("What temperature are you converting from?\n"
							   + "0: Fahrenheit\n"
							   + "1: Celsius\n"
							   + "2: Kelvin\n"
							   + "3: Exit", 3);
			
			if(!dataValidation(inputVal, validInputs))
			{
				help = false;
				valid = false;
			}

			outputVal = promptInt("What temperature are you converting to?\n"
								+ "0: Fahrenheit\n"
								+ "1: Celsius\n"
								+ "2: Kelvin\n"
								+ "3: Exit\n", 3);
			
			if(!dataValidation(outputVal, validInputs))
			{
				help = false;
				valid = false;
			}
				
			if(!valid)
			{
				if(help)
					break;
				else
					System.out.println(red + "That is not a valid input. Please input another value.\n\n" + reset);
			}
			else if(inputVal == outputVal)
			{
				System.out.println(red + "You cannot convert between the same temperature.\n\n" + reset);
				valid = false;
			}
			
			while(valid)
			{
				if(inputVal == 0)
					inputTemp = red + "Fahrenheit" + reset;
				else if(inputVal == 1)
					inputTemp = blue + "Celsius" + reset;
				else if(inputVal == 2)
					inputTemp = purple + "Kelvin" + reset;
				else
					inputTemp = "";
				
				if(outputVal == 0)
					outputTemp = red + "Fahrenheit" + reset;
				else if(outputVal == 1)
					outputTemp = blue + "Celsius" + reset;
				else if(outputVal == 2)
					outputTemp = purple + "Kelvin" + reset;
				else
					outputTemp = "";
				inputTempNum = promptDouble("Enter a value to convert: ('x' to Exit)\n"
						  				  + "(" + inputTemp + " to " + outputTemp + ")");
				if(valid)
				{
					inputTemp = "";
					outputTemp = "";
					TemperatureConverter temp = new TemperatureConverter(inputVal, inputTempNum);
					if(inputVal == 0)
						inputTemp = red + roundNum(inputTempNum, decimalPlaces) + degree + "F" + reset;
					else if(inputVal == 1)
						inputTemp = blue + roundNum(inputTempNum, decimalPlaces) + degree + "C" + reset;
					else if(inputVal == 2)
						inputTemp = purple + roundNum(inputTempNum, decimalPlaces) + "K" + reset;
					
					if(outputVal == 0)
						outputTemp = red + roundNum(temp.getFahrenheit(), decimalPlaces) + degree + "F" + reset;
					else if(outputVal == 1)
						outputTemp = cyan + roundNum(temp.getCelsius(), decimalPlaces) + degree + "C" + reset;
					else if(outputVal == 2)
						outputTemp = purple + roundNum(temp.getKelvin(), decimalPlaces) + "K" + reset;
					else
						valid = false;
					
					if(valid)
						System.out.println(inputTemp + " = " + outputTemp + "\n\n");
				}
			}
		}
	}
	
	//Time Converter main method
	public static void timeConverter()
	{
		DateTimeFormatter hourFormat = DateTimeFormatter.ofPattern("HH");
		DateTimeFormatter minuteFormat = DateTimeFormatter.ofPattern("mm");
		int input;
		String zoneName;
		int zoneNum;
		int hour = Integer.parseInt(LocalTime.now().format(hourFormat).toString());
		int minute = Integer.parseInt(LocalTime.now().format(minuteFormat).toString());
		WorldTimeConverter WTC = new WorldTimeConverter(hour, minute, userTimeZone);
		int delimiter;
		
		while(true)
		{
			input = promptInt("What type are you converting from?\n"
							+ "0: UTC\n"
							+ "1: Other\n"
							+ "2: Set Time Zone\n"
							+ "3: Exit\n", 3);
			
			if(!valid)
				break;
			if(input == 0)
			{
				while(true)
				{
					hour = Integer.parseInt(LocalTime.now().format(hourFormat).toString());
					minute = Integer.parseInt(LocalTime.now().format(minuteFormat).toString());
					WTC = new WorldTimeConverter(hour, minute, userTimeZone);
					valid = true;
					zoneName = promptString("What UTC timezone are you converting to?\n"
										  + "(Enter \'x\' to exit)");
					zoneNum = getNumFromString(zoneName);
					if(zoneNum == -1)
						break;
					else if(zoneNum == userTimeZone || zoneNum > 14 || zoneNum < -11)
					{
						System.out.println(red + "That is not an accepted value. Please enter another value.\n\n" + reset);
						valid = false;
					}
					
					zoneName = promptString("What time are you converting from?\n"
										  + "(Enter \'now\' for the current time)");
					if(valid && zoneName.toLowerCase().matches("now"))
					{
						hour = Integer.parseInt(LocalTime.now().format(hourFormat).toString());
						minute = Integer.parseInt(LocalTime.now().format(minuteFormat).toString());
					}
					else if(valid)
					{
						try
						{
							hour = Integer.parseInt(zoneName);
							minute = 0;
							
							if(hour > 24 || hour < 0)
								throw new NumberFormatException();
						}catch(NumberFormatException e)
						{
							try
							{
								delimiter = zoneName.indexOf(":");
								if(valid && delimiter == -1)
									throw new NumberFormatException();
								hour = Integer.parseInt(zoneName.substring(0, delimiter));
								minute = Integer.parseInt(zoneName.substring(delimiter + 1));
								
								if(hour > 24 || hour < 0)
									throw new NumberFormatException();
							}
							catch(NumberFormatException n)
							{
								System.out.println(red + "That is not an accepted value. Please enter another value.\n\n" + reset);
								valid = false;
							}
						}
					}
					
					if(valid)
					{
						WTC.setHour(hour);
						WTC.setMinute(minute);
						WTC.setTimezone(zoneNum);
						System.out.println(WTC.toString());
					}
				}
			}else if(input == 1)
			{
				while(true)
				{
					hour = Integer.parseInt(LocalTime.now().format(hourFormat).toString());
					minute = Integer.parseInt(LocalTime.now().format(minuteFormat).toString());
					WTC = new WorldTimeConverter(hour, minute, userTimeZone);
					valid = true;
					zoneName = promptString("What timezone are you converting to?\n"
										  + "(Enter \'x\' to exit)");
					
					zoneName = zoneName.toUpperCase();
					
					//this is sooooo messy
					if(zoneName.matches("HST|CKT"))
						zoneNum = -10;
					else if(zoneName.matches("AKST|HDT"))
						zoneNum = -9;
					else if(zoneName.matches("PT|PST|AKDT"))
						zoneNum = -8;
					else if(zoneName.matches("PDT|MST"))
						zoneNum = -7;
					else if(zoneName.matches("CST|EAST|GALT|MDT|CT"))
						zoneNum = -6;
					else if(zoneName.matches("CDT|EASST|EST|ET"))
						zoneNum = -5;
					else if(zoneName.matches("AST|EDT|AT"))
						zoneNum = -4;
					else if(zoneName.matches("ADT|BRT"))
						zoneNum = -3;
					else if(zoneName.matches("BRST|UYST"))
						zoneNum = -2;
					else if(zoneName.matches("EGT"))
						zoneNum = -1;
					else if(zoneName.matches("UTC|GMT|WET"))
						zoneNum = 0;
					else if(zoneName.matches("BST|CET|WEST|"))
						zoneNum = 1;
					else if(zoneName.matches("CEST"))
						zoneNum = 2;
					else if(zoneName.matches("AST|EAT|TRT"))
						zoneNum = 3;
					else if(zoneName.matches("ADT|MSD"))
						zoneNum = 4;
					else if(zoneName.matches("AMST|PKT|TFT"))
						zoneNum = 5;
					else if(zoneName.matches("BST"))
						zoneNum = 6;
					else if(zoneName.matches("CXT|ICT|WIB"))
						zoneNum = 7;
					else if(zoneName.matches("WITA|SGT|PHT|MYT|HKT|CST"))
						zoneNum = 8;
					else if(zoneName.matches("JST|KST|WIT"))
						zoneNum = 9;
					else if(zoneName.matches("AEST"))
						zoneNum = 10;
					else if(!valid)
						break;
					else
					{
						valid = false;
						zoneNum = 0;
						System.out.println(red + "That is not an accepted value. Please enter another value.\n\n" + reset);
					}
						
					
					zoneName = promptString("What time are you converting from?\n"
										  + "(Enter \'now\' for the current time)");
					if(valid && zoneName.toLowerCase().matches("now"))
					{
						hour = Integer.parseInt(LocalTime.now().format(hourFormat).toString());
						minute = Integer.parseInt(LocalTime.now().format(minuteFormat).toString());
					}
					else if(valid)
					{
						try
						{
							hour = Integer.parseInt(zoneName);
							minute = 0;
							
							if(hour > 24 || hour < 0)
								throw new NumberFormatException();
						}catch(NumberFormatException e)
						{
							try
							{
								delimiter = zoneName.indexOf(":");
								if(valid && delimiter == -1)
									throw new NumberFormatException();
								hour = Integer.parseInt(zoneName.substring(0, delimiter));
								minute = Integer.parseInt(zoneName.substring(delimiter + 1));
								
								if(hour > 24 || hour < 0)
									throw new NumberFormatException();
							}
							catch(NumberFormatException n)
							{
								System.out.println(red + "That is not an accepted value. Please enter another value.\n\n" + reset);
								valid = false;
							}
						}
					}
					
					if(valid)
					{
						WTC.setHour(hour);
						WTC.setMinute(minute);
						WTC.setTimezone(zoneNum);
						System.out.println(WTC.toString());
					}
				}
			}
			else if(input == 2)
			{
				int timeZone = promptInt("Enter a UTC Time:\n"
						   + "(Default is " + setUserTimeZone() + ")");
				if(timeZone >= -11 && timeZone <= 14)
				{
					userTimeZone = timeZone;

					if(userTimeZone >= 0)
						System.out.println("System Clock has been set to UTC+" + userTimeZone + "\n\n");
					else
						System.out.println("System Clock has been set to UTC" + userTimeZone + "\n\n");
				}
				else
					System.out.println(red + "That is not an accepted value. Please enter another value.\n\n" + reset);
			}
			else
				System.out.println(red + "That is not an accepted value. Please enter another value.\n\n" + reset);
		}
	}

	//Length Converter main method
	public static void lengthConverter()
	{
		String input;
		double inputNum;
		int inputType;
		boolean validation;
		int x;
		boolean assigned;
		LengthConverter LC = new LengthConverter();
		
		while(true)
		{
			valid = true;
			validation = true;
			input = "";
			assigned = false;
			inputNum = 0;
			inputType = 0;
			
			System.out.println(bold + "What measurement are you converting from?\n" + reset
							 + "(Type \'help\' or \'settings\' to view or change measurement abbreviations)" + cyan);
			while(input.isEmpty())
				input = s.nextLine();
			System.out.print(reset);
			if(input.toLowerCase().trim().matches("help|settings|setting"))
			{
				changeMeasurements();
			}
			else if(input.matches("x|X|exit"))
			{
				valid = false;
			}
			else
			{
				if(input.substring(0, 1).matches("."))
					x = 2;
				else
					x = 1;
				while(x <= input.length())
				{
					try
					{
						inputNum = Float.parseFloat(input.substring(0, x));
						assigned = true;
						x++;
					}catch(NumberFormatException e)
					{
						x++;
						break;
					}
				}
				
				if(!assigned)
				{
					break;
				}else try
				{
					Float.parseFloat(input.substring(x - 2));
					valid = false;
					validation = false;
				}catch(NumberFormatException e)
				{

				}
				
				input = input.substring(x - 2).trim();
				
				while(inputType < abbreviations.length)
				{
					if(input.matches(abbreviations[inputType]))
						break;
					inputType++;
				}
				
				if(inputType == abbreviations.length)
				{
					inputType = 0;
					if(input.substring(input.length() - 1).matches("s"))
						input = input.substring(0, input.length() - 1);
					if(input.matches("inche"))
						input = "inch";
					else if(input.matches("feet"))
						input = "foot";
					
					while(inputType < measurements.length)
					{
						if(input.matches(measurements[inputType].toLowerCase()))
							break;
						inputType++;
					}
					if(inputType == measurements.length)
					{
						valid = false;
						validation = false;
					}
				}
				
				if(valid)
				{
					LC.set(inputType, inputNum);
					System.out.println(LC.toString());
				}
			}
			
			if(!valid)
			{
				if(validation)
				{
					System.out.println();
					break;
				}
				System.out.println(red + "That is not a valid input. Please input another value.\n\n" + reset);
			}
		}
	}

	//Sets user preferences
	public static void settings()
	{
		int input;
		
		while(true)
		{
			valid = true;
			input = promptInt("Settings:\n"
							+ "0: Decimal Rounding\n"
							+ "1: Set Time Zone\n"
							+ "2: Change Abbreviations\n"
							+ "3: Exit", 3);
			
			if(!valid)
				break;
			if(input == 0)
			{
				decimalPlaces = promptInt("How many decimal places should numbers be rounded to?\n"
										+ "(Default is 2)");
				System.out.println("Rounding Decimal Places has been set to " + decimalPlaces + "\n\n");
			}
			else if(input == 1)
			{
				int timeZone = promptInt("Enter a UTC Time:\n"
						   + "(Default is " + setUserTimeZone() + ")");
				if(timeZone >= -11 && timeZone <= 14)
				{
					userTimeZone = timeZone;

					if(userTimeZone >= 0)
						System.out.println("System Clock has been set to UTC+" + userTimeZone + "\n\n");
					else
						System.out.println("System Clock has been set to UTC" + userTimeZone + "\n\n");
				}
				else
					System.out.println(red + "That is not an accepted value. Please enter another value.\n\n" + reset);
			}
			else if(input == 2)
			{
				changeMeasurements();
			}
		}
	}
	
	//avoids exceptions from using Scanner.nextInt()
	//prompts user, then returns the next int the user inputs


	public static int promptInt(String prompt)
	{
		String input;
		String query;
		String userInput;
		
		try
		{
			query = prompt.substring(0, prompt.indexOf("\n"));
			userInput = prompt.substring(prompt.indexOf("\n"));
		}
		catch(StringIndexOutOfBoundsException e)
		{
			query = prompt;
			userInput = "";
		}
		
		if(valid)
		{
			System.out.println(bold + query + reset + userInput + cyan);
			input = s.next();
			System.out.println(reset + "\n");
			
			//use to return dollar amounts
//			if(input.contains("$"))
//				input = input.replace("$", "");
			
			try
			{
				return getNumFromString(input);
			}
			catch(NumberFormatException e)
			{
				valid = false;//return invalid input
			}
		}
		return -1;
	}
	
	//promptInt but returns (valid = false) if (input == invalid)

	public static int promptInt(String prompt, int invalid)
	{
		int input;
		input = promptInt(prompt);
		if(input == invalid)
		{
			valid = false;
			return -1;
		}
		return input;
	}

	//avoids exceptions from using Scanner.nextDouble()
	//prompts user, then returns the next double the user inputs

	public static double promptDouble(String prompt)
	{
		String input;
		String query;
		String userInput;
		
		try
		{
			query = prompt.substring(0, prompt.indexOf("\n"));
			userInput = prompt.substring(prompt.indexOf("\n"));
		}
		catch(StringIndexOutOfBoundsException e)
		{
			query = prompt;
			userInput = "";
		}
		
		if(valid)
		{
			System.out.println(bold + query + reset + userInput + cyan);
			input = s.next();
			System.out.println(reset + "\n");
			
			//use to return dollar amounts
//			if(input.contains("$"))
//				input = input.replace("$", "");
			
			try
			{
				return Double.parseDouble(input);
			}
			catch(NumberFormatException e)
			{
				valid = false;//return invalid input
			}
		}
		return -1;
	}
	
	//promptDouble but returns (valid = false) if (input == invalid)

	public static double promptDouble(String prompt, double invalid)
	{
		String input;
		String query;
		String userInput;
		
		try
		{
			query = prompt.substring(0, prompt.indexOf("\n"));
			userInput = prompt.substring(prompt.indexOf("\n"));
		}
		catch(StringIndexOutOfBoundsException e)
		{
			query = prompt;
			userInput = "";
		}
		
		if(valid)
		{
			System.out.println(bold + query + reset + userInput + cyan);
			input = s.next();
			System.out.println(reset + "\n");
			
			//use to return dollar amounts
//			if(input.contains("$"))
//				input = input.replace("$", "");
			
			try
			{
				if(Double.parseDouble(input) == invalid)
					throw new NumberFormatException();
				return Double.parseDouble(input);
			}
			catch(NumberFormatException e)
			{
				valid = false;//return invalid input
			}
		}
		return -1;
	}

	//prompts user, then returns the next String the user inputs

	public static String promptString(String prompt)
	{
		String input;
		String query;
		String userInput;
		
		if(valid)
		{
			try
			{
				query = prompt.substring(0, prompt.indexOf("\n"));
				userInput = prompt.substring(prompt.indexOf("\n"));
			}
			catch(StringIndexOutOfBoundsException e)
			{
				query = prompt;
				userInput = "";
			}
			
			System.out.println(bold + query + reset + userInput + cyan);
			input = s.next();
			System.out.println(reset + "\n");
			
			if(input.matches("x|X|exit|Exit"))
				valid = false;
			return input;
		}
		return "";
	}
	
	//rounds the given number to the given amount of decimals 

	public static String roundNum(double num, int decimals)
	{
		double factor;
		double multiply;
		
		if(num % 1 == 0)
			return Integer.toString((int) num);
		
		//used to change which digit is rounded
		factor = (long) Math.pow(10, decimals);
		multiply = Math.round(num * factor);
		return Double.toString(multiply / factor);
	}

	//returns true if the input is within the given array

	public static boolean dataValidation(int input, int[] validNums)
	{
		for(int i : validNums)
			if(input == i)
				return true;
		return false;
	}

	//returns the user's time zone

	public static int setUserTimeZone()
	{
		DateTimeFormatter hourFormat = DateTimeFormatter.ofPattern("HH");
		//I don't want to talk about this
		int UTCTime = Integer.parseInt(Instant.now().toString().substring(Instant.now().toString().indexOf("T") + 1, Instant.now().toString().indexOf("T") + 3));
		int userTime = Integer.parseInt(LocalTime.now().format(hourFormat).toString());
		return userTime - UTCTime;
	}	

	public static int getNumFromString(String query)
	{
		char[] integers = new char[] {'-', '1', '2', '3', '4', '5', '6', '7', '8', '9', '0'};
		String s = "";
		
		for(char c : query.toCharArray())
			for(int i : integers)
				if(c == i)
					s = s + c;
		if(s.matches(""))
			return -1;
		return Integer.parseInt(s);
	}

	public static void changeMeasurements()
	{
		int input;
		String changed;
		boolean validation;
		
		if(measurements.length == abbreviations.length)
		{
			for(int x = 0; x < measurements.length; x++)
			{
				System.out.println(x + ": " + measurements[x] + ": " + abbreviations[x]);
			}
			
			while(valid)
			{
				input = promptInt("\nEnter a value to change: ('x' to Exit)");
				
				if(!valid || input == -1)
					break;
				if(input >= measurements.length || input < 0)
					System.out.println(red + "That is not a valid input. Please input another value.\n\n" + reset);
				
				changed = promptString("Enter the new value for " + measurements[input] + "\n"
									 + "(Previous value was \'" + abbreviations[input] + "\')");
				validation = true;
				for(int y = 0; y < measurements.length; y++)
					if(changed.matches(abbreviations[y]))
					{
						validation = false;
						System.out.println("\'" + changed + "\' is used in " + measurements[y]);
					}
				if(validation)
				{
					abbreviations[input] = changed;
					System.out.println(measurements[input] + " was changed to " + abbreviations[input]);
				}
			}
		}
		else
		{
			System.out.println("Your arrays aren't the same size.");
		}
	}
}


