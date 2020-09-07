package kr.ac.univ.common.validation;

import kr.ac.univ.util.ByteSizeUtil;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EditorValidator implements ConstraintValidator<Editor, String> {
    long max;

    @Override
    public void initialize(Editor editor) {
        max = editor.max();
    }

    @Override
    public boolean isValid(String str, ConstraintValidatorContext cxt) {
        return ByteSizeUtil.getByteSize(str) < max;
    }
}