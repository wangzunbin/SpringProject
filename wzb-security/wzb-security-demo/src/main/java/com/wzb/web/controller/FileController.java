package com.wzb.web.controller;


import com.wzb.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * ClassName:FileController  <br/>
 * Function:  <br/>
 *
 * @author WangZunBin <br/>
 * @version 0.4 2020/3/3 8:20   <br/>
 */

@RestController
@RequestMapping("/file")
public class FileController {
    String path = "G:\\SpringProject\\wzb-security\\wzb-security-demo\\src\\main\\java\\com\\wzb\\web\\controller";

    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        System.out.println(file.getName());
        System.out.println(file.getOriginalFilename());
        System.out.println(file.getSize());

        File localFile = new File(path, new Date().getTime() + ".text");
        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{id}")
    public void download(@PathVariable String id,
                         HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // java7的新特性
        try (
                InputStream in = new FileInputStream(new File(path, id + ".text"));
                OutputStream out = resp.getOutputStream();
        ) {
            resp.setContentType("application/x-download");
            resp.addHeader("Content-Disposition", "attachment;filename=test.text");
            IOUtils.copy(in, out);
            out.flush();
        }


    }
}
