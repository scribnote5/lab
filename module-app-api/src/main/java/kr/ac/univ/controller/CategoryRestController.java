package kr.ac.univ.controller;

import kr.ac.univ.category.dto.CategoryDto;
import kr.ac.univ.category.service.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryRestController {
    private final CategoryService categoryService;

    public CategoryRestController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping
    public ResponseEntity<?> postCategory(@RequestBody @Valid CategoryDto categoryDto) {
        Long idx = categoryService.insertCategory(categoryDto);

        return new ResponseEntity<>(idx, HttpStatus.CREATED);
    }

    @PutMapping("/{idx}")
    public ResponseEntity<?> putCategory(@PathVariable("idx") Long idx, @RequestBody @Valid CategoryDto categoryDto) {
        categoryService.updateCategory(idx, categoryDto);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }

    @DeleteMapping("/{idx}")
    public ResponseEntity<?> deleteCategory(@PathVariable("idx") Long idx) throws Exception {
        categoryService.deleteCategoryByIdx(idx);

        return new ResponseEntity<>("{}", HttpStatus.OK);
    }
}