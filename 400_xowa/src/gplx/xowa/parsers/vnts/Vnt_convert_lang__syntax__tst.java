/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*; import gplx.xowa.langs.vnts.*; import gplx.xowa.langs.vnts.converts.*;
public class Vnt_convert_lang__syntax__tst {	// REF: https://www.mediawiki.org/wiki/Writing_systems/Syntax
	private final    Vnt_convert_lang_fxt fxt = new Vnt_convert_lang_fxt();
	@Test   public void Bidi() {
		String text = "-{zh-hans:a;zh-hant:b}-";
		fxt.Test_parse_many(text, "a", "zh-hans", "zh-cn", "zh-sg", "zh");
		fxt.Test_parse_many(text, "b", "zh-hant", "zh-hk", "zh-tw");
	}
	@Test   public void Undi() {
		String text = "-{H|cn_k=>zh-cn:cn_v}-cn_k";
		fxt.Test_parse_many(text, "cn_k", "zh", "zh-hans", "zh-hant", "zh-hk", "zh-my", "zh-mo", "zh-sg", "zh-tw");
		fxt.Test_parse_many(text, "cn_v", "zh-cn");
	}
	@Test   public void Raw() {
		fxt.Test_parse_many("-{a}-", "a", "zh-hans", "zh-cn", "zh-sg", "zh", "zh-hant", "zh-hk", "zh-tw");
		fxt.Test_parse_many("-{R|a}-", "a", "zh-hans", "zh-cn", "zh-sg", "zh", "zh-hant", "zh-hk", "zh-tw");
	}
	@Test   public void Hide() {
		String text = "-{H|zh-cn:cn;zh-hk:hk;zh-tw:tw}-cn hk tw";
		fxt.Test_parse_many(text, "cn cn cn", "zh-cn", "zh-sg");
		fxt.Test_parse_many(text, "hk hk hk", "zh-hk");
		fxt.Test_parse_many(text, "tw tw tw", "zh-tw");
		fxt.Test_parse_many(text, "cn hk tw", "zh", "zh-hans", "zh-hant");
	}
	@Test   public void Aout() {
		String text = "-{A|zh-cn:cn;zh-hk:hk;zh-tw:tw}- cn hk tw";
		fxt.Test_parse_many(text, "cn cn cn cn", "zh-cn", "zh-sg");
		fxt.Test_parse_many(text, "hk hk hk hk", "zh-hk");
		fxt.Test_parse_many(text, "tw tw tw tw", "zh-tw");
		fxt.Test_parse_many(text, "cn cn hk tw", "zh", "zh-hans");
		fxt.Test_parse_many(text, "tw cn hk tw", "zh-hant");
		fxt.Test_parse_many("h-{}-k", "hk", "zh-cn");	// semi-disabled
	}
	@Test   public void Del() {
		String text = "-{H|zh-cn:cn;zh-hk:hk;zh-tw:tw}-cn hk tw-{-|zh-cn:cn;zh-hk:hk;zh-tw:tw}- cn hk tw";
		fxt.Test_parse_many(text, "cn cn cn cn hk tw", "zh-cn", "zh-sg");
		fxt.Test_parse_many(text, "hk hk hk cn hk tw", "zh-hk");
		fxt.Test_parse_many(text, "tw tw tw cn hk tw", "zh-tw");
		fxt.Test_parse_many(text, "cn hk tw cn hk tw", "zh", "zh-hans", "zh-hant");
	}
	@Test   public void Title() {
		fxt.Test_parse_title("-{}-", null, "", "zh-cn");
		String text = "-{T|zh-cn:cn;zh-hk:hk;zh-tw:tw}-cn hk tw";
		fxt.Test_parse_title(text, "cn", "cn hk tw", "zh-cn");
		fxt.Test_parse_title(text, "cn", "cn hk tw", "zh-sg");
		fxt.Test_parse_title(text, "hk", "cn hk tw", "zh-hk");
		fxt.Test_parse_title(text, "tw", "cn hk tw", "zh-tw");
		fxt.Test_parse_title(text, "cn", "cn hk tw", "zh-hans");
		fxt.Test_parse_title(text, "tw", "cn hk tw", "zh-hant");
		fxt.Test_parse_title(text, null, "cn hk tw", "zh");
	}
	@Test   public void Descrip() {
		String text = "-{D|zh-cn:cn;zh-hk:hk;zh-tw:tw}-";
		fxt.Test_parse_many(text, "ZH-CN:cn;ZH-HK:hk;ZH-TW:tw;", "zh", "zh-hans", "zh-hant", "zh-cn", "zh-hk", "zh-my", "zh-mo", "zh-sg", "zh-tw");
	}
	@Test   public void Mixture() {
		String text = "-{H|zh-cn:cn;zh-hk:hk;zh-tw:tw}--{zh;zh-hans;zh-hant|cn hk tw}- -{zh;zh-cn;zh-hk;zh-tw|cn hk tw}-";
		fxt.Test_parse_many(text, "cn hk tw cn cn cn", "zh-cn", "zh-sg", "zh-hans");
		fxt.Test_parse_many(text, "cn hk tw hk hk hk", "zh-hk");
		fxt.Test_parse_many(text, "cn hk tw tw tw tw", "zh-tw", "zh-hant");
		fxt.Test_parse_many(text, "cn hk tw cn hk tw", "zh");
	}
	@Test   public void Descrip__undi() {fxt.Test_parse("-{D|cn_k=>zh-cn:cn_v;hk_k=>zh-hk:hk_v}-", "cn_k⇒ZH-CN:cn_v;hk_k⇒ZH-HK:hk_v;");}
	@Test   public void Descrip__mixd() {fxt.Test_parse("-{D|zh-tw:tw_v;cn_k=>zh-cn:cn_v;hk_k=>zh-hk:hk_v;zh-mo:mo_v}-", "ZH-TW:tw_v;ZH-MO:mo_v;cn_k⇒ZH-CN:cn_v;hk_k⇒ZH-HK:hk_v;");}
}
