package matthewpeach.backend.qual_management.repository;

import matthewpeach.backend.qual_management.entity.Qualification;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class JdbcQualificationRepository implements QualificationRepository{
    final private JdbcTemplate jdbcTemplate;

    public JdbcQualificationRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Qualification> getAll() {
        String sql = "SELECT * FROM QUALIFICATION";
        return jdbcTemplate.query(sql, new QualificationRowMapper());
    }

    @Override
    public Qualification findById(Long id) {
        String sql = "SELECT * FROM QUALIFICATION WHERE id = ?";
        return jdbcTemplate.queryForObject(sql, new QualificationRowMapper(), id);
    }

    @Override
    public List<Qualification> findByCraftId(Long craftId) {
        if(craftId == null){
            String sql = "SELECT * FROM Qualification WHERE craft_id IS NULL";
            return jdbcTemplate.query(sql, new QualificationRowMapper());
        }

        String sql = "SELECT * FROM Qualification WHERE craft_id = ?";
        return jdbcTemplate.query(sql, new QualificationRowMapper(), craftId);
    }

    private class QualificationRowMapper implements RowMapper<Qualification> {
        @Override
        public Qualification mapRow(ResultSet rs, int rowNum) throws SQLException {
            Qualification qualification = new Qualification();
            qualification.setId(rs.getLong("id"));
            qualification.setName(rs.getString("name"));
            qualification.setCraftId(rs.getLong("craft_id"));
            qualification.setDaysUntilExpiration(rs.getLong("daysUntilExpiration"));

            return qualification;
        }
    }
}
