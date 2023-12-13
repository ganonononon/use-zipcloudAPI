package com.example.external_api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.Getter;
import lombok.Setter;

@RestController
public class api {

	@GetMapping(value = "/address")
	public Result search(@RequestParam("zipcode") String zipcode) {
		RestTemplate restTemplate = new RestTemplate();
		String json = restTemplate.getForObject("https://zipcloud.ibsnet.co.jp/api/search?zipcode=" + zipcode,
				String.class);
		// 文字列を変換してJSON(Result)にする
		ObjectMapper mapper = new ObjectMapper();
    	Result result = null;
		try {
			result = mapper.readValue(json, Result.class);
		} catch (JsonProcessingException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return result;
	}

	@Getter
	@Setter
	public static class Result {
		private int status;
		private String message;
		private List<Results> results;

		@Getter
		@Setter
		public static class Results {
			private String zipcode;
			private String prefcode;
			private String address1;
			private String address2;
			private String address3;
			private String kana1;
			private String kana2;
			private String kana3;
		}
	}
}