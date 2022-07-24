package com.atguigu.demo.java.alibaba.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.google.common.base.Stopwatch;
import net.sf.jsqlparser.statement.create.table.ColumnDefinition;
import org.springframework.util.StopWatch;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.List;

public class Test {
    public static void main(String[] args) {

    }

    // 写多个sheet
    public static void writeSheets() {
        ExcelWriter excelWriter = null;
        try {
            OutputStream out = new FileOutputStream("D:\\多sheet" + System.currentTimeMillis() + ".xlsx");
            excelWriter = EasyExcel.write(out, WidthAndHeightData.class).registerWriteHandler(EasyExcelUtils.horizontalCellStyleStrategy()).build();
            List<ColumnDefinition> data = null;
            StopWatch stopwatch = new StopWatch();
            stopwatch.start("写excel");
            String str = "id,name";
            String[] headArr = str.split(",");
            List<List<String>> head = EasyExcelUtils.simpleHead(headArr);
            WriteSheet writeSheet = EasyExcel.writerSheet(0, "sheetName0").head(head).build();
            excelWriter.write(data, writeSheet);
            WriteSheet writeSheet1 = EasyExcel.writerSheet(1, "sheetName2").head(head).build();
            excelWriter.write(data, writeSheet1);
            stopwatch.stop();
            System.out.println("耗时信息如下：" + stopwatch.prettyPrint());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // 千万别忘记finish,会帮忙关闭流
            if (excelWriter != null) {
                excelWriter.finish();
            }
        }
    }
}
