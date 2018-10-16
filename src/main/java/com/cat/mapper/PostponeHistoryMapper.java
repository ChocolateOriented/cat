package com.cat.mapper;

import com.cat.module.entity.PostponeHistory;
import java.util.List;

public interface PostponeHistoryMapper {
    int deleteByPrimaryKey(Long id);

    int insert(PostponeHistory record);

    PostponeHistory selectByPrimaryKey(Long id);

    List<PostponeHistory> selectAll();

    int updateByPrimaryKey(PostponeHistory record);

    List<PostponeHistory> selectHistoriesByOrderId(String orderId);
}