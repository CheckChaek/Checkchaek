package com.cc.business.domain.service;// BusinessService.java
import com.cc.business.domain.dto.AladinResponseDto;
import com.cc.business.domain.dto.BookDto;
import com.cc.business.domain.entity.BookEntity;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.List;

public interface BusinessService {
    // 이미지 프로세스 전체를 담당
    AladinResponseDto processImages(List<String> imageUrlList);

    // TA 서버에 이미지 정보를 전송하고
    List<String> getImageText(List<String> imageList);

    // search 서버에 텍스트를 입력하면 검색 결과를 반환
    AladinResponseDto getBookInfo(List<String> imageList);

    // sc 서버에 이미지 정보를 전송하면 상태 반환
    String getImageStatus(List<String> imageUrlList) throws JsonProcessingException;

    // ans 서버에 상태와 가격을 전송하면 가격 결과 반환;
    int getBookPrice(BookEntity certainBookInfo);

    // 책 정보 DB에 저장
    int saveBookInfo(BookDto bookInfo, int memberId);

    // S3에 저장된 이미지 경로 DB에 저장
    void saveS3URL(List<String> imageUrlList, int bookId);

    // 특정 멤버의 특정 책에 대한 S3에 저장된 이미지 검색
    List<String> getImageUrlList(int bookId);

    // 정확한 책 제목, 저자, 출판사를 이용하여 알라딘 검색 API 호출
    BookEntity searchCertainBookInfo(BookDto bookInfo);
}
