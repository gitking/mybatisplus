package com.atguigu.demo.java.alibaba.easyexcel;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.alibaba.excel.util.ListUtils;

import java.util.Date;
import java.util.List;

/**
 * 写csv
 * CSV中文乱码(utf-8)的解决
 * 背景：数据表（utf-8格式，含中文）内容导出为csv文件，打开，乱码。
 * 原因：csv默认中文支持ANSI编码，且没有预留修改编码的选项。
 * 解决：那只能我们修改csv的中文编码格式为ANSI了。
 * 右键csv->打开方式->选择“记事本”（此时神奇的发现没乱码了，记事本支持utf-8）->另存为->
 * 在弹出的窗口中选择编码‘ANSI’，名称同名，覆盖即可。
 * 此时再打开csv，中文乱码就消失了。
 * 上善若水，水利万物而不争。
 * https://www.cnblogs.com/yoyotl/p/12988356.html
 *
 * EasyExcel3.0读写Excel、CSV 小布1994  已于 2022-04-14 21:49:40 修改 2085
 * ————————————————
 * 版权声明：本文为CSDN博主「小布1994」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
 * 原文链接：https://blog.csdn.net/tanhongwei1994/article/details/122701930
 */
public class WriteCsvDemo {

    public static void main(String[] args) {
        WriteCsvDemo writeCsvDemo = new WriteCsvDemo();
        writeCsvDemo.noModelWrite();
    }

    // 不创建对象的写
    public void noModelWrite() {
        // 写法1
        String fileName = "D://noModelWrite" + System.currentTimeMillis() + ".csv";

        // 这里需要指定写用哪个class去写，然后写到第一个Sheet,名字为模板 然后文件流会自动关闭
        EasyExcel.write(fileName).excelType(ExcelTypeEnum.CSV).head(head()).sheet("模板").doWrite(dataList());
        System.out.println("noModelWrite success");
    }

    private List<List<String>> head() {
        List<List<String>> list = ListUtils.newArrayList();
        List<String> head0 = ListUtils.newArrayList();
        head0.add("字符串" + System.currentTimeMillis());
        List<String> head1 = ListUtils.newArrayList();
        head1.add("数字" + System.currentTimeMillis());
        List<String> head2 = ListUtils.newArrayList();
        head2.add("日期" + System.currentTimeMillis());
        list.add(head0);
        list.add(head1);
        list.add(head2);
        return list;
    }

    private List<List<Object>> dataList() {
        List<List<Object>> list = ListUtils.newArrayList();
        for (int i=0; i<10; i++) {
            List<Object> data = ListUtils.newArrayList();
            data.add("字符串" + i);
            data.add(new Date());
            data.add(0.56);
            list.add(data);
        }
        return list;
    }
}
