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
package gplx.xowa.cfgs; import gplx.*; import gplx.xowa.*;
public class Xowc_xtns implements GfoInvkAble {
	private Hash_adp_bry hash = Hash_adp_bry.ci_a7();
	public Xowc_xtns() {hash.Add(Xowc_xtn_pages.Xtn_key, itm_pages);}
	public Xowc_xtn_pages Itm_pages() {return itm_pages;} private Xowc_xtn_pages itm_pages = new Xowc_xtn_pages();
	public Object Get_by_key(byte[] key) {return hash.Get_by_bry(key);}
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_get))					return (GfoInvkAble)hash.Get_by_bry(m.ReadBry("v"));
		else return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_get = "get";
}
