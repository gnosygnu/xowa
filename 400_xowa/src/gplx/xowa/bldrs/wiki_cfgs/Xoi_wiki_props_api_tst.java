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
package gplx.xowa.bldrs.wiki_cfgs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
import gplx.xowa.*;
import org.junit.*;
import gplx.xowa.addons.bldrs.app_cfgs.*;
public class Xoi_wiki_props_api_tst {
	private Xoi_wiki_props_fxt fxt = new Xoi_wiki_props_fxt();
	@Before public void init() {} // private Xob_subpage_tst_fxt fxt = new] Xob_subpage_tst_fxt();
	@Test public void Parse() {
		fxt.Test_parse(StringUtl.ConcatLinesNl
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
//		@Test public void Build() {
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
	private BryWtr bfr = BryWtr.New();
	public Xoi_wiki_props_wiki wiki_() {return wiki_("domain_doesnt_matter");}
	public Xoi_wiki_props_wiki wiki_(String wiki_domain) {return new Xoi_wiki_props_wiki().Wiki_domain_(BryUtl.NewA7(wiki_domain));}
	public Xoi_wiki_props_alias alias_(int id, String alias) {return new Xoi_wiki_props_alias().Init_by_ctor(id, alias);}
	public Xoi_wiki_props_ns ns_(int id, boolean subpages_enabled) {return new Xoi_wiki_props_ns().Init_by_ctor(id, subpages_enabled);}
	public void Test_parse(String xml, Xoi_wiki_props_wiki expd) {
		Xoi_wiki_props_wiki actl = new Xoi_wiki_props_wiki();
		api.Parse(actl, xml);
		GfoTstr.EqLines(Xto_str(expd), Xto_str(actl));
	}
	public void Test_build(Xoi_wiki_props_wiki wiki, String expd) {
		api.Build_cfg(bfr, wiki);
		GfoTstr.EqLines(expd, bfr.ToStrAndClear());
	}
	private String Xto_str(Xoi_wiki_props_wiki v) {
		int len = v.Alias_ary().length;
		bfr.AddStrA7("aliases").AddByteNl();
		for (int i = 0; i < len; i++) {
			Xoi_wiki_props_alias alias = v.Alias_ary()[i];
			bfr.AddIntVariable(alias.Id()).AddBytePipe().AddStrU8(alias.Alias()).AddByteNl();
		}
		bfr.AddStrA7("ns").AddByteNl();
		len = v.Ns_ary().length;
		for (int i = 0; i < len; i++) {
			Xoi_wiki_props_ns ns = v.Ns_ary()[i];
			bfr.AddIntVariable(ns.Id()).AddBytePipe().AddIntBool(ns.Subpages_enabled()).AddByteNl();
		}
		bfr.AddByteNl();
		return bfr.ToStrAndClear();
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
		Xob_subpage_wiki[] actl = parser.Parse(BryUtl.NewU8(s));
		GfoTstr.EqLines("", X_str_wikis(actl));
	}		
	public String X_str_wikis(Xob_subpage_wiki[] ary) {
		X_str_wikis(sb, ary);
		return sb.ToStrAndClear();
		
	}
	private void X_str_wikis(String_bldr sb, Xob_subpage_wiki[] ary) {
		int len = ary.length;
		for (int i = 0; i < len; i++) {
			Xob_subpage_wiki wiki = ary[i];
			X_str_wiki(sb, wiki);
		}
	}
	private void X_str_wiki(String_bldr sb, Xob_subpage_wiki wiki) {
		sb.Add(wiki.Name()).AddCharNl();
		int ns_len = wiki.Ns_list().Len();
		for (int i = 0; i < ns_len; i++) {
			Xob_subpage_ns ns = (Xob_subpage_ns)wiki.Ns_list().GetAt(i);
			sb.Add(ns.Id()).Add("=").Add(BoolUtl.ToStrLower(ns.Enabled())).AddCharNl();
		}
		sb.AddCharNl();
	}
}
