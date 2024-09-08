package com.my.count.controller;

import com.my.count.model.UserDto;
import com.my.count.service.IUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/user")
@Api(tags = "首页")
public class UserController {

    @Autowired
    IUserService userService;

    @PostMapping("/add")
    @ApiOperation("添加")
    public ResponseEntity<Boolean> add(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.add(dto));
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public ResponseEntity<Boolean> update(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.update(dto));
    }


    @PostMapping("/delete")
    @ApiOperation("批量删除")
    public ResponseEntity<Integer> delete(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(userService.delete(ids));
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public ResponseEntity<UserDto> detail(@RequestParam("id") int id) {
        return ResponseEntity.ok(userService.detail(id));
    }

    @PostMapping("/login")
    @ApiOperation("登录")
    public ResponseEntity<Map> login(@RequestBody UserDto dto) {
        return ResponseEntity.ok(userService.login(dto));
    }
}
