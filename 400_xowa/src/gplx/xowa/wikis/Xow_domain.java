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
package gplx.xowa.wikis; import gplx.*; import gplx.xowa.*;
import gplx.xowa.wikis.domains.*;
import gplx.xowa.langs.*;
public class Xow_domain {
	Xow_domain(byte[] domain_bry, Xow_domain_type domain_type, Xol_lang_itm lang_itm, Xol_lang_itm lang_orig_itm) {
		this.domain_bry = domain_bry; this.domain_type = domain_type; this.lang_itm = lang_itm; this.lang_orig_itm = lang_orig_itm;
		this.domain_str = String_.new_u8(domain_bry);
		this.abrv_xo = Xow_domain_abrv_xo_.To_bry(domain_bry, lang_orig_itm, domain_type);
	}
	public byte[]			Domain_bry() {return domain_bry;} private final byte[] domain_bry;
	public String			Domain_str() {return domain_str;} private final String domain_str;
	public int				Domain_tid() {return domain_type.Tid();}
	public Xow_domain_type	Domain_type() {return domain_type;} private final Xow_domain_type domain_type;
	public byte[]			Abrv_wm() {return abrv_wm;} public void Abrv_wm_(byte[] v) {abrv_wm = v;} private byte[] abrv_wm;	// EX: enwiki
	public byte[]			Abrv_xo() {return abrv_xo;} private final byte[] abrv_xo;
	public Xol_lang_itm		Lang_itm() {return lang_itm;} private final Xol_lang_itm lang_itm;
	public int				Lang_uid() {return lang_itm.Id();}
	public byte[]			Lang_key() {return lang_itm.Key();}
	public Xol_lang_itm		Lang_orig_itm() {return lang_orig_itm;} private final Xol_lang_itm lang_orig_itm;
	public int				Lang_orig_uid() {return lang_orig_itm.Id();}
	public byte[]			Lang_orig_key() {return lang_orig_itm.Key();}
	public int				Sort_idx() {return sort_idx;} public void Sort_idx_(int v) {sort_idx = v;} private int sort_idx = -1;	// used for Search
	public static Xow_domain new_(byte[] domain_bry, int domain_tid, byte[] lang_key) {
		Xol_lang_itm lang_itm = Xol_lang_itm_.Get_by_key_or_intl(lang_key);
		return new Xow_domain(domain_bry, Xow_domain_type_.Get_type_as_itm(domain_tid), lang_itm, lang_itm);
	}
	public static Xow_domain new_(byte[] domain_bry, int domain_tid, Xol_lang_itm lang) {return new Xow_domain(domain_bry, Xow_domain_type_.Get_type_as_itm(domain_tid), lang, lang);}
	public static Xow_domain new_orig(byte[] domain_bry, int domain_tid, byte[] lang_key, byte[] lang_orig_key) {
		Xol_lang_itm lang_itm = Xol_lang_itm_.Get_by_key_or_intl(lang_key);
		Xol_lang_itm lang_orig_itm = Bry_.Eq(lang_key, lang_orig_key) ? lang_itm : Xol_lang_itm_.Get_by_key_or_intl(lang_orig_key);
		return new Xow_domain(domain_bry, Xow_domain_type_.Get_type_as_itm(domain_tid), lang_itm, lang_orig_itm);
	}
}
