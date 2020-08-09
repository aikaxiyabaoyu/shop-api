package com.fh.shop.api.classify.biz;

import com.alibaba.fastjson. JSONObject;
import com.fh.shop.api.classify.mapper.IClassifyMapper;
import com.fh.shop.api.classify.po.Classify;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("classifyService")
public class ClassifyServiceImpl implements IClassifyService{

    @Autowired
    private IClassifyMapper classifyMapper;

    @Override
    public ServerResponse queryClassifyAll() {
        String classifyListStr = RedisUtil.get("classifyList");
        if(StringUtils.isNotEmpty(classifyListStr)){
            List<Classify> classifyList = JSONObject.parseArray(classifyListStr, Classify.class);

            return ServerResponse.success(classifyList);
        }

        List<Classify> classifyList = classifyMapper.selectList(null);

        RedisUtil.set("classifyList",JSONObject.toJSONString(classifyList));

        return ServerResponse.success(classifyList);
    }
}
