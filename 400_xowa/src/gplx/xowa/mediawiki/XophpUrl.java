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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
import gplx.core.net.*;
public class XophpUrl {
	public static final int 
	  PHP_URL_SCHEME = 0
	, PHP_URL_HOST = 3
	, PHP_URL_PATH = 5
	;
	public static String parse_url(String url, int type) {
		Gfo_url_parser parser = new Gfo_url_parser();
		Gfo_url url_itm = parser.Parse(Bry_.new_u8(url));
		switch (type) {
			case PHP_URL_SCHEME:   return String_.new_u8(url_itm.Protocol_bry());
			case PHP_URL_HOST:     return String_.new_u8(url_itm.Segs__get_at_1st());
			case PHP_URL_PATH:
				Bry_bfr bfr = Bry_bfr_.New();
				byte[][] segs = url_itm.Segs();
				int len = segs.length;
				for (int i = 1; i < len; i++) {
					bfr.Add_byte_slash().Add(segs[i]);
				}
				return bfr.To_str_and_clear();
			default: throw Err_.new_unhandled_default(type);
		}
	}
}
