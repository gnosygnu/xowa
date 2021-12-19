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
package gplx.xowa.apps.gfs;
import gplx.types.basics.lists.List_adp_;
import gplx.langs.phps.Php_text_itm_parser;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.fmts.fmtrs.BryFmtr;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.types.basics.utls.StringUtl;
public class Gfs_php_converter {
public static byte[] Xto_php(BryWtr bfr, boolean escape_backslash, byte[] src) {
		int len = src.length;
		int pos = 0;
		boolean dirty = false;
		while (pos < len) {
			byte b = src[pos];
			switch (b) {
				case AsciiByte.Tilde:
					if (!dirty) {
						bfr.AddMid(src, 0, pos);
						dirty = true;
					}
					pos = Xto_php_swap(bfr, src, len, pos + 1);
					break;
				case AsciiByte.Backslash: case AsciiByte.Dollar:
				case AsciiByte.Apos: case AsciiByte.Quote:
					if (escape_backslash) {
						if (!dirty) {
							bfr.AddMid(src, 0, pos);
							dirty = true;
						}
						bfr.AddByte(AsciiByte.Backslash);
						bfr.AddByte(b);
					}
					else {
						if (dirty)
							bfr.AddByte(b);
					}
					++pos;
					break;
				default:
					if (dirty)
						bfr.AddByte(b);
					++pos;
					break;
			}
		}
		return dirty ? bfr.ToBryAndClear() : src;
	}
	private static int Xto_php_swap(BryWtr bfr, byte[] src, int len, int pos) {
		if (pos >= len) throw ErrUtl.NewArgs("invalid gfs: tilde is last char", "src", StringUtl.NewU8(src));
		byte b = src[pos];
		switch (b) {
			case AsciiByte.Tilde: {	// ~~ -> ~
				bfr.AddByte(AsciiByte.Tilde);
				return pos + 1;
			}
			case AsciiByte.CurlyBgn: {
				int num_bgn = pos + 1;
				int num_end = BryFind.FindFwdWhileNum(src, num_bgn, len);	// +1 to position after {
				if (   num_end == BryFind.NotFound
					|| num_end == len
					|| src[num_end] != AsciiByte.CurlyEnd
					)
					throw ErrUtl.NewArgs("invalid gfs; num_end not found", "src", StringUtl.NewU8(src));
				bfr.AddByte(AsciiByte.Dollar);
				int arg_idx = BryUtl.ToIntOr(src, num_bgn, num_end, -1);
				if (arg_idx == -1) {
					throw ErrUtl.NewArgs("invalid int");
				}
				bfr.AddIntVariable(arg_idx + 1);
				return num_end + 1;
			}
			default: {
				throw ErrUtl.NewArgs("invalid gfs; next char after tilde must be either tilde or open brace", "src", StringUtl.NewU8(src));
			}
		}
	}
	public static byte[] To_gfs(BryWtr bfr, byte[] raw) {
		int raw_len = raw.length;
		for (int i = 0; i < raw_len; ++i) {
			byte b = raw[i];
			switch (b) {
				case AsciiByte.Backslash:	// unescape; EX: '\"' -> '"'
					++i;
					if (i < raw_len){
						byte escape_byte = raw[i];
						switch (escape_byte) {	// REF: http://php.net/manual/en/language.types.String.php
							case AsciiByte.Ltr_t:	escape_byte = AsciiByte.Tab; break;
							case AsciiByte.Ltr_n:	escape_byte = AsciiByte.Nl; break;
							case AsciiByte.Ltr_r:	escape_byte = AsciiByte.Cr; break;
							case AsciiByte.Ltr_v:	escape_byte = 11; break;	// 11=vertical tab
							case AsciiByte.Ltr_e:	escape_byte = 27; break;	// 27=escape
							case AsciiByte.Ltr_f:	escape_byte = 12; break;	// 12=form fed
							case AsciiByte.Backslash:
							case AsciiByte.Quote:
							case AsciiByte.Apos:
							case AsciiByte.Dollar:	break;
							// FUTURE:
							// //\[0-7]{1,3} 	the sequence of characters matching the regular expression is a character in octal notation, which silently overflows to fit in a byte (e.g. "\400" === "\000")
							// \ x[0-9A-Fa-f]{1,2} 	the sequence of characters matching the regular expression is a character in hexadecimal notation
							// \ u{[0-9A-Fa-f]+} 	the sequence of characters matching the regular expression is a Unicode codepoint, which will be output to the String as that codepoint's UTF-8 representation (added in PHP 7.0.0) 
							default:	// all else seems to be rendered literally; EX:"You do not need to put \ before a /."; PAGE:en.w:MediaWiki:Spam-whitelist; DATE:2016-09-12
								bfr.AddByte(AsciiByte.Backslash);
								bfr.AddByte(escape_byte);
								continue;
						}
						bfr.AddByte(escape_byte);
					}
					else	// if eos, just output "\"; don't fail; EX: "a\" -> "a\"
						bfr.AddByte(AsciiByte.Backslash);
					break;
				case AsciiByte.Tilde:		// double up tilde; EX: '~' -> '~~'
					bfr.AddByteRepeat(BryFmtr.CharEscape, 2);	// escape tilde; EX: ~u -> ~~u; DATE:2013-11-11
					break;
				case AsciiByte.Dollar:			// convert php args to gfs args; EX: $1 -> ~{0}
					int int_bgn = i + 1;
					int int_end = Php_text_itm_parser.Find_fwd_non_int(raw, int_bgn, raw_len);
					if (int_bgn == int_end )	// no numbers after $; EX: "$ "; "$a"
						bfr.AddByte(b);
					else {
						int int_val = BryUtl.ToIntOr(raw, int_bgn, int_end, -1);
						if (int_val == -1) throw ErrUtl.NewArgs(StringUtl.Format("unknown php dollar sequence: raw=~{0}", raw));
						bfr.AddByte(BryFmtr.CharEscape).AddByte(BryFmtr.CharArgBgn).AddIntVariable(int_val - List_adp_.Base1).AddByte(BryFmtr.CharArgEnd);	// convert "$1" -> "~{0}"
						i = int_end - 1;		// -1 b/c Find_fwd_non_int positions after non-int
					}							
					break;
				default:
					bfr.AddByte(b);
					break;
			}
		}
		return bfr.ToBryAndClear();
	}
}
