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
package gplx.xowa.parsers.lists; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
public class Xop_list_tkn extends Xop_tkn_itm_base {
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_list;}
	public int	List_uid() {return list_uid;} public Xop_list_tkn List_uid_(int v) {list_uid = v; return this;} private int list_uid = -1;
	public byte List_bgn() {return list_bgn;} private byte list_bgn;
	public byte List_itmTyp() {return list_itmTyp;} public Xop_list_tkn List_itmTyp_(byte v) {list_itmTyp = v; return this;} private byte list_itmTyp = Xop_list_tkn_.List_itmTyp_null;
	public int[] List_path() {return path;} public Xop_list_tkn List_path_(int... v) {path = v; return this;} private int[] path = Int_.Ary_empty;
	public int List_path_idx() {return path[path.length - 1];}
	public boolean List_sub_first() {return List_path_idx() == 0;}
	public byte List_sub_last() {return list_sub_last;} public Xop_list_tkn List_sub_last_(byte v) {list_sub_last = v; return this;} private byte list_sub_last = Bool_.__byte;
	public static Xop_list_tkn bgn_(int bgn, int end, byte list_itmTyp, int symLen) {return new Xop_list_tkn(bgn, end, Bool_.Y_byte, list_itmTyp);}
	public static Xop_list_tkn end_(int pos, byte list_itmTyp) {return new Xop_list_tkn(pos, pos, Bool_.N_byte, list_itmTyp);}
	public Xop_list_tkn(int bgn, int end, byte bgnEndType, byte list_itmTyp) {this.Tkn_ini_pos(false, bgn, end); this.list_bgn = bgnEndType; this.list_itmTyp = list_itmTyp;}		
	public static final Xop_list_tkn Null = new Xop_list_tkn(); Xop_list_tkn() {}
}
