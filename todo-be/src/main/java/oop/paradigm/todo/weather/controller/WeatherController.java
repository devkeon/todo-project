package oop.paradigm.todo.weather.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import oop.paradigm.todo.base.Response;
import oop.paradigm.todo.weather.dto.response.TemperatureResponse;
import oop.paradigm.todo.weather.service.WeatherService;

@RestController
@RequestMapping("/api/weather")
@RequiredArgsConstructor
public class WeatherController {

	private final WeatherService weatherService;

	@GetMapping
	public Response<TemperatureResponse> getWeatherInformation() {

		return weatherService.getSeoulWeather();
	}

}
