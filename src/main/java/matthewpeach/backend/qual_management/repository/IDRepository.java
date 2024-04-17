package matthewpeach.backend.qual_management.repository;

import java.util.Map;

public interface IDRepository {
    Map<String, Long> getCrafts();
    Map<String, Long> getGrades();
    Map<String, Long> getQualifications();
}
