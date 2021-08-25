package com.yh.test_demo.controller;


import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.server.HttpServerResponse;
import com.yh.test_demo.common.Result;
import org.hibernate.annotations.Parameter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/files")
public class FileController {

    @Value("server.port")
    private String port;

    private static final String ip = "http://localhost";

    @PostMapping("/upload")
    public Result<?> upload(MultipartFile file) throws IOException {
        //获取原文件的名称
        String originalFilename = file.getOriginalFilename();
        //定义唯一标识
        String flag = IdUtil.fastSimpleUUID();
        //获取上传的路径
        String rootFilePath = System.getProperty("user.dir") + "/src/main/resources/files/" +flag+"_"+ originalFilename;
        //文件写入路径中
        FileUtil.writeBytes(file.getBytes(), rootFilePath);
        //返回结果的url
        return Result.success(ip + ":" + port + "/files/" + flag);
    }

//    @GetMapping("/{flag}")
//    public void getFiles(@PathVariable String flag, HttpServerResponse response){
//        OutputStream os;
//        String basePath = System.getProperty("user.dir") + "/src/main/resources/files/";
//        List<String> fileNames = FileUtil.listFileNames(basePath);
//        String fileName = fileNames.stream().filter(name -> name.contains(flag)).findAny().orElse("");
//        try {
//            if (StrUtil.isNotEmpty(fileName)) {
//                response.addHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
//                response.setContentType("application/octet-stream");
//                byte[] bytes = FileUtil.readBytes(basePath + fileName);
//                os = response.getOut();
//                os.write(bytes);
//                os.flush();
//                os.close();
//            }
//        }catch (Exception e){
//            System.out.println("文件下载失败");
//        }
//    }

}
