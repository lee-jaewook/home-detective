package com.mvc.model.service;

import com.mvc.model.mapper.AptMapper;
import com.mvc.model.vo.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Service
public class AptServiceImpl implements AptService{

    private AptMapper mapper;

    @Autowired
    public AptServiceImpl(AptMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<Address> getAddressListByGugun(String word) throws SQLException {
        return mapper.getAddressListByGugun(word);
    }

    @Override
    public List<Apt> getAptListByName(String word) throws SQLException {
        return mapper.getAptListByName(word);
    }

    @Override
    public List<Apt> getAptListByAddress(Address address) throws SQLException {
        return mapper.getAptListByAddress(address);
    }

    @Override
    public Apt getAptByAptCode(String aptCode) throws SQLException {
        return mapper.getAptByAptCode(aptCode);
    }

    @Override
    public List<AptDeal> getAptDealListByAptCode(Map map) throws SQLException {
        System.out.println(map);
        System.out.println(mapper.getAptDealListByAptCode(map));
        return mapper.getAptDealListByAptCode(map);
    }

    @Override
    public String getTotalCountOfDealListByAptCode(String aptCode) throws SQLException {
        return mapper.getTotalCountOfDealListByAptCode(aptCode);
    }

    @Override
    public List<Apt> getAptLikeListByUserId(String userId) throws SQLException {
        return mapper.getAptLikeListByUserId(userId);
    }

    @Override
    public Boolean addAptLikeListByLike(Like like) throws SQLException {
        return mapper.addAptLikeListByLike(like);
    }

    @Override
    public Boolean deleteAptLikeListByLike(Like like) throws SQLException {
        return mapper.deleteAptLikeListByLike(like);
    }

    @Override
    public boolean checkLikedApt(Like like) throws SQLException {
        return mapper.checkLikedApt(like);
    }

    @Override
    public List<AptDealMonth> getDealCountOfAptByMonth() throws SQLException {
        return mapper.getDealCountOfAptByMonth();
    }

    @Override
    public List<AptDealGugun> getDealCountOfAptByGugun() throws SQLException {
        return mapper.getDealCountOfAptByGugun();
    }
}
