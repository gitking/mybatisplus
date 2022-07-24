package com.atguigu.demo.java.alibaba.easyexcel;


import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.alibaba.excel.annotation.write.style.ContentRowHeight;
import com.alibaba.excel.annotation.write.style.HeadRowHeight;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 基础数据类
 *
 */
@Getter
@Setter
@EqualsAndHashCode
@ContentRowHeight(15)
@HeadRowHeight(30)
@ColumnWidth(50)//这个无效
public class WidthAndHeightData {
}
