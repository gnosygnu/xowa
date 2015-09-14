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
package gplx.ios; import gplx.*;
public class Io_fil_chkr implements Tst_chkr {
	public Io_fil_chkr(Io_url url, String data) {this.expd_url = url; this.expd_data = data;}
	public Io_url Expd_url() {return expd_url;} public Io_fil_chkr Expd_url_(Io_url v) {expd_url = v; return this;} Io_url expd_url;
	public String Expd_data() {return expd_data;} public Io_fil_chkr Expd_data_(String v) {expd_data = v; return this;} private String expd_data;
	public Class<?> TypeOf() {return gplx.ios.Io_fil.class;}
	public int Chk(Tst_mgr mgr, String path, Object actl) {
		gplx.ios.Io_fil fil = (gplx.ios.Io_fil)actl;
		int rv = 0;
		rv += mgr.Tst_val(expd_url == null, path, "url", expd_url, fil.Url());
		rv += mgr.Tst_val(expd_data == null, path, "data", expd_data, fil.Data());
		return rv;
	}
}
