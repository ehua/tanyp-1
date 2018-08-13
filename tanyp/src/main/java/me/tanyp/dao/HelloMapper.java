package me.tanyp.dao;

import me.tanyp.param.HelloParam;
import org.springframework.stereotype.Repository;

/**
 * Created by tanyp on 2018/8/12
 */
@Repository
public interface HelloMapper {

    void create(HelloParam hello);

}
