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
package gplx.langs.htmls;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
public class Gfh_atr_ {
	public static final byte[]
	// "coreattrs"
	  Bry__id					= BryUtl.NewA7("id")
	, Bry__class				= BryUtl.NewA7("class")
	, Bry__style				= BryUtl.NewA7("style")
	, Bry__title				= BryUtl.NewA7("title")
	// "i18n"
	, Bry__lang					= BryUtl.NewA7("lang")
	, Bry__dir					= BryUtl.NewA7("dir")
	// <a>
	, Bry__href					= BryUtl.NewA7("href")
	, Bry__rel					= BryUtl.NewA7("rel")
	, Bry__target				= BryUtl.NewA7("target")
	// <img>
	, Bry__alt					= BryUtl.NewA7("alt")
	, Bry__src					= BryUtl.NewA7("src")
	, Bry__width				= BryUtl.NewA7("width")
	, Bry__height				= BryUtl.NewA7("height")
	// <table>
	//, Bry__width				= Bry_.new_a7("width")
	, Bry__cellpadding			= BryUtl.NewA7("cellpadding")
	, Bry__cellspacing			= BryUtl.NewA7("cellspacing")
	, Bry__summary				= BryUtl.NewA7("summary")		// HTML.ua
	// <table>.borders_and_rules
	, Bry__border				= BryUtl.NewA7("border")
	, Bry__frames				= BryUtl.NewA7("frames")
	, Bry__rules				= BryUtl.NewA7("rules")
	// <th>,<td>
	, Bry__scope				= BryUtl.NewA7("scope")
	, Bry__rowspan				= BryUtl.NewA7("rowspan")
	, Bry__colspan				= BryUtl.NewA7("colspan")
	, Bry__align				= BryUtl.NewA7("align")			// HTML.v4
	, Bry__bgcolor				= BryUtl.NewA7("bgcolor")		// HTML.v4
	, Bry__abbr					= BryUtl.NewA7("abbr")			// HTML.ua
	, Bry__srcset				= BryUtl.NewA7("srcset")
	// <form>
	, Bry__action				= BryUtl.NewA7("action")
	;
	public static byte[] Make(BryWtr bfr, byte[] key, byte[] val) {
		return bfr.AddByteSpace().Add(key).AddByteEq().AddByteQuote().Add(val).AddByteQuote().ToBryAndClear();
	}
	public static byte[] Add_to_bry(BryWtr bfr, byte[] key, byte[] val) {
		bfr.AddByteSpace().Add(key).AddByteEq().AddByteQuote().Add(val).AddByteQuote();
		return bfr.ToBryAndClear();
	}
	public static void Add(BryWtr bfr, byte[] key, byte[] val) {
		bfr.AddByteSpace().Add(key).AddByteEq().AddByteQuote().Add(val).AddByteQuote();
	}
	public static void Add(BryWtr bfr, byte[] key, int val) {
		bfr.AddByteSpace().Add(key).AddByteEq().AddByteQuote();
		bfr.AddIntVariable(val);
		bfr.AddByteQuote();
	}
	public static void Add_double(BryWtr bfr, byte[] key, double val) {
		bfr.AddByteSpace().Add(key).AddByteEq().AddByteQuote();
		bfr.AddDouble(val);
		bfr.AddByteQuote();
	}
}
