package com.primihub.biz.service.sys;

import com.alibaba.fastjson.JSONObject;
import com.primihub.biz.config.base.BaseConfiguration;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.mail.MessagingException;
import java.util.Date;
import java.util.Map;
import java.util.Properties;
import java.util.UUID;

@Slf4j
@Service
public class SysEmailService {
    @Autowired
    private BaseConfiguration baseConfiguration;

    private JavaMailSender javaMailSender;


    @PostConstruct
    public void init(){
        javaMailSender = initMailSender(baseConfiguration);
    }
    private JavaMailSender initMailSender(BaseConfiguration baseConfiguration){
        MailProperties mail = baseConfiguration.getMailProperties();
        if (mail!=null && StringUtils.isNotBlank(mail.getHost())
                && StringUtils.isNotBlank(mail.getUsername()) && StringUtils.isNotBlank(mail.getPassword())){
            log.info(JSONObject.toJSONString(mail));
            JavaMailSenderImpl javaMailSender = mailSender(mail);
            try {
                log.info("Start trying to connect to mailbox : {}",System.currentTimeMillis());
                javaMailSender.testConnection();
                log.info("End attempting to connect to mailbox : {}",System.currentTimeMillis());
            }
            catch (MessagingException ex) {
                throw new IllegalStateException("Mail server is not available", ex);
            }
            return javaMailSender;
        }
        return null;
    }

    private JavaMailSenderImpl mailSender(MailProperties properties) {
        JavaMailSenderImpl sender = new JavaMailSenderImpl();
        applyProperties(properties, sender);
        return sender;
    }

    private void applyProperties(MailProperties properties, JavaMailSenderImpl sender) {
        sender.setHost(properties.getHost());
        if (properties.getPort() != null) {
            sender.setPort(properties.getPort());
        }
        sender.setUsername(properties.getUsername());
        sender.setPassword(properties.getPassword());
        sender.setProtocol(properties.getProtocol());
        if (properties.getDefaultEncoding() != null) {
            sender.setDefaultEncoding(properties.getDefaultEncoding().name());
        }
        if (!properties.getProperties().isEmpty()) {
            sender.setJavaMailProperties(asProperties(properties.getProperties()));
        }
    }

    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }

    /**
     *
     * @param userAccount   a@a.com,b@b.com,......
     * @param subject       email subject
     * @param text          email Text content
     */
    public void send(String userAccount,String subject,String text){
        if (javaMailSender == null){
            log.info("The system does not configure mail");
            return;
        }
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            //???????????????
            message.setFrom(baseConfiguration.getMailProperties().getUsername());
            //??????????????? 1?????????
            message.setTo(userAccount.split(","));
            //????????????
            message.setSubject(subject);
            //????????????
            message.setText(text);
            //??????????????????
            message.setSentDate(new Date());
            javaMailSender.send(message);
            log.info("??????:{} ????????????",userAccount);
        } catch (MailException e) {
            log.info("??????:{} ???????????? e:{}",userAccount,e.getMessage());
        }
    }


}
