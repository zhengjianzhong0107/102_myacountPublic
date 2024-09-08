package com.my.count.servicempl;

import com.my.count.dao.IHomeMapper;
import com.my.count.model.HomeDto;
import com.my.count.reqdto.Querydto;
import com.my.count.resdto.ListResult;
import com.my.count.resdto.SumDto;
import com.my.count.resdto.VsDto;
import com.my.count.service.IHomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.ws.http.HTTPException;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class HomeService implements IHomeService {

    @Autowired
    IHomeMapper mapper;

    private String filterType(String type, String keyWorld) {

        String queryString = "";
        List<String> types = new ArrayList<String>() {{
            add("label");
            add("custom");
            add("type");
            add("comment");
        }};
        if (type.equals("all")) {
            for (String tp : types
            ) {
                queryString += tp + " like '%" + keyWorld + "%' or ";
            }
            queryString = queryString.substring(0, queryString.length() - 3);
        } else if (types.contains(type)) {
            queryString += type + " like '%" + keyWorld + "%'";
        } else {
            throw new HTTPException(400);
        }

        return queryString;
    }

    private boolean filterOrderProp(String prop) {
        List<String> types = new ArrayList<String>() {{
            add("label");
            add("cusDate");
            add("type");
            add("custom");
            add("count");
            add("used");
        }};
        return !types.contains(prop);
    }

    @Override
    public ListResult list(Querydto querydto) {
        querydto.setPage((querydto.getPage() - 1) * querydto.getNum());
        querydto.setType(filterType(querydto.getType(), querydto.getKeyword()));
        querydto.setOrder((filterOrderProp(querydto.getOrderProp()) || querydto.getOrder() == null) ? null : querydto.getOrder().equals("ascending") ? "asc" : "desc");
        ListResult result = new ListResult();
        result.setList(mapper.list(querydto));
        result.setCount(mapper.count(querydto));
        return result;
    }

    @Override
    public boolean add(HomeDto dto) {
        return mapper.add(dto);
    }

    @Override
    public boolean update(HomeDto dto) {
        return mapper.update(dto);
    }

    @Override
    public int delete(List<Integer> ids) {
        return mapper.delete(ids);
    }

    @Override
    public HomeDto detail(int id) {
        return mapper.detail(id);
    }

    @Override
    public List<SumDto> sum(Querydto querydto) {
        querydto.setType(filterType(querydto.getType(), querydto.getKeyword()));
        return mapper.sum(querydto);
    }

    @Override
    public List<VsDto> vs(Querydto querydto) {
        querydto.setType(filterType(querydto.getType(), querydto.getKeyword()));
        return mapper.vs(querydto);
    }

    @Override
    @Transactional
    public Boolean deal(String path) throws IOException, ParseException {
        File root = new File("./upload");

        String fullPath = root.getCanonicalPath() + File.separator + "common" + File.separator + path;
        File dir = new File(fullPath);

        BufferedReader br = null;
        String line = "";
        String splitBy = ",";

        br = new BufferedReader(new FileReader(dir));
        while ((line = br.readLine()) != null) {
            String[] item = line.split(splitBy);
            System.out.println(item);
            HomeDto homeDto = new HomeDto();

            homeDto.setLabel(item[0]);
            homeDto.setCustom(item[1]);
            homeDto.setCount(Float.parseFloat(item[2]));
            homeDto.setType(item[3]);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            homeDto.setCusDate(new java.sql.Date(sdf.parse(item[4]).getTime()));
            homeDto.setUsed(item[5].equals("true"));
            homeDto.setComment(item[6]);
            mapper.add(homeDto);
        }
        return true;
    }

    @Override
    public ListResult export(Querydto querydto) {
        querydto.setPage((querydto.getPage() - 1) * querydto.getNum());
        querydto.setType(filterType(querydto.getType(), querydto.getKeyword()));
        querydto.setOrder((filterOrderProp(querydto.getOrderProp()) || querydto.getOrder() == null) ? null : querydto.getOrder().equals("ascending") ? "asc" : "desc");
        ListResult result = new ListResult();
        result.setList(mapper.list(querydto));
        result.setCount(mapper.count(querydto));
        return result;
    }
}
