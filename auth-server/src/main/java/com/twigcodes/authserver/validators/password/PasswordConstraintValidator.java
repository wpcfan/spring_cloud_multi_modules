package com.twigcodes.authserver.validators.password;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.passay.*;

import java.util.Arrays;

@RequiredArgsConstructor
public class PasswordConstraintValidator implements ConstraintValidator<ValidPassword, String> {

    private final MessageResolver messageResolver;

    @Override
    public void initialize(final ValidPassword constraintAnnotation) {
    }

    @Override
    public boolean isValid(final String password, final ConstraintValidatorContext context) {
        val rules = Arrays.asList(
                // 长度规则：8 - 30 位
                new LengthRule(8, 30),
                // 至少有一个大写字母
                new CharacterRule(EnglishCharacterData.UpperCase, 1),
                // 至少有一个小写字母
                new CharacterRule(EnglishCharacterData.LowerCase, 1),
                // 至少有一个数字
                new CharacterRule(EnglishCharacterData.Digit, 1),
                // 至少有一个特殊字符
                new CharacterRule(EnglishCharacterData.Special, 1),
                // 不允许连续 3 个字母，按字母表顺序
                // alphabetical is of the form 'abcde', numerical is '34567', qwery is 'asdfg'
                // the false parameter indicates that wrapped sequences are allowed; e.g. 'xyzabc'
                new IllegalSequenceRule(EnglishSequenceData.Alphabetical, 5, false),
                // 不允许 3 个连续数字
                new IllegalSequenceRule(EnglishSequenceData.Numerical, 5, false),
                // 不允许 QWERTY 键盘上的三个连续相邻的按键所代表的字符
                new IllegalSequenceRule(EnglishSequenceData.USQwerty, 5, false),
                // 不允许包含空格
                new WhitespaceRule());

        val validator = new PasswordValidator(messageResolver, rules);
        val result = validator.validate(new PasswordData(password));
        if (result.isValid()) {
            return true;
        }
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(String.join(",", validator.getMessages(result)))
                .addConstraintViolation();
        return false;
    }
}