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
package gplx.xowa; import gplx.*;
public class Xop_pre_tkn extends Xop_tkn_itm_base {
	public Xop_pre_tkn(int bgn, int end, byte pre_tid, Xop_tkn_itm pre_bgn_tkn) {
		this.Tkn_ini_pos(false, bgn, end);
		this.pre_tid = pre_tid;
	}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_pre;}
	public byte Pre_tid() {return pre_tid;} private byte pre_tid = Pre_tid_null;
	public static final byte Pre_tid_null = 0, Pre_tid_bgn = 1, Pre_tid_end = 2;
}
