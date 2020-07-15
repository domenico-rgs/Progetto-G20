package server.domain.payment.discount;

/** This class represents a discount code used to modified the total value of the ticket */
public class CodeStrategy implements TicketPricingStrategy{
	private String code;
	private double percent;

	public CodeStrategy(String code, double percent) {
		this.code=code;
		this.percent=percent;
	}

	@Override
	public double getTotalPrice(double price) {
		return price - price*(percent/100);
	}

	@Override
	public String getCode() {
		return code;
	}

	public double getPercent() {
		return percent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		long temp;
		temp = Double.doubleToLongBits(percent);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CodeStrategy other = (CodeStrategy) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (Double.doubleToLongBits(percent) != Double.doubleToLongBits(other.percent))
			return false;
		return true;
	}
}
