public class LengthConverter
{
	double[] measurements = new double[converter.measurements.length];
	String[] names = converter.measurements;
	String[] abbreviations = converter.abbreviations;
	
	public LengthConverter()
	{
		for(int x = 0; x < measurements.length; x++)
			measurements[x] = 0;
	}
	
	public LengthConverter(int inputType, double input)
	{
		set(inputType, input);
	}
	
	public void set(int inputType, double input)
	{
		measurements[inputType] = input;
		if(inputType == 0)
			measurements[2] = input / 1000;
		else if(inputType == 1)
			measurements[2] = input / 100;
		else if(inputType == 2)
			measurements[2] = input;
		else if(inputType == 3)
			measurements[2] = input * 1000;
		else if(inputType == 4)
			measurements[2] = input / 39.3701;
		else if(inputType == 5)
			measurements[2] = input * 12 / 39.3701;
		else if(inputType == 6)
			measurements[2] = input * 36 / 39.3701;
		else if(inputType == 7)
			measurements[2] = input * 63360 / 39.3701;
		
		measurements[0] = measurements[2] * 1000;
		measurements[1] = measurements[2] * 100;
		measurements[2] = measurements[2];
		measurements[3] = measurements[2] / 1000;
		measurements[4] = measurements[2] * 39.3701;
		measurements[5] = measurements[2] * 39.3701 / 12;
		measurements[6] = measurements[2] * 39.3701 / 36;
		measurements[7] = measurements[2] * 39.3701 / 63360;
	}
	
	public double getMilimetre()
	{
		return measurements[0];
	}
	
	public double getCentimetre()
	{
		return measurements[1];
	}
	
	public double getMetre()
	{
		return measurements[2];
	}
	
	public double getKilometre()
	{
		return measurements[3];
	}
	
	public double getInch()
	{
		return measurements[4];
	}
	
	public double getFeet()
	{
		return measurements[5];
	}
	
	public double getYard()
	{
		return measurements[6];
	}
	
	public double getMile()
	{
		return measurements[7];
	}
	
	@Override
	public String toString()
	{
		String s = "";
		for(int x = 0; x < names.length; x++)
			s += names[x] + ": " + converter.roundNum(measurements[x], converter.decimalPlaces) + " " + abbreviations[x] + "\n";
		return s;
	}
}
