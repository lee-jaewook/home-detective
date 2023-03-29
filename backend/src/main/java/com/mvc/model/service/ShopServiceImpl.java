package com.mvc.model.service;

import com.mvc.model.mapper.ShopMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import com.mvc.model.vo.Shop;

@Service
public class ShopServiceImpl implements ShopService {

	ShopMapper shopdao;

	@Autowired
	public ShopServiceImpl(ShopMapper shopdao) {
		this.shopdao = shopdao;
	}


	@Override
	public ArrayList<Shop> selectAll() {

		return shopdao.selectAll();
	}

}
