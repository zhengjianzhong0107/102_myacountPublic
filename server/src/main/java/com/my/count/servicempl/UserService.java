package com.my.count.servicempl;

import com.my.count.dao.IUserMapper;
import com.my.count.model.UserDto;
import com.my.count.reqdto.Querydto;
import com.my.count.reqdto.UserQuerydto;
import com.my.count.resdto.SumDto;
import com.my.count.resdto.VsDto;
import com.my.count.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService implements IUserService {

    @Autowired
    IUserMapper mapper;

    @Override
    public boolean add(UserDto dto) {
        UserQuerydto querydto = new UserQuerydto();
        querydto.setName(dto.getName());
        List<UserDto> list = mapper.list(querydto);
        if(list !=null && list.size()>0){
            return false;
        }
        return mapper.add(dto);
    }

    @Override
    public boolean update(UserDto dto) {
        return mapper.update(dto);
    }

    @Override
    public int delete(List<Integer> ids) {
        return mapper.delete(ids);
    }

    @Override
    public UserDto detail(int id) {
        return mapper.detail(id);
    }

    @Override
    public Map<String,Object> login(UserDto dto) {
        Map<String,Object> map = new HashMap<>();
        map.put("success",false);
        UserQuerydto querydto = new UserQuerydto();
        if(StringUtils.isEmpty(dto.getName()) || StringUtils.isEmpty(dto.getPassword())){
            return map;
        }
        querydto.setName(dto.getName());
        querydto.setPassword(dto.getPassword());
        List<UserDto> list = mapper.list(querydto);
        if(list !=null && list.size()>0){
            map.put("success",true);
            map.put("user",list.get(0));
        }
        return map;
    }
}
