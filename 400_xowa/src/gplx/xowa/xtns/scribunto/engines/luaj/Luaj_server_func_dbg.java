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
package gplx.xowa.xtns.scribunto.engines.luaj;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.constants.AsciiByte;
import gplx.xowa.xtns.scribunto.*;
import org.luaj.vm2.Varargs;
import org.luaj.vm2.lib.VarArgFunction;
public class Luaj_server_func_dbg extends VarArgFunction {
	private final Scrib_core core;
	public Luaj_server_func_dbg(Scrib_core v) {this.core = v;}	
	public Varargs invoke(Varargs args) {
		// init bfrs
		byte dbg_separator = AsciiByte.Tab;
		BryWtr dbg_bfr = BryWtr.New();
		BryWtr html_bfr = BryWtr.New();
		dbg_bfr.Add(core.Frame_current().Frame_ttl()).AddByte(dbg_separator);
		html_bfr.AddStrA7("<span class='xowa_dbg' style='color:red'>");
		
		// loop args and add to bfrs
		int len = args.narg();
		for (int i = 1; i <= len; ++i) {
			String s = args.arg(i).toString();
			if (i != 1) dbg_bfr.AddByte(dbg_separator);
			dbg_bfr.AddStrU8(s);
			html_bfr.AddStrU8(gplx.langs.htmls.Gfh_utl.Escape_html_as_str(s) + "&nbsp;");
		}
		
		// term bfrs and print 
		html_bfr.AddStrA7("</span><br/>");
		core.Page().Html_data().Xtn_scribunto_dbg_(html_bfr.ToBryAndClear());
		gplx.core.consoles.Console_adp__sys.Instance.Write_str_w_nl_utf8(dbg_bfr.ToStrAndClear());
		return NONE;
	}
}
