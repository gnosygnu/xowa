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
package gplx.xowa.addons.bldrs.app_cfgs;
import gplx.libs.files.Io_mgr;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.commons.KeyVal;
import gplx.types.commons.String_bldr;
import gplx.types.commons.String_bldr_;
import gplx.xowa.*;
import org.junit.*;
public class Xob_wiki_cfg_bldr_tst {
	Xob_wiki_cfg_bldr_fxt fxt = new Xob_wiki_cfg_bldr_fxt();
	@Before public void init() {fxt.Clear();}
	@Test public void Exec() {
		fxt	.Init_cmd("en.wikipedia.org", "key0", "en.val0")
			.Init_cmd("en.wikipedia.org", "key1", "en.val1")
			.Init_cmd("fr.wikipedia.org", "key0", "fr.val0")
			.Init_cmd("fr.wikipedia.org", "key1", "fr.val1")
			.Expd_txt("en.wikipedia.org", StringUtl.ConcatLinesNl
			(	"// key0.bgn"
			,	"en.val0"
			,	"// key0.end"
			,	"// key1.bgn"
			,	"en.val1"
			,	"// key1.end"
			))
			.Expd_txt("fr.wikipedia.org", StringUtl.ConcatLinesNl
			(	"// key0.bgn"
			,	"fr.val0"
			,	"// key0.end"
			,	"// key1.bgn"
			,	"fr.val1"
			,	"// key1.end"
			))
			.Test()
			;
		fxt	.Clear()
			.Init_cmd("en.wikipedia.org", "key2", "en.val2")
			.Expd_txt("en.wikipedia.org", StringUtl.ConcatLinesNl
			(	"// key0.bgn"
			,	"en.val0"
			,	"// key0.end"
			,	"// key1.bgn"
			,	"en.val1"
			,	"// key1.end"
			,	"// key2.bgn"
			,	"en.val2"
			,	"// key2.end"
			));
	}
//		@Test public void Lang_names_run() {
//			Io_url dir = Io_url_.new_dir_("/var/www/mediawiki/languages/messages/");
//			Io_url[] fils = Io_mgr.Instance.QueryDir_args(dir).ExecAsUrlAry();
//			int fils_len = fils.length;
//			String_bldr sb = String_bldr_.new_();
//			for (int i = 0; i < fils_len; i++) {
//				Io_url fil = fils[i];
//				String lang_code = String_.Lower(String_.Replace(fil.NameOnly(), "Messages", ""));
//				String txt = Io_mgr.Instance.LoadFilStr(fil);
//				String[] lines = String_.Split(txt, '\n');
//				String line = lines[1];
//				line = String_.Replace(line, "/** ", "");
//				int pos = String_.FindBwd(line, " (");
//				if (pos == String_.Find_none) continue;	// en; en_rtl have no "language" line
//				if (	String_.Has_at_bgn(lang_code, "be_")
//					|| 	String_.Has_at_bgn(lang_code, "crh_")
//					|| 	String_.Has_at_bgn(lang_code, "kk_")
//					|| 	String_.Has_at_bgn(lang_code, "ku_")
//					|| 	String_.Has_at_bgn(lang_code, "sr_")
//					|| 	String_.In(lang_code, "de_formal", "nb", "nl_informal", "nn", "no")
//					) {
//					int new_pos = String_.FindBwd(line, " (", pos - 1);
//					if (new_pos != String_.Find_none)
//						pos = new_pos;
//				}
//				line = Replace_by_pos(line, pos, pos + 2, "|");
//				int line_len = String_.Len(line);
//				if (line_len > 0)
//					line = String_.MidByLen(line, 0, line_len - 1);
//				String[] terms = String_.Split(line, '|');
//				sb.Add(lang_code).Add("|").Add(String_.Trim(terms[0])).Add("|").Add(String_.Trim(terms[1])).Add("\n");
//			}
//			Tfds.Dbg(sb.To_str_and_clear());
//		}
	@Test public void Ns_aliases() {
		Io_mgr.Instance.InitEngine_mem();
		Io_mgr.Instance.SaveFilStr("mem/en.wikipedia.org/w/api.php?action=query&format=xml&meta=siteinfo&siprop=namespacealiases", StringUtl.ConcatLinesNl
		(	"<api>"
		,	"<query>"
		,	"<namespacealiases>"
		,	"<ns id=\"4\" xml:space=\"preserve\">WP"
		,	"</ns>"
		,	"<ns id=\"5\" xml:space=\"preserve\">WT"
		,	"</ns>"
		,	"<ns id=\"6\" xml:space=\"preserve\">Image"
		,	"</ns>"
		,	"<ns id=\"7\" xml:space=\"preserve\">Image talk"
		,	"</ns>"
		,	"</namespacealiases>"
		,	"</query>"
		,	"</api>"
		));
		String[] wikis = new String[] {"en.wikipedia.org"}; String protocol = "mem/";
		GfoTstr.EqLines(Query_ns(protocol, gplx.core.ios.IoEngine_.MemKey, wikis), StringUtl.ConcatLinesNl
		(	"app.bldr.wiki_cfg_bldr.get('en.wikipedia.org').new_cmd_('wiki.ns_mgr.aliases', 'ns_mgr.add_alias_bulk(\""
		,	"4|WP"
		,	"5|WT"
		,	"6|Image"
		,	"7|Image_talk"
		,	"\");');"
		));
	}
	String Query_ns(String protocol, String trg_engine_key, String[] wikis) {
		String_bldr sb = String_bldr_.new_();
		int wikis_len = wikis.length;
		for (int i = 0; i < wikis_len; i++) {
			String wiki = wikis[i];
			if (StringUtl.IsNullOrEmpty(wiki)) continue;
			try {
			String api = protocol + wiki + "/w/api.php?action=query&format=xml&meta=siteinfo&siprop=namespacealiases";
			String xml = StringUtl.NewU8(Io_mgr.Instance.DownloadFil_args("", null).Trg_engine_key_(trg_engine_key).Exec_as_bry(api));
			if (xml == null) continue;	// not found
			gplx.langs.xmls.XmlDoc xdoc = gplx.langs.xmls.XmlDoc_.parse(xml);
			gplx.langs.xmls.XmlNde xnde = gplx.langs.xmls.Xpath_.SelectFirst(xdoc.Root(), "query/namespacealiases");
			sb.Add("app.bldr.wiki_cfg_bldr.get('").Add(wiki).Add("').new_cmd_('wiki.ns_mgr.aliases', 'ns_mgr.add_alias_bulk(\"\n");
			int xndes_len = xnde.SubNdes().Count();
			for (int j = 0; j < xndes_len; j++) {
				gplx.langs.xmls.XmlNde ns_nde = xnde.SubNdes().Get_at(j);
				if (!StringUtl.Eq(ns_nde.Name(), "ns")) continue;
				int id = IntUtl.Parse(ns_nde.Atrs().FetchValOr("id", "-1"));
				String name = StringUtl.Replace(StringUtl.Replace(ns_nde.Text_inner(), " ", "_"), "'", "''");
				sb.Add(IntUtl.ToStr(id)).Add("|").Add(StringUtl.Trim(name)).AddCharNl();
			}
			sb.Add("\");');\n");
			}
			catch(Exception e) {sb.Add("// fail: " + wiki + " " + ErrUtl.ToStrFull(e)).AddCharNl();}
		}
		return sb.ToStrAndClear();
	}
}
class Xob_wiki_cfg_bldr_fxt {
	public Xob_wiki_cfg_bldr_fxt Clear() {
		if (app == null) {
			app = Xoa_app_fxt.Make__app__edit();
			wiki_cfg_bldr = app.Bldr().Wiki_cfg_bldr();
		}
		wiki_cfg_bldr.Clear();
		hash.Clear();
		return this;
	}	private Xoae_app app; Xob_wiki_cfg_bldr wiki_cfg_bldr; Ordered_hash hash = Ordered_hash_.New();
	public Xob_wiki_cfg_bldr_fxt Init_cmd(String wiki, String key, String text) {
		wiki_cfg_bldr.Itms_get_or_new(wiki).Itms_add(key, text);
		return this;
	}
	public Xob_wiki_cfg_bldr_fxt Expd_txt(String wiki, String text) {
		hash.Add(wiki, KeyVal.NewStr(wiki, text));
		return this;
	}
	public void Test() {
		wiki_cfg_bldr.Exec();
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			KeyVal kv = (KeyVal)hash.GetAt(i);
			String wiki = kv.KeyToStr();
			String expd = (String)kv.Val();
			String actl = Io_mgr.Instance.LoadFilStr(app.Fsys_mgr().Cfg_wiki_core_dir().GenSubFil(wiki + ".gfs"));
			GfoTstr.EqLines(expd, actl);
		}
	}
}
