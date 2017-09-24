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
package gplx.xowa.xtns.graphs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
public class Json_fmtr {
	public static byte[] clean(Bry_bfr tmp_bfr, byte[] json) {
		int maxLen = json.length;
		int mark = 0;
		boolean inString = false;
		boolean inComment = false;
		boolean multiline = false;

		for (int idx = 0; idx < maxLen; idx++) {
			switch (json[idx]) {
				case Byte_ascii.Quote: {
					byte lookBehind = (idx - 1 >= 0) ? json[idx - 1] : Byte_ascii.Null;
					if (!inComment && lookBehind != Byte_ascii.Backslash) {
						// Either started or ended a String
						inString = !inString;
					}
					break;
				}
				case Byte_ascii.Slash: {
					byte lookAhead = (idx + 1 < maxLen) ? json[idx + 1] : Byte_ascii.Null;
					byte lookBehind = (idx - 1 >= 0) ? json[idx - 1] : Byte_ascii.Null;
					if (inString) {
						continue;
					}
					else if (	!inComment 
							&&	(lookAhead == Byte_ascii.Slash || lookAhead == Byte_ascii.Star)
					) {
						// Transition into a comment
						// Add characters seen to buffer
						tmp_bfr.Add_mid(json, mark, idx);
						// Consume the look ahead character
						idx++;
						// Track state
						inComment = true;
						multiline = lookAhead == Byte_ascii.Star;
					} else if (multiline && lookBehind == Byte_ascii.Star) {
						// Found the end of the current comment
						mark = idx + 1;
						inComment = false;
						multiline = false;
					}
					break;
				}
				case Byte_ascii.Nl:
					if (inComment && !multiline) {
						// Found the end of the current comment
						mark = idx + 1;
						inComment = false;
					}
					break;
				case Byte_ascii.Comma: {  // remove trailing commas of the form {a,}; note that FormatJson.php does this in a separate regex call; '/,([ \t]*[}\]][^"\r\n]*([\r\n]|$)|[ \t]*[\r\n][ \t\r\n]*[}\]])/'
					if (inComment || inString) continue;

					int peek_next = Bry_find_.Find_fwd_while_ws(json, idx + 1, maxLen);
					if (peek_next != maxLen 
						&&	(	json[peek_next] == Byte_ascii.Brack_end 
							||	json[peek_next] == Byte_ascii.Curly_end)
							) {
						// Add characters seen to buffer
						tmp_bfr.Add_mid(json, mark, idx);
						// position after comma
						mark = idx + 1;
					}
					break;
				}
			}
		}

		if (inComment) {
			// Comment ends with input
			// Technically we should check to ensure that we aren't in
			// a multiline comment that hasn't been properly ended, but this
			// is a strip filter, not a validating parser.
			mark = maxLen;
		}

		// Add final chunk to buffer before returning
		tmp_bfr.Add_mid(json, mark, maxLen);
		return tmp_bfr.To_bry_and_clear();
	}
}

