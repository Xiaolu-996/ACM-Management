package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Builder;
import lombok.Data;

/**
 * @author :lujianbin
 * @create :2022-06-21 15:58:00
 * @description :
 * @solution :
 */
@Data
@Builder
@TableName("user_order")
public class UserOrder {
	private Integer userId;
	private Integer orderId;
	private String time;
}
