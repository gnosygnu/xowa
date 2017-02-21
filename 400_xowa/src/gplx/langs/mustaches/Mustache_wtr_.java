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
package gplx.langs.mustaches; import gplx.*; import gplx.langs.*;
public class Mustache_wtr_ {
	public static byte[] Write_to_bry(byte[] src, Mustache_doc_itm itm) {return Write_to_bry(Bry_bfr_.New(), src, itm);}
	public static byte[] Write_to_bry(Bry_bfr bfr, byte[] src, Mustache_doc_itm itm) {
		Mustache_tkn_parser parser = new Mustache_tkn_parser();
		Mustache_tkn_itm root = parser.Parse(src, 0, src.length);
		Mustache_render_ctx ctx = new Mustache_render_ctx().Init(itm);
		Mustache_bfr mbfr = new Mustache_bfr(bfr);
		root.Render(mbfr, ctx);
		return mbfr.To_bry_and_clear();
	}
}
