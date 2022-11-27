package io.fluentqa.qabox.validator;

import io.qdriven.qaops.supplement.validator.annotation.IsCron;
import org.quartz.CronExpression;


import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * 校验是否为合法的 Cron表达式
 * TODO: add CronExpressSimple
 */
public class CronValidator implements ConstraintValidator<IsCron, String> {

    @Override
    public void initialize(IsCron isCron) {
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        try {
            return CronExpression.isValidExpression(value);
        } catch (Exception e) {
            return false;
        }
    }
}