package it.unipv.www.g20.model.operator;

/**
 * This is a generic operator of the cinema (manager or cashier)
 */
public abstract class Operator {
	private String nickname;
	protected TypeOperator type;

	public Operator(String nickname) {
		setNickname(nickname);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Operator other = (Operator) obj;
		if (nickname == null) {
			if (other.nickname != null)
				return false;
		} else if (!nickname.equals(other.nickname))
			return false;
		if (type != other.type)
			return false;
		return true;
	}

	public String getNickname() {
		return nickname;
	}

	public TypeOperator getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = (prime * result) + ((nickname == null) ? 0 : nickname.hashCode());
		result = (prime * result) + ((type == null) ? 0 : type.hashCode());
		return result;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public void setType(TypeOperator type) {
		this.type = type;
	}

	@Override
	public String toString() {
		return "Operator: " + nickname + ", type=" + type;
	}
}
