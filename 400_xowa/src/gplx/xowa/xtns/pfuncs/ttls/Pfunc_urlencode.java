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
import gplx.xowa.langs.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Pfunc_urlencode extends Pf_func_base {	// EX: {{urlencode:a b}} -> a+b
	@Override public boolean Func_require_colon_arg() {return true;}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bb) {
		byte[] val_ary = Eval_argx(ctx, src, caller, self); if (val_ary == Bry_.Empty) return;
		Xoa_app_.Utl__encoder_mgr().Http_url().Encode(urlEncodeBfr, val_ary);
		bb.Add_bfr_and_preserve(urlEncodeBfr);
		urlEncodeBfr.Clear();
	}	private Bry_bfr urlEncodeBfr = Bry_bfr.new_(128);
	@Override public int Id() {return Xol_kwd_grp_.Id_url_urlencode;}
	@Override public Pf_func New(int id, byte[] name) {return new Pfunc_urlencode().Name_(name);}
}
