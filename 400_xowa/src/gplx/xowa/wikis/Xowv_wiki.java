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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
import gplx.core.primitives.*; import gplx.core.net.*; import gplx.core.brys.*; import gplx.core.ios.*;
import gplx.dbs.*;
import gplx.xowa.apps.*;	
import gplx.xowa.guis.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.msgs.*; import gplx.xowa.langs.cases.*; 
import gplx.xowa.files.*; import gplx.xowa.files.origs.*; import gplx.xowa.files.fsdb.*; import gplx.xowa.files.bins.*;
import gplx.xowa.wikis.domains.*; import gplx.xowa.wikis.metas.*; import gplx.xowa.wikis.data.site_stats.*; import gplx.xowa.wikis.data.*; import gplx.xowa.files.repos.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.xwikis.*; import gplx.xowa.wikis.ttls.*; import gplx.xowa.addons.*;
import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.htmls.utls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.hzips.*; import gplx.xowa.htmls.bridges.dbuis.tbls.*; import gplx.xowa.htmls.hrefs.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.fsys.*;
import gplx.xowa.parsers.*;
import gplx.xowa.apps.urls.*;
import gplx.fsdb.*; import gplx.fsdb.meta.*;
import gplx.xowa.specials.mgrs.*;
import gplx.xowa.addons.wikis.htmls.css.bldrs.*; import gplx.xowa.addons.wikis.htmls.css.mgrs.*; import gplx.xowa.addons.wikis.ctgs.htmls.pageboxs.*; import gplx.xowa.addons.wikis.ctgs.htmls.catpages.*;
public class Xowv_wiki implements Xow_wiki, Xow_ttl_parser, Gfo_invk {
	private final    Xof_fsdb_mgr__sql fsdb_mgr; private Fsdb_db_mgr db_core_mgr;
	private boolean init_needed = true;
	public Xowv_wiki(Xoav_app app, byte[] domain_bry, Io_url wiki_root_dir) {
		this.app = app;

		// domain vars
		this.domain_bry = domain_bry;
		this.domain_str = String_.new_u8(domain_bry); 
		this.domain_itm = Xow_domain_itm_.parse(domain_bry);
		this.domain_tid = domain_itm.Domain_type_id();
		this.domain_abrv = Xow_abrv_wm_.To_abrv(domain_itm);

		this.fsys_mgr = new Xow_fsys_mgr(wiki_root_dir, app.Fsys_mgr().File_dir().GenSubDir(domain_str));

		this.ns_mgr = Xow_ns_mgr_.default_(app.Utl_case_mgr());
		this.lang = app.Lang_mgr().Get_by_or_en(domain_itm.Lang_actl_key());	// NOTE: must not be null, or causes null ref exception on redlinks in drd; DATE:2016-06-28
		this.msg_mgr = new Xow_msg_mgr(this, lang);
		this.html__hdump_mgr = new Xow_hdump_mgr(this);
		this.special_mgr = new Xosp_special_mgr(this);
		this.fsdb_mgr = new Xof_fsdb_mgr__sql();
		this.url__parser = new Xow_url_parser(this);
		this.xwiki_mgr = new Xow_xwiki_mgr(this);
		this.stats = new Xowd_site_stats_mgr(this);
		this.lnki_bldr = new Xoh_lnki_bldr(app, href_wtr);
		this.ctg_catpage_mgr = new Xoctg_catpage_mgr(this);
	}
	public Xoa_app						App() {return app;}
	public boolean							Type_is_edit() {return Bool_.N;}
	public byte[]						Domain_bry() {return domain_bry;} private final    byte[] domain_bry;
	public String						Domain_str() {return domain_str;} private final    String domain_str;
	public Xow_domain_itm				Domain_itm() {return domain_itm;} private final    Xow_domain_itm domain_itm;
	public int							Domain_tid() {return domain_tid;} private final    int domain_tid;
	public byte[]						Domain_abrv() {return domain_abrv;} private final    byte[] domain_abrv;
	public Xow_ns_mgr					Ns_mgr() {return ns_mgr;} private final    Xow_ns_mgr ns_mgr;
	public Xow_fsys_mgr					Fsys_mgr() {return fsys_mgr;} private Xow_fsys_mgr fsys_mgr;
	public Xow_db_mgr					Data__core_mgr() {return data_mgr__core_mgr;} private Xow_db_mgr data_mgr__core_mgr;
	public Xow_repo_mgr					File__repo_mgr() {return file_mgr__repo_mgr;} private Xowv_repo_mgr file_mgr__repo_mgr = new Xowv_repo_mgr();
	public Xof_fsdb_mode				File__fsdb_mode() {return file_mgr__fsdb_mode;} private final    Xof_fsdb_mode file_mgr__fsdb_mode = Xof_fsdb_mode.New__v2__gui();
	public Fsdb_db_mgr					File__fsdb_core() {return db_core_mgr;}
	public Xof_orig_mgr					File__orig_mgr() {return orig_mgr;} private final    Xof_orig_mgr orig_mgr = new Xof_orig_mgr();
	public Xof_bin_mgr					File__bin_mgr() {return fsdb_mgr.Bin_mgr();}
	public Fsm_mnt_mgr					File__mnt_mgr() {return fsdb_mgr.Mnt_mgr();}
	public Xoh_lnki_bldr				Html__lnki_bldr() {return lnki_bldr;}  private final    Xoh_lnki_bldr lnki_bldr;
	public Xoh_href_wtr					Html__href_wtr()  {return href_wtr;} private final    Xoh_href_wtr href_wtr = new Xoh_href_wtr();
	public boolean							Html__hdump_enabled() {return Bool_.Y;}
	public Xow_hdump_mgr				Html__hdump_mgr() {return html__hdump_mgr;} private final    Xow_hdump_mgr html__hdump_mgr;
	public boolean							Html__css_installing() {return html__css_installing;} public void Html__css_installing_(boolean v) {html__css_installing = v;} private boolean html__css_installing;
	public Xoh_page_wtr_mgr				Html__wtr_mgr() {return html__wtr_mgr;} private final    Xoh_page_wtr_mgr html__wtr_mgr = new Xoh_page_wtr_mgr(Bool_.Y);
	public Xoctg_pagebox_wtr			Ctg__pagebox_wtr() {return ctg_pagebox_wtr;} private final    Xoctg_pagebox_wtr ctg_pagebox_wtr = new Xoctg_pagebox_wtr();
	public Xoctg_catpage_mgr			Ctg__catpage_mgr() {return ctg_catpage_mgr;} private final    Xoctg_catpage_mgr ctg_catpage_mgr;
	public Xow_msg_mgr					Msg_mgr() {return msg_mgr;} private final    Xow_msg_mgr msg_mgr;
	public byte[]						Wtxt__expand_tmpl(byte[] src) {return src;}
	public Xow_mw_parser_mgr			Mw_parser_mgr() {return mw_parser_mgr;} private final    Xow_mw_parser_mgr mw_parser_mgr = new Xow_mw_parser_mgr();
	public Xow_wiki_props				Props() {return props;} private final    Xow_wiki_props props = new Xow_wiki_props();
	public Xol_lang_itm					Lang() {return lang;} private final    Xol_lang_itm lang;
	public Xol_case_mgr					Case_mgr() {if (case_mgr == null) case_mgr = Xol_case_mgr_.U8(); return case_mgr;} private Xol_case_mgr case_mgr;
	public Xowd_site_stats_mgr			Stats() {return stats;} private final    Xowd_site_stats_mgr stats;
	public Bry_bfr_mkr					Utl__bfr_mkr()		{return utl__bry_bfr_mkr;}	private final    Bry_bfr_mkr utl__bry_bfr_mkr = new Bry_bfr_mkr();
	public Io_stream_zip_mgr			Utl__zip_mgr()		{return utl__zip_mgr;}		private final    Io_stream_zip_mgr utl__zip_mgr = new Io_stream_zip_mgr();
	public Xow_url_parser				Utl__url_parser()	{return url__parser;}		private final    Xow_url_parser url__parser;
	public Xoax_addon_mgr				Addon_mgr() {return addon_mgr;} private final    Xoax_addon_mgr addon_mgr = new Xoax_addon_mgr();
	public Xosp_special_mgr				Special_mgr() {return special_mgr;} private Xosp_special_mgr special_mgr;
	public Xow_xwiki_mgr				Xwiki_mgr() {return xwiki_mgr;} private final    Xow_xwiki_mgr xwiki_mgr;
	public Xoav_app						Appv() {return app;} private final    Xoav_app app;
	public void Init_by_wiki() {
		if (!init_needed) return;
		init_needed = false;
		if (String_.Eq(domain_str, "xowa")) return;					// HACK: ignore "xowa" for now; WHEN:converting xowa to sqlitedb
		data_mgr__core_mgr = new Xow_db_mgr(fsys_mgr.Root_dir(), this.domain_str);
		Xow_db_mgr.Init_by_load(this, gplx.xowa.wikis.data.Xow_db_file__core_.Find_core_fil_or_null(this));
		app.Html__css_installer().Install(this, Xowd_css_core_mgr.Key_mobile);	// must init after data_mgr
		this.db_core_mgr = Fsdb_db_mgr_.new_detect(this, fsys_mgr.Root_dir(), fsys_mgr.File_dir());
		if (db_core_mgr == null)	// no fsdb; occurs during merge; also, will be null for xowa_db; DATE:2016-05-31
			db_core_mgr = Fsdb_db_mgr__v2_bldr.Get_or_make(this, true);
		else						// fsdb exists; load it
			fsdb_mgr.Mnt_mgr().Ctor_by_load(db_core_mgr);
		file_mgr__repo_mgr.Add_repo(app, fsys_mgr.File_dir(), Xow_domain_itm_.Bry__commons, Xow_domain_itm_.Bry__commons);
		file_mgr__repo_mgr.Add_repo(app, fsys_mgr.File_dir(), domain_bry, domain_bry);
		Xof_orig_tbl[] orig_tbls = db_core_mgr == null ? new Xof_orig_tbl[0] : db_core_mgr.File__orig_tbl_ary();
		orig_mgr.Init_by_wiki(this, file_mgr__fsdb_mode, orig_tbls, Xof_url_bldr.new_v2());
		fsdb_mgr.Init_by_wiki(this);
		data_mgr__core_mgr.Db__core().Tbl__ns().Select_all(ns_mgr);
		data_mgr__core_mgr.Db__core().Tbl__site_stats().Select(stats);
		html__hdump_mgr.Init_by_db(this);
		app.Addon_mgr().Load_by_wiki(this);
		ctg_pagebox_wtr.Init_by_wiki(this);
		ctg_catpage_mgr.Init_by_wiki(this);
	}
	public void	Init_by_wiki__force() {init_needed = true; Init_by_wiki();}
	public void Init_by_make(Xowd_core_db_props props, gplx.xowa.bldrs.infos.Xob_info_session info_session) {
		data_mgr__core_mgr = new Xow_db_mgr(fsys_mgr.Root_dir(), this.domain_str);
		data_mgr__core_mgr.Init_by_make(props, info_session);
		html__hdump_mgr.Init_by_db(this);
	}
	public void Rls() {
		data_mgr__core_mgr.Rls();
		fsdb_mgr.Rls();
	}
	public void Pages_get(Xoh_page rv, Gfo_url url, Xoa_ttl ttl) {
		if (init_needed) Init_by_wiki();
		if (ttl.Ns().Id_is_special())
			special_mgr.Get_by_ttl(rv, url, ttl);
		else
			html__hdump_mgr.Load_mgr().Load_by_xowh(rv, ttl, Bool_.Y);
	}
	public Xoa_ttl	Ttl_parse(byte[] ttl)								{return Ttl_parse(ttl, 0, ttl.length);}
	public Xoa_ttl	Ttl_parse(byte[] src, int src_bgn, int src_end)		{return Xoa_ttl.Parse(app.Utl_amp_mgr(), app.Utl_case_mgr(), xwiki_mgr, ns_mgr, src, src_bgn, src_end);}
	public Xoa_ttl	Ttl_parse(int ns_id, byte[] ttl)					{return Xoa_ttl.Parse(this, ns_id, ttl);}
	public void Init_needed_y_() {this.init_needed = true;}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {throw Err_.new_unimplemented_w_msg("implemented for Xoa_cfg_mgr");}
}
