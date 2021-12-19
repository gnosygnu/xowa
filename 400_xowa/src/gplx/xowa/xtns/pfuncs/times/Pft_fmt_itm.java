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
package gplx.xowa.xtns.pfuncs.times;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.commons.GfoDate;
import gplx.xowa.*;
import gplx.xowa.langs.*;
public interface Pft_fmt_itm {
	int TypeId();
	void Fmt(BryWtr bfr, Xowe_wiki wiki, Xol_lang_itm lang, GfoDate date, Pft_func_formatdate_bldr bldr);
}
