package com.my.count.service;

import com.my.count.model.HomeDto;
import com.my.count.reqdto.Querydto;
import com.my.count.resdto.ListResult;
import com.my.count.resdto.SumDto;
import com.my.count.resdto.VsDto;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

public interface IHomeService {
    ListResult list(Querydto querydto);

    boolean add(HomeDto dto);

    boolean update(HomeDto dto);

    int delete(List<Integer> ids);

    HomeDto detail(int id);

    List<SumDto> sum(Querydto querydto);

    List<VsDto> vs(Querydto querydto);

    Boolean deal(String path) throws IOException, ParseException;

    ListResult export(Querydto querydto);
}
