<%-- 
    Document   : historySearch
    Created on : May 8, 2014, 8:49:35 AM
    Author     : chadmccue
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="container main-container" role="main">
    <div class="row">
        <div class="col-md-12 page-content">
            <ol class="breadcrumb">
                <li><a href="<c:url value='/profile'/>">My Account</a></li>
                <li class="active">History</li>
            </ol>

            <form id="searchHistoryForm" action="/Health-e-Web/history" role="form" class="form" method="post">
                <input type="hidden" name="fromDate" id="fromDate" rel="<fmt:formatDate value="${fromDate}" type="date" pattern="MM/dd/yyyy" />" rel2="<fmt:formatDate value="${userDetails.dateOrgWasCreated}" type="date" pattern="MM/dd/yyyy" />" value="${fromDate}" />
                <input type="hidden" name="toDate" id="toDate" rel="<fmt:formatDate value="${toDate}" type="date" pattern="MM/dd/yyyy" />" value="${toDate}" />
                               
                 <div class="col-md-12 form-section">
                    <section class="panel panel-default">
                        <div class="panel-heading">
                            <h3 class="panel-title">History Search Criteria</h3>
                        </div>
                        <div class="panel-body">
                            <div class="form-container scrollable">
                                <div class="col-md-12">
                                    <div class="form-group">
                                        <label class="control-label" for="fromDate">Date Range</label>
                                        <div class="date-range-picker-trigger form-control daterange" style="width:265px; margin-left: 10px;">
                                            <i class="glyphicon glyphicon-calendar"></i>
                                            <span class="date-label"  rel="" rel2=""><fmt:formatDate value="${fromDate}" type="date" pattern="MMM dd, yyyy" /> - <fmt:formatDate value="${toDate}" type="date" pattern="MMM dd, yyyy" /></span> <b class="caret"></b>
                                        </div>
                                   </div>
                                </div>
                               <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label" for="type">Type</label>
                                        <select name="type" class="form-control">
                                            <option value="0">Both (Referrals & Feedback Reports)</option>
                                            <option value="1">Referrals Only</option>
                                            <option value="2">Feedback Reports Only</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label" for="sentTo">Sent To</label>
                                        <select name="sentTo" class="form-control">
                                            <option value="0">- All Affiliated Organizations -</option>
                                            <c:forEach var="org" items="${associatedOrgs}">
                                                <option value="${org.id}">${org.orgName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label" for="messageType"> Message Type</label>
                                        <select name="messageType" class="form-control">
                                            <option value="0">- All Message Types -</option>
                                            <c:forEach var="msgType" items="${assocMessageTypes}">
                                                <option value="${msgType[0]}">${msgType[1]}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label" for="receivedFrom">Received From</label>
                                        <select name="receivedFrom" class="form-control">
                                            <option value="0">- All Affiliated Organizations -</option>
                                            <c:forEach var="org" items="${associatedOrgs}">
                                                <option value="${org.id}">${org.orgName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label" for="status"> Referral Status</label>
                                        <select name="status" class="form-control">
                                            <option value="0">Both (Open & Closed)</option>
                                            <option value="1">Opened Referrals Only</option>
                                            <option value="2">Closed Referrals Only</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label" for="systemStatus">System Status</label>
                                        <select name="systemStatus" class="form-control">
                                            <option value="0">- All System Statuses -</option>
                                            <option value=""></option>
                                        </select>
                                    </div>
                                </div>  
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label" for="reportType">Report Type</label>
                                        <select name="reportType" class="form-control">
                                            <option value="1">Summary Report</option>
                                            <option value="2">Detailed Report</option>
                                        </select>
                                    </div>
                                </div>        
                            </div>
                        </div>
                    </section>
                   <input type="button" id="search1" class="btn btn-primary btn-action-sm search" value="Search"/>   
                   <span style="padding-left:10px;" id="showOther"><a href="javascript:void(0);" class="showOtherCriteria">Show additional Search Criteria</a></span>
                   <section class="panel panel-default" id="additionalCriteria" style="margin-top: 20px; display:none;">
                        <div class="panel-heading">
                            <h3 class="panel-title">Additional Search Criteria</h3>
                        </div>
                        <div class="panel-body">
                            <div class="form-container scrollable">
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label" for="batchName">Batch Name</label>
                                        <input type="text" class="form-control" name="batchName" />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label" for="utBatchName">System Referral Id</label>
                                        <input type="text" class="form-control" name="utBatchName" />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label" for="lastName">Patient Last Name</label>
                                        <input type="text" class="form-control" name="lastName" />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label" for="patientId">Patient Id</label>
                                        <input type="text" class="form-control" name="patientId" />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label" for="firstName">Patient First Name</label>
                                        <input type="text" class="form-control" name="firstName" />
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <div class="form-group">
                                        <label class="control-label" for="providerId">Provider Id</label>
                                        <input type="text" class="form-control" name="providerId" />
                                    </div>
                                </div>       
                            </div>
                        </div>
                    </section>
                    <input type="button" id="search2" class="btn btn-primary btn-action-sm search" value="Search" style="display:none;"/>   
                    <span style="padding-left:10px; display: none" id="hideOther"><a href="javascript:void(0);" class="hideOtherCriteria">Hide additional Search Criteria</a></span>                     
                </div>

            </form>    

        </div>
    </div>
</div>