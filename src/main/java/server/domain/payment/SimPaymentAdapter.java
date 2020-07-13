package server.domain.payment;

import java.util.Date;
import java.util.Random;

/**This class creates a Payment simulator which can simulate a payment. */

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

	/**
	 * this method can check all data inserted into the payment simulator
	 * @param money money for the payment
	 * @param code cod for a discount
	 * @param date card's deadline
	 * @param cvc card's cvc
	 * @return
	 */
	private boolean checkData(double money, String code, String date, String cvc) {
		if(money<=0)
			return false;
		else if (!(code.length()==16))
			for (int i =0; i < code.length(); i++) {
				char c = code.charAt(i);
				if (c < '0' || c > '9')
					return false;
			}
		else if(!(cvc.length()==3))
			for (int i =0; i < code.length(); i++) {
				char c = code.charAt(i);
				if (c < '0' || c > '9')
					return false;
			}
		else if(new Date("01/"+date).before(new Date(System.currentTimeMillis())))
			return false;
		return true;
	}

}
