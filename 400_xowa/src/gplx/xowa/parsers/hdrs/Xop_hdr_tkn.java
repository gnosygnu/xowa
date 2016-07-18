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
package gplx.xowa.parsers.hdrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_hdr_tkn extends Xop_tkn_itm_base {
	public Xop_hdr_tkn(int bgn, int end, int num) {this.Tkn_ini_pos(false, bgn, end); this.num = num;}
	@Override public byte Tkn_tid()		{return Xop_tkn_itm_.Tid_hdr;}
	public int		Num()				{return num;}				private int num = -1;			// EX: 4 for <h2>
	public int		Manual_bgn()		{return manual_bgn;}		private int manual_bgn;			// unbalanced count; EX: === A == -> 1
	public int		Manual_end()		{return manual_end;}		private int manual_end;			// unbalanced count; EX: == A === -> 1
	public boolean		First_in_doc()		{return first_in_doc;}		private boolean first_in_doc;		// true if 1st hdr in doc
	public void		First_in_doc_y_()	{first_in_doc = true;} 
	public void Init_by_parse(int num, int manual_bgn, int manual_end) {
		this.num = num;
		this.manual_bgn = manual_bgn;
		this.manual_end = manual_end;
	}

	public static final    Xop_hdr_tkn[] Ary_empty = new Xop_hdr_tkn[0];
}
