package sample.my02controller.domain;

import lombok.Setter;
import lombok.ToString;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ToDoDTO {

	private String title ;
	
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date dueDate ;
	
	
}
