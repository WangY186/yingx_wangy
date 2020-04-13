package com.baizhi.wy.serviceImpl;

import com.baizhi.wy.annotation.AddCaches;
import com.baizhi.wy.dao.LogDao;
import com.baizhi.wy.entity.Log;
import com.baizhi.wy.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> showAll(Integer page, Integer rows) {
        Map<String,Object>  map =new HashMap<>();
        //计算总条数
        Integer count = logDao.count();
        map.put("records",count);
        //计算总页数
        Integer total=count%rows==0?count/rows:count/rows+1;
        map.put("total",total);
        //存入当前页
        map.put("page",page);
        //查询所有数据放入集合
        List<Log> logs = logDao.queryAll((page - 1) * rows, rows);
        map.put("rows",logs);
        return map;
    }

    @Override
    public void add(Log log) {
       logDao.insert(log);
    }
}
