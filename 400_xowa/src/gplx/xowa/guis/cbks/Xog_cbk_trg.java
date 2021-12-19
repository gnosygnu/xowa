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
package gplx.xowa.guis.cbks;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
public class Xog_cbk_trg {
	Xog_cbk_trg(byte tid, byte[] page_ttl, String page_guid) {
		this.tid = tid;
		this.page_ttl = page_ttl;
		this.page_guid = page_guid;
	}
	public byte   Tid()       {return tid;} private final byte tid;
	public byte[] Page_ttl()  {return page_ttl;} private final byte[] page_ttl;	// same as ttl.Full_db(); EX: Special:XowaDownloadCentral
	public String Page_guid() {return page_guid;} private final String page_guid;

	public static final byte Tid__cbk_enabled = 0, Tid__specific_page = 1, Tid__page_guid = 2;

	public static final Xog_cbk_trg Any = new Xog_cbk_trg(Tid__cbk_enabled, null, StringUtl.Empty);
	public static Xog_cbk_trg New_by_page(byte[] page_ttl)  {return new Xog_cbk_trg(Tid__specific_page, page_ttl   , StringUtl.Empty);}
	public static Xog_cbk_trg New_by_guid(String page_guid) {return new Xog_cbk_trg(Tid__page_guid    , BryUtl.Empty , page_guid);}
}
