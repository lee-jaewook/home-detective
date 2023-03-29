package com.mvc.controller;

import com.mvc.model.service.QnaService;
import com.mvc.model.vo.Notice;
import com.mvc.model.vo.Qna;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Api("아파트 정보  API V1")
@CrossOrigin("*")
@RequestMapping(value = "/qna")
public class QnaController {

    QnaService qnaService;

    private final Logger logger = LoggerFactory.getLogger(AptController.class);
    static final String OK = "success";
    static final String NO = "fail";

    @Autowired
    public QnaController(QnaService qnaService) {
        this.qnaService = qnaService;
    }

    /* 해당 user의 Question 전체 조회 */
    @ApiOperation(value = "Question 전체 조회", notes = "사용자의 userid를 입력받아 질문 목록을 가져옵니다.")
    @GetMapping("/{userid}/questions")
    public ResponseEntity<?> getAllQuestionList(@PathVariable("userid") String userId) {
        try {
            List<Qna> questionList = qnaService.getAllQuestionList(userId);
            if (questionList != null && !questionList.isEmpty()) {
                return new ResponseEntity<List<Qna>>(questionList, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage() + " error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* Question에 대한 Answer 전체 조회 */
    @ApiOperation(value = "하나의 질문에 대한 Answer 전체 조회", notes = "question의 id를 입력받아 답변 목록을 가져옵니다.")
    @GetMapping("/{id}/answers")
    public ResponseEntity<?> getAllAnswerList(@PathVariable("id") String id) {
        try {
            logger.info("{}번 글에대한 답변",id);
            Integer.parseInt(id);
            List<Qna> questionList = qnaService.getAllAnswerList(id);
            if (questionList != null && !questionList.isEmpty()) {
                return new ResponseEntity<List<Qna>>(questionList, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
//            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage() + " error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* QnA 상세 조회 */
    @ApiOperation(value = "QnA 상세 조회", notes = "QnA 1건에 대한 상세 정보를 반환해 줍니다.")
    @GetMapping("/{id}")
    public ResponseEntity<?> getOneQna(@PathVariable String id) {
        try {
            Qna qna = qnaService.getOneQna(id);
            if (qna != null) {
                return new ResponseEntity<Qna>(qna, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage() + " error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* QnA 등록 */
    @ApiOperation(value = "QnA 등록", notes = "QnA 등록 후, 성공 여부를 반환해 줍니다.")
    @PostMapping
    public ResponseEntity<?> addQna(@RequestBody Qna qna) {
        try {
            // 정수 타입이 아닌 데이터는 null로 변환
            try {
                Integer.parseInt(qna.getParentId());
            } catch (NumberFormatException e) {
                qna.setParentId(null);
            } catch (NullPointerException e2){
                qna.setParentId(null);
            }
            if (qnaService.addQna(qna)) {
                return new ResponseEntity<String>(OK, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(NO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* QnA 수정 */
    @ApiOperation(value = "QnA 수정", notes = "공지사항 수정 후, 성공 여부를 반환해 줍니다.")
    @PutMapping
    public ResponseEntity<?> updateBoard(@RequestBody Qna qna) {
        try {
            if (qnaService.modifyQna(qna)) {
                return new ResponseEntity<String>(OK, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(NO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* QnA 삭제 */
    @ApiOperation(value = "QnA 삭제", notes = "QnA 삭제 후, 성공 여부를 반환해 줍니다.")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteQna(@PathVariable String id) {
        try {
            if (qnaService.deleteQna(id)) {
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
