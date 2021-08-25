package com.yh.test_demo.controller;

import com.yh.test_demo.common.Result;
import com.yh.test_demo.entity.UserInfo;
import com.yh.test_demo.service.UserInfoService;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserInfoController {

    @Resource
    private UserInfoService userInfoService;

    //新增用户
    @PostMapping
    public Result add(@RequestBody UserInfo userInfo) {
        userInfoService.add(userInfo);
        return Result.success();
    }


    //修改用户
    @PutMapping
    public Result update(@RequestBody UserInfo userInfo) {
        userInfoService.update(userInfo);
        return Result.success();
    }

    //删除用户
    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        userInfoService.del(id);
        return Result.success();
    }



    //根据id查询信息
    @GetMapping("/{id}")
    public Result<UserInfo> findById(@PathVariable Long id) {
        return Result.success(userInfoService.findById(id));
    }

    //查询全部
    @GetMapping
    public Result<List<UserInfo>> findAll() {
        return Result.success(userInfoService.findAll());
    }

    @GetMapping("/page")
    public Result<Page<UserInfo>> findByPage(@RequestParam(defaultValue = "1") Integer pageNum,
                                        @RequestParam(defaultValue = "5") Integer pageSize,
                                        @RequestParam(required = false) String name,
                                        @RequestParam(required = false) String address,
                                             @RequestParam(required = false) String createdate) {
        System.out.println(pageNum +","+ pageSize + name + address + createdate);
        return Result.success(userInfoService.findPage(pageNum, pageSize, name, address, createdate));

    }

}
