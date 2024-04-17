package matthewpeach.backend.qual_management.repository;

import matthewpeach.backend.qual_management.entity.Qualification;

import java.util.List;

public interface QualificationRepository {
    public List<Qualification> getAll();
    public Qualification findById(Long id);
    public List<Qualification> findByCraftId(Long craftId);

}
