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
package gplx.xowa.wikis.pages.redirects; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*; import gplx.xowa.wikis.pages.*;
import gplx.xowa.specials.*;
public class Xopg_redirect_mgr {
	private final    List_adp itms = List_adp_.New();
	public int					Itms__len()							{return itms.Len();}
	public byte[]				Itms__get_at_0th_or_null()			{return itms.Len() == 0 ? null : this.Itms__get_at(0).Wikitext();}
	public Xopg_redirect_itm	Itms__get_at_nth_or_null()			{return itms.Len() == 0 ? null : (Xopg_redirect_itm)itms.Get_at(itms.Len() - 1);}
	public Xopg_redirect_itm	Itms__get_at(int i)					{return (Xopg_redirect_itm)itms.Get_at(i);}
	public void					Itms__add__article(Xoa_url url, Xoa_ttl ttl, byte[] wikitext)	{Itms__add(url, ttl, wikitext);}
	public void					Itms__add__special(Xow_wiki wiki, Xow_special_meta meta, Keyval... url_args) {
		// build url and include args if available
		byte[] url_bry = meta.Ttl_bry();
		int url_args_len = url_args.length;
		if (url_args_len > 0) {
			Bry_bfr bfr = Bry_bfr_.New();
			bfr.Add(url_bry);
			for (int i = 0; i < url_args_len; ++i) {
				Keyval url_arg = url_args[i];
				bfr.Add_byte(i == 0 ? Byte_ascii.Question : Byte_ascii.Amp);
				bfr.Add_str_u8(url_arg.Key());
				bfr.Add_byte(Byte_ascii.Eq);
				bfr.Add_obj(url_arg.Val());
			}
			url_bry = bfr.To_bry_and_clear();
		}

		// create objects and add to list
		Xoa_url url = wiki.Utl__url_parser().Parse(url_bry);
		Xoa_ttl ttl = wiki.Ttl_parse(meta.Ttl_bry());
		this.Itms__add(url, ttl, null);
	}
	private void				Itms__add(Xoa_url url, Xoa_ttl ttl, byte[] wikitext) {
		Xopg_redirect_itm itm = new Xopg_redirect_itm(url, ttl, wikitext);
		itms.Add(itm);
	}
	public void Clear() {
		itms.Clear();
	}
}
