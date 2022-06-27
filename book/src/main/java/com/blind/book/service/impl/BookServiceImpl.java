package com.blind.book.service.impl;

import com.blind.book.Dto.PythonRequestDto;
import com.blind.book.Utils.SendPictureUtil;
import com.blind.book.VO.BookListVo;
import com.blind.book.VO.BookVo;
import com.blind.book.repository.BookMapper;
import com.blind.book.service.BookService;
import com.blind.book.service.RPCService;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.Base64;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {
    @Resource
    SendPictureUtil sendPictureUtil;
    @Resource
    RPCService rpcService;
    @Resource
    BookMapper bookMapper;
    private static Gson gson=new Gson();
    @Override
    public String getResult(String s) throws Exception {


            //byte[] b = Base64.getDecoder().decode(sendPictureUtil.doRequest(s));
            String resp= sendPictureUtil.doRequest(s);
            JsonParse myJsonParse = gson.fromJson(resp, JsonParse.class);
            String textBase64Decode=new String(Base64.getDecoder().decode(myJsonParse.payload.result.text), "UTF-8");
            return rpcService.getPinyin(new PythonRequestDto(textBase64Decode));

    }

    @Override
    public List<BookListVo> getBookList(Integer userId) {
        return bookMapper.getBookList(userId);
    }

    @Override
    public BookVo getBook(Integer bookId) {
       return bookMapper.getBook(bookId);
    }
}
class JsonParse{
    Header header;
    Payload payload;
}
class Header{
    int code;
    String message;
    String sid;
}
class Payload{
    Result result;
}
class Result{
    String text;
}
