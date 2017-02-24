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
package gplx.xowa.mediawiki.includes.libs; import gplx.*; import gplx.xowa.*; import gplx.xowa.mediawiki.*; import gplx.xowa.mediawiki.includes.*;
import gplx.core.btries.*;
import gplx.xowa.mediawiki.includes.libs.replacers.*;
/**
* A collection of static methods to play with strings.
*/
public class XomwStringUtils {
//		/**
//		* Test whether a String is valid UTF-8.
//		*
//		* The function check for invalid byte sequences, overlong encoding but
//		* not for different normalisations.
//		*
//		* @note In MediaWiki 1.21, this function did not provide proper UTF-8 validation.
//		* In particular, the pure PHP code path did not in fact check for overlong forms.
//		* Beware of this when backporting code to that version of MediaWiki.
//		*
//		* @since 1.21
//		* @param String $value String to check
//		* @return boolean Whether the given $value is a valid UTF-8 encoded String
//		*/
//		static function isUtf8($value) {
//			$value = (String)$value;
//
//			// HHVM 3.4 and older come with an outdated version of libmbfl that
//			// incorrectly allows values above U+10FFFF, so we have to check
//			// for them separately. (This issue also exists in PHP 5.3 and
//			// older, which are no longer supported.)
//			static $newPHP;
//			if ($newPHP === null) {
//				$newPHP = !mb_check_encoding("\xf4\x90\x80\x80", 'UTF-8');
//			}
//
//			return mb_check_encoding($value, 'UTF-8') &&
//				($newPHP || preg_match("/\xf4[\x90-\xbf]|[\xf5-\xff]/S", $value) === 0);
//		}

	private static final byte DELIMITER_EXPLODE__SEP = 0, DELIMITER_EXPLODE__BGN = 1, DELIMITER_EXPLODE__END = 2;
	private static final    Btrie_slim_mgr delimiter_explode_trie = Btrie_slim_mgr.cs()
		.Add_str_byte("|" , DELIMITER_EXPLODE__SEP)
		.Add_str_byte("-{", DELIMITER_EXPLODE__BGN)
		.Add_str_byte("}-", DELIMITER_EXPLODE__END)
		;
	/**
	* Explode a String, but ignore any instances of the separator inside
	* the given start and end delimiters, which may optionally nest.
	* The delimiters are literal strings, not regular expressions.
	* @param String $startDelim Start delimiter
	* @param String $endDelim End delimiter
	* @param String $separator Separator String for the explode.
	* @param String $subject Subject String to explode.
	* @param boolean $nested True iff the delimiters are allowed to nest.
	* @return ArrayIterator
	*/
	// XO.MW: NOTE: function only used in two places; hard-coding (a) nested=true; (b) bgn="-{" end="}-" sep="|"
	public static byte[][] delimiterExplode(List_adp tmp, Btrie_rv trv, byte[] src) {
		// XO.MW.PORTED:entire proc rewritten; see PHP for source
		int src_bgn = 0;
		int src_end = src.length;

		int depth = 0;
		int cur = src_bgn;
		int prv = cur;
		while (true) {
			// eos
			if (cur == src_end) {
				// add rest
				tmp.Add(Bry_.Mid(src, prv, src_end));
				break;
			}

			Object o = delimiter_explode_trie.Match_at(trv, src, cur, src_end);

			// regular char; continue;
			if (o == null) {
				cur++;
				continue;
			}

			// handle sep, bgn, end
			byte tid = ((gplx.core.primitives.Byte_obj_val)o).Val();
			switch (tid) {
				case DELIMITER_EXPLODE__SEP:
					if (depth == 0) {
						tmp.Add(Bry_.Mid(src, prv, cur));
						prv = cur + 1;
					}
					break;
				case DELIMITER_EXPLODE__BGN:
					depth++;
					break;
				case DELIMITER_EXPLODE__END:
					depth--;
					break;
			}
			cur = trv.Pos();
		}
		return (byte[][])tmp.To_ary_and_clear(byte[].class);
	}

//		/**
//		* Perform an operation equivalent to `preg_replace()`
//		*
//		* Matches this code:
//		*
//		*     preg_replace("!$startDelim(.*?)$endDelim!", $replace, $subject);
//		*
//		* ..except that it's worst-case O(N) instead of O(N^2). Compared to delimiterReplace(), this
//		* implementation is fast but memory-hungry and inflexible. The memory requirements are such
//		* that I don't recommend using it on anything but guaranteed small chunks of text.
//		*
//		* @param String $startDelim
//		* @param String $endDelim
//		* @param String $replace
//		* @param String $subject
//		* @return String
//		*/
//		static function hungryDelimiterReplace($startDelim, $endDelim, $replace, $subject) {
//			$segments = explode($startDelim, $subject);
//			$output = array_shift($segments);
//			foreach ($segments as $s) {
//				$endDelimPos = strpos($s, $endDelim);
//				if ($endDelimPos === false) {
//					$output .= $startDelim . $s;
//				} else {
//					$output .= $replace . substr($s, $endDelimPos + strlen($endDelim));
//				}
//			}
//
//			return $output;
//		}

	/**
	* Perform an operation equivalent to `preg_replace_callback()`
	*
	* Matches this code:
	*
	*     preg_replace_callback("!$startDelim(.*)$endDelim!s$flags", $callback, $subject);
	*
	* If the start delimiter ends with an initial substring of the end delimiter,
	* e.g. in the case of C-style comments, the behavior differs from the model
	* regex. In this implementation, the end must share no characters with the
	* start, so e.g. `/*\/` is not considered to be both the start and end of a
	* comment. `/*\/xy/*\/` is considered to be a single comment with contents `/xy/`.
	*
	* The implementation of delimiterReplaceCallback() is slower than hungryDelimiterReplace()
	* but uses far less memory. The delimiters are literal strings, not regular expressions.
	*
	* @param String $startDelim Start delimiter
	* @param String $endDelim End delimiter
	* @param callable $callback Function to call on each match
	* @param String $subject
	* @param String $flags Regular expression flags
	* @throws InvalidArgumentException
	* @return String
	*/
	// XO.MW:flags not supported; goes directly to regex; also, flags of "i" will do case-insensitive
	public static void delimiterReplaceCallback(Bry_bfr bfr, byte[] bgn, byte[] end, XomwReplacer callback,
		byte[] src
	) {
		/* XO.MW.PORTED:
			MW does following logic
			* Run start/end regex on subject till no matches
			* If start/end found, evaluate possible match (handling nesting)
			* If match found, then pass find-replace pair to callback;
			    find=substr(subject, outputPos, tokenOffset + tokenLength - outputPos)
				replace=substr(subject, contentPos, tokenOffset - contentPos)				
			* Also, unnecessary "overlapping" logic: bgn=ab;end=abc
				$strcmp( $endDelim, substr( $subject, $tokenOffset, $endLength ) ) == 0
		*/
		int pos = 0;
		int prv = 0;
		int srcLen = src.length;
		int bgnLen = bgn.length;
		int endLen = end.length;
		boolean foundStart = false;
		boolean tokenTypeIsStart = false;

		while (true) {
			if (pos >= srcLen) {
				bfr.Add_mid(src, prv, srcLen);
				break;
			}
			if      (Bry_.Eq(src, pos, pos + bgnLen, bgn)) {
				tokenTypeIsStart = true;
			}
			else if (Bry_.Eq(src, pos, pos + endLen, end)) {
				tokenTypeIsStart = false;
			}
			else {
				pos++;
				continue;
			}

			if (tokenTypeIsStart) {
				// Only move the start position if we haven't already found a start
				// This means that START START END matches outer pair
				// EX: "(a(b)" has match of "a(b"
				if (!foundStart) {
					// Found start
					// Write out the non-matching section
					bfr.Add_mid(src, prv, pos);
					pos += bgnLen;
					prv = pos;
					foundStart = true;
				} else {
					// Move the input position past the *first character* of START,
					// to protect against missing END when it overlaps with START
					pos++;
				}
			} else { // elseif (tokenType == 'end')
				if (foundStart) {
					// Found match
					callback.cb(bfr, src, prv, pos);
					foundStart = false;
				} else {
					// Non-matching end, write it out
					// EX: "a)b" -> "a)"
					bfr.Add_mid(src, prv, pos + endLen);
				}
				pos += endLen;
				prv = pos;
			}
		}
	}

	/**
	* Perform an operation equivalent to `preg_replace()` with flags.
	*
	* Matches this code:
	*
	*     preg_replace("!$startDelim(.*)$endDelim!$flags", $replace, $subject);
	*
	* @param String $startDelim Start delimiter regular expression
	* @param String $endDelim End delimiter regular expression
	* @param String $replace Replacement String. May contain $1, which will be
	*  replaced by the text between the delimiters
	* @param String $subject String to search
	* @param String $flags Regular expression flags
	* @return String The String with the matches replaced
	*/
	// XO.MW:removed flags=''
	public static void delimiterReplace(Bry_bfr bfr, byte[] startDelim, byte[] endDelim, byte[] replace, byte[] subject) {
		XomwRegexlikeReplacer replacer = new XomwRegexlikeReplacer(replace);

		delimiterReplaceCallback(bfr, startDelim, endDelim, replacer, subject);
	}

//		/**
//		* More or less "markup-safe" explode()
//		* Ignores any instances of the separator inside `<...>`
//		* @param String $separator
//		* @param String $text
//		* @return array
//		*/
//		static function explodeMarkup($separator, $text) {
//			$placeholder = "\x00";
//
//			// Remove placeholder instances
//			$text = str_replace($placeholder, '', $text);
//
//			// Replace instances of the separator inside HTML-like tags with the placeholder
//			$replacer = new DoubleReplacer($separator, $placeholder);
//			$cleaned = StringUtils::delimiterReplaceCallback('<', '>', $replacer->cb(), $text);
//
//			// Explode, then put the replaced separators back in
//			$items = explode($separator, $cleaned);
//			foreach ($items as $i => $str) {
//				$items[$i] = str_replace($placeholder, $separator, $str);
//			}
//
//			return $items;
//		}

	/**
	* More or less "markup-safe" str_replace()
	* Ignores any instances of the separator inside `<...>`
	* @param String $search
	* @param String $replace
	* @param String $text
	* @return String
	*/
	public static void replaceMarkup(byte[] src, int src_bgn, int src_end, byte[] find, byte[] repl) {	// REF:/includes/libs/StringUtils.php|replaceMarkup
		// XO.MW.PORTED: avoiding multiple regex calls / String creations
		// $placeholder = "\x00";
		//
		// Remove placeholder instances
		// $text = str_replace($placeholder, '', $text);
		//
		// Replace instances of the separator inside HTML-like tags with the placeholder
		// $replacer = new DoubleReplacer($search, $placeholder);
		// $cleaned = StringUtils::delimiterReplaceCallback('<', '>', $replacer->cb(), $text);
		//
		// Explode, then put the replaced separators back in
		// $cleaned = str_replace($search, $replace, $cleaned);
		// $text = str_replace($placeholder, $search, $cleaned);

		// if same length find / repl, do in-place replacement; EX: "!!"  -> "||"
		int find_len = find.length;
		int repl_len = repl.length;
		if (find_len != repl_len) throw Err_.new_wo_type("find and repl should be same length");

		byte find_0 = find[0];
		byte dlm_bgn = Byte_ascii.Angle_bgn;
		byte dlm_end = Byte_ascii.Angle_end;
		boolean repl_active = true;

		// loop every char in array
		for (int i = src_bgn; i < src_end; i++) {
			byte b = src[i];
			if (  b == find_0
				&& Bry_.Match(src, i + 1, i + find_len, find, 1, find_len)
				&& repl_active
				) {
				Bry_.Set(src, i, i + find_len, repl);
			}
			else if (b == dlm_bgn) {
				repl_active = false;
			}
			else if (b == dlm_end) {
				repl_active = true;
			}
		}
	}

//		/**
//		* Escape a String to make it suitable for inclusion in a preg_replace()
//		* replacement parameter.
//		*
//		* @param String $String
//		* @return String
//		*/
//		static function escapeRegexReplacement($String) {
//			$String = str_replace('\\', '\\\\', $String);
//			$String = str_replace('$', '\\$', $String);
//			return $String;
//		}
//
//		/**
//		* Workalike for explode() with limited memory usage.
//		*
//		* @param String $separator
//		* @param String $subject
//		* @return ArrayIterator|ExplodeIterator
//		*/
//		static function explode($separator, $subject) {
//			if (substr_count($subject, $separator) > 1000) {
//				return new ExplodeIterator($separator, $subject);
//			} else {
//				return new ArrayIterator(explode($separator, $subject));
//			}
//		}
}
