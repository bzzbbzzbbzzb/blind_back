package com.blind.book.controller;

import com.blind.book.Dto.BindDto;
import com.blind.book.domain.CommonResult;
import com.blind.book.service.impl.DeviceServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

import java.util.Objects;

import static com.blind.book.constant.WSMap.*;

@RestController

public class BindController {
    @Resource
    DeviceServiceImpl deviceService;
    @PostMapping("bind")
    @PreAuthorize("hasAuthority('bl:read')")
    public CommonResult bind(@RequestBody BindDto bindDto){
        deviceService.bind(bindDto.getDeviceId(),bindDto.getUserId());
        if(!Objects.isNull(notBindSet.get(bindDto.getDeviceId()))){//未绑定但是已经在线
            bindMap.put(bindDto.getUserId(),bindDto.getDeviceId());
            wsMap.put(bindDto.getDeviceId(),notBindSet.get(bindDto.getDeviceId()));
            notBindSet.remove(bindDto.getDeviceId());
        }
        return new CommonResult().success().message("绑定成功");

    }
}
