package app.apiservice;


import app.packet.error.ErrorEnum;
import app.packet.response.CommonMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;

@ControllerAdvice
@RequestMapping(path = "/api")
public class Version {

    @Value("${uploadpath}")
    private String path;

    @RequestMapping(path="/latest-release",method= RequestMethod.GET)
    public void myTree(HttpServletResponse res) throws IOException {
        String filename = "知我.apk";
        res.addHeader("Content-Disposition", "attachment; filename=" + new String(filename.getBytes("utf-8"), "ISO-8859-1"));
        OutputStream outputStream = res.getOutputStream();
        FileInputStream fileInputStream = new FileInputStream(new File(path , "知我.apk"));

        BufferedInputStream in = new BufferedInputStream(fileInputStream);
        BufferedOutputStream out = new BufferedOutputStream(outputStream);

        byte[] bytes = new byte[2048];
        int n;
        while ((n = in.read(bytes,0,bytes.length)) != -1) {
            out.write(bytes, 0, n);
        }
        //清楚缓存
        out.flush();
        //关闭流
        in.close();
        out.close();
    }


    @ExceptionHandler(value = Exception.class)
    public @ResponseBody CommonMessage errorHandler(Exception ex) {
        ex.printStackTrace();
        return CommonMessage.failure(ErrorEnum.SERVER_INTERNAL_ERROR);
    }
}
