package com.baizhi.wy.serviceImpl;

import com.baizhi.wy.annotation.AddCaches;
import com.baizhi.wy.annotation.DelCache;
import com.baizhi.wy.annotation.LogAnnotation;
import com.baizhi.wy.dao.CategoryMapper;
import com.baizhi.wy.entity.Category;
import com.baizhi.wy.entity.CategoryExample;
import com.baizhi.wy.po.CategoryPo;
import com.baizhi.wy.service.CategoryService;
import com.baizhi.wy.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class CategoryServiceImpl  implements CategoryService {

    @Autowired
    private CategoryMapper categoryMapper;

    @AddCaches
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> showAll(Integer page,Integer rows,Integer levels,String parentId) {
        //创建一个Map集合存放数据
        Map<String,Object> map = new HashMap<>();
        CategoryExample example = new CategoryExample();
        if(levels == 1) {
            //条件限制为级别等于多少
            example.createCriteria().andLevelsEqualTo(levels);
        }else{
           //二级类别查询条件根据父级类别查询
            example.createCriteria().andParentIdEqualTo(parentId);
        }
        //查询这个级别下的数据总条数
        int records = categoryMapper.selectCountByExample(example);
        map.put("records",records);
        //通过总条数和每页显示条数计算总页数
        Integer total = records%rows == 0 ? records/rows:records/rows+1;
        map.put("total",total);
        //将当前页放入集合
        map.put("page",page);
        //查询这个级别下数据集合
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Category> categories = categoryMapper.selectByExampleAndRowBounds(example,rowBounds);
        map.put("rows",categories);
        return map;
    }
    @DelCache
    @Override
    @LogAnnotation(name="添加类别")
    public void add(Category category) {
        String uuid = UUIDUtil.getUUID();
        category.setId(uuid);
        if(category.getParentId()!=null&& category.getParentId()!=""){
            category.setLevels(2);
        }else{
            category.setLevels(1);
        }
        categoryMapper.insertSelective(category);
    }
    @DelCache
    @Override
    @LogAnnotation(name = "删除类别")
    public Map<String,Object> delete(Category category) {
        //创建map集合用来存放操作数据
       Map<String,Object> map = new HashMap<>();

       //判断是一级类别还是二级类别
        if(category.getParentId()==null){
            //判断一级类别下是否有二级类别
            CategoryExample example = new CategoryExample();
            example.createCriteria().andParentIdEqualTo(category.getId());
            int count = categoryMapper.selectCountByExample(example);
            if(count ==0){
                //一级类别下没有二级类别直接删除
                categoryMapper.deleteByPrimaryKey(category);
                map.put("status","200");
                map.put("message","删除成功");
            }else{
                //有二级类别，不能删除
                map.put("status","400");
                map.put("message","删除失败，该一级类别下有子类别");
            }
        }else{
            //没有写视频，先直接删除
            categoryMapper.deleteByPrimaryKey(category);
            map.put("status","200");
            map.put("message","删除成功");
        }
       return map;
    }
    @DelCache
    @Override
    @LogAnnotation(name = "修改类别")
    public void update(Category category) {
        categoryMapper.updateByPrimaryKeySelective(category);
    }

    @AddCaches
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryTwo() {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo(2);
        List<Category> categories = categoryMapper.selectByExample(example);
        return categories;
    }

    @AddCaches
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Category> queryOne() {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo(1);
        List<Category> categories = categoryMapper.selectByExample(example);
        return categories;
    }
    @AddCaches
    @Override
    public List<CategoryPo> queryAllCategory() {
        CategoryExample example = new CategoryExample();
        example.createCriteria().andLevelsEqualTo(1);
        List<Category> categories = categoryMapper.selectByExample(example);
        List<CategoryPo> list = new ArrayList<>();
        for (Category category : categories) {
            CategoryExample example1 = new CategoryExample();
            example1.createCriteria().andParentIdEqualTo(category.getId());
            List<Category> categories1 = categoryMapper.selectByExample(example1);
            CategoryPo categoryPo = new CategoryPo(category.getId(),category.getCateName(),category.getLevels(),category.getParentId(),categories1);
            list.add(categoryPo);
        }
        return list;
    }

}
