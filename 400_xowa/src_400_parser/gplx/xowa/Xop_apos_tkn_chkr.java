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
import gplx.xowa.parsers.apos.*;
public class Xop_apos_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_apos_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_apos;}
	public int Apos_cmd() {return apos_cmd;} public Xop_apos_tkn_chkr Apos_cmd_(int v) {apos_cmd = v; return this;} private int apos_cmd = Xop_apos_tkn_.Cmd_nil;
	public int Apos_lit() {return apos_lit;} public Xop_apos_tkn_chkr Apos_lit_(int v) {apos_lit = v; return this;} private int apos_lit = -1;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_apos_tkn actl = (Xop_apos_tkn)actl_obj;
		err += mgr.Tst_val(apos_cmd == Xop_apos_tkn_.Cmd_nil, path, "apos_cmd", Xop_apos_tkn_.Cmd_str(apos_cmd), Xop_apos_tkn_.Cmd_str(actl.Apos_cmd()));
		err += mgr.Tst_val(apos_lit == -1, path, "apos_lit", apos_lit, actl.Apos_lit());
		return err;
	}
}
