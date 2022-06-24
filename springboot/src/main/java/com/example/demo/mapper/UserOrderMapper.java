package com.example.demo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.demo.entity.Order;
import com.example.demo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author :lujianbin
 * @create :2022-06-21 16:18:00
 * @description :
 * @solution :
 */
@Mapper
public interface UserOrderMapper extends BaseMapper<User> {
  @Insert("INSERT INTO user_order  SET user_id=#{uid},order_id=#{oid},time=#{time}")
  void insertByUidAndOid(int uid, Integer oid, String time);

  List<Order> findPersonOrder();
}
