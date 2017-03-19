<%--
  Created by IntelliJ IDEA.
  User: jiahao0
  Date: 2017/3/18
  Time: 9:59
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>HEROIC CRM | 文档管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <!-- Bootstrap 3.3.6 -->
    <%@include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/uploader/webuploader.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="../include/header.jsp"%>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="document"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">

        <!-- Main content -->
        <section class="content">

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">文档列表</h3>
                    <div class="box-tools">
                        <span id="uploadBtn"><span class="text"><i class="fa fa-uploa d"></i> 上传文件</span></span>
                        <button class="btn btn-bitbucket btn-xs" id="newDir"><i class="fa fa-folder"></i> 新建文件夹</button>
                    </div>
                </div>
                <div class="box-body">

                    <table class="table">
                        <thead>
                        <tr>
                            <th></th>
                            <th>名称</th>
                            <th>大小</th>
                            <th>创建人</th>
                            <th>创建时间</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty documentList}">
                            <tr>
                                <td colspan="5">暂时没有任何数据</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${documentList}" var="doc">
                            <tr>
                                <c:choose>
                                    <c:when test="${doc.type == 'dir'}">
                                        <td><i class="fa fa-folder-o"></i></td>
                                        <td><a href="/doc?fid=${doc.id}">${doc.name}</a></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><i class="fa fa-file-o"></i></td>
                                        <td><a href="/doc/download/${doc.id}">${doc.name}</a></td>
                                    </c:otherwise>
                                </c:choose>

                                <td>${doc.size}</td>
                                <td>${doc.createuser}</td>
                                <td><fmt:formatDate value="${doc.createtime}" pattern="y-M-d H:m"/>${doc.createtime}</td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>
            </div>

        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->

<div class="modal fade" id="dirModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新建文件夹</h4>
            </div>
            <div class="modal-body">
                <form action="/doc/dir/new" method="post" id="saveDirForm">
                    <input type="hidden" name="fid" value="${fid}">
                    <div class="form-group">
                        <label>文件夹名称</label>
                        <input type="text" class="form-control" name="name" id="dirname">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveDirBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->

<%@include file="../include/js.jsp"%>
</body>
</html>
