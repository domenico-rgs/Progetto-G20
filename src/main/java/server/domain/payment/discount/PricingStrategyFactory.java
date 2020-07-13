package server.domain.payment.discount;

import java.io.IOException;
import java.sql.SQLException;

import server.domain.exception.SeatException;
import server.services.DB.DiscountCodesMapper;
import server.services.DB.PersistenceFacade;

public class PricingStrategyFactory {
	private static PricingStrategyFactory istance = null;
	
	private PricingStrategyFactory() {
	}
	
	public void createDiscountCode(String code, double percent) throws SQLException, IOException, SeatException {
		PersistenceFacade.getInstance().put(code,  DiscountCodesMapper.class, new CodeStrategy(code,percent));
	}
	
	public TicketPricingStrategy getCodeStrategy(String discount) throws SQLException, IOException, SeatException {
		return (TicketPricingStrategy) PersistenceFacade.getInstance().get(discount, DiscountCodesMapper.class);
	}
	
	public static PricingStrategyFactory getInstance() {
		if (istance == null)
			istance = new PricingStrategyFactory();
		return istance;
	}
}
