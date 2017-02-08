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
package gplx.xowa.mws.utls; import gplx.*; import gplx.xowa.*; import gplx.xowa.mws.*;
import gplx.core.btries.*;
public class Php_str_ {
	public static int Strpos(byte[] src, byte find) {return Strpos(src, find, 0, src.length);}
	public static int Strpos(byte[] src, byte find, int bgn, int end) {
		return Bry_find_.Find_fwd(src, find, bgn, end);
	}
	public static byte[] Substr(byte[] src, int bgn) {return Substr(src, bgn, src.length);}
	public static byte[] Substr(byte[] src, int bgn, int len) {
		int src_len = src.length;
		if (bgn < 0) bgn = src_len + bgn; // handle negative
		if (bgn < 0) bgn = 0;	// handle out of bounds; EX: ("a", -1, -1)
		int end = len < 0 ? src_len + len : bgn + len;
		if (end > src.length) end = src.length;; // handle out of bounds;
		return Bry_.Mid(src, bgn, end);
	}
	public static byte Substr_byte(byte[] src, int bgn) {return Substr_byte(src, bgn, src.length);}
	public static byte Substr_byte(byte[] src, int bgn, int len) {
		int src_len = src.length;
		if (src_len == 0) return Byte_ascii.Null;
		if (bgn < 0) bgn = src_len + bgn; // handle negative
		if (bgn < 0) bgn = 0;	// handle out of bounds; EX: ("a", -1, -1)
		int end = len < 0 ? src_len + len : bgn + len;
		if (end > src.length) end = src.length;; // handle out of bounds;
		return src[bgn];
	}
	public static int Strspn_fwd__ary(byte[] src, boolean[] find, int bgn, int max, int src_len) {
		if (max == -1) max = src_len;
		int rv = 0;
		for (int i = bgn; i < src_len; i++) {
			if (find[src[i] & 0xFF] && rv < max) // PATCH.JAVA:need to convert to unsigned byte
				rv++;
			else
				break;
		}
		return rv;
	}
	public static int Strspn_fwd__byte(byte[] src, byte find, int bgn, int max, int src_len) {
		if (max == -1) max = src_len;
		int rv = 0;
		for (int i = bgn; i < src_len; i++) {
			if (find == src[i] && rv < max) 
				rv++;
			else
				break;
		}
		return rv;
	}
	public static int Strspn_fwd__space_or_tab(byte[] src, int bgn, int max, int src_len) {
		if (max == -1) max = src_len;
		int rv = 0;
		for (int i = bgn; i < src_len; i++) {
			switch (src[i]) {
				case Byte_ascii.Space:
				case Byte_ascii.Tab:
					if (rv < max) {
						rv++;
						continue;
					}
					break;
			}
			break;
		}
		return rv;
	}
	public static int Strspn_bwd__byte(byte[] src, byte find, int bgn, int max) {
		if (max == -1) max = Int_.Max_value;
		int rv = 0;
		for (int i = bgn - 1; i > -1; i--) {
			if (find == src[i] && rv < max) 
				rv++;
			else
				break;
		}
		return rv;
	}
	public static int Strspn_bwd__ary(byte[] src, boolean[] find, int bgn, int max) {
		if (max == -1) max = Int_.Max_value;
		int rv = 0;
		for (int i = bgn - 1; i > -1; i--) {
			if (find[src[i & 0xFF]] && rv < max)  // PATCH.JAVA:need to convert to unsigned byte
				rv++;
			else
				break;
		}
		return rv;
	}
	public static int Strspn_bwd__space_or_tab(byte[] src, int bgn, int max) {
		if (max == -1) max = Int_.Max_value;
		int rv = 0;
		for (int i = bgn - 1; i > -1; i--) {
			switch (src[i]) {
				case Byte_ascii.Space:
				case Byte_ascii.Tab:
					if (rv < max) {
						rv++;
						continue;
					}
					break;
			}
			break;
		}
		return rv;
	}
	public static byte[] Strtr(byte[] src, Btrie_slim_mgr trie, Bry_bfr tmp, Btrie_rv trv) {
		boolean dirty = false;
		int src_bgn = 0;
		int src_end = src.length;
		int i = src_bgn;

		while (true) {
			if (i == src_end) break;
			byte b = src[i];
			Object o = trie.Match_at_w_b0(trv, b, src, i, src_end);
			if (o == null) {
				if (dirty) {
					tmp.Add_byte(b);
				}
				i++;
			}
			else {
				if (!dirty) {
					dirty = true;
					tmp.Add_mid(src, 0, i);
				}
				tmp.Add((byte[])o);
				i = trv.Pos();
			}
		}
		return dirty ? tmp.To_bry_and_clear() : src;
	}
	public static byte[] strtr(byte[] src, byte find, byte repl) {
		return Bry_.Replace(src, 0, src.length, find, repl);
	}
}
