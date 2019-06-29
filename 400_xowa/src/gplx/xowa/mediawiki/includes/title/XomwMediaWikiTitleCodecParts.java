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
package gplx.xowa.mediawiki.includes.title; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
public class XomwMediaWikiTitleCodecParts {
	public byte[] interwiki;
	public boolean local_interwiki;
	public byte[] fragment = Bry_.Empty;
	public int ns;
	public byte[] dbkey;
	public byte[] user_case_dbkey;
	public XomwMediaWikiTitleCodecParts(byte[] dbkey, int defaultNamespace) {
		this.interwiki = Bry_.Empty;
		this.local_interwiki = false;
		this.fragment = Bry_.Empty;
		this.ns = defaultNamespace;
		this.dbkey = dbkey;
		this.user_case_dbkey = dbkey;
	}
	public String ToStr() {
		Bry_bfr bfr = Bry_bfr_.New();
		bfr.Add_int_variable(ns).Add_byte_pipe();
		bfr.Add(dbkey).Add_byte_pipe();
		return bfr.To_str_and_clear();
	}
}
