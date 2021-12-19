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
package gplx.xowa.mediawiki.includes.parsers;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.xowa.mediawiki.includes.xohtml.*;
import gplx.xowa.mediawiki.includes.linkers.*;
public interface XomwParserIface {
	int nextLinkID();
	XomwParserOptions getOptions();
	XomwLinkRenderer getLinkRenderer();

	byte[] armorLinks(BryWtr trg, byte[] src, int src_bgn, int src_end);
	Xomw_atr_mgr getExternalLinkAttribs(Xomw_atr_mgr atrs);
	byte[] stripAltText(byte[] caption, XomwLinkHolderArray holders);
}
