package me.tanyp.dao;

import me.tanyp.param.HelloParam;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by tanyp on 2018/8/12
 */
@Repository
public interface HelloMapper {

    void create(HelloParam hello);

    void delete(Object obj);

    List<Object> list(Object obj);

    void modify(Object obj);
}
