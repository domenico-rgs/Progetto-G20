package server.domain.payment;

import java.util.Random;

public class SimPaymentAdapter implements PaymentAdapter{

	@Override
	public boolean pay(double money, String code, String pin, String cvc) {
		Random random = new Random();
		if(random.nextInt(1000)==0)
			return false;
		return true;
	}

}
