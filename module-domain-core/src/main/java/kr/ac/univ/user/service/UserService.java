package kr.ac.univ.user.service;


import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.dto.UserDto;
import kr.ac.univ.user.dto.UserPrincipal;
import kr.ac.univ.user.dto.mapper.UserMapper;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.EmptyUtil;
import org.springframework.data.domain.*;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Page<UserDto> findUserList(Pageable pageable, SearchDto searchDto) {
        Page<User> userList = null;
        Page<UserDto> userDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "USER_ID":
                userList = userRepository.findAllByUsernameContaining(pageable, searchDto.getKeyword());
                break;
            case "KOREAN_NAME":
                userList = userRepository.findAllByKoreanNameContaining(pageable, searchDto.getKeyword());
                break;
            case "Email":
                userList = userRepository.findAllByEmailContaining(pageable, searchDto.getKeyword());
                break;
            default:
                userList = userRepository.findAll(pageable);
                break;
        }

        userDtoList = new PageImpl<UserDto>(UserMapper.INSTANCE.toDto(userList.getContent()), pageable, userList.getTotalElements());

        return userDtoList;
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
        User user = userRepository.findByUsername(username);
        UserPrincipal userPrincipal = null;

        if(EmptyUtil.isEmpty(user)) {
            // 추후 변경: 에러 처리
            throw new UsernameNotFoundException("Username is not found");
        } else {
            userPrincipal = new UserPrincipal(user);
        }

        return userPrincipal;
    }

    public Long insertUser(User user) {
        return userRepository.save(user).getIdx();
    }

    public User findUserByIdx(Long idx) {

        return userRepository.findById(idx).orElse(new User());
    }

    @Transactional
    public Long updateUser(Long idx, UserDto userDto) {
        User persistUser = userRepository.getOne(idx);
        User user = UserMapper.INSTANCE.toEntity(userDto);

        persistUser.update(user);

        return userRepository.save(persistUser).getIdx();
    }

    public void deleteUserByIdx(Long idx) {
        userRepository.deleteById(idx);
    }

    public boolean isDupulicationUserByUsername(String username) {
        return (userRepository.countByUsername(username) > 0) ? true : false;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}