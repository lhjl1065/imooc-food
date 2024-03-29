package com.imooc.mapper;

import com.imooc.my.mapper.MyMapper;
import com.imooc.pojo.ItemsComments;
import com.imooc.pojo.vo.CommentVo;
import com.imooc.pojo.vo.MyCommentVO;
import com.imooc.pojo.vo.SearchItemVo;
import java.util.HashMap;
import java.util.List;
import org.apache.ibatis.annotations.Param;

/**
 * 自定义的商品评价mapper
 */
public interface ItemsCommentsMapperCustom {
    List<CommentVo> getItemCommentVoList(@Param("map") HashMap map);
    Integer saveItemCommentList(HashMap map);
    String a= null;
    List<MyCommentVO> pageMyComments(@Param("userId") String userId);
}
