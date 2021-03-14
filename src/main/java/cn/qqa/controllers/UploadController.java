package cn.qqa.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 *
 * 上传：
 *      会将文件上传到3个地方
 *          1.项目路径（使用项目小的情况，上传使用率低，容易丢失）
 *          2.磁盘路径（需要通过虚拟目录的映射）
 *          3.上传到静态资源服务器（CDN）
 */
@Controller
public class UploadController {
    /**
     * 基于springmvc MultipartResolver 的单文件上传
     * @param desc
     * @param myfile
     * @return
     * @throws IOException
     */
    @PostMapping("/upload01")
    public String upload01(String desc, MultipartFile myfile, Model model) throws IOException {
        System.out.println(myfile.getOriginalFilename());
        String path = "D:\\Youku Files\\"+myfile.getOriginalFilename();
        File file = new File(path);
        myfile.transferTo(file);
        model.addAttribute("filename", myfile.getOriginalFilename());
        return  "success";
    }

    /**
     * 基于springmvc MultipartResolver 的多文件上传
     * @param desc
     * @param myfile
     * @return
     * @throws IOException
     */
    @PostMapping("/upload02")
    public String upload02(String desc, MultipartFile [] myfile) throws IOException {
        System.out.println(desc);
        for(MultipartFile multipartFile:myfile){
            System.out.println(multipartFile.getOriginalFilename());
            String path = "D:\\Youku Files\\"+multipartFile.getOriginalFilename();
            File file = new File(path);
            multipartFile.transferTo(file);
        }
        return  "success";
    }

    /**
     * 基于springmvc MultipartResolver 的多文件上传--多线程
     * 多线程上传
     * @param desc
     * @param myfile
     * @return
     * @throws IOException
     */
    @PostMapping("/upload03")
    public String upload03(String desc, MultipartFile [] myfile) throws IOException, InterruptedException {
        System.out.println(desc);
        for(MultipartFile multipartFile:myfile){
            //声明线程
            Thread thread = new Thread(() -> {
                System.out.println(multipartFile.getOriginalFilename());
                String path = "D:\\Youku Files\\" + multipartFile.getOriginalFilename();
                File file = new File(path);
                try {
                    multipartFile.transferTo(file);
                }catch (IOException e){
                    e.printStackTrace();
                }
            });
            thread.start();
            thread.join(); //让子线程执行完再执行主线程
        }
        return  "success";
    }

}
