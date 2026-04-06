package com.example.backend.repository;

import com.example.backend.model.entity.Generation;
import com.example.backend.utils.TypeHandlerUUID;
import org.apache.ibatis.annotations.*;

@Mapper
public interface GenerationRepository {

    @Results(id = "generationMapper", value = {
            @Result(property = "genId", column = "gen_id", typeHandler = TypeHandlerUUID.class),
            @Result(property = "genNum", column = "gen_num")
    })
    @Select("SELECT * FROM generations WHERE gen_num = #{genNum}")
    Generation getGenByNum(@Param("genNum") long genNum);

    @Insert("INSERT INTO generations (gen_num) VALUES (#{num})")
    void saveGeneration(long num);

}
