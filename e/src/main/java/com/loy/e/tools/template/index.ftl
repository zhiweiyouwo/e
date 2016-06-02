	<div  id="${entityName?replace("Entity","")?uncap_first}_container">
	<div class="row">
		<div class="col-xs-12">
			<div id="search_box" class="widget-box  ui-sortable-handle <#if oftenField??>collapsed</#if>">
			<form id ="queryForm" >
				<div class="widget-header">
				    <#if oftenField??>
				    <div class="nav-search"  style="padding-top: 5px;  right: 50px">
						<span class="input-icon">
							<input type="text"  i18n="${oftenField.i18nKey}" placeholder ="${oftenField.labelName}" name="${oftenField.combineFieldName}"  id="${oftenField.searchQueryId}" class="nav-search-input"  >
							<i class="ace-icon fa fa-search nav-search-icon" onclick="$('#searchBtn',$('#${entityName?replace("Entity","")?uncap_first}_container')).click()" ></i>
						</span>
					</div>
				    <#else>
				     <a href="#" ><span  onclick="$('#searchBtn',$('#${entityName?replace("Entity","")?uncap_first}_container')).click()" class="ace-icon fa fa-search icon-on-right bigger-110"></span></a>
					<h5 class="widget-title" i18n="search_condition"></h5>
				    </#if>
				    
					<div class="widget-toolbar">
						<a href="#" data-action="collapse">
							<i class="1 ace-icon fa bigger-125 fa-chevron-up"></i>
						</a>
					</div>

				</div>

				<div class="widget-body" style="display: <#if oftenField??>none</#if>;">
					<div class="widget-main">
						<div class="row">
						      <#list conditionColumns as col> 
							   <#if !col.often>${col.conditionHtml}</#if>
							   </#list>
					           <div class="col-xs-12 col-sm-2 " style="float:right">
							    <div id="${entityName?replace("Entity","")?uncap_first}SearchDiv" class="input-group" style="padding-bottom: 2px">
									<span class="input-group-btn" >
										<button id="searchBtn" type="button" class="btn btn-purple btn-sm">
											<span class="ace-icon fa fa-search icon-on-right bigger-110"></span>
											<span i18n="find"></span>
										</button>
									</span>
				              </div>
				              </div>
				         </div>     
			              
					</div>
				</div>
				</form>
			</div>
							    
			
			<table id="${entityName?replace("Entity","")?uncap_first}_grid-table"></table>

			<div id="${entityName?replace("Entity","")?uncap_first}_grid-pager"></div>

		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	
	
	<div id="editModalDiv" class="modal fade" tabindex="-1" data-backdrop="static">
		<div class="modal-dialog" >
				<div class="modal-content">
					<div class="modal-header no-padding">
						<div class="table-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
								<span class="white">&times;</span>
							</button>
							<span i18n="${editI18nKey}"></span>
						</div>
					</div>
					<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">
						<div id="modal-tip" class="red clearfix"></div>
						<div>
							<div class="widget-body">
								<form id="editForm" name="${entityName?replace("Entity","")?uncap_first}Form" class="form-horizontal  col-xs-12">
								     <input type="hidden"  name="id" id="id"/>
								    <#list editColumns as col> 
                                    <div class="row" style="padding-bottom: 2px">
								         <div class="form-group">
					                          <label class="col-sm-3 control-label"  i18n="${col.i18nKey}"></label>
					                          <div class="col-sm-6">
					                             ${col.html}
					                          </div>
					                     </div>
                                    </div>
							       </#list>
								</form>
									
							</div>
						</div>
					</div>
					<div class="modal-footer no-margin-top">
						<div class="text-center">
							
							<button id="submitBtn"  class="btn btn-sm btn-primary">
							  <i class="ace-icon fa fa-floppy-o"></i>
							  <span i18n="save"></span>
							</button>
							
							<button class="btn btn-sm"  data-dismiss="modal">
							  <i class="ace-icon fa fa-share "></i>
							 <span i18n="cancel"></span>
							</button>
						</div>
					</div>
				</div><!-- /.modal-content -->
			
		</div><!-- /.modal-dialog -->
	</div>
	
	
	
	
     <div id="viewModalDiv" class="modal fade" tabindex="-1" data-backdrop="static">
		<div class="modal-dialog" >
				<div class="modal-content">
					<div class="modal-header no-padding">
						<div class="table-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
								<span class="white">&times;</span>
							</button>
							<span i18n="${detailI18nKey}"></span>
						</div>
					</div>
					<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">
						<div id="modal-tip" class="red clearfix"></div>
						<div>
							<div class="widget-body">
								<form class="form-horizontal  col-xs-12">
								    
								    <#list detailColumns as col> 
                                    <div class="row" style="padding-bottom: 2px">
								         <div class="form-group">
					                          <label class="col-sm-3 control-label"  i18n="${col.i18nKey}"></label>
					                          <div class="col-sm-6"  id="view_${col.inputId}"> 
					                          </div>
					                     </div>
                                    </div>
							       </#list>
								</form>
									
							</div>
						</div>
					</div>
					<div class="modal-footer no-margin-top">
						<div class="text-center">
							<button class="btn btn-sm"  data-dismiss="modal">
							  <i class="ace-icon fa fa-share "></i>
							 <span i18n="cancel"></span>
							</button>
						</div>
					</div>
				</div><!-- /.modal-content -->
			
		</div><!-- /.modal-dialog -->
	</div>
	
</div>


<script type="text/javascript">
var scripts = [ null,null ];
$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
    var loyModel = {
	    preI18n:"${preI18n}",
	    modelName:"${entityName?replace("Entity","")?uncap_first}",
	    cols:[
		    <#list modelColumns as col> 
		    {i18nKey:"${col.i18nKey}",
			    <#if col.list?string('true','false')=='true'>list:${col.list?string('true','false')},</#if><#if col.edit?string('true','false')=='true'>edit:${col.edit?string('true','false')},</#if><#if col.detail?string('true','false')=='true'>detail:${col.detail?string('true','false')},</#if><#if col.sortable?string('true','false')=='true'>sortable:${col.sortable?string('true','false')},</#if><#if col.formatter !=''>
			    formatter:"${col.formatter}",</#if>
			    fieldName:"${col.fieldName}"<#if col.edit?string('true','false')=='true'>,
			    properties:{
				     <#list col.properties?keys as key>
				     ${key}:"${col.properties[key]}"<#if key_has_next> ,</#if>
				     </#list>
				}</#if>
			}<#if col_has_next> ,</#if>
			 
			</#list>
       ]
    };
	
	var control = new LoyControl(loyModel);
	var $container = control.$container;
	$.loy.i18n(['${modelName}/${entityName?replace("Entity","")?uncap_first}'],$.homeGlobal.LANG,$container,{custCallback:function(){
		control.init();	
		<#if oftenField??>
		$('#${oftenField.searchQueryId}',$container).bind('keypress',function(event){
	        if(event.keyCode == "13"){
	           $('#searchBtn',$container).click();
	        }
	    });
		</#if>
	    <#list conditionColumns as col> 
		<#if col.type=='select'>
		$.loy.buildSelectOptions('${col.searchQueryId}',$('#${col.searchQueryId}',$container).attr("group"),$.i18n.prop("all"),$container);
		</#if>
		</#list>
	
    }});
	
	
});

</script>

