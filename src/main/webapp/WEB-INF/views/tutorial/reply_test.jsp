<%--
  Created by IntelliJ IDEA.
  User: kang
  Date: 2020/03/11
  Time: 1:08
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<html>

<%@ include file="../include/head.jsp" %>

<body class="hold-transition skin-blue sidebar-mini layout-boxed">

<div class="wrapper">

    <!-- Main Header -->
    <%@ include file="../include/main_header.jsp" %>

    <!-- Left side column. contains the logo and sidebar -->
    <%@ include file="../include/left_column.jsp" %>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                AJAX 댓글 테스트 페이지
            </h1>
            <ol class="breadcrumb">
                <li><i class="fa fa-edit"></i> reply test</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content container-fluid">

            <div class="col-lg-12">
                <div class="box box-primary">
                    <div class="box-header with-border">
                        <h3 class="box-title">댓글 작성</h3>
                    </div>
                    <div class="box-body">
                        <div class="form-group">
                            <label for="newReplyText">댓글 내용</label>
                            <input class="form-control" id="newReplyText" name="replyText" placeholder="댓글 내용을 입력해주세요">
                        </div>
                        <div class="form-group">
                            <label for="newReplyWriter">댓글 작성자</label>
                            <input class="form-control" id="newReplyWriter" name="replyWriter"
                                   placeholder="댓글 작성자를 입력해주세요">
                        </div>
                        <div class="pull-right">
                            <button type="button" id="replyAddBtn" class="btn btn-primary"><i class="fa fa-save"></i>
                                댓글 저장
                            </button>
                        </div>
                    </div>
                    <div class="box-footer">
                        <ul id="replies">

                        </ul>
                    </div>
                    <div class="box-footer">
                        <div class="text-center">
                            <ul class="pagination pagination-sm no-margin">

                            </ul>
                        </div>
                    </div>
                </div>
            </div>
            <!-- 댓글 수정/삭제 Modal 영역 -->
            <div class="modal fade" id="modifyModal" role="dialog">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal">&times;</button>
                            <h4 class="modal-title">댓글 수정창</h4>
                        </div>
                        <div class="modal-body">
                            <div class="form-group">
                                <label for="replyNo">댓글 번호</label>
                                <input class="form-control" id="replyNo" name="replyNo" readonly>
                            </div>
                            <div class="form-group">
                                <label for="replyText">댓글 내용</label>
                                <input class="form-control" id="replyText" name="replyText" placeholder="댓글 내용을 입력해주세요">
                            </div>
                            <div class="form-group">
                                <label for="replyWriter">댓글 작성자</label>
                                <input class="form-control" id="replyWriter" name="replyWriter" readonly>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default pull-left" data-dismiss="modal">닫기</button>
                            <button type="button" class="btn btn-success modalModBtn">수정</button>
                            <button type="button" class="btn btn-danger modalDelBtn">삭제</button>
                        </div>
                    </div>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <%@ include file="../include/main_footer.jsp" %>

</div>
<!-- ./wrapper -->
<%@ include file="../include/plugin_js.jsp" %>
<script type="text/javascript">

    // // JS 코드를 이용한 댓글 목록 가져오기 테스트
    // const articleNo = 1000;
    // $.getJSON("/replies/all/" + articleNo, function(data){
    //     console.log(data)
    // })

    // 댓글 목록
    // 1000 번째 게시글
    const articleNo = 1000;

    // 댓글 목록 페이징 처리 하면서 주석처리
    // // 댓글 목록 페이지 호출
    // getReplies();

    // 댓글 목록 출력 함수
    function getReplies() {
        $.getJSON("/replies/all/" + articleNo, function (data) {
            console.log(data);

            let str = "";

            $(data).each(function () {
                str += "<li data-replyNo='" + this.replyNo + "' class='replyLi'>"
                    +   "<p class='replyText'>" + this.replyText + "</p>"
                    +   "<p class='replyWriter'>" + this.replyWriter + "</p>"
                    +   "<button type='button' class='btn btn-xs btn-success modifyModal' data-toggle='modal' data-target='#modifyModal'>댓글 수정</button>"
                    + "</li>"
                    + "<hr/>";
            });

            $("#replies").html(str);
        });
    }

    // 댓글 저장 버튼 클릭 이벤트 발생시
    $("#replyAddBtn").on("click", function () {

        // 화면으로부터 입력 받은 변수 값 처리
        const replyText = $("#newReplyText");
        const replyWriter = $("#newReplyWriter");
        const replyTextVal = replyText.val();
        const replyWriterVal = replyWriter.val();

        // AJAX 통신 : POST
        $.ajax({
            type : "post",
            url : "/replies",
            headers : {
                "Content-type" : "application/json",
                "X-HTTP-Method-Override" : "POST"
            },
            dataType : "text",
            data : JSON.stringify({
                articleNo : articleNo,
                replyText : replyTextVal,
                replyWriter : replyWriterVal
            }),
            success : function (result) {
                // 성공적인 댓글 등록 처리 알림
                if (result === "regSuccess") {
                    alert("댓글 등록 완료!");
                }

                // getReplies();        // 댓글 목록 출력 함수 호출
                getRepliesPaging(replyPageNum); // 목록 페이지 호출
                replyText.val("");   // 댓글 내용 초기화
                replyWriter.val(""); // 댓글 작성자 초기화
            }
        });
    });

    // 댓글 수정 삭제 관련
    // 댓글의 값들 세팅
    $("#replies").on("click", ".replyLi button", function () {

        const reply = $(this).parent();
        const replyNo = reply.attr("data-replyNo");
        const replyText = reply.find(".replyText").text();
        const replyWriter = reply.find(".replyWriter").text();

        $("#replyNo").val(replyNo);
        $("#replyText").val(replyText);
        $("#replyWriter").val(replyWriter);

    });

    // 댓글 수정 호출하기
    $(".modalModBtn").on("click", function () {

        const reply = $(this).parent().parent();          // 댓글 선택자
        const replyNo = reply.find("#replyNo").val();     // 댓글 번호
        const replyText = reply.find("#replyText").val(); // 수정한 댓글 내용

        // AJAX 통신 : PUT
        $.ajax({
            type : "put",
            url : "/replies/" + replyNo,
            headers : {
                "Content-type" : "application/json",
                "X-HTTP-Method-Override" : "PUT"
            },
            data : JSON.stringify(
                {replyText : replyText}
            ),
            dataType : "text",
            success : function (result) {
                console.log("result : " + result);
                if (result === "modSuccess") {
                    alert("댓글 수정 완료!");
                    $("#modifyModal").modal("hide");    // Modal 닫기
                    // getReplies();   // 댓글 목록 갱신
                    getRepliesPaging(replyPageNum); // 목록 페이지 호출
                }
            }
        });
    });

    // 댓글 삭제 호출하기
    $(".modalDelBtn").on("click", function () {

        // 댓글 번호
        const replyNo = $(this).parent().parent().find("#replyNo").val();

        // AJAX 통신 : DELETE
        $.ajax({
            type : "delete",
            url : "/replies/" + replyNo,
            headers : {
                "Content-type" : "application/json",
                "X-HTTP-Method-Override" : "DELETE"
            },
            dataType : "text",
            success : function (result) {
                console.log("result : " + result);
                if (result === "delSuccess") {
                    alert("댓글 삭제 완료!");
                    $("#modifyModal").modal("hide");    // Modal 닫기
                    // getReplies();   // 댓글 목록 갱신
                    getRepliesPaging(replyPageNum); // 목록 페이지 호출

                }
            }
        });
    });

    // 댓글 목록 페이징 함수
    function getRepliesPaging(page) {

        $.getJSON("/replies/" + articleNo + "/" + page, function (data) {
            console.log(data);

            let str = "";

            $(data.replies).each(function () {
                str += "<li data-replyNo='" + this.replyNo + "' class='replyLi'>"
                    +  "<p class='replyText'>" + this.replyText + "</p>"
                    +  "<p class='replyWriter'>" + this.replyWriter + "</p>"
                    +  "<button type='button' class='btn btn-xs btn-success' data-toggle='modal' data-target='#modifyModal'>댓글 수정</button>"
                    +  "</li>"
                    +  "<hr/>";
            });

            $("#replies").html(str);

            // 페이지 번호 출력 호출
            printPageNumbers(data.pageMaker);
        });
    }

    // 댓글 목록 페이지 번호 출력 함수
    function printPageNumbers(pageMaker) {

        let str = "";

        // 이전 버튼 활성화
        if (pageMaker.prev) {
            str += "<li><a href='"+(pageMaker.startPage-1)+"'>이전</a></li>";
        }
        // 페이지 번호
        for (var i = pageMaker.startPage, len = pageMaker.endPage; i <= len; i++) {
            var strCalss = pageMaker.criteria.page === i ? 'class=active' : '';
            str += "<li "+strCalss+"><a href='"+i+"'>"+i+"</a></li>";
        }
        // 다음 버튼 활성화
        if (pageMaker.next) {
            str += "<li><a href='"+(pageMaker.endPage + 1)+"'>다음</a></li>";
        }

        $(".pagination-sm").html(str);
    }

    // 목록 페이지 번호 변수 선언, 1로 초기화(첫번째 페이지)
    let replyPageNum = 1;

    // 댓글 목록 페이지 호출
    getRepliesPaging(replyPageNum);

    // 댓글 목록 페이지 번호 클릭 이벤트 처리
    $(".pagination").on("click", "li a", function (event) {
        event.preventDefault();
        replyPageNum = $(this).attr("href");    // 목록 페이지 번호 추출
        getRepliesPaging(replyPageNum);         // 목록 페이지 호출
    });

</script>
</body>
</html>