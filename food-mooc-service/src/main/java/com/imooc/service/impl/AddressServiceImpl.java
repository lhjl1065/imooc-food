package com.imooc.service.impl;

import com.imooc.common.enums.YesOrNo;
import com.imooc.mapper.UserAddressMapper;
import com.imooc.pojo.UserAddress;
import com.imooc.service.AddressService;
import java.util.Date;
import java.util.List;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    UserAddressMapper userAddressMapper;

    @Autowired
    Sid sid;

    /**
     * 新增收货地址
     * @param userAddress
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void add(UserAddress userAddress) {
        //设置全局唯一id
        userAddress.setId(sid.nextShort());
        //设置创建时间和修改时间
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        //查询是否是该用户的第一个收货地址,如果是则把默认地址字段设为 1,不是则设为0
        userAddress.setIsDefault(YesOrNo.NO.getType());
        List<UserAddress> list = list(userAddress.getUserId());
        if (CollectionUtils.isEmpty(list)){
            userAddress.setIsDefault(YesOrNo.YES.getType());
        }
        //存储收货地址
        userAddressMapper.insert(userAddress);
    }

    /**
     * 查询用户的收货地址列表
     * @param userId
     * @return
     */
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserAddress> list(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }

    /**
     * 修改收货地址
     * @param userAddress
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void update(UserAddress userAddress) {
        //设置更新时间
        userAddress.setUpdatedTime(new Date());
        //更新收货地址
        userAddressMapper.updateByPrimaryKeySelective(userAddress);
    }
    /**
     * 物理删除收货地址
     * @param addressId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void delete(String addressId,String userId) {
        //如果是默认收货地址,那么如果还剩得有收货地址,则设置剩下的其中一个收货地址为默认
        UserAddress userAddress = userAddressMapper.selectByPrimaryKey(addressId);
        if (userAddress.getIsDefault()==1){
            userAddressMapper.deleteByPrimaryKey(addressId);
            UserAddress userAddress1 = new UserAddress();
            userAddress1.setUserId(userId);
            List<UserAddress> list = userAddressMapper.select(userAddress1);
            if (!CollectionUtils.isEmpty(list)){
                UserAddress userAddress2 = new UserAddress();
                userAddress2.setIsDefault(YesOrNo.YES.getType());
                userAddress2.setId(list.get(0).getId());
                userAddressMapper.updateByPrimaryKeySelective(userAddress2);
            }
            return;
        }
        userAddressMapper.deleteByPrimaryKey(addressId);
    }

    /**
     * 修改用户默认收货地址
     * @param userId
     * @param addressId
     */
    @Transactional(propagation = Propagation.REQUIRED)
    public void updateDefaultAddress(String userId,String addressId){
        //删除所有的的默认收货地址(保险起见)
        UserAddress queryAddress = new UserAddress();
        queryAddress.setIsDefault(YesOrNo.YES.getType());
        queryAddress.setUserId(userId);
        List<UserAddress> addressList = userAddressMapper.select(queryAddress);
        for (UserAddress userAddress : addressList){
            userAddress.setIsDefault(YesOrNo.NO.getType());
            userAddressMapper.updateByPrimaryKeySelective(userAddress);
        }
        //根据addressId设置新的默认收货地址
        UserAddress updateAddress = new UserAddress();
        updateAddress.setIsDefault(YesOrNo.YES.getType());
        updateAddress.setId(addressId);
        userAddressMapper.updateByPrimaryKeySelective(updateAddress);
    }




}
