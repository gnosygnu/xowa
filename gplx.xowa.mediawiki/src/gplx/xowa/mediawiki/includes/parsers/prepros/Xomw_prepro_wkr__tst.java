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
package gplx.xowa.mediawiki.includes.parsers.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*; import gplx.xowa.mediawiki.includes.parsers.*;
import org.junit.*;
public class Xomw_prepro_wkr__tst {
	private final    Xomw_prepro_wkr__fxt fxt = new Xomw_prepro_wkr__fxt();
	@Test  public void Text() {
		fxt.Test__parse("abc", "<root>abc</root>");
	}
	@Test  public void Brack() {
		fxt.Test__parse("a[[b]]c", "<root>a[[b]]c</root>");
	}
	@Test  public void Brack__one() {	// COVERS: "Add literal brace(s)"
		fxt.Test__parse("a[b]c", "<root>a[b]c</root>");
	}
	@Test  public void Brack__max() {	// COVERS: "The specified maximum exists in the callback array, unless the caller"
		fxt.Test__parse("a[[[[[b]]]]]c", "<root>a[[[[[b]]]]]c</root>");
	}
	@Test  public void Template() {
		fxt.Test__parse("a{{b}}c", "<root>a<template><title>b</title></template>c</root>");
	}
	@Test  public void Template__args__idx() {
		fxt.Test__parse("a{{b|c|d}}e", "<root>a<template><title>b</title><part><name index=\"1\" /><value>c</value></part><part><name index=\"2\" /><value>d</value></part></template>e</root>");
	}
	@Test  public void Template__args__key() {
		fxt.Test__parse("a{{b|c=d}}e", "<root>a<template><title>b</title><part><name>c</name>=<value>d</value></part></template>e</root>");
	}
	@Test  public void Template__line_start() {	// COVERS: "The invocation is at the start of the line if lineStart is set in"
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "a"
		, "{{b}}"
		), String_.Concat_lines_nl_skip_last
		( "<root>a"
		, "<template lineStart=\"1\"><title>b</title></template></root>"
		));
	}		
	@Test  public void Template__max() {	// COVERS: "do we still qualify for any callback with remaining count?"
		fxt.Test__parse("a{{{{{b}}}}}c", "<root>a<template><title><tplarg><title>b</title></tplarg></title></template>c</root>");
	}
	@Test  public void Tplarg() {
		fxt.Test__parse("a{{{b}}}c", "<root>a<tplarg><title>b</title></tplarg>c</root>");
	}
	@Test  public void Tplarg__dflt() {
		fxt.Test__parse("a{{{b|c}}}d", "<root>a<tplarg><title>b</title><part><name index=\"1\" /><value>c</value></part></tplarg>d</root>");
	}
	@Test  public void Comment() {
		fxt.Test__parse("a<!--b-->c", "<root>a<comment>&lt;!--b--&gt;</comment>c</root>");
	}
	@Test  public void Comment__dangling() {// COVERS: "Unclosed comment in input, runs to end"
		fxt.Test__parse("a<!--b", "<root>a<comment>&lt;!--b</comment></root>");
	}
	@Test  public void Comment__ws() {		// COVERS: "Search backwards for leading whitespace"
		fxt.Test__parse("a <!--b--> c", "<root>a <comment>&lt;!--b--&gt;</comment> c</root>");	// NOTE: space is outside comment
	}
	@Test  public void Comment__many__ws() {// COVERS: "Dump all but the last comment to the accumulator"
		fxt.Test__parse("a <!--1--> <!--2--> z", "<root>a <comment>&lt;!--1--&gt;</comment> <comment>&lt;!--2--&gt;</comment> z</root>"); // NOTE: space is outside comment; 
	}
	@Test  public void Comment__nl__ws() {	// COVERS: "Eat the line if possible"
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "a"
		, " <!--1--> "
		, " <!--2--> "
		, "z"
		), String_.Concat_lines_nl_skip_last
		( "<root>a"
		, "<comment> &lt;!--1--&gt; "  // NOTE: space is inside </comment> if flanked by nl; 
		, "</comment><comment> &lt;!--2--&gt; "
		, "</comment>z</root>"
		));
	}
	@Test  public void Ext() {					// COVERS.ALSO: "Note that the attr element contains the whitespace between name and attribute," 
		fxt.Test__parse("a<pre id=\"1\">b</pre>c", "<root>a<ext><name>pre</name><attr> id=&quot;1&quot;</attr><inner>b</inner><close>&lt;/pre&gt;</close></ext>c</root>");
	}
	@Test  public void Ext__inline() {			// COVERS: "if ( $text[$tagEndPos - 1] == '/' ) {"
		fxt.Test__parse("a<pre/>b"   , "<root>a<ext><name>pre</name><attr></attr></ext>b</root>");
		fxt.Test__parse("a<pre />b"  , "<root>a<ext><name>pre</name><attr> </attr></ext>b</root>");
	}
	@Test  public void Ext__end__pass__space() {// COVERS: "\s*" in `preg_match( "/<\/" . preg_quote( $name, '/' ) . "\s*>/i",`
		fxt.Test__parse("a<pre>b</pre >c", "<root>a<ext><name>pre</name><attr></attr><inner>b</inner><close>&lt;/pre &gt;</close></ext>c</root>");
	}
	@Test  public void Ext__end__pass__name() { // COVERS: "\s*" in `preg_match( "/<\/" . preg_quote( $name, '/' ) . "\s*>/i",`
		fxt.Test__parse("a<pre>b</pro></pre>c", "<root>a<ext><name>pre</name><attr></attr><inner>b&lt;/pro&gt;</inner><close>&lt;/pre&gt;</close></ext>c</root>");
	}
	@Test  public void Ext__end__fail__angle() {// COVERS: "\s*" in `preg_match( "/<\/" . preg_quote( $name, '/' ) . "\s*>/i",`
		fxt.Test__parse("a<pre>b</pre c", "<root>a&lt;pre&gt;b&lt;/pre c</root>");
	}
	@Test  public void Ext__dangling() {		// COVERS: "Let it run out to the end of the text."
		fxt.Test__parse("a<pre>bc", "<root>a&lt;pre&gt;bc</root>");
	}
	@Test  public void Ext__dangling__many() {	// COVERS: "Cache results, otherwise we have O(N^2) performance for input like <foo><foo><foo>..."
		fxt.Test__parse("a<pre><pre><pre>bc", "<root>a&lt;pre&gt;&lt;pre&gt;&lt;pre&gt;bc</root>");
	}
	@Test  public void Ext__unclosed() {		// COVERS: "Infinite backtrack"
		fxt.Test__parse("a<pre bcd", "<root>a&lt;pre bcd</root>");
	}		
	@Test  public void Ext__noinclude() {	    // COVERS: "<includeonly> and <noinclude> just become <ignore> tags"
		fxt.Init__for_inclusion_(Bool_.N);
		fxt.Test__parse("a<includeonly>b<noinclude>c</noinclude>d</includeonly>e", "<root>a<ignore>&lt;includeonly&gt;b&lt;noinclude&gt;c&lt;/noinclude&gt;d&lt;/includeonly&gt;</ignore>e</root>");
	}
	@Test  public void Heading() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "a"
		, "== b1 =="
		, "z"
		), String_.Concat_lines_nl_skip_last
		( "<root>a"
		, "<h level=\"2\" i=\"1\">== b1 ==</h>"
		, "z</root>"
		));
	}
	@Test  public void Heading__eos__no_nl() {
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "a"
		, "== b1 =="
		), String_.Concat_lines_nl_skip_last
		( "<root>a"
		, "<h level=\"2\" i=\"1\">== b1 ==</h></root>"
		));
	}
	@Test  public void Heading__bos__implied_nl() {  // COVERS: "Is this the start of a heading?"
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "== b1 =="
		, "z"
		), String_.Concat_lines_nl_skip_last
		( "<root><h level=\"2\" i=\"1\">== b1 ==</h>"
		, "z</root>"
		));
	}
	@Test  public void Heading__dwim__y() {	// COVERS: "DWIM: This looks kind of like a name/value separator."
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "a{{b|"
		, "=c="
		, "}}d"
		), String_.Concat_lines_nl_skip_last
		( "<root>a<template><title>b</title><part><name>"
		, "</name>=<value>c="
		, "</value></part></template>d</root>"
		));
	}
	@Test  public void Heading__dwim__n() {	// COVERS: "DWIM: This looks kind of like a name/value separator."
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "a{{b|"
		, "==c=="
		, "}}d"
		), String_.Concat_lines_nl_skip_last
		( "<root>a<template><title>b</title><part><name index=\"1\" /><value>"
		, "<h level=\"2\" i=\"1\">==c==</h>"
		, "</value></part></template>d</root>"
		));
	}
	@Test  public void Heading__comment() {	// COVERS: "Comment found at line end"
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "a"
		, "==b== <!--c-->"
		, ""
		), String_.Concat_lines_nl_skip_last
		( "<root>a"
		, "<h level=\"2\" i=\"1\">==b== <comment>&lt;!--c--&gt;</comment></h>"
		, "</root>"
		));
	}
	@Test  public void Heading__consecutive__5() {	// COVERS: "This is just a single String of equals signs on its own line"
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "a"
		, "====="
		, ""
		), String_.Concat_lines_nl_skip_last
		( "<root>a"
		, "<h level=\"2\" i=\"1\">=====</h>"
		, "</root>"
		));
	}
	@Test  public void Heading__consecutive__1() {	// COVERS: "Single equals sign on its own line, count=0"
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "a"
		, "="
		, ""
		), String_.Concat_lines_nl_skip_last
		( "<root>a"
		, "="
		, "</root>"
		));
	}
	@Test  public void Heading__unclosed() {	// COVERS: "No match, no <h>, just pass down the inner src"
		fxt.Test__parse(String_.Concat_lines_nl_skip_last
		( "a"
		, "===b"
		, ""
		), String_.Concat_lines_nl_skip_last
		( "<root>a"
		, "===b"
		, "</root>"
		));
	}
	@Test  public void Inclusion__n() {
		fxt.Init__for_inclusion_(Bool_.N);
		fxt.Test__parse("a<onlyinclude>b</onlyinclude>c", "<root>a<ignore>&lt;onlyinclude&gt;</ignore>b<ignore>&lt;/onlyinclude&gt;</ignore>c</root>");
	}
	@Test  public void Inclusion__y() {
		fxt.Init__for_inclusion_(Bool_.Y);
		fxt.Test__parse("a<onlyinclude>b</onlyinclude>c", "<root><ignore>a&lt;onlyinclude&gt;</ignore>b<ignore>&lt;/onlyinclude&gt;c</ignore></root>");
	}
	@Test  public void Ignored__noinclude() {	// COVERS: "Handle ignored tags"
		fxt.Init__for_inclusion_(Bool_.N);
		fxt.Test__parse("a<noinclude>b</noinclude>c", "<root>a<ignore>&lt;noinclude&gt;</ignore>b<ignore>&lt;/noinclude&gt;</ignore>c</root>");
	}
}
class Xomw_prepro_wkr__fxt {
	private final    Xomw_prepro_wkr wkr = new Xomw_prepro_wkr();
	private boolean for_inclusion = false;
	public Xomw_prepro_wkr__fxt() {
		wkr.Init_by_wiki("pre");
	}
	public void Init__for_inclusion_(boolean v) {for_inclusion = v;}
	public void Test__parse(String src_str, String expd) {
		byte[] src_bry = Bry_.new_u8(src_str);
		byte[] actl = wkr.Preprocess_to_xml(src_bry, for_inclusion);
		Tfds.Eq_str_lines(expd, String_.new_u8(actl), src_str);
	}
}
