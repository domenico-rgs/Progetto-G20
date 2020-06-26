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
		TheatreMapper tm = new TheatreMapper(sm);
		this.mappers.put(TheatreMapper.class, tm);
		this.mappers.put(MoviesMapper.class, new MoviesMapper());
		this.mappers.put(ShowingsMapper.class, new ShowingsMapper(tm));
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
