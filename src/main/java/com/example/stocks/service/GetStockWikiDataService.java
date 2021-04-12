package com.example.stocks.service;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;


@Service
public class GetStockWikiDataService {

	public static final String GOOGLE_TRANSLATE_URL = "https://translate.google.com";

	public static final Logger LOG = LoggerFactory.getLogger(GetStockWikiDataService.class);

	public static final String GOOGLE_SEARCH_URL = "https://www.google.com/search";


	public String makeSearch(String ticker) throws IOException {

		String searchTerm = ticker + " stock";
		int num = 1;

		String searchURL = GOOGLE_SEARCH_URL + "?q=" + searchTerm + "&num=" + num;
		Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();

		LOG.info(doc.html());
		if (!doc.html().contains("AP7Wnd\">\n" +
				"          <div>\n" +
				"           <div class=\"BNeawe s3v9rd AP7Wnd\">")) {
			return "No info available";
		}
		else {
			String replyRaw = doc.html().substring(doc.html().indexOf("AP7Wnd\">\n" +
					"          <div>\n" +
					"           <div class=\"BNeawe s3v9rd AP7Wnd\">"), doc.html().indexOf("<span class=\"BNeawe\""));

			String reply = replyRaw.substring(replyRaw.indexOf("s3v9rd AP7Wnd\">") + 15);

			return reply.trim();
		}
	}


//	public String translate(String fromLang, String toLang, String text) throws IOException {
//
//		String textAdjustedToGoogleTranslate = text.replace(" ", "%20");
//
//		String searchURL = GOOGLE_TRANSLATE_URL + "/?sl=" + fromLang + "&tl=" + toLang + "&text=" +
//				textAdjustedToGoogleTranslate + "&op=translate";
//		Document doc = Jsoup.connect(searchURL).userAgent("Mozilla/5.0").get();
//
//		LOG.info(doc.html());
//
//		if (!doc.html().contains("jsname=\"W297wb\">")) {
//			return "No english";
//		}
//		else {
//			String replyRaw = doc.html().substring(doc.html().indexOf("jsname=\"W297wb\">"), doc.html().indexOf("</span>"));
//
//
//			String reply = replyRaw.substring(replyRaw.indexOf("jsname=\"W297wb\"") + 15);
//
//
//			LOG.info(reply.trim());
//			return reply.trim();
//		}
//	}

}
