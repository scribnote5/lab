package kr.ac.univ;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.noticeBoard.domain.NoticeBoard;
import kr.ac.univ.noticeBoard.repository.NoticeBoardRepository;
import kr.ac.univ.user.domain.enums.AuthorityType;
import kr.ac.univ.user.domain.enums.UserType;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;

import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.repository.UserRepository;

import java.util.stream.IntStream;

@RestController
@SpringBootApplication
public class ModuleWebApplication {
    BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static void main(String[] args) {
        SpringApplication.run(ModuleWebApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(NoticeBoardRepository noticeBoardRepository, UserRepository userRepository) {
        return (args) -> {
            /* 게시글 모두 삭제 */
            /* noticeBoardRepository.deleteAll(); */

            /* 게시글 등록 */
            /*
            IntStream.rangeClosed(1, 200).forEach(index ->
                noticeBoardRepository.save(NoticeBoard.builder()
                    .title("게시글" + index)
                    .content("컨텐츠" + index)
                    .activeStatus(ActiveStatus.ACTIVE)
                    .build()));
            */

            /* 사용자 모두 삭제 */
           /* userRepository.deleteAll(); */

            /* 사용자 생성 */
            /* userRepository.save(User.builder()
                    .username("root")
                    .password(passwordEncoder.encode("123123123"))
                    .userType(UserType.PART_TIME_MS)
                    .authorityType(AuthorityType.ROOT)
                    .activeStatus(ActiveStatus.ACTIVE)
                    .build());

            userRepository.save(User.builder()
                    .username("manager")
                    .password(passwordEncoder.encode("123123123"))
                    .userType(UserType.PART_TIME_MS)
                    .authorityType(AuthorityType.MANAGER)
                    .activeStatus(ActiveStatus.ACTIVE)
                    .build());

            userRepository.save(User.builder()
                    .username("manager2")
                    .password(passwordEncoder.encode("123123123"))
                    .userType(UserType.PART_TIME_MS)
                    .authorityType(AuthorityType.MANAGER)
                    .activeStatus(ActiveStatus.ACTIVE)
                    .build());

            userRepository.save(User.builder()
                    .username("general")
                    .password(passwordEncoder.encode("123123123"))
                    .userType(UserType.PART_TIME_MS)
                    .authorityType(AuthorityType.GENERAL)
                    .activeStatus(ActiveStatus.ACTIVE)
                    .build());

            userRepository.save(User.builder()
                    .username("general2")
                    .password(passwordEncoder.encode("123123123"))
                    .userType(UserType.PART_TIME_MS)
                    .authorityType(AuthorityType.GENERAL)
                    .activeStatus(ActiveStatus.ACTIVE)
                    .build());

            userRepository.save(User.builder()
                    .username("non_user")
                    .password(passwordEncoder.encode("123123123"))
                    .userType(UserType.PART_TIME_MS)
                    .authorityType(AuthorityType.NON_USER)
                    .activeStatus(ActiveStatus.ACTIVE)
                    .build());
            */
        };
    }
}
