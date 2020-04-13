package com.baizhi.wy.serviceImpl;

import com.baizhi.wy.annotation.AddCaches;
import com.baizhi.wy.dao.FeedbackMapper;
import com.baizhi.wy.entity.Feedback;
import com.baizhi.wy.entity.FeedbackExample;
import com.baizhi.wy.service.FeedbackService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class FeedbackServiceImpl implements FeedbackService {
    @Autowired
    private FeedbackMapper feedbackMapper;

    @AddCaches
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> showAll(Integer page, Integer rows) {
        //创建map集合
        Map<String,Object> map = new HashMap<>();
        FeedbackExample example = new FeedbackExample();
        int records = feedbackMapper.selectCountByExample(example);
        //存入总条数
        map.put("records",records);
        //计算总页数
        Integer total=records%rows==0?records/rows:records/rows+1;
        map.put("total",total);
        //将当前页存入集合
        map.put("page",page);
        //分页
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Feedback> feedbacks = feedbackMapper.selectByExampleAndRowBounds(example, rowBounds);
        //将数据存入集合
        map.put("rows",feedbacks);
        return map;
    }
}
