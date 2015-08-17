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
import gplx.xowa.files.*; import gplx.xowa.files.repos.*;
import gplx.xowa.files.origs.*;
public class Pfunc_filepath extends Pf_func_base {
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public int Id() {return Xol_kwd_grp_.Id_url_filepath;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_filepath().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		byte[] val_ary = Eval_argx(ctx, src, caller, self); if (val_ary == Bry_.Empty) return;
		Xowe_wiki wiki = ctx.Wiki();
		Xoa_ttl ttl = wiki.Ttl_parse(Xow_ns_.Id_file, val_ary); if (ttl == null) return; // text is not valid ttl; exit;
		Xoae_page page = Load_page(wiki, ttl); if (page.Missing()) return; // page not found in wiki or commons; exit;
		byte[] ttl_bry = page.Ttl().Page_url();

//			Xof_orig_itm orig_itm = wiki.File_mgr().Orig_mgr().Find_by_ttl_or_null(ttl_bry);
//			if (orig_itm == Xof_orig_itm.Null) return;
//			Xof_repo_itm repo = wiki.File_mgr().Repo_mgr().Get_trg_by_id_or_null(orig_itm.Repo()).Trg();
//			url_bldr.Init_for_trg_html(Xof_repo_itm_.Mode_orig, repo, orig_itm.Ttl(), Xof_file_wkr_.Md5_fast(orig_itm.Ttl()), orig_itm.Ext(), -1, -1, -1);
//			bfr.Add(url_bldr.Xto_bry());

		Xofw_file_finder_rslt tmp_rslt = wiki.File_mgr().Repo_mgr().Page_finder_locate(ttl_bry);
		if (tmp_rslt.Repo_idx() == Byte_.Max_value_127) return;
		Xof_repo_itm trg_repo = wiki.File_mgr().Repo_mgr().Repos_get_at(tmp_rslt.Repo_idx()).Trg();
		xfer_itm.Orig_ttl_and_redirect_(ttl_bry, Bry_.Empty);	// redirect is empty b/c Get_page does all redirect lookups
		byte[] url = url_bldr.Init_for_trg_html(Xof_repo_itm_.Mode_orig, trg_repo, ttl_bry, xfer_itm.Orig_ttl_md5(), xfer_itm.Orig_ext(), Xof_img_size.Size__neg1, Xof_lnki_time.Null, Xof_lnki_page.Null).Xto_bry();
		bfr.Add(url);
	}
	private static final Xof_xfer_itm xfer_itm = new Xof_xfer_itm();
	private static final Xof_url_bldr url_bldr = new Xof_url_bldr();
	private static Xoae_page Load_page(Xowe_wiki wiki, Xoa_ttl ttl) {
		Xoae_page page = wiki.Data_mgr().Get_page(ttl, false);
		if (page.Missing()) {				// file not found in current wiki; try commons; 
			Xowe_wiki commons_wiki = wiki.Appe().Wiki_mgr().Get_by_key_or_null(wiki.Commons_wiki_key());
			if (commons_wiki != null) {		// commons_wiki not installed; exit; DATE:2013-06-08
				if (!Env_.Mode_testing()) commons_wiki.Init_assert();// must assert load else page_zip never detected; DATE:2013-03-10
				page = commons_wiki.Data_mgr().Get_page(ttl, false);
			}
		}
		return page;
	}
}
