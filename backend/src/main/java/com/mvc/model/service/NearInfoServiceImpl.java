package com.mvc.model.service;

import com.mvc.model.mapper.NearInfoMapper;
import com.mvc.model.vo.Address;
import com.mvc.model.vo.Qna;
import com.mvc.model.vo.School;
import com.mvc.model.vo.Station;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;

@Service
public class NearInfoServiceImpl implements NearInfoService{

    NearInfoMapper mapper;

    @Autowired
    public NearInfoServiceImpl(NearInfoMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<School> getSchoolListByAddress(Address address) throws SQLException {
        return mapper.getSchoolListByAddress(address);
    }

    @Override
    public List<Station> getStationListByAddress(Address address) throws SQLException {
        return mapper.getStationListByAddress(address);
    }
}
