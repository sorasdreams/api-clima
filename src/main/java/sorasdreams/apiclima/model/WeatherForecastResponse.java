package sorasdreams.apiclima.model;

public class WeatherForecastResponse {
    private float latitude;
    private float longitude;
    private float elevation;
    private float generationtime_ms;
    private Integer utc_offset_seconds;
    private String timezone;
    private String timezone_abbreviation;
    Hourly HourlyObject;
    Hourly_units Hourly_unitsObject;
    Current_weather Current_weatherObject;

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getElevation() {
        return elevation;
    }

    public void setElevation(float elevation) {
        this.elevation = elevation;
    }

    public float getGenerationtime_ms() {
        return generationtime_ms;
    }

    public void setGenerationtime_ms(float generationtime_ms) {
        this.generationtime_ms = generationtime_ms;
    }

    public Integer getUtc_offset_seconds() {
        return utc_offset_seconds;
    }

    public void setUtc_offset_seconds(Integer utc_offset_seconds) {
        this.utc_offset_seconds = utc_offset_seconds;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getTimezone_abbreviation() {
        return timezone_abbreviation;
    }

    public void setTimezone_abbreviation(String timezone_abbreviation) {
        this.timezone_abbreviation = timezone_abbreviation;
    }

    public Hourly getHourlyObject() {
        return HourlyObject;
    }

    public void setHourlyObject(Hourly hourlyObject) {
        HourlyObject = hourlyObject;
    }

    public Hourly_units getHourly_unitsObject() {
        return Hourly_unitsObject;
    }

    public void setHourly_unitsObject(Hourly_units hourly_unitsObject) {
        Hourly_unitsObject = hourly_unitsObject;
    }

    public Current_weather getCurrent_weatherObject() {
        return Current_weatherObject;
    }

    public void setCurrent_weatherObject(Current_weather current_weatherObject) {
        Current_weatherObject = current_weatherObject;
    }

    public class Current_weather {
        private String time;
        private float temperature;
        private float weathercode;
        private float windspeed;
        private float winddirection;

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public float getTemperature() {
            return temperature;
        }

        public void setTemperature(float temperature) {
            this.temperature = temperature;
        }

        public float getWeathercode() {
            return weathercode;
        }

        public void setWeathercode(float weathercode) {
            this.weathercode = weathercode;
        }

        public float getWindspeed() {
            return windspeed;
        }

        public void setWindspeed(float windspeed) {
            this.windspeed = windspeed;
        }

        public float getWinddirection() {
            return winddirection;
        }

        public void setWinddirection(float winddirection) {
            this.winddirection = winddirection;
        }
    }

    public class Hourly_units {
        private String temperature_2m;

        public String getTemperature_2m() {
            return temperature_2m;
        }

        public void setTemperature_2m(String temperature_2m) {
            this.temperature_2m = temperature_2m;
        }
    }

    public class Hourly {
        String[] time;
        float[] temperature_2m;

        public String[] getTime() {
            return time;
        }

        public void setTime(String[] time) {
            this.time = time;
        }

        public float[] getTemperature_2m() {
            return temperature_2m;
        }

        public void setTemperature_2m(float[] temperature_2m) {
            this.temperature_2m = temperature_2m;
        }
    }
}
