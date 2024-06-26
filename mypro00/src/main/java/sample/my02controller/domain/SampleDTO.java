package sample.my02controller.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SampleDTO {
	
	private String name ;
	private int age ;
	
	public SampleDTO() {
		System.out.println("SampleDTO 기본생성자.............");
	}

	public SampleDTO(String name, int age) {
		this.name = name;
		this.age = age;
		
		System.out.println("SampleDTO 모든 필드 초기화 생성자.............");
	}
	
	

}
