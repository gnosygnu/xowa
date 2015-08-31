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
package gplx.xowa.wmfs.apis; import gplx.*; import gplx.xowa.*; import gplx.xowa.wmfs.*;
class Xowm_updater {
	public void Exec(byte[][] ttls_bry) {
		/*
		Xoa_ttl[] ttls_ary = Parse_ttl();
		loop (50) {
			Xowm_rev_meta_itm[] trg_revs = Get_trg_revs();
			Xowm_rev_meta_itm[] src_revs = api.Get_src_metas();
			Eliminate(src_rev, trg_revs);
			src_texts = api.Get_src_texts();
			overwrite_trgs();
		}
		loop (changed texts) {
			parse page
		}
		loop (changed texts) {
			get other ref
			get files
		}
		loop (changed texts) {
			update category
			update search			
		}
		*/
	}
}
class Xowm_rev_meta_api__wm {
	public Xowm_rev_meta_itm[] Get(Gfo_usr_dlg usr_dlg, Xowmf_mgr wmf_mgr, Xow_wmf_api_mgr api_mgr, String domain_str, Xoa_ttl[] ttls_ary) {
		byte[] ttls_bry = To_api_arg(ttls_ary);
		byte[] json = api_mgr.Api_exec(usr_dlg, wmf_mgr, domain_str, "action=query&prop=revisions&rvprop=size|ids|timestamp&format=jsonfm&titles=" + String_.new_u8(ttls_bry));
		return Parse(json);
	}
	private byte[] To_api_arg(Xoa_ttl[] ttls_ary) {
		return null;
	}
	private Xowm_rev_meta_itm[] Parse(byte[] json) {
		return null;
	}
}
class Xowm_rev_meta_itm {
	public Xowm_rev_meta_itm(int page_id, int ns_id, byte[] ttl, int rev_id, int rev_len, byte[] rev_time, byte[] rev_user, byte[] rev_note) {
		this.page_id = page_id; this.ns_id = ns_id; this.ttl = ttl;
		this.rev_id = rev_id; this.rev_len = rev_len; this.rev_time = rev_time;
		this.rev_user = rev_user; this.rev_note = rev_note;
	}
	public int Page_id() {return page_id;} private final int page_id;
	public int Ns_id() {return ns_id;} private final int ns_id;
	public byte[] Ttl() {return ttl;} private final byte[] ttl;
	public int Rev_id() {return rev_id;} private final int rev_id;
	public int Rev_len() {return rev_len;} private final int rev_len;
	public byte[] Rev_time() {return rev_time;} private final byte[] rev_time;
	public byte[] Rev_user() {return rev_user;} private final byte[] rev_user;
	public byte[] Rev_note() {return rev_note;} private final byte[] rev_note;
}
