package kr.ac.univ.category.repository;

import kr.ac.univ.category.domain.Category;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
    Page<Category> findAllByTitleContaining(Pageable pageable, String title);

    Page<Category> findAllByCreatedByContaining(Pageable pageable, String username);

    List<Category> findAllByActiveStatusIs(ActiveStatus activeStatus);

    Category findByIdxAndActiveStatusIs(Long idx, ActiveStatus activeStatus);
}