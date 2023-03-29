package com.mvc.model.service;

import com.mvc.model.vo.Address;
import com.mvc.model.vo.Qna;
import com.mvc.model.vo.School;
import com.mvc.model.vo.Station;

import java.sql.SQLException;
import java.util.List;

public interface NearInfoService {
    List<School> getSchoolListByAddress(Address address) throws SQLException;
    List<Station> getStationListByAddress(Address address) throws SQLException;
}
