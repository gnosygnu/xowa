/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.js;

import gplx.core.threads.Thread_adp_;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BrySplit;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.ObjectUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xoae_page;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.guis.views.Xog_html_itm;
import gplx.xowa.htmls.modules.popups.Xow_popup_mgr;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.Xop_root_tkn;
import gplx.xowa.wikis.data.tbls.Xowd_page_itm;
import gplx.xowa.xtns.pfuncs.ifs.Pfunc_ifexist;

public class Xoh_js_cbk implements Gfo_invk {
	private Xoae_app app;
	private Xog_html_itm html_itm;
	private Xop_root_tkn root = new Xop_root_tkn();
	private final BryWtr bfr = BryWtr.NewAndReset(255);
	public Xoh_js_cbk(Xog_html_itm html_itm) {this.html_itm = html_itm; this.app = html_itm.Owner_tab().Tab_mgr().Win().App();}
	private String Xowa_exec_test(GfoMsg m) {	// concat args with pipe; EX: xowa_exec('proc', 'arg0', 'arg1'); -> proc|arg0|arg1
		bfr.Clear();
		bfr.AddStrU8(m.Key());
		int len = m.Args_count();
		for (int i = 0; i < len; i++)
			bfr.AddStrA7("|").AddStrU8(m.Args_getAt(i).ValToStrOrEmpty());
		return bfr.ToStrAndClear();
	}
	private String[] Xowa_exec_test_as_array(GfoMsg m) {// return args as array; EX: xowa_exec('proc', 'arg0', 'arg1'); -> proc,arg0,arg1
		bfr.Clear();
		int len = m.Args_count();
		String[] rv = new String[len + 1];
		rv[0] = Invk_xowa_exec_test_as_array;
		for (int i = 0; i < len; i++)
			rv[i + 1] = ObjectUtl.ToStrOrEmpty(m.ReadValAt(i));
		return rv;
	}
	private String Parse_to_html(GfoMsg m) {
		Xowe_wiki wiki = html_itm.Owner_tab().Wiki();
		Xop_ctx ctx = wiki.Parser_mgr().Ctx();
		boolean old_para_enabled = ctx.Para().Enabled();
		byte[] raw = BryUtl.NewU8(m.Args_getAt(0).ValToStrOrEmpty());
		boolean para_enabled = m.Args_count() < 2 ? false : BoolUtl.Parse(m.Args_getAt(1).ValToStrOrEmpty());
		try {
			ctx.Para().Enabled_(para_enabled);
			wiki.Parser_mgr().Main().Parse_text_to_wdom(root, ctx, ctx.Tkn_mkr(), raw, 0);
			byte[] data = root.Data_mid();
			wiki.Html_mgr().Html_wtr().Write_doc(bfr, ctx, data, root);
			return bfr.ToStrAndClear();
		}
		finally {
			ctx.Para().Enabled_(old_para_enabled);
		}
	}
	private String Get_page(GfoMsg m) {
		Xowe_wiki wiki = html_itm.Owner_tab().Wiki();
		try {
			Xoa_ttl ttl = Xoa_ttl.Parse(wiki, m.Args_getAt(0).ValToBry());
			Xoae_page page = wiki.Data_mgr().Load_page_by_ttl(ttl);
			return StringUtl.NewU8(page.Db().Text().Text_bry());
		} catch (Exception e) {return null;}
	}
	private String Popups_get_html(GfoMsg m) {
		try {
			String mode			= m.Args_getAt(0).ValToStrOrEmpty();
			String popups_id	= m.Args_getAt(1).ValToStrOrEmpty();

			Xow_popup_mgr popup_mgr = html_itm.Owner_tab().Wiki().Html_mgr().Head_mgr().Popup_mgr();
			if      (StringUtl.Eq(mode, "init")) {
				byte[] href_bry		= m.Args_getAt(2).ValToBry();
				byte[] tooltip_bry	= m.Args_getAt(3).ValToBry();
				return popup_mgr.Show_init(popups_id, href_bry, tooltip_bry);
			}
			else if (StringUtl.Eq(mode, "more"))
				return popup_mgr.Show_more(popups_id);
			else if (StringUtl.Eq(mode, "all"))
				popup_mgr.Show_all(popups_id);
			return "";
		} catch (Exception e) {return null;}
	}
	private String[] Get_title_meta(Xowe_wiki wiki, byte[] ttl_bry) {
		synchronized (tmp_page) {
			tmp_page.Clear();
			Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ttl_bry);
			wiki.Db_mgr().Load_mgr().Load_by_ttl(tmp_page, ttl.Ns(), ttl.Page_db());
		}
		return StringUtl.Ary(tmp_page.Exists() ? "1" : "0", IntUtl.ToStr(tmp_page.Id()), IntUtl.ToStr(tmp_page.Ns_id()), StringUtl.NewU8(tmp_page.Ttl_page_db()), BoolUtl.ToStrLower(tmp_page.Redirected()), tmp_page.Modified_on().ToStrFmt("yyyy-MM-dd HH:mm:ss"), IntUtl.ToStr(tmp_page.Text_len()));
	}	private static final Xowd_page_itm tmp_page = Xowd_page_itm.new_tmp();
	private String[][] Get_titles_meta(GfoMsg m) {
		Xowe_wiki wiki = html_itm.Owner_tab().Wiki();
		try {
			byte[][] ttls = BrySplit.Split(BryUtl.NewU8((String)m.ReadValAt(0)), AsciiByte.Nl);
			int ttls_len = ttls.length;
			String[][] rv = new String[ttls_len][];
			for (int i = 0; i < ttls_len; i++) {
				byte[] ttl = ttls[i];
				rv[i] = Get_title_meta(wiki, ttl);
			}
			return rv;
		} catch (Exception e) {return null;}
	}
	private boolean Get_title_exists(Xowe_wiki wiki, byte[] ttl) {
		return Pfunc_ifexist.Exists(wiki, ttl);
	}		
	private String[] Get_titles_exists(GfoMsg m) {
		Xowe_wiki wiki = html_itm.Owner_tab().Wiki();
		try {
			byte[][] ttls = BryUtl.AryObj((Object[])m.ReadValAt(0));
			int ttls_len = ttls.length;
			String[] rv = new String[ttls_len];
			for (int i = 0; i < ttls_len; i++) {
				byte[] ttl = ttls[i];
				rv[i] = Get_title_exists(wiki, ttl) ? "1" : "0";
			}
			return rv;
		} catch (Exception e) {return null;}
	}		
	private String Get_search_suggestions(GfoMsg m) {
		Xowe_wiki wiki = html_itm.Owner_tab().Wiki();
		String search_str = (String)m.ReadValAt(0);
		// 2021-03-02|ISSUE#:841|Pressing backspace on empty search box will generate a null
		if (search_str == null) {
			return "";
		}
		byte[] search_bry = BryUtl.NewU8(search_str);
		byte[] cbk_func = BryUtl.NewU8((String)m.ReadValAt(1));
		app.Addon_mgr().Itms__search__htmlbar().Search_by_swt(wiki, search_bry, cbk_func);
		return "";
	}
	private String[] Wikidata_get_label(GfoMsg m) {
		try {
			Thread_adp_.Sleep(10);	// slow down calls to prevent random crashing in XulRunner; DATE:2014-04-23
			gplx.xowa.xtns.wbases.Wdata_wiki_mgr wdata_mgr = app.Wiki_mgr().Wdata_mgr();
			wdata_mgr.Wdata_wiki().Init_assert();	// NOTE: must assert else ns_mgr won't load Property
			int len = m.Args_count();
			if (len < 1) return null;
			byte[][] langs = BrySplit.Split(m.Args_getAt(0).ValToBry(), AsciiByte.Semic);
			int langs_len = langs.length;
			String[] rv = new String[len - 1];
			for (int i = 1; i < len; i++) {
				try {
					byte[] ttl_bry = m.Args_getAt(i).ValToBry();
					gplx.xowa.xtns.wbases.Wdata_doc page = wdata_mgr.Doc_mgr.Get_by_xid_or_null(ttl_bry); if (page == null) continue;
					for (int j = 0; j < langs_len; j++) {
						byte[] lang_key = langs[j];
						if		(BryLni.Eq(lang_key, Wikidata_get_label_xowa_ui_lang))
							lang_key = app.Sys_cfg().Lang();
						byte[] val_bry = null;
						if		(BryLni.Eq(lang_key, Wikidata_get_label_xowa_title))
							val_bry = ttl_bry;
						else {
							val_bry = page.Get_label_bry_or_null(lang_key);
						}
						if (val_bry == null) continue;
						rv[i - 1] = StringUtl.NewU8(val_bry);
						break;
					}
				}	catch (Exception e) {rv[i] = null;}
				finally {}
			}
			return rv;
		} catch (Exception e) {return null;}
	}
	private String Scripts_exec(GfoMsg m) {
		Object rv = null;
		try {
			rv = app.Gfs_mgr().Run_str(m.Args_getAt(0).ValToStrOrEmpty());
		}
		catch (Exception e) {return null;}
		return ObjectUtl.ToStrOrEmpty(rv);
	}
	private static final byte[] Wikidata_get_label_xowa_ui_lang = BryUtl.NewA7("xowa_ui_lang"), Wikidata_get_label_xowa_title = BryUtl.NewA7("xowa_title");
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_parse_to_html))						return Parse_to_html(m);
		else if	(ctx.Match(k, Invk_wikidata_get_label))					return Wikidata_get_label(m);
		else if	(ctx.Match(k, Invk_get_page))							return Get_page(m);
		else if	(ctx.MatchIn(k, Invk_cmd, Invk_scripts_exec))			return Scripts_exec(m);
		else if	(ctx.Match(k, Invk_scripts_exec))						return Scripts_exec(m);
		else if	(ctx.Match(k, Invk_popups_get_html))					return Popups_get_html(m);
		else if	(ctx.Match(k, Invk_get_search_suggestions))				return Get_search_suggestions(m);
		else if	(ctx.Match(k, Invk_get_titles_meta))					return Get_titles_meta(m);
		else if	(ctx.Match(k, Invk_get_titles_exists))					return Get_titles_exists(m);
		else if	(ctx.Match(k, Invk_get_current_url))					return StringUtl.NewU8(html_itm.Owner_tab().Page().Url().Raw());
		else if	(ctx.Match(k, Invk_xowa_exec_test))						return Xowa_exec_test(m);
		else if	(ctx.Match(k, Invk_xowa_exec_test_as_array))			return Xowa_exec_test_as_array(m);
		else if	(ctx.Match(k, Invk_exec_json))							return app.Html__bridge_mgr().Cmd_mgr().Exec(m);
		else if	(ctx.Match(k, Invk_bldr_exec))							return app.Bldr().Exec_json((String)m.ReadValAt(0));
		else	return Gfo_invk_.Rv_unhandled;
	}
	public static final String Invk_parse_to_html = "parse_to_html", Invk_wikidata_get_label = "wikidata_get_label", Invk_get_page = "get_page", Invk_cmd = "cmd", Invk_scripts_exec = "scripts_exec"
	, Invk_get_search_suggestions = "get_search_suggestions", Invk_get_titles_meta = "get_titles_meta", Invk_get_titles_exists = "get_titles_exists", Invk_get_current_url = "get_current_url"
	, Invk_xowa_exec_test = "xowa_exec_test", Invk_xowa_exec_test_as_array = "xowa_exec_test_as_array"
	, Invk_popups_get_html = "popups_get_html"
	, Invk_exec_json = "exec_json"
	, Invk_bldr_exec = "bldr_exec"
	;
}
