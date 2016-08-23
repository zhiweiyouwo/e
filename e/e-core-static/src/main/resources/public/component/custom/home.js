
Home = function(){
	this.offsetHeight = function(){
		return 320;
	};
	this.getSelfPage = function(){
		
	};
	this.getIndexDataUrl = function(){
		return "indexData";
	}
	this.hideProfileDropdown = function(){
		FastClick.attach(document.body); 
		var self = this;
		$.homeGlobal.gridDefaultHeight = document.documentElement.clientHeight-self.offsetHeight();
		
	   	if($('#myprofileDropdownDiv').is(':visible')){
	   		$('#myprofileDropdown').click();
	   	}
	};
	this.initDefaultPage = function(){
		
	};
	this.buildMenu = function (data){};
	this.setNoticeCount = function(){
		 $.loy.ajax({
				url:'upm/notice/noread',
				success:function(data){
					if(data.success){
						var result = data.data;
						var notices = result.content;
						if(notices){
						   $('#myNoticeSpan').html(result.totalElements);
						}
					}	
				}
		 });
	};
	this.bindNoticeDropdown = function(){
		var self = this;
		$('#noticeDropdown').bind('show.bs.dropdown', function () {
   		 var $noticeList = $('#noticeList');
   		 $noticeList.html('');
   		 $.loy.ajax({
   				url:'upm/notice/noread',
   				success:function(data){
   					if(data.success){
   						var result = data.data;
   						var list = [];
   						var notices = result.content;
   						if(notices){
   						   $('#myNoticeSpan').html(result.totalElements);
   						   var len = notices.length;
   						   if(len>5){
   							   len = 5;
   						   }
   						   for(var i=0;i<len;i++){
   							   var url = self.getSelfPage()+"#upm/personnel/notice_detail.html?id="+notices[i].id;
   							   list.push('<li><a href="'+url+'"><i class="btn btn-xs btn-primary fa fa-user"></i>'+notices[i].subject+'</a></li>');
   						   }
   						}
       					$noticeList.html(list.join(''));
   					}	
   				}
   		});
   	 });
	};
	
	this.buildSystemList = function(list){
		if(list && list.length>0){
			 $('#systemsDiv').show();
			 var temp =[];
			 for(var i=0;i<list.length;i++){
				 for(var i=0;i<list.length;i++){
   				 temp.push('<li><a href="'+list[i].url+'"  target="'+list[i].code+'" ><div class="clearfix"><span class="pull-left">'+list[i].name+'</span></div></a></li>');
   			 }
			 }
			 $('#systemListUl').html(temp.join(' '));
		 }
	};
	this.init = function(){
		var self = this;
		self.hideProfileDropdown();
		self.setNoticeCount();
		self.bindNoticeDropdown();
		$.loy.ajax({
			showSuccess:false,
			url:self.getIndexDataUrl(),
			success:function(result){
				var data = result.data;
				$.homeGlobal.accessCodes = data.accessCodes;
				var simpleUser = data.simpleUser;
				$.homeGlobal.USER_ID = data.simpleUser.id;
				$.homeGlobal.LANG = data.simpleUser.lang;
				
				self.initDefaultPage(data);
				bootbox.setDefaults("locale",$.homeGlobal.LANG); 
				//初始化语言列表
				var supportLocales = data.supportLocales;
				if(supportLocales){
					var temp = [];
					for(var i=0;i<supportLocales.length;i++){
						var v = supportLocales[i].value;
						var displayName = supportLocales[i].displayName;
						temp.push('<option value="'+v+'" ');
						if(v == data.simpleUser.lang){
							temp.push(' selected >');
						}else{
							temp.push('>');
						}
						temp.push(displayName+'</option>');
					}
					$('#languageSelect').html(temp.join(' '));
					$('#languageSelect').bind('change', function () {
		        		 var v = $(this).val();
			        	 $.loy.ajax({
								url:'lang',
								data:{"lang":v},
								success:function(result){
								   location.reload();
								}
			        	 });
		        	});
				}
				//$('#myNoticeSpan').html(data.notReadNotice);
				$('#loginUserName').html(simpleUser.name?simpleUser.name:"");
				$('#mySmallPhoto').attr('alt',simpleUser.name);
				if(data.photo){
					$('#mySmallPhoto').attr('src', "upm/profile/photo?id="+simpleUser.id+"&m="+Math.random());
				}else{
					$('#mySmallPhoto').attr('src', "component/assets/avatars/profile-pic.jpg");
				}
				
				
				$.loy.i18n(['upm/i18n/message'],$.homeGlobal.LANG,$(document),{
		        		 callback: function() {// 回调方法
		     		    	$.homeGlobal.messageFinish = true;
		     		    	self.buildMenu(data); 
		     		    	$("*[i18n]",$(document)).each(function(){
		     		    		 var $this = $(this);
		     		    		 var key =$this.attr('i18n');
		     		    		 var value = $.i18n.prop(key);
		     		    		 $this.html(value);
		     		    	});
		     		    	var lang = $.homeGlobal.LANG.replace('_',"-");
	 						var scripts= ["component/assets/js/jqGrid/i18n/grid.locale-"+lang+".js",
	 						              "component/custom/jquery.loyGrid.js",
	 						              "component/assets/js/date-time/locales/bootstrap-datepicker."+lang+".js"];
	 						$('.page-content-area').ace_ajax('loadScripts', scripts, function() {
	                        	 if($.homeGlobal.aceAjaxObject){
  									$.homeGlobal.aceAjaxObject.loadUrl($.homeGlobal.hs);
  								}
	 						});
				       }
			    });
	       }
        });
	};
}

