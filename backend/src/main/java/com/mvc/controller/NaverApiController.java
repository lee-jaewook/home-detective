package com.mvc.controller;

// 네이버 검색 API 예제 - 블로그 검색
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.mvc.model.service.CrawlingService;
import com.mvc.model.service.JwtService;
import com.mvc.model.service.UserService;
import com.mvc.model.vo.Address;
import com.mvc.model.vo.School;
import com.mvc.model.vo.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.*;
import java.util.*;

@RestController
@Api("아파트 정보  API V1")
@CrossOrigin("*")
@RequestMapping(value = "/naver")
public class NaverApiController {
    static final String clientId = "2QFr6GZqkaJBGXB59bYS";
    static final String clientSecret = "E6wugW2Q1o";
    private final Logger logger = LoggerFactory.getLogger(AptController.class);

    JwtService jwtService;
    UserService userService;
    CrawlingService crawlingService;

    @Autowired
    public NaverApiController(JwtService jwtService, UserService userService, CrawlingService crawlingService) {
        this.jwtService = jwtService;
        this.userService = userService;
        this.crawlingService = crawlingService;
    }

    @ApiOperation(value = "네이버 로그인", notes = "네이버 로그인 token을 리턴합니다.")
    @GetMapping("/auth")
    public ResponseEntity<?> authNaverLogin(@RequestParam String code, @RequestParam String state, HttpServletResponse response) throws URISyntaxException {
        String apiURL;
        apiURL = "https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&";
        apiURL += "client_id=" + clientId;
        apiURL += "&client_secret=" + clientSecret;
        apiURL += "&code=" + code;
        apiURL += "&state=" + state;
        String accessToken = "";
        String refreshToken = "";
        try {
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            int responseCode = con.getResponseCode();
            BufferedReader br;

            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer res = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            if(responseCode==200) {
                String id, nickName, email, tmp, mobile;
                JsonParser parser = new JsonParser();
                JsonElement accessElement = parser.parse(res.toString());
                accessToken = accessElement.getAsJsonObject().get("access_token").getAsString();
                tmp = getUserInfo(accessToken);
                JsonElement userInfoElement = parser.parse(tmp);
//                id = userInfoElement.getAsJsonObject().get("response").getAsJsonObject().get("id").getAsString();
                nickName = userInfoElement.getAsJsonObject().get("response").getAsJsonObject().get("nickname").getAsString();
                email = userInfoElement.getAsJsonObject().get("response").getAsJsonObject().get("email").getAsString();
                mobile = userInfoElement.getAsJsonObject().get("response").getAsJsonObject().get("mobile").getAsString();
                System.out.println(userInfoElement.getAsJsonObject());

                // id, name, phone, email, address, role
                StringTokenizer st = new StringTokenizer(email, "@");
                id = st.nextToken();

                if(userService.userInfo(id) == null){
                    userService.signUp(new User(id, nickName, mobile, email, "서울시 강남구 역삼", "NAVER"));
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
            System.out.println(e);
        }
        logger.info("토큰 생성 완료 access : {} // refrech : {}",accessToken,refreshToken);
        String url = "http://localhost:8080/agreement?access_token=" + accessToken+"&refresh_token="+refreshToken;
        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(url)).build();
    }

    private String getUserInfo(String access_token) {
        String header = "Bearer " + access_token; // Bearer 다음에 공백 추가
        try {
            String apiURL = "https://openapi.naver.com/v1/nid/me";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection)url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", header);
            int responseCode = con.getResponseCode();
            BufferedReader br;
            if(responseCode==200) { // 정상 호출
                br = new BufferedReader(new InputStreamReader(con.getInputStream()));
            } else {  // 에러 발생
                br = new BufferedReader(new InputStreamReader(con.getErrorStream()));
            }
            String inputLine;
            StringBuffer res = new StringBuffer();
            while ((inputLine = br.readLine()) != null) {
                res.append(inputLine);
            }
            br.close();
            return res.toString();
        } catch (Exception e) {
            System.err.println(e);
            return "Err";
        }
    }

    @ApiOperation(value = "뉴스 조회", notes = "키워드를 입력받아 뉴스를 검색합니다.")
    @GetMapping("/news/{word}")
    public ResponseEntity<?> getNewsList(@PathVariable String word) {
        String text = null;
        try {
            int display = 9;
            if(word.equals("서울 부동산 폭락")){
                display = 1;
            }
            text = URLEncoder.encode(word, "UTF-8");

            String apiURL = "https://openapi.naver.com/v1/search/news.json?query=" + text +"&display="+display;    // JSON 결과
            //String apiURL = "https://openapi.naver.com/v1/search/news.xml?query="+ text; // XML 결과

            Map<String, String> requestHeaders = new HashMap<>();
            requestHeaders.put("X-Naver-Client-Id", clientId);
            requestHeaders.put("X-Naver-Client-Secret", clientSecret);
            String responseBody = get(apiURL,requestHeaders);

            if (responseBody != null) {
                JsonParser parser = new JsonParser();
                JsonElement searchInfoElement = parser.parse(responseBody);
                int count = searchInfoElement.getAsJsonObject().get("display").getAsInt();
                JsonArray jarray = searchInfoElement.getAsJsonObject().get("items").getAsJsonArray();
                List<Map<String, String>> result = new ArrayList<>();
                for (int i = 0; i < count; i++) {
                    Map<String, String> tmp = new HashMap<>();

//                    System.out.println("*************************************************************");
                    tmp.put("title",jarray.get(i).getAsJsonObject().get("title").getAsString());
                    tmp.put("link",jarray.get(i).getAsJsonObject().get("link").getAsString());
                    tmp.put("description",jarray.get(i).getAsJsonObject().get("description").getAsString());
                    tmp.put("pubDate",jarray.get(i).getAsJsonObject().get("pubDate").getAsString());
                    tmp.put("pubDate",jarray.get(i).getAsJsonObject().get("pubDate").getAsString());
                    String img = crawlingService.getImgSource(jarray.get(i).getAsJsonObject().get("link").getAsString());
                    tmp.put("img", img);
                    result.add(tmp);
//                    logger.info("클롤링 결과 {}", tmp);
//                    System.out.println(jarray.get(i));
//                    System.out.println(jarray.get(i).getAsJsonObject().get("title").getAsString());
//                    System.out.println(jarray.get(i).getAsJsonObject().get("link").getAsString());
//                    System.out.println(jarray.get(i).getAsJsonObject().get("description").getAsString());
//                    System.out.println(jarray.get(i).getAsJsonObject().get("pubDate").getAsString());
//                    System.out.println("*************************************************************");

                }
                return new ResponseEntity<List<Map<String, String>>>(result, HttpStatus.OK);
            } else {
                return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<String>(e.getMessage() + " error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    private static String get(String apiUrl, Map<String, String> requestHeaders){
        HttpURLConnection con = connect(apiUrl);
        try {
            con.setRequestMethod("GET");
            for(Map.Entry<String, String> header :requestHeaders.entrySet()) {
                con.setRequestProperty(header.getKey(), header.getValue());
            }

            int responseCode = con.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) { // 정상 호출
                return readBody(con.getInputStream());
            } else { // 오류 발생
                return readBody(con.getErrorStream());
            }
        } catch (IOException e) {
            throw new RuntimeException("API 요청과 응답 실패", e);
        } finally {
            con.disconnect();
        }
    }


    private static HttpURLConnection connect(String apiUrl){
        try {
            URL url = new URL(apiUrl);
            return (HttpURLConnection)url.openConnection();
        } catch (MalformedURLException e) {
            throw new RuntimeException("API URL이 잘못되었습니다. : " + apiUrl, e);
        } catch (IOException e) {
            throw new RuntimeException("연결이 실패했습니다. : " + apiUrl, e);
        }
    }


    private static String readBody(InputStream body){
        InputStreamReader streamReader = new InputStreamReader(body);


        try (BufferedReader lineReader = new BufferedReader(streamReader)) {
            StringBuilder responseBody = new StringBuilder();


            String line;
            while ((line = lineReader.readLine()) != null) {
                responseBody.append(line);
            }


            return responseBody.toString();
        } catch (IOException e) {
            throw new RuntimeException("API 응답을 읽는 데 실패했습니다.", e);
        }
    }
}
