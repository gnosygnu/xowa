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
package gplx.xowa.mws; import gplx.*; import gplx.xowa.*;
public class Xomw_MagicWordSynonym {
	public final    byte[] magic_name;
	public final    boolean case_match;
	public final    byte[] text;
	public final    byte arg1_tid;
	public Xomw_MagicWordSynonym(byte[] magic_name, boolean case_match, byte[] text) {
		this.magic_name = magic_name;
		this.case_match = case_match;
		this.text = text;
		this.arg1_tid = Get_arg1_tid(text);
	}

	private static byte Get_arg1_tid(byte[] src) {
		int len = src.length;
		byte rv = Arg1__nil;
		int cur = 0;
		while (true) {
			if (cur == len) break;
			byte b = src[cur];
			// "$" matched
			if (b == Byte_ascii.Dollar) {
				// "1" matched?
				int nxt_pos = cur + 1;
				if (nxt_pos < len && src[nxt_pos] == Byte_ascii.Num_1) {
					// "$1" matched
					if (cur == 0) {
						rv = Arg1__bgn;
					}
					else if (cur == len - 2) {							
						rv = rv == Arg1__nil ? Arg1__end : Arg1__mix;
					}
					else {
						if (rv == Arg1__nil)
							rv = Arg1__mid;
						else if (rv == Arg1__mid)
							rv = Arg1__mix;
					}
					cur += 3;
					continue;
				}
				else {
					cur += 2;
					continue;
				}
			}
			else {
				cur += 1;
				continue;
			}
		}
		return rv;
	}

	public static final byte 
	  Arg1__nil = 0 // EX: "thumb"
	, Arg1__bgn = 1 // EX: "$1px"
	, Arg1__end = 2 // EX: "thumb=$1"
	, Arg1__mid = 3 // EX: "a$1b"
	, Arg1__mix = 4 // EX: "a$1b$cc"
	;
}
