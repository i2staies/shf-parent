package com.itguigu.service.impl;

import com.itguigu.base.BaseMapper;
import com.itguigu.base.BaseServiceImpl;
import com.itguigu.entity.UserFollow;
import com.itguigu.mapper.UserFollowMapper;
import com.itguigu.controller.UserFollowService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

@Service(interfaceClass = UserFollowService.class)
public class UserFollowServiceImpl extends BaseServiceImpl<UserFollow> implements UserFollowService {
    @Autowired
    private UserFollowMapper userFollowMapper;
    @Override
    public BaseMapper<UserFollow> getBaseMapper() {
        return userFollowMapper;
    }

    @Override
    public void addFollow(Long userId, Long houseId) {
        //判断用户是否已添加当前房源为关注
        UserFollow userFollow = userFollowMapper.getByUserIdAndHouseId(userId, houseId);
        if (userFollow == null) {
            userFollow = new UserFollow();
            userFollow.setUserId(userId);
            userFollow.setHouseId(houseId);
            userFollowMapper.insert(userFollow);
            return;
        }
        //如果之前已关注但是取消了，则只需要将其isDeleted设置为0
        if (userFollow != null){
            userFollow.setIsDeleted(0);
            userFollowMapper.update(userFollow);
        }
    }

    @Override
    public void cancelFollow(Long userId, Long houseId) {
        UserFollow dbUserFollow = userFollowMapper.getByUserIdAndHouseId(userId, houseId);

        if (null != dbUserFollow) {
            dbUserFollow.setIsDeleted(1);

            userFollowMapper.update(dbUserFollow);
        }
    }
}