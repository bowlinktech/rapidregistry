<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default actions-nav" role="navigation">
    <div class="contain">
        <div class="navbar-header">
            <h1 class="section-title navbar-brand">
                <c:choose>
                    <c:when test="${param['page'] == 'surveys'}">
                        <a href="javascript:void(0);" title="Surveys" class="unstyled-link">Surveys</a>
                    </c:when>
                    <c:when test="${param['page'] == 'createSurvey'}">
                        <a href="javascript:void(0);" title="Surveys" class="unstyled-link">Create New Survey</a>
                    </c:when>
                    <c:when test="${param['page'] == 'surveyDetails'}">
	                    <div id="surveyTitle">
	                        <a href="#surveyModal" data-toggle="modal" id="editSurveyTitleActionBar" title="${surveyTitle}" relPage="1" relS="${surveyId}" class="editSurveyTitle unstyled-link" role="button">${surveyTitle}</a>
	                   </div>
                    </c:when>
                    <c:when test="${param['page'] == 'staff'}">
                        <a href="javascript:void(0);" title="Staff Members" class="unstyled-link">Staff Members</a>
                    </c:when>    
                    <c:when test="${param['page'] == 'staffdetails'}">
                        <a href="javascript:void(0);" title="Staff Member Details" class="unstyled-link">Staff Member Details</a>
                    </c:when>
                </c:choose>
            </h1>
        </div>
        <ul class="nav navbar-nav navbar-right navbar-actions" role="menu">
           <c:choose>
                <c:when test="${param['page'] == 'staffdetails'}">
                    <li role="menuitem"><a href="javascript:void(0);" id="saveDetails" title="Save Form" role="button"><span class="glyphicon glyphicon-ok icon-stacked"></span> Save </a></li>
                    <li role="menuitem"><a href="javascript:void(0);" id="saveCloseDetails" title="Save &amp; Close" role="button"><span class="glyphicon glyphicon-floppy-disk icon-stacked"></span> Save &amp; Close</a></li>
                    <li role="menuitem"><a href="/programAdmin/staff" title="Save &amp; Close" role="button"><span class="glyphicon glyphicon-ban-circle icon-stacked"></span> Cancel</a></li>
                </c:when>
                <c:when test="${param['page'] == 'surveys'}">
                    <li role="menuitem"><a href="/programAdmin/surveys/create" id="createSurvey" title="Create New Survey" role="button"><span class="glyphicon glyphicon-plus-sign icon-stacked"></span> Create New Survey </a></li>
                </c:when>
                <c:when test="${param['page'] == 'createSurvey'}">
                    <li role="menuitem"><a href="javascript:void(0);" id="saveNewSurvey" title="Save Form" role="button"><span class="glyphicon glyphicon-ok icon-stacked"></span> Next </a></li>
                    <li role="menuitem"><a href="javascript:void(0);" id="saveNextNewSurvey" title="Save &amp; Close" role="button"><span class="glyphicon glyphicon-floppy-disk icon-stacked"></span>Save &amp; Close</a></li>
                    <li role="menuitem"><a href="/programAdmin/surveys" title="Save &amp; Close" role="button"><span class="glyphicon glyphicon-ban-circle icon-stacked"></span> Cancel</a></li>
                </c:when>
                <c:when test="${param['page'] == 'surveyDetails'}">
                    <li role="menuitem"><a href="/programAdmin/surveys" title="Cancel" role="button"><span class="glyphicon glyphicon-ban-circle icon-stacked"></span> Cancel</a></li>
                </c:when>
          </c:choose>
        </ul>
    </div>
</nav>



<!--  note modal - this appears when a survey is changed -->
<div class="modal fade" id="surveySaveNote" role="dialog" tabindex="-1" aria-labeledby="Change Log Note Modal" aria-hidden="true" aria-describedby="Change log note model"></div>

