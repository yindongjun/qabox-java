package io.fluentqa.qabox.validator;

import cn.hutool.core.util.StrUtil;
import io.fluentqa.qabox.MatcherUtils;
import io.fluentqa.qabox.constant.RegexpConstant;
import io.fluentqa.qabox.validator.annotation.IsMobile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的手机号码
 *
 */
public class MobileValidator implements ConstraintValidator<IsMobile, String> {

    @Override
    public void initialize(IsMobile isMobile) {
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (StrUtil.isBlank(s)) {
                return true;
            } else {
                String regex = RegexpConstant.MOBILE_REG;
                return MatcherUtils.match(regex, s);
            }
        } catch (Exception e) {
            return false;
        }
    }
}
