package kr.ac.univ.setting.service;

import kr.ac.univ.setting.domain.Setting;
import kr.ac.univ.setting.dto.SettingDto;
import kr.ac.univ.setting.dto.mapper.SettingMapper;
import kr.ac.univ.setting.repository.SettingRepository;
import kr.ac.univ.setting.repository.SettingRepositoryImpl;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class SettingService {
    private final SettingRepository settingRepository;
    private final SettingRepositoryImpl settingRepositoryImpl;

    public SettingService(SettingRepository settingRepository, SettingRepositoryImpl settingRepositoryImpl) {
        this.settingRepository = settingRepository;
        this.settingRepositoryImpl = settingRepositoryImpl;
    }

    public SettingDto findSettingByIdx(Long idx) {
        return SettingMapper.INSTANCE.toDto(settingRepository.findById(idx).orElse(new Setting()));
    }

    @Transactional
    public Long updateSetting(Long idx, SettingDto settingDto) {
        Setting persistSetting = settingRepository.getOne(idx);
        Setting introduction = SettingMapper.INSTANCE.toEntity(settingDto);

        persistSetting.update(introduction);

        settingRepositoryImpl.updateViewsByIdx(settingDto.getIdx());
        settingDto.setViews(settingDto.getViews() + 1);

        return settingRepository.save(introduction).getIdx();
    }
}