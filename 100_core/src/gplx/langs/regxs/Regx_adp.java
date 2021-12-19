/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.langs.regxs;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.utls.StringUtl;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Regx_adp {
	public Regx_adp(String regx, int flags) {
		this.flags = flags;
		Pattern_(regx);
	}
	public String Pattern() {return pattern;} public Regx_adp Pattern_(String val) {pattern = val; Under_sync(); return this;} private String pattern;
	public boolean Pattern_is_invalid() {return pattern_is_invalid;} private boolean pattern_is_invalid = false;
	public Exception Pattern_is_invalid_exception() {return pattern_is_invalid_exception;} private Exception pattern_is_invalid_exception = null;
	public Regx_match[] Match_all(String text, int bgn) {
		int idx = bgn;
		List_adp rv = List_adp_.New();
		int len = StringUtl.Len(text);
		while (idx <= len) {                // NOTE: must be <= not < else "a?" will return null instead of ""; PAGE:en.d:民; DATE:2015-01-30
			Regx_match match = this.Match(text, idx);
			if (match.Rslt_none()) break;
			rv.Add(match);
			int find_bgn = match.Find_bgn();
			int find_len = match.Find_len();
			idx = find_len == 0                // find_bgn == find_end
				? find_bgn + 1                // add 1 to resume search from next char; DATE:2014-09-02
				: find_bgn + find_len        // otherwise search after find_end
				;
		}
		return (Regx_match[])rv.ToAry(Regx_match.class);
	}
		private int flags = FLAG__DOTALL | FLAG__UNICODE_CHARACTER_CLASS;// JRE.7:UNICODE_CHARACTER_CLASS; added during %w fix for en.w:A#; DATE:2015-06-10
	private Pattern under;
	public Pattern Under() {return under;}
	private void Under_sync() {
		try {under = Pattern.compile(pattern, flags);}
		catch (Exception e) {    // NOTE: if invalid, then default to empty pattern (which should return nothing); EX:d:〆る generates [^]; DATE:2013-10-20
			pattern_is_invalid = true;
			pattern_is_invalid_exception = e;
			under = Pattern.compile("", flags);
		}
	}
	public Regx_match Match(String input, int bgn) {
		Matcher match = under.matcher(input);
		boolean success = match.find(bgn);
		int match_bgn = success ? match.start() : StringUtl.FindNone;
		int match_end = success ? match.end() : StringUtl.FindNone;
		Regx_group[] ary = Regx_group.Ary_empty;
		int groups_len = match.groupCount();
		if (success && groups_len > 0) {
			// NOTE: by convention, there are n groups, but groups.count is n - 1 and groups[0] is entire match (not 1st group); see TEST: DATE:2019-12-28
			groups_len++;
			ary = new Regx_group[groups_len];
			for (int i = 0; i < groups_len; i++) {
				int match_start = match.start(i);
				ary[i] = new Regx_group(match_start != -1, match_start, match.end(i), match.group(i));
			}
		}
		return new Regx_match(success, match_bgn, match_end, ary);
	}
	public String ReplaceAll(String input, String replace) {return under.matcher(input).replaceAll(replace);}
	// https://docs.oracle.com/javase/7/docs/api/java/util/regex/Pattern.html
	public static final int
		FLAG__NONE                    = 0
	, FLAG__UNIX_LINES              = Pattern.UNIX_LINES
	, FLAG__CASE_INSENSITIVE        = Pattern.CASE_INSENSITIVE
	, FLAG__COMMENTS                = Pattern.COMMENTS
	, FLAG__MULTILINE               = Pattern.MULTILINE
	, FLAG__LITERAL                 = Pattern.LITERAL
	, FLAG__DOTALL                  = Pattern.DOTALL
	, FLAG__UNICODE_CASE            = Pattern.UNICODE_CASE
	, FLAG__CANON_EQ                = Pattern.CANON_EQ
	, FLAG__UNICODE_CHARACTER_CLASS = Pattern.UNICODE_CHARACTER_CLASS
	;
	public static final int FLAG__DEFAULT = FLAG__DOTALL | FLAG__UNICODE_CHARACTER_CLASS;// JRE.7:UNICODE_CHARACTER_CLASS; added during %w fix for en.w:A#; DATE:2015-06-10
	}
