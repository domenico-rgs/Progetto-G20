package server.domain.payment.discount;

import java.io.IOException;
import java.sql.SQLException;

import server.domain.exception.SeatException;
import server.services.DB.DiscountCodesMapper;
import server.services.DB.PersistenceFacade;

/**This class is a factory with the aim to create discount code strategy */
public class PricingStrategyFactory {
	private static PricingStrategyFactory istance = null;

	private PricingStrategyFactory() {
	}

	/**
	 * it permits to create a discount code
	 * @param code discount code
	 * @param percent discount percentage
	 */
	public void createDiscountCode(String code, double percent) throws SQLException, IOException, SeatException {
		PersistenceFacade.getInstance().put(code,  DiscountCodesMapper.class, new CodeStrategy(code,percent));
	}

	/**
	 * it permits to obtain a code strategy
	 * @param discount discount code
	 * @return the discount object
	 */
	public TicketPricingStrategy getCodeStrategy(String discount) throws SQLException, IOException, SeatException {
		return (TicketPricingStrategy) PersistenceFacade.getInstance().get(discount, DiscountCodesMapper.class);
	}

	public static PricingStrategyFactory getInstance() {
		if (istance == null)
			istance = new PricingStrategyFactory();
		return istance;
	}
}
