package com.example.vkr_back2.repository;

import com.example.vkr_back2.entity.ClassEntity;
import com.example.vkr_back2.entity.RoleEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class RoleRepository implements IRestRepository<RoleEntity> {

    protected final JdbcOperations jdbcOperations;

    private static String selectQuery = "SELECT \"role_id\", \"role_name\"" +
            "FROM \"users_roles\" " +
            "ORDER BY \"role_id\"";

    private static String selectBySourceIdQuery = "SELECT \"role_id\", \"role_name\" " +
            "FROM \"users_roles\" " +
            "WHERE \"role_id\" = ?";

    private static String selectByIdQuery = "SELECT \"role_id\", \"role_name\" " +
            "FROM \"users_roles\" " +
            "WHERE \"role_id\" = ?";

    private static String insertQuery = "INSERT INTO \"users_roles\"(\"role_name\") " +
            "VALUES (?) " +
            "RETURNING \"role_id\", \"role_name\"";

    private static String updateQuery = "UPDATE \"users_roles\" " +
            "SET \"role_name\" = ?" +
            "WHERE \"role_id\" = ? " +
            "RETURNING \"role_id\", \"role_name\"";

    private static String deleteQuery = "DELETE FROM \"users_roles\" " +
            "WHERE \"role_id\" = ? " +
            "RETURNING \"role_id\", \"role_name\"";

    public RoleRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public RoleEntity[] select() {

        ArrayList<RoleEntity> values = new ArrayList<RoleEntity>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new RoleEntity(
                            rowSet.getInt(1),
                            rowSet.getString(2)
                    )
            );
        }
        RoleEntity[] result = new RoleEntity[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public RoleEntity select(Integer id) {

        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new RoleEntity(
                rowSet.getInt(1),
                rowSet.getString(2)
        );    }

    @Override
    public RoleEntity insert(RoleEntity entity) {

        Object[] params = new Object[] {entity.getRole_name()};
        int[] types = new int[] { Types.VARCHAR };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new RoleEntity(
                rowSet.getInt(1),
                rowSet.getString(2)
        );    }

    @Override
    public RoleEntity update(Integer id, RoleEntity entity) {
        Object[] params = new Object[] {entity.getRole_name(), id };
        int[] types = new int[] { Types.INTEGER, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new RoleEntity(
                rowSet.getInt(1),
                rowSet.getString(2)
        );    }

    @Override
    public RoleEntity delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new RoleEntity(
                rowSet.getInt(1),
                rowSet.getString(2)
        );    }

    public RoleEntity[] selectBySourceId(Integer sourceId) {
        ArrayList<RoleEntity> values = new ArrayList<RoleEntity>();
        Object[] params = new Object[] { sourceId };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectBySourceIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new RoleEntity(
                    rowSet.getInt(1),
                    rowSet.getString(2)
            ));
        }
        RoleEntity[] result = new RoleEntity[values.size()];
        result = values.toArray(result);
        return result;
    }
}
