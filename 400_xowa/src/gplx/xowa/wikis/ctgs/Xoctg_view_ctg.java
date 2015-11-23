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
package gplx.xowa.wikis.ctgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public class Xoctg_view_ctg {
	public byte[] Name() {return name;} public Xoctg_view_ctg Name_(byte[] v) {name = v; return this;} private byte[] name;
	public int Total_count() {return subcs.Total() + pages.Total() + files.Total();}
	public Xoctg_view_grp Subcs() {return subcs;} private Xoctg_view_grp subcs = new Xoctg_view_grp(Xoa_ctg_mgr.Tid_subc);
	public Xoctg_view_grp Pages() {return pages;} private Xoctg_view_grp pages = new Xoctg_view_grp(Xoa_ctg_mgr.Tid_page);
	public Xoctg_view_grp Files() {return files;} private Xoctg_view_grp files = new Xoctg_view_grp(Xoa_ctg_mgr.Tid_file).Hdr_exists_(false);
	public Xoctg_view_grp Grp_by_tid(byte tid) {
		switch (tid) {
			case Xoa_ctg_mgr.Tid_subc: return subcs;
			case Xoa_ctg_mgr.Tid_page: return pages;
			case Xoa_ctg_mgr.Tid_file: return files;
			default: throw Err_.new_unhandled(tid);
		}
	}
	public boolean Hidden() {return hidden;} private boolean hidden;
	public void Num_(Xoctg_data_ctg data_ctg) {
		this.hidden = data_ctg.Hidden();
		Num_set(data_ctg, Xoa_ctg_mgr.Tid_subc);
		Num_set(data_ctg, Xoa_ctg_mgr.Tid_file);
		Num_set(data_ctg, Xoa_ctg_mgr.Tid_page);
	}
	private void Num_set(Xoctg_data_ctg data_ctg, byte tid) {
		Xoctg_idx_mgr idx_mgr = data_ctg.Grp_by_tid(tid);
		if (idx_mgr != null) Grp_by_tid(tid).Total_(idx_mgr.Total());
	}
	public void Fill(Xoctg_url url_ctg, Xoctg_data_ctg data_ctg) {
		for (byte i = 0; i < Xoa_ctg_mgr.Tid__max; i++) {
			Fill_grp(url_ctg, data_ctg, i);
		}
	}
	private void Fill_grp(Xoctg_url url_ctg, Xoctg_data_ctg data_ctg, byte i) {
		Xoctg_view_grp view_grp = Grp_by_tid(i);
		Xoctg_idx_mgr data_grp = data_ctg.Grp_by_tid(i); if (data_grp == null) return; // no itms in grp
		byte[] url_bmk = url_ctg.Grp_idxs()[i]; byte url_bmk_fwd = url_ctg.Grp_fwds()[i];
		data_grp.Find(view_grp.Itms_list(), data_grp.Src(), url_bmk_fwd != Bool_.N_byte, url_bmk, 200, tmp_last_plus_one);
		view_grp.Itms_last_sortkey_(tmp_last_plus_one.Sort_key());
		view_grp.Itms_make();
	}	private Xoctg_view_itm tmp_last_plus_one = new Xoctg_view_itm();
}
