package server.services.DB;

import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import server.domain.exception.SeatException;

public class MapperFactory {
	private static MapperFactory instance = null;
	private Map<Class,IMapper> mappers;

	private MapperFactory()throws SQLException, IOException, SeatException {
		this.mappers  = new HashMap<>();
		SeatsMapper sm = new SeatsMapper();
		TheatresMapper tm = new TheatresMapper(sm);
		AvailabilityMapper am = new AvailabilityMapper();
		this.mappers.put(TheatresMapper.class, tm);
		this.mappers.put(MoviesMapper.class, new MoviesMapper());
		this.mappers.put(ShowingsMapper.class, new ShowingsMapper(tm,am));
		this.mappers.put(TicketsMapper.class, new TicketsMapper());

	}

	public static MapperFactory getInstance()throws SQLException, IOException, SeatException {
		if(instance == null)
			instance = new MapperFactory();
		return instance;
	}

	public Map<Class, IMapper> getMappers() {
		return mappers;
	}
}