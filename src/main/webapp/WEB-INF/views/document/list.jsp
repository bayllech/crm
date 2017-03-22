<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<!--
This is a starter template page. Use this page to start your new project from
scratch. This page gets rid of all links and provides the needed markup only.
-->
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>凯盛CRM | 文档管理</title>
    <%@include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/plugins/uploader/webuploader.css">

    <style>
        #uploadBtn{
            float: left;
            margin-right:20px;
        }
    </style>
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
                        <span id="uploadBtn"><span class="text"><i class="fa fa-upload"></i> 上传文件</span></span>
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
                            <th>#</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:if test="${empty documentList}">
                            <tr>
                                <td colspan="6">暂时没有任何数据</td>
                            </tr>
                        </c:if>
                        <c:forEach items="${documentList}" var="doc">
                            <tr>
                                <c:choose>
                                    <c:when test="${doc.type == 'dir'}">
                                        <td><i class="fa fa-folder-o"></i></td>
                                        <td><a href="/document?fid=${doc.id}">${doc.name}</a></td>
                                    </c:when>
                                    <c:otherwise>
                                        <td><i class="fa fa-file-o"></i></td>
                                        <td><a href="/document/download/${doc.id}">${doc.name}</a></td>
                                    </c:otherwise>
                                </c:choose>

                                <td>${doc.size}</td>
                                <td>${doc.createuser}</td>
                                <td><fmt:formatDate value="${doc.createtime}" pattern="y-M-d H:m"/></td>
                                <td>
                                    <a href="javascript:;" class="remove" rel="${doc.id}"><i class="fa fa-trash text-danger"></i></a>
                                </td>
                            </tr>
                        </c:forEach>

                        </tbody>
                    </table>
                </div>
                <div class="box-footer">
                    <ul style="margin:5px 0px" id="pagination" class="pagination pull-right"></ul>
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
                <form action="/document/dir/new" method="post" id="saveDirForm">
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

<!-- REQUIRED JS SCRIPTS -->

<%@include file="../include/js.jsp"%>
<script src="/static/plugins/layer/layer.js"></script>
<script src="/static/plugins/uploader/webuploader.min.js"></script>
<script src="/static/plugins/jquery.twbsPagination.min.js"></script>

<script>
    $(function(){
        //上传文件
        var uploader = WebUploader.create({
            swf:"/static/plugins/uploader/Uploader.swf",
            pick:"#uploadBtn",
            server:"/document/file/upload",
            fileValL:"file",
            formData:{"fid":"${fid}"},
            auto:true //选择文件后直接上传
        });
        //上传文件成功
        uploader.on("startUpload",function(){
            $("#uploadBtn .text").html('<i class="fa fa-spinner fa-spin"></i> 上传中...');
        });
        uploader.on( 'uploadSuccess', function( file,data ) {
            if(data._raw == "success") {
                layer.msg("上传成功");
                window.history.go(0);
            } else {
                layer.msg("上传失败");
            }

        });
        uploader.on( 'uploadError', function( file ) {
            layer.msg("文件上传失败");
        });
        uploader.on( 'uploadComplete', function( file ) {
            $("#uploadBtn .text").html('<i class="fa fa-upload"></i> 上传文件').removeAttr("disabled");;
        });

        //新建文件夹
        $("#newDir").click(function(){
            $("#dirModal").modal({
                show:true,
                backdrop:'static',
                keyboard:false
            });
        });
        $("#saveDirBtn").click(function(){
            if(!$("#dirname").val()) {
                $("#dirname").focus();
                return;
            }
            $("#saveDirForm").submit();
        });

        //删除
        $(".remove").click(function () {
            var id = $(this).attr("rel");
            layer.confirm("确定要删除吗?",function(index){
                layer.close(index);
                $.get("/document/del/"+id).done(function(resp){
                    if(resp.status == 'success') {
                        layer.msg("删除成功");
                        window.history.go(0);
                    } else {
                        layer.msg(resp.message);
                    }
                }).error(function(){
                    layer.msg("服务器忙，请稍后");
                })
            });
        });

        //分页插件的使用
        $("#pagination").twbsPagination({
            totalPages:${page.totalPages},
            visiblePages:5,
            href:"/document?p={{number}}",
            first:"首页",
            prev:"上一页",
            next:"下一页",
            last:"末页"
        });

    });
</script>
</body>
</html>
