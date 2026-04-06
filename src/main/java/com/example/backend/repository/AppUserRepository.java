package com.example.backend.repository;

import com.example.backend.model.entity.AppUser;
import org.apache.ibatis.annotations.*;


@Mapper
public interface AppUserRepository {

    @Results(id = "appUserMapper", value = {
            @Result(property = "appUserId", column = "app_user_id", typeHandler = TypeHandlerUUID.class),
            @Result(property = "userName", column = "username"),
    })

    @ResultMap("appUserMapper")
    @Select("""
    SELECT * FROM app_users 
    WHERE email = #{identifier} OR username = #{identifier} 
    LIMIT 1
    """)
    AppUser getUserByEmailOrUsername(@Param("identifier") String identifier);
}
