package kr.ac.univ.email.service;

import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.email.domain.Email;
import kr.ac.univ.email.dto.EmailDto;
import kr.ac.univ.email.dto.mapper.EmailMapper;
import kr.ac.univ.email.repository.EmailRepository;
import kr.ac.univ.email.repository.EmailRepositoryImpl;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.EmptyUtil;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class EmailService {
    private final EmailRepository emailRepository;
    private final EmailRepositoryImpl emailRepositoryImpl;
    private final UserRepository userRepository;

    public EmailService(EmailRepository emailRepository, EmailRepositoryImpl emailRepositoryImpl, UserRepository userRepository) {
        this.emailRepository = emailRepository;
        this.emailRepositoryImpl = emailRepositoryImpl;
        this.userRepository = userRepository;
    }

    public Page<EmailDto> findEmailList(Pageable pageable, SearchDto searchDto) {
        Page<Email> emailList = null;
        Page<EmailDto> emailDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "EMAIL_ADDRESS":
                emailList = emailRepository.findAllByEmailAddressContaining(pageable, searchDto.getKeyword());
                break;
            case "NOTE":
                emailList = emailRepository.findAllByNoteContaining(pageable, searchDto.getKeyword());
                break;
            case "ID":
                emailList = emailRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                break;
            default:
                emailList = emailRepository.findAll(pageable);
                break;
        }

        emailDtoList = new PageImpl<>(EmailMapper.INSTANCE.toDto(emailList.getContent()), pageable, emailList.getTotalElements());

        // NewIcon 판별
        for (EmailDto emailDto : emailDtoList) {
            emailDto.setNewIcon(NewIconCheck.isNew(emailDto.getCreatedDate()));
        }

        return emailDtoList;
    }

    public Long insertEmail(EmailDto emailDto) {

        return emailRepository.save(EmailMapper.INSTANCE.toEntity(emailDto)).getIdx();
    }

    public EmailDto findEmailByIdx(Long idx) {
        EmailDto emailDto = EmailMapper.INSTANCE.toDto(emailRepository.findById(idx).orElse(new Email()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            emailDto.setAccess(true);
        }
        // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
        // 탈퇴 회원은 권한을 general로 설정 후 권한을 검사함
        else {
            User user = userRepository.findByUsername(emailDto.getCreatedBy());

            emailDto.setAccess(AccessCheck.isAccessInGeneral(emailDto.getCreatedBy(), EmptyUtil.isEmpty(user) ? "general" : user.getAuthorityType().getAuthorityType()));
        }

        emailRepositoryImpl.updateViewsByIdx(idx);
        emailDto.setViews(emailDto.getViews() + 1);

        return emailDto;
    }

    @Transactional
    public Long updateEmail(Long idx, EmailDto emailDto) {
        Email persistEmail = emailRepository.getOne(idx);
        Email email = EmailMapper.INSTANCE.toEntity(emailDto);

        persistEmail.update(email);

        return emailRepository.save(persistEmail).getIdx();
    }

    public void deleteEmailByIdx(Long idx) {
        emailRepository.deleteById(idx);
    }
}