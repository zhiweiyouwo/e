	<div  id="${entityName?replace("Entity","")?uncap_first}_container">
	<div class="row">
		<div class="col-xs-12">
			<div id="search_box" class="widget-box  ui-sortable-handle <#if oftenField??>collapsed</#if>">
			<form id ="queryForm" >
				
				</form>
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
	    ccols:[
	    <#list conditionColumns as col> 
		    {i18nKey:"${col.i18nKey}",
			    id:"${col.searchQueryId}",
			    name:"${col.combineFieldName}",
			    <#if col.often?string('true','false')=='true'>often:true,</#if>
			    inputType:"${col.type}"<#if col.type=='select'>,
			     properties:{
			        group:'${col.group}'
			     }</#if>
			}<#if col_has_next> ,</#if>
			</#list>
	    ],
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
	var $container = $('#${entityName?replace("Entity","")?uncap_first}_container');
	var grid_selector  = "#${entityName?replace("Entity","")?uncap_first}_grid-table";
	var pager_selector = "#${entityName?replace("Entity","")?uncap_first}_grid-pager";
	
	loyModel.$container = $container;
	loyModel.grid_selector = grid_selector;
	loyModel.pager_selector = pager_selector;
	var control = new loyControl(loyModel);
	
	$.loy.i18n(['${modelName}/${entityName?replace("Entity","")?uncap_first}'],$.homeGlobal.LANG,$container,{custCallback:function(){
		control.initAll();	
    }});
	
	
});

</script>

