package com.yh.test_demo.dao;

import com.yh.test_demo.entity.UserInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends JpaRepository<UserInfo, Long> {

    @Query(value = "select * from user_info where name like %?1%", nativeQuery = true)
    Page<UserInfo> findByNameLike(String name, Pageable pageRequest);

    @Query(value = "select * from user_info where name like %?1% and address = ?2 and create_date = ?3", nativeQuery = true)
    Page<UserInfo> findByAll(String name,String address, String createdate , Pageable pageRequest);

    @Query(value = "select * from user_info where name like %?1% and address = ?2", nativeQuery = true)
    Page<UserInfo> findByNameAndAddress(String name,String address, Pageable pageRequest);

    @Query(value = "select * from user_info where name like %?1% and create_date = ?2", nativeQuery = true)
    Page<UserInfo> findByNameAndCreatedate(String name,String createdate, Pageable pageRequest);
}
