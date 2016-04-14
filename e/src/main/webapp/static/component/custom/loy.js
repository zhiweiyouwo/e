
(function($){
	 $.homeGlobal = {"homeBaseDataFinish":false,
			 "i18n":false,
			 "LANG" :"zh_CN",
			 "messageFinish":false,
			 "USER_ID":null,
			 "accessCodes":null,
			 "BIG_SCREEN":1024,
			 hs:null,
			 callbackAceAjax : function(hash){
        		 this.hs = hash;
        	 }
	 };
	$.loy = $.extend({}, $.loy);
	$.loy.getUrlParam= function(paramName){
        var reg = new RegExp("(^|&)" + paramName + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var url = window.location.href;
        var index = url.indexOf("?");
        url = url.substr(index+1);
        var r = url.match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]); return null; //返回参数值
	       
	}
	$.loy.buildSelectOptions= function(selectId,group,placeholder_text){
		$.ajax({ url: "dict",data:{"group":group}, 
        	success: function(data){
            if(data.success){
            	var list = data.data;
            	var temp = [];
            	if(list){
            		$('#'+selectId).html('');
            		temp.push('<option value=""></option>');
            		for(var i=0;i<list.length;i++){
            			var k = list[i].id;
            			var l = list[i].name;
            			temp.push('<option value="'+k+'">'+l+'</option>');
            		}
            	}
            	$('#'+selectId).html(temp.join(' '));
            	$('#'+selectId).chosen({allow_single_deselect:true,placeholder_text_single:placeholder_text});
            }
        }
        
        });
	       
	}
	$.loy.showSysMsg = function(msg){  
		$('#systemMsgDiv').show();
		$('#systemMsgSpan').html(msg);
		var winHeight = $(window).height();
        var scroH = $(window).scrollTop();
        var topH = winHeight + scroH -150;
        $("#systemMsgDiv").css("top", topH + "px").css("position", "absolute").css("right", "5px");
		setTimeout(function(){
			$('#systemMsgDiv').hide();
		} , 1500)
	};
	$.loy.alert = function(msg,type){  
		bootbox.dialog({
		    title: $.i18n.prop('info'),
			message: msg,
			//className: "alert_modal",
			buttons:{
				ok:{
					"label" : $.i18n.prop('close'),
					"className" : "btn-sm btn-danger"
				}			
			}
		});
	};
	$.loy.confirm = function(msg,fun){  
		  bootbox.confirm({  
		        buttons: {   
		            cancel: {  
		               // label: '取消',  
		                className: 'btn-default'  
		            } ,
		            confirm: {  
		                //label: '确认',  
		                className: "btn  btn-success" 
		            },  
		        },  
		        message: msg,  
		        callback: function(result) {  
		            if(result) {  
		            	if(fun){
		            		fun(result);
		            	}
		            } else {  
		            }  
		        },  
		        title: $.i18n.prop('confirm_info')
		        });
	};
	$.loy.i18n = function($targetScope){
		var $scope;
		if($targetScope){
			$scope = $targetScope;
		}else{
			$scope = $(document);
		}
		$("*[i18n]",$scope).each(function(){
    		 var $this = $(this);
    		 var key =$this.attr('i18n');
    		 var value = $.i18n.prop(key);
    		 $this.html(value);
    	});
	}
})(jQuery);
(function($){
	$.loy = $.extend({}, $.loy);
	$.loy.i18n = function(names,lang,$container,settings){
		settings = $.extend({}, settings);
		//settings.language = lang;
		//settings.container = $container;
		var defaults = { 
		    name:names,// 资源文件名称
		    path:'i18n/',// 资源文件所在目录路径
		    mode:'both',// 模式：变量或 Map 
		    language:lang,// 对应的语言
		    cache:false, 
		    encoding: 'UTF-8', 
		    callback: function() {// 回调方法
		    	$("*[i18n]",$container).each(function(){
		    		 var $this = $(this);
		    		 var key =$this.attr('i18n');
		    		 var value = $.i18n.prop(key);
		    		 if($this.attr('placeholder')){
		    			 $this.attr('placeholder',value);
		    		 }else{
		    			 $this.html(value);
		    		 }
		    	});
		    	if(settings.custCallback){
	    			 settings.custCallback();
	    		}
		    } 
		 };
		settings = $.extend(defaults, settings);
		jQuery.i18n.properties(settings); 
	}
})(jQuery);

(function($){
	var ajaxParam={
		async:true,
		cache:false,
		dataType:'json',
		timeout:60000,
		type:'POST',
		url:'',
		shade:true,
		showErrorMsg:true,
		showSuccess:false
	};
	
	$.loy = $.extend({}, $.loy);
	$.loy.ajax = function(options){
		options = $.extend({}, ajaxParam,options);
		if(!options.url || options.url.length==0){
			$.loy.alert("url can't null ",'error');
			return;
		}
		
		if(options.dataType=='json'){
			var error = options.error;
			var success = options.success;
			options.error = function(XMLHttpRequest, textStatus, errorThrown){
				if(error){
					error.call();
				}
				if(textStatus == 'timeout'){
					$.loy.alert($.i18n.prop('request_timeout'));
				}
			};
			options.success = function(data, textStatus, XMLHttpRequest){
				if(options.dataType=='json'){
					if(XMLHttpRequest && XMLHttpRequest.statusText == ""){
						if(options.showErrorMsg){
							$.loy.alert($.i18n.prop('check_network'),'error');
							return;
						}  
				    }
					if(!data){
						if(error){
							error.call(this,data);
							if(options.showErrorMsg){
								$.loy.alert($.i18n.prop('data_format_error'),'error');
							}  
						}
				    }
					
					if(data == undefined ){
						if(options.shade){
						   //关闭遮罩
							closeMaskDiv();
					    }
						$.loy.alert($.i18n.prop('data_format_error'),'error');
						return;
					}	
					
					if(!data.success){
						if(data.errorCode && data.errorCode == 'not_login'){
							top.location.href ='index.html';
						}
					}
					
					if(!data.success){//业务上失败
						if(error){
							error.call(this);
						}
						if(data.msg && data.msg.length>0){
							if(options.showErrorMsg){
								$.loy.alert(data.msg,'error');	
							} 
						}else{
							if(options.showErrorMsg){
								$.loy.alert($.i18n.prop("system_error"),'error');	
							} 
						}
					}else{
						if(options.showSuccess){
							$.loy.showSysMsg($.i18n.prop("operate_success"));
						}
						if(success){
					       success.call(this,data);
				        }
					}
				}	
			};
			$.ajax(options);
		}	
	}
})(jQuery);