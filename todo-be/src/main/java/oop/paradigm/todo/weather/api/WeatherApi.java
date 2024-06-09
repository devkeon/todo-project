package oop.paradigm.todo.weather.api;

import java.util.Map;

import oop.paradigm.todo.weather.dto.responseVO.WeatherResponse;

public interface WeatherApi {

	WeatherResponse getSeoulWeather(String url, Map<String, ?> requestVariable);

}
