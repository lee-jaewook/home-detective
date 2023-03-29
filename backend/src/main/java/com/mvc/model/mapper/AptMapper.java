package com.mvc.model.mapper;

import com.mvc.model.vo.*;
import org.apache.ibatis.annotations.Mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

@Mapper
public interface AptMapper {
    List<Address> getAddressListByGugun(String word) throws SQLException;
    List<Apt> getAptListByName(String word) throws SQLException;
    List<Apt> getAptListByAddress(Address address) throws SQLException;
    Apt getAptByAptCode(String aptCode) throws SQLException;
    List<AptDeal> getAptDealListByAptCode(Map map) throws SQLException;
    String getTotalCountOfDealListByAptCode(String aptCode) throws SQLException;
    List<Apt> getAptLikeListByUserId(String userId) throws SQLException;
    Boolean addAptLikeListByLike(Like like) throws SQLException;
    Boolean deleteAptLikeListByLike(Like like) throws SQLException;
    boolean checkLikedApt(Like like) throws SQLException;
    List<AptDealMonth> getDealCountOfAptByMonth() throws SQLException;
    List<AptDealGugun> getDealCountOfAptByGugun() throws SQLException;
}
