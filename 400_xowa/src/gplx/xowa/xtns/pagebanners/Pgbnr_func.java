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
package gplx.xowa.xtns.pagebanners; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.btries.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*; import gplx.xowa.xtns.pfuncs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.utils.*;
public class Pgbnr_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_pagebanner;}
	@Override public Pf_func New(int id, byte[] name) {return new Pgbnr_func().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) { // WikidataPageBanner.hooks.php|addCustomBanner
		Xowe_wiki wiki = ctx.Wiki();
		Pgbnr_cfg cfg = new Pgbnr_cfg(); // ctx.Wiki().Xtn_mgr().Xtn_pgbnr();
		Xoa_ttl ttl = ctx.Cur_page().Ttl();
		if (	!cfg.Enabled_in_ns(ttl.Ns().Id())					// chk if ns allows banner
			||	Bry_.Eq(ttl.Page_db(), wiki.Props().Main_page())) 	// never show on main page
			return;
		Bry_bfr tmp_bfr = Bry_bfr.new_();
		boolean bottomtoc = false, toc = false;
		byte[] tooltip = ttl.Page_txt();
		byte[] title = ttl.Page_txt();
		int args_len = self.Args_len();
		List_adp icons_list = null;
		for (int i = 0; i < args_len; ++i) {
			Arg_nde_tkn arg = self.Args_get_by_idx(i);
			byte[] key = Pf_func_.Eval_tkn(tmp_bfr, ctx, src, caller, arg.Key_tkn());
			byte[] val = Pf_func_.Eval_tkn(tmp_bfr, ctx, src, caller, arg.Val_tkn());
			int tid = arg_hash.Get_as_int_or(key, -1);
			if (tid == Arg__pgname)
				tooltip = title = val;
			if (tid == Arg__tooltip)	// note that this overrides pgname above
				tooltip = val;
			if (tid == Arg__bottomtoc	&& Bry_.Eq(val, Bry__yes))
				bottomtoc = true;
			if (tid == Arg__toc			&& Bry_.Eq(val, Bry__yes))
				toc = true;
			if (tid == -1 && Bry_.Has_at_bgn(key, Bry__icon) && Bry_.Len_gt_0(val)) {
				if (icons_list == null) icons_list = List_adp_.new_();
				byte[] icon_name = Xop_sanitizer.Escape_cls(val);
				byte[] icon_title = icon_name;
				Xoa_ttl icon_url_ttl = wiki.Ttl_parse(val);
				byte[] icon_url_bry = Bry_.Empty;
				if (icon_url_ttl == null) icon_url_bry = Bry__url_dflt;
				else  {
					icon_url_bry = Bry_.Empty;// $iconUrl->getLocalUrl();
					icon_title = ttl.Page_txt();
				}
				icons_list.Add(new Pgbnr_icon(icon_name, icon_title, icon_url_bry));
				tid = Arg__icon;
			}
			if (tid == -1) Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown arg type; page=~{0} key=~{1} val=~{2}", "page", ctx.Cur_page().Url_bry_safe(), key, val);
			// WikidataPageBannerFunctions::addFocus( $paramsForBannerTemplate, $argumentsFromParserFunction );
		}
		byte[] name = Eval_argx(ctx, src, caller, self);
		if (Bry_.Len_eq_0(name))
			name = cfg.Default_file();
		Xoa_ttl file_ttl = wiki.Ttl_parse(name);
		Pgbnr_itm itm = new Pgbnr_itm(name, tooltip, title, bottomtoc, toc, file_ttl, icons_list == null ? Pgbnr_icon.Ary_empty : (Pgbnr_icon[])icons_list.To_ary_and_clear(Pgbnr_icon.class));
            Tfds.Write(itm);
	}
	private static final byte[] Bry__yes = Bry_.new_a7("yes"), Bry__icon = Bry_.new_a7("icon-"), Bry__url_dflt = Bry_.new_a7("#");
	private static final int Arg__pgname = 0, Arg__tooltip = 1, Arg__bottomtoc = 2, Arg__toc = 3, Arg__icon = 4;
	private static final Hash_adp_bry arg_hash = Hash_adp_bry.cs().Add_str_int("pgname", Arg__pgname)
		.Add_str_int("tooltip", Arg__tooltip).Add_str_int("bottomtoc", Arg__bottomtoc).Add_str_int("toc", Arg__toc);
}
