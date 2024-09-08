package com.my.count.dao;

import com.my.count.model.HomeDto;
import com.my.count.reqdto.Querydto;
import com.my.count.resdto.SumDto;
import com.my.count.resdto.VsDto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface IHomeMapper {
    List<HomeDto> list(Querydto querydto);

    int count(Querydto querydto);

    List<SumDto> sum(Querydto querydto);

    List<VsDto> vs(Querydto querydto);

    int num(Querydto querydto);

    boolean add(HomeDto dto);

    boolean update(HomeDto dto);

    int delete(@Param("ids") List<Integer> ids);

    HomeDto detail(int id);


}
