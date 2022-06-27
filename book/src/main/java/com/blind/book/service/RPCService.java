package com.blind.book.service;

import com.blind.book.Dto.PythonRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name="python-service")
public interface RPCService {
    @PostMapping("/getPinyin")
    String getPinyin(@RequestBody PythonRequestDto pythonRequestDto);
    @PostMapping("/save")
    String save(@RequestBody PythonRequestDto pythonRequestDto);
    @PostMapping("/compress")
    String compress(@RequestBody PythonRequestDto pythonRequestDto);
}
