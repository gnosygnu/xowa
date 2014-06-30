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
package gplx;
public class Gfo_log_bfr {
	private Bry_bfr bfr = Bry_bfr.reset_(255);
	public Gfo_log_bfr Add(String s) {
		bfr.Add_str(DateAdp_.Now().XtoUtc().XtoStr_fmt_yyyyMMdd_HHmmss_fff());
		bfr.Add_byte_space();
		bfr.Add_str(s);
		bfr.Add_byte_nl();
		return this;
	}
	public String Xto_str() {return bfr.XtoStrAndClear();}
}
