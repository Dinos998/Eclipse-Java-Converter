//import converter;

public class TemperatureConverter {

	private double fahrenheit;
	private double celsius;
	private double kelvin;
	
	public TemperatureConverter(int tempType, double userInput)
	{
		if(tempType == 0)
		{
			this.fahrenheit = userInput;
			this.celsius = (userInput - 32) * 5 / 9;
			this.kelvin = ((userInput - 32) * 5 / 9) + 273.15;
		}
		else if(tempType == 1)
		{
			this.fahrenheit = (userInput * 9 / 5) + 32;
			this.celsius = userInput;
			this.kelvin = userInput + 273.15;
		}
		else if(tempType == 2)
		{
			this.fahrenheit = ((userInput - 273.15) * 9 / 5) + 32;
			this.celsius = userInput - 273.15;
			this.kelvin = userInput;
		}
	}
	
	public double getFahrenheit()
	{
		return fahrenheit;
	}
	
	public double getCelsius()
	{
		return celsius;
	}
	
	public double getKelvin()
	{
		return kelvin;
	}
}
