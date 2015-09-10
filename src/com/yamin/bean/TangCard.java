package com.yamin.bean;

import java.io.File;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.xmlpull.v1.XmlPullParser;

import android.util.Xml;

public class TangCard implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public final static String CARD_TYPE_FO = "fo";
	public final static String CARD_TYPE_GUANYIN = "gy";
	public final static String CARD_TYPE_PUSA = "bdps";
	public final static String CARD_TYPE_TIANWANG = "wdtw";
	public final static String CARD_TYPE_ALL = "all";
	
	private String name;
	private String protrait;
	private String background;
	private String chinese;
	private String english;
	private String tagName;
	
	public String getName() {
		return name;
	}

	public TangCard setName(String name) {
		this.name = name;
		return this;
	}

	public String getProtrait() {
		return protrait;
	}

	public void setProtrait(String protrait) {
		this.protrait = protrait;
	}

	public String getChinese() {
		return chinese;
	}

	public void setChinese(String chinese) {
		this.chinese = chinese;
	}

	public String getEnglish() {
		return english;
	}

	public void setEnglish(String english) {
		this.english = english;
	}

	public String getBackground() {
		return background;
	}

	public void setBackground(String background) {
		this.background = background;
	}
	
	public String getTagName() {
		return tagName;
	}

	public void setTagName(String tagName) {
		this.tagName = tagName;
	}

	public static List<TangCard> parseSet(InputStream inputStream, String type, boolean baseInfo) throws Exception {
		List<TangCard> cards = new ArrayList<TangCard>();
		XmlPullParser xmlPullParser = Xml.newPullParser();
		xmlPullParser.setInput(inputStream, "UTF-8");
		int evtType = xmlPullParser.getEventType();
		boolean found = false;
		TangCard card = null;
		String prefix = null;
		while (evtType != XmlPullParser.END_DOCUMENT) {
			String tag = xmlPullParser.getName();
			switch (evtType) {
			case XmlPullParser.START_TAG:
				if (tag.equals("type")) {
					prefix = xmlPullParser.nextText();
					if (prefix.equals(type) || type.equals(CARD_TYPE_ALL)) {
						found = true;
					}
				} else if (found) {
					if (tag.equals("member")) {
						
					} else if (cards != null) {
						if (tag.equals("item")) {
							card = new TangCard();
						} else if (card != null) {
							if (tag.equals("name")) {
								card.name = xmlPullParser.nextText();
							} else if (tag.equals("protrait")) {
								card.protrait = prefix + File.separator + xmlPullParser.nextText();
							} else if (tag.equals("tagName")) {
								card.tagName = xmlPullParser.nextText();
							} else if (tag.equals("chinese") && !baseInfo) {
								card.chinese = xmlPullParser.nextText();
							} else if (tag.equals("english") && !baseInfo) {
								card.english = xmlPullParser.nextText();
							} else if (tag.equals("background")) {
								card.background = prefix + File.separator + xmlPullParser.nextText();
							}
						}
					}
				}
				break;
			case XmlPullParser.END_TAG:
				if (found) {
					if (tag.equals("member") && !type.equals(CARD_TYPE_ALL)) {
						found = false;
					} else if (tag.equals("item")) {
						cards.add(card);
						card = null;
					}
				}
				
				break;
			}
			evtType = xmlPullParser.next();
		}
		
		return cards;
	
	}

}
