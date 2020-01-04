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
public class XophpRegex_ {
	public static boolean preg_match_bool(Regx_adp pattern, int modifier, String subject) {return preg_match_bool(pattern, modifier, subject, null, 0, 0);}
	public static boolean preg_match_bool(Regx_adp pattern, String subject, XophpArray matches, int flags, int offset) {return preg_match(pattern, MODIFIER_NONE, subject, matches, flags, offset) == FOUND;}
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
			if (!grp.Rslt()) continue; // ignore non matches in group; EX: "1" and "^-?(([0-9]+)(?:\\.([0-9]+))?)" returns a match=false for group(2)
			String grp_match = grp.Val();
			if (flags == PREG_OFFSET_CAPTURE) {
				matches.Add(XophpArray.New(grp_match, grp.Bgn()));
			}
			else {
				matches.Add(grp_match);
			}
		}
	}
	// REF.PHP:https://www.php.net/manual/en/pcre.constants.php
	public static final int
	  PREG_OFFSET_CAPTURE = 256
	, PREG_UNMATCHED_AS_NULL = 0
	, PREG_NO_FLAG = Int_.Min_value
	, PREG_ERR = XophpInt_.False
	;

	public static final int NOT_FOUND = 0, FOUND = 1;

	// REF.PHP:https://www.php.net/manual/en/reference.pcre.pattern.modifiers.php
	public static final    int
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
