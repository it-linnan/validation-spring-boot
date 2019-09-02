package priv.ln.validation.spring.boot.support;

import org.springframework.validation.Errors;

/**
 * 参数验证公共方法
 *
 * @author linnan
 * @version 2019/2/20
 */
public class ValidatorUtil {

    /**
     * 获取第一个错误信息
     *
     * @param errors
     * @return
     */
    public static String getFirstMessage(Errors errors) {
        String message = "";
        if (errors != null && errors.getAllErrors() != null && errors.getAllErrors().size() > 0) {
            message = errors.getAllErrors().get(0).getDefaultMessage();
        }
        return message;
    }
}
