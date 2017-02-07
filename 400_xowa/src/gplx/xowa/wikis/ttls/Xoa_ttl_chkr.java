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
package gplx.xowa.wikis.ttls; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
import gplx.core.tests.*;
public class Xoa_ttl_chkr implements Tst_chkr {
	public Class<?> TypeOf() {return Xoa_ttl.class;}
	public int Chk(Tst_mgr mgr, String path, Object o) {
		Xoa_ttl actl = (Xoa_ttl)o;
		int rv = 0;
		rv += mgr.Tst_val(expd_str == null, path, "raw", expd_str, String_.new_u8(actl.Raw()));
		return rv;
	}
	public String Expd_str() {return expd_str;} public Xoa_ttl_chkr Expd_str_(String v) {expd_str = v; return this;} private String expd_str;
	public static Xoa_ttl_chkr new_(String v) {return new Xoa_ttl_chkr().Expd_str_(v);} private Xoa_ttl_chkr() {}
}
