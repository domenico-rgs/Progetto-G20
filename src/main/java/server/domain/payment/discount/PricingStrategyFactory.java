package server.domain.payment.discount;

import java.sql.SQLException;
import java.util.List;

import server.exception.ObjectNotFoundException;
import server.exception.SearchException;
import server.services.persistence.DiscountCodesMapper;
import server.services.persistence.PersistenceFacade;

/**
 * A class which is the Factory of the discounts.
 * It is implemented through Singleton pattern implementation.
 */
public class PricingStrategyFactory {
	private static PricingStrategyFactory istance = null;

	private PricingStrategyFactory() {}

	/**
	 * It permits to create a discount code and save it in the database
	 * @param code discount code
	 * @param percent discount percentage
	 * @throws SQLException errors with the database and / or with the constraints
	 */
	public void createDiscountCode(String code, double percent) throws SQLException {
		PersistenceFacade.getInstance().put(code,  DiscountCodesMapper.class, new CodeStrategy(code,percent));
	}

	/**
	 * It permits to remove a discount code from the database
	 * @param code discount code
	 * @throws SearchException code not found
	 * @throws SQLException errors with the database and / or with the constraints
	 */
	public void removeDiscountCode(String code) throws SQLException, SearchException{
		PersistenceFacade.getInstance().delete(code, DiscountCodesMapper.class);
	}

	/**
	 * It permits to obtain a discount
	 * @param discount discount code
	 * @return the discount object
	 * @throws SQLException errors with the database and / or with the constraints
	 * @throws ObjectNotFoundException
	 */
	public TicketPricingStrategy getDiscount(String discount) throws SQLException, ObjectNotFoundException {
		return (TicketPricingStrategy) PersistenceFacade.getInstance().get(discount, DiscountCodesMapper.class);
	}

	/**
	 * @return a list with all active discount codes
	 * @throws SQLException errors with the database and / or with the constraints
	 */
	public List<TicketPricingStrategy> getDiscountList() throws NumberFormatException, SQLException{
		return PersistenceFacade.getInstance().getDiscountList();
	}

	/**
	 * 'Pattern Singleton Implementation'
	 * If the object has not already been instanced, it is instanced and it is returned.
	 * @return instance(PricingStrategyFactory)
	 */
	public static PricingStrategyFactory getInstance() {
		if (istance == null) {
			istance = new PricingStrategyFactory();
		}
		return istance;
	}
}
