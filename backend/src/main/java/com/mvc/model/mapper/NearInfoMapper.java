package com.mvc.model.mapper;

import com.mvc.model.vo.Address;
import com.mvc.model.vo.School;
import com.mvc.model.vo.Station;
import org.apache.ibatis.annotations.Mapper;
import java.sql.SQLException;
import java.util.List;

@Mapper
public interface NearInfoMapper {
    List<School> getSchoolListByAddress(Address address) throws SQLException;
    List<Station> getStationListByAddress(Address address) throws SQLException;
}
