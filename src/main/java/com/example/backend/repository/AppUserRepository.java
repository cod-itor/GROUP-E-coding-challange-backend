package com.example.backend.repository;

import com.example.backend.model.entity.AppUser;
import com.example.backend.model.request.AppUserRequest;
import com.example.backend.utils.TypeHandlerUUID;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface AppUserRepository {

    @Results(id = "appUserMapper", value = {
            @Result(property = "userId", column = "user_id", typeHandler = TypeHandlerUUID.class),
            @Result(property = "fullName", column = "full_name"),
            @Result(property = "phoneNumber", column = "phone_number"),
            @Result(property = "userPhoto", column = "user_photo"),
            @Result(property = "isStudent", column = "is_student"),
            @Result(property = "genNum", column = "gen_num"),
            @Result(property = "createdAt", column = "created_at"),
            @Result(property = "updatedAt", column = "updated_at")
    })
    @Select("SELECT u.*, g.gen_num FROM users u LEFT JOIN generations g ON u.gen_id = g.gen_id WHERE u.email = #{email}")
    AppUser getUserByEmail(String email);

    @ResultMap("appUserMapper")
    @Select("SELECT u.*, g.gen_num FROM users u LEFT JOIN generations g ON u.gen_id = g.gen_id WHERE u.user_id = #{userId}")
    AppUser getUserById(@Param("userId") UUID userId);

    @Insert("""
        INSERT INTO users (user_id, full_name, email, password, phone_number, is_student, gen_id)
        VALUES (gen_random_uuid(), #{req.fullName}, #{req.email}, #{req.password},
                #{req.phoneNumber}, #{req.isStudent}, #{genId, typeHandler=com.example.backend.utils.TypeHandlerUUID})
    """)
    int insertUser(@Param("req") AppUserRequest request, @Param("genId") UUID genId);

    @ResultMap("appUserMapper")
    @Select("SELECT u.*, g.gen_num FROM users u LEFT JOIN generations g ON u.gen_id = g.gen_id WHERE u.email = #{email}")
    AppUser getUserAfterInsert(String email);
}