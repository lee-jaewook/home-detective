package com.mvc.controller;

import com.mvc.model.service.JwtServiceImpl;
import com.mvc.model.service.UserService;
import com.mvc.model.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;


@Api("아파트 거래정보  API V1")
@RestController
@CrossOrigin("*")
@RequestMapping(value = "/user")
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(AptController.class);

    private UserService userService;
    private JwtServiceImpl jwtService;

    static final String OK = "success";
    static final String NO = "fail";

    @Autowired
    public UserController(UserService service, JwtServiceImpl jwtService) {
        this.userService = service;
        this.jwtService = jwtService;
    }


    /* 회원 가입 */
    @ApiOperation(value = "회원 가입", notes = "사용자 회원가입 후, 성공 여부를 반환해 줍니다.")
    @PostMapping(value = "/signup")
    public ResponseEntity<?> signUp(@RequestBody User user) {
        try {
            if (userService.signUp(user)) {
                return new ResponseEntity<String>(OK, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(NO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /* 로그인 */
    @ApiOperation(value = "로그인", notes = "로그인 후, 사용자 정보를 반환해 줍니다.")
    @PostMapping(value = "/signin")
    public ResponseEntity<?> signIn(@RequestBody @ApiParam(value = "로그인 시 필요한 회원정보(아이디, 비밀번호).", required = true) User user) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = null;
        try {
            User nUser = userService.signIn(user);
            if (nUser != null) {

                // token 생성
                String accessToken = jwtService.createAccessToken("id", nUser.getId());
                String refreshToken = jwtService.createRefreshToken("id", nUser.getId());


                // refresh token을 데이터베이스에 저장
                Map<String, String> map = new HashMap<>();
                map.put("id", nUser.getId());
                map.put("token", refreshToken);
                userService.saveRefreshToken(map);

                // 로그 출력
                logger.debug("로그인 accessToken 정보 : {}", accessToken);
                logger.debug("로그인 refreshToken 정보 : {}", refreshToken);

                // response map
                resultMap.put("access-token", accessToken);
                resultMap.put("refresh-token", refreshToken);
                resultMap.put("message", OK);

                status = HttpStatus.ACCEPTED;
                System.out.println(resultMap);
            } else {
                resultMap.put("message", NO);
                status = HttpStatus.ACCEPTED;
            }
        } catch (Exception e) {
            logger.error("로그인 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    /* 로그아웃 */
    @ApiOperation(value = "로그아웃", notes = "회원 정보를 담은 Token을 제거한다.", response = Map.class)
    @GetMapping("/logout/{userid}")
    public ResponseEntity<?> removeToken(@PathVariable("userid") String userid) {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        try {
            userService.delRefreshToken(userid);
            resultMap.put("message", OK);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("로그아웃 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "Access Token 재발급", notes = "만료된 access token을 재발급받는다.", response = Map.class)
    @PostMapping("/refresh")
    public ResponseEntity<?> refreshToken(@RequestBody User user, HttpServletRequest request)
            throws Exception {
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.ACCEPTED;
        String token = request.getHeader("refresh-token");
        logger.debug("token : {}, memberDto : {}", token, user);
        if (jwtService.checkToken(token)) {
            if (token.equals(userService.getRefreshToken(user.getId()))) {
                String accessToken = jwtService.createAccessToken("userid", user.getId());
                logger.debug("token : {}", accessToken);
                logger.debug("정상적으로 액세스토큰 재발급!!!");
                resultMap.put("access-token", accessToken);
                resultMap.put("message", OK);
                status = HttpStatus.ACCEPTED;
            }
        } else {
            logger.debug("리프레쉬토큰도 사용불!!!!!!!");
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "회원인증", notes = "회원 정보를 담은 Token을 반환한다.", response = Map.class)
    @GetMapping("/info/{userid}")
    public ResponseEntity<Map<String, Object>> getInfo(
            @PathVariable("userid") @ApiParam(value = "인증할 회원의 아이디.", required = true) String userid,
            HttpServletRequest request) {
        logger.info("user info 호출 !! id =",userid);
        System.out.println(userid);
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        if (jwtService.checkToken(request.getHeader("access-token"))) {
            try {
//				로그인 사용자 정보.
                User user = userService.userInfo(userid);
                System.out.println(user);
                resultMap.put("userInfo", user);
                resultMap.put("message", OK);
                status = HttpStatus.ACCEPTED;
            } catch (Exception e) {
                logger.error("정보조회 실패 : {}", e);
                resultMap.put("message", e.getMessage());
                status = HttpStatus.INTERNAL_SERVER_ERROR;
            }
        } else {
            logger.error("사용 불가능 토큰!!!");
            resultMap.put("message", NO);
            status = HttpStatus.UNAUTHORIZED;
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @ApiOperation(value = "회원 정보 수정", notes = "회원 정보 수정")
    @PostMapping("/info/update")
    public ResponseEntity<?> updateUserInfo(@RequestBody User user){
        try {
            logger.info("회원 정보 수정 {}", user);
            if (userService.updateUserInfo(user)) {
                return new ResponseEntity<String>(OK, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(NO, HttpStatus.BAD_REQUEST);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "비밀번호 변경", notes = "아이디와 비밀번호를 입력받아 옳은지 확인 후 비밀번호를 수정합니다.")
    @GetMapping("/pass/change")
    public ResponseEntity<?> changeUserPassword(@RequestParam String id, @RequestParam String oldpass, @RequestParam String newpass){
        try {
            Map<String ,String> map = new HashMap<>();
            map.put("id", id);
            map.put("opass", oldpass);
            map.put("npass", newpass);
//            logger.info("find pass of user | {}", map);
            if (userService.changePassword(map)) {
                return new ResponseEntity<String>(OK, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(NO, HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @ApiOperation(value = "비밀번호 확인", notes = "아이디와 비밀번호를 입력받아 옳은지 확인합니다.")
    @GetMapping("/pass/check")
    public ResponseEntity<?> checkUserPassword(@RequestParam String id, @RequestParam String pass){
        try {
            Map<String ,String> map = new HashMap<>();
            map.put("id", id);
            map.put("opass", pass);
            logger.info("find pass of user | {}", map);
            if (userService.checkPassword(map)) {
                System.out.println("??");
                return new ResponseEntity<String>(OK, HttpStatus.OK);
            } else {
                return new ResponseEntity<String>(NO, HttpStatus.OK);
            }
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage()+" error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
