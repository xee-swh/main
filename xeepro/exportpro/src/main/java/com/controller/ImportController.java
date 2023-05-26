package com.controller;

import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import com.dto.Book;
import com.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/import")
public class ImportController {

    @Autowired
    BookMapper bookMapper;

    /**
     * 导入功能
     */
    @PostMapping("/importData")
    public void importData(MultipartFile file) {
        try {
            //从文件中读取Excel为ExcelReader
            ExcelReader reader = ExcelUtil.getReader(file.getInputStream());
            //转换成对应的实体类
            List<Book> books = reader.readAll(Book.class);
            //插入到数据库
            books.forEach(book -> bookMapper.insert(book));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
