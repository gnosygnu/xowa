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
package gplx.core.tests; import gplx.*; import gplx.core.*;
public interface Tst_chkr {
	Class<?> TypeOf();
	int Chk(Tst_mgr mgr, String path, Object actl);
}
class Tst_chkr_null implements Tst_chkr {
	public Class<?> TypeOf() {return Object.class;}
	public int Chk(Tst_mgr mgr, String path, Object actl) {
		mgr.Results().Add(Tst_itm.fail_("!=", path, "<cast type>", "<NULL TYPE>",  Type_adp_.NameOf_obj(actl)));
//			mgr.Results().Add(Tst_itm.fail_("!=", path, "<cast value>", "<NULL VAL>", Object_.Xto_str_strict_or_null(actl)));
		return 1;
	}
}
