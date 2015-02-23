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
public class Xol_vnt_mgr implements GfoInvkAble {
	private OrderedHash vnts = OrderedHash_.new_bry_();
	private int converter_ary_len; private OrderedHash tmp_page_list = OrderedHash_.new_bry_();
	public Xol_vnt_mgr(Xol_lang lang) {this.lang = lang;}
	public Xol_vnt_converter[] Converter_ary() {return converter_ary;} private Xol_vnt_converter[] converter_ary; 
	public Xol_lang Lang() {return lang;} private Xol_lang lang;
	public byte[] Cur_vnt() {return cur_vnt;} public Xol_vnt_mgr Cur_vnt_(byte[] v) {cur_vnt = v; return this;} private byte[] cur_vnt = Bry_.Empty;
	public boolean Enabled() {return enabled;} public void Enabled_(boolean v) {this.enabled = v;} private boolean enabled = false;
	public String Html_style() {return html_style;} private String html_style = "";
	public void Init_by_wiki(Xowe_wiki wiki) {
		if (!enabled) return;
		Xop_vnt_lxr_.set_(wiki);
	}
	public Xol_vnt_itm Get_or_new(byte[] key) {
		Xol_vnt_itm rv = (Xol_vnt_itm)vnts.Fetch(key);
		if (rv == null) {			
			rv = new Xol_vnt_itm(this, key);
			vnts.Add(key, rv);
			enabled = true;	// mark enabled if any vnts have been added
		}
		return rv;
	}
	public void Convert_ttl_init() {
		int vnts_len = vnts.Count();
		converter_ary_len = vnts_len;
		converter_ary = new Xol_vnt_converter[vnts_len];
		for (int i = 0; i < vnts_len; i++) {
			Xol_vnt_itm itm = (Xol_vnt_itm)vnts.FetchAt(i);
			converter_ary[i] = itm.Converter();
			if (i == 0) cur_vnt = itm.Key();	// default to 1st item
		}
	}
	public Xodb_page Convert_ttl(Xowe_wiki wiki, Xoa_ttl ttl) {return Convert_ttl(wiki, ttl.Ns(), ttl.Page_db());}	// NOTE: not Full_db as ttl.Ns is passed; EX:Шаблон:Šablon:Jez-eng; PAGE:sr.w:ДНК DATE:2014-07-06
	public Xodb_page Convert_ttl(Xowe_wiki wiki, Xow_ns ns, byte[] ttl_bry) {
		Bry_bfr tmp_bfr = wiki.Utl_bry_bfr_mkr().Get_b512();
		Xodb_page rv = Convert_ttl(wiki, tmp_bfr, ns, ttl_bry);
		tmp_bfr.Mkr_rls();
		return rv;
	}
	public Xodb_page Convert_ttl(Xowe_wiki wiki, Bry_bfr tmp_bfr, Xow_ns ns, byte[] ttl_bry) {	// REF.MW:LanguageConverter.php|findVariantLink
		int converted = Convert_ttl_convert(wiki, tmp_bfr, ns, ttl_bry);	// convert ttl for each vnt
		if (converted == 0) return Xodb_page.Null;							// ttl_bry has no conversions; exit;
		wiki.Db_mgr().Load_mgr().Load_by_ttls(Cancelable_.Never, tmp_page_list, true, 0, converted);
		for (int i = 0; i < converted; i++) {
			Xodb_page page = (Xodb_page)tmp_page_list.FetchAt(i);
			if (page.Exists()) return page;									// return 1st found page
		}
		return Xodb_page.Null;
	}
	private int Convert_ttl_convert(Xowe_wiki wiki, Bry_bfr tmp_bfr, Xow_ns ns, byte[] ttl_bry) {
		tmp_page_list.Clear();
		int rv = 0;
		for (int i = 0; i < converter_ary_len; i++) {					// convert ttl for each variant
			Xol_vnt_converter converter = converter_ary[i];
			tmp_bfr.Clear();
			if (!converter.Convert_text(tmp_bfr, ttl_bry)) continue;	// ttl is not converted for variant; ignore
			Xoa_ttl ttl = Xoa_ttl.parse_(wiki, ns.Id(), tmp_bfr.Xto_bry_and_clear());	// NOTE: must convert to ttl in order to upper 1st letter; EX:{{jez-eng|sense}} -> Jez-eng; PAGE:sr.w:ДНК DATE:2014-07-06
			if (ttl == null) continue;
			Xodb_page page = new Xodb_page();
			page.Ttl_(ns, ttl.Page_db());
			byte[] converted_ttl = page.Ttl_w_ns();
			if (tmp_page_list.Has(converted_ttl)) continue;
			tmp_page_list.Add(converted_ttl, page);
			++rv;
		}
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))					return Get_or_new(m.ReadBry("v"));
		else if	(ctx.Match(k, Invk_init_end))				Convert_ttl_init();
		else if	(ctx.Match(k, Invk_cur_vnt_))				cur_vnt = m.ReadBry("v");
		else if	(ctx.Match(k, Invk_html_style_))			html_style = m.ReadStr("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_get = "get", Invk_init_end = "init_end", Invk_cur_vnt_ = "cur_vnt_", Invk_html_style_ = "html_style_";
}
