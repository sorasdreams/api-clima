package sorasdreams.apiclima.model;

import java.util.Arrays;

public class CityGeocodingData {
    private Integer id;
    private String name;
    private float latitude;
    private float longitude;
    private float elevation;
    private String feature_code;
    private String country_code;
    private Integer admin1_id;
    private Integer admin2_id;
    private Integer admin3_id;
    private Integer admin4_id;
    private String admin1;
    private String admin2;
    private String admin3;
    private String admin4;
    private String timezone;
    private Integer population;
    private Integer country_id;
    private String country;
    private String[] postcodes;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public String getFeature_code() {
        return feature_code;
    }

    public void setFeature_code(String feature_code) {
        this.feature_code = feature_code;
    }

    public String getCountry_code() {
        return country_code;
    }

    public void setCountry_code(String country_code) {
        this.country_code = country_code;
    }

    public Integer getAdmin1_id() {
        return admin1_id;
    }

    public void setAdmin1_id(Integer admin1_id) {
        this.admin1_id = admin1_id;
    }

    public Integer getAdmin2_id() {
        return admin2_id;
    }

    public void setAdmin2_id(Integer admin2_id) {
        this.admin2_id = admin2_id;
    }

    public Integer getAdmin3_id() {
        return admin3_id;
    }

    public void setAdmin3_id(Integer admin3_id) {
        this.admin3_id = admin3_id;
    }

    public Integer getAdmin4_id() {
        return admin4_id;
    }

    public void setAdmin4_id(Integer admin4_id) {
        this.admin4_id = admin4_id;
    }

    public String getAdmin1() {
        return admin1;
    }

    public void setAdmin1(String admin1) {
        this.admin1 = admin1;
    }

    public String getAdmin2() {
        return admin2;
    }

    public void setAdmin2(String admin2) {
        this.admin2 = admin2;
    }

    public String getAdmin3() {
        return admin3;
    }

    public void setAdmin3(String admin3) {
        this.admin3 = admin3;
    }

    public String getAdmin4() {
        return admin4;
    }

    public void setAdmin4(String admin4) {
        this.admin4 = admin4;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public Integer getPopulation() {
        return population;
    }

    public void setPopulation(Integer population) {
        this.population = population;
    }

    public Integer getCountry_id() {
        return country_id;
    }

    public void setCountry_id(Integer country_id) {
        this.country_id = country_id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String[] getPostcodes() {
        return postcodes;
    }

    public void setPostcodes(String[] postcodes) {
        this.postcodes = postcodes;
    }

    @Override
    public String toString() {
        return "CityGeocodingData{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", elevation=" + elevation +
                ", feature_code='" + feature_code + '\'' +
                ", country_code='" + country_code + '\'' +
                ", admin1_id=" + admin1_id +
                ", admin2_id=" + admin2_id +
                ", admin3_id=" + admin3_id +
                ", admin4_id=" + admin4_id +
                ", admin1='" + admin1 + '\'' +
                ", admin2='" + admin2 + '\'' +
                ", admin3='" + admin3 + '\'' +
                ", admin4='" + admin4 + '\'' +
                ", timezone='" + timezone + '\'' +
                ", population=" + population +
                ", country_id=" + country_id +
                ", country='" + country + '\'' +
                ", postcodes=" + Arrays.toString(postcodes) +
                '}';
    }
}
