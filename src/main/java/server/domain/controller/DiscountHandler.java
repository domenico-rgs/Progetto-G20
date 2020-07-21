package server.domain.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import server.domain.payment.discount.PricingStrategyFactory;
import server.domain.payment.discount.TicketPricingStrategy;
import server.exception.DiscountException;
import server.exception.SearchException;
import server.services.persistence.PersistenceFacade;

public class DiscountHandler {
	private static DiscountHandler instance = null;

	private DiscountHandler() {}

	/**
	 * this method creates a discount code
	 * @param code discount's code
	 * @param percent discount's percent
	 * @throws DiscountException 
	 */
	synchronized public void createDiscountCode(String code, double percent) throws SQLException, DiscountException  {
		if(percent < 0 || percent > 100)
			throw new DiscountException("Accepted values between 0 and 100");
		PricingStrategyFactory.getInstance().createDiscountCode(code.toUpperCase(), percent);
	}

	/**
	 * it permits to obtain the discounts list
	 * @return discounts' list
	 */
	synchronized public List<String> getDiscountList() throws NumberFormatException, SQLException {
		List<String> discountCodeList = new ArrayList<>();

		for (TicketPricingStrategy disc: PersistenceFacade.getInstance().getDiscountList()) {
			discountCodeList.add(disc.getCode());
		}
		return discountCodeList;
	}

	/**
	 * it permits to delete a discount
	 * @param code discount's code
	 */
	synchronized public void deleteDiscount(String code) throws SearchException, SQLException {
		PricingStrategyFactory.getInstance().removeDiscountCode(code);
	}

	public static DiscountHandler getInstance() {
		if (instance == null) {
			instance = new DiscountHandler();
		}
		return instance;
	}

}
