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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
class Vnt_flag_itm_ {
	public static final int
	  Tid_unknown	=  0
	, Tid_show		=  1	// S:						EX: -{S|zh-hans:A;zh-hant:B}-	-> "A"
	, Tid_all		=  2	// +:						EX: -{+|zh-hans:A;zh-hant:B}-	-> "A"
	, Tid_err		=  3	// E:						EX: -{E|zh-hans:A;zh-hant:B}-	-> "A"
	, Tid_add		=  4	// A:	add and output;		EX: -{A|zh-hans:A;zh-hant:B}-	-> "A"
	, Tid_title		=  5	// T:	page_title;			EX: -{T|zh-hans:A;zh-hant:B}-	-> ""
	, Tid_raw		=  6	// R:	raw: no convert;	EX: -{R|zh-hans:A;zh-hant:B}-	-> "zh-hans:A;zh-hant:B"
	, Tid_descrip	=  7	// D:	describe;			EX: -{D|zh-hans:A;zh-hant:B}-	-> "简体：A；繁體：B；" (简体=Simplified;繁體=Traditional)
	, Tid_del 		=  8	// -:	remove;				EX: -{-|zh-hans:A;zh-hant:B}-	-> ""
	, Tid_macro 	=  9	// H:	macro;				EX: -{H|zh-hans:A;zh-hant:B}-	-> ""
	, Tid_name 		= 10	// N:						EX: -{N|zh-hans:A;zh-hant:B}-	-> ""
	, Tid_lang		= 11	// vnt:						EX: -{zh-hant|B}-				-> "B"
	, Tid__max		= 12
	;
	private static final String[] Tid__names = new String[]
	{ "unknown", "show", "all", "err", "add", "title"
	, "raw", "descrip", "del", "macro", "name", "lang"
	};
	public static String To_name(int tid) {return Tid__names[tid];}
	public static final Hash_adp_bry Regy = Hash_adp_bry.ci_a7()	// NOTE: match either lc or uc; EX: -{D}- or -{d}-;
	.Add_byte_int(Byte_ascii.Ltr_S			, Tid_show)
	.Add_byte_int(Byte_ascii.Plus			, Tid_all)
	.Add_byte_int(Byte_ascii.Ltr_E			, Tid_err)
	.Add_byte_int(Byte_ascii.Ltr_A			, Tid_add)
	.Add_byte_int(Byte_ascii.Ltr_T			, Tid_title)
	.Add_byte_int(Byte_ascii.Ltr_R			, Tid_raw)
	.Add_byte_int(Byte_ascii.Ltr_D			, Tid_descrip)
	.Add_byte_int(Byte_ascii.Dash			, Tid_del)
	.Add_byte_int(Byte_ascii.Ltr_H			, Tid_macro)
	.Add_byte_int(Byte_ascii.Ltr_N			, Tid_name)
	;
}
