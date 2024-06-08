package oop.paradigm.todo.weather.dto.responseVO;

import java.util.List;

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
public class BodyVO {
	private String dataType;
	private List<ItemVO> items;
}
