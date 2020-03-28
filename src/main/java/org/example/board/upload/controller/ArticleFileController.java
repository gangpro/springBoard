package org.example.board.upload.controller;

import org.apache.commons.io.IOUtils;
import org.example.board.commons.util.UploadFileUtils;
import org.example.board.upload.service.ArticleFileService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.List;

// 첨부파일 컨트롤러는 게시글 입력처리가 되기 전에 클라이언트로부터 AJAX 통신을 통해 첨부파일을 미리 서버에 저장하는 역할을 수행한다.
@RestController
@RequestMapping("/article/file")
public class ArticleFileController {

    private final ArticleFileService articleFileService;

    public ArticleFileController(ArticleFileService articleFileService) {
        this.articleFileService = articleFileService;
    }

    // 게시글 파일 업로드
    @RequestMapping(value = "/upload", method = RequestMethod.POST, produces = "text/plain;charset=UTF-8")  // produces 속성 주의, 클라이언트로 한글을 정상적으로 전송하기 위한 설정이므로 반드시 작성해야함.
    public ResponseEntity<String> uploadFile(MultipartFile file, HttpServletRequest request) {

        ResponseEntity<String> entity = null;

        try {
            String savedFilePath = UploadFileUtils.uploadFile(file, request);   // 클라이언트로부터 전달받은 file 과 request 를 UploadFileUtils 클래스의 uploadFile() 메서드의 매개변수로 전달한다.
            entity = new ResponseEntity<>(savedFilePath, HttpStatus.CREATED);   // uploadFile() 메서드는 파일업로드 처리를 수행하고, "/년/월/일/UUID_파일명"의 문자열을 리턴한다.
        } catch (Exception e) {                                                 // uploadFile() 메서드로부터 리턴 받은 문자열을 호출한 클라이언트  화면으로 리턴한다.
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    // 첨부파일 출력 매핑 메서드
    // 첨부파일이 서버에 업로드가 완료되고 나면, 화면에 업로드한 첨부파일을 확인할 수 있도록 아래와 같이 컨트롤러에 첨부파일 출력 메서들르 작성해준다.
    // 게시글 파일 출력
    @RequestMapping(value = "/display", method = RequestMethod.GET)
    public ResponseEntity<byte[]> displayFile(String fileName, HttpServletRequest request) throws Exception {

        // 첨부파일 출력처리는 다음과 같음
        // 1. 클라이언트로부터 전달받은 첨부파일명(fileName)과 request 를 UploadFileUtils 클래스의 getHttpHeaders() 메서드의 매개변수로 전달한다.
        // 2. getHttpHeaders() 메서드는 첨부파일명(fileName)을 통해 파일타입을 판별하여 적절한 MINE 타입을 지정하고, 클라이언트에게 전송할 HttpHeaders 객체를 리턴한다.
        // 3. getHttpHeaders() 메서드로부터 리턴받은 HttpHeaders 객체와 첨부파일 데이터를 호출한 클라이언트 화면으로 리턴한다.

        HttpHeaders httpHeaders = UploadFileUtils.getHttpHeaders(fileName); // Http 헤더 설정 가져오기
        String rootPath = UploadFileUtils.getRootPath(fileName, request); // 업로드 기본경로 경로

        ResponseEntity<byte[]> entity = null;

        // 파일데이터, HttpHeader 전송
        try (InputStream inputStream = new FileInputStream(rootPath + fileName)) {
            entity = new ResponseEntity<>(IOUtils.toByteArray(inputStream), httpHeaders, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    // 게시글 첨부 파일 목록
    @RequestMapping(value = "/list/{articleNo}", method = RequestMethod.GET)
    public ResponseEntity<List<String>> getFiles(@PathVariable("articleNo") Integer articleNo) {

        ResponseEntity<List<String>> entity = null;

        try {
            List<String> fileList = articleFileService.getArticleFiles(articleNo);
            entity = new ResponseEntity<>(fileList, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        return entity;
    }
//
//    // 게시글 파일 삭제 : 게시글 작성
//    @RequestMapping(value = "/delete", method = RequestMethod.POST)
//    public ResponseEntity<String> deleteFile(String fileName, HttpServletRequest request) {
//        ResponseEntity<String> entity = null;
//
//        try {
//            UploadFileUtils.deleteFile(fileName, request);
//            entity = new ResponseEntity<>("DELETED", HttpStatus.OK);
//        } catch (Exception e) {
//            e.printStackTrace();
//            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//
//        return entity;
//    }
//

    // 게시글 파일 삭제 : 게시글 수정
    @RequestMapping(value = "/delete/{articleNo}", method = RequestMethod.POST)
    public ResponseEntity<String> deleteFile(@PathVariable("articleNo") Integer articleNo,  // 특정 게시글의 첨부파일들을 조회할 수 있도록 @PathVariable 애너테이션을 통해 게시글 번호를 가져오게 된다.
                                             String fileName,
                                             HttpServletRequest request) {
        // 첨부파일 삭제 처리는 다음과 같은 과정을 거치게 된다.
        // 1. 클라이언트로부터 전달받은 첨부파일명(fileName)과 request 를 UploadFileUtils 클래스의 deleteFile() 메서드의 매개변수로 전달한다.
        // 2. deleteFile() 메서드는 첨부파일명을 MediaUtils 클래스의 getMediaType() 메서드의 매개변수로 전달하고, 이미지 타입 여부를 판별한다.
        // 3. 이미지 파일인 경우 원본 이미지 파일을 삭제 처리하고, 썸네일 파일도 삭제 처리한다.
        // 4. 일반 파일인 경우 파일을 삭제 처리를 수행한다.

        ResponseEntity<String> entity = null;

        try {
            UploadFileUtils.deleteFile(fileName, request);
            articleFileService.deleteFile(fileName, articleNo);
            entity = new ResponseEntity<>("DELETED", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

    // 게시글 파일 전체 삭제
    // 게시글을 수정처리 시에 서버에 저장된 게시글의 기존 첨부파일들을 삭제처리하기 위한 매핑 메서드
    @RequestMapping(value = "/deleteAll", method = RequestMethod.POST)
    public ResponseEntity<String> deleteAllFiles(@RequestParam("files[]") String[] files, HttpServletRequest request) {

        if (files == null || files.length == 0)
            return new ResponseEntity<>("DELETED", HttpStatus.OK);

        ResponseEntity<String> entity = null;

        try {
            for (String fileName : files)
                UploadFileUtils.deleteFile(fileName, request);
            entity = new ResponseEntity<>("DELETED", HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            entity = new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return entity;
    }

}