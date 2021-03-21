package kr.ac.univ.maintenance.service;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.email.domain.enums.ReceiverType;
import kr.ac.univ.email.repository.EmailRepositoryImpl;
import kr.ac.univ.maintenance.domain.MaintenanceComment;
import kr.ac.univ.maintenance.dto.MaintenanceCommentDto;
import kr.ac.univ.maintenance.dto.mapper.MaintenanceCommentMapper;
import kr.ac.univ.maintenance.repository.MaintenanceCommentRepository;
import kr.ac.univ.maintenance.repository.MaintenanceCommentRepositoryImpl;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.EmailUtil;
import kr.ac.univ.util.EmptyUtil;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaintenanceCommentService {
    @Value("${email.sender}")
    private String sender;
    @Value("${email.password}")
    private String password;
    @Value("${title}")
    private String title;
    @Value("${module-app-admin.address}")
    private String moduleAppAdminAddress;

    private final MaintenanceCommentRepository maintenanceCommentRepository;
    private final MaintenanceCommentRepositoryImpl maintenanceCommentRepositoryImpl;
    private final UserRepository userRepository;
    private final EmailRepositoryImpl emailRepositoryImpl;

    public MaintenanceCommentService(MaintenanceCommentRepository maintenanceCommentRepository, MaintenanceCommentRepositoryImpl maintenanceCommentRepositoryImpl, UserRepository userRepository, EmailRepositoryImpl emailRepositoryImpl) {
        this.maintenanceCommentRepository = maintenanceCommentRepository;
        this.maintenanceCommentRepositoryImpl = maintenanceCommentRepositoryImpl;
        this.userRepository = userRepository;
        this.emailRepositoryImpl = emailRepositoryImpl;
    }

    public List<MaintenanceCommentDto> findAllByMaintenanceIdxOrderByCreatedDateDesc(Long maintenanceIdx) {
        List<MaintenanceCommentDto> maintenanceCommentDtoList = null;

        maintenanceCommentDtoList = MaintenanceCommentMapper.INSTANCE.toDto(maintenanceCommentRepository.findAllByMaintenanceIdxOrderByCreatedDateDesc(maintenanceIdx));

        for (MaintenanceCommentDto maintenanceCommentDto : maintenanceCommentDtoList) {
            // NewIcon 판별
            maintenanceCommentDto.setNewIcon(NewIconCheck.isNew(maintenanceCommentDto.getCreatedDate()));

            // 권한 설정
            // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
            maintenanceCommentDto.setAccess(AccessCheck.isAccessInGeneral(maintenanceCommentDto.getCreatedBy(), userRepository.findByUsername(maintenanceCommentDto.getCreatedBy()).getAuthorityType().name()));
        }

        return maintenanceCommentDtoList;
    }

    public Long insertMaintenanceComment(MaintenanceCommentDto maintenanceCommentDto) {
        Long idx = maintenanceCommentRepository.save(MaintenanceCommentMapper.INSTANCE.toEntity(maintenanceCommentDto)).getIdx();

        List<String> emailAddressArray = new ArrayList<>();
        String mailTitle = "'" + title + "' new maintenance comment is registered.";
        String mailContent = "<span style=\"font-size: 18px; color: black;\">The new maintenance comment is registered.</span><br><br>"
                + "<b>Created By: <b>" + maintenanceCommentDto.getCreatedBy() + "<br>"
                + "You can check <a href=\"" + moduleAppAdminAddress + "/maintenance?idx=" + maintenanceCommentDto.getMaintenanceIdx() + "\">" + moduleAppAdminAddress + "/maintenance?idx=" + maintenanceCommentDto.getMaintenanceIdx() +
                "<a><br><br>"
                + "Thank you.";

        switch (maintenanceCommentDto.getReceiverType()) {
            case DEVELOPER:
                emailAddressArray = emailRepositoryImpl.findEmailByReceiverTypeAndActiveStatus(ReceiverType.DEVELOPER, ActiveStatus.ACTIVE);
                break;
            case ADMIN:
                emailAddressArray = emailRepositoryImpl.findEmailByReceiverTypeAndActiveStatus(ReceiverType.ADMIN, ActiveStatus.ACTIVE);
                break;
            case ALL:
                emailAddressArray = emailRepositoryImpl.findAllByActiveStatus(ActiveStatus.ACTIVE);
                break;
            default:
                // case None:
                break;
        }

        if (!EmptyUtil.isEmpty(emailAddressArray)) {
            EmailUtil.gmailSend(sender, password, emailAddressArray, mailTitle, mailContent);
        }

        return idx;
    }

    @Transactional
    public Long updateMaintenanceComment(Long idx, MaintenanceCommentDto maintenanceCommentDto) {
        MaintenanceComment persistMaintenanceComment = maintenanceCommentRepository.getOne(idx);
        MaintenanceComment maintenanceComment = MaintenanceCommentMapper.INSTANCE.toEntity(maintenanceCommentDto);

        persistMaintenanceComment.update(maintenanceComment);

        return maintenanceCommentRepository.save(persistMaintenanceComment).getIdx();
    }

    public void deleteMaintenanceCommentByIdx(Long idx) {
        maintenanceCommentRepository.deleteById(idx);
    }

    public void deleteAllByMaintenanceIdx(Long idx) {
        maintenanceCommentRepositoryImpl.deleteAllByMaintenanceIdx(idx);
    }

}