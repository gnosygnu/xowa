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
package gplx.xowa.langs; import gplx.*; import gplx.xowa.*;
import gplx.xowa.apps.fsys.*;
import gplx.xowa.langs.bldrs.*;
public class Xoa_lang_mgr implements GfoInvkAble {		
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	private final Xobc_utl_make_lang mw_converter;
	public Xoa_lang_mgr(Xoa_app app) {
		this.mw_converter = new Xobc_utl_make_lang(this, app.Fsys_mgr(), app.Tid_is_edit() ? ((Xoae_app)app).Msg_log() : null);
		this.lang_en = Xol_lang_itm_.Lang_en_make(this); this.Add(lang_en);
	}
	public Xol_lang_itm				Lang_en() {return lang_en;} private final Xol_lang_itm lang_en; 
	public void						Clear() {hash.Clear();}
	public int						Len() {return hash.Count();}
	public void						Add(Xol_lang_itm itm)		{hash.Add(itm.Key_bry(), itm);}
	public Xol_lang_itm				Get_at(int i)				{return (Xol_lang_itm)hash.Get_at(i);}
	public Xol_lang_itm				Get_by(byte[] key)			{return (Xol_lang_itm)hash.Get_by(key);}
	public Xol_lang_itm				Get_by_or_load(byte[] key)	{return Get_by_or_new(key).Init_by_load_assert();}
	public Xol_lang_itm				Get_by_or_new(byte[] key) {
		Xol_lang_itm rv = Get_by(key);
		if (rv == null) {
			rv = new Xol_lang_itm(this, key);
			this.Add(rv);
		}
		return rv;
	}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))					return Get_by_or_new(m.ReadBry("key"));
		else if	(ctx.Match(k, Invk_mediawiki_converter))	return mw_converter;
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_get = "get", Invk_mediawiki_converter = "mediawiki_converter";
	public static final byte[] Fallback_false = Bry_.new_a7("false");
}
