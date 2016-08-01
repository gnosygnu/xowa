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
package gplx.xowa.apps.urls; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
public class Xoa_url_encoder {
	private final    Bry_bfr bfr = Bry_bfr_.New();
	public byte[] Encode(byte[] src) {
		int src_len = src.length;
		for (int i = 0; i < src_len; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Space:		bfr.Add(Bry__underline); break;
				case Byte_ascii.Amp:		bfr.Add(Bry__amp); break;
				case Byte_ascii.Apos:		bfr.Add(Bry__apos); break;
				case Byte_ascii.Eq:			bfr.Add(Bry__eq); break;
				case Byte_ascii.Plus:		bfr.Add(Bry__plus); break;
				default:					bfr.Add_byte(b); break;
			}
		}
		return bfr.To_bry_and_clear();
	}
	private static final    byte[] Bry__amp = Bry_.new_a7("%26"), Bry__eq = Bry_.new_a7("%3D")
	, Bry__plus = Bry_.new_a7("%2B"), Bry__apos = Bry_.new_a7("%27")
	, Bry__underline = new byte[] {Byte_ascii.Underline}
	;
}
