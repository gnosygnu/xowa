/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.xowa.files; import gplx.*; import gplx.xowa.*;
import gplx.core.consoles.*; import gplx.langs.htmls.encoders.*;
public class Xof_file_wkr_ {
	private static final    gplx.core.security.Hash_algo md5_hash = gplx.core.security.Hash_algo_.New__md5();
	public static final    Gfo_url_encoder Md5_decoder = Gfo_url_encoder_.New__http_url().Init__same__many(Byte_ascii.Plus).Make();
	public static byte[] Md5_fast(byte[] v) {return md5_hash.Hash_bry_as_bry(v);}
	public static byte[] Md5(byte[] ttl) {
		ttl = Md5_decoder.Decode(Ttl_standardize(ttl));
		return Xof_file_wkr_.Md5_fast(ttl);					// NOTE: md5 is calculated off of url_decoded ttl; EX: A%2Cb is converted to A,b and then md5'd. note that A%2Cb still remains the title
	}
	public static byte[] Ttl_standardize(byte[] src) {
		int len = src.length; if (len == 0) return src;
		byte[] rv = null;
		boolean dirty = false;
		byte b = src[0];
		if (b > 96 && b < 123) {
			dirty = true;
			rv = new byte[len];
			rv[0] = (byte)(b - 32);	// NOTE: [[File:]] automatically uppercases 1st letter for md5; EX:en.d:File:wikiquote-logo.png has md5 of "32" (W...) not "82" (w...); PAGE:en.d:freedom_of_speech DATE:2016-01-21
		}
		for (int i = 1; i < len; ++i) {
			b = src[i];
			if (b == Byte_ascii.Space) {
				if (!dirty) {
					dirty = true;
					rv = new byte[len]; Bry_.Copy_by_pos(src, 0, i, rv, 0);
				}
				rv[i] = Byte_ascii.Underline;
			}
			else {
				if (dirty)
					rv[i] = b;
			}
		}
		return dirty ? rv : src;
	}
}
