package oop.paradigm.todo.weather.dto.responseVO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Items {
	private List<Item> item;
}
