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
package gplx.core.json; import gplx.*; import gplx.core.*;
public class Json_itm_int extends Json_itm_base {
	private final Json_doc doc;
	private byte[] data_bry; private int data; private boolean data_is_null = true;
	public Json_itm_int(Json_doc doc, int src_bgn, int src_end) {this.Ctor(src_bgn, src_end); this.doc = doc;}
	@Override public byte Tid() {return Json_itm_.Tid__int;}
	public int Data_as_int() {
		if (data_is_null) {
			data = doc.Utl_num_parser().Parse(doc.Src(), Src_bgn(), Src_end()).Rv_as_int();
			data_is_null = false;
		}
		return data;
	}
	@Override public Object Data() {return Data_as_int();}
	@Override public byte[] Data_bry() {if (data_bry == null) data_bry = Bry_.Mid(doc.Src(), this.Src_bgn(), this.Src_end()); return data_bry;}
	@Override public void Print_as_json(Bry_bfr bfr, int depth) {bfr.Add_mid(doc.Src(), this.Src_bgn(), this.Src_end());}
	public static Json_itm_int cast(Json_itm v) {return v == null || v.Tid() != Json_itm_.Tid__int ? null : (Json_itm_int)v;}
}
