package com.baizhi.wy.resposity;

import com.baizhi.wy.entity.Video;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface VideoResposity extends ElasticsearchRepository<Video,String> {
}
