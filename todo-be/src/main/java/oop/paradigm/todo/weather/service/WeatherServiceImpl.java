package oop.paradigm.todo.weather.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import oop.paradigm.todo.base.Response;
import oop.paradigm.todo.weather.api.WeatherApi;
import oop.paradigm.todo.weather.dto.response.TemperatureResponse;
import oop.paradigm.todo.weather.dto.responseVO.Category;
import oop.paradigm.todo.weather.dto.responseVO.Item;

@Service
@RequiredArgsConstructor
public class WeatherServiceImpl implements WeatherService {

	private final WeatherApi weatherApi;

	@Value("${spring.weather.api.key}")
	private String weatherApiKey;

	@Override
	public Response<TemperatureResponse> getSeoulWeather() {

		String url = "http://apis.data.go.kr/1360000/VilageFcstInfoService_2.0/getUltraSrtNcst?serviceKey={serviceKey}"
			+ "&pageNo={pageNo}&numOfRows={numOfRows}&dataType={dataType}&base_date={base_date}&base_time={base_time}&nx={nx}&ny={ny}";
		Map<String, Object> variable = new HashMap<>();

		LocalDate localDate = LocalDate.now();
		DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		LocalTime localTime = LocalTime.now();
		DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern(("HHmm"));

		variable.put("serviceKey", weatherApiKey);
		variable.put("pageNo", 1);
		variable.put("numOfRows", 1000);
		variable.put("dataType", "JSON");
		variable.put("base_date", localDate.format(dateTimeFormatter));
		variable.put("base_time", localTime.format(timeFormatter));
		variable.put("nx", 60);
		variable.put("ny", 127);

		TemperatureResponse response = new TemperatureResponse();

		List<Item> list = weatherApi.getSeoulWeather(url, variable).getResponse().getBody().getItems().getItem().stream()
			.filter(itemVO -> (itemVO.getCategory().equals(Category.T1H) || itemVO.getCategory().equals(Category.RN1)))
			.toList();

		list.forEach(itemVO -> {
			if (itemVO.getCategory().equals(Category.T1H)){
				response.setTemperature(Double.valueOf(itemVO.getObsrValue()));
			} else if (itemVO.getCategory().equals(Category.RN1) && !itemVO.getObsrValue().equals("0")){
				response.setRainAmount(Double.valueOf(itemVO.getObsrValue()));
			}
		});

		return Response.ok(response);
	}
}
