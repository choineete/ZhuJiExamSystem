package com.tencent.wxcloudrun.controller.wx.hr;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.ListUtils;
import com.tencent.wxcloudrun.base.RestResponse;
import com.tencent.wxcloudrun.controller.wx.BaseApiWXController;
import com.tencent.wxcloudrun.dao.QuestionMapper;
import com.tencent.wxcloudrun.dao.UploadDAO;
import com.tencent.wxcloudrun.listener.QuestionUploadListener;
import com.tencent.wxcloudrun.listener.UploadDataListener;
import com.tencent.wxcloudrun.model.UploadData;
import com.tencent.wxcloudrun.service.QuestionService;
import com.tencent.wxcloudrun.util.ConstantUtil;
import com.tencent.wxcloudrun.viewmodel.wx.QuestionUploadVM;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@ResponseBody
//@RequestMapping("/api/wx")
public class QuestionManageController extends BaseApiWXController implements ConstantUtil {

    @Autowired
    QuestionService questionService;
    @Autowired
    QuestionMapper questionMapper;

    @PostMapping("/upload")
    public RestResponse upload(MultipartFile file) throws IOException {


        if (file == null) {
            return RestResponse.fail(2, "文件为空");
        }

        String fileName = file.getOriginalFilename();

        int lastIndexOf = fileName.lastIndexOf('.') + 1;

        String suffix = fileName.substring(lastIndexOf);

        if (!suffix.equals("xlsx")) {
            return RestResponse.fail(3, "文件格式错误");
        }


        EasyExcel.read(file.getInputStream(), QuestionUploadVM.class,
                new QuestionUploadListener(questionMapper)).sheet().doRead();
        return RestResponse.ok("上传成功");
    }


}
