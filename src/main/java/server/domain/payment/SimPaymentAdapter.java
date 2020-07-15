package server.domain.payment;

import java.time.LocalDate;
import java.util.Random;

import server.domain.exception.PaymentException;

/**This class creates a Payment simulator which can simulate a payment. */
public class SimPaymentAdapter implements PaymentAdapter{
	SimPaymentAdapter() {}

	@Override
	public boolean pay(double money, String code, String date, String cvc) throws PaymentException {
		checkCard(code,date,cvc);
		Random random = new Random();
		if(random.nextInt(1000)==0) //randomly simulates the failure of the operation
			return false;
		return true;
	}

	/*
	 * This method can check all data inserted into the payment simulator
	 */
	private boolean checkCard(String code, String date, String cvc) throws PaymentException {
		String[] value = date.split("/");
		LocalDate cardDate = LocalDate.of(Integer.parseInt(value[1]), Integer.parseInt(value[0]), 01);

		//check that the code is only numeric and 16 digits
		if ((code.length()==16)) {
			if(!isNumberOnly(code))
				throw new PaymentException("Card code must be numeric only");
		}else
			throw new PaymentException("The data entered is not valid");

		//check that the cvc is only numeric and 3 digits
		if((cvc.length()==3)) {
			if(!isNumberOnly(code))
				throw new PaymentException("CVC must be numeric only");
		}else
			throw new PaymentException("The data entered is not valid");

		//check that the card has not already expired
		if(cardDate.isBefore(LocalDate.now()))
			throw new PaymentException("The date entered is not valid");
		return true;
	}

	private boolean isNumberOnly(String code) throws PaymentException {
		for (int i =0; i < code.length(); i++) {
			char c = code.charAt(i);
			if (c < '0' || c > '9')
				return false;
		}
		return true;
	}
}

