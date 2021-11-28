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
package gplx.xowa.mediawiki.extensions.JsonConfig.includes; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.extensions.*; import gplx.xowa.mediawiki.extensions.JsonConfig.*;
public interface Xomw_page_fetcher {
	byte[] Get_wtxt(byte[] wiki, byte[] page);
}
class Xomw_page_fetcher__mock implements Xomw_page_fetcher {
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	public void Set_wtxt(byte[] wiki, byte[] page, byte[] wtxt) {
		hash.Add(Make_key(wiki, page), wtxt);
	}
	public byte[] Get_wtxt(byte[] wiki, byte[] page) {
		return (byte[])hash.Get_by(Make_key(wiki, page));
	}
	private static byte[] Make_key(byte[] wiki, byte[] page) {
		return Bry_.Add(wiki, Byte_ascii.Pipe_bry, page);
	}
}
