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
package gplx.xowa.xtns.gallery; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.primitives.*; import gplx.dbs.cfgs.*;
import gplx.fsdb.meta.*;
import gplx.xowa.parsers.logs.*;
import gplx.xowa.html.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
public class Gallery_xnde implements Xox_xnde, Xop_xnde_atr_parser {
	private Gallery_xtn_mgr xtn_mgr;
	public byte Mode()						{return mode;} private byte mode;
	public int Itm_w()						{return itm_w;} private int itm_w = Null;
	public int Itm_h()						{return itm_h;} private int itm_h = Null;
	public int Itms_per_row()				{return itms_per_row;} private int itms_per_row = Null;
	public boolean Show_filename()				{return show_filename;} private boolean show_filename = false;
	public byte[] Atr_caption()				{return atr_caption;} private byte[] atr_caption = Bry_.Empty;
	public byte[] Atr_style()				{return atr_style;} private byte[] atr_style = Bry_.Empty;
	public byte[] Atr_cls()					{return atr_cls;} private byte[] atr_cls = Bry_.Empty;
	public ListAdp Atrs_other()				{return atrs_other;} private ListAdp atrs_other;
	public int Itm_w_or_default()			{return itm_w == Null ? Default : itm_w;}
	public int Itm_h_or_default()			{return itm_h == Null ? Default : itm_h;}
	public int Itms_len()					{return itms.Count();} private ListAdp itms = ListAdp_.new_();
	public Gallery_itm Itms_get_at(int i)	{return (Gallery_itm)itms.FetchAt(i);}
	public Gallery_mgr_base Gallery_mgr()	{return gallery_mgr;} private Gallery_mgr_base gallery_mgr;
	private boolean html_wtr_v1 = false;
	public void Xatr_parse(Xowe_wiki wiki, byte[] src, Xop_xatr_itm xatr, Object xatr_key_obj) {
		if (xatr_key_obj != null) {
			Byte_obj_val xatr_key = (Byte_obj_val)xatr_key_obj;
			switch (xatr_key.Val()) {
				case Gallery_xnde_atrs.Mode_tid:			mode = Gallery_mgr_base_.Get_or_traditional(xatr.Val_as_bry(src)); break;
				case Gallery_xnde_atrs.Perrow_tid:			itms_per_row = xatr.Val_as_int_or(src, Null); break;
				case Gallery_xnde_atrs.Widths_tid:			itm_w = xatr.Val_as_int_or(src, Null); break;
				case Gallery_xnde_atrs.Heights_tid:			itm_h = xatr.Val_as_int_or(src, Null); break;
				case Gallery_xnde_atrs.Showfilename_tid:	show_filename = xatr.Val_as_bool(src); break;
				case Gallery_xnde_atrs.Caption_tid:			if (!xatr.Tid_is_key_only()) atr_caption = xatr.Val_as_bry(src); break;	// NOTE: do not create caption for key only; EX:<gallery caption=> PAGE:fr.w:Chronologie_du_si�ge_de_Paris_(1870); DATE:2014-08-15
				case Gallery_xnde_atrs.Style_tid:			atr_style = xatr.Val_as_bry(src); break;
				case Gallery_xnde_atrs.Class_tid:			atr_cls = xatr.Val_as_bry(src); break;
			}
		}
		else {
			if (atrs_other == null) atrs_other = ListAdp_.new_();
			atrs_other.Add(xatr);
		}
	}
	public void Xtn_parse(Xowe_wiki wiki, Xop_ctx ctx, Xop_root_tkn root, byte[] src, Xop_xnde_tkn xnde) {
		try {
			ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_bgn);			// cancel pre for <gallery>; DATE:2014-03-11
			Xop_xatr_itm.Xatr_parse(wiki.Appe(), this, Gallery_xnde_atrs.Key_hash, wiki, src, xnde);
			xtn_mgr = (Gallery_xtn_mgr)wiki.Xtn_mgr().Get_or_fail(Gallery_xtn_mgr.XTN_KEY);
			Init_atrs(wiki);
			gallery_mgr.Get_modules(ctx.Cur_page());
			xtn_mgr.Parser().Parse_all(itms, gallery_mgr, this, src, xnde.Tag_open_end(), xnde.Tag_close_bgn());
			boolean log_wkr_enabled = Log_wkr != Xop_log_basic_wkr.Null; if (log_wkr_enabled) Log_wkr.Log_end_xnde(ctx.Cur_page(), Xop_log_basic_wkr.Tid_gallery, src, xnde);
			ctx.Para().Process_block__xnde(xnde.Tag(), Xop_xnde_tag.Block_end);			// cancel pre for <gallery>; DATE:2014-03-11
		} catch (Exception exc) {
			wiki.Appe().Usr_dlg().Warn_many("", "", "failed to write gallery; src=~{0} err=~{1}", String_.new_utf8_(src, xnde.Src_bgn(), xnde.Src_end()), Err_.Message_gplx(exc));
		}
	}	public static Xop_log_basic_wkr Log_wkr = Xop_log_basic_wkr.Null;
	public void Xtn_write(Bry_bfr bfr, Xoae_app app, Xop_ctx ctx, Xoh_html_wtr html_wtr, Xoh_wtr_ctx hctx, Xop_xnde_tkn xnde, byte[] src) {
		Xowe_wiki wiki = ctx.Wiki();
		try {
			if (html_wtr_v1)
				xtn_mgr.Html_wtr().Write_html(bfr, app, wiki, ctx, html_wtr, hctx, ctx.Cur_page(), this, src);
			else {
				gallery_mgr.Write_html(bfr, wiki, ctx.Cur_page(), ctx, hctx, src, this);
			}
		} catch (Exception exc) {
			wiki.Appe().Usr_dlg().Warn_many("", "", "failed to write gallery; src=~{0} err=~{1}", String_.new_utf8_(src, xnde.Src_bgn(), xnde.Src_end()), Err_.Message_gplx(exc));
		}
	}
	private void Init_atrs(Xowe_wiki wiki) {
		Db_cfg_grp cfg_grp = wiki.File_mgr().Cfg_get(Xof_fsdb_mgr_cfg.Grp_xowa);
		if (cfg_grp.Get_yn_or_n(Xof_fsdb_mgr_cfg.Key_gallery_fix_defaults)) {
			if (itm_w == Gallery_xnde.Null && itm_h == Gallery_xnde.Null)	// if no w/h specified, set both to default (just like v1)
				itm_w = itm_h = Gallery_xnde.Default;
		}
		else {
			if (itm_w == Gallery_xnde.Null) itm_w = Gallery_xnde.Default;
			if (itm_h == Gallery_xnde.Null) itm_h = Gallery_xnde.Default;
		}
		gallery_mgr = Gallery_mgr_base_.New_by_mode(mode);
		if (	!wiki.File_mgr().Version_1_y()											// v2: fsdb
			&&	!cfg_grp.Get_yn_or_n(Xof_fsdb_mgr_cfg.Key_gallery_packed) 				// packed not supported
			) {
			gallery_mgr = Gallery_mgr_base_.New_by_mode(Gallery_mgr_base_.Traditional_tid);	// always go to traditional
			html_wtr_v1 = true;
		}
		gallery_mgr.Init(itms_per_row, this.Itm_w_or_default(), this.Itm_h_or_default());
	}
	public static final int Default = 120, Null = -1;
}
class Gallery_xnde_atrs {
	public static final byte 
	  Mode_tid			= 0
	, Perrow_tid		= 1
	, Widths_tid		= 2
	, Heights_tid		= 3
	, Caption_tid		= 4
	, Showfilename_tid	= 5
	, Style_tid			= 6
	, Class_tid			= 7
	;
	public static Hash_adp_bry Key_hash = Hash_adp_bry.ci_ascii_()
	.Add_str_byte("mode"			, Mode_tid)
	.Add_str_byte("perrow"			, Perrow_tid)
	.Add_str_byte("widths"			, Widths_tid)
	.Add_str_byte("heights"			, Heights_tid)
	.Add_str_byte("caption"			, Caption_tid)
	.Add_str_byte("showfilename"	, Showfilename_tid)
	.Add_str_byte("style"			, Style_tid)
	.Add_str_byte("class"			, Class_tid)
	;
}
