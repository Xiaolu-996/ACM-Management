package com.example.demo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author xiaolu
 */
@Data
@TableName("t_order")
public class Order {

  @TableId(type = IdType.AUTO)
  private Integer id;

  private String name;
  private String broadKnowledge;
  private String subdivisionKnowledge;
  private String hard;
  private String master;

  @TableField(exist = false)
  private Integer uid;

  @TableField(exist = false)
  private String time;
}
