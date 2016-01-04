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
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.wikis.nss.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_ns extends Pf_func_base {	// EX: {{ns:6}} -> File
	private boolean encode;
	public Pfunc_ns(boolean encode) {this.encode = encode; if (canonical == null) canonical_();}
	@Override public int Id() {return Xol_kwd_grp_.Id_url_ns;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_ns(encode).Name_(name);}
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
		byte[] val_dat_ary = Eval_argx(ctx, src, caller, self); if (val_dat_ary == Bry_.Empty) return;

		int val_dat_ary_len = val_dat_ary.length;
		int ns_id = Bry_.To_int_or(val_dat_ary, 0, val_dat_ary_len, Int_.Min_value);
		if (ns_id == Int_.Min_value) {
			Object o = ctx.Wiki().Ns_mgr().Names_get_or_null(val_dat_ary, 0, val_dat_ary_len);
			if (o == null
				&& !Bry_.Eq(ctx.Lang().Key_bry(), Xol_lang_itm_.Key_en)) // foreign language; english canonical names are still valid; REF.MW: Language.php|getNsIndex
					o = canonical.Get_by_mid(val_dat_ary, 0, val_dat_ary_len);				
			if (o != null) {
				Xow_ns itm = (Xow_ns)o;
				if (itm.Id() == Xow_ns_.Tid__file) itm = ctx.Wiki().Ns_mgr().Ns_file();	// handles "Image" -> "File"
				bfr.Add(encode ? itm.Name_enc() : itm.Name_ui());
			}
		}
		else {
			Xow_ns itm = (Xow_ns)ctx.Wiki().Ns_mgr().Ids_get_or_null(ns_id);
			if (itm == null) return;	// occurs when ns_id is not known; EX: {{ns:999}}; SEE: Wiktionary:Grease pit archive/2007/October; "{{ns:114}}"
			bfr.Add(encode ? itm.Name_enc() : itm.Name_ui());
		}
	}
	private static Hash_adp_bry canonical;
	private static void canonical_() {
		canonical = Hash_adp_bry.ci_a7();	// ASCII:canonical English names
		for (Xow_ns ns : Xow_ns_canonical_.Ary)
			canonical_add(ns.Id(), ns.Name_db());
	}
	private static void canonical_add(int ns_id, byte[] ns_name) {
		Xow_ns ns = new Xow_ns(ns_id, Xow_ns_case_.Tid__all, ns_name, false);
		canonical.Add(ns_name, ns);
	}
}	
