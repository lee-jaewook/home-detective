package com.mvc.controller;

import com.mvc.model.service.NoticeService;
import com.mvc.model.vo.Notice;
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
@RequestMapping(value = "/notice")
@CrossOrigin("*")
public class NoticeController {

    private NoticeService noticeService;

    private final Logger logger = LoggerFactory.getLogger(AptController.class);
    static final String OK = "success";
    static final String NO = "fail";

    @Autowired
    public NoticeController(NoticeService noticeService) {
        this.noticeService = noticeService;
    }

    /* 공지사항 전체 조회 */
    @ApiOperation(value = "공지사항 전체 조회", notes = "공지사항 전체 목록을 반환해 줍니다.")
    @GetMapping
    public ResponseEntity<?> getAllNoticeList() {
        try {
            System.out.println("????");
            List<Notice> noticeList = noticeService.getAllNoticeList();
            if (noticeList != null && !noticeList.isEmpty()) {
                return new ResponseEntity<List<Notice>>(noticeList, HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Notice>>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage() + " error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 공지사항 상세 조회 */
    @ApiOperation(value = "공지사항 상세 조회", notes = "공지사항 1건에 대한 상세 정보를 반환해 줍니다.")
    @GetMapping("/{id}")
    public ResponseEntity<?> selectOneBoard(@PathVariable String id) {
        try {
            Notice notice = noticeService.getOneNotice(id);
            if (notice != null) {
                return new ResponseEntity<Notice>(notice, HttpStatus.OK);
            } else {
                return new ResponseEntity<Notice>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage() + " error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 공지사항 등록 */
    @ApiOperation(value = "공지사항 등록", notes = "공지사항 등록 후, 성공 여부를 반환해 줍니다.")
    @PostMapping
    public ResponseEntity<?> registerBoard(@RequestBody Notice notice) {
        try {
            if (noticeService.addNotice(notice)) {
                return new ResponseEntity<String>(OK, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(NO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 공지사항 수정 */
    @ApiOperation(value = "공지사항 수정", notes = "공지사항 수정 후, 성공 여부를 반환해 줍니다.")
    @PutMapping
    public ResponseEntity<?> updateBoard(@RequestBody Notice notice) {
        try {
            if (noticeService.modifyNotice(notice)) {
                return new ResponseEntity<String>(OK, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(NO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 공지사항 삭제 */
    @ApiOperation(value = "공지사항 삭제", notes = "공지사항 삭제 후, 성공 여부를 반환해 줍니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteNotice(@PathVariable String id) {
        try {
            if (noticeService.deleteNotice(id)) {
                return new ResponseEntity<String>(OK, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(NO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
