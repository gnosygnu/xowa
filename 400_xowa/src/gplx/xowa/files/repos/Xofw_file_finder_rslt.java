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
import gplx.xowa.parsers.utils.*;
public class Xofw_file_finder_rslt {
	public byte[] Ttl() {return ttl;} private byte[] ttl;
	public byte[] Redirect() {return redirect;} private byte[] redirect;
	public int Repo_idx() {return repo_idx;} private int repo_idx;
	public byte[] Repo_wiki_key() {return repo_wiki_key;} private byte[] repo_wiki_key;
	public void Init(byte[] ttl) {
		this.ttl = ttl; redirect = Xop_redirect_mgr.Redirect_null_bry; repo_wiki_key = null; repo_idx = Byte_.Max_value_127;
	}
	public void Done(int repo_idx, byte[] repo_wiki_key, byte[] redirect) {
		this.repo_idx = repo_idx; this.repo_wiki_key = repo_wiki_key; this.redirect = redirect;
	}
}
