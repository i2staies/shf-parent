package com.itguigu.mapper;

import com.itguigu.base.BaseMapper;
import com.itguigu.entity.UserFollow;
import org.apache.ibatis.annotations.Param;

public interface UserFollowMapper extends BaseMapper<UserFollow> {
    UserFollow getByUserIdAndHouseId(@Param("userId") Long userId, @Param("houseId") Long houseId);
}