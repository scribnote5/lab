package kr.ac.univ.user.dto;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.domain.enums.AuthorityType;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class UserPrincipal implements UserDetails {
    private long idx;
    @Getter(value = AccessLevel.NONE)
    private String username;
    @Getter(value = AccessLevel.NONE)
    private String password;

    private String koreanName;
    private String englishName;

    private ActiveStatus activeStatus;
    private AuthorityType authorityType;

    public UserPrincipal(User user) {
        setIdx(user.getIdx());
        setUsername(user.getUsername());
        setPassword(user.getPassword());
        setKoreanName(user.getKoreanName());
        setEnglishName(user.getEnglishName());
        setActiveStatus(user.getActiveStatus());
        setAuthorityType(user.getAuthorityType());
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();

        switch (this.getAuthorityType()) {
            case ROOT:
                authorities.add(new SimpleGrantedAuthority(AuthorityType.ROOT.getAuthorityType()));
                break;
            case MANAGER:
                authorities.add(new SimpleGrantedAuthority(AuthorityType.MANAGER.getAuthorityType()));
                break;
            case GENERAL:
                authorities.add(new SimpleGrantedAuthority(AuthorityType.GENERAL.getAuthorityType()));
                break;
            default:
                authorities.add(new SimpleGrantedAuthority(AuthorityType.NON_USER.getAuthorityType()));
                break;
        }

        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.getAuthorityType() != AuthorityType.NON_USER;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.getActiveStatus() == ActiveStatus.ACTIVE;
    }
}
