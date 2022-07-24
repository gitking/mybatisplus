package com.atguigu.demo.java.alibaba.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.EasyExcelFactory;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.WriteTable;
import com.alibaba.excel.write.metadata.style.WriteCellStyle;
import com.alibaba.excel.write.metadata.style.WriteFont;
import com.alibaba.excel.write.style.HorizontalCellStyleStrategy;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.springframework.stereotype.Indexed;

import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

/**
 * EasyExcel3.0读写Excel、CSV 小布1994 已于 2022-04-14 21:49:40 修改 2085 收藏 4
 *
 * <dependency>
 *     <groupId>com.alibaba</groupId>
 *     <artifactId>easyexcel</artifactId>
 *     <version>3.0.5</version>
 * </dependency>
 * ————————————————
 * 版权声明：本文为CSDN博主「小布1994」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/tanhongwei1994/article/details/122701930
 */
public class EasyExcelUtils<T> {

    /**
     * 写单个表头
     *
     * @param headArr
     * @return
     */
    public static List<List<String>> simpleHead(String[] headArr) {
        List<List<String>> headTitles = new ArrayList<>();
        for (String s : headArr) {
            List<String> head = new ArrayList<>();
            head.add(s);
            headTitles.add(head);
        }
        return headTitles;
    }

    /**
     * 默认策略 HorizontalCellStyleStrategy
     *
     * @return
     */
    public static HorizontalCellStyleStrategy horizontalCellStyleStrategy() {
        // 头的策略
        WriteCellStyle headWriteCellStyle = new WriteCellStyle();
        headWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        // 设置头的字体
        WriteFont headWriteFont = new WriteFont();
        headWriteFont.setFontName("微软雅黑");
        headWriteFont.setFontHeightInPoints((short)10);
        headWriteCellStyle.setWriteFont(headWriteFont);

        // 内容格式
        WriteCellStyle contentWriteCellStyle = new WriteCellStyle();
        WriteFont contentWriteFont = new WriteFont();
        contentWriteFont.setFontName("微软雅黑");
        contentWriteFont.setFontHeightInPoints((short)9);
        contentWriteCellStyle.setFillForegroundColor(IndexedColors.WHITE.getIndex());
        contentWriteCellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        contentWriteCellStyle.setHorizontalAlignment(HorizontalAlignment.CENTER);
        contentWriteCellStyle.setBorderBottom(BorderStyle.THIN);
        contentWriteCellStyle.setBorderLeft(BorderStyle.THIN);
        contentWriteCellStyle.setBorderRight(BorderStyle.THIN);
        contentWriteCellStyle.setBorderTop(BorderStyle.THIN);
        contentWriteCellStyle.setWriteFont(contentWriteFont);
        return new HorizontalCellStyleStrategy(headWriteCellStyle, contentWriteCellStyle);
    }

    /**
     * EasyExcel不支持Map的写入,将LinkedHashMap 转成List<List<Object>
     *
     * @param list
     * @return
     */
    public static List<List<Object>> map2List(List<LinkedHashMap<String, Object>> list) {
        List<List<Object>> excelList = new ArrayList<>();
        for (LinkedHashMap<String, Object> map: list) {
            List<Object> data = new ArrayList<>();
            for (Map.Entry<String, Object> entry: map.entrySet()) {
                data.add(entry.getValue());
            }
        }
        return excelList;
    }

    /**
     * writeWithCustomizeCellStyle
     * 自定义的默认样式写数据
     *
     * @param out
     * @param sheetName
     * @param head
     * @param data
     * @throws IOException
     */
    public void writeWithCustomize(OutputStream out, String sheetName, List<List<String>> head, List<T> data) throws IOException {
        try {
            EasyExcel.write(out, WidthAndHeightData.class)
                    .registerWriteHandler(horizontalCellStyleStrategy())
                    .sheet(sheetName)
                    .head(head)
                    .doWrite(data);

        } finally {
            out.close();
        }
    }

    /**
     * 默认的样式写文件
     */
    public void writeWithDefaultCellStyle(OutputStream out, String sheetName, String[] headArr, List<T> data) throws IOException{
        ExcelWriter excelWriter = null;
        try {
            excelWriter = EasyExcelFactory.write(out).build();
            // 动态添加表头，适用一些表头动态变化的场景
            WriteSheet sheet = new WriteSheet();
            sheet.setSheetName(sheetName);
            sheet.setSheetNo(0);
            // 创建一个表格， 用于Sheet中使用
            WriteTable table = new WriteTable();
            table.setTableNo(1);
            table.setHead(simpleHead(headArr));
            // 写数据
            excelWriter.write(data, sheet, table);
        } finally {
            // 千万别忘记finish 会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
            out.close();
        }
    }


    /**
     * 动态排除要写入的字段
     *
     * @param out
     * @param sheetName
     * @param head
     * @param excludeColumnFiledNames
     * @param data
     * @throws IOException
     */
    public void writeWithCustomizeExcludeColumn(OutputStream out, String sheetName, List<List<String>> head, Set<String> excludeColumnFiledNames, List<T> data) throws IOException{
        try {
            EasyExcel.write(out, WidthAndHeightData.class).excludeColumnFiledNames(excludeColumnFiledNames)
                    .registerWriteHandler(horizontalCellStyleStrategy())
                    .sheet(sheetName)
                    .head(head)
                    .doWrite(data);
        } finally {
            out.close();
        }

    }
}
