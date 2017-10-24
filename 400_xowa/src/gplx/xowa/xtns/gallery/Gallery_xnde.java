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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.htmls.*;
import gplx.dbs.cfgs.*; import gplx.xowa.parsers.logs.*; 
import gplx.xowa.htmls.core.htmls.*; import gplx.xowa.files.fsdb.*;
public class Gallery_xnde implements Xox_xnde, Mwh_atr_itm_owner2 {
	private Gallery_xtn_mgr xtn_mgr;
	public byte					Mode()						{return mode;} private byte mode;
	public int					Itm_w_orig()				{return itm_w_orig;} private int itm_w_orig = Null;
	public int					Itm_h_orig()				{return itm_h_orig;} private int itm_h_orig = Null;
	public int					Itm_w_actl()				{return itm_w_actl;} private int itm_w_actl = Null;
	public int					Itm_h_actl()				{return itm_h_actl;} private int itm_h_actl = Null;
	public int					Itms_per_row()				{return itms_per_row;} private int itms_per_row = Null;
	public int					Itms_len()					{return itms.Count();} private final    List_adp itms = List_adp_.New();
	public Gallery_itm			Itms_get_at(int i)			{return (Gallery_itm)itms.Get_at(i);}
	public byte[]				Atr_style()					{return atr_style;} private byte[] atr_style = Bry_.Empty;
	public byte[]				Atr_cls()					{return atr_cls;} private byte[] atr_cls = Bry_.Empty;
	public List_adp				Atrs_other()				{return atrs_other;} private List_adp atrs_other;
	public boolean				Show_filename()				{return show_filename;} private boolean show_filename = false;
	public byte[]				Mgr_caption()				{return mgr_caption;} private byte[] mgr_caption = Bry_.Empty;
	public Gallery_mgr_base		Gallery_mgr()				{return gallery_mgr;} private Gallery_mgr_base gallery_mgr;
	public void Xatr__set(Xowe_wiki wiki, byte[] src, Mwh_atr_itm xatr, byte xatr_id) {
		switch (xatr_id) {
			case Gallery_xnde_atrs.Tid__mode:			mode = Gallery_mgr_base_.To_tid_or_traditional(xatr.Val_as_bry()); break;
			case Gallery_xnde_atrs.Tid__perrow:			itms_per_row = xatr.Val_as_int_or(Null); break;
			case Gallery_xnde_atrs.Tid__widths:			itm_w_orig = itm_w_actl = xatr.Val_as_int_or(Null); break;
			case Gallery_xnde_atrs.Tid__heights:		itm_h_orig = itm_h_actl = xatr.Val_as_int_or(Null); break;
			case Gallery_xnde_atrs.Tid__style:			atr_style = xatr.Val_as_bry(); break;
			case Gallery_xnde_atrs.Tid__class:			atr_cls = xatr.Val_as_bry(); break;
			case Gallery_xnde_atrs.Tid__showfilename:	show_filename = xatr.Val_as_bool(); break;
			case Gallery_xnde_atrs.Tid__caption:		if (xatr.Key_exists()) mgr_caption = xatr.Val_as_bry(); break;	// NOTE: do not create caption for key only; EX:<gallery caption=> PAGE:fr.w:Chronologie_du_si�ge_de_Paris_(1870); DATE:2014-08-15
			default:
				if (atrs_other == null) atrs_other = List_adp_.New();
				atrs_other.Add(xatr);
				break;
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		Xox_xnde_.Parse_xatrs(wiki, this, Gallery_xnde_atrs.Key_hash, src, xnde);
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_bgn);			// cancel pre for <gallery>; DATE:2014-03-11
		try {
			// do further post-processing of vars based on wiki's fsdb.cfg
			Init_xnde_by_atrs(wiki);
			gallery_mgr.Get_modules(ctx.Page());

			// parse
			xtn_mgr = (Gallery_xtn_mgr)wiki.Xtn_mgr().Get_or_fail(Gallery_xtn_mgr.XTN_KEY);
			Gallery_parser parser = xtn_mgr.Parser();
			if (parser.Parse_in_progress()) parser = new Gallery_parser().Init_by_wiki(wiki);	// handle nested galleries; EX: <gallery><ref><gallery>; PAGE:es.w:Arquitectura_medieval DATE:2015-07-10
			parser.Parse_all(itms, gallery_mgr, this, src, xnde.Tag_open_end(), xnde.Tag_close_bgn());

			// if packed, enable flag; needed for reload in Xog_async_wkr; PAGE:en.w:Mexico; DATE:2016-08-14
			if (Gallery_mgr_base_.Mode_is_packed(this.mode))
				ctx.Page().Xtn_gallery_packed_exists_y_();
		} catch (Exception exc) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to write gallery; src=~{0} err=~{1}", String_.new_u8(src, xnde.Src_bgn(), xnde.Src_end()), Err_.Message_gplx_full(exc));
		}
		ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);			// cancel pre for <gallery>; DATE:2014-03-11
		boolean log_wkr_enabled = Log_wkr != Xop_log_basic_wkr.Null; if (log_wkr_enabled) Log_wkr.Log_end_xnde(ctx.Page(), Xop_log_basic_wkr.Tid_gallery, src, xnde);
	}	public static Xop_log_basic_wkr Log_wkr = Xop_log_basic_wkr.Null;
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xoae_page wpg, Xop_xnde_tkn xnde, byte[] src) {
		try		{Gallery_mgr_wtr.Write_mgr(bfr, gallery_mgr, ctx.Wiki(), ctx.Page(), ctx, hctx, src, this);}
		catch	(Exception e) {Gfo_usr_dlg_.Instance.Warn_many("", "", "failed to write gallery; src=~{0} err=~{1}", String_.new_u8(src, xnde.Src_bgn(), xnde.Src_end()), Err_.Message_gplx_log(e));}
	}
	private void Init_xnde_by_atrs(Xowe_wiki wiki) {
		Db_cfg_hash cfg_grp = wiki.File_mgr().Cfg_get(Xof_fsdb_mgr_cfg.Grp_xowa);
		if (cfg_grp.Get_by(Xof_fsdb_mgr_cfg.Key_gallery_fix_defaults).To_yn_or_n()) {
			if (itm_w_actl == Gallery_xnde.Null && itm_h_actl == Gallery_xnde.Null)	// if no w/h specified, set both to default (just like v1)
				itm_w_actl = itm_h_actl = Gallery_xnde.Default;
		}
		gallery_mgr = Gallery_mgr_base_.New(mode);
		if (	!wiki.File_mgr().Version_1_y()												// v2: fsdb
			&&	!cfg_grp.Get_by(Xof_fsdb_mgr_cfg.Key_gallery_packed).To_yn_or_n() 			// packed not supported
			) {
			gallery_mgr = Gallery_mgr_base_.New(Gallery_mgr_base_.Tid__traditional);		// always go to traditional
		}
		if (itm_w_actl == Gallery_xnde.Null) itm_w_actl = Gallery_xnde.Default;
		if (itm_h_actl == Gallery_xnde.Null) itm_h_actl = Gallery_xnde.Default;
		gallery_mgr.Init(this.Itm_w_actl(), this.Itm_h_actl(), itms_per_row);
		if (Bry_.Eq(wiki.Domain_itm().Domain_bry(), gplx.xowa.wikis.domains.Xow_domain_itm_.Bry__commons)) {	// HACK.CFG: for commons, hardcode itms per row as 8; DATE:2016-06-30
			itms_per_row = 8;
		}
	}
	public static final int Default = 120;
	private static final int Null = -1;
}
