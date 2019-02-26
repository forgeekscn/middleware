package cn.forgeeks.awesome.springboot.mail.common;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

@ToString
@Data
public class MailBean implements Serializable {
    private String recipient;   //邮件接收人
    private String subject; //邮件主题
    private String content; //邮件内容

}
