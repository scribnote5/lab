package kr.ac.univ.researchField.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.researchField.domain.ResearchField;
import kr.ac.univ.researchField.domain.enums.ResearchFieldStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ResearchFieldRepository extends JpaRepository<ResearchField, Long> {
    Page<ResearchField> findAllByTitleContaining(Pageable pageable, String title);

    Page<ResearchField> findAllByCreatedByContaining(Pageable pageable, String username);

    List<ResearchField> findAllByActiveStatusIs(ActiveStatus activeStatus);

    List<ResearchField> findAllByActiveStatusIsAndResearchFieldStatusIs(ActiveStatus activeStatus, ResearchFieldStatus researchFieldStatus);

    ResearchField findByIdxAndActiveStatusIs(Long idx, ActiveStatus activeStatus);
}