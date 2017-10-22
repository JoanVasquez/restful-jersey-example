package user.managment.crud;

import java.sql.SQLException;
import java.util.List;

/**
 * Date : 19/10/2017
 * This is an interface with the CRUD methods for an User.
 * @author JoanVasquez
 * @param <Entity> - Generic
 */
public interface CrudMethod<Entity> {
	/**
	 * @param entity - Generic
	 * @return - Generic
	 * @throws SQLException
	 */
	Entity saveEntity(Entity entity) throws SQLException;

	/**
	 * @param entity - Generic
	 * @throws SQLException
	 */
	void updateEntity(Entity entity)throws SQLException;

	/**
	 * @param id - Entity Id
	 * @throws SQLException
	 */
	void deleteEntity(int id) throws SQLException;
	
	/**
	 * @return - Generic List
	 * @throws SQLException
	 */
	List<Entity> getEntities() throws SQLException;
	
}
