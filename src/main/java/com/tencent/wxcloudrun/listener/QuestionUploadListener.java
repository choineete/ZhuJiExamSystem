package com.tencent.wxcloudrun.listener;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.read.listener.ReadListener;
import com.alibaba.excel.util.ListUtils;
import com.tencent.wxcloudrun.dao.QuestionMapper;
import com.tencent.wxcloudrun.model.Question;
import com.tencent.wxcloudrun.util.JsonUtil;
import com.tencent.wxcloudrun.util.ModelMapperSingle;
import com.tencent.wxcloudrun.viewmodel.wx.QuestionUploadVM;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

@Slf4j
public class QuestionUploadListener implements ReadListener<QuestionUploadVM> {

    protected final static ModelMapper modelMapper = ModelMapperSingle.Instance();

    //注入QuestionMapper
    final QuestionMapper questionMapper;
    public QuestionUploadListener(@Autowired QuestionMapper questionMapper){
        this.questionMapper = questionMapper;
    }

    /**
     * 每隔5条存储数据库，实际使用中可以100条，然后清理list ，方便内存回收
     */
    private static final int BATCH_COUNT = 100;


    /**
     * 缓存的数据，应该为Question对象
     */
    private List<Question> cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);


    @Override
    public void invoke(QuestionUploadVM data, AnalysisContext analysisContext) {

        log.info("解析到一条数据:{}", JsonUtil.toJsonStr(data));
        Question question = this.toQuestion(data);

        cachedDataList.add(question);
        // 达到BATCH_COUNT了，需要去存储一次数据库，防止数据几万条数据在内存，容易OOM
        if (cachedDataList.size() >= BATCH_COUNT) {
            saveData();
            // 存储完成清理 list
            cachedDataList = ListUtils.newArrayListWithExpectedSize(BATCH_COUNT);
        }
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {
        // 这里也要保存数据，确保最后遗留的数据也存储到数据库
        saveData();
        log.info("所有数据解析完成！");
    }

    //调用questionMapper，将数据存入数据库
    private void saveData() {
        log.info("{}条数据，开始存储数据库！", cachedDataList.size());
        questionMapper.insertQuestionsFromExcel(cachedDataList);
        log.info("存储数据库成功！");
    }

    private Question toQuestion(QuestionUploadVM questionUploadVM){
        //将questionUploadVM映射为Question对象
        Question question = modelMapper.map(questionUploadVM,Question.class);

        question.setCreateTime(new Date());
        question.setStatus(1);
        return question;
    }
}
