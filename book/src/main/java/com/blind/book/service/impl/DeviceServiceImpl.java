package com.blind.book.service.impl;

import com.blind.book.Dto.BindDto;
import com.blind.book.repository.DeviceMapper;
import com.blind.book.service.DeviceService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DeviceServiceImpl implements DeviceService {
    @Resource
    DeviceMapper deviceMapper;
    @Override
    public void bind(String deviceId,Integer userId) {
        deviceMapper.bind(deviceId,userId);
    }
}
