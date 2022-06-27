package com.blind.user.repository;

import com.blind.user.Domain.VerifyUser;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserMapper {
    int ifExistUser(@Param("email") String email);
    void registry(@Param("email")String email,@Param("password")String password);
    VerifyUser verify(@Param("email") String email);
    void record(@Param("userId") Integer userId, @Param("bookId")List<Integer> bookId);
    List<Integer> getBookList();
    int getUserId(@Param("email") String email);
}
