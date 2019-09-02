package priv.ln.validation.spring.boot.core;

import java.util.Locale;

/**
 * 消息模板提供者
 *
 * @author linnan
 * @version 2019/2/19
 */
public interface CodeMessageTemplateProvider {

    /**
     * 根据错误码查找错误消息模板
     *
     * @param code   错误码
     * @param locale 地区（用于国际化）
     * @return 错误消息模板
     */
    String getMessageTemplate(String code, Locale locale);
}
