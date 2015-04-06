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
package gplx.xowa.xtns.wdatas.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
public class Xob_wdata_qid_txt extends Xob_wdata_qid_base {
	@Override public String Wkr_key() {return gplx.xowa.bldrs.Xob_cmd_keys.Key_tdb_text_wdata_qid;}
	
	Wdata_idx_bldr_qid qid_bldr;
	@Override public void Qid_bgn() {qid_bldr = new Wdata_idx_bldr_qid().Ctor(this, bldr, wiki, dump_fil_len);}
	@Override public void Qid_add(byte[] wiki_key, Xow_ns ns, byte[] ttl, byte[] qid) {
		qid_bldr.Add(String_.new_utf8_(wiki_key), ns, ttl, qid);
	}
	@Override public void Qid_end() {
		qid_bldr.Flush();
		qid_bldr.Make();
	}
}
