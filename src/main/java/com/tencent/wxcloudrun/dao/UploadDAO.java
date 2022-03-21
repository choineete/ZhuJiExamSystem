package com.tencent.wxcloudrun.dao;

import com.tencent.wxcloudrun.model.UploadData;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Mapper;

import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

@Mapper
public interface UploadDAO {

    @InsertProvider(type = Provider.class, method = "batchInsert")
    void save(List<UploadData> uploadDataList);

//    @InsertProvider(type = Provider.class, method = "batchInsert")
//    int batchInsert(List<Student> students);

    class Provider {
        /* 批量插入 */
        public String batchInsert(Map map) {
            List<UploadData> students = (List<UploadData>) map.get("list");

            StringBuilder sb = new StringBuilder();
            sb.append("INSERT INTO upload_data (string,date,double_data) VALUES ");

            MessageFormat mf = new MessageFormat(
                    "(#'{'list[{0}].string}, #'{'list[{0}].date}, #'{'list[{0}].doubleData})"
            );

            for (int i = 0; i < students.size(); i++) {
                sb.append(mf.format(new Object[] {i}));
                if (i < students.size() - 1)
                    sb.append(",");
            }
            return sb.toString();
        }

//        /* 批量删除 */
//        public String batchDelete(Map map) {
//            List<Student> students = (List<Student>) map.get("list");
//            StringBuilder sb = new StringBuilder();
//            sb.append("DELETE FROM student WHERE id IN (");
//            for (int i = 0; i < students.size(); i++) {
//                sb.append("'").append(students.get(i).getId()).append("'");
//                if (i < students.size() - 1)
//                    sb.append(",");
//            }
//            sb.append(")");
//            return sb.toString();
//        }
    }
}
