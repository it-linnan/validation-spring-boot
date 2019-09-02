package priv.ln.validation.spring.boot.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 参数验证配置属性
 *
 * @author linnan
 * @version 2019/2/19
 */
@Data
@ConfigurationProperties("validation")
public class ValidationProperties {
    /**
     * 异常类型-错误码映射属性文件
     */
    private String codeFileName = "/validation/code.properties";
    /**
     * 校验是否是快速失败模式（遇到第一个异常就返回）
     */
    private boolean fastFailMode = true;
}
