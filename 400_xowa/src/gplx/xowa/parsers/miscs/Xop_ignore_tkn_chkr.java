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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.tests.*;
public class Xop_ignore_tkn_chkr extends Xop_tkn_chkr_base {
	@Override public Class<?> TypeOf() {return Xop_ignore_tkn.class;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_ignore;}
	public byte Ignore_type() {return ignore_type;} public Xop_ignore_tkn_chkr Ignore_tid_(byte v) {ignore_type = v; return this;} private byte ignore_type = Xop_ignore_tkn.Ignore_tid_null;
	@Override public int Chk_hook(Tst_mgr mgr, String path, Object actl_obj, int err) {
		Xop_ignore_tkn actl = (Xop_ignore_tkn)actl_obj;
		err += mgr.Tst_val(ignore_type == Xop_ignore_tkn.Ignore_tid_null, path, "ignore_type", ignore_type, actl.Ignore_type());
		return err;
	}
}
