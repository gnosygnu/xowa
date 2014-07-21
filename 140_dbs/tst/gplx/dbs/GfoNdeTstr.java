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
package gplx.dbs; import gplx.*;
public class GfoNdeTstr {
	public static void tst_ValsByCol(GfoNde nde, String fld, Object... expdAry) {
		ListAdp expd = ListAdp_.new_();
		for (int i = 0; i < expdAry.length; i++) {
			expd.Add(Object_.Xto_str_strict_or_empty(expdAry[i]));
		}
		ListAdp actl = ListAdp_.new_();
		for (int i = 0; i < nde.Subs().Count(); i++) {
			GfoNde sub = nde.Subs().FetchAt_asGfoNde(i);
			actl.Add(Object_.Xto_str_strict_or_empty(sub.Read(fld)));
		}
		Tfds.Eq_ary(expd.XtoStrAry(), actl.XtoStrAry());
	}
}