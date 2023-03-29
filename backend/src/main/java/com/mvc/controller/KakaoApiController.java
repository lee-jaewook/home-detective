package com.mvc.controller;

// 네이버 검색 API 예제 - 블로그 검색

import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mvc.model.service.JwtService;
import com.mvc.model.service.UserService;
import com.mvc.model.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

@RestController
@Api("아파트 정보  API V1")
@CrossOrigin("*")
@RequestMapping(value = "/kakao")
public class KakaoApiController {
    JwtService jwtService;
    UserService userService;

    @Autowired
    public KakaoApiController(JwtService jwtService, UserService userService) {
        this.jwtService = jwtService;
        this.userService = userService;
    }

    @ApiOperation(value = "카카오 로그인", notes = "카카오 로그인 token을 리턴합니다.")
    @GetMapping("/auth")
    public ResponseEntity<?> authKakaoLogin(@RequestParam String code, HttpServletResponse response) throws URISyntaxException {
        System.out.println("kakao callback 컨트롤러 접근");
        System.out.println(code);

        //1. 코드전달
        String access_token = getKaKaoAccessToken(code);

        System.out.println("1. access_token : " + access_token);

        //2. 인증코드로 토큰 전달
        HashMap<String, String> userInfoMAp = getUserInfo(access_token);

        System.out.println("Result in redirect func>>");
        System.out.println(userInfoMAp);

        String accessToken ="", refreshToken = "";

        try {
            if(userInfoMAp!=null) {
                StringTokenizer st = new StringTokenizer((String) userInfoMAp.get("email"), "@");
                String id = st.nextToken();
                if(userService.userInfo(id) == null){
                    userService.signUp(new User(id, userInfoMAp.get("name"), null, userInfoMAp.get("email"), "서울시 강남구 역삼", "KAKAO"));
                }

                accessToken = jwtService.createAccessToken("id", id);
                refreshToken = jwtService.createRefreshToken("id", id);

                // refresh token을 데이터베이스에 저장
                Map<String, String> map = new HashMap<>();
                map.put("id", id);
                map.put("token", refreshToken);
                userService.saveRefreshToken(map);
            }else{
                ResponseEntity.status(HttpStatus.UNAUTHORIZED).location(URI.create("http://localhost:8080/sign-in")).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }



        String url = "http://localhost:8080/agreement?access_token=" + accessToken+"&refresh_token="+refreshToken;
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }

    public String getKaKaoAccessToken(String code){
        String access_Token="";
        String refresh_Token ="";
        String reqURL = "https://kauth.kakao.com/oauth/token";

        try{
            URL url = new URL(reqURL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            //POST 요청을 위해 기본값이 false인 setDoOutput을 true로
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);

            //POST 요청에 필요로 요구하는 파라미터 스트림을 통해 전송
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id=af455fc306697ff0091a108bd8a75281"); // TODO REST_API_KEY 입력
            sb.append("&redirect_uri=http://localhost/kakao/auth"); // TODO 인가코드 받은 redirect_uri 입력
            sb.append("&code=" + code);
            bw.write(sb.toString());
            bw.flush();

            //결과 코드가 200이라면 성공
            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);
            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line = "";
            String result = "";

            while ((line = br.readLine()) != null) {
                result += line;
            }
            System.out.println("response body : " + result);

            //Gson 라이브러리에 포함된 클래스로 JSON파싱 객체 생성
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            access_Token = element.getAsJsonObject().get("access_token").getAsString();
            refresh_Token = element.getAsJsonObject().get("refresh_token").getAsString();

            System.out.println("access_token : " + access_Token);
            System.out.println("refresh_token : " + refresh_Token);

            br.close();
            bw.close();
        }catch (IOException e) {
            e.printStackTrace();
        }
        return access_Token;
    }

    public HashMap<String, String> getUserInfo(String access_token) {

        HashMap<String, String> userInfo = new HashMap<>();
        String reqUrl = "https://kapi.kakao.com/v2/user/me";


        //access_token을 이용하여 사용자 정보 조회
        try {
            URL url = new URL(reqUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            conn.setRequestProperty("Authorization", "Bearer " + access_token);

            int responseCode = conn.getResponseCode();
            System.out.println("responseCode : " + responseCode);


            //요청을 통해 얻은 JSON타입의 Response 메세지 읽어오기
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            String line = "";
            String result = "";

            while((line = br.readLine())!=null) {
                result += line;
            }

            System.out.println("resopnse body =" + result);

            //Gson 라이브러리로 JSON파싱
            JsonParser parser = new JsonParser();
            JsonElement element = parser.parse(result);

            String name = element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("profile").getAsJsonObject().get("nickname").getAsString();

            userInfo.put("email",  element.getAsJsonObject().get("kakao_account").getAsJsonObject().get("email").getAsString());
            userInfo.put("name", name);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return userInfo;
    }
}
