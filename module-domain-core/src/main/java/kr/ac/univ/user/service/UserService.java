package kr.ac.univ.user.service;


import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.domain.enums.AuthorityType;
import kr.ac.univ.user.domain.enums.UserStatus;
import kr.ac.univ.user.domain.enums.UserType;
import kr.ac.univ.user.dto.UserDto;
import kr.ac.univ.user.dto.UserPrincipal;
import kr.ac.univ.user.dto.UserSearchDto;
import kr.ac.univ.user.dto.mapper.UserMapper;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.user.repository.UserRepositoryImpl;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.EmptyUtil;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final UserRepositoryImpl userRepositoryImpl;
    @Value("${module.name}")
    private String moduleName;

    public UserService(UserRepository userRepository, UserRepositoryImpl userRepositoryImpl) {
        this.userRepository = userRepository;
        this.userRepositoryImpl = userRepositoryImpl;
    }

    public Page<UserDto> findUserList(Pageable pageable, UserSearchDto userSearchDto) {
        Page<User> userList = null;
        Page<UserDto> userDtoList = null;

        if ("module-app-admin".equals(moduleName)) {
            pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

            switch (userSearchDto.getSearchType()) {
                case "USER_ID":
                    userList = userRepository.findAllByUsernameContaining(pageable, userSearchDto.getKeyword());
                    break;
                case "KOREAN_NAME":
                    userList = userRepository.findAllByKoreanNameContaining(pageable, userSearchDto.getKeyword());
                    break;
                case "EMAIL":
                    userList = userRepository.findAllByEmailContaining(pageable, userSearchDto.getKeyword());
                    break;
                default:
                    userList = userRepository.findAll(pageable);
                    break;
            }
        } else if ("module-app-web".equals(moduleName)) {
            // 정렬 기준 설정
            Sort sort = Sort.by("userStatus").ascending().and(Sort.by("userType").ascending().and(Sort.by("englishName").ascending()));
            pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, 12, sort);
            List<AuthorityType> authorityTypeList = new ArrayList<>();
            List<UserStatus> userStatusList = new ArrayList<>();
            List<UserType> userTypeList = new ArrayList<>();

            authorityTypeList.add(AuthorityType.MANAGER);
            authorityTypeList.add(AuthorityType.GENERAL);

            switch (userSearchDto.getUserSearchType()) {
                case A_SHOW_ALL:
                    userStatusList.add(UserStatus.ATTENDING);
                    userStatusList.add(UserStatus.GRADUATED);
                    userTypeList.add(UserType.A_FACULTY);
                    userTypeList.add(UserType.B_FULL_TIME_PHD);
                    userTypeList.add(UserType.C_FULL_TIME_MS);
                    userTypeList.add(UserType.D_PART_TIME_PHD);
                    userTypeList.add(UserType.E_PART_TIME_MS);
                    userTypeList.add(UserType.F_BS);
                    break;
                case B_FACULTY:
                    userStatusList.add(UserStatus.ATTENDING);
                    userTypeList.add(UserType.A_FACULTY);
                    break;
                case C_PHD:
                    userStatusList.add(UserStatus.ATTENDING);
                    userTypeList.add(UserType.B_FULL_TIME_PHD);
                    userTypeList.add(UserType.D_PART_TIME_PHD);
                    break;
                case D_MS:
                    userStatusList.add(UserStatus.ATTENDING);
                    userTypeList.add(UserType.C_FULL_TIME_MS);
                    userTypeList.add(UserType.E_PART_TIME_MS);
                    break;
                case E_BS:
                    userStatusList.add(UserStatus.ATTENDING);
                    userTypeList.add(UserType.F_BS);
                    break;
                case F_ALUMNI:
                    userStatusList.add(UserStatus.GRADUATED);
                    userTypeList.add(UserType.A_FACULTY);
                    userTypeList.add(UserType.B_FULL_TIME_PHD);
                    userTypeList.add(UserType.C_FULL_TIME_MS);
                    userTypeList.add(UserType.D_PART_TIME_PHD);
                    userTypeList.add(UserType.E_PART_TIME_MS);
                    userTypeList.add(UserType.F_BS);
                    break;
            }

            switch (userSearchDto.getSearchType()) {
                case "ENGLISH_NAME":
                    userList = userRepository.findAllByAuthorityTypeInAndEnglishNameContainingAndUserStatusInAndUserTypeInAndActiveStatusIs(pageable, authorityTypeList, userSearchDto.getKeyword(), userStatusList, userTypeList, ActiveStatus.ACTIVE);
                    break;
                case "KOREAN_NAME":
                    userList = userRepository.findAllByAuthorityTypeInAndKoreanNameContainingAndUserStatusInAndUserTypeInAndActiveStatusIs(pageable, authorityTypeList, userSearchDto.getKeyword(), userStatusList, userTypeList, ActiveStatus.ACTIVE);
                    break;
                case "EMAIL":
                    userList = userRepository.findAllByAuthorityTypeInAndEmailContainingAndUserStatusInAndUserTypeInAndActiveStatusIs(pageable, authorityTypeList, userSearchDto.getKeyword(), userStatusList, userTypeList, ActiveStatus.ACTIVE);
                    break;
                default:
                    userList = userRepository.findAllByAuthorityTypeInAndUserStatusInAndUserTypeInAndActiveStatusIs(pageable, authorityTypeList, userStatusList, userTypeList, ActiveStatus.ACTIVE);
                    break;
            }
        } else {
            userList = null;
        }

        userDtoList = new PageImpl<>(UserMapper.INSTANCE.toDto(userList.getContent()), pageable, userList.getTotalElements());

        // NewIcon 판별
        for (UserDto userDto : userDtoList) {
            userDto.setNewIcon(NewIconCheck.isNew(userDto.getCreatedDate()));
        }

        return userDtoList;
    }


    public Long countAllByActiveStatusIsAndUserStatusIsAndUserTypeIs(UserType userType) {
        return userRepository.countAllByActiveStatusIsAndUserStatusIsAndUserTypeIs(ActiveStatus.ACTIVE, UserStatus.ATTENDING, userType);
    }

    public Long countUser() {
        return userRepository.count();
    }


    /**
     * 회원 가입
     *
     * @param userDto
     * @return
     */
    @Transactional
    public Long joinUser(UserDto userDto) {
        // 비밀번호 암호화
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));

        return userRepository.save(UserMapper.INSTANCE.toEntity(userDto)).getIdx();
    }

    /**
     * 사용자 정의 로그인
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = null;
        UserPrincipal userPrincipal = null;

        if ("module-app-admin".equals(moduleName)) {
            AuthorityType[] authorityType = {AuthorityType.ROOT, AuthorityType.MANAGER};

            user = userRepository.findByUsernameAndAuthorityTypeIn(username, authorityType);
        } else if ("module-app-web".equals(moduleName)) {
            user = userRepository.findByUsername(username);
        } else {
            user = null;
        }

        // User 아이디가 없는 경우, 로그인 실패
        if (EmptyUtil.isEmpty(user)) {
            throw new UsernameNotFoundException(null);
        }

        userPrincipal = new UserPrincipal(user);

        return userPrincipal;
    }

    public UserDto findUserByIdx(Long idx) {
        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        // Update: isAccess 메소드에 따라 접근 가능 및 불가
        UserDto userDto = UserMapper.INSTANCE.toDto(userRepository.findById(idx).orElse(new User()));

        if ("module-app-web".equals(moduleName)) {
            userDto.setAccess(AccessCheck.isAccessInModuleWebUser(userDto.getCreatedBy(), userDto.getUsername(), EmptyUtil.isEmpty(userDto) ? "general" : userDto.getAuthorityType().getAuthorityType()));
        } else {
            if (idx == 0) {
                userDto.setAccess(true);
            } else if (EmptyUtil.isEmpty(userDto)) {
                userDto.setAccess(false);
            } else {
                userDto.setAccess(AccessCheck.isAccessInModuleAdminUser(userDto.getCreatedBy(), userDto.getUsername(), userDto.getAuthorityType().getAuthorityType()));
            }
        }

        userRepositoryImpl.updateViewsByIdx(idx);
        userDto.setViews(userDto.getViews() + 1);

        return userDto;
    }

    @Transactional
    public Long updateUser(Long idx, UserDto userDto) {
        User persistUser = userRepository.getOne(idx);
        User user = UserMapper.INSTANCE.toEntity(userDto);

        persistUser.update(user, persistUser.getPassword());

        return userRepository.save(persistUser).getIdx();
    }

    public void deleteUserByIdx(Long idx) {
        userRepository.deleteById(idx);
    }

    public boolean isDuplicationUserByUsername(String username) {
        String regex = "^[a-z0-9]{4,16}$";

        return userRepository.countByUsername(username) > 0 && username.length() >= 4 && username.length() <= 16 && Pattern.matches(regex, username) ? true : false;
    }
}