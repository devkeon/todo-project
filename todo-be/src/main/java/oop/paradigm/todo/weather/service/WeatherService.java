package oop.paradigm.todo.weather.service;

import oop.paradigm.todo.base.Response;
import oop.paradigm.todo.weather.dto.response.TemperatureResponse;

public interface WeatherService {

	Response<TemperatureResponse> getSeoulWeather();

}
