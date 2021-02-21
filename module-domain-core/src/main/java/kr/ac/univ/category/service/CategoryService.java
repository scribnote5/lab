package kr.ac.univ.category.service;

import kr.ac.univ.category.domain.Category;
import kr.ac.univ.category.dto.CategoryDto;
import kr.ac.univ.category.dto.mapper.CategoryMapper;
import kr.ac.univ.category.repository.CategoryRepository;
import kr.ac.univ.category.repository.CategoryRepositoryImpl;
import kr.ac.univ.common.domain.enums.ActiveStatus;
import kr.ac.univ.common.dto.SearchDto;
import kr.ac.univ.user.repository.UserRepository;
import kr.ac.univ.util.AccessCheck;
import kr.ac.univ.util.NewIconCheck;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CategoryService {
    private final CategoryRepository categoryRepository;
    private final CategoryRepositoryImpl categoryRepositoryImpl;
    private final UserRepository userRepository;

    public CategoryService(CategoryRepository categoryRepository, CategoryRepositoryImpl categoryRepositoryImpl, UserRepository userRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryRepositoryImpl = categoryRepositoryImpl;
        this.userRepository = userRepository;
    }

    public Page<CategoryDto> findCategoryList(Pageable pageable, SearchDto searchDto) {
        Page<Category> categoryList = null;
        Page<CategoryDto> categoryDtoList = null;

        pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1, pageable.getPageSize(), Sort.Direction.DESC, "idx");

        switch (searchDto.getSearchType()) {
            case "TITLE":
                categoryList = categoryRepository.findAllByTitleContaining(pageable, searchDto.getKeyword());
                break;
            case "ID":
                categoryList = categoryRepository.findAllByCreatedByContaining(pageable, searchDto.getKeyword());
                break;
            default:
                categoryList = categoryRepository.findAll(pageable);
                break;
        }

        categoryDtoList = new PageImpl<>(CategoryMapper.INSTANCE.toDto(categoryList.getContent()), pageable, categoryList.getTotalElements());

        // NewIcon 판별
        for (CategoryDto categoryDto : categoryDtoList) {
            categoryDto.setNewIcon(NewIconCheck.isNew(categoryDto.getCreatedDate()));
        }

        return categoryDtoList;
    }

    public List<CategoryDto> findCategoryListByActiveStatusIs() {
        return CategoryMapper.INSTANCE.toDto(categoryRepository.findAllByActiveStatusIs(ActiveStatus.ACTIVE));
    }

    public Long insertCategory(CategoryDto categoryDto) {
        return categoryRepository.save(CategoryMapper.INSTANCE.toEntity(categoryDto)).getIdx();
    }

    public CategoryDto findCategoryByIdx(Long idx) {
        CategoryDto categoryDto = CategoryMapper.INSTANCE.toDto(categoryRepository.findById(idx).orElse(new Category()));

        // 권한 설정
        // Register: 로그인한 사용자 Register 접근 가능
        if (idx == 0) {
            categoryDto.setAccess(true);
        }
        // Update: isAccess 메소드에 따라 접근 가능 및 불가
        // Update: isAccessInGeneral 메소드에 따라 접근 가능 및 불가
        else {
            categoryDto.setAccess(AccessCheck.isAccessInGeneral(categoryDto.getCreatedBy(), userRepository.findByUsername(categoryDto.getCreatedBy()).getAuthorityType().name()));
        }

        categoryRepositoryImpl.updateViewsByIdx(idx);
        categoryDto.setViews(categoryDto.getViews() + 1);

        return categoryDto;
    }

    public CategoryDto findCategoryByIdxInActive(Long idx) {
        return CategoryMapper.INSTANCE.toDto(categoryRepository.findByIdxAndActiveStatusIs(idx, ActiveStatus.ACTIVE));
    }

    @Transactional
    public Long updateCategory(Long idx, CategoryDto categoryDto) {
        Category persistCategory = categoryRepository.getOne(idx);
        Category category = CategoryMapper.INSTANCE.toEntity(categoryDto);

        persistCategory.update(category);

        return categoryRepository.save(persistCategory).getIdx();
    }

    public void deleteCategoryByIdx(Long idx) {
        categoryRepository.deleteById(idx);
    }
}