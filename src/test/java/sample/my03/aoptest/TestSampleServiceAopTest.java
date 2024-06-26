package sample.my03.aoptest;



import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.spring5legacy.mypro00.domain.MyBoardVO;
import com.spring5legacy.mypro00.service.MyBoardService;

import sample.my03aop.aop.service.SampleService;


@WebAppConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"file:src/main/webapp/WEB-INF/spring/mybatis-context.xml",
					  "file:src/main/webapp/WEB-INF/spring/appServlet/servlet-context.xml"}) 
public class TestSampleServiceAopTest {

	//MyBoardService가 주입되도록 구현
    
    private SampleService sampleService;

    @Autowired
    public void setSampleService(SampleService sampleService) {
		this.sampleService = sampleService;
    }
    
//    @Test
//    public void testAopClass() {
//    	System.out.println("sampleService: " + sampleService);
//    	System.out.println(sampleService.getClass().getName());
//    }
    
    @Test
    public void testAdd() throws Exception{
    	//AOP 활성화 전
//	    Integer result = sampleService.doAdd("200", "300"); 
//	    Integer result = sampleService.doAdd(200, 300);
//	    System.out.println(result);
    	
    	//AOP 활성화 후
	    Integer result = sampleService.doAdd2("200", "300");
	    System.out.println(result);
    }
	
	
}
