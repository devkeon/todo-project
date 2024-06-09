package oop.paradigm.todo.weather.api;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import oop.paradigm.todo.weather.dto.WeatherApiResponse;

@Component
public class WeatherApi {

	private final RestTemplate restTemplate = new RestTemplate();

	public WeatherApiResponse getSeoulWeather(String url, Map<String, ?> requestVariable) {

		return restTemplate.getForObject(url, WeatherApiResponse.class, requestVariable);
	}

}
