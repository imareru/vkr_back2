package com.example.vkr_back2.repository;

import com.example.vkr_back2.entity.TestEntity;
import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.sql.Types;
import java.util.ArrayList;

@Repository
public class TestRepository implements IRestRepository<TestEntity> {

    protected final JdbcOperations jdbcOperations;


    private static String selectQuery = "SELECT \"test_id\", \"test_name\", \"pass_time\", \"if_access\", \"close_date\", \"subject_id\"" +
            "FROM \"tests\" " +
            "ORDER BY \"test_id\"";

    private static String selectBySourceIdQuery = "SELECT \"test_id\", \"test_name\", \"pass_time\", \"if_access\", \"close_date\", \"subject_id\"" +
            "FROM \"tests\" " +
            "WHERE \"test_id\" = ?";

    private static String selectByIdQuery = "SELECT \"test_id\", \"test_name\", \"pass_time\", \"if_access\", \"close_date\", \"subject_id\"" +
            "FROM \"tests\" " +
            "WHERE \"test_id\" = ?";

    private static String insertQuery = "INSERT INTO \"tests\"(\"test_name\", \"pass_time\", \"if_access\", \"close_date\", \"subject_id\") " +
            "VALUES (?, ?, ?, ?, ?) " +
            "RETURNING \"test_id\", \"test_name\", \"pass_time\", \"if_access\", \"close_date\", \"subject_id\"";

    private static String updateQuery = "UPDATE \"tests\" " +
            "SET \"test_id\" = ?, \"test_name\" = ?, \"pass_time\" = ?, \"if_access\" = ?, \"close_date\" = ?, \"subject_id\" = ? " +
            "WHERE \"test_id\" = ? " +
            "RETURNING \"test_id\", \"test_name\", \"pass_time\", \"if_access\", \"close_date\", \"subject_id\"";

    private static String deleteQuery = "DELETE FROM \"tests\" " +
            "WHERE \"test_id\" = ? " +
            "RETURNING \"test_id\", \"test_name\", \"pass_time\", \"if_access\", \"close_date\", \"subject_id\"";

    public TestRepository(JdbcOperations jdbcOperations) {
        this.jdbcOperations = jdbcOperations;
    }

    @Override
    public TestEntity[] select() {
        ArrayList<TestEntity> values = new ArrayList<TestEntity>();
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectQuery);
        while (rowSet.next()) {
            values.add(new TestEntity(
                            rowSet.getInt(1),
                            rowSet.getString(2),
                            rowSet.getString(3),
                            rowSet.getInt(4),
                            rowSet.getBoolean(5),
                            rowSet.getString(6)
                    )
            );
        }
        TestEntity[] result = new TestEntity[values.size()];
        result = values.toArray(result);
        return result;
    }

    @Override
    public TestEntity select(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectByIdQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new TestEntity(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getInt(4),
                rowSet.getBoolean(5),
                rowSet.getString(6)
        );
    }

    @Override
    public TestEntity insert(TestEntity entity) {
        Object[] params = new Object[] {entity.getTest_name(), entity.getPass_time(), entity.getIf_access(), entity.getClose_date(), entity.getSubject_id()};
        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN, Types.VARCHAR, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(insertQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new TestEntity(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getInt(4),
                rowSet.getBoolean(5),
                rowSet.getString(6)
        );
    }

    @Override
    public TestEntity update(Integer id, TestEntity entity) {
        Object[] params = new Object[] {entity.getTest_name(), entity.getPass_time(), entity.getIf_access(), entity.getClose_date(), entity.getSubject_id(), id };
        int[] types = new int[] { Types.VARCHAR, Types.VARCHAR, Types.BOOLEAN, Types.VARCHAR, Types.INTEGER, Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(updateQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new TestEntity(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getInt(4),
                rowSet.getBoolean(5),
                rowSet.getString(6)
        );
    }

    @Override
    public TestEntity delete(Integer id) {
        Object[] params = new Object[] { id };
        int[] types = new int[] {Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(deleteQuery, params, types);
        if (!rowSet.next()) {
            return null;
        }
        return new TestEntity(
                rowSet.getInt(1),
                rowSet.getString(2),
                rowSet.getString(3),
                rowSet.getInt(4),
                rowSet.getBoolean(5),
                rowSet.getString(6)
        );
    }

    public TestEntity[] selectBySourceId(Integer sourceId) {
        ArrayList<TestEntity> values = new ArrayList<TestEntity>();
        Object[] params = new Object[] { sourceId };
        int[] types = new int[] { Types.INTEGER };
        SqlRowSet rowSet = jdbcOperations.queryForRowSet(selectBySourceIdQuery, params, types);
        while (rowSet.next()) {
            values.add(new TestEntity(
                    rowSet.getInt(1),
                    rowSet.getString(2),
                    rowSet.getString(3),
                    rowSet.getInt(4),
                    rowSet.getBoolean(5),
                    rowSet.getString(6)
            ));
        }
        TestEntity[] result = new TestEntity[values.size()];
        result = values.toArray(result);
        return result;
    }
}
