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
package gplx.xowa.xtns.pfuncs.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.files.*;
public class Pfunc_filepath extends Pf_func_base {
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public int Id() {return Xol_kwd_grp_.Id_url_filepath;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_filepath().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bb) {
		byte[] val_ary = Eval_argx(ctx, src, caller, self); if (val_ary == Bry_.Empty) return;
		Xow_wiki wiki = ctx.Wiki();
		val_ary = Bry_.Add(Bry_file, val_ary);
		Xoa_ttl ttl = Xoa_ttl.new_(wiki, ctx.App().Msg_log_null(), val_ary, 0, val_ary.length);  if (ttl == null) return; // text is not valid ttl; return;
		Xoa_page page = Load_page(wiki, ttl);
		if (page.Missing()) return; // page not found in commons; exit;
		byte[] ttl_bry = page.Ttl().Page_url();
		Xofw_file_finder_rslt tmp_rslt = wiki.File_mgr().Repo_mgr().Page_finder_locate(ttl_bry);
		if (tmp_rslt .Repo_idx() == Byte_.Max_value_127) return;
		Xof_repo_itm trg_repo = wiki.File_mgr().Repo_mgr().Repos_get_at(tmp_rslt.Repo_idx()).Trg();
		xfer_itm.Set__ttl(ttl_bry, Bry_.Empty);	// redirect is empty b/c Get_page does all redirect lookups
		byte[] url = url_bldr.Init_for_trg_html(Xof_repo_itm.Mode_orig, trg_repo, ttl_bry, xfer_itm.Lnki_md5(), xfer_itm.Lnki_ext(), Xof_img_size.Size_null_deprecated, Xof_doc_thumb.Null, Xof_doc_page.Null).Xto_bry();
		bb.Add(url);
	}	private static final byte[] Bry_file = Bry_.new_ascii_("File:");
	private static final Xof_xfer_itm xfer_itm = new Xof_xfer_itm();
	private static final Xof_url_bldr url_bldr = new Xof_url_bldr();
	public static Xoa_page Load_page(Xow_wiki wiki, Xoa_ttl ttl) {
		Xoa_page page = wiki.Data_mgr().Get_page(ttl, false);
		if (page.Missing()) {	// file not found in current wiki; try commons; 
			Xow_wiki commons_wiki = wiki.App().Wiki_mgr().Get_by_key_or_null(wiki.Commons_wiki_key());
			if (commons_wiki != null) {;	// commons_wiki not installed; exit; DATE:2013-06-08
				if (!Env_.Mode_testing()) commons_wiki.Init_assert();// must assert load else page_zip never detected; TODO: move to Xoa_wiki_mgr.New_wiki; DATE:2013-03-10
				page = commons_wiki.Data_mgr().Get_page(ttl, false);
			}
		}
		return page;
	}
}
