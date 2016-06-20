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
	public byte[] Encode(byte[] src) {
		int src_len = src.length;
		for (int i = 0; i < src_len; i++) {
			byte b = src[i];
			switch (b) {
				case Byte_ascii.Space:		bb.Add(Bry_underline); break;
				case Byte_ascii.Amp:		bb.Add(Bry_amp); break;
				case Byte_ascii.Apos:		bb.Add(Bry_apos); break;
				case Byte_ascii.Eq:			bb.Add(Bry_eq); break;
				case Byte_ascii.Plus:		bb.Add(Bry_plus); break;
				default:					bb.Add_byte(b); break;
				// FUTURE: html_entities, etc:
			}
		}
		return bb.To_bry_and_clear();
	}
	private static final    byte[] Bry_amp = Bry_.new_a7("%26"), Bry_eq = Bry_.new_a7("%3D")
		, Bry_plus = Bry_.new_a7("%2B"), Bry_apos = Bry_.new_a7("%27")
		, Bry_underline = new byte[] {Byte_ascii.Underline}
		;
	Bry_bfr bb = Bry_bfr_.New();
	public static final    Xoa_url_encoder Instance = new Xoa_url_encoder(); Xoa_url_encoder() {}
}
