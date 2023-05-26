package com.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dto.Book;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface BookMapper extends BaseMapper<Book> {

}
