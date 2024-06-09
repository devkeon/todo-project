package oop.paradigm.todo.weather.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class TemperatureResponse {

	private Double temperature;
	private Double rainAmount;

}
