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
package gplx.core.regxs; import gplx.*; import gplx.core.*;
public class Gfo_pattern_itm_ {
	public static final byte Tid_text = 0, Tid_wild = 1;
	public static Gfo_pattern_itm[] Compile(byte[] raw) {
		ListAdp rv = ListAdp_.new_();
		int raw_len = raw.length;
		int itm_bgn = -1;
		Gfo_pattern_itm itm = null;
		int pos = 0;
		while (true) {
			boolean last = pos == raw_len;
			byte b = last ? Byte_ascii.Nil : raw[pos];
			switch (b) {
				case Byte_ascii.Nil:
					if (itm != null) {itm.Compile(raw, itm_bgn, pos); itm = null;}
					break;
				case Byte_ascii.Asterisk:
					if (itm != null) {itm.Compile(raw, itm_bgn, pos); itm = null;}
					rv.Add(Gfo_pattern_itm_wild._);
					break;
				default:
					if (itm_bgn == -1) {
						itm_bgn = pos;
						itm = new Gfo_pattern_itm_text();
						rv.Add(itm);
					}
					break;
			}
			++pos;
			if (last) break;
		}
		return (Gfo_pattern_itm[])rv.XtoAryAndClear(Gfo_pattern_itm.class);
	}
}
