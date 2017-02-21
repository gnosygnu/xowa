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
package gplx.xowa.addons.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
import org.junit.*; import gplx.core.tests.*;
public class Xoh_toc_wkr__txt__xnde__tst {
	@Before public void init() {fxt.Clear();} private final    Xoh_toc_wkr__txt__fxt fxt = new Xoh_toc_wkr__txt__fxt();
	@Test   public void I()				{fxt.Test__both("<i>a</i>"								, "a", "<i>a</i>");}
	@Test   public void I__id()			{fxt.Test__both("<i id='1'>a</i>"						, "a", "<i>a</i>");}
	@Test   public void B()				{fxt.Test__both("<b>a</b>"								, "a", "<b>a</b>");}
	@Test   public void Sup()			{fxt.Test__both("<sup>a</sup>"							, "a", "<sup>a</sup>");}
	@Test   public void Sub()			{fxt.Test__both("<sub>a</sub>"							, "a", "<sub>a</sub>");}
	@Test   public void Bdi()			{fxt.Test__both("<bdi>a</bdi>"							, "a", "<bdi>a</bdi>");}
	@Test   public void Span()			{fxt.Test__both("<span>a</span>"						, "a", "a");}
	@Test   public void Span__id()		{fxt.Test__both("<span id='1'>a</span>"					, "a", "a");}
	@Test   public void Span__dir()		{fxt.Test__both("<span dir=\"ltr\">a</span>"			, "a", "<span dir=\"ltr\">a</span>");}
	@Test   public void Span__dir_id()	{fxt.Test__both("<span id='1' dir=\"ltr\">a</span>"		, "a", "<span dir=\"ltr\">a</span>");}
	@Test   public void Small()			{fxt.Test__text("<small>a</small>"						, "a");}
	@Test   public void A()				{fxt.Test__both("<a href=\"/wiki/A\">b</a>"				, "b");}
	@Test   public void A__nest()		{fxt.Test__both("<a href=\"/wiki/A\">b<i>c</i>d</a>"	, "bcd", "b<i>c</i>d");}
	@Test   public void Br()			{fxt.Test__both("a<br/>b"								, "ab");}
	@Test   public void Br__dangling()	{fxt.Test__both("a<br>b"								, "ab");}
	@Test   public void Wbr__dangling()	{fxt.Test__both("a<wbr>b"								, "ab");}
	@Test   public void H2()			{fxt.Test__both("a<h2>b</h2>c"							, "abc");}	// NOTE: not a valid test; MW actually generates "ab" b/c of tidy; see corresponding edit test; DATE:2016-06-28
	@Test   public void Li()			{fxt.Test__text("a<ul><li>b</li></ul>c"					, "abc");}
	@Test   public void Table()			{fxt.Test__text("a<table><tr><td>b</td></tr></table>c"	, "abc");}
	@Test   public void Unknown__i()	{fxt.Test__both("a<unknown>b<i>c</i>d</unknown>e"		, "abcde", "a<unknown>b<i>c</i>d</unknown>e");}	// NOTE: technically, anch should be href_encoded a<unknown>b<i>c</i>d</unknown>e b/c <unknown> is not a valid tag; compare with known tags like <li> / <table> which are just stripped
	@Test   public void Unknown__a()	{fxt.Test__both("a<unknown>b<a>c</a>d</unknown>e"		, "abcde", "a<unknown>bcd</unknown>e");}
	@Test   public void Fail() {
		String html = "<i><a href='b'>c</i></a>";
		fxt.Init__tidy(html, "<i><a href='b'>c</a></i>");
		fxt.Test__both(html, "c", "<i>c</i>");
	}
}
