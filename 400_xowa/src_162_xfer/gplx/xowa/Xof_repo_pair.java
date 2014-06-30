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
public class Xof_repo_pair implements GfoInvkAble {
	public Xof_repo_pair(int id, Xof_repo_itm src, Xof_repo_itm trg, Xof_meta_mgr trg_meta_mgr, byte[] wiki_key) {
		this.id = (byte)id; this.src = src; this.trg = trg; this.trg_meta_mgr = trg_meta_mgr; this.wiki_key = wiki_key;
		this.repo_id = this.id;
	}
	public byte Id() {return id;} private byte id;
	public byte Repo_id() {return repo_id;} private byte repo_id;
	public Xof_repo_itm Src() {return src;} private Xof_repo_itm src;
	public Xof_repo_itm Trg() {return trg;} private Xof_repo_itm trg;
	public Xof_meta_mgr Trg_meta_mgr() {return trg_meta_mgr;} private Xof_meta_mgr trg_meta_mgr;
	public byte[] Wiki_key() {return wiki_key;} private byte[] wiki_key;
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_repo_id_))		repo_id = m.ReadByte("v");
		else	return GfoInvkAble_.Rv_unhandled;
		return this;
	}	private static final String Invk_repo_id_ = "repo_id_";
}
