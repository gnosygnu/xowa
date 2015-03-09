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
import gplx.xowa.langs.*;
public class Xow_domain {
	Xow_domain(byte[] domain_bry, int domain_tid, Xol_lang_itm lang_itm, Xol_lang_itm lang_orig_itm) {
		this.domain_bry = domain_bry; this.domain_tid = domain_tid; this.lang_itm = lang_itm; this.lang_orig_itm = lang_orig_itm;
	}
	public byte[]			Domain_bry() {return domain_bry;} private final byte[] domain_bry;
	public int				Domain_tid() {return domain_tid;} private final int domain_tid;
	public Xol_lang_itm		Lang_itm() {return lang_itm;} private final Xol_lang_itm lang_itm;
	public int				Lang_uid() {return lang_itm.Id();}
	public byte[]			Lang_key() {return lang_itm.Key();}
	public Xol_lang_itm		Lang_orig_itm() {return lang_orig_itm;} private final Xol_lang_itm lang_orig_itm;
	public int				Lang_orig_uid() {return lang_orig_itm.Id();}
	public byte[]			Lang_orig_key() {return lang_orig_itm.Key();}
	public byte[]			Wmf_key() {return wmf_key;} public void Wmf_key_(byte[] v) {wmf_key = v;} private byte[] wmf_key;
	public static Xow_domain new_(byte[] domain_bry, int domain_tid, byte[] lang_key) {
		Xol_lang_itm lang_itm = Xol_lang_itm_.Get_by_key_or_intl(lang_key);
		return new Xow_domain(domain_bry, domain_tid, lang_itm, lang_itm);
	}
	public static Xow_domain new_(byte[] domain_bry, int domain_tid, Xol_lang_itm lang) {return new Xow_domain(domain_bry, domain_tid, lang, lang);}
	public static Xow_domain new_orig(byte[] domain_bry, int domain_tid, byte[] lang_key, byte[] lang_orig_key) {
		Xol_lang_itm lang_itm = Xol_lang_itm_.Get_by_key_or_intl(lang_key);
		Xol_lang_itm lang_orig_itm = Bry_.Eq(lang_key, lang_orig_key) ? lang_itm : Xol_lang_itm_.Get_by_key_or_intl(lang_orig_key);
		return new Xow_domain(domain_bry, domain_tid, lang_itm, lang_orig_itm);
	}
}
