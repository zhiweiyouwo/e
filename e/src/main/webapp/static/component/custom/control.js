
function   combineFieldName(fieldName){
	var strs=fieldName.split("\.");
	var temp = "";
    for (i=0;i<strs.length ;i++ )   {   
       if(i==0){
    	   temp = strs[0];
       }else{
    	   temp = temp+strs[i].substring(0,1).toUpperCase( ) +  strs[i].substring(1);  
       }   
    }   
    return temp;
}

function buildId(fieldName){
	var id = fieldName.replace("\.","_");
    return id;
}
function chosenFitLen($container){
	 $(window)
		.off('resize.chosen')
		.on('resize.chosen', function() {
			$('.chosen-select').each(function() {
				 var $this = $(this);
				 $this.next().css({'width': $this.parent().width()});
			})
		}).trigger('resize.chosen');
		$('#editModalDiv',$container).on('shown.bs.modal', function () {
		$('.chosen-select',$('#editModalDiv',$container)).each(function() {
			 var $this = $(this);
			 $this.next().css({'width': $this.parent().width()});
		});
	 });
}

loyControl = function(loyModel){
	var loyModel = loyModel;
	var $container = loyModel.$container?loyModel.$container:$('#'+loyModel.modelName+'_container');
	this.$container = $container;
	var grid_selector = loyModel.grid_selector?loyModel.grid_selector: loyModel.modelName+'_'+'grid-table';
	var pager_selector = loyModel.pager_selector?loyModel.pager_selector:loyModel.modelName+'_'+'grid-pager';
	
	var editFormId = loyModel.editForm?loyModel.editForm:"editForm";
	var queryFormId = loyModel.queryForm?loyModel.queryForm:"queryForm";
	var submitBtnId = loyModel.submitBtnId?loyModel.submitBtnId:"submitBtn";
	var editModalDivId = loyModel.editModalDivId?loyModel.editModalDivId:"editModalDiv";
	var searchBtnId = loyModel.searchBtnId?loyModel.searchBtnId:"searchBtn";
	var viewModalDivId = loyModel.viewModalDivId?loyModel.viewModalDivId:"viewModalDiv";
	var baseUrl = baseUrl?baseUrl:loyModel;
	
	var colNames = null;
	var colModels = null;
	this.grid = null;
	this.$validateForm = null;
	
	function buildQueryInputHtml(inputType,id,name,i18n,properties){
		var i18nValue =  $.i18n.prop(i18n);
		var inputTypes = {
			text:function(id,name,i18n,properties){
				var buffer = [];
				buffer.push('<div class="col-xs-12 col-sm-2 ">');
				buffer.push('<input type="text"  i18n="'+i18n+'" placeholder ="'+i18nValue+'" class="form-control  search-query" ');
				buffer.push('id="'+id+'"   name="'+name+'" />');
				buffer.push("</div>");
				return buffer.join('');
			},
			select:function(id,name,i18n,properties){
				var buffer = [];
				buffer.push('<div class="col-xs-12 col-sm-2 ">');
				buffer.push('<select group="'+properties.group+'"   i18n="'+i18n+'" placeholder ="'+i18nValue+'" class="form-control search-query"');
				buffer.push('id="'+id+'"   name="'+name+'Id'+'"  >');
				buffer.push(' <option value="">ALL</option> ');
				buffer.push('</select>');
				buffer.push('</div>');
				return buffer.join('');
			},
			
			date:function(id,name,i18n,properties){
				var buffer = [];
				var i18nStartKey = i18n+"Start";
				var i18nEndKey = i18n+"End";
				buffer.push('<div class="input-group col-xs-12 col-sm-2"  style="float:left;padding-left: 15px;padding-right: 15px">');
				buffer.push('<input type="text"  i18n="'+i18nStartKey+'" placeholder ="'+$.i18n.prop(i18nStartKey)+'" class="form-control  date-picker" ');
				buffer.push('id="'+id+'_start" name="'+name+'Start"/>');
				buffer.push('<span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>');
				buffer.push('</div>');
				
				
				buffer.push('<div class="input-group col-xs-12 col-sm-2"  style="float:left;padding-left: 15px;padding-right: 15px">');
				buffer.push('<input type="text"  i18n="'+i18nEndKey+'" placeholder ="'+$.i18n.prop(i18nEndKey)+'" class="form-control  date-picker" ');
				buffer.push('id="'+id+'_end" name="'+name+'End"/>');
				buffer.push('<span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span>');
				buffer.push('</div>');
				return buffer.join('');
			},
		};
		return inputTypes[inputType](id,name,i18n,properties);
	};
	function buildInputHtml(inputType,id,name,i18n,properties){
		var i18nValue =  $.i18n.prop(i18n);
		var inputTypes = {
			text:function(id,name,i18n,properties){
				var buffer = [];
				buffer.push('<div class="form-group">');
				  buffer.push('<label class="col-sm-3 control-label" i18n="'+i18n+'">'+i18nValue+'</label>');
				  buffer.push('<div class="col-sm-6">');
					buffer.push('<input type="text"  class="form-control" id="'+id+'" name="'+name+'" />');
	              buffer.push('</div>');
				buffer.push('</div>');
				return buffer.join('')
			},
			text_area:function(id,name,i18nValue,properties){
				var buffer = [];
				buffer.push('<div class="form-group">');
				  buffer.push('<label class="col-sm-3 control-label"  i18n="'+i18n+'">'+i18nValue+'</label>');
				  buffer.push('<div class="col-sm-6">');
					buffer.push(' <textarea type="text"   class="form-control" id="'+id+'" name="'+name+'" ></textarea>');
	              buffer.push('</div>');
				buffer.push('</div>');
				return buffer.join('');
			},
			date:function(id,name,i18nValue,properties){
				var buffer = [];
				buffer.push('<div class="form-group">');
				  buffer.push('<label class="col-sm-3 control-label"  i18n="'+i18n+'">'+i18nValue+'</label>');
				  buffer.push('<div class="col-sm-6">');
				  buffer.push('<div class="input-group"><input type="text"  class="form-control  date-picker" id="'+id+'" name="'+name+'" /><span class="input-group-addon"><i class="fa fa-calendar bigger-110"></i></span></div>');
	              buffer.push('</div>');
				buffer.push('</div>');
				return buffer.join('');
				 
			},
			select:function(id,name,i18nValue,properties){
				var buffer = [];
				buffer.push('<div class="form-group">');
				  buffer.push('<label class="col-sm-3 control-label"  i18n="'+i18n+'">'+i18nValue+'</label>');
				  buffer.push('<div class="col-sm-6">');
				  buffer.push('  <select class="form-control chosen-select" group="'+properties.group+'" id="'+id+'" name="'+name+'"> <option value=""></option> </select>');
	              buffer.push('</div>');
				buffer.push('</div>');
				return buffer.join('');
				 
			},
			search_text:function(id,name,i18nValue,properties){
				var buffer = [];
				buffer.push('<div class="form-group">');
				  buffer.push('<label class="col-sm-3 control-label"  i18n="'+i18n+'">'+i18nValue+'</label>');
				  buffer.push('<div class="col-sm-6">');
				  buffer.push(' <select class="form-control chosen-select" id="'+id+'" name="'+name+'" label="'+properties.name+'" tableName="'+properties.tableName+'"> <option value=""></option> </select>');
	              buffer.push('</div>');
				buffer.push('</div>');
				return buffer.join('');
				 
			},
			integer:function(id,name,i18nValue,properties){
				var buffer = [];
				buffer.push('<div class="form-group">');
				  buffer.push('<label class="col-sm-3 control-label"  i18n="'+i18n+'">'+i18nValue+'</label>');
				  buffer.push('<div class="col-sm-6">');
				  buffer.push(' <input type="text"  class="form-control spinner" id="'+id+'" name="'+name+'" />');
	              buffer.push('</div>');
				buffer.push('</div>');
				return buffer.join('');
				 
			},
			float:function(id,name,i18nValue,properties){
				var buffer = [];
				buffer.push('<div class="form-group">');
				  buffer.push('<label class="col-sm-3 control-label"  i18n="'+i18n+'">'+i18nValue+'</label>');
				  buffer.push('<div class="col-sm-6">');
				  buffer.push(' <input type="text"  class="form-control spinner" id="'+id+'" name="'+name+'" />');
	              buffer.push('</div>');
				buffer.push('</div>');
				return buffer.join('');
				 
			}
		};
		return inputTypes[inputType](id,name,i18nValue,properties);
		
	};
	this.loadCompleteCallback = function(data){
		var self = this;
		loadComplete(data);
		var list = data.data?data.data.content:null;
		if(list){
			for(var i=0;i<list.length;i++){
				var editDivId = "jEditButton_"+list[i].id;
				$('#'+editDivId,self.grid).attr('onclick','').on('click',function(){
					self.edit($(this).closest('tr').attr('id'));
				});
			}
		}	
	};
	this.init = function(){
		this.buildColNames();
		this.buildGridColModels();
		//this.buildEditWin();
		//this.buildDetailWin();
		this.initDate();
		this.initInput();
		$('input, textarea',$container).placeholder();
		this.bindSearchBtn();
		this.buildValidateForm();
		this.bindSubmit();
		this.createGrid();
	};
	this.initAll = function(){
		this.buildColNames();
		this.buildGridColModels();
		this.buildQueryForm();
		this.buildEditWin();
		this.buildDetailWin();
		this.initDate();
		this.initInput();
		$('input, textarea',$container).placeholder();
		
		this.bindSearchBtn();
		this.buildValidateForm();
		this.bindSubmit();
		this.createGrid();
	};
	
	this.bindSubmit = function(){
		var self = this;
		$('#'+submitBtnId).click(function(){
		     if(!self.$validateForm.checkForm()){
		    	 self.$validateForm.defaultShowErrors();
				return;
			 }
			 var url = $(this).attr("url");
	         $.loy.ajax({
					url:url,
					data:$("#"+editFormId,$container).serialize(),
					success:function(data){
						if(data.success){
							$('#'+editModalDivId,$container).modal("hide");
							self.grid.trigger("reloadGrid");
						}
					}
			});
     });
	};
	this.bindSearchBtn = function(){
		var self = this;
		$("#"+searchBtnId,$container).click(function(){
		    var postData = $("#"+queryFormId,$container).serialize();
		    postData.page=0;
		    self.grid.loyGrid("setGridParam",{"postData":postData}).trigger("reloadGrid"); 
			
		});
	};
	this.initDate = function(){
		for(var i=0;i<loyModel.cols.length;i++){
			var col = loyModel.cols[i];
			if(col.edit){
				if(col.properties.input_type=='date'){
					 $('.date-picker',$container).datepicker({
							autoclose: true,
							format : 'yyyy-mm-dd',
							language: $.homeGlobal.LANG,
							todayHighlight: true
						}).next().on(ace.click_event, function(){
							$(this).prev().focus();
						});
					 break;
				}
			}
	    }
	};
	this.initInput = function(){
		var hasChosen = false;
		for(var i=0;i<loyModel.cols.length;i++){
			var col = loyModel.cols[i];
			if(col.edit){
				var id = buildId(col.fieldName);
				if(col.properties.input_type=='integer' || col.properties.input_type=='float'){
					var min=col.properties.min;
					var max = col.properties.max;
					var step = col.step;
					$('#'+id,$container).ace_spinner({value:0,min:min?min:0,max:max?max:99999999,step:step?step:1, on_sides: true,
						icon_up:'ace-icon fa fa-plus smaller-75', 
						icon_down:'ace-icon fa fa-minus smaller-75',
						btn_up_class:'btn-success' , 
						btn_down_class:'btn-danger'});
				}else if(col.properties.input_type=='select'){
					 $.loy.buildSelectOptions(id,$('#'+id,$container).attr("group"),$.i18n.prop("pleaseChoose"),$container);
					 hasChosen = true;
				}else if(col.properties.input_type=='search_text'){
					 $('#'+id,$container).cchosen({allow_single_deselect:true,placeholder_text_single:$.i18n.prop("pleaseChoose")});
					 hasChosen = true;
				}
			}
	    }
		if(hasChosen){
			chosenFitLen($container);
		}
	};
	this.initDate = function(){
		 $('.date-picker',$container).datepicker({
				autoclose: true,
				format : 'yyyy-mm-dd',
				language: $.homeGlobal.LANG,
				todayHighlight: true
		 }).next().on(ace.click_event, function(){
				$(this).prev().focus();
		 });
	};
	
	this.buildValidateForm = function(){
		var rules = {};
		for(var i=0;i<loyModel.cols.length;i++){
			var col = loyModel.cols[i];
			if(col.edit){
				if(col.rule){
					for(var name in col.rule){       
						rules[name] = col.rule[name]; 
					 }  
				}
			}
	    }
		this.$validateForm = $('#'+editFormId,$container).validate({
	    	onsubmit:false,
	    	ignore: ".hidden",
	    	rules : rules,
	    	errorPlacement: function(error, element) { 
	    		if((element.is("select") &&  element.is('.chosen-select'))){
	    			 error.appendTo ( element.parent() ); 
	    		}else if (element.is('.date-picker')){
	    			 error.appendTo ( element.parent().parent() ); 
	    		}else{
	    			 error.insertAfter(element); 
	    		}
	    	} 
	    });
		return this.$validateForm;
	};
	this.buildColNames  = function (){
		colNames =[' '];
		for(var i=0;i<loyModel.cols.length;i++){
			var col = loyModel.cols[i];
			if(col.list){
				colNames.push($.i18n.prop(col.i18nKey));
			}
	    }
		
		return colNames;
	};
	
	this.buildGridColModels = function (){
		colModels= [
					 {name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false ,
						formatter:'actions', 
						formatoptions:getFormatoptions(loyModel.modelName+'/')
					 }];
		for(var i=0;i<loyModel.cols.length;i++){
			var col = loyModel.cols[i];
			if(col.list){
				var colModel = { name: col.fieldName, index: col.fieldName,sortable:col.sortable?col.sortable:false,  width: col.w?col.w:100, align: "left", editable: false} 
				var strs=colModel.name.split("\.");
				if(strs.length>1){
					colModel.formatter=function(cellvalue, options, rowObject){
						var strs=options.colModel.name.split("\.");
						var v = rowObject;
						for(var j=0;j<strs.length;j++){
							var key = strs[j];
							if(v){
								v = v[key];
							}
						}
						if(v){
							return v;
						}
						return "";
					}
				}else{
					if(col.formatter && col.formatter !=""){
						colModel.formatter = col.formatter;
					}
				}
				colModels.push(colModel);	
			}
	    }	
		return colModels;
	};
	this.createGrid = function(){
		var self = this;
		this.grid =jQuery('#'+grid_selector).loyGrid({
			url: loyModel.modelName+'/page',
			datatype: "json",
			mtype: 'GET',
			colNames:colNames,
			colModel:colModels,
			pager: pager_selector,
			loadComplete:function(data){
				self.loadCompleteCallback(data);
			}
		}).loyGrid('navGrid','#'+pager_selector,{"baseUrl":loyModel.modelName+"/",
			"addfunc":function(){
				self.add();
			},
			"editfunc":function(rowId){
				self.edit(rowId);
			},
			"viewfunc":function(rowId){
				self.view(rowId);
			},
			view: true
		});
		self.grid.jqGrid('setFrozenColumns');
		searchBoxHideShown($container,self.grid);
		resizeToFitPage(self.grid);
		return self.grid;
	};
	
	this.clearForm =function (){
		if(this.$validateForm){
			this.$validateForm.resetForm();
		}
		for(var i=0;i<loyModel.cols.length;i++){
			var col = loyModel.cols[i];
			if(col.edit){
				var id = buildId(col.fieldName);
				$('#'+id,$container).val('');
				if(col.properties.input_type=="search_text" ||
						col.properties.input_type=="select"){
					$('#'+id,$container).trigger("chosen:updated");
				}
			}
	    }
	};
	
	this.fillForm = function (result){
		for(var i=0;i<loyModel.cols.length;i++){
			var col = loyModel.cols[i];
			if(col.edit){
				    var id = buildId(col.fieldName);
					var strs=col.fieldName.split("\.");
					var v = result;
					for(var j=0;j<strs.length;j++){
						var key = strs[j];
						if(v){
							v = v[key];
						}
					}
					if(col.properties.input_type=="date"){
						v = v?v.substring(0,10):"";
					}
					$('#'+id,this.$container).val(v?v:"");
					if(col.properties.input_type=="select" || col.properties.input_type=="search_text"){
						if(col.properties.input_type=="search_text"){
							if(v && v !=''){
								 var name = result[strs[0]].name?result[strs[0]].name:'';
								 $('#'+id,$container).html('<option value=""></option> <option selected value="'+v+'">'+name+'</option>');
							}
						}
						$('#'+id,$container).trigger("chosen:updated");
					}
			}
	    }
	};
	
	this.edit = function (id){
		var self = this;
		self.clearForm();
		$('#'+submitBtnId,$container).attr("url",loyModel.modelName+"/update");
		$('#'+editModalDivId,$container).modal("show");
		$.loy.ajax({
			url:loyModel.modelName+'/get',
			data:{id:id},
			success:function(data){
				 var result = data.data;
				 $('#id',$container).val(result.id?result.id:'');
				 self.fillForm(result);
			}
	   });
	};
	
	this.buildDetailWin =function (){
		var temp = [];
		temp.push('<div id="'+viewModalDivId+'" class="modal fade" tabindex="-1" data-backdrop="static">');
		  temp.push('<div class="modal-dialog" >');
			temp.push('<div class="modal-content">');
			  temp.push('<div class="modal-header no-padding">');
				temp.push('<div class="table-header">');
				  temp.push('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">');
				  temp.push('<span class="white">&times;</span>');
				  temp.push('</button>');
				  var detailTitle = $.i18n.prop(loyModel.preI18n+'.detail'+loyModel.modelName.substring(0,1).toUpperCase( ) +  loyModel.modelName.substring(1)); 
				  temp.push('<span >'+detailTitle+'</span>');
				temp.push('</div>');
			  temp.push('</div>');
			  temp.push('<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">');
				temp.push('<div id="modal-tip" class="red clearfix"></div>');
				  temp.push('<div>');
					temp.push('<div class="widget-body">');
					  temp.push('<form class="form-horizontal  col-xs-12">');
						for(var i=0;i<loyModel.cols.length;i++){
							var col = loyModel.cols[i];
							if(col.detail){
							    var id = buildId(col.fieldName);
							    temp.push('<div class="row" style="padding-bottom: 2px">');
							    temp.push('<div class="form-group">');
							    var i18nValue = $.i18n.prop(col.i18nKey);
							    temp.push('<label class="col-sm-3 control-label"  i18n="'+col.i18nKey+'">'+i18nValue+'</label>');
							    temp.push('<div class="col-sm-6"  id="view_'+id+'">'); 
							    temp.push('</div>');
							    temp.push('</div>');
							    temp.push('</div>');
							}				
					    }
					  temp.push('</form>');
					temp.push('</div>');
				  temp.push('</div>');
				temp.push('</div>');
				temp.push('<div class="modal-footer no-margin-top">');
				  temp.push('<div class="text-center">');
					temp.push('<button class="btn btn-sm"  data-dismiss="modal">');
					temp.push('<i class="ace-icon fa fa-share "></i>');
					temp.push('<span i18n="cancel">'+$.i18n.prop("cancel")+'</span>');
					temp.push('</button>');
                  temp.push('</div>');
                temp.push('</div>');
              temp.push('</div>');
            temp.push('</div>');
          temp.push('</div>');
          var str = temp.join(" ");
          $container.append(str);
		  return str;
	};
	
	
	
	this.buildEditWin = function(){
		
		var temp = [];
		temp.push('<div id="'+editModalDivId+'" class="modal fade" tabindex="-1" data-backdrop="static">');
		  temp.push('<div class="modal-dialog" >');
			temp.push('<div class="modal-content">');
			  temp.push('<div class="modal-header no-padding">');
				temp.push('<div class="table-header">');
					temp.push('<button type="button" class="close" data-dismiss="modal" aria-hidden="true">');
					temp.push('<span class="white">&times;</span>');
					temp.push('</button>');
					var editTitle = $.i18n.prop(loyModel.preI18n+'.edit'+loyModel.modelName.substring(0,1).toUpperCase( ) +  loyModel.modelName.substring(1)); 
					temp.push('<span >'+editTitle+'</span>');
				temp.push('</div>');
			  temp.push('</div>');
			  temp.push('<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">');
				temp.push('<div id="modal-tip" class="red clearfix"></div>');
				  temp.push('<div>');
				  temp.push('<div class="widget-body">');
					temp.push('<form id="editForm" name="editForm" class="form-horizontal  col-xs-12">');
					  temp.push('<input type="hidden"  name="id" id="id"/>');
					  
					  for(var i=0;i<loyModel.cols.length;i++){
						var col = loyModel.cols[i];
						if(col.edit){
						    var id = buildId(col.fieldName);
						    var name = col.fieldName;
						    var inputHtml = buildInputHtml(col.properties.input_type,id,name,col.i18nKey,col.properties)
						    temp.push(inputHtml);
						}
					  }
					  
					temp.push('</form>');
	               temp.push('</div>');
				 temp.push('</div>');
			   temp.push('</div>');
			   temp.push('<div class="modal-footer no-margin-top">');
				 temp.push('<div class="text-center">');
				   temp.push('<button id="submitBtn"  class="btn btn-sm btn-primary">');
				   temp.push('<i class="ace-icon fa fa-floppy-o"></i>');
				   temp.push('<span i18n="save">'+$.i18n.prop("save")+'</span>');
				   temp.push('</button>');
				   temp.push('<button class="btn btn-sm"  data-dismiss="modal"');
				   temp.push('<i class="ace-icon fa fa-share"></i>');
				   temp.push('<span i18n="cancel">'+$.i18n.prop("cancel")+'</span>');
				   temp.push('</button>');
				 temp.push('</div>');
			   temp.push('</div>');
			 temp.push('</div>');
           temp.push('</div>');
		temp.push('</div>');					
		var str = temp.join(" ");
        $container.append(str);						
		return str;	
	};
    this.buildQueryForm = function(){
    	var self = this;
		var temp = [];
		var often = false;
		var oftenQueryCol = null;
		temp.push('<div class="row">');
		  temp.push('<div class="col-xs-12">');
			temp.push('<div id="search_box" class="widget-box  ui-sortable-handle collapsed">');
			  temp.push('<form id ="'+queryFormId+'" >');
				temp.push('<div class="widget-header">');
					 if(loyModel.ccols){
						 for(var i=0;i<loyModel.ccols.length;i++){
							 var ccol = loyModel.ccols[i];
							 if(ccol.often){
								 often = true;
								 oftenQueryCol = ccol;
								 temp.push('<div class="nav-search"  style="padding-top: 5px;  right: 50px">');
								    temp.push('<span class="input-icon">');
								    temp.push('<input type="text"  i18n="'+ccol.i18nKey+'" placeholder ="'+$.i18n.prop(ccol.i18nKey)+'" name="'+ccol.name+'"  id="'+ccol.id+'" class="nav-search-input"  >');
								    temp.push('<i class="ace-icon fa fa-search nav-search-icon" onclick="$(\'#'+searchBtnId+'\',$(\'#'+loyModel.modelName+'_container\')).click()"></i>');
								    temp.push('</span>');
								  temp.push('</div>');
								  break;
							 } 
					     }
					 }
					 if(!often){
						 temp.push('<a href="#" ><span  onclick="$(\'#'+searchBtnId+'\',$(\'#'+loyModel.modelName+'_container\')).click()" class="ace-icon fa fa-search icon-on-right bigger-110"></span></a>');
						 temp.push('<h5 class="widget-title" i18n="search_condition">'+$.i18n.prop("search_condition")+'</h5>');
					 }
			        temp.push('<div class="widget-toolbar">');
					  temp.push('<a href="#" data-action="collapse">');
					  temp.push('<i class="1 ace-icon fa bigger-125 fa-chevron-up"></i>');
					  temp.push('</a>');
					temp.push('</div>');
		        temp.push('</div>');
		        var display = often?"none":"";
		        temp.push('<div class="widget-body" style="display: '+display+';">');
		          temp.push('<div class="widget-main">');
		        	temp.push('<div class="row">');
		        	 
		        	if(loyModel.ccols){
						 for(var i=0;i<loyModel.ccols.length;i++){
							 var ccol = loyModel.ccols[i];
							 if(!ccol.often){
								 var queryInputStr = buildQueryInputHtml(ccol.inputType,ccol.id,ccol.name,ccol.i18nKey,ccol.properties);
								 temp.push(queryInputStr);
							 } 
					     }
					 }
						  
					  temp.push('<div class="col-xs-12 col-sm-2 " style="float:right">');
						temp.push('<div id="'+loyModel.modelName+'SearchDiv" class="input-group" style="padding-bottom: 2px">');
						  temp.push('<span class="input-group-btn" >');
						  temp.push('<button id="'+searchBtnId+'" type="button" class="btn btn-purple btn-sm">');
						  temp.push('<span class="ace-icon fa fa-search icon-on-right bigger-110"></span>');
						  temp.push('<span i18n="find">'+$.i18n.prop("find")+'</span>');
						  temp.push('</button>');
						  temp.push('</span>');
						temp.push('</div>');
					  temp.push('</div>');
					temp.push('</div> ');     
				  temp.push('</div>');
				temp.push('</div>');
			  temp.push('</form');
			temp.push('</div> ');   
	  temp.push('</div>');
	  temp.push('<table id="'+grid_selector+'"></table>');
	  temp.push('<div id="'+pager_selector+'"></div>');
	temp.push('</div>');
		
		$container.html(temp.join(''));
		
		$('#'+oftenQueryCol.id,$container).bind('keypress',function(event){
	        if(event.keyCode == "13"){
	           $('#'+searchBtnId,$container).click();
	        }
	    });
		if(loyModel.ccols){
			 for(var i=0;i<loyModel.ccols.length;i++){
				 var ccol = loyModel.ccols[i];
				 if(ccol.inputType =='select'){
					 $.loy.buildSelectOptions(ccol.id,
							 ccol.properties.group,
							 $.i18n.prop("all"),$container);
				 }
			 }
		}
    };
	
	this.detail =function (result){
		for(var i=0;i<loyModel.cols.length;i++){
			var col = loyModel.cols[i];
			var v = result;
			if(col.detail){
			    var id = buildId(col.fieldName);
				var strs=col.fieldName.split("\.");
				for(var j=0;j<strs.length;j++){
					var key = strs[j];
					if(v){
						v = v[key];
					}
				}
				$('#view_'+id,$container).html(v?v:'');
			}
	    }
	};
	
	
   this.view = function (id){
		$('#'+viewModalDivId,$container).modal("show");
		var detail = this.detail;
		$.loy.ajax({
			url:loyModel.modelName+'/get',
			data:{id:id},
			success:function(data){
				var result = data.data;
				detail(result);
			}
	});
	
  };
  this.add = function add(){
		this.clearForm();
		$('#id',$('#'+editModalDivId,$container)).val("");
		$('#'+submitBtnId,$container).attr("url",loyModel.modelName+"/save");
		$('#'+editModalDivId,$container).modal("show");
  };
  this.submit = function(){
	  var self = this;
	  var url = $(this).attr("url");
      $.loy.ajax({
				url:url,
				data:$("#"+editFormId,$container).serialize(),
				success:function(data){
					if(data.success){
						$('#'+editModalDivId,$container).modal("hide");
						self.grid.trigger("reloadGrid");
					}
				}
		});
 };
 }

