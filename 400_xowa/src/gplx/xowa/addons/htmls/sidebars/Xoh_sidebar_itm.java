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
package gplx.xowa.addons.htmls.sidebars; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.htmls.*;
public class Xoh_sidebar_itm {
	public Xoh_sidebar_itm(boolean tid_is_itm, byte[] text_key, byte[] text_val, byte[] href) {
		this.tid_is_itm = tid_is_itm;
		this.id = gplx.langs.htmls.encoders.Gfo_url_encoder_.Id.Encode(Bry_.Add(CONST_id_prefix, text_key));	// build id; EX:"n-encoded_id"
		this.text = text_val;
		this.href = href;
	}
	public boolean					Tid_is_itm() {return tid_is_itm;} private final    boolean tid_is_itm;
	public byte[]				Id() {return id;} private final    byte[] id;
	public byte[]				Text() {return text;} private final    byte[] text;
	public byte[]				Href() {return href;} private final    byte[] href;
	public byte[]				Title() {return title;} private byte[] title;
	public byte[]				Accesskey() {return accesskey;} private byte[] accesskey;
	public byte[]				Atr_accesskey_and_title() {return atr_accesskey_and_title;} private byte[] atr_accesskey_and_title;
	public int					Subs__len() {return subs.Count();} private final    List_adp subs = List_adp_.New();
	public Xoh_sidebar_itm		Subs__get_at(int i) {return (Xoh_sidebar_itm)subs.Get_at(i);}
	public Xoh_sidebar_itm		Subs__add(Xoh_sidebar_itm... ary) {
		int len = ary.length;
		for (int i = 0; i < len; ++i)
			subs.Add(ary[i]);
		return this;
	}

	public void Init_by_title_and_accesskey(byte[] title, byte[] accesskey, byte[] atr_accesskey_and_title) {
		this.title = title;
		this.accesskey = accesskey;
		this.atr_accesskey_and_title = atr_accesskey_and_title;
	}

	private static final    byte[] CONST_id_prefix = Bry_.new_a7("n-");
}
