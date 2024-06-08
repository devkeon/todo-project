package oop.paradigm.todo.weather.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import oop.paradigm.todo.weather.dto.responseVO.Category;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WeatherApiResponse {

	private Integer resultCode;
	private String resultMsg;
	private Integer numOfRows;
	private Integer pageNo;
	private Integer totalCount;
	private String dataType;
	private String baseDate;
	private String baseTime;
	private Integer nx;
	private Integer ny;
	private Category category;
	private  Integer obsrValue;

}
