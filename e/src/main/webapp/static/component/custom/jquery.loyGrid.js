
//function removeHorizontalScrollBar($jqGrid) {
//    $("div.ui-state-default.ui-jqgrid-hdiv.ui-corner-top",$jqGrid).css("width", parseInt($jqGrid.closest(".ui-jqgrid-bdiv").css("width")) + 1 + "px");
//    $jqGrid.closest(".ui-jqgrid-bdiv").css("width", parseInt($jqGrid.closest(".ui-jqgrid-bdiv").css("width")) + 1 + "px");
//}
function pickDate(cellvalue, options, cell) {
	setTimeout(function() {
		$(cell).find('input[type=text]').datepicker({
			format : 'yyyy-mm-dd',
			autoclose : true,
		    language: 'zh-CN'
		});
	}, 0);
}

function  getTableHtml(tableId,removeCols){
	if(!removeCols){
		removeCols = [0,1,2];
	}
	var $table = $('#'+tableId);
	var headerHtml = ["<tr>"];
	var $htable = $('.ui-jqgrid-htable',$("#gbox_"+tableId));
	var $headerTable = $('th',$htable).each(function(index){
		if($.inArray(index, removeCols) == -1){
			headerHtml.push("<td>"+$(this).text()+"</td>");
		}
	});
	headerHtml.push("</tr>");
	var html =  $table.html();
	var $newHtml  = $(html);
	$('tr',$table).each(function(){
		headerHtml.push("<tr>");
		$(this).find('td').each(function(index){
			if($.inArray(index, removeCols)==-1){
				var text = $(this).text();
				headerHtml.push('<td>'+text+'</td>');
			}
		});
		headerHtml.push("</tr>");
	});
	html = "<table>"+headerHtml.join("")+"</table>";
	return html;
}
function   searchBoxHideShown($container,grid){
	$('#search_box',$container).on('shown.ace.widget', function(e) {
		$('.chosen-container',$('#search_box',$container)).each(function() {
			 var $this = $(this);
			 $this.css({'width': $this.parent().width()});
		});
		var searchConditionHeight = $('.widget-body',$('#search_box',$container)).height();
		grid.setGridHeight(getGridDefaultHeight()-searchConditionHeight);
	});
	
	
	$('#search_box',$container).on('hide.ace.widget', function(e) {
		var searchConditionHeight = $('.widget-body',$('#search_box',$container)).height();
		grid.setGridHeight(getGridDefaultHeight());
	});
}
function exportExcel(url,tableId,removeCols){
   var html = getTableHtml(tableId,removeCols);
  
   var $exportForm = $('#exportForm');
   if($exportForm.length == 0){
	   var form = "<form id ='exportForm' name='exportForm'  style='display:none' action='"+url+"' method='post'>";
	   form = form + "<input type='hidden' name='html' value='" +html + "'>";
	   form = form + "</form>";
	   $exportForm = $(form);
       $('body').append($exportForm);
   }else{
	   exportForm.action = url;
	   $('input[name="html"]',$exportForm).val(html);
   }
   exportForm.submit();
}
function getFitGridWidth(w){
//	var v = window.screen.width;
//	if(v<1024){
//		if(w){
//			return w;
//		}
//		return v;
//	}
	return $(".page-content").width();
}
   function getFormatoptions(baseUrl){
	   var formatoptions = {
			keys:true,
			editformbutton:hasPermission(baseUrl,"update"),
			editbutton:hasPermission(baseUrl,"update"),
			delbutton : true,
			editOptions:{
			   recreateForm: true,
			   url:baseUrl+'update',
			   closeOnEscape: true,
               closeAfterAdd: true,
               viewPagerButtons: true,
               closeAfterEdit: true,
               beforeShowForm: function(form){
               	style_edit_form(form);
			   },
			   afterSubmit:afterSubmit
			},
			delOptions : {
				 recreateForm: true,
				 afterSubmit : afterSubmit,
				 url:baseUrl+'del',
				 beforeShowForm: function(form){
					 style_delete_form(form);
				 }
			}
		};
	   return formatoptions;
   }
   function getGridDefaultHeight(){
	   if($.homeGlobal.gridDefaultHeight){
		   return $.homeGlobal.gridDefaultHeight;
	   }
	   return 310;
   }

   function afterSubmit (response, postdata) {
		var data = eval('('+response.responseText+')');
        if(data && !data.success){
        	 if(data.errorCode == 'not_login'){
        		 top.location.href ='index.html';
        	 }
        	 return [false,data.msg]; 
        }else{
        	 return [true,''] ;
        } 
     }
	function style_edit_form(form) {
		//enable datepicker on "sdate" field and switches for "stock" field
		form.find("input[ctype='date']").datepicker({format:'yyyy-mm-dd' , autoclose:true});
		
		//form.find('input[name=stock]').addClass('ace ace-switch ace-switch-5').after('<span class="lbl"></span>');
				   //don't wrap inside a label element, the checkbox value won't be submitted (POST'ed)
				  //.addClass('ace ace-switch ace-switch-5').wrap('<label class="inline" />').after('<span class="lbl"></span>');
		//update buttons classes
		var buttons = form.next().find('.EditButton .fm-button');
		buttons.addClass('btn btn-sm').find('[class*="-icon"]').hide();//ui-icon, s-icon
		buttons.eq(0).addClass('btn-primary').prepend('<i class="ace-icon fa fa-check"></i>');
		buttons.eq(1).prepend('<i class="ace-icon fa fa-times"></i>')
		
		buttons = form.next().find('.navButton a');
		buttons.find('.ui-icon').hide();
		buttons.eq(0).append('<i class="ace-icon fa fa-chevron-left"></i>');
		buttons.eq(1).append('<i class="ace-icon fa fa-chevron-right"></i>');		
	}
	function style_delete_form(form) {
		var buttons = form.next().find('.EditButton .fm-button');
		buttons.addClass('btn btn-sm btn-white btn-round').find('[class*="-icon"]').hide();//ui-icon, s-icon
		buttons.eq(0).addClass('btn-danger').prepend('<i class="ace-icon fa fa-trash-o"></i>');
		buttons.eq(1).addClass('btn-default').prepend('<i class="ace-icon fa fa-times"></i>')
	}
	
	function style_search_filters(form) {
		form.find('.delete-rule').val('X');
		form.find('.add-rule').addClass('btn btn-xs btn-primary');
		form.find('.add-group').addClass('btn btn-xs btn-success');
		form.find('.delete-group').addClass('btn btn-xs btn-danger');
	}
	function style_search_form(form) {
		var dialog = form.closest('.ui-jqdialog');
		var buttons = dialog.find('.EditTable')
		buttons.find('.EditButton a[id*="_reset"]').addClass('btn btn-sm btn-info').find('.ui-icon').attr('class', 'ace-icon fa fa-retweet');
		buttons.find('.EditButton a[id*="_query"]').addClass('btn btn-sm btn-inverse').find('.ui-icon').attr('class', 'ace-icon fa fa-comment-o');
		buttons.find('.EditButton a[id*="_search"]').addClass('btn btn-sm btn-purple').find('.ui-icon').attr('class', 'ace-icon fa fa-search');
	}
	
	
	//it causes some flicker when reloading or navigating grid
	//it may be possible to have some custom formatter to do this as the grid is being created to prevent this
	//or go back to default browser checkbox styles for the grid
	function styleCheckbox(table) {
	/**
		$(table).find('input:checkbox').addClass('ace')
		.wrap('<label />')
		.after('<span class="lbl align-top" />')


		$('.ui-jqgrid-labels th[id*="_cb"]:first-child')
		.find('input.cbox[type=checkbox]').addClass('ace')
		.wrap('<label />').after('<span class="lbl align-top" />');
	*/
	}
	

	//unlike navButtons icons, action icons in rows seem to be hard-coded
	//you can change them like this in here if you want
	function updateActionIcons(table) {
		/**
		var replacement = 
		{
			'ui-ace-icon fa fa-pencil' : 'ace-icon fa fa-pencil blue',
			'ui-ace-icon fa fa-trash-o' : 'ace-icon fa fa-trash-o red',
			'ui-icon-disk' : 'ace-icon fa fa-check green',
			'ui-icon-cancel' : 'ace-icon fa fa-times red'
		};
		$(table).find('.ui-pg-div span.ui-icon').each(function(){
			var icon = $(this);
			var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
			if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
		})
		*/
	}
	
	//replace icons with FontAwesome icons like above
	function updatePagerIcons(table) {
		var replacement = 
		{
			'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
			'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
			'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
			'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
		};
		$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
			var icon = $(this);
			var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
			
			if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
		})
	}

	function enableTooltips(table) {
		$('.navtable .ui-pg-button').tooltip({container:'body'});
		$(table).find('.ui-pg-div').tooltip({container:'body'});
	}
	function loadComplete (data) {
	   
		if(!data.success){
			if(data.errorCode && data.errorCode == 'not_login'){
				top.location.href ='index.html';
			}else{
				$.loy.alert(data.msg ,'error');
			}
		}
		var table = this;
		setTimeout(function(){
			styleCheckbox(table);
			updateActionIcons(table);
			updatePagerIcons(table);
			enableTooltips(table);
		}, 0);
	}
	function hasPermission(baseUrl,op){
		baseUrl = baseUrl.replace("/",""); 
		var accessCode = baseUrl+":"+op;
//		if($.homeGlobal.accessCodes == null){
//			setTimeout(function() {
//				hasPermission(baseUrl,op);
//			}, 2);
//			return;
//		}
		var value = $.homeGlobal.accessCodes[accessCode];
		if(value == null || value == undefined){
			return true;
		}
		return value;
	}
	
(function ($) {
	
	var mEdit = {
			//edit record form
			 closeOnEscape: true,
             viewPagerButtons: true,
             closeAfterEdit: true,
			//width: 700,
			//url:"/user/edit",
			recreateForm: true,
			errorTextFormat: function (response) {
				var result = eval('('+response.responseText+')');
			    return result.message;
			},
			afterSubmit : afterSubmit,  
			beforeShowForm : function(form) {
				style_edit_form(form);
				var form = $(form[0]);
				/*
				 var e = $(e);
	                $("tr", e).each(function() {
	                    var inputs = $(">td.DataTD:has(input,select)",this);
	                    if (inputs.length == 1) {
	                        var tds = $(">td", this);
	                        tds.eq(1).attr("colSpan", tds.length - 1);
	                        tds.slice(2).hide();
	                    }
	                });
				 */
				//form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
			}
		};
	var mAdd = {
			 closeOnEscape: true,
             closeAfterAdd: true,
             viewPagerButtons: true,
			recreateForm: true,
			//url:"/user/add",
			beforeShowForm : function(form) {
				style_edit_form(form);
			},
			afterSubmit : afterSubmit,
			errorTextFormat: function (response) {
				var result = eval('('+response.responseText+')');
			    return result.message;
			}
	};
	var mDel ={
		//delete record form
		recreateForm: true,
		//url:"/user/del",
		afterSubmit : afterSubmit, 
		beforeShowForm : function(e) {
			var form = $(e[0]);
			if(form.data('styled')) return false;
			form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
			style_delete_form(form);
			
			form.data('styled', true);
		},
		onClick : function(e) {
			//alert(1);
		}
	};
	
	var mSearch = {
		//search form
		recreateForm: true,
		afterShowSearch: function(e){
			var form = $(e[0]);
			form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
			style_search_form(form);
		},
		afterRedraw: function(){
			style_search_filters($(this));
		},
		multipleSearch: true,
		/**
		multipleGroup:true,
		showQuery: true
		*/
	};
	var mView = {
		//view record form
		recreateForm: true,
		beforeShowForm: function(e){
			var form = $(e[0]);
			form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
		}
	};
	
	var mExcel = {
			
		};
	var setting = {
			cellEdit:false,
			datatype: "json",
	   		scrollOffset:20,
	   		height: getGridDefaultHeight()+'',
			shrinkToFit:window.screen.width>=$.homeGlobal.BIG_SCREEN?true:false,
			//toppager: true, 
			pagerpos:'center',
			viewrecords : true,
			rowNum:10,
			rownumbers: true, 
			rowList:[10,20,30],
			altRows: true,
			//toppager: true,
			multiselect: true,
			//multikey: "ctrlKey",
	        multiboxonly: true,
	        autowidth: true,
	        sortable:false,
	        //width:getFitGridWidth(),
	        jsonReader: {
				root: "data.content", 
				total: "data.totalPages",//总页数
				page: "data.number",//当前页
				records: "data.totalElements",//记录总数
				repeatitems: false
			},
			prmNames : {
				page:"page", // 表示请求页码的参数名称
				rows:"size", // 表示请求行数的参数名称
				sort: "orderProperty", // 表示用于排序的列名的参数名称
				order: "direction", // 表示采用的排序方式的参数名称
				//search:"_search", // 表示是否是搜索请求的参数名称
				//nd:"nd", // 表示已经发送请求的次数的参数名称
				//id:"id", // 表示当在编辑数据模块中发送数据时，使用的id的名称
				//oper:"oper", // operation参数名称
				//editoper:"edit", // 当在edit模式中提交数据时，操作的名称
				//addoper:"add", // 当在add模式中提交数据时，操作的名称
				//deloper:"del", // 当在delete模式中提交数据时，操作的名称
				//subgridid:"id", // 当点击以载入数据到子表时，传递的数据名称
				npage: null
				//totalrows:"totalrows" // 表示需从Server得到总共多少行数据的参数名称，参见jqGrid选项中的rowTotal

			},
			loadComplete : function(data) {
				loadComplete(data);
			},
			gridComplete:function(){
				 var $jqGrid = $(this);
				 var width = getFitGridWidth();
				 if(width == 0){
					 setTimeout(function() {
						 $jqGrid.jqGrid('setGridWidth', getFitGridWidth());
					}, 20);
				 }else{
					 $jqGrid.jqGrid('setGridWidth', getFitGridWidth());
				 }	
			},
			onInitGrid:function(){
				var $jqGrid = $(this);
				$(window).on('resize.jqGrid', function() {
					 $jqGrid.loyGrid('setGridWidth', getFitGridWidth());
				});
				var parent_column = $jqGrid.closest('[class*="col-"]');
				$(document).on('settings.ace.jqGrid', function(ev, event_name, collapsed) {
					if (event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed') {
							setTimeout(function() {
								$jqGrid.jqGrid('setGridWidth', getFitGridWidth());
							}, 10);
							
					}
				});
				$(document).on('mainTabClick', function(ev) {
						setTimeout(function() {
							 $jqGrid.jqGrid('setGridWidth', getFitGridWidth());
						}, 0);
						
				});
			}
	}
	
	$.extend($.jgrid.defaults, setting || {});
	
	$.fn.loyGrid = $.fn.extend($.fn.jqGrid,{
		
		navGrid : function (elem, o, pEdit,pAdd,pDel,pSearch, pView) {
				o = $.extend({
				position : "left",
				closeOnEscape : true,
				beforeRefresh : null,
				afterRefresh : null,
				alertwidth : 200,
				alertheight : 'auto',
				alerttop: null,
				alertleft: null,
				alertzIndex : null,
				
				baseUrl:null,
				//cloneToTop: true, 
				edit: true,
				editicon : 'ace-icon fa fa-pencil blue',
				add: true,
				addicon : 'ace-icon fa fa-plus-circle purple',
				del: true,
				delicon : 'ace-icon fa fa-trash-o red',
				search: false,
				searchicon : 'ace-icon fa fa-search orange',
				refresh: true,
				refreshicon : 'ace-icon fa fa-refresh green',
				view: false,
				viewicon : 'ace-icon fa fa-search-plus grey',
				excel:true,
				excelicon:'ace-icon fa fa-file-excel-o green',
				refreshstate:'current'
					
			}, $.jgrid.nav, o ||{});
			//o =  $.extend(mo,o);
			return this.each(function() {
				if(this.nav) {return;}
				var alertIDs = {themodal: 'alertmod_' + this.p.id, modalhead: 'alerthd_' + this.p.id,modalcontent: 'alertcnt_' + this.p.id},
				$t = this, twd, tdw;
				if(!$t.grid || typeof elem !== 'string') {return;}
				if ($("#"+alertIDs.themodal)[0] === undefined) {
					if(!o.alerttop && !o.alertleft) {
						if (window.innerWidth !== undefined) {
							o.alertleft = window.innerWidth;
							o.alerttop = window.innerHeight;
						} else if (document.documentElement !== undefined && document.documentElement.clientWidth !== undefined && document.documentElement.clientWidth !== 0) {
							o.alertleft = document.documentElement.clientWidth;
							o.alerttop = document.documentElement.clientHeight;
						} else {
							o.alertleft=1024;
							o.alerttop=768;
						}
						o.alertleft = o.alertleft/2 - parseInt(o.alertwidth,10)/2;
						o.alerttop = o.alerttop/2-25;
					}
					$.jgrid.createModal(alertIDs,
						"<div>"+o.alerttext+"</div><span tabindex='0'><span tabindex='-1' id='jqg_alrt'></span></span>",
						{ 
							gbox:"#gbox_"+$.jgrid.jqID($t.p.id),
							jqModal:true,
							drag:true,
							resize:true,
							caption:o.alertcap,
							top:o.alerttop,
							left:o.alertleft,
							width:o.alertwidth,
							height: o.alertheight,
							closeOnEscape:o.closeOnEscape, 
							zIndex: o.alertzIndex
						},
						"#gview_"+$.jgrid.jqID($t.p.id),
						$("#gbox_"+$.jgrid.jqID($t.p.id))[0],
						true
					);
				}
				var clone = 1, i,
				onHoverIn = function () {
					if (!$(this).hasClass('ui-state-disabled')) {
						$(this).addClass("ui-state-hover");
					}
				},
				onHoverOut = function () {
					$(this).removeClass("ui-state-hover");
				};
				if(o.cloneToTop && $t.p.toppager) {clone = 2;}
				for(i = 0; i<clone; i++) {
					var tbd,
					navtbl = $("<table cellspacing='0' cellpadding='0' border='0' class='ui-pg-table navtable' style='float:left;table-layout:auto;'><tbody><tr></tr></tbody></table>"),
					sep = "<td class='ui-pg-button ui-state-disabled' style='width:4px;'><span class='ui-separator'></span></td>",
					pgid, elemids;
					if(i===0) {
						pgid = elem;
						elemids = $t.p.id;
						if(pgid === $t.p.toppager) {
							elemids += "_top";
							clone = 1;
						}
					} else {
						pgid = $t.p.toppager;
						elemids = $t.p.id+"_top";
					}
					if($t.p.direction === "rtl") {$(navtbl).attr("dir","rtl").css("float","right");}
					if (o.add && hasPermission(o.baseUrl,"save")) {
						pAdd = pAdd || {};
						pAdd = $.extend(pAdd,mAdd);
						if(!pAdd.url && o.baseUrl){
							pAdd.url = o.baseUrl+"/save";
						}
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						$(tbd).append("<div class='ui-pg-div'><span class='ui-icon "+o.addicon+"'></span>"+o.addtext+"</div>");
						$("tr",navtbl).append(tbd);
						$(tbd,navtbl)
						.attr({"title":o.addtitle || "",id : pAdd.id || "add_"+elemids})
						.click(function(){
							if (!$(this).hasClass('ui-state-disabled')) {
								if ($.isFunction( o.addfunc )) {
									o.addfunc.call($t);
								} else {
									$($t).jqGrid("editGridRow","new",pAdd);
								}
							}
							return false;
						}).hover(onHoverIn, onHoverOut);
						tbd = null;
					}
					if (o.edit && hasPermission(o.baseUrl,"update")) {
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						pEdit = pEdit ||{};
						var pEdit = $.extend(pEdit,mEdit);
						if(!pEdit.url && o.baseUrl){
							pEdit.url = o.baseUrl+"/update";
						}
						$(tbd).append("<div class='ui-pg-div'><span class='ui-icon "+o.editicon+"'></span>"+o.edittext+"</div>");
						$("tr",navtbl).append(tbd);
						$(tbd,navtbl)
						.attr({"title":o.edittitle || "",id: pEdit.id || "edit_"+elemids})
						.click(function(){
							if (!$(this).hasClass('ui-state-disabled')) {
								var sr = $t.p.selrow;
								if (sr) {
									if($.isFunction( o.editfunc ) ) {
										o.editfunc.call($t, sr);
									} else {
										$($t).jqGrid("editGridRow",sr,pEdit);
									}
								} else {
									$.jgrid.viewModal("#"+alertIDs.themodal,{gbox:"#gbox_"+$.jgrid.jqID($t.p.id),jqm:true});
									$("#jqg_alrt").focus();
								}
							}
							return false;
						}).hover(onHoverIn, onHoverOut);
						tbd = null;
					}
					if (o.view && hasPermission(o.baseUrl,"detail")) {
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						pView = pView || {};
						pView = $.extend(pView,mView);
						$(tbd).append("<div class='ui-pg-div'><span class='ui-icon "+o.viewicon+"'></span>"+o.viewtext+"</div>");
						$("tr",navtbl).append(tbd);
						$(tbd,navtbl)
						.attr({"title":o.viewtitle || "",id: pView.id || "view_"+elemids})
						.click(function(){
							if (!$(this).hasClass('ui-state-disabled')) {
								var sr = $t.p.selrow;
								if (sr) {
									if($.isFunction( o.viewfunc ) ) {
										o.viewfunc.call($t, sr);
									} else {
										$($t).jqGrid("viewGridRow",sr,pView);
									}
								} else {
									$.jgrid.viewModal("#"+alertIDs.themodal,{gbox:"#gbox_"+$.jgrid.jqID($t.p.id),jqm:true});
									$("#jqg_alrt").focus();
								}
							}
							return false;
						}).hover(onHoverIn, onHoverOut);
						tbd = null;
					}
					if (o.del && hasPermission(o.baseUrl,"del")) {
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						pDel = pDel || {};
						pDel = $.extend(pDel,mDel);
						if(!pDel.url && o.baseUrl){
							pDel.url = o.baseUrl+"/del";
						}
						$(tbd).append("<div class='ui-pg-div'><span class='ui-icon "+o.delicon+"'></span>"+o.deltext+"</div>");
						$("tr",navtbl).append(tbd);
						$(tbd,navtbl)
						.attr({"title":o.deltitle || "",id: pDel.id || "del_"+elemids})
						.click(function(){
							if (!$(this).hasClass('ui-state-disabled')) {
								var dr;
								if($t.p.multiselect) {
									dr = $t.p.selarrrow;
									if(dr.length===0) {dr = null;}
								} else {
									dr = $t.p.selrow;
								}
								if(dr){
									if($.isFunction( o.delfunc )){
										o.delfunc.call($t, dr);
									}else{
										$($t).jqGrid("delGridRow",dr,pDel);
									}
								} else  {
									$.jgrid.viewModal("#"+alertIDs.themodal,{gbox:"#gbox_"+$.jgrid.jqID($t.p.id),jqm:true});$("#jqg_alrt").focus();
								}
							}
							return false;
						}).hover(onHoverIn, onHoverOut);
						tbd = null;
					}
					if (o.excel && hasPermission(o.baseUrl,"excel")) {
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						pExcel = pExcel ||{};
						var pExcel = $.extend(pExcel,mExcel);
						if(!pExcel.url && o.baseUrl){
							pExcel.url = o.baseUrl+"/excel";
						}
						$(tbd).append("<div class='ui-pg-div'><span class='ui-icon "+o.excelicon+"'></span>"+o.exceltext+"</div>");
						$("tr",navtbl).append(tbd);
						$(tbd,navtbl)
						.attr({"title":o.exceltitle || "",id: pExcel.id || "excel_"+elemids})
						.click(function(){
							if($.isFunction( o.excelfunc )) {
								o.excelfunc.call($t, pExcel);
							} else {
								var tableId = o.baseUrl.replace("/","");
								exportExcel(pExcel.url,tableId+"_grid-table");
							}
						}).hover(onHoverIn, onHoverOut);
						tbd = null;
					}
					
					if(o.add || o.edit || o.del || o.view) {$("tr",navtbl).append(sep);}
					if (o.search) {
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						pSearch = pSearch || {};
						pSearch = $.extend(mSearch,pSearch);
						$(tbd).append("<div class='ui-pg-div'><span class='ui-icon "+o.searchicon+"'></span>"+o.searchtext+"</div>");
						$("tr",navtbl).append(tbd);
						$(tbd,navtbl)
						.attr({"title":o.searchtitle  || "",id:pSearch.id || "search_"+elemids})
						.click(function(){
							if (!$(this).hasClass('ui-state-disabled')) {
								if($.isFunction( o.searchfunc )) {
									o.searchfunc.call($t, pSearch);
								} else {
									$($t).jqGrid("searchGrid",pSearch);
								}
							}
							return false;
						}).hover(onHoverIn, onHoverOut);
						if (pSearch.showOnLoad && pSearch.showOnLoad === true) {
							$(tbd,navtbl).click();
						}
						tbd = null;
					}
					if (o.refresh) {
						tbd = $("<td class='ui-pg-button ui-corner-all'></td>");
						$(tbd).append("<div class='ui-pg-div'><span class='ui-icon "+o.refreshicon+"'></span>"+o.refreshtext+"</div>");
						$("tr",navtbl).append(tbd);
						$(tbd,navtbl)
						.attr({"title":o.refreshtitle  || "",id: "refresh_"+elemids})
						.click(function(){
							if (!$(this).hasClass('ui-state-disabled')) {
								if($.isFunction(o.beforeRefresh)) {o.beforeRefresh.call($t);}
								$t.p.search = false;
								$t.p.resetsearch =  true;
								try {
									if( o.refreshstate !== 'currentfilter') {
										var gID = $t.p.id;
										$t.p.postData.filters ="";
										try {
											$("#fbox_"+$.jgrid.jqID(gID)).jqFilter('resetFilter');
										} catch(ef) {}
										if($.isFunction($t.clearToolbar)) {$t.clearToolbar.call($t,false);}
									}
								} catch (e) {}
								switch (o.refreshstate) {
									case 'firstpage':
										$($t).trigger("reloadGrid", [{page:1}]);
										break;
									case 'current':
									case 'currentfilter':
										$($t).trigger("reloadGrid", [{current:true}]);
										break;
								}
								if($.isFunction(o.afterRefresh)) {o.afterRefresh.call($t);}
							}
							return false;
						}).hover(onHoverIn, onHoverOut);
						tbd = null;
					}
					tdw = $(".ui-jqgrid").css("font-size") || "11px";
					$('body').append("<div id='testpg2' class='ui-jqgrid ui-widget ui-widget-content' style='font-size:"+tdw+";visibility:hidden;' ></div>");
					twd = $(navtbl).clone().appendTo("#testpg2").width();
					$("#testpg2").remove();
					$(pgid+"_"+o.position,pgid).append(navtbl);
					if($t.p._nvtd) {
						if(twd > $t.p._nvtd[0] ) {
							$(pgid+"_"+o.position,pgid).width(twd);
							$t.p._nvtd[0] = twd;
						}
						$t.p._nvtd[1] = twd;
					}
					tdw =null;twd=null;navtbl =null;
					this.nav = true;
				}
			});
		}
	});
	
	
})(jQuery);