package org.example.handle.weather;
public class WeatherData {
    private double temperature;
    private double humidity;
    private double pressure;
    private String weather;

    @Override
    public String toString() {
        return "```\n"+ "Погода в Москве: \n\n" + "Температура: " + getTemperatureInCelsius() + "°C\n" +
                "Влажность: " + humidity + "%\n" +
                "Давление: " + pressure + "hPa\n" +
                "Погода: " + weather + "```\n";
    }
    
    public int getTemperatureInCelsius() {
        return (int) (temperature - 273.15);
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }

    public void setPressure(double pressure) {
        this.pressure = pressure;
    }

    public void setWeather(String weather) {
        this.weather = weather;
    }

}
