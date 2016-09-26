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
package gplx.xowa.files.repos; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
public class Xof_repo_pair implements Gfo_invk {
	public Xof_repo_pair(byte id, byte[] wiki_domain, Xof_repo_itm src, Xof_repo_itm trg) {
		this.id = id; this.wiki_domain = wiki_domain; this.src = src; this.trg = trg;
	}
	public byte				Id()			{return id;} private byte id;
	public byte[]			Wiki_domain()	{return wiki_domain;} private final    byte[] wiki_domain;
	public Xof_repo_itm		Src()			{return src;} private final    Xof_repo_itm src;
	public Xof_repo_itm		Trg()			{return trg;} private final    Xof_repo_itm trg;

	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		if		(ctx.Match(k, Invk_repo_id_))		id = m.ReadByte("v");
		else	return Gfo_invk_.Rv_unhandled;
		return this;
	}	private static final String Invk_repo_id_ = "repo_id_";
}
