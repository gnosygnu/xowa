/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.langs; import gplx.*; import gplx.xowa.*;
import gplx.xowa.apps.fsys.*; import gplx.xowa.apps.gfs.*;
import gplx.xowa.langs.bldrs.*;	
public class Xoa_lang_mgr implements Gfo_invk {		
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	private final    Xobc_utl_make_lang mw_converter;
	public Xoa_lang_mgr(Xoa_app app, Xoa_gfs_mgr gfs_mgr) {
		this.mw_converter = new Xobc_utl_make_lang(this, app.Fsys_mgr(), app.Tid_is_edit() ? ((Xoae_app)app).Msg_log() : null);
		this.lang_en = Xol_lang_itm_.Lang_en_make(this); this.Add(lang_en);
		this.gfs_mgr = gfs_mgr;
	}
	public Xoa_gfs_mgr				Gfs_mgr() {return gfs_mgr;} private final    Xoa_gfs_mgr gfs_mgr;
	public Xol_lang_itm				Lang_en() {return lang_en;} private final    Xol_lang_itm lang_en; 
	public void						Clear() {hash.Clear();}
	public int						Len() {return hash.Count();}
	public void						Add(Xol_lang_itm itm)		{hash.Add(itm.Key_bry(), itm);}
	public Xol_lang_itm				Get_at(int i)				{return (Xol_lang_itm)hash.Get_at(i);}
	public Xol_lang_itm				Get_by(byte[] key)			{return (Xol_lang_itm)hash.Get_by(key);}
	public Xol_lang_itm				Get_by_or_load(byte[] key)	{return Get_by_or_new(key).Init_by_load_assert();}
	public Xol_lang_itm				Get_by_or_en(byte[] key) {	// called by Xowv_wiki for its .Lang()
		Xol_lang_itm rv = Get_by(key);
		return rv == null ? lang_en : rv;
	}
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
		else	return Gfo_invk_.Rv_unhandled;
	}	private static final String Invk_get = "get", Invk_mediawiki_converter = "mediawiki_converter";
	public static final    byte[] Fallback_false = Bry_.new_a7("false");
}
