package sample.mydi;

import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;

//@Component
public class Foodshop {
  //setter를 이용한 의존성 주입
	private Chef chef ;
	
	public Foodshop() {
		System.out.println("FoodShop 기본 생성자=========");
	}

	@Autowired
	public void setChef(Chef chef) {
		this.chef= chef;
		sellRamenFood();
	}

  //생성자를 이용한 의존성 주입	
//	private Chef chef ;
//	
////	public Foodshop() {
////		System.out.println("FoodShop 기본 생성자=========");
////	}
//	
//	@Autowired
//	public FoodShop(Chef chef) {
//		System.out.println("FoodShop 모든 필드 초기화 생성자============");
//		this.chef =chef;
//		sellRamenFood();
//	}

//	//필드 주입
//	@Autowired
//	private Chef chef ;
//	
//	public Foodshop() {
//		System.out.println("FoodShop 기본 생성자=========");
//		sellRamenFood();
//	}
	
	public void sellRamenFood() {
		
//		Chef chef = new Chef();
		
		chef.makingRamen();
		System.out.println("라면판매......");
	}
	
}
