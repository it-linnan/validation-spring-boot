package priv.ln.validation.spring.boot.autoconfigure;

import priv.ln.validation.spring.boot.config.ValidationProperties;
import priv.ln.validation.spring.boot.core.CodeMessageInterpolator;
import priv.ln.validation.spring.boot.core.CodeMessageTemplateProvider;
import priv.ln.validation.spring.boot.support.ValidatorTool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.Validator;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.HashMap;
import java.util.Map;

/**
 * 参数验证自动配置
 * <p>自动配置条件:</p>
 * <p>存在CodeMessageTemplateProvider类型的bean</p>
 * <p>存在WebMvcConfigurer类</p>
 *
 * @author linnan
 * @version 2019/2/19
 * @see CodeMessageTemplateProvider
 * @see ValidatorConfiguration
 */
@Configuration
@EnableConfigurationProperties
@ConditionalOnBean(CodeMessageTemplateProvider.class)
@ConditionalOnClass(WebMvcConfigurer.class)
public class ValidationConfiguration extends WebMvcConfigurationSupport {

    @Bean
    public ValidationProperties validationProperties() {
        return new ValidationProperties();
    }

    @Bean
    public CodeMessageInterpolator codeMessageInterpolator() {
        return new CodeMessageInterpolator();
    }

    @Bean
    public ValidatorTool validatorTool() {
        return new ValidatorTool();
    }

    @Bean("customValidator")
    public Validator validator(ValidationProperties validationProperties, CodeMessageInterpolator codeMessageInterpolator) {
        Map<String, String> validationPropertyMap = new HashMap<>();
        // 设置快速失败模式
        validationPropertyMap.put("failFast", String.valueOf(validationProperties.isFastFailMode()));
        LocalValidatorFactoryBean factory = new LocalValidatorFactoryBean();
        // 注册自定义错误消息解析器
        factory.setMessageInterpolator(codeMessageInterpolator);
        factory.setValidationPropertyMap(validationPropertyMap);
        return factory;
    }
}