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
package gplx.texts; import gplx.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class RegxAdp {
		void Under_sync() {
		try {under = Pattern.compile(pattern, Pattern.DOTALL);}
		catch (Exception e) {	// NOTE: if invalid, then default to empty pattern (which should return nothing); EX:d:〆る generates [^]; DATE:2013-10-20
			pattern_is_invalid = true;
			under = Pattern.compile("", Pattern.DOTALL);
		}
	}  private Pattern under;
	public RegxMatch Match(String input, int bgn) {
		Matcher match = under.matcher(input);
		boolean success = match.find(bgn);
		int match_bgn = success ? match.start() : String_.Find_none; 
		int match_end = success ? match.end() : String_.Find_none;
		RegxGroup[] ary = RegxGroup.Ary_empty;
		int groups_len = match.groupCount();
		if (success && groups_len > 0) {
			ary = new RegxGroup[groups_len];
			for (int i = 0; i < groups_len; i++)
				ary[i] = new RegxGroup(true, match.start(i + 1), match.end(i + 1), match.group(i + 1));				
		}
		return new RegxMatch(success, match_bgn, match_end, ary);
	}
	public String ReplaceAll(String input, String replace) {return under.matcher(input).replaceAll(replace);}
		public String Pattern() {return pattern;} public RegxAdp Pattern_(String val) {pattern = val; Under_sync(); return this;} private String pattern;
	public boolean Pattern_is_invalid() {return pattern_is_invalid;} private boolean pattern_is_invalid = false;
	public RegxMatch[] Match_all(String text, int bgn) {
		int idx = bgn;
		ListAdp rv = ListAdp_.new_();
		int len = String_.Len(text);
		while (idx < len)  {
			RegxMatch match = this.Match(text, idx);
			if (match.Rslt_none()) break;
			rv.Add(match);
			int find_bgn = match.Find_bgn();
			int find_len = match.Find_len();
			idx = find_len == 0				// find_bgn == find_end
				? find_bgn + 1				// add 1 to resume search from next char; DATE:2014-09-02
				: find_bgn + find_len		// otherwise search after find_end
				;
		}
		return (RegxMatch[])rv.Xto_ary(RegxMatch.class);
	}
	@gplx.Internal protected RegxAdp(String regx) {Pattern_(regx);}
}
