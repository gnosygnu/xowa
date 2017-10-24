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
package gplx.xowa.xtns.proofreadPage; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import org.junit.*; import gplx.xowa.apps.cfgs.*;
public class Pp_pages_nde_hdr_tst {
	private final    Xop_fxt fxt = new Xop_fxt();
	@Before public void Init() {
		Io_mgr.Instance.InitEngine_mem();
		fxt.Wiki().Xtn_mgr().Xtn_proofread().Enabled_y_();
		fxt.Wiki().Cache_mgr().Page_cache().Free_mem(true);
		fxt.Wiki().Db_mgr().Load_mgr().Clear(); // must clear; otherwise fails b/c files get deleted, but wiki.data_mgr caches the Xowd_regy_mgr (the .reg file) in memory;
		fxt.Wiki().Ns_mgr().Add_new(Xowc_xtn_pages.Ns_page_id_default, "Page").Add_new(Xowc_xtn_pages.Ns_index_id_default, "Index").Init();
		fxt.Init_page_create("MediaWiki:Proofreadpage_header_template", String_.Concat
		( "{{#if:{{{value|}}}|value={{{value}}};|value=nil;}}"
		, "{{#if:{{{current|}}}|current={{{current}}};|}}"
		, "{{#if:{{{prev|}}}|prev={{{prev}}};|}}"
		, "{{#if:{{{next|}}}|next={{{next}}};|}}"
		, "{{#if:{{{from|}}}|from={{{from}}};|}}"
		, "{{#if:{{{to|}}}|to={{{to}}};|}}"
		, "{{#if:{{{custom|}}}|custom={{{custom}}};|}}"
		, "\n\n"
		));
	}
	@Test  public void Default_to_toc() {	// PURPOSE: default header to "toc" if no "from", "to", "include"; DATE:2014-01-27
		fxt.Init_page_create("Index:A", "");
		// only index supplied; add header='toc'
		fxt.Test_parse_page_wiki_str("<pages index='A'/>", String_.Concat_lines_nl
		(	"<p>value=toc;"
		,	"</p>"
		,	""
		,	"<p><br/>"
		,	"</p>"
		));

		fxt.Init_page_create("Page:A/1", "A/1");
		// from specified; don't add toc
		fxt.Test_parse_page_wiki_str("<pages index='A' from='1'/>", String_.Concat_lines_nl
		(	"<p>A/1&#32;"
		,	"</p>"
		));
	}
	@Test  public void From_set() {	// PURPOSE: "from" should (a) appear in toc; and (b) select pages; DATE:2014-01-27
		fxt.Init_page_create("Index:A" , "idx");
		fxt.Init_page_create("Page:A/1", "a1");
		fxt.Init_page_create("Page:A/2", "a2");
		fxt.Init_page_create("Page:A/3", "a3");
		fxt.Test_parse_page_wiki_str("<pages index='A' from=2 to=2 header='toc'/>", String_.Concat_lines_nl
		(	"<p>value=toc;from=2;to=2;"
		,	"</p>"
		,	""
		,	"<p>a2&#32;"
		,	"</p>"
		));
	}
	@Test  public void Mainspace_toc() {	// PURPOSE: Mainspace links should be sent to toc; DATE:2014-01-27
		fxt.Init_page_create("Index:A" , String_.Concat_lines_nl_skip_last
		( "[[Page/1]]"
		, "[[Page/2]]"
		, "[[Page/3]]"
		));
		// next only
		fxt.Page_ttl_("Page/1");
		fxt.Test_parse_page_wiki_str("<pages index='A' />", String_.Concat_lines_nl
		(	"<p>value=toc;current=<b>Page/1</b>;next=<a href=\"/wiki/Page/2\">Page/2</a>;"
		,	"</p>"
		,	""
		,	"<p><br/>"
		,	"</p>"
		));

		// next and prev
		fxt.Page_ttl_("Page/2");
		fxt.Test_parse_page_wiki_str("<pages index='A' />", String_.Concat_lines_nl
		(	"<p>value=toc;current=<b>Page/2</b>;prev=<a href=\"/wiki/Page/1\">Page/1</a>;next=<a href=\"/wiki/Page/3\">Page/3</a>;"
		,	"</p>"
		,	""
		,	"<p><br/>"
		,	"</p>"
		));

		// prev only
		fxt.Page_ttl_("Page/3");
		fxt.Test_parse_page_wiki_str("<pages index='A' />", String_.Concat_lines_nl
		(	"<p>value=toc;current=<b>Page/3</b>;prev=<a href=\"/wiki/Page/2\">Page/2</a>;"
		,	"</p>"
		,	""
		,	"<p><br/>"
		,	"</p>"
		));

		// override current only;
		fxt.Page_ttl_("Page/2");
		fxt.Test_parse_page_wiki_str("<pages index='A' current='custom_cur'/>", String_.Concat_lines_nl
		(	"<p>value=toc;current=custom_cur;prev=<a href=\"/wiki/Page/1\">Page/1</a>;next=<a href=\"/wiki/Page/3\">Page/3</a>;"
		,	"</p>"
		,	""
		,	"<p><br/>"
		,	"</p>"
		));

		// override current, prev, next
		fxt.Test_parse_page_wiki_str("<pages index='A' current='custom_cur' prev='custom_prv' next='custom_nxt'/>", String_.Concat_lines_nl
		(	"<p>value=toc;current=custom_cur;prev=custom_prv;next=custom_nxt;"
		,	"</p>"
		,	""
		,	"<p><br/>"
		,	"</p>"
		));
	}
	@Test  public void Mainspace_caption() {	// PURPOSE: extract caption; DATE:2014-01-27
		fxt.Init_page_create("Index:A" , String_.Concat_lines_nl_skip_last
		( "[[Page/1|Caption_1]]"
		, "[[Page/2]]"
		, "[[Page/3]]"
		));

		fxt.Page_ttl_("Page/2");
		fxt.Test_parse_page_wiki_str("<pages index='A' />", String_.Concat_lines_nl
		(	"<p>value=toc;current=<b>Page/2</b>;prev=<a href=\"/wiki/Page/1\">Caption_1</a>;next=<a href=\"/wiki/Page/3\">Page/3</a>;"
		,	"</p>"
		,	""
		,	"<p><br/>"
		,	"</p>"
		));
	}
	@Test  public void Xwiki() {	// PURPOSE: Mainspace links should be sent to toc; DATE:2014-01-27
		fxt.Init_xwiki_add_wiki_and_user_("commons", "commons.wikimedia.org");
		fxt.Init_page_create("Index:A" , String_.Concat_lines_nl_skip_last
		( "[[Page/1]]"
		, "[[:commons:File:A.png]]"
		));
		// next only
		fxt.Page_ttl_("Page/1");
		fxt.Test_parse_page_wiki_str("<pages index='A' />", String_.Concat_lines_nl
		(	"<p>value=toc;current=<b>Page/1</b>;next=<a href=\"/site/commons.wikimedia.org/wiki/File:A.png\">File:A.png</a>;"
		,	"</p>"
		,	""
		,	"<p><br/>"
		,	"</p>"
		));
	}
}
