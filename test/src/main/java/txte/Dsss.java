package txte;

import java.util.Random;

public class Dsss {

	public static void main(String[] args) {
		int heartrdao = 78;
		Random r =  new Random();
		int hear = r.nextInt((heartrdao/10)*2)+(heartrdao-heartrdao/10)+1;
		System.out.println(hear);
		System.out.println(r.nextInt((heartrdao/10)*2));
	}

}
