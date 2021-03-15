package headfirst.designpatterns.observer.weather;

public class WeatherStation {

	public static void main(String[] args) {
		WeatherData weatherData = new WeatherData();

		CurrentConditionsDisplay currentDisplay = 
				new CurrentConditionsDisplay(weatherData);
		StatisticsDisplay statisticsDisplay = new StatisticsDisplay(weatherData);
		ForecastDisplay forecastDisplay = new ForecastDisplay(weatherData);

		try {
			weatherData.setMeasurements(80, 65, 30.4f);
			Thread.sleep(500);
			weatherData.setMeasurements(82, 70, 29.2f);
			Thread.sleep(500);
			weatherData.setMeasurements(78, 90, 29.2f);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
