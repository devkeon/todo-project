package oop.paradigm.todo.weather.dto.responseVO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Item {

	private String baseDate;
	private String baseTime;
	private Category category;
	private Integer nx;
	private Integer ny;
	private String obsrValue;
}
