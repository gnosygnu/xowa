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
package gplx.xowa.xtns.mapSources; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.xtns.pfuncs.*;
import gplx.xowa.langs.*; import gplx.xowa.langs.kwds.*;
import gplx.xowa.parsers.*; import gplx.xowa.parsers.tmpls.*;
public class Map_deg2dd_func extends Pf_func_base {
	@Override public int Id() {return Xol_kwd_grp_.Id_mapSources_deg2dd;}
	@Override public Pf_func New(int id, byte[] name) {return new Map_deg2dd_func().Name_(name);}
	@Override public void Func_evaluate(Bry_bfr bfr, Xop_ctx ctx, Xot_invk caller, Xot_invk self, byte[] src) {
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
