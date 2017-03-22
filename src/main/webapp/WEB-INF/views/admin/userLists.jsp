<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <title>HEROIC CRM-员工管理</title>
    <!-- Tell the browser to be responsive to screen width -->
    <meta content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no" name="viewport">
    <%@include file="../include/css.jsp"%>
    <link rel="stylesheet" href="/static/dist/css/skins/skin-blue.min.css">
    <link rel="stylesheet" href="/static/plugins/datatables/jquery.dataTables.css">
</head>
<body class="hold-transition skin-blue sidebar-mini">
<div class="wrapper">

    <%@include file="../include/header.jsp"%>
    <jsp:include page="../include/sider.jsp">
        <jsp:param name="menu" value="adminUser"/>
    </jsp:include>

    <!-- Content Wrapper. Contains page content -->
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        <section class="content-header">
            <h1>
                员工管理
            </h1>
        </section>

        <!-- Main content -->
        <section class="content">

            <div class="box box-primary">
                <div class="box-header with-border">
                    <h3 class="box-title">员工列表</h3>
                    <div class="box-tools pull-right">
                        <a href="javascript:;" id="newBtn" class="btn btn-xs btn-success"><i class="fa fa-plus"></i> 新增</a>
                    </div>
                </div>
                <div class="box-body">
                    <form action="" class="form-inline">
                        <input type="text" name="q_like_s_username_or_realname" value="${q_like_s_username_or_realname}" class="form-control" placeholder="账号名或员工姓名">
                        <button class="btn btn-default">搜索</button>
                    </form>
                    <table class="table" id="userTable">
                        <thead>
                        <tr>
                            <th hidden>ID</th>
                            <th>账号</th>
                            <th>员工姓名</th>
                            <th>微信号</th>
                            <th>角色</th>
                            <th>状态</th>
                            <th>创建时间</th>
                            <th>#</th>
                        </tr>
                        </thead>
                        <tbody>
                            <c:forEach items="${page.items}" var="user">
                                <tr>
                                    <td hidden>${user.id}</td>
                                    <td>${user.username}</td>
                                    <td>${user.realname}</td>
                                    <td>${user.weixin}</td>
                                    <td>${user.role.rolename}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.enable == 'true'}"><span class='label label-success'>正常</span></c:when>
                                            <c:otherwise><span class='label label-success'>禁用</span></c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td class="createtime">${user.createtime}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user.username == 'admin'}"></c:when>
                                            <c:otherwise><a href="javascript:;" class="resetPwd" rel="${user.id}">重置密码</a> &nbsp;&nbsp;
                                                <a href="javascript:;" class="edit" rel="${user.id}">编辑</a></c:otherwise>
                                        </c:choose>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>

                    </table>
                </div>
            </div>
            <ul class="pagination pull-right" id="page"></ul>
        </section>
        <!-- /.content -->
    </div>
    <!-- /.content-wrapper -->
</div>
<!-- ./wrapper -->


<div class="modal fade" id="newModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">新增用户</h4>
            </div>
            <div class="modal-body">
                <form id="newForm">
                    <div class="form-group">
                        <label>账号(用于系统登录)</label>
                        <input type="text" class="form-control" name="username">
                    </div>
                    <div class="form-group">
                        <label>员工姓名(真实姓名)</label>
                        <input type="text" class="form-control" name="realname">
                    </div>
                    <div class="form-group">
                        <label>密码(默认 000000)</label>
                        <input type="text" class="form-control" name="password" value="000000">
                    </div>
                    <div class="form-group">
                        <label>微信号</label>
                        <input type="text" class="form-control" name="weixin">
                    </div>
                    <div class="form-group">
                        <label>角色</label>
                        <select class="form-control" name="role.id">
                            <c:forEach items="${roleList}" var="role">
                                <option value="${role.id}">${role.rolename}</option>
                            </c:forEach>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="saveBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<div class="modal fade" id="editModal">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title">编辑用户</h4>
            </div>
            <div class="modal-body">
                <form id="editForm">
                    <input type="hidden" name="id" id="edit_user_id">
                    <div class="form-group">
                        <label>账号(用于系统登录)</label>
                        <input type="text" class="form-control" disabled name="username" id="edit_user_username">
                    </div>
                    <div class="form-group">
                        <label>员工姓名(真实姓名)</label>
                        <input type="text" class="form-control" name="realname" id="edit_user_realname">
                    </div>
                    <div class="form-group">
                        <label>微信号</label>
                        <input type="text" class="form-control" name="weixin" id="edit_user_weixin">
                    </div>
                    <div class="form-group">
                        <label>角色</label>
                        <select class="form-control" name="role.id" id="edit_user_roleid">

                            <c:forEach items="${roleList}" var="role">
                                <option value="${role.id}">${role.rolename}</option>
                            </c:forEach>
                        </select>
                    </div>
                    <div class="form-group">
                        <label>状态</label>
                        <select class="form-control" name="enable" id="edit_user_enable">
                            <option value="true">正常</option>
                            <option value="false">禁用</option>
                        </select>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                <button type="button" class="btn btn-primary" id="editBtn">保存</button>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->



<!-- REQUIRED JS SCRIPTS -->

<!-- jQuery 2.2.0 -->
<script src="/static/plugins/jQuery/jQuery-2.2.0.min.js"></script>
<!-- Bootstrap 3.3.6 -->
<script src="/static/bootstrap/js/bootstrap.min.js"></script>
<!-- AdminLTE App -->
<script src="/static/dist/js/app.min.js"></script>
<script src="/static/plugins/datatables/jquery.dataTables.min.js"></script>
<script src="/static/plugins/moment.js"></script>
<script src="/static/plugins/validate/jquery.validate.min.js"></script>
<script src="/static/plugins/jquery.twbsPagination.min.js"></script>
<script src="/static/layer.js"></script>
<script>
    $(function(){
        $("#page").twbsPagination({
            totalPages:${page.totalPages},
            visiblePages:5,
            first:'首页',
            prev:'上一页',
            next:'下一页',
            last:'末页',
            href:'?p={{number}}&q_like_s_username_or_realname=${q_like_s_username_or_realname}'
        });
        //时间格式
        $(".createtime").text(function () {
            return moment($(this).text())
                .format("YYYY-MM-DD HH:mm")
        });

        //新增用户
        $("#newForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                username:{
                    required:true,
                    rangelength:[3,20],
                    remote:"/admin/user/checkusername"
                },
                realname:{
                    required:true,
                    rangelength:[2,20]
                },
                password:{
                    required:true,
                    rangelength:[6,18]
                },
                weixin:{
                    required:true
                }
            },
            messages:{
                username:{
                    required:"请输入用户名",
                    rangelength:"用户名的长度3~20位",
                    remote:"该用户名已被占用"
                },
                realname:{
                    required:"请输入真实姓名",
                    rangelength:"真实姓名长度2~20位"
                },
                password:{
                    required:"请输入密码",
                    rangelength:"密码长度6~18位"
                },
                weixin:{
                    required:"请输入微信号码"
                }
            },
            submitHandler:function(form){
                $.post("/admin/users/new",$(form).serialize()).done(function(data){
                    if(data == "success") {
                        $("#newModal").modal('hide');
//                        location.reload();
                        layer.msg("创建成功！");
                        windows.history.go(0);
                    }
                }).fail(function(){
                    alert("服务器异常");
                });
            }
        });

        $("#newBtn").click(function(){
            $("#newForm")[0].reset();
            $("#newModal").modal({
                show:true,
                backdrop:'static',
                keyboard:false
            });
        });

        $("#saveBtn").click(function(){
            $("#newForm").submit();
        });

        //重置密码
        $(document).delegate(".resetPwd","click",function(){
            var id = $(this).attr("rel");
            layer.confirm("确认将密码重置为：000000？",function (index) {
                $.post("/admin/users/resetpassword",{"id":id}).done(function(data){
                    if(data == 'success') {
                        layer.msg("密码重置成功");
                    }
                }).fail(function(){
                    alert("服务器异常");
                });
                layer.close(index);
            });
        });

        //编辑
        $("#editForm").validate({
            errorClass:"text-danger",
            errorElement:"span",
            rules:{
                realname:{
                    required:true,
                    rangelength:[2,20]
                },
                weixin:{
                    required:true
                }
            },
            messages:{
                realname:{
                    required:"请输入真实姓名",
                    rangelength:"真实姓名长度2~20位"
                },
                weixin:{
                    required:"请输入微信号码"
                }
            },
            submitHandler:function(form){
                $.post("/admin/users/edit",$(form).serialize()).done(function(data){
                    if(data == "success") {
                        $("#editModal").modal('hide');
                        layer.msg("修改成功");
                        window.history.go(0);
                    }
                }).fail(function(){
                    alert("服务器异常");
                });
            }
        });

        $(document).delegate(".edit","click",function(){
            var id = $(this).attr("rel");
            $.get("/admin/users/"+id+".json").done(function(result){
                if(result.state == "success") {
                    $("#edit_user_id").val(result.data.id);
                    $("#edit_user_username").val(result.data.username);
                    $("#edit_user_realname").val(result.data.realname);
                    $("#edit_user_weixin").val(result.data.weixin);
                    $("#edit_user_roleid").val(result.data.role.id);
                    $("#edit_user_enable").val(result.data.enable.toString());
                    $("#editModal").modal({
                        show:true,
                        dropback:'static',
                        keyboard:false
                    });
                } else {
                    layer.msg(result.message);
                }
            }).fail(function(){
                alert("服务器异常");
            });
        });

        $("#editBtn").click(function(){
            $("#editForm").submit();
        });
    });
</script>
</body>
</html>
