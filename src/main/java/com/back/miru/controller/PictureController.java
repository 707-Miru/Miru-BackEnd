package com.back.miru.controller;

import com.back.miru.ai.tracking.HandTracking;
import com.back.miru.ai.transfer.TransformPainting;
import com.back.miru.model.dto.Picture;
import com.back.miru.model.service.PictureService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"*"}, maxAge = 6000)
@RequestMapping("/picture")
public class PictureController {
    public static final Logger logger = LoggerFactory.getLogger(PictureController.class);
    private static final String SUCCESS = "success";
    private static final String FAIL = "fail";

    @Autowired
    private PictureService pictureService;

    @PostMapping("/tracking")
    public ResponseEntity<Map<String, Object>> handTracking(@RequestBody HashMap<String, String> map, HttpServletRequest request) {
        System.out.println("transferPicture 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            HandTracking.tracking(map.get("id"));
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("핸드 트래킹 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/transfer")
    public ResponseEntity<Map<String, Object>> transferPicture(@RequestBody HashMap<String, String> map) {
        System.out.println("transferPicture 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            String transferPicturePath = TransformPainting.transform(map.get("option_num"), map.get("style_file_path"), map.get("content_file_path"), map.get("id"));
            resultMap.put("transferPicturePath", transferPicturePath);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("파일 명화 변경 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 전체 사진 목록 불러오기
    // selectAllPictures
    @GetMapping()
    public ResponseEntity<Map<String, Object>> getPictureList(@RequestParam Map<String, String> map) {
        System.out.println("getPictureList controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            List<Picture> pictureList = pictureService.getPictureList(map);
            resultMap.put("pictureList", pictureList);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("목록 불러오기 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    @PostMapping("/{keyword}")
    public ResponseEntity<Map<String, Object>> searchPicture(@PathVariable String keyword, @RequestBody Map<String, String> map) {
        System.out.println("searchPicture controller 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            List<Picture> pictureList = pictureService.searchPictureList(keyword, map);
            resultMap.put("pictureList", pictureList);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("정보 조회 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 사진 디테일
    @GetMapping("/detail/{pictureIdx}")
    public ResponseEntity<Map<String, Object>> getPictureDetail(@PathVariable String pictureIdx) {
        System.out.println("getPictureDetail 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            Map<String, Object> picture = pictureService.getPictureDetail(pictureIdx);
            resultMap.put("picture", picture);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("정보 조회 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 사진 등록
    @PostMapping(value = "/upload", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public Object registPicture(@RequestParam("data") MultipartFile file,
                                @RequestParam("publicFlag") String publicFlag,
                                @RequestParam("isPicture") String isPicture,
                                @RequestParam("id") String id,
                                @RequestParam("tags") String tags,
                                HttpServletRequest request) throws Exception {
        StringBuilder sb = new StringBuilder();
        Map<String, String> map = new HashMap<>();

        System.out.println("registPicture 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;

        try {
            //            String root = "C:\\Users\\SSAFY\\Desktop\\";
            String dbRoot = "/var/www/html/S07P12A707/BackEnd/src/main/resources/static/img/";
            String root = "/var/www/html/crawling/img/";
            sb.append(root).append(file.getOriginalFilename());

            // 폴더 생성
//            File convFile = new File(sb.toString());
//            System.out.println("convFile = " + convFile);
//            if (!convFile.exists()) convFile.mkdir();

            // 사진 저장
            String filepath = sb.toString();
            System.out.println("filepath = " + filepath);
            file.transferTo(new File(filepath));

            sb = new StringBuilder();
            sb.append(dbRoot).append(file.getOriginalFilename());
            dbRoot = sb.toString();
            System.out.println("dbRoot = " + dbRoot);

            map.put("filepath", dbRoot);
            map.put("tags", tags);
            map.put("publicFlag", publicFlag);
            map.put("isPicture", isPicture);
            map.put("id", id);

            pictureService.registPicture(map);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("파일 업로드 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 사진 삭제
    @DeleteMapping("/{pictureIdx}")
    public ResponseEntity<Map<String, Object>> deletePicture(@PathVariable String pictureIdx) {
        System.out.println("deletePicture 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            pictureService.deletePicture(pictureIdx);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("정보조회 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

    // 파이에서 요청한 사진 보여주기
    @PostMapping("/show")
    public ResponseEntity<Map<String, Object>> showPicture(@RequestBody Map<String, String> map) throws Exception {
        System.out.println("showPicture 시작");
        Map<String, Object> resultMap = new HashMap<>();
        HttpStatus status;
        try {
            pictureService.showPicture(map);
            resultMap.put("message", SUCCESS);
            status = HttpStatus.ACCEPTED;
        } catch (Exception e) {
            logger.error("소켓 통신 실패 : {}", e);
            resultMap.put("message", e.getMessage());
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            return new ResponseEntity<Map<String, Object>>(resultMap, status);
        }
        return new ResponseEntity<Map<String, Object>>(resultMap, status);
    }

}
