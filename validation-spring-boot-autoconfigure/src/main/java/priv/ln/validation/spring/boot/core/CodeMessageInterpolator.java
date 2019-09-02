package priv.ln.validation.spring.boot.core;

import priv.ln.validation.spring.boot.config.ValidationProperties;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.StringSubstitutor;
import org.springframework.beans.factory.annotation.Autowired;

import javax.annotation.PostConstruct;
import javax.validation.MessageInterpolator;
import javax.validation.metadata.ConstraintDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.util.InvalidPropertiesFormatException;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

/**
 * code-message解析器
 *
 * @author linnan
 * @version 2019/2/19
 */
@Slf4j
public class CodeMessageInterpolator implements MessageInterpolator {
    @Autowired
    private CodeMessageTemplateProvider codeMessageTemplateProvider;
    @Autowired
    private ValidationProperties validationProperties;
    /**
     * 异常类型-错误码映射属性
     */
    private Properties codeProperties;

    @PostConstruct
    private void init() {
        // 解析异常类型-错误码映射属性文件
        try (InputStream resourceAsStream = this.getClass().getResourceAsStream(validationProperties.getCodeFileName())) {
            codeProperties = new Properties();
            codeProperties.load(resourceAsStream);
        } catch (InvalidPropertiesFormatException e) {
            log.warn("解析异常类型-错误码映射属性文件时出错", e);
            throw new RuntimeException(e);
        } catch (IOException e) {
            log.warn("加载异常类型-错误码映射属性文件时出错", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public String interpolate(String message, Context context) {
        return interpolate(message, context, Locale.CHINA);
    }

    @Override
    public String interpolate(String message, Context context, Locale locale) {
        // 获取校验未通过的约束描述符
        ConstraintDescriptor<?> constraintDescriptor = context.getConstraintDescriptor();
        // 获取校验未通过的注解类型
        Annotation annotation = constraintDescriptor.getAnnotation();
        // 根据注解类型获取错误码
        String messageCode = getMessageCode(annotation);
        // 根据错误码获取错误消息模板
        String messageTemplate = codeMessageTemplateProvider.getMessageTemplate(messageCode, locale);
        // 格式化错误消息
        Map<String, Object> attributes = constraintDescriptor.getAttributes();
        StringSubstitutor sub = new StringSubstitutor(attributes);
        String resolvedString = sub.replace(messageTemplate);
        return resolvedString;
    }

    /**
     * 根据校验未通过的注解类型 获取错误码
     *
     * @param annotation 注解类型
     * @return 错误码
     */
    private String getMessageCode(Annotation annotation) {
        // 从异常类型-错误码映射属性中获取错误码
        String name = annotation.annotationType().getName();
        return codeProperties.getProperty(name, name);
    }
}
