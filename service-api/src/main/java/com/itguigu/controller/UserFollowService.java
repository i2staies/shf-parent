package com.itguigu.controller;

import com.itguigu.base.BaseService;
import com.itguigu.entity.UserFollow;

public interface UserFollowService extends BaseService<UserFollow> {
    /**
     * 添加关注
     * @param userId
     * @param houseId
     */
    void addFollow(Long userId,Long houseId);

    void cancelFollow(Long userId,Long houseId);
}