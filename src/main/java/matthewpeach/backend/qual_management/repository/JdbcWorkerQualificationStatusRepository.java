package matthewpeach.backend.qual_management.repository;

import matthewpeach.backend.qual_management.entity.Worker;
import matthewpeach.backend.qual_management.entity.WorkerQualificationStatus;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

@Repository
public class JdbcWorkerQualificationStatusRepository implements WorkerQualificationStatusRepository {
    final private JdbcTemplate jdbcTemplate;

    public JdbcWorkerQualificationStatusRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public void deleteAll() {
        jdbcTemplate.update("DELETE FROM WorkerQualificationStatus");
    }

    @Override
    public void insert(WorkerQualificationStatus workerQualificationStatus) {
        String sql = """
                INSERT INTO WORKERQUALIFICATIONSTATUS (qualificationId, workerId, assignedOn, completedOn, expiration) 
                VALUES (?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, workerQualificationStatus.getQualificationId());
            preparedStatement.setLong(2, workerQualificationStatus.getWorkerId());
            preparedStatement.setDate(3, Date.valueOf(workerQualificationStatus.getAssignedOn()));

            LocalDate completedOn = workerQualificationStatus.getCompletedOn();
            LocalDate expiration = workerQualificationStatus.getExpiration();

            if(completedOn != null){
                preparedStatement.setDate(4, Date.valueOf(completedOn));
            }
            else{
                preparedStatement.setDate(4, null);
            }

            if(expiration != null){
                preparedStatement.setDate(5, Date.valueOf(expiration));
            }
            else{
                preparedStatement.setDate(5, null);
            }

            return preparedStatement;
        });
    }


    @Override
    public void update(Long id, LocalDate completedOn, LocalDate expiration) {
        String sql = "UPDATE WorkerQualificationStatus SET completedOn = ?, expiration = ? WHERE id = ?";
        Date completedOnSql = completedOn == null ? null : Date.valueOf(completedOn);
        Date expirationSql = expiration == null ? null : Date.valueOf(expiration);
        jdbcTemplate.update(sql, completedOnSql, expirationSql, id);
    }

    @Override
    public void update(Long id, LocalDate assignedOn) {
        String sql = "UPDATE WorkerQualificationStatus SET assignedOn = ? WHERE id = ?";
        Date assignedOnSql = assignedOn == null ? null : Date.valueOf(assignedOn);
        jdbcTemplate.update(sql, assignedOnSql, id);
    }

    @Override
    public void assign(Long workerId, Long qualificationId, LocalDate assignedOn) {
        String sql = """
                UPDATE WorkerQualificationStatus SET assignedOn = ? WHERE workerId = ? AND qualificationId = ?
                """;

        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setDate(1, Date.valueOf(assignedOn));
            preparedStatement.setLong(2, workerId);
            preparedStatement.setLong(3, qualificationId);
            return preparedStatement;
        });
    }

    @Override
    public List<WorkerQualificationStatus> findByWorkerId(Long workerId) {
        String sql = "SELECT * FROM WorkerQualificationStatus WHERE workerId = ?";
        return jdbcTemplate.query(sql, new WorkerQualificationStatusRowMapper(), workerId);
    }

    @Override
    public WorkerQualificationStatus findByWorkerIdAndQualificationId(Long workerId, Long qualificationId) {
        String sql = "SELECT * FROM WorkerQualificationStatus WHERE workerId = ? AND qualificationId = ?";
        return jdbcTemplate.queryForObject(sql, new WorkerQualificationStatusRowMapper(), workerId, qualificationId);
    }

    @Override
    public Integer getSize() {
        String sql = "SELECT COUNT(*) FROM WorkerQualificationStatus";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    private class WorkerQualificationStatusRowMapper implements RowMapper<WorkerQualificationStatus>{
        @Override
        public WorkerQualificationStatus mapRow(ResultSet rs, int rowNum) throws SQLException {
            WorkerQualificationStatus workerQualificationStatus = new WorkerQualificationStatus();
            workerQualificationStatus.setId(rs.getLong("id"));
            workerQualificationStatus.setQualificationId((rs.getLong("qualificationId")));
            workerQualificationStatus.setWorkerId(rs.getLong("workerId"));
            workerQualificationStatus.setAssignedOn(rs.getDate("assignedOn").toLocalDate());

            Date completedOn = rs.getDate("completedOn");
            Date expiration = rs.getDate("expiration");

            if(completedOn != null){
                workerQualificationStatus.setCompletedOn(completedOn.toLocalDate());
            }

            if(expiration != null){
                workerQualificationStatus.setExpiration(expiration.toLocalDate());
            }

            return workerQualificationStatus;
        }
    }
}
