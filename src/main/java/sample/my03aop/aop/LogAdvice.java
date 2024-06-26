package sample.my03aop.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

//부가기능(횡단 관심사->관심사): AOP에서는 Advice라고 함, 
//관점지향 프로그램, 부가기능이 프레임워크에 의해서 자동으로 실행되도록 구성함
//AOP입장에서는 관심사를 주된 관점대상으로 인식, -->자동으로 실행될 수 있도록 구성되면, AOP가 실행시켜줌
//Aspect(Advice + joinpoint)

@Aspect
@Component
public class LogAdvice {

	public void myLog() {
		System.out.println("LogAdvice의 로그를 남기는 myLog()메서드 실행됨=========");
		System.out.println("SampleService의 doAdd 메서드입니다");
		
	}
	//////여기까지 AOP 사용안함
	
	
	@Before(value="execution(* sample.my03aop.aop.service.SampleServiceImpl.doAdd2(String, String))")
//	@Before(value="execution(* sample.my03aop.aop.service.SampleService*.*(..))")
	public void logBefore1() {
		System.out.println("LogjAdvice(Before-부가기능 메서드 실행)=============");
		System.out.println("SampleService의 doAdd 메서드입니다");
	}
	
	
	/*   
	   //위의 @Before(value="execution(* sample.less03.service.SampleService*.*(..))")  설명
	   - @Before: 핵심기능 메서드 실행 전에 실행됨: join-point라고 함
	   
	   - 괄호 안의 첫 번째 * : 모든 접근제한자를 의미 
	   - sample.chap18.service.SampleService* : 패키지이름.클래스접두어가 포함된 모든 클래스를 의미
	   - 첫번째 점 다음의 * : 모든 메서드  
	   - (..) : 메서드의 매개변수 갯수, 타입 상관없음
	*/
}
