<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>

<html>

<%@include file="../include/head.jsp"%>

<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <!-- Main Header -->
    <%@include file="../include/main_header.jsp"%>

    <!-- Left side column. contains the logo and sidebar -->
    <%@include file="../include/left_column.jsp"%>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                에러, 예외 페이지
                <small>Optional description</small>
            </h1>
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-dashboard"></i> Level</a></li>
                <li class="active">Here</li>
            </ol>
        </section>

        <!-- Main content -->
        <section class="content container-fluid">

            <!--------------------------
              | Your Page Content Here |
              -------------------------->
            <h3><i class="fa fa-warning text-red"></i> ${exception.getMessage()}</h3>
            <ul>
                <c:forEach items="${exception.getStackTrace()}" var="stack">
                    <li>${stack.toString()}</li>
                </c:forEach>
            </ul>



        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->

    <!-- Main Footer -->
    <%@include file="../include/main_footer.jsp"%>

</div>
<!-- ./wrapper -->
<%@include file="../include/plugin_js.jsp"%>
</body>
</html>