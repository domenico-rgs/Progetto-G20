package server.domain.payment.discount;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import server.domain.exception.SearchException;
import server.domain.exception.SeatException;
import server.services.DB.DiscountCodesMapper;
import server.services.DB.PersistenceFacade;

/**This class is a factory with the aim to create discount code strategy */
public class PricingStrategyFactory {
	private static PricingStrategyFactory istance = null;

	/**
	 * It permits to create a discount code
	 * @param code discount code
	 * @param percent discount percentage
	 */
	public void createDiscountCode(String code, double percent) throws SQLException, IOException, SeatException {
		PersistenceFacade.getInstance().put(code,  DiscountCodesMapper.class, new CodeStrategy(code,percent));
	}

	/**
	 * It permits to create a discount code
	 * @param code discount code
	 * @throws SearchException
	 * @throws SeatException
	 * @throws IOException
	 * @throws SQLException
	 */
	public void removeDiscountCode(String code) throws SearchException, SQLException, IOException, SeatException {
		PersistenceFacade.getInstance().delete(code, DiscountCodesMapper.class);
	}

	/**
	 * It permits to obtain a code strategy
	 * @param discount discount code
	 * @return the discount object
	 */
	public TicketPricingStrategy getCodeStrategy(String discount) throws SQLException, IOException, SeatException {
		return (TicketPricingStrategy) PersistenceFacade.getInstance().get(discount, DiscountCodesMapper.class);
	}
	
	public List<TicketPricingStrategy> getDiscountList() throws NumberFormatException, SQLException, IOException, SeatException{
		return PersistenceFacade.getInstance().getDiscountList();
	}

	public static PricingStrategyFactory getInstance() {
		if (istance == null)
			istance = new PricingStrategyFactory();
		return istance;
	}
}
