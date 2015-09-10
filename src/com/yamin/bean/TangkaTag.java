package com.yamin.bean;

import java.util.LinkedHashMap;
import java.util.Map;

public class TangkaTag {
	// public enum ThangkaTags {
	// /** 金刚手, 脑袋 */
	// TANG_2(0.4, 0.6,
	// "但最常见者为一面三臂三目，身黑蓝色，头戴五股骷髅冠，发赤上扬，须眉如火，獠牙露齿卷舌，三红目圆睁，十分怖畏，右手施期勉印，持金刚杵，左手忿怒拳印，持金刚钩绳当胸，以骨饰与蛇饰为庄严，蓝缎与虎皮为裙，双足右屈左伸，威立在莲花日轮座上，于般若烈焰中安住。"),
	// /** 无量寿佛, 巴热 */
	// TANG_3(0.5, 0.3, "一头二臂，身红色，盘发成髻，戴五佛宝冠，上穿天衣，下著绸裙，身佩珍宝璎珞"),
	// /** 四臂观音-阿 */
	// TANG_4(0.2, 0.8, "护法神"),
	// TANG_5(0.8, 0.8, "护法神");
	// private double x;
	// private double y;
	// private String desc;
	//
	// ThangkaTags(double x, double y, String desc) {
	// this.x = x;
	// this.y = y;
	// this.desc = desc;
	// }
	//
	// public double getX() {
	// return x;
	// }
	//
	// public double getY() {
	// return y;
	// }
	//
	// public String getDesc() {
	// return desc;
	// }
	//
	// public ThangkaTags getByName(String name) {
	// if (name == null || name.length() == 0)
	// return null;
	//
	// for (ThangkaTags tag : ThangkaTags.values()) {
	// Map<String, String> typesMap = new LinkedHashMap<String, String>();
	// if (name.equals(tag.name())) {
	// return tag;
	// }
	// }
	// return null;
	// }
	// }

	public enum ThangkaTags {
	    /** 无量寿佛, 巴热 */
	    TANG_3(0.5, 0.3, "一头二臂，身红色，盘发成髻，戴五佛宝冠，上穿天衣，下著绸裙，身佩珍宝璎珞"),
	    TANG_x(0.5, 0.6, "手中常常自然生出无尽宝藏、衣服饮食、珍妙华香、彩画宝盖、仪仗旗帜等装饰物品。"), // 手
	    /** 释尊 */
	    TANG_sjdf(0.3, 0.55, "手下伸、掌向上，名施无畏手，除一切众生怖畏"), // 右手
	    TANG_sdjfs(0.5, 0.45, "宝钵手，令身体安稳"), // 左手
	    /** 四臂观音-阿 */
	    TANG_y(0.25, 0.3, "右手持水晶念珠, 表每拨一珠即救度一众生出脱轮回"),
	    TANG_z(0.77, 0.43, "左手拈八瓣莲花与耳际齐，表清净无恼"),
	    TANG_safd(0.6, 0.3, "左肩穿戴有动物的皮毛(母鹿的皮毛)，象征伟大的慈心与悲心，也象征永远不再落入三界轮回之中，并且不进入个人解脱的涅盘。"), //左肩穿戴有动物的皮毛

	    /** 地藏菩萨 */
	    TANG_xdjf(0.45, 0.57, "右手持宝珠, 能得一切佛接引"),
	    TANG_xcj(0.7, 0.35, "左手持莲花，花上有如意宝幢"),

	    /** 弥勒菩萨 */
	    TANG_xx(0.75, 0.3, "青莲花手，为生十方净土"), // 左手莲花
	    /** 文殊菩萨 */
	    TANG_xyy(0.25, 0.25, "右手执金刚宝剑，断一切众生的烦恼，以无畏的狮子吼震醒沉迷的众生"),
	    TANG_usyf(0.7, 0.35, "左手持莲花上放置「般若经」，代表智慧的思维"),
	    TANG_sdkf(0.5, 0.8, "以莲花为台座，代表清净无染"),
	    /** 普贤菩萨 */
	    TANG_JI(0.6, 0.45, "象征着理德、行德"),
	    /** 虚空臧菩萨 */
	    TANG_sjlidf(0.5, 0.4, "左手置于腰侧，握拳持宝莲，宝莲上有如意宝珠"),
	    TANG_xc(0.5, 0.8, "宝莲华"), //坐

	    /** 金刚手 */
	    TANG_YU(0.55, 0.3, "发赤上扬，须眉如火，獠牙露齿卷舌，三红目圆睁，十分怖畏"),
	    TANG_yj(0.25, 0.25, "右手施期勉印，持金刚杵"),
	    TANG_ghj(0.5, 0.50, "左手忿怒拳印，持金刚钩绳当胸"),
	    TANG_hj(0.45, 0.7, "以骨饰与蛇饰为庄严，蓝缎与虎皮为裙，双足右屈左伸，威立在莲花日轮座上，于般若烈焰中安住"), // 肚子

	    /** 除盖障菩萨 */
	    TANG_kjh(0.53, 0.4, "左手持莲华，华上有如意珠，表示以菩提心中之如意珠满足一切众生的愿望"),
	    TANG_hjj(0.25, 0.6, "右手结无畏印"),

	    /**四大天王*/
	    /** 东-持国天王 */
	    TANG_ljskf(0.5, 0.5, "尊身为白色，穿甲胄"),
	    TANG_ghjs(0.4, 0.4, "手持宝慧琵琶, 有两义：一、弦乐器松紧要适中，太紧则易断，太松则声不响，表行中道之法[1]；二、是主乐神，表明他要用音乐来使众生皈依佛教"),
	    /** 北-多闻天王 */
	    TANG_ghj2(0.6, 0.40, "左手卧银鼠"),
	    TANG_sjkf(0.27, 0.15, "右持宝伞（或作宝幡）有两义：一：伞盖代表要保护自己的内心不受外面环境染污[1]；二、用以遮蔽世间，避免魔神危害，以护持人民财富"),
	    /** 南-增长天王 */
	    TANG_ghjsaf(0.48, 0.6, "身为青色，穿甲胄"), // 胸口部分
	    TANG_kljsfd(0.45, 0.45, "手握宝剑，有两义：一、宝剑象征智慧，慧剑斩烦恼[1]；二、为的是保护佛法，不受侵犯"),
	    /** 西-广目天王 */
	    TANG_skjfd(0.5, 0.6, "身为红色，穿甲胄"),
	    TANG_kjs(0.4, 0.45, "手缠一条龙或是蛇，有两义：一、表世间多变之意，二、是龙神的首领。另一手上拿着宝珠，表内心不变之意"),
	    /** 千手观音 */
	    TANG_sjkdf(0.8, 0.3, "1000只手，每个手掌心中一只眼，千手的排列犹如孔雀开屏似的排在观音身后"), // 左手的位置
	    TANG_sjkdfs(0.2, 0.33, "各持刀、枪、拂尘、伞、镜和净瓶等各种神通广大的法器"), // 右手法器
	    TANG_yuk6(0.75, 0.53, "手中各有一眼，实为四十二臂四十二眼"), //左手眼睛
	    /** 白莲花观音 */
	    TANG_sfj(0.75, 0.25, "手持法器"),
	    TANG_hsjf(0.3, 0.4, "佛经对于莲花的记载广泛, 莲花几乎可以和“佛”划上等号"),
	    TANG_sdf(0.2, 0.7, "白莲花手，成就种种功德"), // 左下边最大那个莲花
	    TANG_sfjd(0.7, 0.5, "数珠手，能得一切佛接引"), // 左手边那个珠子
	    /** 空行观音 */
	    TANG_jslkdf(0.4, 0.3, "左手持莲花，花上有如意宝幢"),
	    TANG_sdkfj(0.24, 0.6, "施无畏手，除一切众生怖畏");


	    private double x;
	    private double y;
	    private String desc;

	    ThangkaTags(double x, double y, String desc) {
	        this.x = x;
	        this.y = y;
	        this.desc = desc;
	    }

	    public double getX() {
	        return x;
	    }

	    public double getY() {
	        return y;
	    }

	    public String getDesc() {
	        return desc;
	    }

	    public ThangkaTags getByName(String name) {
	        if (name == null || name.length() == 0) return null;

	        for (ThangkaTags tag : ThangkaTags.values()) {
	            Map<String, String> typesMap = new LinkedHashMap<String, String>();
	            if (name.equals(tag.name())) {
	                return tag;
	            }
	        }
	        return null;
	    }
	}
}
