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
package gplx.xowa.htmls.core.wkrs.bfr_args; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;
public class Bfr_arg__hatr_id implements Bfr_arg_clearable {
	private final byte[] atr_bgn;
	private final byte[] bry; private int num;
	public Bfr_arg__hatr_id(byte[] atr_key, byte[] bry)	{this.bry = bry; this.atr_bgn = Bfr_arg__hatr_.Bld_atr_bgn(atr_key);}
	public Bfr_arg__hatr_id Set(int num)				{this.num = num; return this;}
	public void Bfr_arg__clear()						{num = -1;}
	public boolean Bfr_arg__missing()						{return num == -1;}
	public void Bfr_arg__add(Bry_bfr bfr) {
		if (Bfr_arg__missing()) return;
		bfr.Add(atr_bgn);
		bfr.Add(bry);
		bfr.Add_int_variable(num);
		bfr.Add_byte_quote();
	}
	public static Bfr_arg__hatr_id New_id(String v) {return new Bfr_arg__hatr_id(gplx.langs.htmls.Gfh_atr_.Bry__id, Bry_.new_u8(v));}
	public static Bfr_arg__hatr_id New_id(byte[] v) {return new Bfr_arg__hatr_id(gplx.langs.htmls.Gfh_atr_.Bry__id, v);}
}
