package com.blind.book.controller;

import com.blind.book.Dto.BindDto;
import com.blind.book.Dto.BookDto;
import com.blind.book.Dto.PythonRequestDto;
import com.blind.book.VO.BookListVo;
import com.blind.book.VO.BookVo;
import com.blind.book.domain.CommonResult;
import com.blind.book.domain.WebSocket;
import com.blind.book.service.RPCService;
import com.blind.book.service.impl.BookServiceImpl;
import com.blind.book.service.impl.DeviceServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import java.util.List;

import static com.blind.book.constant.WSMap.bindMap;
import static com.blind.book.constant.WSMap.wsMap;

@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    DeviceServiceImpl deviceService;
    @Resource
    BookServiceImpl bookService;
    @Resource
    RPCService rpcService;
    @Resource
    WebSocket webSocket;
    @PostMapping("/upload")
    @PreAuthorize("hasAuthority('bl:read')")
    public CommonResult picIdentification(@RequestBody BookDto bookDto) throws Exception {
        if(bookDto.getIsBig().equals("false")){
            String s = bookService.getResult(bookDto.getDetails());
            return new CommonResult().success().message(s);
            //TODO  web socket通讯
        }else if(bookDto.getIsBig().equals("true")){
           String s1 = rpcService.compress(new PythonRequestDto(bookDto.getDetails()));
           return  new CommonResult().success().message(bookService.getResult(s1));
        }else{
            return new CommonResult().fail().message("isBig字段错误");
        }
    }
    @GetMapping("/bookList/{userId}")
    @PreAuthorize("hasAuthority('bl:read')")
    public CommonResult getBookList(@PathVariable("userId") Integer userId){
        return new CommonResult().success().put("bookList",bookService.getBookList(userId));
    }
    @GetMapping("/getBook/{userId}/{bookId}")
    @PreAuthorize("hasAuthority('bl:read')")
    public CommonResult getBook(@PathVariable("userId")Integer userId,@PathVariable("bookId") Integer bookId){
        String deviceId = bindMap.get(userId);
        WebSocket webSocket = wsMap.get(deviceId);
        BookVo bookVo =bookService.getBook(bookId);
        webSocket.sendMessage(bookVo.toString());
        return new CommonResult().success().put("book",bookVo);
    }
    @GetMapping("/update/{userId}/{bookOffset}/{sOffset}")
    @PreAuthorize("hasAuthority('bl:read')")
    public CommonResult updateOffset(@PathVariable("userId")Integer userId,@PathVariable("bookOffset")Integer bookOffset,@PathVariable("sOffset")Integer sOffset){
        String deviceId = bindMap.get(userId);
        WebSocket webSocket = wsMap.get(deviceId);
        List<BookListVo> bookListVo =bookService.getBookList(userId);
        webSocket.sendMessage(bookListVo.toString());
        return new CommonResult().success().message("更新成功");
    }


}
