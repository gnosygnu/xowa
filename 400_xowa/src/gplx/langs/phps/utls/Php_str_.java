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
package gplx.langs.phps.utls; import gplx.*; import gplx.langs.*; import gplx.langs.phps.*;
public class Php_str_ {
	public static byte[] Substr(byte[] src, int bgn, int len) {return Bry_.Mid(src, bgn, bgn + len);}
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
}
