public class WorldTimeConverter
{
	int hour;
	int minute;
	int timezone;
	
	public WorldTimeConverter()
	{
		hour = 0;
		minute = 0;
		timezone = 0;
		verify();
	}
	
	public WorldTimeConverter(int hour, int minute, int timezone)
	{
		this.hour = hour;
		this.minute = minute;
		this.timezone = timezone;
		verify();
	}
	
	public void verify()
	{
		while(hour > 24)
			hour = hour - 24;
		while(minute >= 60)
			minute = minute - 60;
		while(timezone > 14 || timezone < -11)
			System.out.println("That is not a valid timezone.");
	}
	
	public int setHour(int hour)
	{
		this.hour = hour;
		verify();
		return hour;
	}
	
	public int getHour()
	{
		verify();
		return hour;
	}
	
	public int setMinute(int minute)
	{
		this.minute = minute;
		verify();
		return minute;
	}
	
	public int getMinute()
	{
		verify();
		return minute;
	}
	
	public int setTimezone(int timezone)
	{
		hour = hour - this.timezone + timezone;
		this.timezone = timezone;
		verify();
		return timezone;
	}
	
	public int getTimezone()
	{
		return timezone;
	}
	
	@Override
	public String toString()
	{
		String Timezone;
		String Hour = String.valueOf(hour);
		String Minute = String.valueOf(minute);
		
		if(timezone >= 0)
			Timezone = "UTC+" + timezone;
		else
			Timezone = "UTC" + timezone;
		
		if(hour < 10)
			Hour = "0" + hour;
		
		if(minute < 10)
			Minute = "0" + minute;
		return "It is " + Hour + ":" + Minute + " in " + Timezone + ".\n\n";
	}
}
