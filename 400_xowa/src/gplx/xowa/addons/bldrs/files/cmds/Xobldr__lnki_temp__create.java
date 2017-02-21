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
package gplx.xowa.addons.bldrs.files.cmds; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.files.*;
import gplx.dbs.*; import gplx.dbs.cfgs.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.files.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.wikis.*; import gplx.xowa.wikis.domains.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.logs.*; import gplx.xowa.parsers.lnkis.*; import gplx.xowa.parsers.xndes.*;
import gplx.xowa.htmls.core.bldrs.*; import gplx.xowa.xtns.scribunto.*; import gplx.xowa.xtns.wbases.*;
import gplx.fsdb.meta.*; import gplx.xowa.files.fsdb.*; import gplx.fsdb.*;
import gplx.xowa.langs.vnts.*; import gplx.xowa.parsers.vnts.*;
import gplx.xowa.parsers.lnkis.files.*;
import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.cmds.*; import gplx.xowa.bldrs.wkrs.*;
import gplx.xowa.addons.bldrs.files.dbs.*; import gplx.xowa.addons.bldrs.mass_parses.parses.*; import gplx.xowa.addons.bldrs.mass_parses.parses.utls.*;
import gplx.xowa.addons.bldrs.wmdumps.imglinks.*;
public class Xobldr__lnki_temp__create extends Xob_dump_mgr_base implements gplx.xowa.parsers.lnkis.files.Xop_file_logger {
	private Xob_lnki_temp_tbl tbl; private boolean wdata_enabled = true, xtn_ref_enabled = true, gen_html, gen_hdump, load_all_imglinks;
	private Xop_log_invoke_wkr invoke_wkr; private Xop_log_property_wkr property_wkr;		
	private boolean ns_file_is_case_match_all = true; private Xowe_wiki commons_wiki;
	private final    Xob_hdump_bldr hdump_bldr = new Xob_hdump_bldr(); private Vnt_convert_lang converter_lang;
	public Xobldr__lnki_temp__create(Xob_bldr bldr, Xowe_wiki wiki) {this.Cmd_ctor(bldr, wiki);}
	@Override public byte Init_redirect()	{return Bool_.N_byte;}	// lnki_temp does not look at redirect pages
	@Override public int[] Init_ns_ary()		{return ns_ids;} private int[] ns_ids = Int_.Ary(Xow_ns_.Tid__main);
	@Override protected void Init_reset(Db_conn conn) {
		Db_cfg_tbl cfg_tbl = gplx.xowa.wikis.data.Xowd_cfg_tbl_.New(conn);
		cfg_tbl.Delete_all();
		invoke_wkr.Init_reset();
		property_wkr.Init_reset();
	}
	@Override protected Db_conn Init_db_file() {
		ctx.Lnki().File_logger_(this);
		Xob_db_file make_db = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir());
		Db_conn make_conn = make_db.Conn();
		this.tbl = new Xob_lnki_temp_tbl(make_conn); tbl.Create_tbl();
		this.gen_hdump = hdump_bldr.Init(wiki, make_conn, new Xob_hdump_tbl_retriever__ns_to_db(wiki));
		Xol_vnt_mgr vnt_mgr = wiki.Lang().Vnt_mgr();
		if (vnt_mgr.Enabled()) {
			this.converter_lang = vnt_mgr.Convert_lang();
			converter_lang.Log__init(make_conn);
		}
		return make_conn;
	}
	@Override protected void Cmd_bgn_end() {
		ns_file_is_case_match_all = Ns_file_is_case_match_all(wiki);							// NOTE: must call after wiki.init
		wiki.Html_mgr().Page_wtr_mgr().Wkr(Xopg_page_.Tid_read).Ctgs_enabled_(false);			// disable categories else progress messages written (also for PERF)
		if (wiki.File__bin_mgr() != null)
			wiki.File__bin_mgr().Wkrs__del(gplx.xowa.files.bins.Xof_bin_wkr_.Key_http_wmf);		// remove wmf wkr, else will try to download images during parsing
		commons_wiki = app.Wiki_mgr().Get_by_or_make(Xow_domain_itm_.Bry__commons);

		// create imglinks
		Xof_orig_wkr__img_links orig_wkr = new Xof_orig_wkr__img_links(wiki);
		wiki.File__orig_mgr().Wkrs__set(orig_wkr);
		if (load_all_imglinks) Xof_orig_wkr__img_links_.Load_all(orig_wkr);

		Xow_wiki_utl_.Clone_repos(wiki);

		// init log_mgr / property_wkr
		Xop_log_mgr log_mgr = ctx.App().Log_mgr();
		log_mgr.Log_dir_(wiki.Fsys_mgr().Root_dir());	// put log in wiki dir, instead of user.temp
		invoke_wkr = this.Invoke_wkr();					// set member reference
		invoke_wkr = log_mgr.Make_wkr_invoke();
		property_wkr = this.Property_wkr();				// set member reference
		property_wkr = log_mgr.Make_wkr_property();
		wiki.Appe().Wiki_mgr().Wdata_mgr().Enabled_(wdata_enabled);
		if (!xtn_ref_enabled) gplx.xowa.xtns.cites.References_nde.Enabled = false;

		// init log wkrs
		gplx.xowa.xtns.gallery.Gallery_xnde.Log_wkr = log_mgr.Make_wkr().Save_src_str_(Bool_.Y);
		gplx.xowa.xtns.imaps.Imap_xnde.Log_wkr = log_mgr.Make_wkr();
		gplx.xowa.parsers.xndes.Xop_xnde_wkr.Timeline_log_wkr = log_mgr.Make_wkr();
		gplx.xowa.xtns.scores.Score_xnde.Log_wkr = log_mgr.Make_wkr();
		gplx.xowa.xtns.hieros.Hiero_xnde.Log_wkr = log_mgr.Make_wkr();
		gplx.xowa.xtns.math.Xomath_xnde.Log_wkr = log_mgr.Make_wkr().Save_src_str_(Bool_.Y);	// enabled; DATE:2015-10-10

		// init fsdb
		Xof_fsdb_mgr__sql trg_fsdb_mgr = new Xof_fsdb_mgr__sql();
		wiki.File__fsdb_mode().Tid__v2__bld__y_();
		Fsdb_db_mgr__v2 fsdb_core = Fsdb_db_mgr__v2_bldr.Get_or_make(wiki, Bool_.Y);
		trg_fsdb_mgr.Init_by_wiki(wiki);
		Fsm_mnt_mgr trg_mnt_mgr = trg_fsdb_mgr.Mnt_mgr();
		wiki.File_mgr().Init_file_mgr_by_load(wiki);										// must happen after fsdb.make
		wiki.File__bin_mgr().Wkrs__del(gplx.xowa.files.bins.Xof_bin_wkr_.Key_http_wmf);		// must happen after init_file_mgr_by_load; remove wmf wkr, else will try to download images during parsing
		wiki.File__orig_mgr().Wkrs__del(gplx.xowa.files.origs.Xof_orig_wkr_.Tid_wmf_api);

		trg_mnt_mgr = new Fsm_mnt_mgr(); trg_mnt_mgr.Ctor_by_load(fsdb_core);
		trg_mnt_mgr.Mnts__get_insert_idx_(Fsm_mnt_mgr.Mnt_idx_main);
		Fsm_mnt_mgr.Patch(trg_mnt_mgr.Mnts__get_main().Cfg_mgr().Tbl()); // NOTE: see fsdb_make; DATE:2014-04-26
		tbl.Insert_bgn();
		log_mgr.Txn_bgn();
	}
	@Override public void Exec_pg_itm_hook(int ns_ord, Xow_ns ns, Xowd_page_itm db_page, byte[] page_src) {
		Xoa_ttl ttl = Xoa_ttl.Parse(wiki, ns.Gen_ttl(db_page.Ttl_page_db()));
		byte[] ttl_bry = ttl.Page_db();
		byte page_tid = Xow_page_tid.Identify(wiki.Domain_tid(), ns.Id(), ttl_bry);
		if (page_tid != Xow_page_tid.Tid_wikitext) return; // ignore js, css, lua, json
		Xoae_page page = ctx.Page();
		page.Clear_all();
		page.Bldr__ns_ord_(ns_ord);
		page.Ttl_(ttl);
		page.Db().Page().Id_(db_page.Id());
		page.Html_data().Redlink_list().Clear();
		page.Url_(Xoa_url.New(wiki, ttl));
		if (ns.Id_is_tmpl())
			parser.Parse_text_to_defn_obj(ctx, ctx.Tkn_mkr(), wiki.Ns_mgr().Ns_template(), ttl_bry, page_src);
		else {
			parser.Parse_page_all_clear(root, ctx, ctx.Tkn_mkr(), page_src);
			if (	gen_html
				&&	page.Redirect_trail().Itms__len() == 0)	// don't generate html for redirected pages
				wiki.Html_mgr().Page_wtr_mgr().Gen(ctx.Page().Root_(root), Xopg_page_.Tid_read);
			if (gen_hdump)
				hdump_bldr.Insert(ctx, page.Root_(root));
			root.Clear();
		}
	}
	@Override public void Exec_commit_hook() {
		tbl.Conn().Txn_sav();
//			if (converter_lang != null) converter_lang.Log__save();
		if (gen_hdump) {
			hdump_bldr.Commit();
		}
	}
	@Override public void Exec_end_hook() {
//			if (converter_lang != null) converter_lang.Log__rls();
		if (gen_hdump) hdump_bldr.Term();
		String err_filter_mgr = invoke_wkr.Err_filter_mgr().Print();
		if (String_.Len_gt_0(err_filter_mgr)) usr_dlg.Warn_many("", "", err_filter_mgr);
		wiki.Appe().Log_mgr().Txn_end();
		tbl.Insert_end();
	}
	public void Log_file(Xop_ctx ctx, Xop_lnki_tkn lnki, byte caller_tid) {
		if (lnki.Ttl().ForceLiteralLink()) return; // ignore literal links which creat a link to file, but do not show the image; EX: [[:File:A.png|thumb|120px]] creates a link to File:A.png, regardless of other display-oriented args
		byte[] ttl = lnki.Ttl().Page_db();
		Xof_ext ext = Xof_ext_.new_by_ttl_(ttl);
		double lnki_time = lnki.Time();
		int lnki_page = lnki.Page();
		byte[] ttl_commons = Xomp_lnki_temp_wkr.To_commons_ttl(ns_file_is_case_match_all, commons_wiki, ttl);
		if (	Xof_lnki_page.Null_n(lnki_page) 				// page set
			&&	Xof_lnki_time.Null_n(lnki_time))				// thumbtime set
				usr_dlg.Warn_many("", "", "page and thumbtime both set; this may be an issue with fsdb: page=~{0} ttl=~{1}", ctx.Page().Ttl().Page_db_as_str(), String_.new_u8(ttl));
		if (lnki.Ns_id() == Xow_ns_.Tid__media)
			caller_tid = Xop_file_logger_.Tid__media;
		tbl.Insert_cmd_by_batch(ctx.Page().Bldr__ns_ord(), ctx.Page().Db().Page().Id(), ttl, ttl_commons, Byte_.By_int(ext.Id()), lnki.Lnki_type(), caller_tid, lnki.W(), lnki.H(), lnki.Upright(), lnki_time, lnki_page);
	}
	@Override public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_wdata_enabled_))				wdata_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_xtn_ref_enabled_))			xtn_ref_enabled = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_ns_ids_))					ns_ids = Int_.Ary_parse(m.ReadStr("v"), "|");
		else if	(ctx.Match(k, Invk_ns_ids_by_aliases))			ns_ids = Xobldr__lnki_temp__create_.Ns_ids_by_aliases(wiki, m.ReadStrAry("v", "|"));
		else if	(ctx.Match(k, Invk_gen_html_))					gen_html = m.ReadYn("v");
		else if	(ctx.Match(k, Invk__load_all_imglinks_))		load_all_imglinks = m.ReadYn("v");
		else if	(ctx.Match(k, Invk_hdump_bldr))					return hdump_bldr;
		else if	(ctx.Match(k, Invk_property_wkr))				return this.Property_wkr();
		else if	(ctx.Match(k, Invk_invoke_wkr))					return this.Invoke_wkr();
		else	return super.Invk(ctx, ikey, k, m);
		return this;
	}
	private static final String Invk_wdata_enabled_ = "wdata_enabled_", Invk_xtn_ref_enabled_ = "xtn_ref_enabled_", Invk_gen_html_ = "gen_html_"
	, Invk_ns_ids_ = "ns_ids_", Invk_ns_ids_by_aliases = "ns_ids_by_aliases"
	, Invk_invoke_wkr = "invoke_wkr", Invk_property_wkr = "property_wkr", Invk_hdump_bldr = "hdump_bldr"
	, Invk__load_all_imglinks_ = "load_all_imglinks_"
	;
	public static final String BLDR_CMD_KEY = "file.lnki_temp";
	@Override public String Cmd_key() {return BLDR_CMD_KEY;} 
	public static final    Xob_cmd Prototype = new Xobldr__lnki_temp__create(null, null);
	@Override public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return new Xobldr__lnki_temp__create(bldr, wiki);}

	private Xop_log_invoke_wkr Invoke_wkr() {
		if (invoke_wkr == null) invoke_wkr = ((Scrib_xtn_mgr)bldr.App().Xtn_mgr().Get_or_fail(Scrib_xtn_mgr.XTN_KEY)).Invoke_wkr_or_new();
		return invoke_wkr;
	}
	private Xop_log_property_wkr Property_wkr() {
		if (property_wkr == null) property_wkr = bldr.App().Wiki_mgr().Wdata_mgr().Property_wkr_or_new();
		return property_wkr;
	}
	public static boolean Ns_file_is_case_match_all(Xow_wiki wiki) {return wiki.Ns_mgr().Ns_file().Case_match() == Xow_ns_case_.Tid__all;}
}
