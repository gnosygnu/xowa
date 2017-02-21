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
package gplx.xowa.mediawiki.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*;
public class XomwMagicWordMgr {
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	public void Add(byte[] name, boolean cs, byte[]... synonyms) {
		XomwMagicWord mw = new XomwMagicWord(name, cs, synonyms);
		hash.Add(name, mw);
	}
	public XomwMagicWord Get(byte[] name) {
		return (XomwMagicWord)hash.Get_by(name);
	}
}
