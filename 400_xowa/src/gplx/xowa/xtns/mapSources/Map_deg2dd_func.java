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
package gplx.xowa.xtns.mapSources; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Map_deg2dd_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_mapSources_deg2dd;}
	@Override public Pf_func New(int id, byte[] name) {return new Map_deg2dd_func().Name_(name);}
	@Override public void Func_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Xot_invk self, Bry_bfr bfr) {
		byte[] coord = Eval_argx(ctx, src, caller, self);
		byte[] precision_bry = Pf_func_.Eval_val_or(ctx, src, caller, self, self.Args_len(), 0, null);
		int prec = precision_bry == null ? -1 : Bry_.To_int_or(precision_bry, -1);
		Map_math map_math = Map_math.Instance;
		if (map_math.Ctor(coord, prec, Bry_.Empty, 2))
			bfr.Add_double(map_math.Coord_dec());
		else
			map_math.Fail(ctx, src, self, bfr, this.Name());			
	}
	public static final Map_deg2dd_func Instance = new Map_deg2dd_func(); Map_deg2dd_func() {}
}
