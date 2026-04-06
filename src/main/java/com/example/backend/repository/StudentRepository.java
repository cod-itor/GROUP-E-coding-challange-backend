package com.example.backend.repository;

import com.example.backend.model.entity.Student;
import com.example.backend.utils.TypeHandlerUUID;
import org.apache.ibatis.annotations.*;

import java.util.UUID;

@Mapper
public interface StudentRepository {
    @Select("SELECT * FROM students WHERE stu_id = #{stuId}")
    @Results(value = {
            @Result(property = "stuId",   column = "stu_id",   typeHandler = TypeHandlerUUID.class),
            @Result(property = "classId", column = "class_id", one = @One(select = "com.example.backend.repository.ClassroomRepository.getClassByName")),
            @Result(
                    property = "userId",
                    column = "user_id",
                    one = @One(select = "com.example.backend.repository.AppUserRepository.getUserById")
            )
    })
    Student getStudentById(@Param("stuId") UUID stuId);

    @Insert("INSERT INTO students (user_id, class_id) VALUES (#{userId, typeHandler=com.example.backend.utils.TypeHandlerUUID}, #{classId, typeHandler=com.example.backend.utils.TypeHandlerUUID})")
    void insertStudent(@Param("userId") UUID userId, @Param("classId") UUID classId);
}
