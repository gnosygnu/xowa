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
package gplx.xowa.mediawiki; import gplx.*; import gplx.xowa.*;
import gplx.langs.regxs.*;
import gplx.core.strings.*; import gplx.core.primitives.*; import gplx.core.bits.*;
public class XophpRegex_ {
	// REF.PHP: https://www.php.net/manual/en/function.preg-quote.php
	// The special regular expression characters are:
	private static final Hash_adp preg_quote_hash = Hash_adp_.New().Add_many_as_key_and_val
		('.', '\\', '+', '*', '?', '[', '^', ']', '$', '(', ')', '{', '}', '=', '!', '<', '>', '|', ':', '-', '#');
	public static String preg_quote(String str, String delimiter) {// NOTE: "String delimiter" not used b/c Java / XO does not allow symbolic quotes; EX: "/abc/i"
		String_bldr sb = String_bldr_.new_();
		int len = String_.Len(str);
		for (int i = 0; i < len; i++) {
			char c = String_.CharAt(str, i);
			if (preg_quote_hash.Has(c)) {
				sb.Add("\\");
			}
			sb.Add(c);
		}
		return sb.To_str_and_clear();
	}
	public static boolean preg_match_bool(Regx_adp pattern, int modifier, String subject) {return preg_match_bool(pattern, modifier, subject, null, 0, 0);}
	public static boolean preg_match_bool(Regx_adp pattern, String subject, XophpArray matches, int flags, int offset) {return preg_match(pattern, MODIFIER_NONE, subject, matches, flags, offset) == FOUND;}
	public static boolean preg_match_bool(String pattern, int modifier, String subject, XophpArray matches, int flags, int offset) {return preg_match(Regx_adp_.new_(pattern), modifier, subject, matches, flags, offset) == FOUND;}
	public static boolean preg_match_bool(Regx_adp pattern, int modifier, String subject, XophpArray matches, int flags, int offset) {return preg_match(pattern, modifier, subject, matches, flags, offset) == FOUND;}
	public static int preg_match(Regx_adp pattern, String subject) {return preg_match(pattern, MODIFIER_NONE, subject, null, 0, 0);}
	public static int preg_match(Regx_adp pattern, int modifier, String subject) {return preg_match(pattern, modifier, subject, null, 0, 0);}
	// REF.PHP: https://www.php.net/manual/en/function.preg-match.php
	public static int preg_match(Regx_adp pattern, int modifier, String subject, XophpArray matches, int flags, int offset) {
		// handle offset
		int subject_len = String_.Len(subject);
		if (offset >= subject_len || offset < 0) return PREG_ERR;

		// exec match
		// FUTURE: offset is in bytes, whereas subject will be in chars
		Regx_match match = pattern.Match(subject, offset);

		// update vars if something found
		int rv = NOT_FOUND;
		if (match.Rslt()) {
			rv = FOUND;
			int find_bgn = match.Find_bgn();
			String match_str = String_.Mid(subject, find_bgn, match.Find_end());
			Regx_group[] grps = match.Groups();
			int grps_len = grps.length;

			// handle grps
			if (matches != null) {
				if (grps_len == 0) {
					if (flags == PREG_OFFSET_CAPTURE) {
						matches.Add(XophpArray.New(match_str, find_bgn));
					}
					else {
						matches.Add(match_str);
					}
				}
				else {
					preg_match_fill(subject, matches, flags, match, match_str, grps, grps_len);
				}
			}
		}
		return rv;
	}
	private static void preg_match_fill(String subject, XophpArray matches, int flags, Regx_match match, String match_str, Regx_group[] grps, int grps_len) {
		for (int i = 0; i < grps_len; i++) {
			Regx_group grp = grps[i];
			// TOMBSTONE: if (!grp.Rslt()) continue; // ignore non matches in group; EX: "1" and "^-?(([0-9]+)(?:\\.([0-9]+))?)" returns a match=false for group(2)
			String grp_match = grp.Rslt() ? grp.Val() : "";
			if (flags == PREG_OFFSET_CAPTURE) {
				matches.Add(XophpArray.New(grp_match, grp.Bgn()));
			}
			else {
				matches.Add(grp_match);
			}
		}
	}
	// REF.PHP:https://www.php.net/manual/en/function.preg-match-all.php
	// $flags = PREG_PATTERN_ORDER
	public static int preg_match_all(Regx_adp pattern, String subject, XophpArray matches, int flags) {return preg_match_all(pattern, subject, matches, flags, 0);}
	public static int preg_match_all(Regx_adp pattern, String subject, XophpArray matches, int flags, int offset) {
		// decompose flags to bools
		// boolean unmatched_as_null = Bitmask_.Has_int(flags, PREG_OFFSET_CAPTURE);
		boolean offset_capture = Bitmask_.Has_int(flags, PREG_OFFSET_CAPTURE);
		boolean pattern_order = Bitmask_.Has_int(flags, PREG_PATTERN_ORDER);
		boolean set_order = Bitmask_.Has_int(flags, PREG_SET_ORDER);

		if (pattern_order && set_order) { // PHP.TEST:echo(preg_match_all("|a|U", "a", $out, PREG_SET_ORDER | PREG_PATTERN_ORDER));
			matches.Clear();
			return 0;
		}
//			else if (!pattern_order && !set_order) { // occurs when passing just PREG_OFFSET_CAPTURE
//				set_order = true;
//			}

		// ARRAY
		XophpArray array_0 = null;
		XophpArray array_1 = null;
		boolean match_is_full = true;
		if (pattern_order) {
			array_0 = XophpArray.New();
			array_1 = XophpArray.New();
			matches.Add(array_0);
			matches.Add(array_1);
		}
		int len = String_.Len(subject);
		int count = 0;
		while (offset < len) {
			Regx_match match = pattern.Match(subject, offset);
			if (!match.Rslt())
				break;

			Regx_group[] groups = match.Groups();
			int groups_len = groups.length;
			XophpArray array = null;
			if (set_order) {
				array = XophpArray.New();
				matches.Add(array);
			}
			for (int i = 0; i < groups_len; i++) {
				Regx_group group = groups[i];
				if (pattern_order) {
					array = match_is_full ? array_0 : array_1;
					match_is_full = !match_is_full;
				}
				if (offset_capture) {
					matches.Add(XophpArray.New(group.Val(), group.Bgn()));
				}
				else {
					array.Add(group.Val());
				}
			}

			offset = match.Find_end();
			count++;
		}
		return count;
	}

	// REF.PHP:https://www.php.net/manual/en/function.preg-replace.php
	public static final int preg_replace_limit_none = -1;
	public static String preg_replace(Regx_adp pattern, String replacement, String subject) {return preg_replace(pattern, replacement, subject, -1, null);}
	public static String preg_replace(Regx_adp pattern, String replacement, String subject, int limit, Int_obj_ref count_rslt) {
		// if no limit specified, default to max
		if (limit == preg_replace_limit_none) limit = Int_.Max_value;

		// init vars for loop
		int pos = 0;
		int count = 0;
		String_bldr sb = null;

		// exec match
		for (int i = 0; i < limit; i++) {
			// find next
			Regx_match match = pattern.Match(subject, pos);

			// found nothing; stop
			if (!match.Rslt()) {
				if (count == 0)
					return subject; // optimized case if no matches
				else
					break;
			}

			// found something
			if (sb == null) {sb = String_bldr_.new_();} // lazy-make sb

			// add everything up to match
			sb.Add_mid(subject, pos, match.Find_bgn());

			// add repl
			sb.Add(replacement);

			// update counters
			pos = match.Find_end();
			count++;
		}

		// add rest of String
		sb.Add_mid(subject, pos, String_.Len(subject));

		// update count_rslt if set
		if (count_rslt != null) count_rslt.Val_(count);

		// return
		return sb.To_str_and_clear();
	}
	// REF.PHP:https://www.php.net/manual/en/pcre.constants.php
	// REF.PHP:https://github.com/php/php-src/blob/master/ext/pcre/php_pcre.c
	public static final int
	  PREG_NO_FLAG              = Int_.Min_value
	, PREG_ERR                  = -1

	, PREG_PATTERN_ORDER        = 1
	, PREG_SET_ORDER            = 2
	, PREG_OFFSET_CAPTURE       = 1<<8
	, PREG_UNMATCHED_AS_NULL    = 1<<9

//		, PREG_SPLIT_NO_EMPTY       = 1<<0
//		, PREG_SPLIT_DELIM_CAPTURE  = 1<<1
//		, PREG_SPLIT_OFFSET_CAPTURE = 1<<2

//		, PREG_REPLACE_EVAL         = 1<<0
//
//		, PREG_GREP_INVERT          = 1<<0
//
//		, PREG_JIT                  = 1<<3
	;

	public static Regx_adp Pattern(String pattern) {return new Regx_adp(pattern, Regx_adp.FLAG__NONE);}
	public static Regx_adp Pattern(String pattern, int modifier) {
		int flags = Regx_adp.FLAG__NONE;
		if (Bitmask_.Has_int(modifier, MODIFIER_i))
			flags = Bitmask_.Set_or_add(flags, Regx_adp.FLAG__CASE_INSENSITIVE);
		if (Bitmask_.Has_int(modifier, MODIFIER_m))
			flags = Bitmask_.Set_or_add(flags, Regx_adp.FLAG__MULTILINE);
		if (Bitmask_.Has_int(modifier, MODIFIER_s))
			flags = Bitmask_.Set_or_add(flags, Regx_adp.FLAG__DOTALL);
		if (Bitmask_.Has_int(modifier, MODIFIER_x))
			flags = Bitmask_.Set_or_add(flags, Regx_adp.FLAG__COMMENTS);
		if (Bitmask_.Has_int(modifier, MODIFIER_U)) {
			pattern = String_.Replace(pattern, ".*", ".*?");
		}
		return new Regx_adp(pattern, flags);
	}

	public static final int NOT_FOUND = 0, FOUND = 1;

	// REF.PHP:https://www.php.net/manual/en/reference.pcre.pattern.modifiers.php
	// Some modifiers can be set using "(?LETTER)"; EX: "(?J)"; REF.PHP:https://www.php.net/manual/en/regexp.reference.@gplx.Internal protected-options.php
	// https://stackoverflow.com/questions/5767627/how-to-add-features-missing-from-the-java-regex-implementation/5771326#5771326
	public static final int
	  MODIFIER_NONE = 0
	, MODIFIER_i    = Math_.Pow_int(2,  0) // PCRE_CASELESS: If this modifier is set, letters in the pattern match both upper and lower case letters.
	, MODIFIER_m    = Math_.Pow_int(2,  1) // PCRE_MULTILINE: By default, PCRE treats the subject String as consisting of a single "line" of characters (even if it actually contains several newlines). The "start of line" metacharacter (^) matches only at the start of the String, while the "end of line" metacharacter ($) matches only at the end of the String, or before a terminating newline (unless D modifier is set). This is the same as Perl. When this modifier is set, the "start of line" and "end of line" constructs match immediately following or immediately before any newline in the subject String, respectively, as well as at the very start and end. This is equivalent to Perl's /m modifier. If there are no "\n" characters in a subject String, or no occurrences of ^ or $ in a pattern, setting this modifier has no effect.
	, MODIFIER_s    = Math_.Pow_int(2,  2) // PCRE_DOTALL: If this modifier is set, a dot metacharacter in the pattern matches all characters, including newlines. Without it, newlines are excluded. This modifier is equivalent to Perl's /s modifier. A negative class such as [^a] always matches a newline character, independent of the setting of this modifier.
	, MODIFIER_x    = Math_.Pow_int(2,  3) // PCRE_EXTENDED: If this modifier is set, whitespace data characters in the pattern are totally ignored except when escaped or inside a character class, and characters between an unescaped # outside a character class and the next newline character, inclusive, are also ignored. This is equivalent to Perl's /x modifier, and makes it possible to include commentary inside complicated patterns. Note, however, that this applies only to data characters. Whitespace characters may never appear within special character sequences in a pattern, for example within the sequence (?( which introduces a conditional subpattern.
	, MODIFIER_e    = Math_.Pow_int(2,  4) // PREG_REPLACE_EVAL: If this deprecated modifier is set, preg_replace() does normal substitution of backreferences in the replacement String, evaluates it as PHP code, and uses the result for replacing the search String. Single quotes, double quotes, backslashes (\) and NULL chars will be escaped by backslashes in substituted backreferences.
	, MODIFIER_A    = Math_.Pow_int(2,  5) // PREG_ANCHORED: If this modifier is set, the pattern is forced to be "anchored", that is, it is constrained to match only at the start of the String which is being searched (the "subject String"). This effect can also be achieved by appropriate constructs in the pattern itself, which is the only way to do it in Perl.
	, MODIFIER_D    = Math_.Pow_int(2,  6) // PCRE_DOLLAR_ENDONLY: If this modifier is set, a dollar metacharacter in the pattern matches only at the end of the subject String. Without this modifier, a dollar also matches immediately before the final character if it is a newline (but not before any other newlines). This modifier is ignored if m modifier is set. There is no equivalent to this modifier in Perl.
	, MODIFIER_S    = Math_.Pow_int(2,  7) // PCRE_STUDY: When a pattern is going to be used several times, it is worth spending more time analyzing it in order to speed up the time taken for matching. If this modifier is set, then this extra analysis is performed. At present, studying a pattern is useful only for non-anchored patterns that do not have a single fixed starting character.
	, MODIFIER_U    = Math_.Pow_int(2,  8) // PCRE_UNGREEDY: This modifier inverts the "greediness" of the quantifiers so that they are not greedy by default, but become greedy if followed by ?. It is not compatible with Perl. It can also be set by a (?U) modifier setting within the pattern or by a question mark behind a quantifier (e.g. .*?).
	, MODIFIER_X    = Math_.Pow_int(2,  9) // PCRE_EXTRA: This modifier turns on additional functionality of PCRE that is incompatible with Perl. Any backslash in a pattern that is followed by a letter that has no special meaning causes an error, thus reserving these combinations for future expansion. By default, as in Perl, a backslash followed by a letter with no special meaning is treated as a literal. There are at present no other features controlled by this modifier.
	, MODIFIER_J    = Math_.Pow_int(2, 10) // PCRE_INFO_JCHANGED: The (?J) @gplx.Internal protected option setting changes the local PCRE_DUPNAMES option. Allow duplicate names for subpatterns. As of PHP 7.2.0 J is supported as modifier as well.
	, MODIFIER_u    = Math_.Pow_int(2, 11) // PCRE_UTF8: This modifier turns on additional functionality of PCRE that is incompatible with Perl. Pattern and subject strings are treated as UTF-8. An invalid subject will cause the preg_* function to match nothing; an invalid pattern will trigger an error of level E_WARNING. Five and six octet UTF-8 sequences are regarded as invalid since PHP 5.3.4 (resp. PCRE 7.3 2007-08-28); formerly those have been regarded as valid UTF-8.
	;
}
