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
package gplx.xowa.hdumps.core; import gplx.*; import gplx.xowa.*; import gplx.xowa.hdumps.*;
class Hdump_file_itm {
	public byte[] Lnki_ttl() {return lnki_ttl;} private byte[] lnki_ttl;
	public byte Lnki_tid() {return lnki_tid;} private byte lnki_tid;
	public int Lnki_w() {return lnki_w;} private int lnki_w;
	public int Lnki_h() {return lnki_h;} private int lnki_h;
	public byte Lnki_align_x() {return lnki_align_x;} private byte lnki_align_x;
	public byte Lnki_align_y() {return lnki_align_y;} private byte lnki_align_y;
	public byte Lnki_border() {return lnki_border;} private byte lnki_border;
	public double Lnki_upright() {return lnki_upright;} private double lnki_upright;
	public double Lnki_time() {return lnki_time;} private double lnki_time;
	public int Lnki_page() {return lnki_page;} private int lnki_page;
	public boolean Lnki_media_icon() {return lnki_media_icon;} private boolean lnki_media_icon;
	public byte[] Lnki_alt() {return lnki_alt;} private byte[] lnki_alt;
	public byte[] Lnki_caption() {return lnki_caption;} private byte[] lnki_caption;
	public void Parse(byte[] src) {
		int len = src.length;
		int pos = 0;
		int fld_idx = 0, fld_bgn = 0;
		while (pos < len) {
			byte b = src[pos];
			if (b == Byte_ascii.Pipe) {
				switch (fld_idx) {
					case  0: lnki_ttl				= Bry_.Mid(src, fld_bgn, pos); break;
					case  2: lnki_tid				= Bry_.Xto_byte_by_int(src, fld_bgn, pos, Byte_.MaxValue_127); break;
					case  3: lnki_w					= Bry_.Xto_int_or(src, fld_bgn, pos, -1); break;
					case  4: lnki_h					= Bry_.Xto_int_or(src, fld_bgn, pos, -1); break;
					case  5: lnki_align_x			= Bry_.Xto_byte_by_int(src, fld_bgn, pos, Byte_.MaxValue_127); break;
					case  6: lnki_align_y			= Bry_.Xto_byte_by_int(src, fld_bgn, pos, Byte_.MaxValue_127); break;
					case  7: lnki_border			= Bry_.Xto_byte_by_int(src, fld_bgn, pos, Byte_.MaxValue_127); break;
					case  8: lnki_upright			= Bry_.XtoDoubleByPos(src, fld_bgn, pos); break;
					case  9: lnki_time				= Bry_.XtoDoubleByPos(src, fld_bgn, pos); break;
					case 10: lnki_page				= Bry_.Xto_int_or(src, fld_bgn, pos, -1); break;
					case 11: lnki_media_icon		= src[pos] == Byte_ascii.Ltr_y; break;
					case 12: lnki_alt				= Bry_.Mid(src, fld_bgn, pos); break;
					case 13: lnki_caption			= Bry_.Mid(src, fld_bgn, pos); break;
				}
				++fld_idx;
			}
		}
	}		
}
