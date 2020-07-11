package server.domain.payment;

import java.util.Date;
import java.util.Random;

public class SimPaymentAdapter implements PaymentAdapter{

	@Override
	public boolean pay(double money, String code, String date, String cvc) {
		if(!checkData(money,code,date,cvc))
			return false;
		Random random = new Random();
		if(random.nextInt(1000)==0)
			return false;
		return true;
	}

	@SuppressWarnings("deprecation")
	private boolean checkData(double money, String code, String date, String cvc) {
		if(money<=0) {
			return false;
		}else if (!(code.length()==16)) {
			for (int i =0; i < code.length(); i++) {
				char c = code.charAt(i);
				if (c < '0' || c > '9')
					return false;
			}
		}else if(!(cvc.length()==3)) {
			for (int i =0; i < code.length(); i++) {
				char c = code.charAt(i);
				if (c < '0' || c > '9')
					return false;
			}
//		}else if(new Date("01/"+date).before(new Date(System.currentTimeMillis()))) {
//			return false;
		}
		return true;
	}

}
