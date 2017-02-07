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
package gplx.xowa.parsers.miscs; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.xowa.parsers.tmpls.*;
public class Xop_ignore_tkn extends Xop_tkn_itm_base {
	public Xop_ignore_tkn(int bgn, int end, byte ignore_type) {this.Tkn_ini_pos(false, bgn, end); this.ignore_type = ignore_type;}
	public byte Ignore_type() {return ignore_type;} private byte ignore_type = Ignore_tid_null;
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_ignore;}
	@Override public void Tmpl_compile(Xop_ctx ctx, byte[] src, Xot_compile_data prep_data) {}
	@Override public boolean Tmpl_evaluate(Xop_ctx ctx, byte[] src, Xot_invk caller, Bry_bfr bfr) {return true;}
	public static final byte 
	  Ignore_tid_null = 0, Ignore_tid_comment = 1, Ignore_tid_include_tmpl = 2, Ignore_tid_include_wiki = 3, Ignore_tid_htmlTidy_tblw = 4
	, Ignore_tid_xnde_dangling = 5, Ignore_tid_nbsp = 6, Ignore_tid_empty_li = 7, Ignore_tid_pre_at_bos = 8, Ignore_tid_tr_w_td = 9, Ignore_tid_double_pipe = 10;
}
