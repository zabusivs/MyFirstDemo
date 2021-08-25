package com.yh.test_demo.service;


import com.yh.test_demo.dao.UserDao;
import com.yh.test_demo.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserInfoService {


    @Resource
    private UserDao userDao;

    public void add(UserInfo userInfo) {
        System.out.println(userInfo.getCreatedate());
        userDao.save(userInfo);
    }

    public void del(Long id) {
        userDao.deleteById(id);
    }

    public void update(UserInfo userInfo) {
        userDao.save(userInfo);
    }

    public UserInfo findById(Long id) {
        return userDao.findById(id).orElse(null);
    }

    public List<UserInfo> findAll() {
        return userDao.findAll();
    }

    public Page<UserInfo> findPage(Integer pageNum, Integer pageSize, String name,String address, String createdate) {
        // 构建分页查询条件
        Sort sort = new Sort(Sort.Direction.ASC,"id");
        Pageable pageRequest = PageRequest.of(pageNum - 1, pageSize, sort);
        //分条件选择查询方法
        if (address != "" && address != null && createdate!="" && createdate != null) {
            System.out.println("1");
            return userDao.findByAll(name, address, createdate, pageRequest);
        }else if(address == "" && createdate !=""){
            return userDao.findByNameAndCreatedate(name, createdate, pageRequest);
        } else if(address != "" && createdate ==""){
            return userDao.findByNameAndAddress(name,address,pageRequest);
        }else {
            return userDao.findByNameLike(name, pageRequest);
        }
    }


}
