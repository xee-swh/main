package com.controller;

import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.dto.Book;
import com.mapper.BookMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/export")
public class ExportController {

    @Autowired
    private BookMapper bookMapper;

    /**
     * 导出功能
     *
     * @param response
     */
    @GetMapping("/export1")
    public void export(HttpServletResponse response) {
        // 查询所有导出数据
        List<Book> list = bookMapper.selectList(new QueryWrapper<Book>());

        // 通过工具类创建writer，默认创建xls格式
        // 写出文件路径
        String inputPath = "E:/db/data.xlsx";
        ExcelWriter writer = ExcelUtil.getWriter(inputPath);

        // 自定义表格列名 给实体类对应的字段取名字
        writer.addHeaderAlias("id", "编号");
        writer.addHeaderAlias("bookName", "书名");
        writer.addHeaderAlias("bookCounts", "数量");
        writer.addHeaderAlias("detail", "描述");
        writer.addHeaderAlias("dbSource", "来源");

        // 合并单元格后的标题行，使用默认标题样式
        writer.merge(4, "书籍表");

        // 一次性写出内容，使用默认样式，强制输出标题
        writer.write(list, true);

        // out为OutputStream，需要写出到的目标流
        // response为HttpServletResponse对象
        response.setContentType("application/vnd.ms-excel;charset=utf-8");

        // 输出表名编码
        ServletOutputStream out = null;
        try {
            String name = new String("书籍表.csv"
                    .getBytes("UTF-8"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment;filename=" + name);
            out = response.getOutputStream();
            writer.flush(out, true);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭writer，释放内存
            writer.close();
        }
        // 此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

}
