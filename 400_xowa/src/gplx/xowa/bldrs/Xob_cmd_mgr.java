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
package gplx.xowa.bldrs;
import gplx.frameworks.invks.GfoMsg;
import gplx.frameworks.invks.Gfo_invk;
import gplx.frameworks.invks.Gfo_invk_;
import gplx.frameworks.invks.GfsCtx;
import gplx.types.basics.lists.Hash_adp;
import gplx.types.basics.lists.Hash_adp_;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.custom.brys.wtrs.BryRef;
import gplx.types.errs.ErrUtl;
import gplx.types.commons.KeyVal;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.addons.wikis.htmls.css.bldrs.Xob_css_cmd;
import gplx.xowa.addons.wikis.searchs.bldrs.Srch_bldr_cmd;
import gplx.xowa.bldrs.cmds.diffs.Xob_diff_build_cmd;
import gplx.xowa.bldrs.cmds.texts.sqls.Xob_init_cmd;
import gplx.xowa.bldrs.cmds.texts.sqls.Xob_page_cmd;
import gplx.xowa.bldrs.cmds.texts.sqls.Xob_page_delete_cmd;
import gplx.xowa.bldrs.cmds.texts.sqls.Xob_term_cmd;
import gplx.xowa.bldrs.cmds.utils.Xob_cleanup_cmd;
import gplx.xowa.bldrs.cmds.utils.Xob_core_batch_utl;
import gplx.xowa.bldrs.cmds.utils.Xob_decompress_bz2_cmd;
import gplx.xowa.bldrs.cmds.utils.Xob_delete_cmd;
import gplx.xowa.bldrs.cmds.utils.Xob_download_cmd;
import gplx.xowa.bldrs.cmds.utils.Xob_exec_sql_cmd;
import gplx.xowa.bldrs.cmds.utils.Xob_site_meta_cmd;
import gplx.xowa.bldrs.cmds.utils.Xob_xml_dumper_cmd;
import gplx.xowa.bldrs.wkrs.Xob_cmd;
import gplx.xowa.bldrs.wkrs.Xob_page_wkr;
import gplx.xowa.htmls.core.bldrs.Xob_redlink_mkr_cmd;
import gplx.xowa.wikis.Xoae_wiki_mgr;
import gplx.xowa.xtns.wbases.imports.Xob_wdata_db_cmd;
import gplx.xowa.xtns.wbases.imports.Xob_wdata_pid;
import gplx.xowa.xtns.wbases.imports.Xob_wdata_qid;
public class Xob_cmd_mgr implements Gfo_invk {
	private final Xob_bldr bldr;
	public final Xob_cmd_regy cmd_regy;
	public Xob_cmd_mgr(Xob_bldr bldr, Xob_cmd_regy cmd_regy) {this.bldr = bldr; this.cmd_regy = cmd_regy;}
	public void Clear() {list.Clear(); dump_rdrs.Clear();}
	public int Len() {return list.Len();} private final List_adp list = List_adp_.New();
	public Xob_cmd Get_at(int i) {return (Xob_cmd)list.GetAt(i);}
	public Xob_cmd Add(Xob_cmd cmd) {list.Add(cmd); return cmd;}
	public Gfo_invk Add_cmd(Xowe_wiki wiki, String cmd_key) {
		Xob_cmd prime = cmd_regy.Get_or_null(cmd_key);
		if (prime != null) {
			Xob_cmd clone = prime.Cmd_clone(bldr, wiki);
			Add(clone);
			return clone;
		}
		if		(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_text_init))					return Add(new Xob_init_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_text_page))					return Xml_rdr_direct_add(wiki, new Xob_page_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_text_css))					return Add(new Xob_css_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_text_search_wkr))				return Xml_rdr_direct_add(wiki, new gplx.xowa.addons.wikis.searchs.bldrs.Srch_bldr_wkr(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_text_search_cmd))				return Add(new Srch_bldr_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_text_term))					return Add(new Xob_term_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_text_delete_page))			return Add(new Xob_page_delete_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_html_redlinks))				return Add(new Xob_redlink_mkr_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_util_cleanup))				return Add(new Xob_cleanup_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_util_delete))					return Add(new Xob_delete_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_util_download))				return Add(new Xob_download_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_util_xml_dump))				return Add(new Xob_xml_dumper_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_wbase_qid))					return Xml_rdr_direct_add(wiki, new Xob_wdata_qid(null).Ctor(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_wbase_pid))					return Xml_rdr_direct_add(wiki, new Xob_wdata_pid(null).Ctor(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_wbase_db))					return Add(new Xob_wdata_db_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_site_meta))					return Add(new Xob_site_meta_cmd(bldr, wiki));

		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_diff_build))					return Add(new Xob_diff_build_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_exec_sql))					return Add(new Xob_exec_sql_cmd(bldr, wiki));
		else if	(StringUtl.Eq(cmd_key, Xob_cmd_keys.Key_decompress_bz2))				return Add(new Xob_decompress_bz2_cmd(bldr, wiki));
		else 																		throw ErrUtl.NewUnimplemented("builder command is not supported: " + cmd_key);
	}
	private Xob_page_wkr Xml_rdr_direct_add(Xowe_wiki wiki, Xob_page_wkr wkr) {
		Xob_page_wkr_cmd dump_rdr = Xml_rdr_get(wiki);
		dump_rdr.Wkr_add(wkr);
		return wkr;
	}
	private Xob_page_wkr_cmd Xml_rdr_get(Xowe_wiki wiki) {
		byte[] wiki_key = wiki.Domain_bry();
		Xob_page_wkr_cmd rv = (Xob_page_wkr_cmd)dump_rdrs.GetByOrNull(dump_rdrs_ref.ValSet(wiki_key));
		if (rv == null) {
			rv = new Xob_page_wkr_cmd(bldr, wiki);
			dump_rdrs.Add(BryRef.New(wiki_key), rv);
			this.Add(rv);
		}
		return rv;
	}
	private Hash_adp dump_rdrs = Hash_adp_.New(); private BryRef dump_rdrs_ref = BryRef.NewEmpty();
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
		int cmds_len = list.Len();
		for (int i = 0;i < cmds_len; i++) {
			Xob_cmd cmd = (Xob_cmd)list.GetAt(i);
			if (StringUtl.Eq(cmd.Cmd_key(), cmd_key)) return cmd;
		}
		throw ErrUtl.NewArgs("cmd not found", "key", cmd_key);
	}
	private Object Add_many(GfoMsg m) {
		Xowe_wiki wiki = Wiki_get_or_make(m);
		// TOMBSTONE: redundant since Wiki_get_or_make also calls Init_by_load(); DATE:2019-02-09
		// wiki.Lang().Init_by_load_assert();	// NOTE: must check that lang is loaded; else case_mgr will not initialize; DATE:2013-05-11
		int args_len = m.Args_count();
		String[] cmds = new String[args_len - 1];	// -1 b/c 1st arg is wiki
		for (int i = 1; i < args_len; i++) {
			KeyVal kv = m.Args_getAt(i);
			cmds[i - 1] = kv.ValToStrOrEmpty();
		}
		return Add_many(wiki, cmds);
	}
	public Object Add_many(Xowe_wiki wiki, String... cmds) {
		int len = cmds.length; if (len == 0) throw ErrUtl.NewArgs("add_many cannot have 0 cmds");
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
