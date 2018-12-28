package app.bankview;

import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by gongmingbo on 2018/11/16.
 */
@Controller
@RequestMapping("/web")
public class UeditorConfig {
    @Value("${uploadpath}")
    private String uploadpath;

    //----------------------------------以下为富文本配置-----------------------------------------------------------------
    //获取配置文件
    @RequestMapping("/configjson")
    public void getConfigInfo(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        response.setCharacterEncoding("utf-8");
        try {
            //  Resource resource = new ClassPathResource("config.json");
            ClassPathResource resource = new ClassPathResource("config.json");
            InputStream inputStream = resource.getInputStream();

            //   File file = resource.getFile();
            //  BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            PrintWriter writer = response.getWriter();
            StringBuffer buffer = new StringBuffer();
            String str = null;
            while ((str = br.readLine()) != null) {
                buffer.append(str);
            }
            writer.write(buffer.toString());
            writer.flush();
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //上传图片
    @RequestMapping("/uploadimages")
    @ResponseBody
    public String upImage(HttpServletRequest request, HttpServletResponse response) {
        String url = request.getRequestURL().toString();
        String address = url.substring(0, url.indexOf("/", 10));
        try {
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("upfile");
            for (MultipartFile m : files) {
                String fileName = m.getOriginalFilename();
                File targetFile = new File(uploadpath + "ueditor/", "images");
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                String NewFileName = UUID.randomUUID().toString() + fileName;
                System.out.println(m.getSize());
                FileOutputStream out = new FileOutputStream(uploadpath + "ueditor/images/" + NewFileName);
                out.write(m.getBytes());
                out.close();
                Map<String, String> map = new HashMap<>();
                map.put("state", "SUCCESS");
                map.put("url", address + "/resources/ueditor/images/" + NewFileName);
                return JSON.toJSONString(map);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //上传视频
    @RequestMapping("/uploadvideo")
    @ResponseBody
    public String upvideo(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("upfile");
            for (MultipartFile m : files) {
                String fileName = m.getOriginalFilename();
                File targetFile = new File(uploadpath + "ueditor/", "video");
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                String NewFileName = UUID.randomUUID().toString() + fileName;
                System.out.println(m.getSize());
                FileOutputStream out = new FileOutputStream(uploadpath + "ueditor/video/" + NewFileName);
                out.write(m.getBytes());
                out.close();
                Map<String, String> map = new HashMap<>();
                map.put("state", "SUCCESS");
                map.put("url", "/resources/ueditor/video/" + NewFileName);
                return JSON.toJSONString(map);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    //上传附件
    @RequestMapping("/uploadfile")
    @ResponseBody
    public String upfile(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<MultipartFile> files = ((MultipartHttpServletRequest) request).getFiles("upfile");
            for (MultipartFile m : files) {
                String fileName = m.getOriginalFilename();
                File targetFile = new File(uploadpath + "ueditor/", "file");
                if (!targetFile.exists()) {
                    targetFile.mkdirs();
                }
                String NewFileName = UUID.randomUUID().toString() + fileName;
                System.out.println(m.getSize());
                FileOutputStream out = new FileOutputStream(uploadpath + "ueditor/file/" + NewFileName);
                out.write(m.getBytes());
                out.close();
                Map<String, String> map = new HashMap<>();
                map.put("state", "SUCCESS");
                map.put("url", "/resources/ueditor/file/" + NewFileName);
                return JSON.toJSONString(map);
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}
