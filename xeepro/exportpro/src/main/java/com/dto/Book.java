package com.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

@Data
@TableName("book")
public class Book implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    @TableField("book_name")
    private String bookName;
    @TableField("book_counts")
    private Integer bookCounts;
    private String detail;
    @TableField("db_source")
    private String dbSource;
}