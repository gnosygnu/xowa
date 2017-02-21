/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
class Vnt_flag_code_ {
	public static final int
	  Tid_add			=  0	// +:						EX: -{+|zh-hans:A;zh-hant:B}-	-> "A"
	, Tid_del 			=  1	// -:	remove;				EX: -{-|zh-hans:A;zh-hant:B}-	-> ""
	, Tid_aout			=  2	// A:	Add and output;		EX: -{A|zh-hans:A;zh-hant:B}-	-> "A"
	, Tid_hide			=  3	// H:	Hide macro;			EX: -{H|zh-hans:A;zh-hant:B}-	-> ""
	, Tid_raw			=  4	// R:	Raw: no convert;	EX: -{R|zh-hans:A;zh-hant:B}-	-> "zh-hans:A;zh-hant:B"
	, Tid_show			=  5	// S:	Show				EX: -{S|zh-hans:A;zh-hant:B}-	-> "A"
	, Tid_descrip		=  6	// D:	Describe;			EX: -{D|zh-hans:A;zh-hant:B}-	-> "简体：A；繁體：B；" (简体=Simplified;繁體=Traditional)
	, Tid_name 			=  7	// N:	variant Name		EX: -{N|zh-hans:A;zh-hant:B}-	-> ""
	, Tid_title			=  8	// T:	page Title;			EX: -{T|zh-hans:A;zh-hant:B}-	-> ""
	, Tid_err			=  9	// E:	Error				EX: -{E|zh-hans:A;zh-hant:B}-	-> "A"
	, Tid__max			= 10
	;
	private static final String[] Tid__names = new String[]
	{ "+", "-", "A", "H", "R"
	, "S", "D", "N", "T", "E"
	};
	public static String To_str(int tid) {return Tid__names[tid];}
	public static final Hash_adp_bry Regy = Hash_adp_bry.ci_a7()	// NOTE: match either lc or uc; EX: -{D}- or -{d}-;
	.Add_byte_int(Byte_ascii.Plus			, Tid_add)
	.Add_byte_int(Byte_ascii.Dash			, Tid_del)
	.Add_byte_int(Byte_ascii.Ltr_A			, Tid_aout)
	.Add_byte_int(Byte_ascii.Ltr_H			, Tid_hide)
	.Add_byte_int(Byte_ascii.Ltr_R			, Tid_raw)
	.Add_byte_int(Byte_ascii.Ltr_S			, Tid_show)
	.Add_byte_int(Byte_ascii.Ltr_D			, Tid_descrip)
	.Add_byte_int(Byte_ascii.Ltr_N			, Tid_name)
	.Add_byte_int(Byte_ascii.Ltr_T			, Tid_title)
	.Add_byte_int(Byte_ascii.Ltr_E			, Tid_err)
	;
}
