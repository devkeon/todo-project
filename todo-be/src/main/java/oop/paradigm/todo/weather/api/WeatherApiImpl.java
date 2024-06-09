package oop.paradigm.todo.weather.api;

import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.extern.slf4j.Slf4j;
import oop.paradigm.todo.weather.dto.responseVO.WeatherResponse;

@Slf4j
@Component
public class WeatherApiImpl implements WeatherApi{

	private final RestTemplate restTemplate = new RestTemplate();

	@Override
	public WeatherResponse getSeoulWeather(String url, Map<String, ?> requestVariable) {

		String fullUrl = UriComponentsBuilder.fromHttpUrl(url)
			.buildAndExpand(requestVariable)
			.toUriString();

		return restTemplate.getForObject(fullUrl, WeatherResponse.class);
	}

}
