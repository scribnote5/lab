package kr.ac.univ.user.dto;

import kr.ac.univ.user.dto.enums.UserSearchType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserSearchDto {
    private String searchType = "";
    private String keyword = "";
    private UserSearchType userSearchType = UserSearchType.A_SHOW_ALL;
}