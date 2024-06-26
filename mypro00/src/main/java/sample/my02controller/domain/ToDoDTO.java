package sample.my02controller.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ToDoDTO {
	
	private String title ;
	
	@DateTimeFormat(pattern = "yyyy.MM.dd")
	private Date dueDate ;

}
