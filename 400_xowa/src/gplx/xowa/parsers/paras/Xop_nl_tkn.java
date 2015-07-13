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
package gplx.xowa.parsers.paras; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_nl_tkn extends Xop_tkn_itm_base {
	public Xop_nl_tkn(int bgn, int end, byte nl_tid, int nl_len) {
		this.Tkn_ini_pos(false, bgn, end);
		this.nl_tid = nl_tid;
	}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_newLine;}		
	public byte Nl_tid() {return nl_tid;} private byte nl_tid = Xop_nl_tkn.Tid_unknown; 		
	public static final byte Tid_unknown = 0, Tid_char = 1, Tid_hdr = 2, Tid_hr = 3, Tid_list = 4, Tid_tblw = 5, Tid_file = 6;
}
