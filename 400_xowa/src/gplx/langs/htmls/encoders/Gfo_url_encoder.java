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
package gplx.langs.htmls.encoders;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.wtrs.BryBfrUtl;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.StringUtl;
import gplx.langs.htmls.Url_encoder_interface;
import gplx.types.basics.utls.BoolUtl;
import gplx.types.basics.constants.AsciiByte;
public class Gfo_url_encoder implements Url_encoder_interface {	// TS; Gfo_url_encoder_itm[] are read-only; anchor_encoder is effectively read-only
	private final Gfo_url_encoder_itm[] encode_ary, decode_ary; private final Gfo_url_encoder anchor_encoder;
	public Gfo_url_encoder(Gfo_url_encoder_itm[] encode_ary, Gfo_url_encoder_itm[] decode_ary, Gfo_url_encoder anchor_encoder) {
		this.encode_ary = encode_ary; this.decode_ary = decode_ary; this.anchor_encoder = anchor_encoder;
	}
	public String	Encode_str(String str)			{return StringUtl.NewU8(Encode(BryUtl.NewU8(str)));}
	public byte[]	Encode_bry(String str)			{return Encode(BryUtl.NewU8(str));}
	public byte[]	Encode(byte[] bry)				{BryWtr bfr = BryBfrUtl.Get();	Encode(bfr, bry, 0, bry.length); return bfr.ToBryAndRls();}
	public BryWtr Encode(BryWtr bfr, byte[] bry) {								Encode(bfr, bry, 0, bry.length); return bfr;}
	public void		Encode(BryWtr bfr, byte[] bry, int bgn, int end) {
		for (int i = bgn; i < end; ++i) {
			byte b = bry[i];
			if (anchor_encoder != null && b == AsciiByte.Hash) {
				bfr.AddByte(AsciiByte.Hash);
				anchor_encoder.Encode(bfr, bry, i + 1, end);
				break;
			}
			Gfo_url_encoder_itm itm = encode_ary[b & 0xff];// PATCH.JAVA:need to convert to unsigned byte
			i += itm.Encode(bfr, bry, end, i, b);
		}
	}
	public byte[] Encode_to_file_protocol(Io_url url) {
		BryWtr bfr = BryBfrUtl.Get();
		bfr.Add(Io_url.Http_file_bry);
		Encode(bfr, url.RawBry());
		return bfr.ToBryAndRls();
	}
	public String	Decode_str(String str)									{return StringUtl.NewU8(Decode(BryUtl.NewU8(str)));}
	public byte[]	Decode(byte[] bry)										{return Decode(BoolUtl.N, bry,   0, bry.length);}
	public byte[]	Decode(byte[] bry, int bgn, int end)					{return Decode(BoolUtl.N, bry, bgn, end);}
	private byte[]	Decode(boolean fail, byte[] bry, int bgn, int end)			{BryWtr bfr = BryBfrUtl.Get(); Decode(bfr, fail, bry, bgn, end); return bfr.ToBryAndRls();}
	public BryWtr Decode(BryWtr bfr, boolean fail, byte[] bry, int bgn, int end) {
		for (int i = bgn; i < end; ++i) {
			byte b = bry[i];
			if (anchor_encoder != null && b == AsciiByte.Hash) {
				bfr.AddByte(AsciiByte.Hash);
				anchor_encoder.Decode(bfr, BoolUtl.N, bry, i + 1, end);
				break;
			}
			Gfo_url_encoder_itm itm = decode_ary[b & 0xff];// PATCH.JAVA:need to convert to unsigned byte
			i += itm.Decode(bfr, bry, end, i, b, fail);
		}
		return bfr;
	}
}
