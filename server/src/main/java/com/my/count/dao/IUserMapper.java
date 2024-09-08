package com.my.count.dao;

import com.my.count.model.UserDto;
import com.my.count.reqdto.Querydto;
import com.my.count.reqdto.UserQuerydto;
import com.my.count.resdto.SumDto;
import com.my.count.resdto.VsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IUserMapper {
    List<UserDto> list(UserQuerydto querydto);

    int count(UserQuerydto querydto);

    Integer sum(UserQuerydto querydto);

    boolean add(UserDto dto);

    boolean update(UserDto dto);

    int delete(@Param("ids") List<Integer> ids);

    UserDto detail(int id);
}
