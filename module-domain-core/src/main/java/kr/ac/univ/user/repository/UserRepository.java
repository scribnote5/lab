package kr.ac.univ.user.repository;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.domain.enums.AuthorityType;
import kr.ac.univ.user.domain.enums.UserStatus;
import kr.ac.univ.user.domain.enums.UserType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Long countByUsername(String username);

    User findByUsername(String username);

    User findByUsernameAndAuthorityTypeIn(String username, AuthorityType[] authorityType);

    Long countAllBy();

    Long countAllByActiveStatusIsAndUserStatusIsAndUserTypeIs(ActiveStatus activeStatus, UserStatus userStatus, UserType userType);

    Page<User> findAllByUsernameContaining(Pageable pageable, String username);

    Page<User> findAllByKoreanNameContaining(Pageable pageable, String koreanName);

    Page<User> findAllByEmailContaining(Pageable pageable, String email);

    Page<User> findAllByUsernameNotAndActiveStatusIs(Pageable pageable, String rootName, ActiveStatus activeStatus);

    Page<User> findAllByUsernameNotAndEnglishNameContainingAndActiveStatusIs(Pageable pageable, String rootName, String englishName, ActiveStatus activeStatus);

    Page<User> findAllByUsernameNotAndKoreanNameContainingAndActiveStatusIs(Pageable pageable, String rootName, String koreanName, ActiveStatus activeStatus);

    Page<User> findAllByUsernameNotAndEmailContainingAndActiveStatusIs(Pageable pageable, String rootName, String email, ActiveStatus activeStatus);
}