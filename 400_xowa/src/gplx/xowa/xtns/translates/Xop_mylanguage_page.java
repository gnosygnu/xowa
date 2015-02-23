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
package gplx.xowa.xtns.translates; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.xowa.specials.*;
public class Xop_mylanguage_page implements Xows_page {
	public void Special_gen(Xoa_url calling_url, Xoae_page page, Xowe_wiki wiki, Xoa_ttl ttl) {
		// Special:MyLanguage/Help:A -> Help:A/fr
		byte[] page_bry = ttl.Leaf_txt_wo_qarg(); 					// EX: Help:A
		byte[] lang_key = wiki.Appe().User().Lang().Key_bry();		// EX: fr
		byte[] trg_bry = page_bry;
		boolean lang_is_english = Bry_.Eq(lang_key, Xol_lang_.Key_en); 
		if (!lang_is_english)
			trg_bry = Bry_.Add_w_dlm(Xoa_ttl.Subpage_spr, page_bry, lang_key);
		Xoae_page found_page = wiki.Data_mgr().Redirect(page, trg_bry);
		if (found_page.Missing() && !lang_is_english)	// foreign lang does not exist; default to english
			wiki.Data_mgr().Redirect(page, page_bry);
	}
}
