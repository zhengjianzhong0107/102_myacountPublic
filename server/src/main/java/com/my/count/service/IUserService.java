package com.my.count.service;



import com.my.count.model.UserDto;
import com.my.count.reqdto.UserQuerydto;
import com.my.count.resdto.ListResult;
import java.util.List;
import java.util.Map;

public interface IUserService {
    boolean add(UserDto dto);

    boolean update(UserDto dto);

    int delete(List<Integer> ids);

    UserDto detail(int id);

    Map<String,Object> login(UserDto dto);
}
