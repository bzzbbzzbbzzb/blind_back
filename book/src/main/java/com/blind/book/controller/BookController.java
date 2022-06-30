package com.blind.book.controller;

import com.blind.book.Dto.BookDto;
import com.blind.book.Dto.PythonRequestDto;
import com.blind.book.VO.BookListVo;
import com.blind.book.VO.BookVo;
import com.blind.book.VO.StudyVo;
import com.blind.book.domain.CommonResult;
import com.blind.book.domain.WebSocket;
import com.blind.book.service.RPCService;
import com.blind.book.service.impl.BookServiceImpl;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

import java.util.List;
import java.util.Objects;

import static com.blind.book.constant.WSMap.*;

@RestController
@RequestMapping("/book")
public class BookController {
    @Resource
    BookServiceImpl bookService;
    @Resource
    RPCService rpcService;
    @PostMapping("/upload")
    public CommonResult picIdentification(@RequestBody BookDto bookDto) throws Exception {
        if(bookDto.getIsBig().equals("false")){
            String s = bookService.getResult(bookDto.getDetails());
            return new CommonResult().success().message(s);
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
        BookVo bookVo = bookService.getBook(bookId);
        if(!Objects.isNull(deviceId)) {
        WebSocket webSocket = wsMap.get(deviceId);
        webSocket.sendMessage("{\"type\":\"getBook\",\"bookId\":" + bookId +","+"\"userId\""+userId+","+ "Braille:" + bookVo.getBraille() + "}");
        }
        return new CommonResult().success().put("text", bookVo.getText());
    }
    @GetMapping("/updateBook/{userId}/{bookId}/{bookOffset}")
    public CommonResult updateOffset(@PathVariable("userId")Integer userId,@PathVariable("bookId")Integer bookId,@PathVariable("bookOffset")Integer bookOffset){
        String deviceId = bindMap.get(userId);
        BookListVo bookListVo = new BookListVo();
        bookListVo.setBookId(bookId);
        bookListVo.setBookOffset(bookOffset);
        bookService.updateOff(userId, bookId, bookOffset);
        if(!Objects.isNull(deviceId)) {
        WebSocket webSocket = wsMap.get(deviceId);
            webSocket.sendMessage("\"type\":\"bookChange\",\"bookOffset\":"+bookOffset);
        }
        return new CommonResult().success().message("更新成功");
    }
    @GetMapping("/updateSingle/{userId}/{sOffset}")
    @PreAuthorize("hasAuthority('bl:read')")
    public CommonResult updateOffset(@PathVariable("userId")Integer userId,@PathVariable("sOffset")Integer sOffset){
        String deviceId = bindMap.get(userId);
        BookListVo bookListVo =new BookListVo();
        bookListVo.setSOffset(sOffset);
        bookService.updateSOff(userId,sOffset);
        if(!Objects.isNull(deviceId)){
        WebSocket webSocket = wsMap.get(deviceId);
            webSocket.sendMessage("\"type\":\"pageChange\",\"sOffset\":"+sOffset);
        }
        return new CommonResult().success().message("更新成功");
    }
    @GetMapping("/getStudy/{userId}")
    @PreAuthorize("hasAuthority('bl:read')")
    public CommonResult getStudy(@PathVariable("userId")Integer userId){
        List<StudyVo> studyVos = bookService.getStudy();
        String deviceId = bindMap.get(userId);
        if(!Objects.isNull(deviceId)){
            WebSocket webSocket = wsMap.get(deviceId);
            webSocket.sendMessage("{\"type\":\"getStudy\"," +"\"userId\""+userId+","+ "details:"+studyVos+"}");
        }
        return new CommonResult().success().put("total",studyVos.size()).put("study",studyVos);
    }


}
