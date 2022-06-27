package com.blind.book.repository;

import com.blind.book.Dto.BindDto;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceMapper {
    void bind(@Param("deviceId") String deviceId,@Param("userId") Integer userId);
    Integer getBind(@Param("deviceId") String deviceId);
}
