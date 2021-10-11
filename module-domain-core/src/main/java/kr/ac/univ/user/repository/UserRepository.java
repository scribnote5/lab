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

    User findByEnglishName(String englishName);

    User findByUsernameAndAuthorityTypeIn(String username, AuthorityType[] authorityTypeList);

    Long countAllBy();

    Long countAllByActiveStatusIsAndUserStatusIsAndUserTypeIs(ActiveStatus activeStatus, UserStatus userStatusList, UserType userTypeList);

    Page<User> findAllByUsernameContaining(Pageable pageable, String username);

    Page<User> findAllByKoreanNameContaining(Pageable pageable, String koreanName);

    Page<User> findAllByEmailContaining(Pageable pageable, String email);


    Page<User> findAllByAuthorityTypeInAndUserStatusInAndUserTypeInAndActiveStatusIs(Pageable pageable, List<AuthorityType> authorityTypeList, List<UserStatus> userStatusList, List<UserType> userTypeList, ActiveStatus activeStatus);

    Page<User> findAllByAuthorityTypeInAndEnglishNameContainingAndUserStatusInAndUserTypeInAndActiveStatusIs(Pageable pageable, List<AuthorityType> authorityTypeList, String email, List<UserStatus> userStatusList, List<UserType> userTypeList, ActiveStatus activeStatus);

    Page<User> findAllByAuthorityTypeInAndKoreanNameContainingAndUserStatusInAndUserTypeInAndActiveStatusIs(Pageable pageable, List<AuthorityType> authorityTypeList, String email, List<UserStatus> userStatusList, List<UserType> userTypeList, ActiveStatus activeStatus);

    Page<User> findAllByAuthorityTypeInAndEmailContainingAndUserStatusInAndUserTypeInAndActiveStatusIs(Pageable pageable, List<AuthorityType> authorityTypeList, String email, List<UserStatus> userStatusList, List<UserType> userTypeList, ActiveStatus activeStatus);
}