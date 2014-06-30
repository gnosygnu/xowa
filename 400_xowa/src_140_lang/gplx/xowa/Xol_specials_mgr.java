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
package gplx.xowa; import gplx.*;
public class Xol_specials_mgr implements GfoInvkAble {
	private OrderedHash hash_by_special = OrderedHash_.new_bry_(), hash_by_aliases = OrderedHash_.new_bry_();
	public Xol_specials_mgr(Xol_lang lang) {this.lang = lang;} private Xol_lang lang;
	public void Clear() {hash_by_special.Clear();}
	public int Count() {return hash_by_special.Count();}
	public Xol_specials_itm Get_at(int i) {return (Xol_specials_itm)hash_by_special.FetchAt(i);}
	public Xol_specials_itm Get_by_alias(byte[] alias) {return (Xol_specials_itm)hash_by_aliases.Fetch(alias);}
	public Xol_specials_itm Get_by_key(byte[] special) {return (Xol_specials_itm)hash_by_special.Fetch(special);}
	public void Add(byte[] special, byte[][] alias_ary) {
		Xol_specials_itm itm = new Xol_specials_itm(special, alias_ary);
		hash_by_special.Add(special, itm);
		int len = alias_ary.length;
		for (int i = 0; i < len; i++) {
			byte[] alias = alias_ary[i];
			if (!hash_by_aliases.Has(alias))
				hash_by_aliases.Add(alias, itm);
		}
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_lang))			return lang;
		else if	(ctx.Match(k, Invk_clear))			this.Clear();
		else if	(ctx.Match(k, Invk_load_text))		Xol_lang_srl.Load_specials(this, m.ReadBry("v"));
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}
	public static final String Invk_lang = "lang", Invk_clear = "clear", Invk_load_text = "load_text";
}
