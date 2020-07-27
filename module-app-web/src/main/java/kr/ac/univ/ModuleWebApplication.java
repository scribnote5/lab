package kr.ac.univ;

import kr.ac.univ.noticeBoard.repository.NoticeBoardRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ModuleWebApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModuleWebApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(NoticeBoardRepository noticeBoardRepository) {
        return (args) -> {
            /* 기존 데이터 모두 삭제 */
            /* noticeBoardRepository.deleteAll(); */

            /* 새로운 데이터 생성 */
            /*
            IntStream.rangeClosed(1, 200).forEach(index ->
                    noticeBoardRepository.save(NoticeBoard.builder()
                            .title("게시글" + index)
                            .content("컨텐츠" + index)
                            .viewCount(0L)
                            .activeStatus(ActiveStatus.ACTIVE)
                            .build()));
            */
        };
    }
}
