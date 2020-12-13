package com.imooc.service;

import com.imooc.pojo.OrderItems;
import com.imooc.pojo.PagedGridResult;
import com.imooc.pojo.UserAddress;
import com.imooc.pojo.bo.OrderItemBO;
import com.imooc.pojo.vo.MyCommentVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MyCommentsService {
    List<OrderItems> listOrderItems(String orderId);
    void saveComments(String userId,String orderId,List<OrderItemBO> list);
    PagedGridResult pageMyComments(String userId, Integer page, Integer pageSize);

}
