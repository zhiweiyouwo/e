	<div  id="notice_container">
	<div class="row">
		<div class="col-xs-12">
			<div class="widget-box  ui-sortable-handle">
				<div class="widget-header"><a href="#" ><span  onclick="$('#noticeSearchBtn').click()" class="ace-icon fa fa-search icon-on-right bigger-110"></span></a>
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
						       <div class="col-xs-12 col-sm-2 " style="padding-bottom: 2px">
							       <select id="noticeQueryParam_noticeStatus" class="form-control search-query">
							       <option value="" i18n="all"></option>
							       <option value="DRAFT">草稿</option>
							       <option value="SENT">发送</option>
							       </select>
					           </div>
					           <div class="col-xs-12 col-sm-6 ">
							    <div id="noticeSearchDiv" class="input-group col-xs-12  col-sm-6  " style="padding-bottom: 2px">
									 <input type="text" id="noticeQueryParam_subject"  i18n="personnel.notice.subject"  placeholder ="主题" class="form-control search-query" >
									<span class="input-group-btn">
										<button id="noticeSearchBtn" type="button" class="btn btn-purple btn-sm">
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
							    
			
			<table id="notice_grid-table"></table>

			<div id="notice_grid-pager"></div>

		</div>
		<!-- /.col -->
	</div>
	<!-- /.row -->
	
	
	<div id="noticeModalDiv" class="modal fade" tabindex="-1" data-backdrop="static">
		<div class="modal-dialog modal-lg" >
				<div class="modal-content">
					<div class="modal-header no-padding">
						<div class="table-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
								<span class="white">&times;</span>
							</button>
							<span i18n="personnel.notice.editNotice">编辑通知</span>
						</div>
					</div>
					<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">
						<div id="modal-tip" class="red clearfix"></div>
						<div>
							<div class="widget-body">
								
								<form id="noticeForm" name="noticeForm" class="form-horizontal notice-form col-xs-12">
								     <input type="hidden"  name="id" id="id"/>
								     <input id = "content" type="hidden"  name="content" />
									<!-- #section:pages/inbox.compose -->
									<div>
										<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right" i18n="personnel.notice.subject" for="form-field-subject">主题:</label>
											<div class="col-sm-8">
												<div class="input-icon block col-xs-12 no-padding">
													<input maxlength="100" type="text" class="col-xs-12" name="subject" id="subject" />
													<i class="ace-icon fa fa-comment-o"></i>
												</div>
											</div>
										</div>

										<div class="hr hr-18 dotted"></div>

										<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right">
												<span class="inline space-24 hidden-480"></span>
												<span i18n="personnel.notice.content"></span>
											</label>

											<!-- #section:plugins/editor.wysiwyg -->
											<div class="col-sm-9">
												<div id="contentDiv"  style="height: 150px;border:1px solid  #BBC0CA;" class="wysiwyg-editor"></div>
											</div>

											<!-- /section:plugins/editor.wysiwyg -->
										</div>

										<div class="hr hr-18 dotted"></div>


                                         <div class="form-group no-margin-bottom">
											<label class="col-sm-3 control-label  i18n="personnel.notice.attachment"  no-padding-right">附件:</label>

											<div class="col-sm-9">
												<div id="view-attachments">
												
												
												 
												</div>
											</div>
										</div>
										
										
										<div class="form-group no-margin-bottom">
											<label class="col-sm-3 control-label no-padding-right"></label>

											<div class="col-sm-9">
												<div id="notice-form-attachments">
													<!-- #section:custom/file-input -->
													<input type="file" name="uploadAttachments" />

													<!-- /section:custom/file-input -->
												</div>
											</div>
										</div>

										<div class="align-right">
											<button id="id-add-attachment" type="button" class="btn btn-sm btn-danger">
												<i class="ace-icon fa fa-paperclip bigger-140"></i>
												<span i18n="personnel.notice.addAttachment"></span>
											</button>
										</div>

										<div class="space"></div>
									</div>

									<!-- /section:pages/inbox.compose -->
								</form>
									
								
							</div>
						</div>
					</div>
					<div class="modal-footer no-margin-top">
						<div class="text-center">
							
							<button id="submitNoticeBtn"  class="btn btn-sm btn-primary">
							  <i class="ace-icon fa fa-floppy-o"></i>
							  <span i18n="save"></span>
							</button>
							<button id="sendNoticeBtn"  class="btn btn-sm btn-primary">
							  <i class="ace-icon fa fa-floppy-o"></i>
							  <span i18n="personnel.notice.send"></span>
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
	
	
	
	
		
	<div id="noticeViewModalDiv" class="modal fade" tabindex="-1" data-backdrop="static" ng-controller="noticeViewCtrl">
		<div class="modal-dialog modal-lg" >
				<div class="modal-content">
					<div class="modal-header no-padding">
						<div class="table-header">
							<button type="button" class="close" data-dismiss="modal" aria-hidden="true">
								<span class="white">&times;</span>
							</button>
							<span i18n="personnel.notice"></span>
						</div>
					</div>
					<div class="modal-body" style="max-height: 450px;overflow-y: scroll;">
						<div id="modal-tip" class="red clearfix"></div>
						<div>
								<div class="widget-body">
								
								<form id="noticeForm" name="noticeForm" class="form-horizontal notice-form col-xs-12">
								     <input type="hidden"  name="id" id="id"/>
								     <input id = "content" type="hidden"  name="content" />
									<!-- #section:pages/inbox.compose -->
									<div>
										<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right" i18n="personnel.notice.subject" for="form-field-subject">主题:</label>
											<div class="col-sm-8">
												<div class="input-icon block col-xs-12 no-padding">
													<div id="view_subject"></div>
												</div>
											</div>
										</div>

										<div class="hr hr-18 dotted"></div>

										<div class="form-group">
											<label class="col-sm-3 control-label no-padding-right">
												<span class="inline space-24 hidden-480"></span>
												<span i18n="personnel.notice.content"></span>
											</label>

											<!-- #section:plugins/editor.wysiwyg -->
											<div class="col-sm-9">
												<div id="view_content" ></div>
											</div>

											<!-- /section:plugins/editor.wysiwyg -->
										</div>

										<div class="hr hr-18 dotted"></div>

										<div class="form-group no-margin-bottom">
											<label class="col-sm-3 control-label no-padding-right" i18n="personnel.notice.attachment"></label>

											<div class="col-sm-9">
												<div id="detail-iew-attachments">
												
												
												 
												</div>
											</div>
										</div>


										<div class="space"></div>
									</div>

									<!-- /section:pages/inbox.compose -->
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
var scripts = [ null,"static/component/assets/js/jquery.hotkeys.js",
                "static/component/assets/js/bootstrap-wysiwyg.js",
                "static/component/assets/js/ace/elements.wysiwyg.js",null ];
$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
	$container = $('#notice_container');
	var grid_selector  = "#notice_grid-table";
	var pager_selector = "#notice_grid-pager";
	var colNames;
	var  noticeGrid = null;
	$.loy.i18n(['personnel/notice'],$.homeGlobal.LANG,$container,{custCallback:function(){
		colNames =[' ',
	               $.i18n.prop("personnel.notice.subject"),
	               $.i18n.prop("personnel.notice.status"),
	               $.i18n.prop("personnel.notice.createdTime")
	               ];
	    createNoticeGrid();
	}});
	
	var $validateNoticeForm = $('#noticeForm').validate({
    	onsubmit:false,
    	rules : {
			subject : {
				required : true,
			}
		}
    });
	function clearNoticeForm(){
		$('#id').val('');
		$('#subject').val('');
		$('#content').val('');
		$('#contentDiv').html('');
		$('#view-attachments').html('');
		$('.notice-form .ace-file-input:not(:first-child)').remove();
		$('.notice-form input[type=file]').ace_file_input('reset_input_ui');
		$('a[data-action=delete]',$('#notice-form-attachments')).click();
	}
	function edit (id){
		clearNoticeForm();
		$('#submitNoticeBtn').attr("url","notice/update");
		$.loy.ajax({
			url:'notice/get',
			data:{id:id},
			success:function(data){
				var result = data.data;
				$('#id').val(result.id?result.id:'');
				$('#subject').val(result.subject?result.subject:'');
				$('#content').val(result.content?result.content:'');
				$('#contentDiv').html((result.content?result.content:''));
			}
	   });
	}
	function view (id){
		$('#noticeViewModalDiv').modal("show");
		$.loy.ajax({
			url:'notice/get',
			data:{id:id},
			success:function(data){
				var result = data.data;
				$('#view_subject').html(result.subject?result.subject:'');
				$('#view_content').html(result.content?result.content:'');
			}
	});
  }
  function add(){
		clearNoticeForm();
		$('#submitNoticeBtn').attr("url","notice/save");
		$('#noticeModalDiv').modal("show");
		$('#submitNoticeBtn').show();
  }
  
  function  createNoticeGrid(){
		noticeGrid =jQuery(grid_selector).loyGrid({
			url: 'notice/page',
			datatype: "json",
			mtype: 'GET',
			colNames:colNames,
			colModel: [
			 {name:'myac',index:'', width:80, fixed:true, sortable:false, resize:false ,
				formatter:'actions', 
				formatoptions:getFormatoptions('notice/')
			 },
	   		
	   		{ name: 'subject', index: 'subject', width: 100, align: "left",editable: true }, 
	   		
	   		{ name: 'createdTime', index: 'createdTime', width: 100, align: "left",editable: true,formatter:'time', editoptions:{ctype:'date'}}
	   		],
			pager: pager_selector,
			//width:$(".page-content").width(),
			height: '310',
			loadComplete:function(data){
				loadComplete(data);
				var list = data.data?data.data.content:null;
				if(list){
					for(var i=0;i<list.length;i++){
						
					}
				}	
			}
		}).loyGrid('navGrid','#notice_grid-pager',{"baseUrl":"notice/",
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
		noticeGrid.jqGrid('setFrozenColumns');
		resizeToFitPage(noticeGrid);
	}
	
	$("#noticeSearchBtn").click(function(){
		var subject = $("#noticeQueryParam_subject").val();
		var noticeStatus = $("#noticeQueryParam_noticeStatus").val();
		var postData ={"subject":subject,"noticeStatus":noticeStatus,page:0};
		noticeGrid.loyGrid("setGridParam",{"postData":postData}).trigger("reloadGrid"); 
		
	});
	
	$('#submitNoticeBtn').click(function(){
	     if(!$validateNoticeForm.checkForm()){
			$validateNoticeForm.defaultShowErrors();
			return;
		 }
		 var url = $(this).attr("url");
		 $('#content').val($('#contentDiv').html());
		 var form = $("#noticeForm")[0];
		 var formData = new FormData(form);
		 $.loy.ajax({
				url:url,
				data:formData,
				success:function(data){
					if(data.success){
						$('#noticeModalDiv').modal("hide");
						noticeGrid.trigger("reloadGrid");
					}
				}
		});
    });
	
});

</script>
