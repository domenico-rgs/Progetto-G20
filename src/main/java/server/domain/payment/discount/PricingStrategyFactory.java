package server.domain.payment.discount;

import java.sql.SQLException;
import java.util.List;

import server.domain.exception.SearchException;
import server.services.DB.DiscountCodesMapper;
import server.services.DB.PersistenceFacade;

/**This class is a factory with the aim to create discount code strategy */
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
	 * It permits to obtain a code strategy
	 * @param discount discount code
	 * @return the discount object
	 * @throws SQLException errors with the database and / or with the constraints
	 */
	public TicketPricingStrategy getCodeStrategy(String discount) throws SQLException {
		return (TicketPricingStrategy) PersistenceFacade.getInstance().get(discount, DiscountCodesMapper.class);
	}

	/**
	 * @return a list with all active discount codes
	 * @throws SQLException errors with the database and / or with the constraints
	 */
	public List<TicketPricingStrategy> getDiscountList() throws NumberFormatException, SQLException{
		return PersistenceFacade.getInstance().getDiscountList();
	}

	public static PricingStrategyFactory getInstance() {
		if (istance == null) {
			istance = new PricingStrategyFactory();
		}
		return istance;
	}
}
