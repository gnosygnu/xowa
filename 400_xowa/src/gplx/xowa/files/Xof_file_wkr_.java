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
import gplx.core.consoles.*;
public class Xof_file_wkr_ {
	public static final Url_encoder Md5_decoder = Url_encoder.new_http_url_().Itms_raw_same_many(Byte_ascii.Plus);
	public static byte[] Md5_fast(byte[] v) {return Bry_.new_a7(gplx.security.HashAlgo_.Md5.CalcHash(Console_adp_.Noop, gplx.ios.IoStream_.ary_(v)));}
	public static byte[] Md5_(byte[] ttl) {
		ttl = Md5_decoder.Decode_lax(Ttl_standardize(ttl));
		return Xof_file_wkr_.Md5_fast(ttl);					// NOTE: md5 is calculated off of url_decoded ttl; EX: A%2Cb is converted to A,b and then md5'd. note that A%2Cb still remains the title
	}
	public static byte[] Ttl_standardize(byte[] ttl) {
		int ttl_len = ttl.length;
		for (int i = 0; i < ttl_len; i++) {	// convert all spaces to _; NOTE: not same as lnki.Ttl().Page_url(), b/c Page_url does incompatible encoding
			byte b = ttl[i];
			if (b == Byte_ascii.Space) ttl[i] = Byte_ascii.Underline;
			if (i == 0) {
				if (b > 96 && b < 123) ttl[i] -= 32;	// NOTE: file automatically uppercases 1st letter
			}
		}
		return ttl;
	}
}
