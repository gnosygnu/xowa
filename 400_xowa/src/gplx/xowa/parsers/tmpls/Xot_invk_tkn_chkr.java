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
package gplx.xowa.parsers.tmpls; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.tests.*;
public class Xot_invk_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xot_invk_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_tmpl_invk;}
	public Xop_tkn_chkr_base Name_tkn() {return name_tkn;} public Xot_invk_tkn_chkr Name_tkn_(Xop_tkn_chkr_base v) {name_tkn = v; return this;} private Xop_tkn_chkr_base name_tkn;
	public Xop_tkn_chkr_base[] Args() {return args;} public Xot_invk_tkn_chkr Args_(Xop_tkn_chkr_base... v) {args = v; return this;} private Xop_tkn_chkr_base[] args = Xop_tkn_chkr_base.Ary_empty;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xot_invk_tkn actl = (Xot_invk_tkn)actl_obj;
		if (name_tkn != null) err += mgr.Tst_sub_obj(name_tkn, actl.Name_tkn(), path + "." + "name", err);
		err += mgr.Tst_sub_ary(args, Args_xtoAry(actl), path + "." + "args", err);
		return err;
	}
	Arg_nde_tkn[] Args_xtoAry(Xot_invk_tkn tkn) {
		int args_len = tkn.Args_len();
		Arg_nde_tkn[] rv = new Arg_nde_tkn[args_len];
		for (int i = 0; i < args_len; i++)
			rv[i] = tkn.Args_get_by_idx(i);
		return rv;
	}
}
