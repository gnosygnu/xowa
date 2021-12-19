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
package gplx.xowa.htmls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
public class Xoh_consts {
	public static final String
	  Atr_xowa_title_str		= "xowa_title"
	, Img_w_str					= "width"
	, Img_h_str					= "height"
	;
	public static final byte[] 
	  __end				= BryUtl.NewA7(">")
	, __inline			= BryUtl.NewA7("/>")
	, __end_quote		= BryUtl.NewA7("\">")
	, __inline_quote	= BryUtl.NewA7("\"/>")
	, Space_2			= BryUtl.NewA7("  ")

	, A_mid_id = BryUtl.NewA7("\" id=\"xolnki_")
	, Div_bgn_open = BryUtl.NewA7("<div ")

	, Img_bgn					= BryUtl.NewA7("<img src=\"")
	, Span_bgn_open				= BryUtl.NewA7("<span")
	, Span_end					= BryUtl.NewA7("</span>")
	, Span_bgn					= BryUtl.NewA7("<span>")

	, Pre_bgn					= BryUtl.NewA7("<pre>"), Pre_end = BryUtl.NewA7("</pre>")
	, Pre_bgn_open				= BryUtl.NewA7("<pre")
	, Pre_bgn_overflow			= BryUtl.NewA7("<pre style=\"overflow:auto\">")

	, Code_bgn_closed			= BryUtl.NewA7("<code>")
	, Code_bgn_open				= BryUtl.NewA7("<code")
	, Code_end					= BryUtl.NewA7("</code>")
	, Id_atr					= BryUtl.NewA7(" id=\"")
	, Style_atr					= BryUtl.NewA7(" style=\"")
	, Atr_xowa_title_bry		= BryUtl.NewA7(Atr_xowa_title_str)
	;
	public static final int Nbsp_int = 160;
	public static String Escape_apos(String s) {return StringUtl.Replace(s, "'", "\"");}
}
