package com.cc.business.domain.controller;// BusinessController.java
import com.cc.business.domain.controller.openfeign.AuthOpenFeign;
import com.cc.business.domain.dto.AladinResponseDto;
import com.cc.business.domain.dto.BookDto;
import com.cc.business.domain.entity.BookEntity;
import com.cc.business.domain.service.BusinessService;
import com.cc.business.domain.service.ImageService;
import com.cc.business.domain.service.S3Service;
import com.cc.business.global.common.response.EnvelopeResponse;

import feign.FeignException;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Tag(name = "예제 API", description = "Swagger 테스트용 API")
@RestController
@RequiredArgsConstructor
public class BusinessController {

    private final BusinessService businessService;
    private final S3Service s3Service;
    private final ImageService imageService;
    private final AuthOpenFeign authOpenFeign;

    private int isAuthorized(HttpServletRequest request) throws FeignException {
        String Authorization = request.getHeader("Authorization");
        String AuthorizationRefresh = request.getHeader("Authorization-refresh");
        int memberId;
        memberId = authOpenFeign.connectToAuthServer(Authorization,AuthorizationRefresh);
        return memberId;
    }

    @PostMapping("/imageinfo")
    public ResponseEntity<EnvelopeResponse<HashMap<String, Object>>> getImageInfo(HttpServletRequest request, @RequestBody List<MultipartFile> imageList) throws Exception {

//       int memberId = isAuthorized(request);
//       System.out.println("=======memberId======" + memberId);
//        log.info("사용자 ID: {}", memberId);

        log.info("이미지 정보 요청값: {}", imageList.size());
        /* S3에 이미지 저장 */
        List<String> imageUrlList = s3Service.upload(imageList);
        log.info("S3 이미지 저장 경로 {}", imageUrlList);

        /* 글자 추출 후 책 검색 */
        AladinResponseDto aladinResponse = businessService.processImages(imageUrlList);
        BookDto bookInfo = new BookDto();
        bookInfo.setTitle(aladinResponse.getTitle());
        bookInfo.setAuthor(aladinResponse.getAuthor());
        bookInfo.setPublisher(aladinResponse.getPublisher());
        bookInfo.setImage(aladinResponse.getCover());

        /* step1. 책 정보 먼저 저장 */
        int bookId = businessService.saveBookInfo(bookInfo, 8);
        log.info("책 번호: {}", bookId);
        bookInfo.setBookId(bookId);

        /* step2. 저장된 책의 id를 가지고 이미지 정보를 저장 */
        businessService.saveS3URL(imageUrlList, bookId);

        HashMap<String, Object> data = new HashMap<>();
        data.put("bookInfo", bookInfo);

        EnvelopeResponse<HashMap<String, Object>> result = new EnvelopeResponse(200, "이미지 검색 성공", data);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping("/bookpredict")
    public ResponseEntity<EnvelopeResponse<BookEntity>> predictBookInfo(HttpServletRequest request, @RequestBody HashMap<String, BookDto> params) throws Exception {

//        int memberId = isAuthorized(request);
//        log.info("{}", memberId);
        log.info("수정된 책 정보 요청값: {}", params.get("bookInfo"));
        BookDto editedBookInfo = params.get("bookInfo");

        /* 책ID를 이용하여 S3에 저장된 이미지 리스트 호출  */
        List<String> imageUrlList = businessService.getImageUrlList(editedBookInfo.getBookId());
        log.info("S3에 저장된 이미지 목록: {}", imageUrlList);

        /* imageUrlList를 이용하여 책의 상태 반환 */
        String imageStatus = businessService.getImageStatus(imageUrlList);
        log.info("책의 상태: {}", imageStatus);

        /* 수정된 책 정보를 이용하여 다시 알라딘 API 검색 */
        BookEntity certainBookInfo = businessService.searchCertainBookInfo(editedBookInfo);
        log.info("정확한 책 정보: {}", certainBookInfo);
        certainBookInfo.setStatus(imageStatus);

        /* 책의 상태를 이용하여 재평가된 책의 가격 반환 */
        int bookPrice = businessService.getBookPrice(certainBookInfo);
        log.info("재평가된 책의 가격: {}", bookPrice);
        certainBookInfo.setEstimatedPrice(bookPrice);

        /* 재검색된 책의 정보 DB에 저장 */
        certainBookInfo.setBookId(editedBookInfo.getBookId());
        certainBookInfo.setMemberId(8);
        businessService.saveCertainBookInfo(certainBookInfo);

        EnvelopeResponse response = new EnvelopeResponse(200, "최종 책의 정보 반환 성공", certainBookInfo);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @GetMapping("/test")
    public int test(HttpServletRequest request){
        return isAuthorized(request);
    }
}
