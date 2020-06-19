package server.domain.discount;

public class CodeStrategy implements TicketPricingStrategy{
	private String code;
	private double percent;

	public CodeStrategy(String code, double percent) {
		this.code=code;
		this.percent=percent;
	}

	public String getCode() {
		return code;
	}

	public double getPercent() {
		return percent;
	}

	@Override
	public double getTotalPrice(double price) {
		return price*(percent/100);
	}
}
