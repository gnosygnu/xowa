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
package gplx.xowa; import gplx.*;
public class Xoa_repo_mgr implements GfoInvkAble {
	public Xoa_repo_mgr(Xoa_app app) {this.app = app;}
	public Xoa_app App() {return app;} private Xoa_app app;
	public int Count() {return hash.Count();}
	public Xof_repo_itm Get_at(int i)		{return (Xof_repo_itm)hash.FetchAt(i);}
	public Xof_repo_itm Get_by(byte[] key)	{return (Xof_repo_itm)hash.Fetch(key);}
	public Xof_repo_itm Get_primary(byte[] key)	{
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Xof_repo_itm repo = (Xof_repo_itm)hash.FetchAt(i);
			if (Bry_.Eq(key, repo.Wiki_key()) && repo.Primary()) return repo;
		}
		return null;
	}
	public Xof_repo_itm Get_by_wmf(byte[] key)	{
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Xof_repo_itm repo = (Xof_repo_itm)hash.FetchAt(i);
			if (Bry_.Eq(key, repo.Wiki_key()) && repo.Wmf_fsys()) return repo;
		}
		return null;
	}
	public Xof_repo_itm Get_by_wiki_key(byte[] key)	{
		int len = hash.Count();
		for (int i = 0; i < len; i++) {
			Xof_repo_itm repo = (Xof_repo_itm)hash.FetchAt(i);
			if (Bry_.Eq(key, repo.Wiki_key())) return repo;
		}
		return null;
	}
	public Xof_repo_itm Add(Xof_repo_itm itm) {hash.Add(itm.Key(), itm); return itm;} private OrderedHash hash = OrderedHash_.new_bry_();
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_set))			return Set(m.ReadStr("key"), m.ReadStr("url"), m.ReadStr("wiki"));
		else	return GfoInvkAble_.Rv_unhandled;
	}	private static final String Invk_set = "set";
	public Xof_repo_itm Set(String key, String url_str, String wiki) {
		byte[] key_bry = Bry_.new_utf8_(key);
		Xof_repo_itm itm = (Xof_repo_itm)hash.Fetch(key_bry);
		if (itm == null) {
			itm = new Xof_repo_itm(this, key_bry);
			this.Add(itm);
		}
		itm.Root_str_(url_str).Wiki_key_(Bry_.new_utf8_(wiki));
		return itm;
	}
}
