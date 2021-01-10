package kr.ac.univ.controller;

import kr.ac.univ.category.dto.CategoryDto;
import kr.ac.univ.category.service.CategoryService;
import kr.ac.univ.common.dto.SearchDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // List
    @GetMapping("/list")
    public String categoryList(@PageableDefault Pageable pageable, SearchDto searchDto, Model model) {
        model.addAttribute("categoryDtoList", categoryService.findCategoryList(pageable, searchDto));

        return "category/list";
    }

    // Form Update
    @GetMapping("/form{idx}")
    public String categoryForm(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        CategoryDto categoryDto = categoryService.findCategoryByIdx(idx);
        String returnPage = null;

        // 권한 확인
        if (categoryDto.isAccess()) {
            model.addAttribute("categoryDto", categoryDto);

            returnPage = "category/form";
        } else {
            returnPage = "user/access-denied";
        }

        return returnPage;
    }

    // Read
    @GetMapping({"", "/"})
    public String categoryRead(@RequestParam(value = "idx", defaultValue = "0") Long idx, Model model) {
        CategoryDto categoryDto = categoryService.findCategoryByIdx(idx);

        model.addAttribute("categoryDto", categoryDto);

        return "category/read";
    }
}