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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.envs.*;
import gplx.xowa.parsers.xndes.*; import gplx.xowa.parsers.miscs.*;
public class Xot_tmpl_wtr {
	public static byte[] Write_all(Xop_ctx ctx, Xot_invk frame, Xop_root_tkn root, byte[] src) {
		Bry_bfr bfr = ctx.Wiki().Utl__bfr_mkr().Get_m001().Reset_if_gt(Io_mgr.Len_mb);
		Write_tkn(bfr, ctx, frame, src, src.length, root);
		byte[] rv = bfr.To_bry_and_rls();
		return ctx.Wiki().Parser_mgr().Uniq_mgr().Parse(rv); // NOTE: noops if no UNIQs; // UNIQ; DATE:2017-03-31
	}
	private static void Write_tkn(Bry_bfr rslt_bfr, Xop_ctx ctx, Xot_invk frame, byte[] src, int src_len, Xop_tkn_itm tkn) {
		switch (tkn.Tkn_tid()) {
			case Xop_tkn_itm_.Tid_root: // write each sub
				int subs_len = tkn.Subs_len();
				for (int i = 0; i < subs_len; i++) {
					Xop_tkn_itm sub_tkn = tkn.Subs_get(i);
					if (!sub_tkn.Ignore())
						Write_tkn(rslt_bfr, ctx, frame, src, src_len, sub_tkn);
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
					case Xop_xnde_tag_.Tid__onlyinclude: {
						// NOTE: originally "if (ctx.Parse_tid() == Xop_parser_tid_.Tid__tmpl) {" but if not needed; Xot_tmpl_wtr should not be called for tmpls and <oi> should not make it to page_wiki
						Bry_bfr tmp_bfr = Bry_bfr_.New();
						ctx.Only_include_evaluate_(true);
						xnde.Tmpl_evaluate(ctx, src, Xot_invk_temp.Page_is_caller, tmp_bfr);
						ctx.Only_include_evaluate_(false);
						rslt_bfr.Add_bfr_and_preserve(tmp_bfr);
						break;
					}
					case Xop_xnde_tag_.Tid__includeonly:	// noop; DATE:2014-02-12
						break;
					case Xop_xnde_tag_.Tid__nowiki: {
						if (xnde.Tag_close_bgn() == Int_.Min_value)
							rslt_bfr.Add_mid(src, tkn.Src_bgn(), tkn.Src_end());	// write src from bgn/end
						else {												// NOTE: if nowiki then "deactivate" all xndes by swapping out < for &lt; nowiki_xnde_frag; DATE:2013-01-27
							Bry_bfr tmp_bfr = ctx.Wiki().Utl__bfr_mkr().Get_k004();
							int nowiki_content_bgn = xnde.Tag_open_end(), nowiki_content_end = xnde.Tag_close_bgn();
							boolean escaped = gplx.xowa.parsers.tmpls.Nowiki_escape_itm.Escape(tmp_bfr, src, nowiki_content_bgn, nowiki_content_end);
							rslt_bfr.Add_bfr_or_mid(escaped, tmp_bfr, src, nowiki_content_bgn, nowiki_content_end);
							tmp_bfr.Mkr_rls();
						}
						break;
					}
					case Xop_xnde_tag_.Tid__xowa_cmd:
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
					if (frame == Xot_invk_temp.Null_frame) { // NOTE: should probably remove lazy-instantiation and always force frame to be passed in; DATE:2017-09-03
						frame = Xot_invk_temp.Page_is_caller.Src_(src);
					}
					tkn.Tmpl_evaluate(ctx, src, frame, rslt_bfr);
				}
				catch (Exception e) {
					String err_string = String_.new_u8(src, tkn.Src_bgn(), tkn.Src_end()) + "|" + Type_adp_.NameOf_obj(e) + "|" + Err_.Cast_or_make(e).To_str__log();
					if (Env_.Mode_testing())
						throw Err_.new_exc(e, "xo", err_string);
					else
						ctx.App().Usr_dlg().Warn_many("", "", "failed to write tkn: page=~{0} err=~{1}", String_.new_u8(ctx.Page().Ttl().Page_db()), err_string);
				}
				break;
		}
	}
}
