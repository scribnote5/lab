package kr.ac.univ.maintenance.service;

import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.email.domain.enums.ReceiverType;
import kr.ac.univ.email.repository.EmailRepository;
import kr.ac.univ.email.repository.EmailRepositoryImpl;
import kr.ac.univ.maintenance.domain.Maintenance;
import kr.ac.univ.maintenance.dto.MaintenanceDto;
import kr.ac.univ.maintenance.dto.mapper.MaintenanceMapper;
import kr.ac.univ.maintenance.repository.MaintenanceRepository;
import kr.ac.univ.maintenance.repository.MaintenanceRepositoryImpl;
import kr.ac.univ.user.domain.User;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.EmailUtil;
import kr.ac.univ.util.EmptyUtil;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class MaintenanceService {
    @Value("${email.sender}")
    private String sender;
    @Value("${email.password}")
    private String password;
    @Value("${title}")
    private String title;
    @Value("${module-app-admin.address}")
    private String moduleAppAdminAddress;

    private final MaintenanceRepository maintenanceRepository;
    private final MaintenanceRepositoryImpl maintenanceRepositoryImpl;
    private final UserRepository userRepository;
    private final EmailRepositoryImpl emailRepositoryImpl;

    public MaintenanceService(MaintenanceRepository maintenanceRepository, MaintenanceRepositoryImpl maintenanceRepositoryImpl, UserRepository userRepository, EmailRepository emailRepository, EmailRepositoryImpl emailRepositoryImpl) {
        this.maintenanceRepository = maintenanceRepository;
        this.maintenanceRepositoryImpl = maintenanceRepositoryImpl;
        this.userRepository = userRepository;
        this.emailRepositoryImpl = emailRepositoryImpl;
    }

    public Page<MaintenanceDto> findMaintenanceList(Pageable pageable, SearchDto searchDto) {
        Page<Maintenance> maintenanceList = null;
        Page<MaintenanceDto> maintenanceDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                maintenanceList = maintenanceRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                break;
            case "CONTENT":
                maintenanceList = maintenanceRepository.findAllByContentContaining(pageable, searchDto.getKeyword());
                break;
            case "ID":
                maintenanceList = maintenanceRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                break;
            default:
                maintenanceList = maintenanceRepository.findAll(pageable);
                break;
        }

        maintenanceDtoList = new PageImpl<>(MaintenanceMapper.INSTANCE.toDto(maintenanceList.getContent()), pageable, maintenanceList.getTotalElements());

        // NewIcon 판별
        for (MaintenanceDto maintenanceDto : maintenanceDtoList) {
            maintenanceDto.setNewIcon(NewIconCheck.isNew(maintenanceDto.getCreatedDate()));
        }

        return maintenanceDtoList;
    }

    public Long insertMaintenance(MaintenanceDto maintenanceDto) {
        Long idx = maintenanceRepository.save(MaintenanceMapper.INSTANCE.toEntity(maintenanceDto)).getIdx();

        List<String> emailAddressArray = new ArrayList<>();
        String mailTitle = "'" + title + "' new maintenance is registered.";
        String mailContent = "<span style=\"font-size: 18px; color: black;\">The new maintenance is registered.</span><br><br>"
                + "<b>Title : <b>" + maintenanceDto.getTitle() + "<br>"
                + "<b>Created By: <b>" + maintenanceDto.getCreatedBy() + "<br>"
                + "<b>Maintenance Status: <b>" + maintenanceDto.getMaintenanceStatus() + "<br>"
                + "You can check <a href=\"" + moduleAppAdminAddress + "/maintenance?idx=" + idx + "\">" + moduleAppAdminAddress + "/maintenance?idx=" + idx + "<a><br><br>"
                + "Thank you.";

        switch (maintenanceDto.getReceiverType()) {
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

    public MaintenanceDto findMaintenanceByIdx(Long idx) {
        MaintenanceDto maintenanceDto = MaintenanceMapper.INSTANCE.toDto(maintenanceRepository.findById(idx).orElse(new Maintenance()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            maintenanceDto.setAccess(true);
        }
        // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
        // 탈퇴 회원은 권한을 general로 설정 후 권한을 검사함
        else {
            User user = userRepository.findByUsername(maintenanceDto.getCreatedBy());

            maintenanceDto.setAccess(AccessCheck.isAccessInGeneral(maintenanceDto.getCreatedBy(),  EmptyUtil.isEmpty(user) ? "general" : user.getAuthorityType().getAuthorityType()));
        }

        maintenanceRepositoryImpl.updateViewsByIdx(idx);
        maintenanceDto.setViews(maintenanceDto.getViews() + 1);

        return maintenanceDto;
    }

    @Transactional
    public Long updateMaintenance(Long idx, MaintenanceDto maintenanceDto) {
        Maintenance persistMaintenance = maintenanceRepository.getOne(idx);
        Maintenance maintenance = MaintenanceMapper.INSTANCE.toEntity(maintenanceDto);

        persistMaintenance.update(maintenance);

        return maintenanceRepository.save(persistMaintenance).getIdx();
    }

    public void deleteMaintenanceByIdx(Long idx) {
        maintenanceRepository.deleteById(idx);
    }
}