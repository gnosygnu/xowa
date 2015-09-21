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
package gplx.xowa.parsers.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.parsers.*;
import gplx.core.btries.*;
import gplx.xowa.langs.vnts.*;
class Xop_vnt_rules_parser_ {
	public static Btrie_slim_mgr new_trie(Xol_vnt_regy regy) {
		Btrie_slim_mgr rv = Btrie_slim_mgr.ci_a7();	// NOTE:ci.ascii:MW_const.en; lang variant name; EX:zh-hans
		int len = regy.Len();
		for (int i = 0; i < len; ++i) {
			Xol_vnt_itm itm = regy.Get_at(i);
			byte[] key = itm.Key();
			rv.Add_obj(key, Xop_vnt_rule_trie_itm.lang_(key));
		}
		rv.Add_obj(";", Xop_vnt_rule_trie_itm.Dlm_semic);
		return rv;
	}
	public static Xop_tkn_itm[] Get_subs_as_ary(Xop_tkn_itm owner, int bgn, int end) {
		int len = end - bgn;
		Xop_tkn_itm[] rv = new Xop_tkn_itm[len];
		for (int i = bgn; i < end; i++)
			rv[i - bgn] = owner.Subs_get(i);
		return rv;
	}
}
class Xop_vnt_rule_trie_itm {
	public Xop_vnt_rule_trie_itm(byte tid, byte[] lang) {this.tid = tid; this.lang = lang;}
	public byte Tid() {return tid;} private byte tid;
	public byte[] Lang() {return lang;} private byte[] lang;
	public static final byte Tid_semic = 1, Tid_lang = 2;
	public static Xop_vnt_rule_trie_itm lang_(byte[] lang) {return new Xop_vnt_rule_trie_itm(Tid_lang, lang);}
	public static final Xop_vnt_rule_trie_itm
	  Dlm_semic = new Xop_vnt_rule_trie_itm(Tid_semic, null)
	;
}
/*
-{flags|lang:rule}-				EX: -{A|zh-hant:a}-
-{lang:rule;lang:rule}			EX: -{zh-hans:a;zh-hant:b}-
-{lang;lang|rule}-				EX: -{zh-hans;zh-hant|XXXX}-
-{rule}-						EX: -{a}-
-{flags|from=>variant:to;}-		EX: -{H|HUGEBLOCK=>zh-cn:macro;}-
-{lang:data_0;data_1;}-			EX: -{zh-hans:<span style='border:solid;color:blue;'>;zh-hant:b}-
. where data_0 and data_1 is actually one itm since ; is not delimiter b/c data_1 must be variant_code
-{zh-hans:a-{zh-hans:b}-c}-	
*/
