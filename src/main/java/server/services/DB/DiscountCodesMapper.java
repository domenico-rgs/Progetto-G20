package server.services.DB;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import server.domain.payment.discount.CodeStrategy;

public class DiscountCodesMapper extends AbstractPersistenceMapper {

	private Map<String, CodeStrategy> discounts;

	public DiscountCodesMapper() throws SQLException {
		super("DISCOUNTS");
		this.discounts = new HashMap<>();
		setUp();
	}

	@Override
	protected Object getObjectFromTable(String OID) {
		return null;
	}

	@Override
	protected Object getObjectFromCache(String OID) {
		if(this.discounts.containsKey(OID))
			return this.discounts.get(OID);
		return null;
	}

	@Override
	protected void updateCache(String OID,Object obj) {
		this.discounts.remove(OID);
		this.discounts.put(OID,(CodeStrategy)obj);
	}


	@Override
	public synchronized void put(String OID, Object obj) throws SQLException{
		CodeStrategy d = (CodeStrategy)obj;
		updateCache(OID,d);

		PreparedStatement pstm = conn.prepareStatement("INSERT INTO "+tableName+" VALUES(?,?)");

		pstm.setString(1,OID);
		pstm.setDouble(2,d.getPercent());

		pstm.execute();

	}

	@Override
	public synchronized void updateTable(String OID, Object obj)throws SQLException {
		CodeStrategy d = (CodeStrategy)obj;
		updateCache(OID,d);

		PreparedStatement pstm = conn.prepareStatement("UPDATE " + tableName+" SET discountCode=?, percent=? WHERE discountCode=? ");
		pstm.setString(1,d.getCode());
		pstm.setDouble(2,d.getPercent());
		pstm.setString(3,OID);
		pstm.execute();
	}
	
	protected void deleteDiscount(String code) throws SQLException {
		PreparedStatement stm = conn.prepareStatement("DELETE FROM " + super.tableName + "WHERE discountCode!='' and discountCode=?");
		stm.setObject(1, code);
		stm.execute();
	}

	protected void setUp() throws SQLException {
		Statement stm = super.conn.createStatement();
		ResultSet rs = stm.executeQuery("select * from "+super.tableName);
		while (rs.next()){
			CodeStrategy tmp = new CodeStrategy(rs.getString(1), rs.getDouble(2));

			this.discounts.put(rs.getString(1),tmp);
		}
	}
}
