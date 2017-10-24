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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*;
import gplx.xowa.wikis.*; import gplx.xowa.xtns.wbases.imports.*;
import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.cmds.texts.*; import gplx.xowa.bldrs.cmds.texts.sqls.*; import gplx.xowa.bldrs.cmds.texts.tdbs.*; import gplx.xowa.addons.bldrs.files.*; import gplx.xowa.addons.wikis.ctgs.bldrs.*; import gplx.xowa.bldrs.cmds.utils.*;
import gplx.xowa.bldrs.cmds.diffs.*;
import gplx.xowa.files.origs.*; import gplx.xowa.htmls.core.bldrs.*;
import gplx.xowa.addons.wikis.searchs.bldrs.*;
import gplx.xowa.addons.bldrs.files.cmds.*; import gplx.xowa.addons.wikis.htmls.css.bldrs.*;
public class Xob_cmd_mgr implements Gfo_invk {
	private final    Xob_bldr bldr;
	public final    Xob_cmd_regy cmd_regy;
	public Xob_cmd_mgr(Xob_bldr bldr, Xob_cmd_regy cmd_regy) {this.bldr = bldr; this.cmd_regy = cmd_regy;}
	public void Clear() {list.Clear(); dump_rdrs.Clear();}
	public int Len() {return list.Count();} private final    List_adp list = List_adp_.New();
	public Xob_cmd Get_at(int i) {return (Xob_cmd)list.Get_at(i);} 
	public Xob_cmd Add(Xob_cmd cmd) {list.Add(cmd); return cmd;}
	public Gfo_invk Add_cmd(Xowe_wiki wiki, String cmd_key) {
		Xob_cmd prime = cmd_regy.Get_or_null(cmd_key);
		if (prime != null) {
			Xob_cmd clone = prime.Cmd_clone(bldr, wiki);
			Add(clone);
			return clone;
		}
		if		(String_.Eq(cmd_key, Xob_cmd_keys.Key_text_init))					return Add(new Xob_init_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_text_page))					return Xml_rdr_direct_add(wiki, new Xob_page_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_text_css))					return Add(new Xob_css_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_text_search_wkr))				return Xml_rdr_direct_add(wiki, new gplx.xowa.addons.wikis.searchs.bldrs.Srch_bldr_wkr(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_text_search_cmd))				return Add(new Srch_bldr_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_text_term))					return Add(new Xob_term_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_text_delete_page))			return Add(new Xob_page_delete_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_html_redlinks))				return Add(new Xob_redlink_mkr_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_util_cleanup))				return Add(new Xob_cleanup_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_util_delete))					return Add(new Xob_delete_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_util_download))				return Add(new Xob_download_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_util_xml_dump))				return Add(new Xob_xml_dumper_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_wbase_qid))					return Xml_rdr_direct_add(wiki, new Xob_wdata_qid_sql().Ctor(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_wbase_pid))					return Xml_rdr_direct_add(wiki, new Xob_wdata_pid_sql().Ctor(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_wbase_db))					return Add(new Xob_wdata_db_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_site_meta))					return Add(new Xob_site_meta_cmd(bldr, wiki));

		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_tdb_text_init))				return Add(new Xob_init_tdb(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_tdb_make_id))					return Xml_rdr_direct_add(wiki, new Xob_make_id_wkr(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_tdb_calc_stats))				return Add(new Xob_calc_stats_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_tdb_text_wdata_qid))			return Xml_rdr_direct_add(wiki, new Xob_wdata_qid_txt().Ctor(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_tdb_text_wdata_pid))			return Xml_rdr_direct_add(wiki, new Xob_wdata_pid_txt().Ctor(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_diff_build))					return Add(new Xob_diff_build_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_exec_sql))					return Add(new Xob_exec_sql_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_cmd_keys.Key_decompress_bz2))				return Add(new Xob_decompress_bz2_cmd(bldr, wiki));
		else 																		throw Err_.new_unimplemented_w_msg("builder command is not supported: " + cmd_key);
	}
	private Xob_page_wkr Xml_rdr_direct_add(Xowe_wiki wiki, Xob_page_wkr wkr) {
		Xob_page_wkr_cmd dump_rdr = Xml_rdr_get(wiki);
		dump_rdr.Wkr_add(wkr);
		return wkr;
	}
	private Xob_page_wkr_cmd Xml_rdr_get(Xowe_wiki wiki) {
		byte[] wiki_key = wiki.Domain_bry();
		Xob_page_wkr_cmd rv = (Xob_page_wkr_cmd)dump_rdrs.Get_by(dump_rdrs_ref.Val_(wiki_key));
		if (rv == null) {
			rv = new Xob_page_wkr_cmd(bldr, wiki);
			dump_rdrs.Add(Bry_obj_ref.New(wiki_key), rv);
			this.Add(rv);
		}
		return rv;
	}
	private Hash_adp dump_rdrs = Hash_adp_.New(); private Bry_obj_ref dump_rdrs_ref = Bry_obj_ref.New_empty();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if			(ctx.Match(k, Invk_add))				return Add_cmd(Wiki_get_or_make(m), m.ReadStr("v"));
		else if		(ctx.Match(k, Invk_add_many))			return Add_many(m);
		else if		(ctx.Match(k, Invk_get_first))			return Get_first(m);
		else if		(ctx.Match(k, Invk_new_batch))			return new Xob_core_batch_utl(bldr, m.ReadBry("v"));
		else	return Gfo_invk_.Rv_unhandled;
	}
	private static final String Invk_add = "add", Invk_add_many = "add_many", Invk_new_batch = "new_batch", Invk_get_first = "get_first";
	private Object Get_first(GfoMsg m) {
		String cmd_key = m.ReadStr("v");
		int cmds_len = list.Count();
		for (int i = 0;i < cmds_len; i++) {
			Xob_cmd cmd = (Xob_cmd)list.Get_at(i);
			if (String_.Eq(cmd.Cmd_key(), cmd_key)) return cmd;
		}
		throw Err_.new_wo_type("cmd not found", "key", cmd_key);
	}
	private Object Add_many(GfoMsg m) {
		Xowe_wiki wiki = Wiki_get_or_make(m);
		wiki.Lang().Init_by_load_assert();	// NOTE: must check that lang is loaded; else case_mgr will not initialize; DATE:2013-05-11
		int args_len = m.Args_count();
		String[] cmds = new String[args_len - 1];	// -1 b/c 1st arg is wiki
		for (int i = 1; i < args_len; i++) {
			Keyval kv = m.Args_getAt(i);
			cmds[i - 1] = kv.Val_to_str_or_empty();
		}
		return Add_many(wiki, cmds);
	}
	public Object Add_many(Xowe_wiki wiki, String... cmds) {
		int len = cmds.length; if (len == 0) throw Err_.new_wo_type("add_many cannot have 0 cmds");
		Object rv = null;
		for (int i = 0; i < len; i++)
			rv = Add_cmd(wiki, cmds[i]);
		return rv;
	}
	public void Add_cmd_ary(Xob_cmd... cmds_ary) {
		int cmds_len = cmds_ary.length;
		for (int i = 0; i < cmds_len; ++i)
			this.Add(cmds_ary[i]);
	}
	private Xowe_wiki Wiki_get_or_make(GfoMsg m) {
		byte[] wiki_key = m.ReadBry("v");
		Xoae_wiki_mgr wiki_mgr = bldr.App().Wiki_mgr();
		Xowe_wiki rv = wiki_mgr.Get_by_or_make(wiki_key);
		rv.Lang().Init_by_load();
		return rv;
	}
	public static final String GRP_KEY = "xowa.bldr.cmds";
}
