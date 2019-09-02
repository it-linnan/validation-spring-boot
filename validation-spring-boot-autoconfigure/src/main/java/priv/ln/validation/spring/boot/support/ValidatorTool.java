package priv.ln.validation.spring.boot.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;


/**
 * 参数验证工具
 * <p>用于不能通过spring拦截的场景手动校验</p>
 *
 * @author linnan
 * @version 2019/2/20
 */
public class ValidatorTool {
    @Autowired
    @Qualifier("customValidator")
    private Validator validator;

    public <T> Errors validate(T t) {
        Errors errors = new BeanPropertyBindingResult(t, t.getClass().getName());
        validator.validate(t, errors);
        return errors;
    }

    public <T> String getFirstMessage(T t) {
        Errors errors = validate(t);
        return ValidatorUtil.getFirstMessage(errors);
    }
}
