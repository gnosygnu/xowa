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
package gplx.xowa.bldrs.wiki_cfgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import org.junit.*; import gplx.core.strings.*;
import gplx.langs.xmls.*;
import gplx.xowa.addons.bldrs.app_cfgs.*;
public class Xoi_wiki_props_api_tst {
	private Xoi_wiki_props_fxt fxt = new Xoi_wiki_props_fxt();
	@Before public void init() {} // private Xob_subpage_tst_fxt fxt = new] Xob_subpage_tst_fxt();
	@Test   public void Parse() {
		fxt.Test_parse(String_.Concat_lines_nl
		(	"<api>"
		,	"<query>"
		,	"	<namespacealiases>"
		,	"		<ns id=\"4\" xml:space=\"preserve\">WP</ns>"
		,	"		<ns id=\"5\" xml:space=\"preserve\">WT</ns>"
		,	"	</namespacealiases>"
		,	"	<namespaces>"
		,	"		<ns id=\"0\" case=\"first-letter\" content=\"\" xml:space=\"preserve\"/>"
		,	"		<ns id=\"1\" case=\"first-letter\" subpages=\"\" canonical=\"Talk\" xml:space=\"preserve\">Talk</ns>"
		,	"	</namespaces>"
		,	"</query>"
		,	"</api>"
		), fxt.wiki_()
		.Alias_ary_(fxt.alias_(4, "WP"), fxt.alias_(5, "WT"))
		.Ns_ary_(fxt.ns_(0, false), fxt.ns_(1, true))
		);
	}
//		@Test   public void Build() {
//			fxt.Test_build(fxt.wiki_("enwiki")
//				.Alias_ary_(fxt.alias_(4, "WP"), fxt.alias_(5, "WT"))
//				.Ns_ary_(fxt.ns_(0, false), fxt.ns_(1, true))
//				, "");
//		}
//		Tfds.Eq_str_lines(Query_ns(protocol, gplx.core.ios.IoEngine_.MemKey, wikis), String_.Concat_lines_nl
//		(	"app.bldr.wiki_cfg_bldr.get('en.wikipedia.org').new_cmd_('wiki.ns_mgr.aliases', 'ns_mgr.add_alias_bulk(\""
//		,	"4|WP"
//		,	"5|WT"
//		,	"6|Image"
//		,	"7|Image_talk"
//		,	"\");');"
//			fxt.Test_parse(String_.Concat_lines_nl
//			(	"'wgNamespacesWithSubpages' => array"
//			,	"( 'default' => array(2 => 1)"
//			,	", 'enwiki'  => array(0 => 1)"
//			,	")"
//			));
}
class Xoi_wiki_props_fxt {
	private Xoi_wiki_props_api api = new Xoi_wiki_props_api();
	private Bry_bfr bfr = Bry_bfr_.New();
	public Xoi_wiki_props_wiki wiki_() {return wiki_("domain_doesnt_matter");}
	public Xoi_wiki_props_wiki wiki_(String wiki_domain) {return new Xoi_wiki_props_wiki().Wiki_domain_(Bry_.new_a7(wiki_domain));}
	public Xoi_wiki_props_alias alias_(int id, String alias) {return new Xoi_wiki_props_alias().Init_by_ctor(id, alias);}
	public Xoi_wiki_props_ns ns_(int id, boolean subpages_enabled) {return new Xoi_wiki_props_ns().Init_by_ctor(id, subpages_enabled);}
	public void Test_parse(String xml, Xoi_wiki_props_wiki expd) {
		Xoi_wiki_props_wiki actl = new Xoi_wiki_props_wiki();
		api.Parse(actl, xml);
		Tfds.Eq_str_lines(Xto_str(expd), Xto_str(actl));
	}
	public void Test_build(Xoi_wiki_props_wiki wiki, String expd) {
		api.Build_cfg(bfr, wiki);
		Tfds.Eq_str_lines(expd, bfr.To_str_and_clear());
	}
	private String Xto_str(Xoi_wiki_props_wiki v) {
		int len = v.Alias_ary().length;
		bfr.Add_str_a7("aliases").Add_byte_nl();
		for (int i = 0; i < len; i++) {
			Xoi_wiki_props_alias alias = v.Alias_ary()[i];
			bfr.Add_int_variable(alias.Id()).Add_byte_pipe().Add_str_u8(alias.Alias()).Add_byte_nl();
		}
		bfr.Add_str_a7("ns").Add_byte_nl();
		len = v.Ns_ary().length;
		for (int i = 0; i < len; i++) {
			Xoi_wiki_props_ns ns = v.Ns_ary()[i];
			bfr.Add_int_variable(ns.Id()).Add_byte_pipe().Add_int_bool(ns.Subpages_enabled()).Add_byte_nl();
		}
		bfr.Add_byte_nl();
		return bfr.To_str_and_clear();
	}
}
class Xob_subpage_tst_fxt {
	public Xob_subpage_tst_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			mgr = app.Bldr().Wiki_cfg_bldr();
		}
		mgr.Clear();
		hash.Clear();
		return this;
	}	private Xoae_app app; Xob_wiki_cfg_bldr mgr; Ordered_hash hash = Ordered_hash_.New();
	private Xob_subpage_parser parser = new Xob_subpage_parser();
	public Xob_subpage_tst_fxt Init_cmd(String wiki, String key, String text) {
//		mgr.Itms_get_or_new(wiki).Itms_add(key, text);
		return this;
	}
	public Xob_subpage_tst_fxt Expd_txt(String wiki, String text) {
//		hash.Add(wiki, Keyval_.new_(wiki, text));
		return this;
	}
	private String_bldr sb = String_bldr_.new_();
	public void Test_parse(String s) {
		Xob_subpage_wiki[] actl = parser.Parse(Bry_.new_u8(s));
		Tfds.Eq_str_lines("", X_str_wikis(actl));
	}		
	public String X_str_wikis(Xob_subpage_wiki[] ary) {
		X_str_wikis(sb, ary);
		return sb.To_str_and_clear();
		
	}
	private void X_str_wikis(String_bldr sb, Xob_subpage_wiki[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xob_subpage_wiki wiki = ary[i];
			X_str_wiki(sb, wiki);
		}
	}
	private void X_str_wiki(String_bldr sb, Xob_subpage_wiki wiki) {
		sb.Add(wiki.Name()).Add_char_nl();
		int ns_len = wiki.Ns_list().Count();
		for (int i = 0; i < ns_len; i++) {
			Xob_subpage_ns ns = (Xob_subpage_ns)wiki.Ns_list().Get_at(i);
			sb.Add(ns.Id()).Add("=").Add(Bool_.To_str_lower(ns.Enabled())).Add_char_nl();
		}
		sb.Add_char_nl();
	}
}
