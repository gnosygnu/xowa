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
package gplx.json; import gplx.*;
public abstract class Json_itm_base implements Json_itm {
	public abstract byte Tid();
	public void Ctor(int src_bgn, int src_end) {this.src_bgn = src_bgn; this.src_end = src_end;}
	public int Src_bgn() {return src_bgn;} private int src_bgn;
	public int Src_end() {return src_end;} protected int src_end;
	public abstract Object Data();
	public abstract byte[] Data_bry();
	public String Print_as_json() {Bry_bfr bfr = Bry_bfr.new_(); Print_as_json(bfr, 0); return bfr.XtoStrAndClear();}
	public abstract void Print_as_json(Bry_bfr bfr, int depth);
	@gplx.Virtual public boolean Data_eq(byte[] comp) {return false;}
}
