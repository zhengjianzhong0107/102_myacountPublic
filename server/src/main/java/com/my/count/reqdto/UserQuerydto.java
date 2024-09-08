package com.my.count.reqdto;

import io.swagger.annotations.ApiModelProperty;

public class UserQuerydto {
    @ApiModelProperty(value = "用户名")
    String name;
    @ApiModelProperty(value = "密码")
    String password;

    @ApiModelProperty(value = "id")
    int id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
