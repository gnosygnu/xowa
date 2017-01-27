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
package gplx.langs.regxs; import gplx.*; import gplx.langs.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class Regx_adp {
	@gplx.Internal protected Regx_adp(String regx) {Pattern_(regx);}
	public String Pattern() {return pattern;} public Regx_adp Pattern_(String val) {pattern = val; Under_sync(); return this;} private String pattern;
	public boolean Pattern_is_invalid() {return pattern_is_invalid;} private boolean pattern_is_invalid = false;
	public Regx_match[] Match_all(String text, int bgn) {
		int idx = bgn;
		List_adp rv = List_adp_.New();
		int len = String_.Len(text);
		while (idx <= len) {				// NOTE: must be <= not < else "a?" will return null instead of ""; PAGE:en.d:民; DATE:2015-01-30
			Regx_match match = this.Match(text, idx);
			if (match.Rslt_none()) break;
			rv.Add(match);
			int find_bgn = match.Find_bgn();
			int find_len = match.Find_len();
			idx = find_len == 0				// find_bgn == find_end
				? find_bgn + 1				// add 1 to resume search from next char; DATE:2014-09-02
				: find_bgn + find_len		// otherwise search after find_end
				;
		}
		return (Regx_match[])rv.To_ary(Regx_match.class);
	}
		private Pattern under;
	public Pattern Under() {return under;}
	void Under_sync() {
		try {under = Pattern.compile(pattern, Pattern.DOTALL | Pattern.UNICODE_CHARACTER_CLASS);}	// JRE.7:UNICODE_CHARACTER_CLASS; added during %w fix for en.w:A#; DATE:2015-06-10 
		catch (Exception e) {	// NOTE: if invalid, then default to empty pattern (which should return nothing); EX:d:〆る generates [^]; DATE:2013-10-20
			pattern_is_invalid = true;
			under = Pattern.compile("", Pattern.DOTALL | Pattern.UNICODE_CHARACTER_CLASS);
		}
	}
	public Regx_match Match(String input, int bgn) {
		Matcher match = under.matcher(input);
		boolean success = match.find(bgn);
		int match_bgn = success ? match.start() : String_.Find_none; 
		int match_end = success ? match.end() : String_.Find_none;
		Regx_group[] ary = Regx_group.Ary_empty;
		int groups_len = match.groupCount();
		if (success && groups_len > 0) {
			ary = new Regx_group[groups_len];
			for (int i = 0; i < groups_len; i++)
				ary[i] = new Regx_group(true, match.start(i + 1), match.end(i + 1), match.group(i + 1));				
		}
		return new Regx_match(success, match_bgn, match_end, ary);
	}
	public String ReplaceAll(String input, String replace) {return under.matcher(input).replaceAll(replace);}
	}
