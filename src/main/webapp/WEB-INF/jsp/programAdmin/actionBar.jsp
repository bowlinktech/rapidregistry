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
                            <a href="#surveyModal" data-toggle="modal" title="${survey.title}" rel="${encryptedId}" rel2="${encryptedSecret}" class="editSurveyTitle unstyled-link" role="button">
                                ${survey.title} <span class="glyphicon glyphicon-edit" style="padding-left:5px; cursor: pointer;"></span>
                            </a>
                        </div>
                    </c:when>
                    <c:when test="${param['page'] == 'staff'}">
                        <a href="javascript:void(0);" title="Users" class="unstyled-link">Users</a>
                    </c:when>    
                    <c:when test="${param['page'] == 'staffdetails'}">
                        <a href="javascript:void(0);" title="User Details" class="unstyled-link">User Details</a>
                    </c:when>
                    <c:when test="${param['page'] == 'categories'}">
                        <a href="javascript:void(0);" title="Service Categories" class="unstyled-link">Service Categories</a>
                    </c:when>  
                    <c:when test="${param['page'] == 'services'}">
                        <a href="javascript:void(0);" title="Services" class="unstyled-link">Services</a>
                    </c:when> 
                    <c:when test="${param['page'] == 'serviceDetails'}">
                        <a href="javascript:void(0);" title="Service Details" class="unstyled-link">Service Details</a>
                    </c:when>  
                    <c:when test="${param['page'] == 'entity'}">
                        <a href="javascript:void(0);" title="Entity Management" class="unstyled-link">Entity Management</a>
                    </c:when>     
                    <c:when test="${param['page'] == 'entitydetails'}">
                        <a href="javascript:void(0);" title="Entity Management" class="unstyled-link">Entity Management</a>
                    </c:when>     
                </c:choose>
            </h1>
        </div>
        <ul class="nav navbar-nav navbar-right navbar-actions" role="menu">
           <c:choose>
                <c:when test="${param['page'] == 'staffdetails' || param['page'] == 'serviceDetails'}">
                    <li role="menuitem"><a href="javascript:void(0);" id="saveDetails" title="Save Form" role="button"><span class="glyphicon glyphicon-ok icon-stacked"></span> Save </a></li>
                    <li role="menuitem"><a href="javascript:void(0);" id="saveCloseDetails" title="Save &amp; Close" role="button"><span class="glyphicon glyphicon-floppy-disk icon-stacked"></span> Save &amp; Close</a></li>
                    <li role="menuitem"><a href="/programAdmin/staff" title="Save &amp; Close" role="button"><span class="glyphicon glyphicon-ban-circle icon-stacked"></span> Cancel</a></li>
                </c:when>
                <c:when test="${param['page'] == 'createSurvey'}">
                    <li role="menuitem"><a href="javascript:void(0);" id="saveNewSurvey" title="Save Form" role="button"><span class="glyphicon glyphicon-ok icon-stacked"></span> Next </a></li>
                    <li role="menuitem"><a href="javascript:void(0);" id="saveNextNewSurvey" title="Save &amp; Close" role="button"><span class="glyphicon glyphicon-floppy-disk icon-stacked"></span>Save &amp; Close</a></li>
                    <li role="menuitem"><a href="/programAdmin/surveys" title="Save &amp; Close" role="button"><span class="glyphicon glyphicon-ban-circle icon-stacked"></span> Cancel</a></li>
                </c:when>
                <c:when test="${param['page'] == 'surveyDetails'}">
                    <li role="menuitem"><a href="/programAdmin/surveys" title="Cancel" role="button"><span class="glyphicon glyphicon-ban-circle icon-stacked"></span> Exit Survey Builder</a></li>
                </c:when>
                <c:when test="${param['page'] == 'entitydetails'}">
                    <li role="menuitem"><a href="javascript:void(0);" id="saveDetails" title="Save Form" role="button"><span class="glyphicon glyphicon-ok icon-stacked"></span> Save </a></li>
                    <li role="menuitem"><a href="javascript:void(0);" id="saveCloseDetails" title="Save &amp; Close" role="button"><span class="glyphicon glyphicon-floppy-disk icon-stacked"></span> Save &amp; Close</a></li>
                    <li role="menuitem"><a href="/programAdmin/entity" title="Save &amp; Close" role="button"><span class="glyphicon glyphicon-ban-circle icon-stacked"></span> Cancel</a></li>
                </c:when>    
          </c:choose>
        </ul>
    </div>
</nav>



<!--  note modal - this appears when a survey is changed -->
<div class="modal fade" id="surveySaveNote" role="dialog" tabindex="-1" aria-labeledby="Change Log Note Modal" aria-hidden="true" aria-describedby="Change log note model"></div>

