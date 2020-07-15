package server.services.DB;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class MapperFactory {
	private static MapperFactory instance = null;
	private Map<Class<?>,IMapper> mappers;

	private MapperFactory()throws SQLException {
		this.mappers  = new HashMap<>();
		SeatsMapper sm = new SeatsMapper();
		AvailabilityMapper am = new AvailabilityMapper();
		TheatresMapper tm = new TheatresMapper(sm);
		ShowingsMapper shm = new ShowingsMapper(tm,am);
		this.mappers.put(TheatresMapper.class, tm);
		this.mappers.put(MoviesMapper.class, new MoviesMapper());
		this.mappers.put(ShowingsMapper.class, shm);
		this.mappers.put(TicketsMapper.class, new TicketsMapper(shm));
		this.mappers.put(AvailabilityMapper.class, am);
		this.mappers.put(SeatsMapper.class, sm);
		this.mappers.put(DiscountCodesMapper.class, new DiscountCodesMapper());
	}

	public static MapperFactory getInstance()throws SQLException {
		if(instance == null) {
			instance = new MapperFactory();
		}
		return instance;
	}

	public Map<Class<?>, IMapper> getMappers() {
		return mappers;
	}
}
