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
package gplx.xowa.setup.maints; import gplx.*; import gplx.xowa.*; import gplx.xowa.setup.*;
public class Xoa_maint_wikis_mgr implements GfoInvkAble {
	public Xoa_maint_wikis_mgr(Xoae_app app) {this.app = app;} private Xoae_app app;
	private OrderedHash hash = OrderedHash_.new_bry_();
	public int Len() {return hash.Count();}
	public Xowe_wiki Get_at(int i) {
		if (init) Init();
		byte[] domain = (byte[])hash.FetchAt(i);
		Xowe_wiki wiki = app.Wiki_mgr().Get_by_key_or_make(domain);
		wiki.Init_assert();
		return wiki;
	}
	public void Add(byte[] domain) {hash.AddReplace(domain, domain);}	// NOTE: must be AddReplace to replace existing wikis
	public void Init() {
		int len = this.Len();
		for (int i = 0; i < len; i++) {
			byte[] domain = (byte[])hash.FetchAt(i);
			Xowe_wiki wiki = app.Wiki_mgr().Get_by_key_or_make(domain);
			wiki.Init_assert();
		}
		init = false;
	}
	private boolean init = true;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_len))		return this.Len();
		else if	(ctx.Match(k, Invk_get_at))		return this.Get_at(m.ReadInt("v"));
		else	return GfoInvkAble_.Rv_unhandled;
//			return this;
	}	private static final String Invk_len = "len", Invk_get_at = "get_at";
}