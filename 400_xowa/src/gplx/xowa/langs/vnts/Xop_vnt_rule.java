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
package gplx.xowa.langs.vnts; import gplx.*; import gplx.xowa.*; import gplx.xowa.langs.*;
public class Xop_vnt_rule extends Xop_tkn_itm_base {
	public Xop_vnt_rule(byte[] rule_macro, byte[] rule_lang, Xop_tkn_itm[] rule_subs) {this.rule_macro = rule_macro; this.rule_lang = rule_lang; this.rule_subs = rule_subs;}
	@Override public byte Tkn_tid() {return Xop_tkn_itm_.Tid_vnt_rule;}
	public byte[] Rule_macro() {return rule_macro;} private byte[] rule_macro;
	public byte[] Rule_lang() {return rule_lang;} private byte[] rule_lang;
	public Xop_tkn_itm[] Rule_subs() {return rule_subs;} private Xop_tkn_itm[] rule_subs;
	public static final byte[] Null_lang = null, Null_macro = null;
}
