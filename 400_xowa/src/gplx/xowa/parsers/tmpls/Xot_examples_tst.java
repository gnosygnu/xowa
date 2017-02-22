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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import org.junit.*;
public class Xot_examples_tst {
	private final Xop_fxt fxt = new Xop_fxt();
	@Before public void init() {
		Io_mgr.Instance.InitEngine_mem();
		fxt.Reset();
	}
	@Test  public void Arg_0()					{Init_tmpl_for(); fxt.Test_parse_tmpl_str("{{For}}"				, "For other uses, see [[Test page (disambiguation)]].");}
	@Test  public void Arg_1()					{Init_tmpl_for(); fxt.Test_parse_tmpl_str("{{For|a}}"			, "For a, see [[Test page (disambiguation)]].");}
	@Test  public void Arg_2()					{Init_tmpl_for(); fxt.Test_parse_tmpl_str("{{For|a|b}}"			, "For a, see [[b]].");}
	@Test  public void Arg_3()					{Init_tmpl_for(); fxt.Test_parse_tmpl_str("{{For|a|b|c}}"		, "For a, see [[b]]&#32;and [[c]].");}
	@Test  public void Arg_4()					{Init_tmpl_for(); fxt.Test_parse_tmpl_str("{{For|a|b|c|d}}"		, "For a, see [[b]], [[c]], and [[d]].");}
	@Test  public void Arg_5()					{Init_tmpl_for(); fxt.Test_parse_tmpl_str("{{For|a|b|c|d|e}}"	, "For a, see [[b]], [[c]], [[d]], and [[e]].");}
	@Test  public void Arg_1_nil()				{Init_tmpl_for(); fxt.Test_parse_tmpl_str("{{For||a|b}}"			, "For other uses, see [[a]]&#32;and [[b]].");}
	@Test  public void Main() {
		Init_tmpl_main(); fxt.Test_parse_tmpl_str("{{Main|a}}", "Main article: [[a|a]]");
	}
	@Test  public void About() {
		Init_tmpl_about(); fxt.Test_parse_tmpl_str("{{About|abc}}", "This article is about abc.&#32;&#32;For other uses, see [[Test page (disambiguation)]].");
	}
	@Test  public void About_2() {	// PAGE:en.w:{{About|the NASA space mission||Messenger (disambiguation)}}
		Init_tmpl_about(); fxt.Test_parse_tmpl_str("{{About|a||b{{!}}c}}", "This article is about a.&#32;&#32;For other uses, see [[b|c]].");
	}
	@Test  public void OtherUses() {
		Init_tmpl_other_uses(); fxt.Test_parse_tmpl_str("{{Other uses|abc}}", "For other uses, see [[abc]].");
	}
	@Test  public void SeeAlso() {
		Init_tmpl_see_also(); fxt.Test_parse_tmpl_str("{{See also|abc}}", "See also: [[abc]]");
	}
	@Test  public void Redirect() {
		Init_tmpl_redirect(); fxt.Test_parse_tmpl_str("{{Redirect|abc}}", "\"abc\" redirects here. For other uses, see [[abc (disambiguation)]].");
	}
	private void Init_tmpl_main() {
		fxt.Init_page_create("Template:Main", String_.Concat_lines_nl
		(	"{{#ifeq:{{SUBJECTSPACE}}|Category|The main {{#ifeq:{{NAMESPACE:{{{1}}}}}||article|page}}{{#if:{{{2|}}}|s}} for this [[Wikipedia:Categorization|category]] {{#if:{{{2|}}}|are|is}}|Main {{#ifeq:{{NAMESPACE:{{{1}}}}}||article|page}}{{#if:{{{2|}}}|s}}:}} [[{{{1|{{PAGENAME}}}}}|{{{l1|{{{1|{{PAGENAME}}}}}}}}]]{{#if:{{{2| }}}"
		,	" |{{#if:{{{3|}}}|,&#32;|&#32;and&#32;}}[[{{{2}}}|{{{l2|{{{2}}}}}}]]}}{{#if:{{{3|}}}"
		,	" |{{#if:{{{4|}}}|,&#32;|,&#32;and&#32;}}[[{{{3}}}|{{{l3|{{{3}}}}}}]]}}{{#if:{{{4|}}}"
		,	" |{{#if:{{{5|}}}|,&#32;|,&#32;and&#32;}}[[{{{4}}}|{{{l4|{{{4}}}}}}]]}}{{#if:{{{5|}}}"
		,	" |{{#if:{{{6|}}}|,&#32;|,&#32;and&#32;}}[[{{{5}}}|{{{l5|{{{5}}}}}}]]}}{{#if:{{{6|}}}"
		,	" |{{#if:{{{7|}}}|,&#32;|,&#32;and&#32;}}[[{{{6}}}|{{{l6|{{{6}}}}}}]]}}{{#if:{{{7|}}}"
		,	" |{{#if:{{{8|}}}|,&#32;|,&#32;and&#32;}}[[{{{7}}}|{{{l7|{{{7}}}}}}]]}}{{#if:{{{8|}}}"
		,	" |{{#if:{{{9|}}}|,&#32;|,&#32;and&#32;}}[[{{{8}}}|{{{l8|{{{8}}}}}}]]}}{{#if:{{{9|}}}"
		,	" |{{#if:{{{10|}}}|,&#32;|,&#32;and&#32;}}[[{{{9}}}|{{{l9|{{{9}}}}}}]]}}{{#if:{{{10|}}}"
		,	" |, and [[{{{10}}}|{{{l10|{{{10}}}}}}]]}}{{#if:{{{11| }}}|&#32; (too many parameters in &#123;&#123;[[Template:main|main]]&#125;&#125;)}}"
		));
	}
	private void Init_tmpl_for() {
		fxt.Init_page_create("Template:For", "For {{#if:{{{1|}}}|{{{1}}}|other uses}}, see [[{{{2|{{PAGENAME}} (disambiguation)}}}]]{{#if:{{{3|}}}|{{#if:{{{4|}}}|, [[{{{3}}}]], {{#if:{{{5|}}}|[[{{{4}}}]], and [[{{{5}}}]]|and [[{{{4}}}]]}}|&#32;and [[{{{3}}}]]}}}}.");
	}
	private void Init_tmpl_other_uses() {
		Init_tmpl_about();
		fxt.Init_page_create("Template:Other uses", "{{#if:{{{2|}}}|{{about|||{{{1}}}|and|{{{2|}}}|_nocat=1}}|{{about|||{{{1|{{PAGENAME}} (disambiguation)}}}|_nocat=1}}}}");
	}
	private void Init_tmpl_about() {
		fxt.Init_page_create("Template:!", "|");
		fxt.Init_page_create("Template:About", String_.Concat_lines_nl
		(	"{{#if: {{{1|}}}|<!--"
		,	"	-->This {{#ifeq:{{NAMESPACE}}|{{ns:0}}|article|page}} is about {{{1}}}.&#32;&#32;}}<!--"
		,	"-->For {{#if:{{{2|}}}|{{{2}}}|other uses}}, see {{#if:{{{3|}}}|[[{{{3}}}]]{{#ifeq:{{{4|}}}|and|&#32;and {{#if:{{{5|}}}|[[{{{5}}}]]|[[{{PAGENAME}} (disambiguation)]]}}}}|[[{{PAGENAME}} (disambiguation)]]}}.<!--"
		,	"-->{{#if:{{{2|}}}|{{#if:{{{4|}}}|<!--"
		,	"  -->{{#ifeq:{{{4|}}}|and||<!-- 'and' is a special word, don't output 'For and, ...'"
		,	"    -->&#32;&#32;For {{#ifeq:{{{4}}}|1|other uses|{{{4}}}}}, see {{#if:{{{5|}}}|[[{{{5}}}]]{{#ifeq:{{{6|}}}|and|&#32;and {{#if:{{{7|}}}|[[{{{7}}}]]|[[{{PAGENAME}} (disambiguation)]]}}}}|[[{{PAGENAME}} (disambiguation)]]}}.}}<!--"
		,	"  -->{{#if:{{{6|}}}|<!--"
		,	"    -->{{#ifeq:{{{6|}}}|and||<!--"
		,	"      -->&#32;&#32;For {{#ifeq:{{{6}}}|1|other uses|{{{6}}}}}, see {{#if:{{{7|}}}|[[{{{7}}}]]{{#ifeq:{{{8|}}}|and|&#32;and {{#if:{{{9|}}}|[[{{{9}}}]]|[[{{PAGENAME}} (disambiguation)]]}}}}|[[{{PAGENAME}} (disambiguation)]]}}.}}<!--"
		,	"    -->{{#if:{{{8|}}}|<!--"
		,	"      -->{{#ifeq:{{{8|}}}|and||<!--"
		,	"        -->&#32;&#32;For {{#ifeq:{{{8}}}|1|other uses|{{{8}}}}}, see {{#if:{{{9|}}}|[[{{{9}}}]]|[[{{PAGENAME}} (disambiguation)]]}}.}}<!--"
		,	"    -->}}<!--"
		,	"  -->}}<!--"
		,	"-->}}}}<!--"
		,	"-->{{#if:{{{_nocat|}}}||{{#if:{{{1|}}}{{{2|}}}||{{#if:{{{3|}}}|[[Category:Hatnote templates using unusual parameters|A{{PAGENAME}}]]}}}}{{#ifeq:{{str left|{{{1}}}|3}}|is |[[Category:Hatnote templates using unusual parameters|B{{PAGENAME}}]]}}}}"
		));
	}
	private void Init_tmpl_see_also() {
		fxt.Init_page_create("Template:See also", String_.Concat_lines_nl
		(	"See also: {{#if:{{{1<includeonly>|</includeonly>}}} |<!--then:-->[[{{{1}}}{{#if:{{{label 1|{{{l1|}}}}}}|{{!}}{{{label 1|{{{l1}}}}}}}}]] |<!--else:-->'''Error: [[Template:See also|Template must be given at least one article name]]'''"
		,	"}}{{#if:{{{2|}}}|{{#if:{{{3|}}}|, |&nbsp;and }} [[{{{2}}}{{#if:{{{label 2|{{{l2|}}}}}}|{{!}}{{{label 2|{{{l2}}}}}}}}]]"
		,	"}}{{#if:{{{3|}}}|{{#if:{{{4|}}}|, |,&nbsp;and }} [[{{{3}}}{{#if:{{{label 3|{{{l3|}}}}}}|{{!}}{{{label 3|{{{l3}}}}}}}}]]"
		,	"}}{{#if:{{{4|}}}|{{#if:{{{5|}}}|, |,&nbsp;and }} [[{{{4}}}{{#if:{{{label 4|{{{l4|}}}}}}|{{!}}{{{label 4|{{{l4}}}}}}}}]]"
		,	"}}{{#if:{{{5|}}}|{{#if:{{{6|}}}|, |,&nbsp;and }} [[{{{5}}}{{#if:{{{label 5|{{{l5|}}}}}}|{{!}}{{{label 5|{{{l5}}}}}}}}]]"
		,	"}}{{#if:{{{6|}}}|{{#if:{{{7|}}}|, |,&nbsp;and }} [[{{{6}}}{{#if:{{{label 6|{{{l6|}}}}}}|{{!}}{{{label 6|{{{l6}}}}}}}}]]"
		,	"}}{{#if:{{{7|}}}|{{#if:{{{8|}}}|, |,&nbsp;and }} [[{{{7}}}{{#if:{{{label 7|{{{l7|}}}}}}|{{!}}{{{label 7|{{{l7}}}}}}}}]]"
		,	"}}{{#if:{{{8|}}}|{{#if:{{{9|}}}|, |,&nbsp;and }} [[{{{8}}}{{#if:{{{label 8|{{{l8|}}}}}}|{{!}}{{{label 8|{{{l8}}}}}}}}]]"
		,	"}}{{#if:{{{9|}}}|{{#if:{{{10|}}}|, |,&nbsp;and }} [[{{{9}}}{{#if:{{{label 9|{{{l9|}}}}}}|{{!}}{{{label 9|{{{l9}}}}}}}}]]"
		,	"}}{{#if:{{{10|}}}|{{#if:{{{11|}}}|, |,&nbsp;and }} [[{{{10}}}{{#if:{{{label 10|{{{l10|}}}}}}|{{!}}{{{label 10|{{{l10}}}}}}}}]]"
		,	"}}{{#if:{{{11|}}}|{{#if:{{{12|}}}|, |,&nbsp;and }} [[{{{11}}}{{#if:{{{label 11|{{{l11|}}}}}}|{{!}}{{{label 11|{{{l11}}}}}}}}]]"
		,	"}}{{#if:{{{12|}}}|{{#if:{{{13|}}}|, |,&nbsp;and }} [[{{{12}}}{{#if:{{{label 12|{{{l12|}}}}}}|{{!}}{{{label 12|{{{l12}}}}}}}}]]"
		,	"}}{{#if:{{{13|}}}|{{#if:{{{14|}}}|, |,&nbsp;and }} [[{{{13}}}{{#if:{{{label 13|{{{l13|}}}}}}|{{!}}{{{label 13|{{{l13}}}}}}}}]]"
		,	"}}{{#if:{{{14|}}}|{{#if:{{{15|}}}|, |,&nbsp;and }} [[{{{14}}}{{#if:{{{label 14|{{{l14||}}}}}}|{{!}}{{{label 14|{{{l14}}}}}}}}]]"
		,	"}}{{#if:{{{15|}}}|,&nbsp;and [[{{{15}}}{{#if:{{{label 15|{{{l15}}}}}}|{{!}}{{{label 15|{{{l15}}}}}}}}]]"
		,	"}}{{#if:{{{16|}}}| &mdash; '''<br/>Error: [[Template:See also|Too many links specified (maximum is 15)]]'''"
		,	"}}"
		));
	}
	private void Init_tmpl_redirect() {
		fxt.Init_page_create("Template:Redirect", String_.Concat_lines_nl
		(	"\"{{{1}}}\" redirects here. For {{#if:{{{2|}}}|{{{2}}}|other uses}}, see {{#if:{{{3|}}}|[[{{{3}}}]]{{#ifeq:{{{4|}}}|and|&#32;and {{#if:{{{5|}}}|[[{{{5}}}]]|[[{{{1}}} (disambiguation)]]}}}}|[[{{{1}}} (disambiguation)]]}}.<!--"
		,	"-->{{#if:{{{2|}}}|{{#if:{{{4|}}}|<!--"
		,	"  -->{{#ifeq:{{{4|}}}|and||<!-- \"and\" is a special word, don't output \"For and, ...\""
		,	"    -->&#32;&#32;For {{#ifeq:{{{4}}}|1|other uses|{{{4}}}}}, see {{#if:{{{5|}}}|[[{{{5}}}]]{{#ifeq:{{{6|}}}|and|&#32;and {{#if:{{{7|}}}|[[{{{7}}}]]|[[{{{4}}} (disambiguation)]]}}}}|[[{{{4}}} (disambiguation)]]}}.}}<!--"
		,	"  -->{{#if:{{{6|}}}|<!--"
		,	"    -->{{#ifeq:{{{6|}}}|and||<!--"
		,	"      -->&#32;&#32;For {{#ifeq:{{{6}}}|1|other uses|{{{6}}}}}, see {{#if:{{{7|}}}|[[{{{7}}}]]{{#ifeq:{{{8|}}}|and|&#32;and {{#if:{{{9|}}}|[[{{{9}}}]]|[[{{{6}}} (disambiguation)]]}}}}|[[{{{6}}} (disambiguation)]]}}.}}<!--"
		,	"    -->{{#if:{{{8|}}}|<!--"
		,	"      -->{{#ifeq:{{{8|}}}|and||<!--"
		,	"        -->&#32;&#32;For {{#ifeq:{{{8}}}|1|other uses|{{{8}}}}}, see {{#if:{{{9|}}}|[[{{{9}}}]]|[[{{{8}}} (disambiguation)]]}}.}}<!--"
		,	"    -->}}<!--"
		,	"  -->}}<!--"
		,	"-->}}}}"
		));
	}
}
