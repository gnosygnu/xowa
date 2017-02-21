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
public class Vnt_convert_lang__html__tst {	// REF: https://www.mediawiki.org/wiki/Writing_systems/Syntax
	private final    Vnt_convert_lang_fxt fxt = new Vnt_convert_lang_fxt();
	private String rule;
	@Before public void init() {
		rule = "-{H|zh-cn:cn;zh-hk:hk;zh-tw:tw}-";
		fxt.Clear();
	}
	@Test   public void Node() {
		fxt.Test_parse(rule + "hk<span>hk</span>hk", "cn<span>cn</span>cn");
	}
	@Test   public void Attribs() {
		fxt.Test_parse(rule + "<span class='hk'>hk</span>", "<span class='hk'>cn</span>");
	}
	@Test   public void Attribs__title() {
		fxt.Test_parse(rule + "<span title='hk'>hk</span>", "<span title='cn'>cn</span>");
	}
	@Test   public void Attribs__alt() {
		fxt.Test_parse(rule + "<span alt='hk'>hk</span>", "<span alt='cn'>cn</span>");
	}
	@Test   public void Attribs__alt_w_embedded_vnt() {	// PURPOSE: handle embedded variants inside attribute tags; PAGE:sr.n:Проглашени_победници_„Вики_воли_Земљу"  DATE:2015-10-13
		fxt.Test_parse(rule + "<img id='hk' alt='hk-{hk}-hk' src='hk'>", "<img id='hk' alt='cnhkcn' src='hk'>");
	}
	@Test   public void Attribs__skip_url() {
		fxt.Test_parse(rule + "<span alt='http://hk.org'>hk</span>", "<span alt='http://hk.org'>cn</span>");
	}
	@Test   public void Node__script() {
		fxt.Test_parse(rule + "hk<script>hk</script>hk", "cn<script>hk</script>cn");
	}
	@Test   public void Node__code() {
		fxt.Test_parse(rule + "hk<code>hk</code>hk", "cn<code>hk</code>cn");
	}
	@Test   public void Node__pre() {
		fxt.Test_parse(rule + "hk<pre>hk</pre>hk", "cn<pre>hk</pre>cn");
	}
	@Test   public void Node__pre__nested() {
		fxt.Test_parse(rule + "hk<pre><span>hk</span></pre>hk", "cn<pre><span>hk</span></pre>cn");
	}
	@Test   public void Recursive__deep() {
		fxt.Test_parse("a-{b-{c-{d}-e}-f}-g", "abcdefg");
	}
	@Test   public void Recursive__many() {
		fxt.Test_parse("a-{b-{c}-d-{e}-f}-g", "abcdefg");
	}
	@Test   public void Recursive__unclosed() {
		fxt.Test_parse("a-{b-{c", "a-{b-{c");
	}
	@Test   public void Recursive__unclosed_2() {
		fxt.Test_parse("a-{b-{c}-", "a-{bc");
	}
	@Test   public void Recursive__failed() {	// PURPOSE: handle out of bounds exception; PAGE:zh.w:重庆市 ;DATE:2015-10-01
		fxt.Test_parse("-{zh-cn:a-{b}-c; zh-tw:d-{e}-f; }-", "abc");
	}
	@Test   public void Unclosed() {
		fxt.Test_parse("a-{bc", "a-{bc");
	}
	@Test   public void Entity__body() {
		fxt.Test_parse("-{H|zh-cn:nbsp-cn;zh-hk:nbsp;}-" + "&nbsp;nbsp", "&nbsp;nbsp-cn");
	}
	@Test   public void Entity__atr() {
		fxt.Test_parse("-{H|zh-cn:nbsp-cn;zh-hk:nbsp;}-" + "<div title='nbsp&nbsp;nbsp'/>"	, "<div title='nbsp-cn&nbsp;nbsp-cn'/>");
	}
	@Test   public void Node__example() {
		fxt.Test_parse(rule + String_.Concat_lines_nl_skip_last
		( "{|"
		, "|-"
		, "|A<br />"
		, "|B<br/>"
		, "<span style=''>-{zh-hans:C;zh-hant:D;}-</span>"
		, "|}")
		, String_.Concat_lines_nl_skip_last
		( "{|"
		, "|-"
		, "|A<br />"
		, "|B<br/>"
		, "<span style=''>C</span>"
		, "|}"
		));
	}
	@Test   public void Attribs__title__w_vnt() {
		fxt.Init_cur("zh-tw");
		fxt.Test_parse("<span title=\"-{zh-cn:cn;zh-hant:hk;}-cn\" style=\"color:red;\">cn</span>", "<span title=\"hkcn\" style=\"color:red;\">cn</span>");	// cn not converted to hk
	}
}
