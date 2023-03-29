package com.mvc.controller;

import com.mvc.model.service.AptService;
import com.mvc.model.vo.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Api("아파트 정보  API V1")
@CrossOrigin("*")
@RequestMapping(value = "/apt")
public class AptController {

    private AptService service;

    private final Logger logger = LoggerFactory.getLogger(AptController.class);
    static final String OK = "success";
    static final String NO = "fail";

    @Autowired
    public AptController(AptService service) {
        this.service = service;
    }

    @ApiOperation(value = "구군 이름으로 구군 리스트 검색", notes = "단어를 입력받아 구군 리스트(시도, 구군, 위도, 경도)를 반환합니다.")
    @GetMapping("/find/address/{word}")
    public ResponseEntity<?> findAddressByGugun(@PathVariable String word){
        logger.info("구군 이름으로 구군 리스트 검색");
        try {
            List<Address> list = service.getAddressListByGugun(word);
            if (list != null && !list.isEmpty()) {
                return new ResponseEntity<List<Address>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "아파트 이름으로 아파트 리스트 검색", notes = "단어를 입력받아 아파트 정보를 담은 리스트를 반환합니다.")
    @GetMapping("/find/aptname/{word}")
    public ResponseEntity<?> findAptListByName(@PathVariable String word){
        logger.info("아파트 이름으로 아파트 리스트 검색");
        try {
            List<Apt> list = service.getAptListByName(word);
            if (list != null && !list.isEmpty()) {
                return new ResponseEntity<List<Apt>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 주소(시도, 구군)에 있는 전체 아파트 리스트", notes = "시도와 구군정보를 json으로 입력받아 아파트 리스트를 반환합니다.")
    @PostMapping("/find/address")
    public ResponseEntity<?> findAptListByAddress(@RequestBody Address address){
        try {
            List<Apt> list = service.getAptListByAddress(address);
            if (list != null && !list.isEmpty()) {
                return new ResponseEntity<List<Apt>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "아파트 번호로 해당 아파트의 정보를 리턴", notes = "aptCode를 입력받아 해당하는 아파트의 정보를 리턴합니다.")
    @GetMapping("/{aptCode}")
    public ResponseEntity<?> findAptByAptCode(@PathVariable String aptCode){
        try {
            Apt apt = service.getAptByAptCode(aptCode);
            if (apt != null) {
                return new ResponseEntity<Apt>(apt, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 아파트의 과거 거래 리스트", notes = "아파트 코드와 페이지번호를 입력받아 5개씩 해당 아파트의 실거래 자료를 출력합니다.")
    @GetMapping("/deal")
    public ResponseEntity<?> findAptDealListByAptCode(@RequestParam("aptCode") String aptCode, @RequestParam("pageNo") String pageNo){
        try {
            int totalCount = Integer.parseInt(service.getTotalCountOfDealListByAptCode(aptCode));
            totalCount = Math.min(totalCount, 25);
            int limit = 5; // 한번에 페이지에 보여줄 갯수
            int start = (Integer.parseInt(pageNo) - 1) * limit;

            if(totalCount != 0 && start < totalCount){
                Map<String, Object> query = new HashMap<>();
                query.put("aptCode", aptCode);
                query.put("limit", limit);
                query.put("start", start);

                List<AptDeal> deals = service.getAptDealListByAptCode(query);
                for (AptDeal deal : deals) {
                    if (deal.getDealDay().length() == 1){
                        deal.setDealDay("0"+deal.getDealDay());
                    }
                    if (deal.getDealMonth().length() == 1){
                        deal.setDealMonth("0"+deal.getDealMonth());
                    }
                    System.out.println(deal);
                }
                System.out.println(deals.size());

                Map<String, Object> result = new HashMap<>();
                Map<String, String> info = new HashMap<>();
                info.put("totalCount", String.valueOf(totalCount));
                info.put("pageNo", pageNo);
                info.put("aptCode", aptCode);

                result.put("info", info);
                result.put("deals", deals);
                return new ResponseEntity<Map>(result, HttpStatus.OK);
            }else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "특정 유저의 관심 아파트 리스트", notes = "유저의 아이디를 입력받아 유저의 관심 아파트 리스트를 반환합니다.")
    @GetMapping("/like")
    public ResponseEntity<?> findAptLikeListByUserId(@RequestParam("userid") String userId){
        try {
            List<Apt> list = service.getAptLikeListByUserId(userId);
            if (list != null && !list.isEmpty()) {
                return new ResponseEntity<List<Apt>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "관심 아파트 추가", notes = "유저의 아이디와 아파트 코드를 입력받아 유저의 관심 아파트에 추가합니다.")
    @PostMapping("/like")
    public ResponseEntity<?> addAptLikeListByLike(@RequestBody Like like){
        try {
            if (service.addAptLikeListByLike(like)) {
                return new ResponseEntity<String>(OK, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(NO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "관심 아파트 삭제", notes = "유저의 아이디와 아파트 코드를 입력받아 유저의 관심 아파트를 삭제합니다.")
    @PostMapping("/like/delete")
    public ResponseEntity<?> deleteAptLikeListByLike(@RequestBody Like like){
        try {
            if (service.deleteAptLikeListByLike(like)) {
                return new ResponseEntity<String>(OK, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(NO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "관심 아파트 여부 확인", notes = "유저의 아이디와 아파트 코드를 입력받아 유저의 관심 아파트 여부를 확인합니다.")
    @PostMapping("/isliked")
    public ResponseEntity<?> checkLikedApt(@RequestBody Like like){
        try {
            if (service.checkLikedApt(like)) {
                return new ResponseEntity<String>(OK, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(NO, HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "월별 부동산 거래건수", notes = "최근 1년간의 아파트 거래 건수를 리턴합니다.")
    @GetMapping("/deal/count/month")
    public ResponseEntity<?> getDealCountOfAptByMonth(){
        try {
            List<AptDealMonth> list = service.getDealCountOfAptByMonth();
            if (list != null & !list.isEmpty()) {
                for (AptDealMonth deal : list) {
                     long amountLong = Long.parseLong(deal.getAmount()) / 10_000;
                     double amountLongDouble = amountLong / 10_000.0;
                    deal.setAmount(String.valueOf(amountLongDouble));
                    deal.setDeal(String.valueOf((Long.parseLong(deal.getDeal()) / 1000)));
                }
                System.out.println(list);
                return new ResponseEntity<List<AptDealMonth>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @ApiOperation(value = "top 5 인기 거래 지역 리턴", notes = "10년간 가장 많이 거래된 지역 상위 5개를 리턴합니다.")
    @GetMapping("/deal/count/gugun")
    public ResponseEntity<?> getDealCountOfAptByGugun(){
        try {
            List<AptDealGugun> list = service.getDealCountOfAptByGugun();

            for (AptDealGugun apt : list) {
                apt.setDeal(apt.getDeal().substring(0,2));
            }

            if (list != null & !list.isEmpty()) {
                return new ResponseEntity<List<AptDealGugun>>(list, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
