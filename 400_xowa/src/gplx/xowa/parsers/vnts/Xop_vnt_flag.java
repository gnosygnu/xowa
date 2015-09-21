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
import gplx.core.btries.*;
public class Xop_vnt_flag {
	public Xop_vnt_flag(int tid) {this.tid = tid; this.mask = 0;}
	public Xop_vnt_flag(int tid, int mask) {this.tid = tid; this.mask = mask;}
	public int Tid() {return tid;} private final int tid;
	public int Mask() {return mask;} private final int mask;
	public static Xop_vnt_flag new_lang(int mask) {return new Xop_vnt_flag(Xop_vnt_flag_.Tid_lang, mask);}
}
class Xop_vnt_flag_ {	// REF.MW: /languages/LanguageConverter.php
	public static final Xop_vnt_flag[] Ary_empty = new Xop_vnt_flag[0];
	public static final int
	  Tid_unknown	=  0
	, Tid_show		=  1	// EX: -{S|zh-hans:A;zh-hant:B}-  -> "A"
	, Tid_all		=  2	// EX: -{+|zh-hans:A;zh-hant:B}-  -> "A"
	, Tid_err		=  3	// EX: -{E|zh-hans:A;zh-hant:B}-  -> "A"
	, Tid_add		=  4	// add and output;		EX: -{A|zh-hans:A;zh-hant:B}-  -> "A"
	, Tid_title		=  5	// page_title;			EX: -{T|zh-hans:A;zh-hant:B}-  -> ""
	, Tid_raw		=  6	// raw: no convert;		EX: -{R|zh-hans:A;zh-hant:B}-  -> "zh-hans:A;zh-hant:B"
	, Tid_descrip	=  7	// describe;			EX: -{D|zh-hans:A;zh-hant:B}-  -> "简体：A；繁體：B；" (简体=Simplified;繁體=Traditional)
	, Tid_del 		=  8	// remove;				EX: -{-|zh-hans:A;zh-hant:B}-  -> ""
	, Tid_macro 	=  9	// macro;				EX: -{H|zh-hans:A;zh-hant:B}-  -> ""
	, Tid_name 		= 10	// EX: -{N|zh-hans:A;zh-hant:B}-		-> ""
	, Tid_lang		= 11	// EX: -{zh-hant|B}-					-> "B"
	, Tid_len		= 12
	;
	public static final byte Tid__max = 12;
	private static final String[] Tid__names = new String[]
	{ "unknown", "show", "all", "err", "add", "title"
	, "raw", "descrip", "del", "macro", "name", "lang"
	};
	public static String To_name(int tid) {return Tid__names[tid];}
	public static final Xop_vnt_flag
	  Flag_unknown	= new Xop_vnt_flag(Tid_unknown)
	, Flag_show		= new Xop_vnt_flag(Tid_show)
	, Flag_all		= new Xop_vnt_flag(Tid_all)
	, Flag_err		= new Xop_vnt_flag(Tid_err)
	, Flag_add		= new Xop_vnt_flag(Tid_add)
	, Flag_title	= new Xop_vnt_flag(Tid_title)
	, Flag_raw		= new Xop_vnt_flag(Tid_raw)
	, Flag_descrip	= new Xop_vnt_flag(Tid_descrip)
	, Flag_del		= new Xop_vnt_flag(Tid_del)
	, Flag_macro	= new Xop_vnt_flag(Tid_macro)
	, Flag_name		= new Xop_vnt_flag(Tid_name)
	;
	public static final Btrie_fast_mgr Trie = Btrie_fast_mgr.ci_a7()	// NOTE: match either lc or uc; EX: -{D}- or -{d}-; // NOTE:ci.ascii:MW_const.en; flag keys; EX: -{S|a}-
	.Add(Byte_ascii.Ltr_S			, Xop_vnt_flag_.Flag_show)
	.Add(Byte_ascii.Plus			, Xop_vnt_flag_.Flag_all)
	.Add(Byte_ascii.Ltr_E			, Xop_vnt_flag_.Flag_err)
	.Add(Byte_ascii.Ltr_A			, Xop_vnt_flag_.Flag_add)
	.Add(Byte_ascii.Ltr_T			, Xop_vnt_flag_.Flag_title)
	.Add(Byte_ascii.Ltr_R			, Xop_vnt_flag_.Flag_raw)
	.Add(Byte_ascii.Ltr_D			, Xop_vnt_flag_.Flag_descrip)
	.Add(Byte_ascii.Dash			, Xop_vnt_flag_.Flag_del)
	.Add(Byte_ascii.Ltr_H			, Xop_vnt_flag_.Flag_macro)
	.Add(Byte_ascii.Ltr_N			, Xop_vnt_flag_.Flag_name)
	;
}
