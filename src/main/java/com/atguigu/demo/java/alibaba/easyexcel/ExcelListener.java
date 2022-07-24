package com.atguigu.demo.java.alibaba.easyexcel;

import cn.hutool.json.JSONUtil;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.excel.metadata.data.ReadCellData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.DependsOn;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
@DependsOn("springContextUtils")
public class ExcelListener<T> extends AnalysisEventListener<T> {

    // 默认给一万，过大效率会变低
    private static final int BATCH_COUNT = 10000;

    List<T> list = new ArrayList<>();

    // 是否保存数据
    Boolean saveData;

    List<String> headList = new ArrayList<>();

    public ExcelListener() {

    }

    @Override
    public void invoke(T data, AnalysisContext analysisContext) {
        log.info("解析到一条数据:{}", JSONUtil.toJsonStr(data));
        list.add(data);

        // 把BATCH_COUNT倍数的数据全部save
        if (saveData && list.size() >= BATCH_COUNT) {
            saveData();
            list.clear();
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        if (saveData) {
            // 把最后的数据统一保存起来，有可能数据不是BATCH_COUNT的整数倍
            saveData();
        }
    }

    /**
     * 读取表头，考虑表头信息如何处理
     * @param headMap
     * @param context
     */
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        super.invokeHeadMap(headMap, context);
        for (Map.Entry<Integer, String> entry: headMap.entrySet()) {
            headList.add("item" + (entry.getKey() + 1));
        }
    }

    /**
     * 加上存储数据库
     */
    private void saveData() {
        log.info("{}条数据，开始存储数据库！" + list.size());
        log.info("存储数据库成功!");
    }

    public List<String> getHeadList() {
        return headList;
    }
}
