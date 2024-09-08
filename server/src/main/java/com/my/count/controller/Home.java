package com.my.count.controller;

import com.my.count.model.HomeDto;
import com.my.count.reqdto.Querydto;
import com.my.count.resdto.ListResult;
import com.my.count.resdto.SumDto;
import com.my.count.resdto.VsDto;
import com.my.count.service.IHomeService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("api/home")
@Api(tags = "首页")
public class Home {

    @Autowired
    IHomeService homeService;

    @PostMapping("/list")
    @ApiOperation("列表查询")
    public ResponseEntity<ListResult> list(@RequestBody Querydto querydto) {
        return ResponseEntity.ok(homeService.list(querydto));
    }

    @PostMapping("/add")
    @ApiOperation("添加")
    public ResponseEntity<Boolean> add(@RequestBody HomeDto dto) {
        return ResponseEntity.ok(homeService.add(dto));
    }

    @PostMapping("/update")
    @ApiOperation("修改")
    public ResponseEntity<Boolean> update(@RequestBody HomeDto dto) {
        return ResponseEntity.ok(homeService.update(dto));
    }


    @PostMapping("/delete")
    @ApiOperation("批量删除")
    public ResponseEntity<Integer> delete(@RequestBody List<Integer> ids) {
        return ResponseEntity.ok(homeService.delete(ids));
    }

    @GetMapping("/detail")
    @ApiOperation("详情")
    public ResponseEntity<HomeDto> detail(@RequestParam("id") int id) {
        return ResponseEntity.ok(homeService.detail(id));
    }

    @PostMapping("/sum")
    @ApiOperation("分类求和")
    public ResponseEntity<List<SumDto>> sum(@RequestBody Querydto querydto) {
        return ResponseEntity.ok(homeService.sum(querydto));
    }

    @PostMapping("/vs")
    @ApiOperation("消费对比")
    public ResponseEntity<List<VsDto>> vs(@RequestBody Querydto querydto) {
        return ResponseEntity.ok(homeService.vs(querydto));
    }


    @ApiOperation("上传文件")
    @PostMapping("/upload")
    public ResponseEntity upload(@RequestParam("file") MultipartFile file) throws IOException {
        String fileName = file.getOriginalFilename();
        String extensionName = fileName.substring(fileName.indexOf("."));
        String newFileName = String.valueOf(System.currentTimeMillis()) + extensionName;
        File root = new File("./upload");

        String newsPath = root.getCanonicalPath() + File.separator + "common";
        File dir = new File(newsPath);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = newsPath + File.separator + newFileName;
        File dest = new File(filePath);
        file.transferTo(dest);
        Map<String, String> resultMap = new HashMap<>();
        resultMap.put("name", newFileName);
        return ResponseEntity.ok(resultMap);
    }

    @ApiOperation("处理文件")
    @GetMapping("/deal")
    public ResponseEntity<Boolean> deal(@RequestParam("path") String path) throws IOException, ParseException {
        return ResponseEntity.ok(homeService.deal(path));
    }

    @PostMapping("/export")
    @ApiOperation("列表查询")
    public ResponseEntity<ListResult> export(@RequestBody Querydto querydto) {
        return ResponseEntity.ok(homeService.export(querydto));
    }
}
