	<div  id="${entityName?replace("Entity","")?uncap_first}_container">
	<div class="row">
		<div class="col-xs-12">
			<div class="widget-box  ui-sortable-handle">
				<div class="widget-header"><a href="#" ><span  onclick="$('#${entityName?replace("Entity","")?uncap_first}SearchBtn').click()" class="ace-icon fa fa-search icon-on-right bigger-110"></span></a>
					<h5 class="widget-title" i18n="search_condition"></h5>

					<div class="widget-toolbar">
						<a href="#" data-action="collapse">
							<i class="1 ace-icon fa bigger-125 fa-chevron-up"></i>
						</a>
					</div>

				</div>

				<div class="widget-body" style="display: block;">
					<div class="widget-main">
						<div class="row">
						      <#list conditionColumns as col> 
							   ${col.conditionHtml}
							   </#list>
					           <div class="col-xs-12 col-sm-2 " style="float:right">
							    <div id="${entityName?replace("Entity","")?uncap_first}SearchDiv" class="input-group" style="padding-bottom: 2px">
									<span class="input-group-btn" >
										<button id="${entityName?replace("Entity","")?uncap_first}SearchBtn" type="button" class="btn btn-purple btn-sm">
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
	
	
	<div id="${entityName?replace("Entity","")?uncap_first}ModalDiv" class="modal fade" tabindex="-1" data-backdrop="static">
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
								<form id="${entityName?replace("Entity","")?uncap_first}Form" name="${entityName?replace("Entity","")?uncap_first}Form" class="form-horizontal  col-xs-12">
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
							
							<button id="submit${entityName?replace("Entity","")}Btn"  class="btn btn-sm btn-primary">
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
	
	
	
	
     <div id="${entityName?replace("Entity","")?uncap_first}ViewModalDiv" class="modal fade" tabindex="-1" data-backdrop="static">
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
					                          <div class="col-sm-6"  id="view_${col.combineFieldName}"> 
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
	$container = $('#${entityName?replace("Entity","")?uncap_first}_container');
	var grid_selector  = "#${entityName?replace("Entity","")?uncap_first}_grid-table";
	var pager_selector = "#${entityName?replace("Entity","")?uncap_first}_grid-pager";
	
	$('.date-picker').datepicker({
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
	
	<#list conditionColumns as col> 
	<#if col.type=='select'>
	 $.loy.buildSelectOptions('${col.searchQueryId}',$('#${col.searchQueryId}',$container).attr("group"));
	</#if>
	</#list>
	
	<#list editColumns as col> 
	<#if col.type=='select'>
	 $.loy.buildSelectOptions('${col.inputId}',$('#${col.inputId}',$container).attr("group"));
	</#if>
	</#list>
		
	<#assign cchosen = false>
	<#list editColumns as col> 
	 <#if col.type=='search_text'>
	 $('#${col.inputId}').cchosen({allow_single_deselect:true});<#assign cchosen = true>
	</#if>
	<#if col.type=='select'>
	 <#assign cchosen = true>
	</#if>
	</#list>
	<#if cchosen>
	$(window)
		.off('resize.chosen')
		.on('resize.chosen', function() {
			$('.chosen-select').each(function() {
				 var $this = $(this);
				 $this.next().css({'width': $this.parent().width()});
			})
		}).trigger('resize.chosen');
		$('#${entityName?replace("Entity","")?uncap_first}ModalDiv').on('shown.bs.modal', function () {
		$('.chosen-select',$('#${entityName?replace("Entity","")?uncap_first}ModalDiv')).each(function() {
			 var $this = $(this);
			 $this.next().css({'width': $this.parent().width()});
		});
	});
	</#if>	
	var colNames;
	var  ${entityName?replace("Entity","")?uncap_first}Grid = null;
	$.loy.i18n(['${modelName}/${entityName?replace("Entity","")?uncap_first}'],$.homeGlobal.LANG,$container,{custCallback:function(){
		$('input, textarea',$container).placeholder();
		colNames =[' ',
		    <#list listColumns as col> 
	   		$.i18n.prop("${col.i18nKey}")<#if col_has_next> ,</#if>
	   		</#list> 
        ];
	    create${entityName?replace("Entity","")}Grid();
	}});
	
	var $validate${entityName?replace("Entity","")}Form = $('#${entityName?replace("Entity","")?uncap_first}Form').validate({
    	onsubmit:false,
    	rules : {
			/**name : {
				required : true,
			}*/
		}
    });
	function clear${entityName?replace("Entity","")}Form(){
		 <#list editColumns as col> 
		 $('#${col.inputId}',$container).val('');
		 <#if col.type=='search_text' || col.type=='select'>
		 $('#${col.inputId}',$container).trigger("chosen:updated");
		 </#if>
		 </#list>
	}
	function edit (id){
		clear${entityName?replace("Entity","")}Form();
		$('#submit${entityName?replace("Entity","")}Btn').attr("url","${entityName?replace("Entity","")?uncap_first}/update");
		$('#${entityName?replace("Entity","")?uncap_first}ModalDiv').modal("show");
		$.loy.ajax({
			url:'${entityName?replace("Entity","")?uncap_first}/get',
			data:{id:id},
			success:function(data){
				var result = data.data;
				$('#id').val(result.id?result.id:'');
				<#list editColumns as col> 
				<#if col.type=='search_text' || col.type=='select'>
				<#if col.type=='search_text'>
				 var ${col.fieldName}IdValue = result.${col.fieldName}.id?result.${col.fieldName}.id:'';
				 if(${col.fieldName}IdValue && ${col.fieldName}IdValue !=''){
					 var name = result.${col.fieldName}.name?result.${col.fieldName}.name:'';
					 $('#${col.inputId}',$container).html('<option value=""></option> <option selected value="'+${col.fieldName}IdValue+'">'+name+'</option>');
					 $('#${col.inputId}',$container).trigger("chosen:updated");
				 }
				 </#if>
				 <#if col.type=='select'>
				 var ${col.fieldName}IdValue = result.${col.fieldName}.id?result.${col.fieldName}.id:'';
				 $('#${col.inputId}',$container).val(${col.fieldName}IdValue);
				 $('#${col.inputId}',$container).trigger("chosen:updated");
				 </#if>
				<#else>
					 <#if col.formatter =='date'>
				 $('#${col.inputId}',$container).val(result.${col.fieldName}?result.${col.fieldName}.substring(0,10):'');
				<#else>
				 $('#${col.inputId}',$container).val(result.${col.fieldName}?result.${col.fieldName}:'');
				</#if>
				</#if>
				</#list>
			}
	   });
	}
	function view (id){
		$('#${entityName?replace("Entity","")?uncap_first}ViewModalDiv').modal("show");
		$.loy.ajax({
			url:'${entityName?replace("Entity","")?uncap_first}/get',
			data:{id:id},
			success:function(data){
				var result = data.data;
				<#list detailColumns as col> 
				<#if col.formatter =='date'>
				   $('#view_${col.combineFieldName}',$container).html(result.${col.fieldName}?result.${col.fieldName}.substring(0,10):'');
				<#else>
				  $('#view_${col.combineFieldName}',$container).html(result.${col.fieldName}?result.${col.fieldName}:'');
				</#if>
				</#list>
			}
	});
  }
  function add(){
		clear${entityName?replace("Entity","")}Form();
		$('#submit${entityName?replace("Entity","")}Btn').attr("url","${entityName?replace("Entity","")?uncap_first}/save");
		$('#${entityName?replace("Entity","")?uncap_first}ModalDiv').modal("show");
  }
  
  function  create${entityName?replace("Entity","")}Grid(){
		${entityName?replace("Entity","")?uncap_first}Grid =jQuery(grid_selector).loyGrid({
			url: '${entityName?replace("Entity","")?uncap_first}/page',
			datatype: "json",
			mtype: 'GET',
			colNames:colNames,
			colModel: [
			 {name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false ,
				formatter:'actions', 
				formatoptions:getFormatoptions('${entityName?replace("Entity","")?uncap_first}/')
			 },
	   		 <#list listColumns as col> 
	   		{ name: '${col.fieldName}', index: '${col.fieldName}', <#if col.formatter !=''>formatter:'${col.formatter}' ,</#if> width: 100, align: "left", editable: false<#if col.fieldName?contains(".")?string == 'true'>, formatter:function(cellvalue, options, rowObject){
	   			if(rowObject.${col.fieldName?substring(0,col.fieldName?index_of("."))}){
	   				return rowObject.${col.fieldName};
	   			}
	   			return "";
	   		}</#if>}<#if col_has_next> ,</#if>
	   		 </#list> 
	   		],
			pager: pager_selector,
			//width:$(".page-content").width(),
			height: '310',
			loadComplete:function(data){
				loadComplete(data);
				var list = data.data?data.data.content:null;
				if(list){
					for(var i=0;i<list.length;i++){
						var editDivId = "jEditButton_"+list[i].id;
						$('#'+editDivId,${entityName?replace("Entity","")?uncap_first}Grid).attr('onclick','').on('click',function(){
							edit($(this).closest('tr').attr('id'));
						});
					}
				}	
			}
		}).loyGrid('navGrid','#${entityName?replace("Entity","")?uncap_first}_grid-pager',{"baseUrl":"${entityName?replace("Entity","")?uncap_first}/",
			"addfunc":function(){
				add();
			},
			"editfunc":function(rowId){
				edit(rowId);
			},
			"viewfunc":function(rowId){
				view(rowId);
			},
			view: true
		});
		${entityName?replace("Entity","")?uncap_first}Grid.jqGrid('setFrozenColumns');
		resizeToFitPage(${entityName?replace("Entity","")?uncap_first}Grid);
	}
	
	$("#${entityName?replace("Entity","")?uncap_first}SearchBtn").click(function(){
	    var postData ={page:0};
	    <#list conditionColumns as col>
	    <#if col.count ==1>
	    <#if col.type ='select'>
	      postData["${col.combineFieldName}Id"] = $("#${col.searchQueryId}").val();	
	    <#else>
	      postData["${col.combineFieldName}"] = $("#${col.searchQueryId}").val();	
	    </#if>
		<#else>
		postData["${col.combineFieldName}Start"] = $("#${col.searchQueryId}_start").val();
		postData["${col.combineFieldName}End"] = $("#${col.searchQueryId}_end").val();
		</#if>
		</#list>
		${entityName?replace("Entity","")?uncap_first}Grid.loyGrid("setGridParam",{"postData":postData}).trigger("reloadGrid"); 
		
	});
	
	$('#submit${entityName?replace("Entity","")}Btn').click(function(){
	     if(!$validate${entityName?replace("Entity","")}Form.checkForm()){
			$validate${entityName?replace("Entity","")}Form.defaultShowErrors();
			return;
		 }
		 var url = $(this).attr("url");
         $.loy.ajax({
				url:url,
				data:$("#${entityName?replace("Entity","")?uncap_first}Form").serialize(),
				success:function(data){
					if(data.success){
						$('#${entityName?replace("Entity","")?uncap_first}ModalDiv').modal("hide");
						${entityName?replace("Entity","")?uncap_first}Grid.trigger("reloadGrid");
					}
				}
		});
    });
	
});

</script>

