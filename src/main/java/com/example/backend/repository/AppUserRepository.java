package com.example.backend.repository;

import com.example.backend.model.entity.AppUser;
import com.example.backend.model.request.AppUserRequest;
import com.example.backend.utils.TypeHandlerUUID;
import org.apache.ibatis.annotations.*;
import org.hibernate.validator.constraints.pl.REGON;


@Mapper
public interface AppUserRepository {

    @Results(id = "appUserMapper", value = {
            @Result(property = "userId", column = "user_id", typeHandler = TypeHandlerUUID.class),
            @Result(property = "userName", column = "username"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "userPhoto" , column = "user_photo"),
            @Result(property = "isStudent", column = "is_student"),
            @Result(property = "createAt", column = "create_at"),
            @Result(property = "updateAt", column = "updateAt")
    })

    @Select("""
    SELECT * FROM users
    WHERE email = #{email}
    """)
    AppUser getUserByEmail(String email);

//    @Select("""
//        INSERT INTO users VALUES (gen_random_uuid(), #{req.fullName}, #{req.email}, #{req.password}, #{req.isStudent}, #{req.genId}, )
//    """)
//
//    AppUser register(@Param("req") AppUserRequest request);
}
