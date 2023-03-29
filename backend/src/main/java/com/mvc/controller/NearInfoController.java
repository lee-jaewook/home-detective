package com.mvc.controller;

import com.mvc.model.mapper.NearInfoMapper;
import com.mvc.model.service.NearInfoService;
import com.mvc.model.service.NoticeService;
import com.mvc.model.vo.Address;
import com.mvc.model.vo.Notice;
import com.mvc.model.vo.School;
import com.mvc.model.vo.Station;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api("아파트 거래정보  API V1")
@RestController
@RequestMapping(value = "/near")
public class NearInfoController {

    private NearInfoService infoService;

    private final Logger logger = LoggerFactory.getLogger(AptController.class);
    static final String OK = "success";
    static final String NO = "fail";

    @Autowired
    public NearInfoController(NearInfoService infoService) {
        this.infoService = infoService;
    }

    /* 주변 학교 조회 */
    @ApiOperation(value = "주변 학교 조회", notes = "시도-구군을 입력받아 학교 목록을 반환해 줍니다.")
    @GetMapping("/school/{sidoname}/{gugunname}")
    public ResponseEntity<?> getSchoolListByAddress(@PathVariable("sidoname") String sidoName, @PathVariable("gugunname") String gugunName) {
        try {
            List<School> schoolList = infoService.getSchoolListByAddress(new Address(sidoName, gugunName, null, null));
            if (schoolList != null && !schoolList.isEmpty()) {
                return new ResponseEntity<List<School>>(schoolList, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Void>>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage() + " error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 주변 역 조회 */
    @ApiOperation(value = "주변 역 조회", notes = "시도-구군을 입력받아 역 목록을 반환해 줍니다.")
    @GetMapping("/station/{sidoname}/{gugunname}")
    public ResponseEntity<?> getStationListByAddress(@PathVariable("sidoname") String sidoName, @PathVariable("gugunname") String gugunName) {
        try {
            List<Station> stationList = infoService.getStationListByAddress(new Address(sidoName, gugunName, null, null));
            if (stationList != null && !stationList.isEmpty()) {
                return new ResponseEntity<List<Station>>(stationList, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Void>>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage() + " error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
