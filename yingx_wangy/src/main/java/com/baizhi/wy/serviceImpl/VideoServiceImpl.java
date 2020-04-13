package com.baizhi.wy.serviceImpl;

import com.baizhi.wy.annotation.AddCaches;
import com.baizhi.wy.annotation.DelCache;
import com.baizhi.wy.annotation.LogAnnotation;
import com.baizhi.wy.dao.VideoMapper;
import com.baizhi.wy.entity.Video;
import com.baizhi.wy.entity.VideoExample;
import com.baizhi.wy.po.DetailVideoPo;
import com.baizhi.wy.po.LikeVideoPo;
import com.baizhi.wy.po.VideoPo;
import com.baizhi.wy.resposity.VideoResposity;
import com.baizhi.wy.service.VideoService;
import com.baizhi.wy.util.AliyunOssUtils;
import com.baizhi.wy.util.InterceptorVideoPhotoUtil;
import com.baizhi.wy.util.UUIDUtil;
import org.apache.ibatis.session.RowBounds;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoMapper videoMapper;
    @Autowired
    HttpSession session;

    @Autowired
    private VideoResposity videoResposity;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @AddCaches
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> showAll(Integer page, Integer rows) {
        Map<String,Object> map = new HashMap<>();
        VideoExample example = new VideoExample();
        //计算视频总条数
        int count = videoMapper.selectCountByExample(example);
        map.put("records",count);
        //计算总页数
        Integer total = count % rows==0?count/rows:count/rows+1;
        map.put("total",total);
        //将当前页放入集合
        map.put("page",page);
        //分页查询视频数据
        List<Video> videos = videoMapper.queryAll((page-1)*rows,rows);
        map.put("rows",videos);
        return map;
    }

    @DelCache
    @Override
    @LogAnnotation(name = "添加视频")
    public String addVideo(Video video) {
        String uuid = UUIDUtil.getUUID();
        video.setId(uuid);
        video.setPublishDate(new Date());
        video.setStatus("0");
        videoMapper.insertSelective(video);
        return uuid;
    }

    @Override
    public void upload(MultipartFile path, String id) {
        //获取文件名
        String originalFilename = path.getOriginalFilename();
        String name = new Date().getTime()+"-"+originalFilename;
        //将文件上传到阿里云
        AliyunOssUtils.uploadAliyun(path,"video/"+name);
        String netFilePath = "https://yingx-wang.oss-cn-beijing.aliyuncs.com/video/"+name;
        //获取本地保存截图路径
        String realPath = session.getServletContext().getRealPath("/upload/cover");
        File file = new File(realPath);
        if(!file.exists()){
            file.mkdirs();
        }
        String[] split = name.split("\\.");
        //获取截图文件名
        String photoName = split[0]+".jpg";
        //截图本地路径
        String coverPath = realPath+"\\"+photoName;
        //截取视频第一帧封面
        try {
            InterceptorVideoPhotoUtil.fetchFrame(netFilePath,coverPath);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //上传截图到阿里云
        AliyunOssUtils.uploadLocal("photo/"+photoName,coverPath);
        //删除本地截图
        File file1 = new File(coverPath);
        if(file1.isFile()&&file1.exists()){
            file1.delete();
        }
        //设置数据库文件路径
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);
        Video video = new Video();
        video.setPath("https://yingx-wang.oss-cn-beijing.aliyuncs.com/video/"+name);
        video.setCover("https://yingx-wang.oss-cn-beijing.aliyuncs.com/photo/"+photoName);
        videoMapper.updateByExampleSelective(video,example);
    }

    @Override
    public void uploadAliyun(MultipartFile path, String id) {
        //获取文件名
        String originalFilename = path.getOriginalFilename();
        String name = new Date().getTime()+"-"+originalFilename;
        //将文件上传到阿里云
        AliyunOssUtils.uploadAliyun(path,"video/"+name);

        String[] split = name.split("\\.");
        //获取截图文件名
        String photoName = split[0]+".jpg";
        //通过阿里云工具截取封面并上传
        AliyunOssUtils.interceptVideoPhoto("video/"+name,"photo/"+photoName);
        //设置数据库文件路径
        VideoExample example = new VideoExample();
        example.createCriteria().andIdEqualTo(id);
        Video video = new Video();
        video.setPath("https://yingx-wang.oss-cn-beijing.aliyuncs.com/video/"+name);
        video.setCover("https://yingx-wang.oss-cn-beijing.aliyuncs.com/photo/"+photoName);
        videoMapper.updateByExampleSelective(video,example);

        Video video1 = videoMapper.selectOneByExample(example);
        //添加索引
        videoResposity.save(video1);
    }

    @DelCache
    @Override
    @LogAnnotation(name = "修改视频")
    public void update(Video video) {
        videoMapper.updateByPrimaryKeySelective(video);
    }

    @DelCache
    @Override
    @LogAnnotation(name = "删除视频")
    public Map<String, Object> delete(Video video) {
        //创建一个map集合
        Map<String,Object> map = new HashMap<>();
       try{
           //根据传递的video获取数据库数据
           VideoExample example = new VideoExample();
           example.createCriteria().andIdEqualTo(video.getId());
           Video video1 = videoMapper.selectOneByExample(example);
           //删除数据
           videoMapper.deleteByExample(example);

           //删除阿里云有关数据
           //1.获取视频和封面的文件名
           String[] pathSplit = video1.getPath().split("/");
           String[] coverSplit = video1.getCover().split("/");
           String pathName = pathSplit[pathSplit.length-2]+"/"+pathSplit[pathSplit.length-1];
           String coverName = coverSplit[coverSplit.length-2]+"/"+coverSplit[coverSplit.length-1];

           //2.删除封面和视频
           AliyunOssUtils.deleteFile(pathName);
           AliyunOssUtils.deleteFile(coverName);
           //删除索引
           videoResposity.delete(video);
           map.put("status","200");
           map.put("message","刪除成功");
       }catch(Exception e){
           e.printStackTrace();
           map.put("status","400");
           map.put("message","刪除失敗");
       }
        return map;
    }

    @AddCaches
    @Override
    //前台页面查询所有视频
    public List<VideoPo> queryByReleaseTime() {
        List<VideoPo> videoPos = videoMapper.queryByReleaseTime();
        for (VideoPo videoPo : videoPos) {
            String id = videoPo.getId();
            //根据id查询点赞数
            Integer likeCount=10;
            videoPo.setLikeCount(likeCount);
        }
        return videoPos;
    }

    @Override
    //前台页面根据视频名称模糊查询
    public List<LikeVideoPo> queryByLikeVideoName(String content) {
        List<LikeVideoPo> likeVideoPos = videoMapper.queryByLikeVideoName(content);
        for (LikeVideoPo likeVideoPo : likeVideoPos) {
            String id = likeVideoPo.getId();
            //根据id查询点赞数
            Integer likeCount=10;
            likeVideoPo.setLikeCount(likeCount);
        }
        return likeVideoPos;
    }

    @Override
    public DetailVideoPo queryByVideoDetail(String videoId,String userId) {

        DetailVideoPo detailVideoPo = videoMapper.queryByVideoDetail(videoId);
        String id = detailVideoPo.getId();
        //根据id查询点赞数、播放次数
        Integer likeCount=8;
        Integer playCount=20;
        detailVideoPo.setLikeCount(likeCount);
        detailVideoPo.setPlayCount(playCount);
        //根据登录的用户id判断是否关注
        Boolean isAttention = true;
        detailVideoPo.setIsAttention(isAttention);
        List<LikeVideoPo> likeVideoPos = videoMapper.queryByCateId(detailVideoPo.getCategoryId(),videoId);
        for (LikeVideoPo likeVideoPo : likeVideoPos) {
            String id1 = likeVideoPo.getId();
            //根据id查询点赞数
            Integer likeCount1=33;
            likeVideoPo.setLikeCount(likeCount1);
        }
        detailVideoPo.setVideoList(likeVideoPos);
        return detailVideoPo;
    }

    @Override
    public List<LikeVideoPo> queryCateVideoList(String cateId) {
        List<LikeVideoPo> likeVideoPos = videoMapper.queryCateVideoList(cateId);
        for (LikeVideoPo likeVideoPo : likeVideoPos) {
            String id = likeVideoPo.getId();
            //根据id查询点赞数
            Integer likeCount=20;
            likeVideoPo.setLikeCount(likeCount);
        }
        return likeVideoPos;
    }

    @Override
    public List<LikeVideoPo> queryByUserVideo(String userId) {
        List<LikeVideoPo> likeVideoPos = videoMapper.queryByUserVideo(userId);
        for (LikeVideoPo likeVideoPo : likeVideoPos) {
            String id = likeVideoPo.getId();
            //根据id查询点赞数
            Integer likeCount=18;
            likeVideoPo.setLikeCount(likeCount);
        }
        return likeVideoPos;
    }

    @Override
    public List<DetailVideoPo> queryByUserMoving(String userId) {
        List<DetailVideoPo> detailVideoPos = videoMapper.queryByUserMoving();
        for (DetailVideoPo detailVideoPo : detailVideoPos) {
            //根据视频id查询点赞数，播放数
            String id = detailVideoPo.getId();
            Integer likeCount=88;
            Integer playCount=18;
            detailVideoPo.setLikeCount(likeCount);
            detailVideoPo.setPlayCount(playCount);
            //根据登录的用户id判断是否关注
            Boolean isAttention = true;
            detailVideoPo.setIsAttention(isAttention);
        }
        return detailVideoPos;
    }

    @Override
    //检索结果
    public List<Video> querySearch(String content){

        //高亮字段
        HighlightBuilder.Field field = new HighlightBuilder
                .Field("*")
                .preTags("<span style='color:red'>")
                .postTags("</span>")
                .requireFieldMatch(false);

        //查询条件
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
                .withIndices("yingx")
                .withTypes("video")
                .withQuery(QueryBuilders.queryStringQuery(content).field("title").field("brief"))
                .withHighlightFields(field)
                .build();
           AggregatedPage<Video> videos = elasticsearchTemplate.queryForPage(nativeSearchQuery, Video.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                //获取击中的字段和高亮字段
                SearchHit[] hits = searchResponse.getHits().getHits();

                List<Video> videos = new ArrayList<>();

                for (SearchHit hit : hits) {
                    //获取索引字段
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    //获取高亮字段
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();

                    Video video = new Video();

                    if (sourceAsMap.get("id") != null) {
                        video.setId(sourceAsMap.get("id").toString());
                    }
                    if (sourceAsMap.get("title") != null) {
                        if (highlightFields.get("title") != null) {
                            video.setTitle(highlightFields.get("title").getFragments()[0].toString());
                        }else{
                            video.setTitle(sourceAsMap.get("title").toString());
                        }
                    }
                    if (sourceAsMap.get("brief") != null) {
                        if (highlightFields.get("brief") != null) {
                            video.setBrief(highlightFields.get("brief").getFragments()[0].toString());
                        }else{
                            video.setBrief(sourceAsMap.get("brief").toString());
                        }
                    }
                    if (sourceAsMap.get("path") != null) {
                        video.setPath(sourceAsMap.get("path").toString());
                    }
                    if (sourceAsMap.get("cover") != null) {
                        video.setCover(sourceAsMap.get("cover").toString());
                    }
                    if (sourceAsMap.get("publishDate") != null) {
                        video.setPublishDate(new Date(Long.valueOf(sourceAsMap.get("publishDate").toString())));
                    }
                    if (sourceAsMap.get("categoryId") != null) {
                        video.setCategoryId(sourceAsMap.get("categoryId").toString());
                    }
                    if (sourceAsMap.get("userId") != null) {
                        video.setUserId(sourceAsMap.get("userId").toString());
                    }
                    if (sourceAsMap.get("groupId") != null) {
                        video.setGroupId(sourceAsMap.get("groupId").toString());
                    }
                    if (sourceAsMap.get("status") != null) {
                        video.setGroupId(sourceAsMap.get("status").toString());
                    }
                    videos.add(video);
                }
                return new AggregatedPageImpl<T>((List<T>) videos);
            }
        });

        return videos.getContent();
    }
}
