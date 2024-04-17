package matthewpeach.backend.qual_management.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class JdbcIDRepository implements IDRepository{
    final private JdbcTemplate jdbcTemplate;

    public JdbcIDRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Map<String, Long> getCrafts() {
        Map<String, Long> result = new HashMap<>();
        String sql = "SELECT * FROM Craft";
        List<Map<String, Object>> columnValues = jdbcTemplate.queryForList(sql);
        columnValues.forEach(row -> {
            String name = (String) row.get("name");
            Long id = (Long) row.get("id");
            result.put(name, id);
        });
        return result;
    }

    @Override
    public Map<String, Long> getGrades() {
        Map<String, Long> result = new HashMap<>();
        String sql = "SELECT * FROM Grade";
        List<Map<String, Object>> columnValues = jdbcTemplate.queryForList(sql);
        columnValues.forEach(row -> {
            String name = (String) row.get("name");
            Long id = (Long) row.get("id");
            result.put(name, id);
        });
        return result;
    }

    @Override
    public Map<String, Long> getQualifications() {
        Map<String, Long> result = new HashMap<>();
        String sql = "SELECT * FROM Qualification";
        List<Map<String, Object>> columnValues = jdbcTemplate.queryForList(sql);
        columnValues.forEach(row -> {
            String name = (String) row.get("name");
            Long id = (Long) row.get("id");
            result.put(name, id);
        });
        return result;
    }
}
