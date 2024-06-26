package sample.my03aop.aop.service;

import org.springframework.stereotype.Service;

//import sample.my03aop.aop.LogAdvice;

@Service
public class SampleServiceImpl implements SampleService{

//	@Override
//	public Integer doAdd(String str1, String str2) throws Exception {
//		System.out.println("SampleService의 doAdd 메서드입니다=================");
//		
//		return Integer.parseInt(str1) + Integer.parseInt(str2);
//	}

	///////////////////////////////////////////////////////////////
	
//	private LogAdvice logAdvice ;
//	
//	public SampleServiceImpl(LogAdvice logAdvice) {
//		this.logAdvice = logAdvice ;
//	}
//	
//	@Override
//	public Integer doAdd(Integer str1, Integer str2) throws Exception {
//		logAdvice.myLog();   //로그기록 메서드를 호출
//		return str1 + str2 ;
//	}

	//여기까지는 AOP 기능 사용 않함
	//////////////////////////////////////////////////////////////////////
	//여기서부터는 AOP 기능 사용
	@Override
	public Integer doAdd2(String str1, String str2) throws Exception {
		System.out.println("sample service: doAdd2 에 있는 로그입니다.####");
		return Integer.parseInt(str1) + Integer.parseInt(str2);
	}

	///////////////////////////////////////////////////////////////
	
	@Override
	public Integer doAdd2(Integer str1, Integer str2) throws Exception {
		return str1 + str2 ;
	}
}
