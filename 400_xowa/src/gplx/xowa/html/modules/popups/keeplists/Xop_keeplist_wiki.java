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
package gplx.xowa.html.modules.popups.keeplists; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.modules.*; import gplx.xowa.html.modules.popups.*;
import gplx.core.regxs.*;
public class Xop_keeplist_wiki {
	public Xop_keeplist_wiki(Xowe_wiki wiki) {
		srl = new Xop_keeplist_wiki_srl(wiki);
	}
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {enabled = v;} private boolean enabled = false;	// NOTE: default to false, b/c wikis that are not listed in cfg will not call Rules_seal
	public Xop_keeplist_rule[] Rules() {return rules;} private Xop_keeplist_rule[] rules; private int rules_len;
	public Xop_keeplist_wiki_srl Srl() {return srl;} private Xop_keeplist_wiki_srl srl;
	public void Rules_add(Xop_keeplist_rule rule) {rules_list.Add(rule);} private ListAdp rules_list = ListAdp_.new_();
	public void Rules_seal() {
		this.rules = (Xop_keeplist_rule[])rules_list.Xto_ary_and_clear(Xop_keeplist_rule.class);
		this.rules_len = rules.length;
		if (rules_len == 0) return;
		if (rules_len == 1) {
			Xop_keeplist_rule rule_0 = rules[0];
			if (rule_0.Includes().length == 0)
				enabled = false;
			else
				enabled = true;
		}
		else
			enabled = true;
	}
	public boolean Match(byte[] ttl) {
		for (int i = 0; i < rules_len; ++i) {
			Xop_keeplist_rule rule = rules[i];
			if (rule.Match(ttl)) return true;
		}
		return false;
	}
}
