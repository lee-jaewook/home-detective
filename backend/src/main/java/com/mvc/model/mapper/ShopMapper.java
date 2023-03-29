package com.mvc.model.mapper;

import com.mvc.model.vo.Shop;
import org.apache.ibatis.annotations.Mapper;

import java.util.ArrayList;

@Mapper
public interface ShopMapper {

	ArrayList<Shop> selectAll();
}
