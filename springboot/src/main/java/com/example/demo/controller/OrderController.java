package com.example.demo.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.io.IoUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.demo.common.Result;
import com.example.demo.entity.Order;
import com.example.demo.mapper.OrderMapper;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderController extends BaseController {

  @Resource OrderMapper OrderMapper;

  // 获取所有题目
  @GetMapping("/all")
  public Result<?> findAll() {
    return Result.success(OrderMapper.selectList(null));
  }

  // 根据难度获取题目
  @GetMapping("/hard")
  public Result<?> findAllByHard(@RequestParam(defaultValue = "") String hard) {
    QueryWrapper<Order> wrapper = new QueryWrapper<>();
    wrapper.eq("hard", hard);
    //        Page<Order> OrderPage = OrderMapper.selectPage(new Page<>(pageNum, pageSize),
    // wrapper);
    //        return Result.success(OrderPage);
    return Result.success(OrderMapper.selectList(wrapper));
  }

  // 分页操作
  @GetMapping
  public Result<?> findPage(
      @RequestParam(defaultValue = "1") Integer pageNum,
      @RequestParam(defaultValue = "10") Integer pageSize,
      @RequestParam(defaultValue = "") String search) {
    System.out.println("分页的查询");

    LambdaQueryWrapper<Order> wrapper = Wrappers.lambdaQuery();
    System.out.println("搜索进入：" + search);
    if (StrUtil.isNotBlank(search)) {
      if (search.equals("0")
          || search.equals("1")
          || search.equals("2")
          || search.equals("3")
          || search.equals("4")) {
        System.out.println("按照难度进行筛选：" + search);
        wrapper.eq(Order::getHard, search);
      } else if (search.equals("数学")
          || search.equals("数论")
          || search.equals("图论")
          || search.equals("算法")
          || search.equals("字符串")
          || search.equals("博弈论")
          || search.equals("计算几何")
          || search.equals("数据结构")
          || search.equals("动态规划")) {
        System.out.println("按照大类知识点进行筛选：" + search);
        wrapper.eq(Order::getBroadKnowledge, search);
      } else if (search.equals("基础")
          || search.equals("分类基础")
          || search.equals("分类中级")
          || search.equals("分类高级")) {
        System.out.println("按照大类知识点进行筛选：" + search);
        wrapper.eq(Order::getMaster, search);
      } else {
        System.out.println("按照细分知识点进行搜索：" + search);
        wrapper.like(Order::getSubdivisionKnowledge, search);
      }
    }
    Page<Order> OrderPage = OrderMapper.selectPage(new Page<>(pageNum, pageSize), wrapper);
    return Result.success(OrderPage);
  }

  // 插入题目
  @PostMapping
  public Result<?> save(@RequestBody Order Order) {
    OrderMapper.insert(Order);
    return Result.success();
  }

  // 更新题目
  @PutMapping
  public Result<?> update(@RequestBody Order Order) {
    OrderMapper.updateById(Order);
    return Result.success();
  }

  // 根据id删除题目
  @DeleteMapping("/{id}")
  public Result<?> delete(@PathVariable Long id) {
    OrderMapper.deleteById(id);
    return Result.success();
  }

  // 根据id查询题目
  @GetMapping("/{id}")
  public Result<?> getById(@PathVariable Long id) {
    return Result.success(OrderMapper.selectById(id));
  }

  /**
   * Excel导出
   *
   * @param response
   * @throws IOException
   */
  @GetMapping("/export")
  public void export(HttpServletResponse response) throws IOException {

    List<Map<String, Object>> list = CollUtil.newArrayList();

    List<Order> all = OrderMapper.selectList(null);
    for (Order order : all) {
      Map<String, Object> row1 = new LinkedHashMap<>();
      row1.put("题目", order.getName());
      row1.put("大类知识点", order.getBroadKnowledge());
      row1.put("细分知识点", order.getSubdivisionKnowledge());
      row1.put("难度", order.getHard());
      row1.put("掌握程度", order.getMaster());
      list.add(row1);
    }

    // 2. 写excel
    ExcelWriter writer = ExcelUtil.getWriter(true);
    writer.write(list, true);

    response.setContentType(
        "application/vnd.malformations-office document.spreadsheet.sheet;charset=utf-8");
    String fileName = URLEncoder.encode("题目信息", "UTF-8");
    response.setHeader("Content-Disposition", "attachment;filename=" + fileName + ".xlsx");

    ServletOutputStream out = response.getOutputStream();
    writer.flush(out, true);
    writer.close();
    IoUtil.close(System.out);
  }

  /**
   * Excel导入 导入的模板可以使用 Excel导出的文件
   *
   * @param file Excel
   * @return
   * @throws IOException
   */
  @PostMapping("/import")
  public Result<?> upload(MultipartFile file) throws IOException {
    InputStream inputStream = file.getInputStream();
    List<List<Object>> lists = ExcelUtil.getReader(inputStream).read(1);
    List<Order> saveList = new ArrayList<>();
    for (List<Object> row : lists) {
      Order order = new Order();
      order.setName(row.get(0).toString());
      order.setBroadKnowledge(row.get(1).toString());
      order.setSubdivisionKnowledge(row.get(2).toString());
      order.setHard((row.get(3).toString()));
      order.setMaster(row.get(4).toString());
      saveList.add(order);
    }
    for (Order order : saveList) {
      OrderMapper.insert(order);
    }
    return Result.success();
  }
}
