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
package gplx.xowa.apps.gfs; import gplx.*; import gplx.xowa.*; import gplx.xowa.apps.*;
import gplx.core.brys.fmtrs.*;
import gplx.langs.phps.*;
public class Gfs_php_converter {
public static byte[] Xto_php(Bry_bfr bfr, boolean escape_backslash, byte[] src) {
		int len = src.length;
		int pos = 0;
		boolean dirty = false;
		while (pos < len) {
			byte b = src[pos];
			switch (b) {
				case Byte_ascii.Tilde:
					if (!dirty) {
						bfr.Add_mid(src, 0, pos);
						dirty = true;
					}
					pos = Xto_php_swap(bfr, src, len, pos + 1);
					break;
				case Byte_ascii.Backslash: case Byte_ascii.Dollar:
				case Byte_ascii.Apos: case Byte_ascii.Quote:
					if (escape_backslash) {
						if (!dirty) {
							bfr.Add_mid(src, 0, pos);
							dirty = true;
						}
						bfr.Add_byte(Byte_ascii.Backslash);
						bfr.Add_byte(b);
					}
					else {
						if (dirty)
							bfr.Add_byte(b);
					}
					++pos;
					break;
				default:
					if (dirty)
						bfr.Add_byte(b);
					++pos;
					break;
			}
		}
		return dirty ? bfr.To_bry_and_clear() : src;
	}
	private static int Xto_php_swap(Bry_bfr bfr, byte[] src, int len, int pos) {
		if (pos >= len) throw Err_.new_wo_type("invalid gfs: tilde is last char", "src", String_.new_u8(src));
		byte b = src[pos];
		switch (b) {
			case Byte_ascii.Tilde: {	// ~~ -> ~
				bfr.Add_byte(Byte_ascii.Tilde);
				return pos + 1;
			}
			case Byte_ascii.Curly_bgn: {
				int num_bgn = pos + 1;
				int num_end = Bry_find_.Find_fwd_while_num(src, num_bgn, len);	// +1 to position after {
				if (   num_end == Bry_find_.Not_found
					|| num_end == len
					|| src[num_end] != Byte_ascii.Curly_end
					)
					throw Err_.new_wo_type("invalid gfs; num_end not found", "src", String_.new_u8(src));
				bfr.Add_byte(Byte_ascii.Dollar);
				int arg_idx = Bry_.To_int_or(src, num_bgn, num_end, -1);
				if (arg_idx == -1) {
					throw Err_.new_wo_type("invalid int");
				}
				bfr.Add_int_variable(arg_idx + 1);
				return num_end + 1;
			}
			default: {
				throw Err_.new_wo_type("invalid gfs; next char after tilde must be either tilde or open brace", "src", String_.new_u8(src));
			}
		}
	}
	public static byte[] To_gfs(Bry_bfr bfr, byte[] raw) {
		int raw_len = raw.length;
		for (int i = 0; i < raw_len; ++i) {
			byte b = raw[i];
			switch (b) {
				case Byte_ascii.Backslash:	// unescape; EX: '\"' -> '"'
					++i;
					if (i < raw_len){
						byte escape_byte = raw[i];
						switch (escape_byte) {	// REF: http://php.net/manual/en/language.types.String.php
							case Byte_ascii.Ltr_t:	escape_byte = Byte_ascii.Tab; break;
							case Byte_ascii.Ltr_n:	escape_byte = Byte_ascii.Nl; break;
							case Byte_ascii.Ltr_r:	escape_byte = Byte_ascii.Cr; break;
							case Byte_ascii.Ltr_v:	escape_byte = 11; break;	// 11=vertical tab
							case Byte_ascii.Ltr_e:	escape_byte = 27; break;	// 27=escape
							case Byte_ascii.Ltr_f:	escape_byte = 12; break;	// 12=form fed
							case Byte_ascii.Backslash:
							case Byte_ascii.Quote:
							case Byte_ascii.Apos:
							case Byte_ascii.Dollar:	break;
							// FUTURE:
							// //\[0-7]{1,3} 	the sequence of characters matching the regular expression is a character in octal notation, which silently overflows to fit in a byte (e.g. "\400" === "\000")
							// \ x[0-9A-Fa-f]{1,2} 	the sequence of characters matching the regular expression is a character in hexadecimal notation
							// \ u{[0-9A-Fa-f]+} 	the sequence of characters matching the regular expression is a Unicode codepoint, which will be output to the String as that codepoint's UTF-8 representation (added in PHP 7.0.0) 
							default:	// all else seems to be rendered literally; EX:"You do not need to put \ before a /."; PAGE:en.w:MediaWiki:Spam-whitelist; DATE:2016-09-12
								bfr.Add_byte(Byte_ascii.Backslash);
								bfr.Add_byte(escape_byte);
								continue;
						}
						bfr.Add_byte(escape_byte);
					}
					else	// if eos, just output "\"; don't fail; EX: "a\" -> "a\"
						bfr.Add_byte(Byte_ascii.Backslash);
					break;
				case Byte_ascii.Tilde:		// double up tilde; EX: '~' -> '~~'
					bfr.Add_byte_repeat(Bry_fmtr.char_escape, 2);	// escape tilde; EX: ~u -> ~~u; DATE:2013-11-11
					break;
				case Byte_ascii.Dollar:			// convert php args to gfs args; EX: $1 -> ~{0}
					int int_bgn = i + 1;
					int int_end = Php_text_itm_parser.Find_fwd_non_int(raw, int_bgn, raw_len);
					if (int_bgn == int_end )	// no numbers after $; EX: "$ "; "$a"
						bfr.Add_byte(b);
					else {
						int int_val = Bry_.To_int_or(raw, int_bgn, int_end, -1);
						if (int_val == -1) throw Err_.new_wo_type(String_.Format("unknown php dollar sequence: raw=~{0}", raw));
						bfr.Add_byte(Bry_fmtr.char_escape).Add_byte(Bry_fmtr.char_arg_bgn).Add_int_variable(int_val - List_adp_.Base1).Add_byte(Bry_fmtr.char_arg_end);	// convert "$1" -> "~{0}"
						i = int_end - 1;		// -1 b/c Find_fwd_non_int positions after non-int
					}							
					break;
				default:
					bfr.Add_byte(b);
					break;
			}
		}
		return bfr.To_bry_and_clear();
	}
}
