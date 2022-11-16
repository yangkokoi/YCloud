package cn.xiaosm.cloud.core.util.mail;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.core.util.StrUtil;
import cn.xiaosm.cloud.common.exception.CanShowException;
import cn.xiaosm.cloud.core.entity.vo.MailVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.UnsupportedEncodingException;

/**
 * 邮件发送工具类
 *
 * @author Young
 * @create 2020/6/28
 * @since 1.0.0
 */
@Slf4j
@Component
public class MailUtils {

    private static String FROM_ADDRESS = "";
    private static String FROM_NAME = "YAdmin";

    private static JavaMailSenderImpl javaMailSender;
    private static MailConfig mailConfig;

    @Autowired
    public MailUtils(JavaMailSenderImpl javaMailSender, MailConfig mailConfig) {
        this.javaMailSender = javaMailSender;
        this.mailConfig = mailConfig;
        // updateMail();
    }

    /**
     * 公开的邮件发送方法
     * @param mailVO
     */
    public static void sendMail(MailVO mailVO) {
        if (StrUtil.isNotBlank(mailVO.getTemplate())) {
            String content = ResourceUtil.readUtf8Str("/templates/" + mailVO.getTemplate());
            mailVO.setContent(content);
            mailVO.setIsHtml(true);
            log.error("读取模板出现错误");
        }
        MailUtils.sendMail(mailVO.getTo(), mailVO.getSubject(), mailVO.getContent(), mailVO.getIsHtml());
    }

    /**
     * 公开的邮件发送方法
     * @param to
     * @param subject
     * @param content
     */
    public static void sendMail(String to, String subject, String content) {
        MailUtils.sendMail(to, subject, content, false);
    }

    /**
     * 发送普通邮件
     *
     * @param to      收件人
     * @param subject 主题
     * @param content 内容
     * @param isHtml 是否以html形式发送
     */
    public static void sendMail(String to, String subject, String content, boolean isHtml) {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        try {
            helper.setFrom(FROM_ADDRESS, FROM_NAME);
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(content, isHtml);
            javaMailSender.send(message);
            log.info("邮件发送成功，接收方=>[{}]", to);
        } catch (MessagingException | UnsupportedEncodingException e) {
            log.error("发送简单邮件时出现异常!", e);
            throw new CanShowException("邮件发送出现错误###详情请查看异常日志信息");
            // ResponseUtils.sendError();
        }
    }

    /**
     * 依赖注入完成后进行 JavaMailSenderImpl 更新
     */
    public static void updateMail() {
        mailConfig.updateMail();
    }

    public static void testConnection() {
        try {
            javaMailSender.testConnection();
        } catch (MessagingException e) {
            e.printStackTrace();
            log.error("邮箱连接失败，请检查配置是否正确");
        }
    }

    public static void setFromAddress(String fromAddress) {
        MailUtils.FROM_ADDRESS = fromAddress;
    }

    public static void setFromName(String fromName) {
        MailUtils.FROM_NAME = fromName;
    }
}