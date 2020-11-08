package com.imooc.service;

import com.imooc.pojo.UserAddress;
import java.util.List;

public interface AddressService {
    void add(UserAddress userAddress);
    List<UserAddress> list(String userId);
    void update(UserAddress userAddress);
    void delete(String addressId,String userId);
    void updateDefaultAddress(String userId,String addressId);
}
