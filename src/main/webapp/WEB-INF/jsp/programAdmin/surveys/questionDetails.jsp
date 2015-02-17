<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="s" uri="http://www.springframework.org/tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<%-- need to add codes to figure out which question it is  --%>
<div class="panel-body">
                <form:form id="pageForm" method="post" role="form">
                    <section class="panel panel-default">
                        <div class="panel-heading clearfix" style="height:46px; background-color: rgba(0,0,0, 0.3);">
                            <div class="btn-group pull-left">
                                <ul class="nav panel-tabs">
                                    <li class="active"><a href="#edit" data-toggle="tab"><strong>Edit</strong></a></li>
                                    <li><a href="#options" data-toggle="tab"><strong>Options</strong></a></li>
                                    <li><a href="#logic" data-toggle="tab"><strong>Logic</strong></a></li>
                                    <li><a href="#move" data-toggle="tab"><strong>Move</strong></a></li>
                                    <li><a href="#copy" data-toggle="tab"><strong>Copy</strong></a></li>
                                </ul>
                            </div>
                        </div>
                        <div class="panel-body">
                            <div class="tab-content">
                                <div class="tab-pane active" id="edit">
                                    Edit Section Q. ${param.questionForPage.question}
                                </div> 
                                <div class="tab-pane" id="options">
                                    Options Section
                                </div>
                                <div class="tab-pane" id="logic">
                                    Logic Section
                                </div>
                                <div class="tab-pane" id="move">
                                    Move Section
                                </div>
                                <div class="tab-pane" id="copy">
                                    Copy Section
                                </div>
                            </div>   
                        </div>
                    </section>
                </form:form>
</div>