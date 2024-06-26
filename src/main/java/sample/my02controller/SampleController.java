package sample.my02controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;

import javax.xml.ws.spi.http.HttpHandler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import sample.my02controller.domain.SampleDTO;
import sample.my02controller.domain.SampleDTOList;
import sample.my02controller.domain.ToDoDTO;


@Controller	//@RestController
@RequestMapping(value = {"/sample/*"} ) //method = {RequestMethod.GET, RequestMethod.POST}
public class SampleController {

//	@RequestMapping(value = "/*")
//	@RequestMapping(value = "/1")
	@GetMapping("/1")
	public void basic1() {
	System.out.println("basic1()==================");
	}
	
	//반환타입이 void로 설정되면, URL을 기반해서 화면을 생성하는 JSP파일을 호출합니다.
	//접두어: /WEB-INF/views/
	//접미어: .jsp
	//요청: /sample/2
	//파일 [/WEB-INF/views/sample/2.jsp] 을(를) 찾을 수 없습니다.
	
//	@RequestMapping(value = "/2")
	@GetMapping("/2")
	public void basic2() {
		System.out.println("basic2()==================");
	}
	//반환타입이 void로 설정되면, URL을 기반해서 화면을 생성하는 JSP파일을 호출합니다.
	//접두어: /WEB-INF/views/
	//요청: /sample/1
	//파일 [/WEB-INF/views/sample/1.jsp]을(를) 찾을 수 없습니다.
	
//	@RequestMapping(value = "/3")
	@GetMapping("/3")
	public String basic3() {
		System.out.println("basic3()=================="); 
		return "mysample/myresult";
	}
	//컨트롤러의 메서드 반환타입이 String으로 설정되면, 반환 문자열을 기반해서 화면을 생성하는 JSP파일을 호출합니다.
	//요청: /sample/2
	//파일 파일 [/WEB-INF/views/mysample/myresult.jsp]을(를) 찾을 수 없습니다.
	//접두어: /WEB-INF/views/
	//접미어: jsp
	
	//POST 방식 요청 처리
	@PostMapping("/basicPostOnly")
		public void basicPost() {
		System.out.println("basicPost()==================");
	}
	
	//웹브라우저: http://localhost:8080/mypro00/sample/basicPostOnly

	////////////////////////////////////////////////////////////////////////////////////////
	
	@GetMapping(value = {"/ex01"})
	public void myEx01(SampleDTO sampleDTO) {
		System.out.println("name: " + sampleDTO.getName());
		System.out.println("age: " + sampleDTO.getAge());
		System.out.println("sampleDTO: " + sampleDTO);
	}	//웹브라우저: http://localhost:8080/mypro00/sample/ex01?name=홍길동&age=24 로 요청
	
	//메서드의 매개변수가 DTO/VO 타입인 경우, 매서드 실행 시 매개변수에 객체가 자동으로 생성되어 주입됨
	//사용자 브라우저에서 보낸 값들의 파라미터 이름과 매개변수 객체의 필드이름과 동일하면,
	//매개변수에 대입된 객체의 필드에 값들이 자동으로 저장됨
	
	@GetMapping(value = {"/ex02"})
	public void myEx02(String name, int age) {
		System.out.println("name: " + name);
		System.out.println("age: " + age);
	}	//웹브라우저: http://localhost:8080/mypro00/sample/ex02?name=홍길동&age=24 로 요청
	
//	public void myEx022(@RequestParam("name") String name, 
//						@RequestParam("age") Integer age) {
	
	@GetMapping(value = {"/ex022"})
	public void myEx022(@RequestParam("name") String name1, 
						@RequestParam("age") Integer age1) {
		System.out.println("name: " + name1);
		System.out.println("age: " + age1);
	}	//웹브라우저: http://localhost:8080/mypro00/sample/ex022?name=홍길동&age=24 로 요청
	
	@GetMapping(value = {"/ex02List"})
	public void myEx02List(ArrayList<String> myIds) {
		System.out.println("myIds: " + myIds);
	}	//웹브라우저: http://localhost:8080/mypro00/sample/ex02List?myIds=홍길동&myIds=슈퍼맨&myIds=베트맨 요청
	
	@GetMapping(value = {"/ex022List"})
	public void myEx022List(@RequestParam("myIds") ArrayList<String> myIds) {
		System.out.println("myIds: " + myIds);
	} //웹브라우저: http://localhost:8080/mypro00/sample/ex022List?myIds=홍길동&myIds=슈퍼맨&myIds=베트맨 요청
	
	@GetMapping(value = {"/ex02Array"})
	public void myEx02Array(String[] myIds) {
		
		System.out.println(Arrays.toString(myIds));
	} //웹브라우저: http://localhost:8080/mypro00/sample/ex02Array?myIds=홍길동&myIds=슈퍼맨&myIds=베트맨 요청
	
	@GetMapping(value = {"/ex02DTOList"})
	public void myEx02DTOList(SampleDTOList list) {
		
		System.out.println("sampleDTO\n" + list);
	} //웹브라우저: http://localhost:8080/mypro00/sample/ex02DTOList?list[0].name=홍길동&list[1].name=슈퍼맨&list[2].age=24 요청
    	//웹브라우저: http://localhost:8080/mypro00/sample/ex02DTOList?list%5B0%5D.name=홍길동&list%5B1%5D.name=슈퍼맨&list%5B2%5D.age=24 요청
		//[ : %5B, ] : %5D
	
	
	@GetMapping(value = {"/ex03"})
	public void myEx03(ToDoDTO todo) {
		
		System.out.println("todo: " + todo );
	}//웹브라우저: http://localhost:8080/mypro00/sample/ex03?title=홍길동&dueDate=2023-05-28 요청
	
	//////////////////////////////////////
	
	@GetMapping(value = {"/ex04"})
	public String myEx04(SampleDTO dto, String page) {
		
		System.out.println("dto: " + dto );
		System.out.println("page: " + page );
		
		return "/sample/ex04";
	} //웹브라우저: http://localhost:8080/mypro00/sample/ex04?name=홍길동&age=24&page=1 요청
	   //ex04.jsp에 표시된 내용을 확인하면, SampleDTO에 저장된 값들은 JSP로 전달되었지만,
	   //기본타입, Wrapper, String 타입의 값은 JSP로 전달되지 못합니다.
	   //기본타입, Wrapper, String 타입의 값은 JSP로 전달하려면 ModelAtttribute 어노테이션을 이용합니다.
	
	@GetMapping(value = {"/ex05"})
	public String myEx05(SampleDTO dto, @ModelAttribute("page") String page1) {
		
		System.out.println("dto: " + dto );
		System.out.println("page: " + page1 );
		
		return "/sample/ex04";
	} //웹브라우저: http://localhost:8080/mypro00/sample/ex05?name=홍길동&age=24&page=1
	
	
	@GetMapping(value = {"/ex055"})
	public String myEx055(SampleDTO dto,  String page1, Model model) {
		
		System.out.println("dto: " + dto );
		System.out.println("page: " + page1 );
		System.out.println(model.toString());
		model.addAttribute("page", page1);
		
		return "/sample/ex04";
	} //웹브라우저: http://localhost:8080/mypro00/sample/ex055?name=홍길동&age=24&page1=5
	
	@GetMapping(value = {"/ex055List"})
	public String myEx0555List(@RequestParam("myIds") ArrayList<String> myIds, SampleDTO dto, Model model) {
		System.out.println("myIds:" + myIds);
		System.out.println("dto:" + dto);
		System.out.println("model:" + model);
		
		return "/sample/ex04";
	} //웹브라우저: http://localhost:8080/mypro00/sample/ex055List?myIds=홍길동&myIds=슈퍼맨&myIds=베트맨&name=이이순신&age=999 요청
	
	
	// 잘못된 사용 예
	@GetMapping(value = {"/ex0555List"})
	public String myEx05555List(@ModelAttribute("myIds") ArrayList<String> myIds, SampleDTO dto, Model model) {
		System.out.println("myIds:" + myIds);
		System.out.println("dto:" + dto);
		System.out.println("model:" + model);
		
		return "/sample/ex04";
	} //웹브라우저: http://localhost:8080/mypro00/sample/ex0555List?myIds=홍길동&myIds=슈퍼맨&myIds=베트맨&name=이이순신&age=999 요청
	
	//올바른 사용 예
	@GetMapping(value = {"/ex0555ListOk"})
	public String myEx05555ListOk(@RequestParam("myIds") ArrayList<String> myIds, SampleDTO dto, Model model, String weight) {
		System.out.println("myIds:" + myIds);
		System.out.println("dto:" + dto);
		
		
		model.addAttribute("myIds", myIds);
//		model.addAttribute("dto", dto);
		model.addAttribute("sampleDto", dto);
		model.addAttribute("weight", weight);
		
		System.out.println("model:" + model);
		
		return "/sample/ex04";
	} //웹브라우저: http://localhost:8080/mypro00/sample/ex0555ListOk?myIds=홍길동&myIds=슈퍼맨&myIds=베트맨&name=이이순신&age=999&weight=67.5 요청
	
	
	////////////////////////////////////////////////////////////////
	//스프링 컨트롤러의 추가적인 기능
	//컨트롤러 메서드의 반환타입이 String 이고, 반환문자열이 redirect:로 시작된 경우 실습
	
	@GetMapping("/ex06")
	public String myEx06(String name, int age, int page) {
		System.out.println("name: " + name);
		System.out.println("age: " + age); 
		System.out.println("page: "+ page);
		
		try {
			name = URLEncoder.encode(name, "utf-8");
			System.out.println("URLEncoder.encode 처리 후: " + name);
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		
		//[주의]아래에서 redirect: 다음에 컨텍스트이름을 포함하면 않됨
		return "redirect:/ex04.jsp?name="+name+"&age="+age+"&page="+page;
	} //웹브라우저: http://localhost:8080/mypro00/sample/ex06?name=홍길동&age=50&page=2 요청
	//위의 메서드의 jsp를 직접 URL에서 호출 하므로 안 좋음
	//jsp는 컨트롤러를 통해서 호출되도록 변경해 줍니다(아래 실습)
	
	@GetMapping("/ex066")
	public String myEx066(String name, int age, int page) {
		System.out.println("name: " + name);
		System.out.println("age: " + age); 
		System.out.println("page: "+ page);
		
		try {
			name = URLEncoder.encode(name, "utf-8");
			System.out.println("URLEncoder.encode 처리 후: " + name);
		} catch (UnsupportedEncodingException e) {
			
			e.printStackTrace();
		}
		
		//[주의]아래에서 redirect: 다음에 컨텍스트이름을 포함하면 않됨
		return "redirect:/sample/ex066Result"+name+"&age="+age+"&page="+page;
	}  //웹브라우저: http://localhost:8080/mypro00/sample/ex066?name=홍길동&age=50&page=2
	
	@GetMapping("/ex066Result")
	public String myEx066R(String name, Integer age, Integer page, Model model) {
		
		model.addAttribute("name", name);
		model.addAttribute("age", age);
		model.addAttribute("page", page);
		
		return "sample/ex044";
				
	}
	
	//스프링 컨트롤러의 추가적인 기능
	//2. 컨트롤러 메서드의 반환타입이 String 이고, 반환문자열이 redirect:로 시작된 경우 실습
	//	메서드의 매개변수 타입으로 RedirectAttributes를 같이 사용
	
	@GetMapping("/ex0666")
	public String myEx0666(String name, int age, int page, RedirectAttributes redirectAttr) {
		System.out.println("name: " + name);
		System.out.println("age: " + age); 
		System.out.println("page: "+ page);
		
		redirectAttr.addAttribute("name", name);
		redirectAttr.addAttribute("age", age);
		redirectAttr.addAttribute("page", page);
		
		
		//[주의]아래에서 redirect: 다음에 컨텍스트이름을 포함하면 않됨
		return "redirect:/sample/ex066Result";
	}//웹브라우저: http://localhost:8080/mypro00/sample/ex0666?name=홍길동&age=50&page=2
	
	///////////////////////////////////////////////////
	//메서드 반환타입이 DTO, VO 인 경우(void/String 이 아닌 경우)
	
	@GetMapping("/ex07")
	public SampleDTO ex07(SampleDTO myDto) {
		System.out.println("name: " + myDto.getName());
		System.out.println("age: " + myDto.getAge());
		
		SampleDTO yourDto = new SampleDTO() ;
		yourDto.setName("스파이더맨");
		yourDto.setAge(100000);
		
		return yourDto; //JSP로 전달됨
	}
	   // 웹브라우저에서 http://localhost:8080/mypro00/sample/ex07?name=이순신&age=24
	   // URL을 기반으로 JSP 파일을 호출합니다.
	
	//////////////////////////////////////////////
	
	//@ResponseBody: REST API 어노테이션, JSP 파일 호출이 없음(오류는 발생되지만 브라우저에서 갓은 전달됨)
	@GetMapping("/ex08")
	@ResponseBody
	public SampleDTO myEx08(SampleDTO myDto) { 
		System.out.println("name: " + myDto.getName()); 
		System.out.println("age: " + myDto.getAge()); 
		
		return myDto;
	} //웹브라우저에서 http://localhost:8080/mypro00/sample/ex08?name=이순신&age=24
	
	
	//반환타입이 ResponseEntity<E> 인 경우, JSP 호출을 않함
	@GetMapping("/ex09")
	public ResponseEntity<String> myEx09 (SampleDTO myDto) {
		System.out.println("ex09=========================");
		HttpHeaders httpHeaders = new HttpHeaders() ;
//		httpHeaders.add("Content-Type", "text/plain; charset=utf-8");
		httpHeaders.add("Content-Type", "application/json; charset=utf-8");
									/* {"name": "홍길동", "age": 24} */
		String myJsonStr = "{\"name\": \"" +myDto.getName()+ "\", \"age\": "+ myDto.getAge()+"}";
		
		return new ResponseEntity<String>(myJsonStr, httpHeaders, HttpStatus.OK);
//		return new ResponseEntity<String>(myJsonStr, HttpStatus.OK);
	}	
	//웹브라우저에서 http://localhost:8080/mypro00/sample/ex09?name=이순신&age=24
	
	
	
	
	
	
}
