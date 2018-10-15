package com.cat.service;

import com.cat.mapper.PostponeHistoryMapper;
import com.cat.module.entity.PostponeHistory;
import com.cat.module.vo.PostponeHistoryVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cyuan on 2018/10/12.
 */
@Service
public class PostponeHistoryService {
    @Autowired
    private PostponeHistoryMapper postponeHistoryMapper;

    public void insert(PostponeHistory postponeHistory) {
        postponeHistoryMapper.insert(postponeHistory);
    }

    public List<PostponeHistoryVo> fetchpostponeHistoryByOrderId(String orderId) {
        List<PostponeHistoryVo> voList = new ArrayList<>();
        List<PostponeHistory> postponeHistoryList = postponeHistoryMapper.selectHistoriesByOrderId(orderId);
        if (postponeHistoryList != null && !postponeHistoryList.isEmpty()) {
            postponeHistoryList.forEach(item -> {
                PostponeHistoryVo vo = new PostponeHistoryVo();
                vo.setId(item.getId());
                vo.setPostponeAmount(item.getRepayAmount());
                vo.setPostponeTime(item.getCreatedTime());
                voList.add(vo);
            });
        }
        return voList;
    }
}
