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
package gplx.xowa; import gplx.*;
public class Xot_tmpl_wtr {
	public byte[] Write_all(Xop_ctx ctx, Xop_root_tkn root, byte[] src) {
		Bry_bfr rslt_bfr = ctx.App().Utl_bry_bfr_mkr().Get_m001();
		ctx.Tmpl_output_(rslt_bfr);
		rslt_bfr.Reset_if_gt(Io_mgr.Len_mb);
		Write_tkn(ctx, src, src.length, rslt_bfr, root);
		ctx.Tmpl_output_(null);
		return rslt_bfr.Mkr_rls().Xto_bry_and_clear();
	}
	private void Write_tkn(Xop_ctx ctx, byte[] src, int src_len, Bry_bfr rslt_bfr, Xop_tkn_itm tkn) {
		switch (tkn.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_root:											// write each sub
				int subs_len = tkn.Subs_len();
				for (int i = 0; i < subs_len; i++) {
					Xop_tkn_itm sub_tkn = tkn.Subs_get(i);
					if (!sub_tkn.Ignore())
						Write_tkn(ctx, src, src_len, rslt_bfr, sub_tkn);
				}
				break;
			case Xop_tkn_itm_.Tid_bry:
				Xop_bry_tkn bry = (Xop_bry_tkn)tkn;
				rslt_bfr.Add(bry.Val());
				break;
			case Xop_tkn_itm_.Tid_space:
				if (tkn.Tkn_immutable())
					rslt_bfr.Add_byte(Byte_ascii.Space);
				else
					rslt_bfr.Add_byte_repeat(Byte_ascii.Space, tkn.Src_end() - tkn.Src_bgn());
				break;
			case Xop_tkn_itm_.Tid_xnde:
				Xop_xnde_tkn xnde = (Xop_xnde_tkn)tkn;
				int xnde_tag_id = xnde.Tag().Id();
				switch (xnde_tag_id) {
					case Xop_xnde_tag_.Tid_onlyinclude: {
						// NOTE: originally "if (ctx.Parse_tid() == Xop_parser_.Parse_tid_page_tmpl) {" but if not needed; Xot_tmpl_wtr should not be called for tmpls and <oi> should not make it to page_wiki
						Bry_bfr tmp_bfr = Bry_bfr.new_();
						ctx.Only_include_evaluate_(true);
						xnde.Tmpl_evaluate(ctx, src, Xot_invk_temp.Page_is_caller, tmp_bfr);
						ctx.Only_include_evaluate_(false);
						rslt_bfr.Add_bfr_and_preserve(tmp_bfr);
						break;
					}
					case Xop_xnde_tag_.Tid_includeonly:	// noop; DATE:2014-02-12
						break;
					case Xop_xnde_tag_.Tid_nowiki: {
						if (xnde.Tag_close_bgn() == Int_.MinValue)
							rslt_bfr.Add_mid(src, tkn.Src_bgn(), tkn.Src_end());	// write src from bgn/end
						else {												// NOTE: if nowiki then "deactivate" all xndes by swapping out < for &lt; nowiki_xnde_frag; DATE:2013-01-27
							Bry_bfr tmp_bfr = ctx.Wiki().Appe().Utl_bry_bfr_mkr().Get_k004();
							int nowiki_content_bgn = xnde.Tag_open_end(), nowiki_content_end = xnde.Tag_close_bgn();
							boolean escaped = gplx.xowa.parsers.tmpls.Nowiki_escape_itm.Escape(tmp_bfr, src, nowiki_content_bgn, nowiki_content_end);
							rslt_bfr.Add_bfr_or_mid(escaped, tmp_bfr, src, nowiki_content_bgn, nowiki_content_end);
							tmp_bfr.Mkr_rls();
						}
						break;
					}
					case Xop_xnde_tag_.Tid_xowa_cmd:
						gplx.xowa.xtns.xowa_cmds.Xop_xowa_cmd xowa_cmd = (gplx.xowa.xtns.xowa_cmds.Xop_xowa_cmd)xnde.Xnde_xtn();					
						rslt_bfr.Add(xowa_cmd.Xtn_html());
						break;
					default:
						rslt_bfr.Add_mid(src, tkn.Src_bgn(), tkn.Src_end());				// write src from bgn/end
						break;
				}
				break;
			default:
				rslt_bfr.Add_mid(src, tkn.Src_bgn(), tkn.Src_end()); break;			// write src from bgn/end
			case Xop_tkn_itm_.Tid_ignore: break;								// hide comments and <*include*> ndes
			case Xop_tkn_itm_.Tid_tmpl_prm:
				tkn.Tmpl_evaluate(ctx, src, Xot_invk_temp.Page_is_caller.Src_(src), rslt_bfr);
				break;
			case Xop_tkn_itm_.Tid_tvar:
				gplx.xowa.xtns.translates.Xop_tvar_tkn tvar_tkn = (gplx.xowa.xtns.translates.Xop_tvar_tkn)tkn;
				rslt_bfr.Add(tvar_tkn.Wikitext());
				break;
			case Xop_tkn_itm_.Tid_tmpl_invk:
				try {
					tkn.Tmpl_evaluate(ctx, src, Xot_invk_temp.Page_is_caller.Src_(src), rslt_bfr);
				}
				catch (Exception exc) {
					Err_string = String_.new_utf8_(src, tkn.Src_bgn(), tkn.Src_end()) + "|" + ClassAdp_.NameOf_obj(exc) + "|" + Err_.Message_lang(exc);
					if (Env_.Mode_testing())
						throw Err_.err_(exc, Err_string);
					else
						ctx.App().Usr_dlg().Warn_many("", "", "failed to write tkn: page=~{0} err=~{1}", String_.new_utf8_(ctx.Cur_page().Ttl().Page_db()), Err_string);
				}
				break;
		}
	}
	public static String Err_string = "";
	public static final Xot_tmpl_wtr _ = new Xot_tmpl_wtr(); Xot_tmpl_wtr() {}
}
