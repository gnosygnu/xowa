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
package gplx.langs.htmls.encoders; import gplx.*; import gplx.langs.*; import gplx.langs.htmls.*;
import gplx.core.btries.*; import gplx.xowa.parsers.amps.*;
import gplx.langs.htmls.*;
public class Gfo_url_encoder implements Url_encoder_interface {
	private final    Gfo_url_encoder_itm[] encode_ary, decode_ary; private final    Gfo_url_encoder anchor_encoder;
	public Gfo_url_encoder(Gfo_url_encoder_itm[] encode_ary, Gfo_url_encoder_itm[] decode_ary, Gfo_url_encoder anchor_encoder) {
		this.encode_ary = encode_ary; this.decode_ary = decode_ary; this.anchor_encoder = anchor_encoder;
	}
	public String	Encode_str(String str)			{return String_.new_u8(Encode(Bry_.new_u8(str)));}
	public byte[]	Encode_bry(String str)			{return Encode(Bry_.new_u8(str));}
	public byte[]	Encode(byte[] bry)				{Bry_bfr bfr = Bry_bfr_.Get();	Encode(bfr, bry, 0, bry.length); return bfr.To_bry_and_rls();}
	public Bry_bfr	Encode(Bry_bfr bfr, byte[] bry) {								Encode(bfr, bry, 0, bry.length); return bfr;}
	public void		Encode(Bry_bfr bfr, byte[] bry, int bgn, int end) {
		for (int i = bgn; i < end; ++i) {
			byte b = bry[i];
			if (anchor_encoder != null && b == Byte_ascii.Hash) {
				bfr.Add_byte(Byte_ascii.Hash);
				anchor_encoder.Encode(bfr, bry, i + 1, end);
				break;
			}
			Gfo_url_encoder_itm itm = encode_ary[b & 0xff];// PATCH.JAVA:need to convert to unsigned byte
			i += itm.Encode(bfr, bry, end, i, b);
		}
	}
	public byte[] Encode_to_file_protocol(Io_url url) {
		Bry_bfr bfr = Bry_bfr_.Get();
		bfr.Add(Io_url.Http_file_bry);
		Encode(bfr, url.RawBry());
		return bfr.To_bry_and_rls();
	}
	public String	Decode_str(String str)									{return String_.new_u8(Decode(Bry_.new_u8(str)));}
	public byte[]	Decode(byte[] bry)										{return Decode(Bool_.N, bry,   0, bry.length);}
	public byte[]	Decode(byte[] bry, int bgn, int end)					{return Decode(Bool_.N, bry, bgn, end);}
	private byte[]	Decode(boolean fail, byte[] bry, int bgn, int end)			{Bry_bfr bfr = Bry_bfr_.Get(); Decode(bfr, fail, bry, bgn, end); return bfr.To_bry_and_rls();}
	public Bry_bfr	Decode(Bry_bfr bfr, boolean fail, byte[] bry, int bgn, int end) {
		for (int i = bgn; i < end; ++i) {
			byte b = bry[i];
			if (anchor_encoder != null && b == Byte_ascii.Hash) {
				bfr.Add_byte(Byte_ascii.Hash);
				anchor_encoder.Decode(bfr, Bool_.N, bry, i + 1, end);
				break;
			}
			Gfo_url_encoder_itm itm = decode_ary[b & 0xff];// PATCH.JAVA:need to convert to unsigned byte
			i += itm.Decode(bfr, bry, end, i, b, fail);
		}
		return bfr;
	}
}
