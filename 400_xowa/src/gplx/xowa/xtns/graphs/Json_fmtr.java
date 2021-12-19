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
package gplx.xowa.xtns.graphs;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.basics.constants.AsciiByte;
public class Json_fmtr {
	public static byte[] clean(BryWtr tmp_bfr, byte[] json) {
		int maxLen = json.length;
		int mark = 0;
		boolean inString = false;
		boolean inComment = false;
		boolean multiline = false;

		for (int idx = 0; idx < maxLen; idx++) {
			switch (json[idx]) {
				case AsciiByte.Quote: {
					byte lookBehind = (idx - 1 >= 0) ? json[idx - 1] : AsciiByte.Null;
					if (!inComment && lookBehind != AsciiByte.Backslash) {
						// Either started or ended a String
						inString = !inString;
					}
					break;
				}
				case AsciiByte.Slash: {
					byte lookAhead = (idx + 1 < maxLen) ? json[idx + 1] : AsciiByte.Null;
					byte lookBehind = (idx - 1 >= 0) ? json[idx - 1] : AsciiByte.Null;
					if (inString) {
						continue;
					}
					else if (	!inComment 
							&&	(lookAhead == AsciiByte.Slash || lookAhead == AsciiByte.Star)
					) {
						// Transition into a comment
						// Add characters seen to buffer
						tmp_bfr.AddMid(json, mark, idx);
						// Consume the look ahead character
						idx++;
						// Track state
						inComment = true;
						multiline = lookAhead == AsciiByte.Star;
					} else if (multiline && lookBehind == AsciiByte.Star) {
						// Found the end of the current comment
						mark = idx + 1;
						inComment = false;
						multiline = false;
					}
					break;
				}
				case AsciiByte.Nl:
					if (inComment && !multiline) {
						// Found the end of the current comment
						mark = idx + 1;
						inComment = false;
					}
					break;
				case AsciiByte.Comma: {  // remove trailing commas of the form {a,}; note that FormatJson.php does this in a separate regex call; '/,([ \t]*[}\]][^"\r\n]*([\r\n]|$)|[ \t]*[\r\n][ \t\r\n]*[}\]])/'
					if (inComment || inString) continue;

					int peek_next = BryFind.FindFwdWhileWs(json, idx + 1, maxLen);
					if (peek_next != maxLen 
						&&	(	json[peek_next] == AsciiByte.BrackEnd
							||	json[peek_next] == AsciiByte.CurlyEnd)
							) {
						// Add characters seen to buffer
						tmp_bfr.AddMid(json, mark, idx);
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
		tmp_bfr.AddMid(json, mark, maxLen);
		return tmp_bfr.ToBryAndClear();
	}
}

