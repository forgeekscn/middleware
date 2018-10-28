package cn.forgeeks.springcloud.fastDFS.controller;

import cn.forgeeks.springcloud.fastDFS.fastdfs.FastDFSClient;
import cn.forgeeks.springcloud.fastDFS.fastdfs.FastDFSFile;
import cn.forgeeks.springcloud.mail.common.MailBean;
import cn.forgeeks.springcloud.mail.common.MailUtil;
import cn.forgeeks.springcloud.memcached.common.MemcachedRunner;
import net.spy.memcached.MemcachedClient;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.InputStream;

@RestController
public class UploadController {
    private static Logger log = LoggerFactory.getLogger(UploadController.class);

    @Autowired
    MemcachedRunner memcachedRunner;

    @Autowired
    MailUtil mailUtil;

    @GetMapping("/")
    public String index() {
        return "upload";
    }

    @PostMapping("/upload") //new annotation since 4.3
    public String singleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {
        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "Please select a file to upload");
            return "redirect:uploadStatus";
        }
        try {
            // Get the file and save it somewhere
            String path=saveFile(file);
            redirectAttributes.addFlashAttribute("message",
                    "You successfully uploaded '" + file.getOriginalFilename() + "'");
            redirectAttributes.addFlashAttribute("path",
                    "file path url '" + path + "'");
        } catch (Exception e) {
            log.error("upload file failed",e);
        }
        return "redirect:/uploadStatus";
    }

    @GetMapping("/uploadStatus")
    public String uploadStatus() {
        return "uploadStatus";
    }

    /**
     * @param multipartFile
     * @return
     * @throws IOException
     */
    public String saveFile(MultipartFile multipartFile) throws IOException {
        String[] fileAbsolutePath={};
        String fileName=multipartFile.getOriginalFilename();
        String ext = fileName.substring(fileName.lastIndexOf(".") + 1);
        byte[] file_buff = null;
        InputStream inputStream=multipartFile.getInputStream();
        if(inputStream!=null){
            int len1 = inputStream.available();
            file_buff = new byte[len1];
            inputStream.read(file_buff);
        }
        inputStream.close();
        FastDFSFile file = new FastDFSFile(fileName, file_buff, ext);
        try {
            fileAbsolutePath = FastDFSClient.upload(file);  //upload to fastdfs
        } catch (Exception e) {
            log.error("upload file Exception!",e);
        }
        if (fileAbsolutePath==null) {
            log.error("upload file failed,please upload again!");
        }
        String path=FastDFSClient.getTrackerUrl()+fileAbsolutePath[0]+ "/"+fileAbsolutePath[1];
        return path;
    }

    @PostMapping("/testmail")
    public void testMail(){
        MailBean mailBean = new MailBean();
        mailBean.setSubject("Have a nice try on the link below.");
        mailBean.setContent("http://fuckqq.com");
        mailBean.setRecipient("forgeekscn@gmail.com");

        mailUtil.sendSimpleMail(mailBean);
        mailUtil.sendAttachmentMail(mailBean);
        mailUtil.sendHTMLMail(mailBean);
        mailUtil.sendInlineMail(mailBean);
        mailUtil.sendTemplateMail(mailBean);

    }

    @PostMapping("/testMemcached")
    public void testMemcached(){
        MemcachedClient memcachedClient = memcachedRunner.getClient();
        memcachedClient.set("testkey", 1000, "666666");
        log.info("***********  [{}]" , memcachedClient.get("testkey").toString());
    }




}