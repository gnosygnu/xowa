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
package gplx.xowa.wikis.pages.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
public class Xopg_db_text {
	public byte[]		Text_bry()	{return text_bry;}	private byte[] text_bry = Bry_.Empty;	// NOTE: if null, will cause NullPointer exception on Special pages like Special:XowaDownloadCentral; DATE:2016-07-05
	public void			Text_bry_(byte[] v) {this.text_bry = v;}
	public void Clear() {
		// text_bry = Bry_.Empty;	// TOMBSTONE: do not set to Bry_.Empty else causes mass parse to noop; also causes Options/Files.Clear to noop; DATE:2016-07-15
	}
}
