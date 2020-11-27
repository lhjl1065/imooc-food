package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.Stu;
import com.imooc.pojo.Stu1;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface StuMapper extends MyMapper<Stu> {
    void updateAge(@Param("id") Integer id);
    List<Stu1> getAll();

}
