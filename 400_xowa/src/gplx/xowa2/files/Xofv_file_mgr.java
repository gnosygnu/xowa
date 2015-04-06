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
package gplx.xowa2.files; import gplx.*; import gplx.xowa2.*;
import gplx.dbs.*;
import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.caches.*;
import gplx.xowa.files.origs.*; import gplx.xowa.wmfs.apis.*;
public class Xofv_file_mgr {
	private final Xop_xfer_itm_hash lnki_hash = new Xop_xfer_itm_hash();
	public Xofv_file_mgr(byte[] wiki_bry) {
		this.wiki_bry = wiki_bry;
		this.cache_mgr = new Xof_cache_mgr(Gfo_usr_dlg_.I, null, null);
	}
	public byte[] Wiki_bry() {return wiki_bry;} private final byte[] wiki_bry;
	public Xofv_repo_mgr Repo_mgr() {return repo_mgr;} private final Xofv_repo_mgr repo_mgr = new Xofv_repo_mgr();
	public Xof_cache_mgr Cache_mgr() {return cache_mgr;} private final Xof_cache_mgr cache_mgr;
	public void Clear() {lnki_hash.Clear();}
	public void Reg(Xof_xfer_itm xfer_itm) {lnki_hash.Add(xfer_itm);}
	public void Process_lnki() {}
}
