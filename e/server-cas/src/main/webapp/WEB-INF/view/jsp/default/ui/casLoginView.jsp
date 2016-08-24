<%--

    Licensed to Jasig under one or more contributor license
    agreements. See the NOTICE file distributed with this work
    for additional information regarding copyright ownership.
    Jasig licenses this file to you under the Apache License,
    Version 2.0 (the "License"); you may not use this file
    except in compliance with the License.  You may obtain a
    copy of the License at the following location:

      http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing,
    software distributed under the License is distributed on an
    "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
    KIND, either express or implied.  See the License for the
    specific language governing permissions and limitations
    under the License.

--%>
<jsp:directive.include file="includes/top.jsp" />
		<link rel="stylesheet" href="css/ace.css" />
		<link rel="stylesheet" href="css/bootstrap.css" />
        <link rel="stylesheet" href="css/ace-fonts.css" />
        <link rel="stylesheet" href="css/font-awesome.css" />
	<body class="login-layout">
		<div class="main-container">
			<div class="main-content">
 				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<small>
			                             <img  width="50"  height="36" src="images/jee3.png"/> 
			                        </small>
									<span class="red"> </span>
									<span class="white" id="id-text2">17JEE</span>
								</h1>
								<h4 class="blue" id="id-company-text">&copy; Loy Fu</h4>
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
												
											</h4>

											<div class="space-6"></div>
                                            <div id="login">
											 <form:form method="post" id="fm1" commandName="${commandName}" htmlEscape="true">
												 <form:errors path="*" id="msg" cssClass="errors" element="div" htmlEscape="false" />
                                                
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															
														      <c:choose>
														        <c:when test="${not empty sessionScope.openIdLocalId}">
														          <strong>${sessionScope.openIdLocalId}</strong>
														          <input type="hidden" id="username" name="username" value="${sessionScope.openIdLocalId}" />
														        </c:when>
														        <c:otherwise>
														          <spring:message code="screen.welcome.label.netid.accesskey" var="userNameAccessKey" />
														          <form:input cssClass="required form-control" cssErrorClass="error" id="username" size="25" tabindex="1" accesskey="${userNameAccessKey}" path="username" autocomplete="off" htmlEscape="true" />
														        </c:otherwise>
														      </c:choose>
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															
														      <spring:message code="screen.welcome.label.password.accesskey" var="passwordAccessKey" />
														      <form:password cssClass="required form-control" cssErrorClass="error" id="password" size="25" tabindex="2" path="password"  accesskey="${passwordAccessKey}" htmlEscape="true" autocomplete="off" />
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														  <input type="hidden" name="lt" value="${loginTicket}" />
													      <input type="hidden" name="execution" value="${flowExecutionKey}" />
													      <input type="hidden" name="_eventId" value="submit" />
													      
													       
                                                           <button style="margin-left: 10px" class="width-35 pull-right btn btn-sm btn-primary" name="reset" accesskey="c" value="<spring:message code="screen.welcome.button.clear" />" tabindex="5" type="reset" >
                                                            <i class="ace-icon fa fa-key"></i>
															<span  class="bigger-110"><spring:message code="screen.welcome.button.clear" /></span>
                                                           </button>
                                                           
                                                           
                                                            <button  style="margin-left: 10px" class="width-35 pull-right btn btn-sm btn-primary" name="submit" accesskey="l" value="<spring:message code="screen.welcome.button.login" />" tabindex="4" type="submit" >
													        <i class="ace-icon fa fa-key"></i>
															<span  class="bigger-110"><spring:message code="screen.welcome.button.login" /></span>
													      </button>

													</div>

													<div class="space-4"></div>
												</fieldset>
											</form:form>
                                            </div>
											
										</div><!-- /.widget-main -->

										
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->
    </div>

<jsp:directive.include file="includes/bottom.jsp" />
