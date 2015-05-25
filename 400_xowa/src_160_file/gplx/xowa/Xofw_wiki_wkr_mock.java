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
import gplx.xowa.files.*;
public class Xofw_wiki_wkr_mock implements Xofw_wiki_finder {
	int repo_idx; byte[] repo_wiki_key;
	public Xofw_wiki_wkr_mock Clear_en_wiki() {return Clear(1, Bry_en_wiki);}
	public Xofw_wiki_wkr_mock Clear_commons() {return Clear(0, Bry_commons);}
	Xofw_wiki_wkr_mock Clear(int repo_idx, byte[] repo_wiki_key) {
		this.repo_idx = repo_idx; this.repo_wiki_key = repo_wiki_key;
		if_ttl = then_redirect = Bry_.Empty;
		return this;
	}	
	private static final byte[] Bry_commons = Bry_.new_a7("commons.wikimedia.org"), Bry_en_wiki = Bry_.new_a7("en.wikipedia.org");
	public Xofw_wiki_wkr_mock Repo_idx_(int v) {this.repo_idx = v; return this;}
	public Xofw_wiki_wkr_mock Redirect_(String if_ttl_str, String then_redirect_str) {this.if_ttl = Bry_.new_u8(if_ttl_str); this.then_redirect = Bry_.new_u8(then_redirect_str); return this;} private byte[] if_ttl, then_redirect;
	public void Find(List_adp repo_pairs, Xof_xfer_itm file) {
		byte[] ttl = file.Lnki_ttl();
		if (Bry_.Eq(ttl, if_ttl) && repo_idx != -1)		{file.Orig_ttl_and_redirect_(ttl, then_redirect);	file.Orig_repo_id_(repo_idx);}
		else											{file.Orig_ttl_and_redirect_(ttl, Bry_.Empty)	;	file.Orig_repo_id_(Xof_meta_itm.Repo_unknown);}	// FUTURE: this should be missing, but haven't implemented unknown yet
	}
	public boolean Locate(Xofw_file_finder_rslt rv, List_adp repo_pairs, byte[] ttl) {
		rv.Init(ttl);
		byte[] redirect = Bry_.Eq(ttl, if_ttl) ? then_redirect : null;
		rv.Done(repo_idx, repo_wiki_key, redirect);
		return true;
	}
}
