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
package gplx.xowa.xtns.math.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*;
import gplx.core.primitives.*;
import gplx.core.btries.*;
class Math_func_itm {
	private final Ordered_hash props = Ordered_hash_.new_(); private final Int_obj_ref props_key = Int_obj_ref.neg1_();
	public Math_func_itm(byte[] key) {this.key = key;}
	public byte[] Key() {return key;} private final byte[] key;
	public byte[] Tex_bry() {return tex_bry;} public void Tex_bry_(byte[] v) {tex_bry = v;} private byte[] tex_bry;
	public byte[] Html_bry() {return html_bry;} public void Html_bry_(byte[] v) {html_bry = v;} private byte[] html_bry;
	public int Props__len() {return props.Count();}
	public boolean Props__has(int v) {return props.Has(props_key.Val_(v));}
	public void Props__add(int v) {
		Int_obj_ref itm = Int_obj_ref.new_(v);
		props.Add(itm, itm);
	}
	public int Get_at(int i) {
		Int_obj_ref itm = (Int_obj_ref)props.Get_at(i);
		return itm == null ? Int_.Min_value : itm.Val();
	}
}
class Math_prop_itm {
	public static final int
		Int__LITERAL = 0
	,	Int__HTMLABLE = 1
	,	Int__HTMLABLE_BIG = 2
	,	Int__HTMLABLEC = 3
	,	Int__HTMLABLEM = 4
	,	Int__BIG = 5
	,	Int__FONT_UF = 6
	,	Int__FONT_UFH = 7
	,	Int__tex_use_ams = 8
	,	Int__tex_use_euro = 9
	,	Int__tex_use_teubner = 10
	,	Int__TEX_ONLY = 11
	,	Int__DELIMITER = 12
	,	Int__FUN_AR1 = 13
	,	Int__FUN_AR2 = 14
	,	Int__FUN_AR2h = 15
	,	Int__FUN_AR1hf = 16
	,	Int__FUN_AR1hl = 17
	,	Int__FUN_AR2nb = 18
	,	Int__FUN_INFIX = 19
	,	Int__FUN_INFIXh = 20
	,	Int__DECL = 21
	,	Int__DECLh = 22
	,	Int__FONTFORCE_IT = 23
	,	Int__FONTFORCE_RM = 24
	,	Int__sym_paren_bgn = 25
	,	Int__sym_paren_end = 26
	,	Int__sym_semic = 27
	,	Int__sym_quote = 28
	;
	public static final byte[]
		Bry__LITERAL = Bry_.new_a7("LITERAL")
	,	Bry__HTMLABLE = Bry_.new_a7("HTMLABLE")
	,	Bry__HTMLABLE_BIG = Bry_.new_a7("HTMLABLE_BIG")
	,	Bry__HTMLABLEC = Bry_.new_a7("HTMLABLEC")
	,	Bry__HTMLABLEM = Bry_.new_a7("HTMLABLEM")
	,	Bry__BIG = Bry_.new_a7("BIG")
	,	Bry__FONT_UF = Bry_.new_a7("FONT_UF")
	,	Bry__FONT_UFH = Bry_.new_a7("FONT_UFH")
	,	Bry__tex_use_ams = Bry_.new_a7("tex_use_ams")
	,	Bry__tex_use_euro = Bry_.new_a7("tex_use_euro")
	,	Bry__tex_use_teubner = Bry_.new_a7("tex_use_teubner")
	,	Bry__TEX_ONLY = Bry_.new_a7("TEX_ONLY")
	,	Bry__DELIMITER = Bry_.new_a7("DELIMITER")
	,	Bry__FUN_AR1 = Bry_.new_a7("FUN_AR1")
	,	Bry__FUN_AR2 = Bry_.new_a7("FUN_AR2")
	,	Bry__FUN_AR2h = Bry_.new_a7("FUN_AR2h")
	,	Bry__FUN_AR1hf = Bry_.new_a7("FUN_AR1hf")
	,	Bry__FUN_AR1hl = Bry_.new_a7("FUN_AR1hl")
	,	Bry__FUN_AR2nb = Bry_.new_a7("FUN_AR2nb")
	,	Bry__FUN_INFIX = Bry_.new_a7("FUN_INFIX")
	,	Bry__FUN_INFIXh = Bry_.new_a7("FUN_INFIXh")
	,	Bry__DECL = Bry_.new_a7("DECL")
	,	Bry__DECLh = Bry_.new_a7("DECLh")
	,	Bry__FONTFORCE_IT = Bry_.new_a7("FONTFORCE_IT")
	,	Bry__FONTFORCE_RM = Bry_.new_a7("FONTFORCE_RM")
	;
	public static final Btrie_slim_mgr Trie = Btrie_slim_mgr.cs()
	.Add_bry_int(Bry__LITERAL, Int__LITERAL)
	.Add_bry_int(Bry__HTMLABLE, Int__HTMLABLE)
	.Add_bry_int(Bry__HTMLABLE_BIG, Int__HTMLABLE_BIG)
	.Add_bry_int(Bry__HTMLABLEC, Int__HTMLABLEC)
	.Add_bry_int(Bry__HTMLABLEM, Int__HTMLABLEM)
	.Add_bry_int(Bry__BIG, Int__BIG)
	.Add_bry_int(Bry__FONT_UF, Int__FONT_UF)
	.Add_bry_int(Bry__FONT_UFH, Int__FONT_UFH)
	.Add_bry_int(Bry__tex_use_ams, Int__tex_use_ams)
	.Add_bry_int(Bry__tex_use_euro, Int__tex_use_euro)
	.Add_bry_int(Bry__tex_use_teubner, Int__tex_use_teubner)
	.Add_bry_int(Bry__TEX_ONLY, Int__TEX_ONLY)
	.Add_bry_int(Bry__DELIMITER, Int__DELIMITER)
	.Add_bry_int(Bry__FUN_AR1, Int__FUN_AR1)
	.Add_bry_int(Bry__FUN_AR2, Int__FUN_AR2)
	.Add_bry_int(Bry__FUN_AR2h, Int__FUN_AR2h)
	.Add_bry_int(Bry__FUN_AR1hf, Int__FUN_AR1hf)
	.Add_bry_int(Bry__FUN_AR1hl, Int__FUN_AR1hl)
	.Add_bry_int(Bry__FUN_AR2nb, Int__FUN_AR2nb)
	.Add_bry_int(Bry__FUN_INFIX, Int__FUN_INFIX)
	.Add_bry_int(Bry__FUN_INFIXh, Int__FUN_INFIXh)
	.Add_bry_int(Bry__DECL, Int__DECL)
	.Add_bry_int(Bry__DECLh, Int__DECLh)
	.Add_bry_int(Bry__FONTFORCE_IT, Int__FONTFORCE_IT)
	.Add_bry_int(Bry__FONTFORCE_RM, Int__FONTFORCE_RM)
	;
}
