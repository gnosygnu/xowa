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
package gplx.xowa.htmls.core.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.xowa.htmls.core.hzips.*;
public class Xoh_hzip_bfr extends Bry_bfr { 	private final    Xoh_hzip_int hzint = new Xoh_hzip_int();
	private final    byte stop_byte;
	public Xoh_hzip_bfr(int bfr_max, boolean mode_is_b256, byte stop_byte) {
		this.Init(bfr_max);
		this.stop_byte = stop_byte;
		Mode_is_b256_(mode_is_b256);
	}
	public Xoh_hzip_bfr Mode_is_b256_(boolean mode_is_b256) {
		hzint.Mode_is_b256_(mode_is_b256);
		return this;
	}
	public Xoh_hzip_bfr Add_hzip_bry(byte[] bry)					{Add(bry);					Add_byte(stop_byte); return this;}
	public Xoh_hzip_bfr Add_hzip_mid(byte[] src, int bgn, int end)	{Add_mid(src, bgn, end);	Add_byte(stop_byte); return this;}
	public Xoh_hzip_bfr Add_hzip_double(double val)					{this.Add_double(val);		Add_byte(stop_byte); return this;}
	public Xoh_hzip_bfr Add_hzip_int(int reqd, int val) {
		hzint.Encode(reqd, this, val);
		return this;
	}
	public static Xoh_hzip_bfr New_txt(int bfr_max) {return new Xoh_hzip_bfr(bfr_max, Bool_.N, gplx.xowa.htmls.core.hzips.Xoh_hzip_dict_.Escape);}
}
