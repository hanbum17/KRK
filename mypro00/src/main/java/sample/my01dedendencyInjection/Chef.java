package sample.my01dedendencyInjection;

//import org.springframework.stereotype.Component;

//@Component
public class Chef {
	
	private String name ;
	
	public Chef() {
		System.out.println("Chef 기본 생성자 입니다.==========");
	}
	
//	public Chef(String name) {
//		System.out.println("Chef 모든 필드 초기화 생성자 입니다.==========");
//		this.name = name ;
//	}


	public void makingRamen() {
		System.out.println("라면을 요리합니다..." + name);
	}

}
