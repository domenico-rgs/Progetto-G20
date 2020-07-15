package server.services.DB;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * A class which is the Factory of the mappers of the Persistence Framework.
 * It is implemented through Singleton pattern implementation.
 */
public class MapperFactory {
	private static MapperFactory instance = null;
	private Map<Class<?>,IMapper> mappers;

	/**
     *Initialize a map which contains all the mappers( key : the Class Object of the mapper,
     * and value : is the instance of the mapper itself.
     * @throws SQLException
     */
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

	/**
     * 'Pattern Singleton Implementation'
     *
     * If the object has not already been instanced, it is instanced and it is returned.
     * @return instance(MapperFactory)
     */
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
