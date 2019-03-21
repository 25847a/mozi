package jian;

public class Client {

	public static void main(String[] args) {
		test();
		System.out.println(Service.a);
	}
	
	
	public static void test(){
		Service.a="D";
	}
}
