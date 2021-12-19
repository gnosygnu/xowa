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
package gplx.xowa.mediawiki.includes.title;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
public class XomwMediaWikiTitleCodecParts {
	public byte[] interwiki;
	public boolean local_interwiki;
	public byte[] fragment = BryUtl.Empty;
	public int ns;
	public byte[] dbkey;
	public byte[] user_case_dbkey;
	public XomwMediaWikiTitleCodecParts(byte[] dbkey, int defaultNamespace) {
		this.interwiki = BryUtl.Empty;
		this.local_interwiki = false;
		this.fragment = BryUtl.Empty;
		this.ns = defaultNamespace;
		this.dbkey = dbkey;
		this.user_case_dbkey = dbkey;
	}
	public String ToStr() {
		BryWtr bfr = BryWtr.New();
		bfr.AddIntVariable(ns).AddBytePipe();
		bfr.Add(dbkey).AddBytePipe();
		return bfr.ToStrAndClear();
	}
}
