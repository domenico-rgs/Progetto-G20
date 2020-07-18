package server.services.payment;

import java.time.LocalDate;
import java.util.Random;

import server.exception.PaymentException;

/**This class creates a Payment simulator which can simulate a payment. */
public class SimPaymentAdapter implements PaymentAdapter{
	public SimPaymentAdapter() {}

	@Override
	public boolean pay(double money, String code, String date, String cvc) throws PaymentException {
		checkCard(code,date,cvc);
		Random random = new Random();
		if(random.nextInt(1000)==0) //randomly simulates the failure of the operation
			return false;
		return true;
	}

	/**
	 * This method can check all data inserted into the payment simulator
	 */
	private boolean checkCard(String code, String date, String cvc) throws PaymentException {
		String[] value = date.split("/");
		LocalDate cardDate = LocalDate.of(Integer.parseInt(value[1]), Integer.parseInt(value[0]), 01);

		//check that the code and cvc are only numeric and 16 and 3 digits respectively
		if (code.length()==16 && cvc.length()==3) {
			if(!isNumberOnly(code) || !isNumberOnly(cvc))
				throw new PaymentException("Card code or cvc must be numeric only");
		}else
			throw new PaymentException("The data entered is not valid");

		//check that the card has not already expired
		if(cardDate.isBefore(LocalDate.now()))
			throw new PaymentException("The date entered is not valid");
		return true;
	}

	/**
	 * Check that there are only numbers in a string (code/cvc)
	 * @param code card code or cvc
	 * @return true if is number only
	 */
	private boolean isNumberOnly(String code) {
		for (int i =0; i < code.length(); i++) {
			char c = code.charAt(i);
			if (c < '0' || c > '9')
				return false;
		}
		return true;
	}
}

