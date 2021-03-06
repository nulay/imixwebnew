<%@ page import="by.imix.cms.web.Const" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<c:if test="${not empty key_user }">
    <div id="menu-for-admin">
        <!-- use it for correct load static resourses -->
        <c:set var="ctx" scope="request" value="${pageContext.request.contextPath}"/>
            <%--for client side , ex. ajax in javaScript--%>
        <script>var ctx = "${ctx}";
        var ctxmodule = "${ctxmodule}";</script>

        <link href="${ctx}/resources/css/adminMenu.css?v2" rel="stylesheet">
        <link href="<c:url value="<%=Const.JQUERY_UI_THEMES_CSS%>"/>" rel="stylesheet">
        <link href="<c:url value="<%=Const.JQUERY_CONTEXT_MENU_CSS%>"/>" rel="stylesheet">
        <link href="<c:url value="<%=Const.BOOTSTRAP_CSS%>"/>" rel="stylesheet">

        <script type="text/javascript" src="<c:url value="<%=Const.JQUERY_JS%>"/>"></script>
        <script type="text/javascript" src="<c:url value="<%=Const.BOOTSTRAP_JS%>"/>"></script>
        <script type="text/javascript"
                src="${pageContext.request.contextPath}/webjars/ckeditor/4.4.7-1/full/ckeditor.js"></script>
        <script type="text/javascript" src="<c:url value="<%=Const.JQUERY_CONTEXT_MENU_JS%>"/>"></script>
        <script type="text/javascript" src="<c:url value="<%=Const.JQUERY_UI_JS%>"/>"></script>

        <link rel="stylesheet" type="text/css" href="<c:url value="<%=Const.JQ_GRID_CSS%>"/>"/>
            <%--<link rel="stylesheet" type="text/css" href="/webjars/jqgrid/<%=Const.VER_JQGRID%>/css/ui.jqgrid-bootstrap.css"/>--%>
            <%--<link rel="stylesheet" type="text/css" href="/webjars/jqgrid/<%=Const.VER_JQGRID%>/css/ui.jqgrid-bootstrap-ui.css"/>--%>
            <%--<script type="text/javascript">--%>
            <%--jQuery.jgrid.no_legacy_api = true;--%>
            <%--</script>--%>
        <script type="text/javascript" src="<c:url value="<%=Const.JQ_GRID_JS%>" />"></script>
        <script type="text/javascript" src="<c:url value="<%=Const.JQ_GRID_LOCATION_JS%>" />"></script>


            <%--<link rel="stylesheet" type="text/css"--%>
            <%--href="${pageContext.request.contextPath}/webjars/datatables/1.10.7/css/jquery.dataTables.min.css">--%>
            <%--<script type="application/javascript"--%>
            <%--src="${pageContext.request.contextPath}/webjars/datatables/1.10.7/js/jquery.dataTables.min.js"></script>--%>

        <script type="application/javascript"
                src="${pageContext.request.contextPath}/webjars/noty/2.3.5/js/noty/packaged/jquery.noty.packaged.min.js"></script>


            <%--???????????????? ???????????????? ?????? ???????????????? ?????????????? ???????????? ???????? ?????????? ?????????? template, ?????????? ???? ?????????? ????????????????--%>
        <template id="tmpDialogOne">
            <div id="dialog-form-add" title="???????????????????????????? ????????????????????" class="ui-dialog ">
                <div class="dialog-content">

                    <div class="row">
                        <div class="col-md-10">
                            <form id="form-add" role="form" action="?test" method="post">
                                <input type="hidden" name="dynBlockId">
                                <fieldset>
                                    <legend>?????????????????? ??????????????????????</legend>
                                </fieldset>
                                <div id="dialog-form-add-content">
                                </div>

                            </form>
                        </div>
                    </div>
                    <div id="razmetka" class="row hidden">
                        <div class="col-md-10">
                            <span class="help-block" aria-describedby="nameDC"></span>
                            <fieldset>
                                <legend>???????????????????? ????????????????</legend>
                            </fieldset>
                            <div class="input-group"><span>?????? ???????????????????????? ????????????????????</span><input id="nameDC"
                                                                                                    class="form-control">
                            </div>
                            <span class="help-block" aria-describedby="nameDC"></span>
                            <span>?????????????????? ????????????<select id="shName">
                                    <%--<option value="1">????????????</option>--%>
                                    <%--<option value="2">??????????</option>--%>
                                    <%--<option value="3">??????????</option>--%>
                            </select></span>
                                <%--<button class="disabled" disabled>??????????????????</button>--%>
                            <div id="wrapperframe2">
                                <iframe id="frame2" class="col-md-12 " style="height: 100px;"
                                        scrolling="auto"></iframe>
                            </div>
                        </div>
                    </div>
                    <div class="row hidden">
                        <div class="col-md-4">
                            <table class="table">
                                <tr>
                                    <td>??????</td>
                                    <td id="nameblock"></td>
                                </tr>
                                <tr>
                                    <td>????</td>
                                    <td id="idblock"></td>
                                </tr>
                                <tr>
                                    <td>????????????</td>
                                    <td id="idsname"></td>
                                </tr>
                            </table>
                        </div>
                    </div>
                </div>


            </div>
        </template>

        <template id="tmpFirst">
            <div id="{{name}}" name="{{name}}">
                    <%--<label><span>{{num}}</span> ??????????:<label class="label label-primary">{{name}}</label></label>--%>
                <div id="grselcont" class="input-group">
                        <span id="typecont" class="input-group-addon ">
                            <select id="{{id}}-{{name}}-sel" class="">
                                <option value="container">??????????????????</option>
                                <option value="jsp">???????? ?? ?????????? jsp</option>
                                <option value="tiles">??????-tiles</option>
                                <option value="text" selected>??????????</option>
                            </select>
                        </span>
                    <input id="{{id}}-{{name}}-inp" class="form-control " placeholder="?????? ????????????">
                    <select id="selcontainers" class="form-control hidden"></select>
                </div>
                <div id="wrapperframe">
                    <iframe id="framecont" class="col-md-12 hidden" style="height: 175px;" scrolling="auto"
                            sandbox></iframe>
                </div>
                <div id="wrappereditor1" class="hidden"><textarea id="editor1"></textarea></div>
            </div>
        </template>

        <template id="tmpDialogRoleFirst">
            <div id="dialog-role-first" title="???????????????????? ????????????" class="ui-dialog ">
                <div class="dialog-content">
                    <div class="row">
                        <ul id="roleTabs" class="nav nav-tabs">
                            <li class="active"><a href="#redit" aria-controls="redit" role="tab" data-toggle="tab">????????????????</a>
                            </li>
                            <li><a href="#rcreate" aria-controls="rcreate" role="tab" data-toggle="tab">??????????????</a></li>
                            <li><a href="#rdelete" aria-controls="rdelete" role="tab" data-toggle="tab">??????????????</a></li>
                        </ul>
                        <!-- Tab panes -->
                        <div class="tab-content">
                            <div id="redit" class="tab-pane fade in active">
                                <div class="form-group">
                                    <span class="col-md-4"><select id="roleList" class="form-control"></select></span>
                                    <input id="btn-role-save" class="btn btn-warning" type="button"
                                           value="????????????????">
                                </div>
                                <div class="form-group col-md-4">
                                    <table class="table roles">
                                        <tbody id="tPermissions">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div id="rcreate" class="tab-pane fade">
                                <div class="form-group">
                                    <span class="col-md-4"><input id="name-new-role" type="text" class="form-control"
                                                                  placeholder="?????? ?????????? ????????"></span>
                                    <input id="btn-role-create" class="btn btn-success" type="button" value="??????????????">
                                </div>
                                <div class="form-group col-md-4">
                                    <table class="table roles">
                                        <tbody id="tPermissions2">
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                            <div id="rdelete" class="tab-pane fade">
                                <div class="form-group">
                                        <span class="col-md-4"><select id="roleList2"
                                                                       class="form-control"></select></span>
                                    <input id="btn-role-delete" class="btn btn-danger" type="button"
                                           value="??????????????">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </template>
        <template id="tmpDialogUsersFirst">
            <div id="dialog-users-first" title="???????????????????? ????????????????????????????" class="ui-dialog ">
                <div class="dialog-content">
                    <div class="row">
                        <ul id="userTabs" class="nav nav-tabs">
                            <li class="active"><a href="#tabUserEdit" role="tab" data-toggle="tab">????????????????</a>
                            </li>
                            <li><a href="#tabUserCreate" role="tab" data-toggle="tab">??????????????</a></li>
                            <li><a href="#tabUserDelete" role="tab" data-toggle="tab">??????????????</a></li>
                        </ul>
                        <!-- Tab panes -->
                        <div class="tab-content">
                            <div id="tabUserEdit" class="tab-pane fade in active">
                                <div class="form-group">
                                    <div class="col-md-2"><input id="filterUsers" type="text" class="form-control"
                                                                 placeholder="????????????"></div>
                                    <div class="col-md-4"><select id="listUsersForChange"
                                                                  class="form-control"></select></div>
                                    <input id="btn-user-change" class="btn btn-warning" type="button" value="????????????????">
                                    <input id="btn-restore" class="btn btn-success hidden" type="button"
                                           value="????????????????????????"
                                           disabled>
                                </div>
                                <form id="user-sett" class="form-group col-md-6">
                                    <div class="checkbox">
                                        <label>
                                            <input id="user-isActive" type="checkbox"> ????????????????
                                        </label>
                                    </div>
                                    <div class="form-group">
                                        <table class="table table-striped ">
                                            <thead>
                                            <tr>
                                                <th colspan="2">?????????????????????? ????????</th>
                                            </tr>
                                            </thead>
                                            <tbody id="tUserRoles"></tbody>
                                        </table>
                                    </div>
                                </form>

                                    <%--<div class="row">--%>
                                    <%--<table id="tableUsersEdit" class="table table-striped"></table>--%>
                                    <%--<div id="gridpager"></div>--%>
                                    <%--</div>--%>

                                    <%--<div class="form-group">--%>
                                    <%--<span class="col-md-4"><select id="roleList" class="form-control"></select></span>--%>
                                    <%--<input id="btn-role-save" class="btn btn-warning" type="button"--%>
                                    <%--value="????????????????">--%>
                                    <%--</div>--%>
                                    <%--<div class="form-group col-md-4">--%>
                                    <%--<table class="table roles">--%>
                                    <%--<tbody id="tPermissions">--%>
                                    <%--</tbody>--%>
                                    <%--</table>--%>
                                    <%--</div>--%>
                            </div>
                            <div id="tabUserCreate" class="tab-pane fade">
                                    <%--<div class="form-group">--%>
                                    <%--<span class="col-md-4"><input id="name-new-role" type="text" class="form-control"--%>
                                    <%--placeholder="?????? ?????????? ????????"></span>--%>
                                    <%--<input id="btn-role-create" class="btn btn-success" type="button" value="??????????????">--%>
                                    <%--</div>--%>
                                    <%--<div class="form-group col-md-4">--%>
                                    <%--<table class="table roles">--%>
                                    <%--<tbody id="tPermissions2">--%>
                                    <%--</tbody>--%>
                                    <%--</table>--%>
                                    <%--</div>--%>
                            </div>
                            <div id="tabUserDelete" class="tab-pane fade">
                                <div class="form-group">
                                    <span class="col-md-4"><select id="listUsersForDelete"
                                                                   class="form-control"></select></span>
                                    <input id="btn-user-delete" class="btn btn-danger" type="button" value="??????????????">
                                </div>
                            </div>
                        </div>
                    </div>
                </div>


            </div>
        </template>

        <template id="tmpDialogDynamicContent">
            <div id="dialog-dc-first" title="???????????????????????? ????????????????/??????????" class="ui-dialog ">
                <div class="dialog-content">
                    <select id="selTypeDc">
                        <option value="page">page</option>
                        <option value="container">container</option>
                    </select>
                    <div class="row">
                        <table id="tableDc" class="table table-striped"></table>
                        <div id="tableDcPager"></div>
                    </div>
                </div>
            </div>
        </template>


        <template id="tmpMenuAdmin">
            <div class="adminka ui-menu">
                <ul id="adminmenu" class="ui-menu">
                    <li>A
                        <ul>
                            <li><a href="#">????????????????</a>
                                <ul>
                                    <li><a class="" href="/content/dc/ndp">?????????????? ????????????</a></li>
                                        <%--<li><a class="menudisabled" href="#">?????????????????????????? ????????????????</a></li>--%>
                                        <%--<li><a class="menudisabled" href="#">??????????????</a></li>--%>
                                    <li><a class="" href="/managercms/dynamiccontent/alldynamiccontents.html#">????????????????
                                        ??????</a></li>
                                    <li id="dc.show">???????????????? ?????? 2</li>
                                </ul>
                            </li>
                            <li>-</li>
                            <li><a href="#">??????????????</a>
                                <ul>
                                    <li id="users.first"><a href="#">???????????????????? ????????????????????????????</a></li>
                                        <%--<li><a href="#">????????????????????????</a>--%>
                                        <%--<ul>--%>
                                        <%--<li id="users.first"><a class="" href="">????????????</a></li>--%>
                                        <%--</ul>--%>
                                        <%--</li>--%>
                                    <li id="role.first"><a href="#">???????????????????? ????????????</a></li>
                                </ul>
                            </li>
                            <li>-</li>
                            <li id="backlight">???????????????? ??????????</li>
                                <%--<li><a class="menudisabled" href="#">??????????</a></li>--%>
                                <%--<li id="test"><a class="" href="">????????</a></li>--%>
                        </ul>
                    </li>
                </ul>
            </div>
        </template>


        <script type="text/javascript" src="${ctx}/resources/js/cms/adminMenu.js?v3"></script>

    </div>
    <!-- end div admin-->
</c:if>