package com.loy.e.core.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 
 * 
 * @author Loy Fu qq群 540553957
 * @since 1.7
 * @version 1.0.0
 *
 */

@RequestMapping("weixin")
public class WeixinController {
	@RequestMapping(value = { "signature" }, method = RequestMethod.GET)
	public void signature(
			@RequestParam(value = "signature", required = true) String signature,
			@RequestParam(value = "timestamp", required = true) String timestamp,
			@RequestParam(value = "nonce", required = true) String nonce,
			@RequestParam(value = "echostr", required = true) String echostr,
			HttpServletResponse response) throws IOException {

		String[] values = { "WEIXIN_TOKEN", timestamp, nonce };
		Arrays.sort(values); // 字典序排序
		String value = values[0] + values[1] + values[2];
		String sign = DigestUtils.shaHex(value);
		PrintWriter writer = response.getWriter();
		if (signature.equals(sign)) {// 验证成功返回ehcostr
			writer.print(echostr);
		} else {
			writer.print("error");
		}
		writer.flush();
		writer.close();
	}
}
