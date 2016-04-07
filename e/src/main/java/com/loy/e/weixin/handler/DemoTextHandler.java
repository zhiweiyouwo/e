package com.loy.e.weixin.handler;

import java.util.Map;

import me.chanjar.weixin.common.session.WxSessionManager;
import me.chanjar.weixin.mp.api.WxMpMessageHandler;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.WxMpXmlMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage;
import me.chanjar.weixin.mp.bean.WxMpXmlOutNewsMessage.Item;

/**
 * Created by qianjia on 15/1/22.
 */
public class DemoTextHandler implements WxMpMessageHandler {
String aa ="<section class=\"wx96Diy\" data-source=\"bj.96weixin.com\"></section><section class=\"wx96Diy\" data-source=\"bj.96weixin.com\"><section class=\"96wxDiy\" style=\"clear: both; position: relative; width: 100%; margin: 0px auto; overflow: hidden;\"><section style=\"position: static; box-sizing: border-box;\"><section style=\"display: inline-block; vertical-align: top; width: 95%; box-sizing: border-box;\"><section class=\"96wx-bdrc\" style=\"border-right-width: 1px; border-right-style: solid; border-right-color: rgb(155, 187, 89); height: 1.2em; box-sizing: border-box;\"></section><section class=\"96wx-bdc\" style=\"border: 1px solid; color: rgb(155, 187, 89); font-size: 19.2px; display: inline-block; float: right; width: 1.33em; height: 1.3em; line-height: 1.3em; margin-right: -0.6em; border-radius: 100%; text-align: center; box-sizing: border-box; background-color: rgb(254, 255, 255);\"><p class=\"96wx-color\" style=\"box-sizing: border-box;display: inline-block;\">1</p></section><section class=\"96wx-bdrc\" style=\"border-right-width: 1px; border-right-style: solid; border-right-color: rgb(155, 187, 89); padding-bottom: 0.5em; box-sizing: border-box;\"><section style=\"vertical-align: middle; padding-top: 8px; padding-right: 15px; box-sizing: border-box;\"><section style=\"box-sizing: border-box;\"><section style=\"text-align: center; position: static; box-sizing: border-box;\"><section class=\"wx96Diy\" data-source=\"bj.96weixin.com\"><section class=\"96wxDiy\" style=\"display:block;clear:both;position:relative;width:100%;margin:0 auto;overflow:hidden;\"><section style=\" margin-top: 0.5em; margin-bottom: 0.5em; font-size: 1em; font-family: inherit; text-align: center; text-decoration: inherit; \"><section style=\"width: 100%; margin-bottom: -1.85em;\"><section class=\"96wx-bgc\" style=\"width: 100%; height: 0.3em; margin-top: 0.3em; background-color: rgb(9, 185, 34);\"></section><section class=\"96wx-bgc\" style=\"width: 100%; height: 0.3em; margin-top: 0.3em; background-color: rgb(9, 185, 34);\"></section><section class=\"96wx-bgc\" style=\"width: 100%; height: 0.3em; margin-top: 0.3em; background-color: rgb(9, 185, 34);\"></section></section><section class=\"96wx-bgc\" style=\"display: inline-block; vertical-align: bottom; line-height: 2.3em; padding-right: 15px; padding-left: 15px; background-color: rgb(9, 185, 34); min-height: 2.3em !important;\"><p style=\"color: rgb(255,255,255);font-size: 16px;-webkit-margin-before: 0em;  -webkit-margin-after: 0em;\">请输入标题</p></section></section></section></section></section></section><section style=\"position: static; box-sizing: border-box;\"><section style=\"text-align: right; padding-top: 5px; box-sizing: border-box;\"><p class=\"96wxDiy\" style=\"box-sizing: border-box;\">输入文字</p></section></section></section></section></section></section></section></section><p><br/></p>";
	public WxMpXmlOutMessage handle(WxMpXmlMessage wxMessage, Map<String, Object> context, WxMpService wxMpService, WxSessionManager sessionManager) {
		
		//WxMpXmlOutTextMessage m = WxMpXmlOutMessage.TEXT().content("<a  href=\"https://www.baidu.com\">hello</a>").fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
		WxMpXmlOutNewsMessage.Item item = new Item();
		item.setTitle("test");
		item.setDescription("hello world");
		item.setPicUrl("http://www.wuxiwang.net/uploads/allimg/140826/1-140R6154US19.jpg");
		WxMpXmlOutMessage m = WxMpXmlOutMessage.NEWS().addArticle(item).fromUser(wxMessage.getToUserName()).toUser(wxMessage.getFromUserName()).build();
		return m;
	}

}
