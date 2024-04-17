package matthewpeach.backend.qual_management.repository;

import matthewpeach.backend.qual_management.entity.Worker;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcWorkerRepository implements WorkerRepository {
    final private JdbcTemplate jdbcTemplate;
    final private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public JdbcWorkerRepository(JdbcTemplate jdbcTemplate, NamedParameterJdbcTemplate namedParameterJdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Override
    public List<Worker> advancedSearch(Long workerId, String firstName, String lastName, String phone, String email, Long gradeId, Long craftId) {
        StringBuilder sql = new StringBuilder("SELECT * FROM WORKER WHERE 1=1");
        MapSqlParameterSource parameters = new MapSqlParameterSource();

        if (workerId != null) {
            // Assuming ID should match exactly, not using LIKE
            sql.append(" AND id = :id");
            parameters.addValue("id", workerId);
        }

        if (firstName != null && !firstName.isBlank()) {
            sql.append(" AND firstName LIKE :firstName");
            parameters.addValue("firstName", "%" + firstName + "%");
        }

        if (lastName != null && !lastName.isBlank()) {
            sql.append(" AND lastName LIKE :lastName");
            parameters.addValue("lastName", "%" + lastName + "%");
        }

        if (phone != null && !phone.isBlank()) {
            sql.append(" AND phone LIKE :phone");
            parameters.addValue("phone", "%" + phone + "%");
        }

        if (email != null && !email.isBlank()) {
            sql.append(" AND email = :email");
            parameters.addValue("email", email);
        }

        if (gradeId != null) {
            sql.append(" AND grade_id = :gradeId");
            parameters.addValue("gradeId", gradeId);
        }

        if (craftId != null) {
            sql.append(" AND craft_id = :craftId");
            parameters.addValue("craftId", craftId);
        }

        return namedParameterJdbcTemplate.query(sql.toString(), parameters, new BeanPropertyRowMapper<>(Worker.class));

    }

    @Override
    public List<Worker> getAll() {
        String sql = "SELECT * FROM WORKER";
        return jdbcTemplate.query(sql, new WorkerRowMapper());
    }

    @Override
    public Worker findById(Long id) {
        String sql = "SELECT * FROM WORKER WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new WorkerRowMapper(), id);
    }

    @Override
    public Long insert(Worker worker) {
        String sql = """
                INSERT INTO WORKER (firstName, lastName, phone, email, grade_id, craft_id) 
                VALUES (?, ?, ?, ?, ?, ?)
                """;

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, worker.getFirstName());
            preparedStatement.setString(2, worker.getLastName());
            preparedStatement.setString(3, worker.getPhone());
            preparedStatement.setString(4, worker.getEmail());
            preparedStatement.setLong(5, worker.getGradeId());
            preparedStatement.setLong(6, worker.getCraftId());
            return preparedStatement;
        }, keyHolder);

        return (long) keyHolder.getKey();
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM WORKER");
    }

    @Override
    public Integer getSize() {
        String sql = "SELECT COUNT(*) FROM Worker";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    private class WorkerRowMapper implements RowMapper<Worker>{
        @Override
        public Worker mapRow(ResultSet rs, int rowNum) throws SQLException {
            Worker worker = new Worker();
            worker.setId(rs.getLong("id"));
            worker.setFirstName(rs.getString("firstName"));
            worker.setLastName(rs.getString("lastName"));
            worker.setPhone(rs.getString("phone"));
            worker.setEmail(rs.getString("email"));
            worker.setGradeId(rs.getLong("grade_id"));
            worker.setCraftId(rs.getLong("craft_id"));
            return worker;
        }
    }
}
