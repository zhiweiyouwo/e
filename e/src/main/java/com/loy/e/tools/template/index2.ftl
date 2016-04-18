	<div  id="${entityName?replace("Entity","")?uncap_first}_container">
	<div class="row">
		<div class="col-xs-12">
			<div id="search_box" class="widget-box  ui-sortable-handle <#if oftenField??>collapsed</#if>">
				<div class="widget-header">
				    <#if oftenField??>
				    <div class="nav-search"  style="padding-top: 5px;  right: 50px">
						<span class="input-icon">
							<input type="text"  i18n="${oftenField.i18nKey}" placeholder ="${oftenField.labelName}"   id="${oftenField.searchQueryId}" class="nav-search-input"  >
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
			</div>
							    
			
			<table id="${entityName?replace("Entity","")?uncap_first}_grid-table"></table>

			<div id="${entityName?replace("Entity","")?uncap_first}_grid-pager"></div>

		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	
	
	
</div>



<script type="text/javascript">
var scripts = [ null,null ];
$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
    var loyModel = {
    preI18n:"${preI18n}",
    modelName:"${entityName?replace("Entity","")?uncap_first}",
    cols:[
	    <#list modelColumns as col> 
	    {i18nKey:"${col.i18nKey}",list:${col.list?string('true','false')},edit:${col.edit?string('true','false')},detail:${col.detail?string('true','false')},fieldName:"${col.fieldName}",formatter:"${col.formatter}",sortable:${col.sortable?string('true','false')}<#if col.edit?string('true','false')=='true'>,
	     properties:{
		     <#list col.properties?keys as key>
		     ${key}:"${col.properties[key]}"<#if key_has_next> ,</#if>
		     </#list>
		 }</#if>}<#if col_has_next> ,</#if>
		</#list>
    ]};
	var $container = $('#${entityName?replace("Entity","")?uncap_first}_container');
	var grid_selector  = "#${entityName?replace("Entity","")?uncap_first}_grid-table";
	var pager_selector = "#${entityName?replace("Entity","")?uncap_first}_grid-pager";
	loyModel.container = $container;
	var control = new loyControl(loyModel);
	
		
	
	var colNames;
	var colModels;
	var  ${entityName?replace("Entity","")?uncap_first}Grid = null;
	$.loy.i18n(['${modelName}/${entityName?replace("Entity","")?uncap_first}'],$.homeGlobal.LANG,$container,{custCallback:function(){
		
		colNames = control.buildColNames(loyModel);
        colModels = control.buildGridColModels(loyModel);
        
        var buildEditWinStr = control.buildEditWin(loyModel);
        $container.append(buildEditWinStr);
        
        var buildDetailWinStr = control.buildDetailWin(loyModel);
        $container.append(buildDetailWinStr);
        
	    $('.date-picker',$container).datepicker({
			autoclose: true,
			format : 'yyyy-mm-dd',
			language: $.homeGlobal.LANG,
			todayHighlight: true
		}).next().on(ace.click_event, function(){
			$(this).prev().focus();
		});
		
		$('.spinner',$container).ace_spinner({value:0,min:0,max:500,step:1, on_sides: true,
			icon_up:'ace-icon fa fa-plus smaller-75', 
			icon_down:'ace-icon fa fa-minus smaller-75',
			btn_up_class:'btn-success' , 
			btn_down_class:'btn-danger'});
		
		
	    var $validate${entityName?replace("Entity","")}Form = $('#editForm',$container).validate({
	    	onsubmit:false,
	    	rules : {
				/**name : {
					required : true,
				}*/
			}
	    });
        $('#submitBtn').click(function(){
		     if(!$validate${entityName?replace("Entity","")}Form.checkForm()){
				$validate${entityName?replace("Entity","")}Form.defaultShowErrors();
				return;
			 }
			 var url = $(this).attr("url");
	         $.loy.ajax({
					url:url,
					data:$("#editForm",$container).serialize(),
					success:function(data){
						if(data.success){
							$('#editModalDiv',$container).modal("hide");
							${entityName?replace("Entity","")?uncap_first}Grid.trigger("reloadGrid");
						}
					}
			});
      });
    <#assign cchosen = false>
	<#list editColumns as col> 
	 <#if col.type=='search_text'>
	 $('#${col.inputId}').cchosen({allow_single_deselect:true,placeholder_text_single:$.i18n.prop("pleaseChoose")});<#assign cchosen = true>
	</#if>
	<#if col.type=='select'>
	 <#assign cchosen = true>
	</#if>
	</#list>
	<#if cchosen>
	chosenFitLen($container);
	</#if>	
	<#if oftenField??>
	$('#${oftenField.searchQueryId}',$container).bind('keypress',function(event){
        if(event.keyCode == "13"){
           $('#searchBtn',$container).click();
        }
    });
	</#if>
    <#list conditionColumns as col> 
	<#if col.type=='select'>
	 $.loy.buildSelectOptions('${col.searchQueryId}',$('#${col.searchQueryId}',$container).attr("group"),$.i18n.prop("all"));
	</#if>
	</#list>
	
	<#list editColumns as col> 
	<#if col.type=='select'>
	 $.loy.buildSelectOptions('${col.inputId}',$('#${col.inputId}',$container).attr("group"),$.i18n.prop("pleaseChoose"));
	</#if>
	</#list>
	$('input, textarea',$container).placeholder();
	    create${entityName?replace("Entity","")}Grid();
	}});
	
	

  
  function  create${entityName?replace("Entity","")}Grid(){
		${entityName?replace("Entity","")?uncap_first}Grid =jQuery(grid_selector).loyGrid({
			url: '${entityName?replace("Entity","")?uncap_first}/page',
			datatype: "json",
			mtype: 'GET',
			colNames:colNames,
			colModel:colModels,
			pager: pager_selector,
			loadComplete:function(data){
				loadComplete(data);
				var list = data.data?data.data.content:null;
				if(list){
					for(var i=0;i<list.length;i++){
						var editDivId = "jEditButton_"+list[i].id;
						$('#'+editDivId,${entityName?replace("Entity","")?uncap_first}Grid).attr('onclick','').on('click',function(){
							control.edit($(this).closest('tr').attr('id'));
						});
					}
				}	
			}
		}).loyGrid('navGrid','#${entityName?replace("Entity","")?uncap_first}_grid-pager',{"baseUrl":"${entityName?replace("Entity","")?uncap_first}/",
			"addfunc":function(){
				control.add();
			},
			"editfunc":function(rowId){
				control.edit(rowId);
			},
			"viewfunc":function(rowId){
				control.view(rowId);
			},
			view: true
		});
		${entityName?replace("Entity","")?uncap_first}Grid.jqGrid('setFrozenColumns');
		<#if oftenField??>
		searchBoxHideShown($container,${entityName?replace("Entity","")?uncap_first}Grid);
		</#if>
		resizeToFitPage(${entityName?replace("Entity","")?uncap_first}Grid);
	}
	
	$("#searchBtn",$container).click(function(){
	    var postData ={page:0};
	    <#list conditionColumns as col>
	    <#if col.count ==1>
	    <#if col.type ='select'>
	    postData["${col.combineFieldName}Id"] = $("#${col.searchQueryId}",$container).val();	
	    <#else>
	    postData["${col.combineFieldName}"] = $("#${col.searchQueryId}",$container).val();	
	    </#if>
		<#else>
		postData["${col.combineFieldName}Start"] = $("#${col.searchQueryId}_start",$container).val();
		postData["${col.combineFieldName}End"] = $("#${col.searchQueryId}_end",$container).val();
		</#if>
		</#list>
		${entityName?replace("Entity","")?uncap_first}Grid.loyGrid("setGridParam",{"postData":postData}).trigger("reloadGrid"); 
		
	});
	
});

</script>

