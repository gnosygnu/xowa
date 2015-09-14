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
package gplx.xowa.parsers; import gplx.*; import gplx.xowa.*;
public class Xop_root_tkn extends Xop_tkn_itm_base {
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_root;}
	public byte[] Root_src() {return root_src;} public Xop_root_tkn Root_src_(byte[] v) {root_src = v; return this;} private byte[] root_src = Bry_.Empty;
	public byte[] Data_mid() {return data_mid;} public Xop_root_tkn Data_mid_(byte[] v) {data_mid = v; return this;} private byte[] data_mid = Bry_.Empty;
	public byte[] Data_htm() {return data_htm;} public Xop_root_tkn Data_htm_(byte[] v) {data_htm = v; return this;} private byte[] data_htm = Bry_.Empty;
	@Override public void Reset() {
		super.Reset();
		root_src = Bry_.Empty;
	}
}
