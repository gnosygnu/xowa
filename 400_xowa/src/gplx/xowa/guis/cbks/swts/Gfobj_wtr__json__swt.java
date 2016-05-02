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
package gplx.xowa.guis.cbks.swts; import gplx.*; import gplx.xowa.*; import gplx.xowa.guis.*; import gplx.xowa.guis.cbks.*;
import gplx.core.gfobjs.*;
public class Gfobj_wtr__json__swt extends Gfobj_wtr__json {		private final    Bry_bfr bfr;
	public Gfobj_wtr__json__swt() {
		this.Opt_ws_(Bool_.N);
		this.bfr = this.Bfr();
	}
	public String Write_as_func(String func_name, Gfobj_grp root) {
		bfr.Add(Bry__func_bgn);
		bfr.Add_str_u8(func_name);
		bfr.Add(Bry__args_bgn);
		this.Write(root);
		bfr.Add(Bry__args_end);
		return this.To_str();
	}
	private static final    byte[]
	  Bry__func_bgn	= Bry_.new_a7("return ")
	, Bry__args_bgn = Bry_.new_a7("('")
	, Bry__args_end = Bry_.new_a7("');")
	;
}
