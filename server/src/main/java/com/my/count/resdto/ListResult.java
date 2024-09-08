package com.my.count.resdto;

import com.my.count.model.HomeDto;

import java.util.List;

public class ListResult {
    int count;
    List<HomeDto> list;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<HomeDto> getList() {
        return list;
    }

    public void setList(List<HomeDto> list) {
        this.list = list;
    }
}
