package priv.ln.validation.spring.boot.autoconfigure;

import priv.ln.validation.spring.boot.core.CodeMessageInterpolator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 注册参数验证器
 * <p>自动配置条件:</p>
 * <p>存在ValidationConfiguration类型的bean</p>
 *
 * @author linnan
 * @version 2019/2/19
 * @see CodeMessageInterpolator
 */
@Configuration
@ConditionalOnBean(ValidationConfiguration.class)
public class ValidatorConfiguration implements WebMvcConfigurer {
    @Autowired
    @Qualifier("customValidator")
    private Validator validator;

    @Override
    public Validator getValidator() {
        return validator;
    }
}
