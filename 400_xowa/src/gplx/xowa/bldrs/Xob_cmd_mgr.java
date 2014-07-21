/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.*; import gplx.xowa.xtns.wdatas.imports.*; import gplx.xowa.bldrs.imports.ctgs.*; import gplx.xowa.bldrs.imports.*; import gplx.xowa.bldrs.oimgs.*;
import gplx.xowa.bldrs.wikis.redirects.*; import gplx.xowa.bldrs.wikis.images.*; import gplx.xowa.files.wiki_orig.*;
import gplx.xowa.bldrs.files.*;
public class Xob_cmd_mgr implements GfoInvkAble {
	public Xob_cmd_mgr(Xob_bldr bldr) {this.bldr = bldr;} private Xob_bldr bldr;
	public void Clear() {list.Clear(); dump_rdrs.Clear();}
	public int Len() {return list.Count();} ListAdp list = ListAdp_.new_();
	public Xob_cmd Get_at(int i) {return (Xob_cmd)list.FetchAt(i);} 
	public Xob_cmd Add(Xob_cmd cmd) {list.Add(cmd); return cmd;}
	public GfoInvkAble Add_cmd(Xow_wiki wiki, String cmd_key) {
		if		(String_.Eq(cmd_key, Xob_init_txt.KEY))						return Add(new Xob_init_txt(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_init_sql.KEY))						return Add(new Xob_init_sql(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_term_txt.KEY))						return Add(new Xob_term_txt(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_term_sql.KEY))						return Add(new Xob_term_sql(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_page_sql.KEY))						return Xml_rdr_direct_add(wiki, new Xob_page_sql(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_page_txt.KEY))						return Xml_rdr_direct_add(wiki, new Xob_page_txt(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_search_txt.KEY))					return Xml_rdr_direct_add(wiki, new Xob_search_txt(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_ctg_v1_txt.KEY))					return Xml_rdr_parser_add(wiki, new Xob_ctg_v1_txt().Ctor(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_ctg_v1_sql.KEY))					return Xml_rdr_parser_add(wiki, new Xob_ctg_v1_sql().Ctor(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_categorylinks_txt.KEY))			return Add(new Xob_categorylinks_txt(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_categorylinks_sql.KEY))			return Add(new Xob_categorylinks_sql(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_wdata_qid_txt.KEY))				return Xml_rdr_direct_add(wiki, new Xob_wdata_qid_txt().Ctor(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_wdata_qid_sql.KEY))				return Xml_rdr_direct_add(wiki, new Xob_wdata_qid_sql().Ctor(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_wdata_pid_txt.KEY))				return Xml_rdr_direct_add(wiki, new Xob_wdata_pid_txt().Ctor(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_wdata_pid_sql.KEY))				return Xml_rdr_direct_add(wiki, new Xob_wdata_pid_sql().Ctor(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_search_sql_wkr.KEY))				return Xml_rdr_direct_add(wiki, new Xob_search_sql_wkr(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_search_sql_cmd.KEY_search_sql))	return Add(new Xob_search_sql_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_category_registry_sql.KEY))		return Add(new Xob_category_registry_sql(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_lnki_temp_wkr.KEY_oimg))			return Add(new Xob_lnki_temp_wkr(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_lnki_regy_cmd.KEY_oimg))			return Add(new Xob_lnki_regy_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_text_db_prep.KEY_oimg))			return Add(new Xob_text_db_prep(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_orig_regy_cmd.KEY_oimg))			return Add(new Xob_orig_regy_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_xfer_temp_cmd_thumb.KEY_oimg))		return Add(new Xob_xfer_temp_cmd_thumb(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_xfer_temp_cmd_orig.KEY_oimg))		return Add(new Xob_xfer_temp_cmd_orig(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_xfer_regy_cmd.KEY_oimg))			return Add(new Xob_xfer_regy_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_xfer_regy_update_cmd.KEY_oimg))	return Add(new Xob_xfer_regy_update_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_xfer_update_cmd.KEY_oimg))			return Add(new Xob_xfer_update_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_diff_regy_exec_cmd.KEY_oimg))		return Add(new Xob_diff_regy_exec_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_diff_regy_make_cmd.KEY_oimg))		return Add(new Xob_diff_regy_make_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xof_wiki_orig_cmd.KEY_oimg))			return Add(new Xof_wiki_orig_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_download_wkr.KEY_oimg))			return Add(new Xob_download_wkr(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_page_regy_cmd.KEY_oimg))			return Add(new Xob_page_regy_cmd(bldr, wiki));

		else if	(String_.Eq(cmd_key, Xob_wiki_redirect_cmd.KEY_redirect))	return Add(new Xob_wiki_redirect_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_wiki_image_sql.KEY))				return Add(new Xob_wiki_image_sql(bldr, wiki));

		else if	(String_.Eq(cmd_key, Xob_fsdb_make.KEY_oimg))				return Add(new Xob_fsdb_make(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xob_wdata_db_cmd.KEY_oimg))			return Add(new Xob_wdata_db_cmd(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xoctg_link_idx_wkr.KEY))				return Add(new Xoctg_link_idx_wkr(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xoctg_hiddencat_parser_sql.KEY))		return Add(new Xoctg_hiddencat_parser_sql(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xoctg_hiddencat_parser_txt.KEY))		return Add(new Xoctg_hiddencat_parser_txt(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xoctg_hiddencat_ttl_wkr.KEY))			return Add(new Xoctg_hiddencat_ttl_wkr(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xobc_core_make_id.KEY))				return Xml_rdr_direct_add(wiki, new Xobc_core_make_id(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xobc_core_calc_stats.KEY))				return Add(new Xobc_core_calc_stats(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xobc_core_cleanup.KEY))				return Add(new Xobc_core_cleanup(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xobc_core_decompress_bz.KEY))			return Add(new Xobc_core_decompress_bz(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xobc_deploy_zip.KEY))					return Add(new Xobc_deploy_zip(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xobc_deploy_copy.KEY))					return Add(new Xobc_deploy_copy(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xobc_parse_dump_templates.KEY))		return Xml_rdr_direct_add(wiki, new Xobc_parse_dump_templates(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xobc_parse_run.KEY))					return Add(new Xobc_parse_run(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xobc_img_dump_ttl.KEY))				return Xml_rdr_direct_add(wiki, new Xobc_img_dump_ttl(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xobc_img_merge_ttl_sql.KEY))			return Add(new Xobc_img_merge_ttl_sql(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xobc_img_prep_xfer.KEY))				return Add(new Xobc_img_prep_xfer(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xobc_img_run_xfer.KEY))				return Add(new Xobc_img_run_xfer(bldr, wiki));
		else if	(String_.Eq(cmd_key, Xobc_math_run.KEY))					return Add(new Xobc_math_run(bldr, wiki));
		else 																throw Err_.unhandled(cmd_key);
	}
	private Xobd_wkr Xml_rdr_direct_add(Xow_wiki wiki, Xobd_wkr wkr) {
		Xobd_rdr dump_rdr = Xml_rdr_get(wiki);
		dump_rdr.Wkr_add(wkr);
		return wkr;
	}
	private Xobd_parser_wkr Xml_rdr_parser_add(Xow_wiki wiki, Xobd_parser_wkr wkr) {
		Xobd_rdr dump_rdr = Xml_rdr_get(wiki);
		dump_rdr.Page_parser_assert().Wkr_add(wkr);
		return wkr;
	}
	private Xobd_rdr Xml_rdr_get(Xow_wiki wiki) {
		byte[] wiki_key = wiki.Domain_bry();
		Xobd_rdr rv = (Xobd_rdr)dump_rdrs.Fetch(dump_rdrs_ref.Val_(wiki_key));
		if (rv == null) {
			rv = new Xobd_rdr(bldr, wiki);
			dump_rdrs.Add(Bry_obj_ref.new_(wiki_key), rv);
			this.Add(rv);
		}
		return rv;
	}
	private HashAdp dump_rdrs = HashAdp_.new_(); private Bry_obj_ref dump_rdrs_ref = Bry_obj_ref.null_();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if			(ctx.Match(k, Invk_add))				return Add_cmd(Wiki_get_or_make(m), m.ReadStr("v"));
		else if		(ctx.Match(k, Invk_add_many))			return Add_many(m);
		else if		(ctx.Match(k, Invk_get_first))			return Get_first(m);
		else if		(ctx.Match(k, Invk_new_batch))			return new Xobc_core_batch(bldr, m.ReadBry("v"));
		else	return GfoInvkAble_.Rv_unhandled;
	}
	private static final String Invk_add = "add", Invk_add_many = "add_many", Invk_new_batch = "new_batch", Invk_get_first = "get_first";
	private Object Get_first(GfoMsg m) {
		String cmd_key = m.ReadStr("v");
		int cmds_len = list.Count();
		for (int i = 0;i < cmds_len; i++) {
			Xob_cmd cmd = (Xob_cmd)list.FetchAt(i);
			if (String_.Eq(cmd.Cmd_key(), cmd_key)) return cmd;
		}
		throw Err_.new_fmt_("cmd not found; key=~{0}", cmd_key);
	}
	private Object Add_many(GfoMsg m) {
		Xow_wiki wiki = Wiki_get_or_make(m);
		wiki.Lang().Init_by_load_assert();	// NOTE: must check that lang is loaded; else case_mgr will not initialize; DATE:2013-05-11
		int args_len = m.Args_count();
		String[] cmds = new String[args_len - 1];	// -1 b/c 1st arg is wiki
		for (int i = 1; i < args_len; i++) {
			KeyVal kv = m.Args_getAt(i);
			cmds[i - 1] = kv.Val_to_str_or_empty();
		}
		return Add_many(wiki, cmds);
	}
	public Object Add_many(Xow_wiki wiki, String... cmds) {
		int len = cmds.length; if (len == 0) throw Err_mgr._.fmt_(GRP_KEY, Invk_add_many, "add_many cannot have 0 cmds");
		Object rv = null;
		for (int i = 0; i < len; i++)
			rv = Add_cmd(wiki, cmds[i]);
		return rv;
	}
	private Xow_wiki Wiki_get_or_make(GfoMsg m) {
		byte[] wiki_key = m.ReadBry("v");
		Xoa_wiki_mgr wiki_mgr = bldr.App().Wiki_mgr();
		Xow_wiki rv = wiki_mgr.Get_by_key_or_make(wiki_key);
		rv.Lang().Init_by_load();
		return rv;
	}
	public static final String GRP_KEY = "xowa.bldr.cmds";
}
