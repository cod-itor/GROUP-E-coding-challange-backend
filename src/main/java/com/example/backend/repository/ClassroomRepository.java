package com.example.backend.repository;

import com.example.backend.model.entity.Classroom;
import com.example.backend.utils.TypeHandlerUUID;
import org.apache.ibatis.annotations.*;

@Mapper
public interface ClassroomRepository {
    @Results(id = "classroomMapper", value = {
            @Result(property = "classId", column = "class_id",typeHandler = TypeHandlerUUID.class),
            @Result(property = "className", column = "class_name")
    })
    @Select("SELECT * FROM classrooms WHERE class_name = #{className}")
    Classroom getClassByName(@Param("className") String className);
}
