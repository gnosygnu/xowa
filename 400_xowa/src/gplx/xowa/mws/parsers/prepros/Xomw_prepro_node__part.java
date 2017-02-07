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
package gplx.xowa.mws.parsers.prepros; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*; import gplx.xowa.mws.parsers.*;
public class Xomw_prepro_node__part extends Xomw_prepro_node__base {
	public Xomw_prepro_node__part(int idx, byte[] key, byte[] val) {
		this.idx = idx;
		this.key = key;
		this.val = val;
	}
	public int Idx() {return idx;} private final    int idx;
	public byte[] Key() {return key;} private final    byte[] key;
	public byte[] Val() {return val;} private final    byte[] val;
	@Override public void To_xml(Bry_bfr bfr) {
		bfr.Add_str_a7("<part>");
		bfr.Add_str_a7("<name");
		if (idx > 0) {
			bfr.Add_str_a7(" index=\"").Add_int_variable(idx).Add_str_a7("\" />");
		}
		else {
			bfr.Add_str_a7(">");
			bfr.Add(key);
			bfr.Add_str_a7("</name>");
			bfr.Add_str_a7("=");
		}
		bfr.Add_str_a7("<value>");
		bfr.Add(val);
		bfr.Add_str_a7("</value>");
		bfr.Add_str_a7("</part>");
	}
}
