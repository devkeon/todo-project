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
public class HeaderVO {
	private String resultCode;
	private String resultMsg;
}
