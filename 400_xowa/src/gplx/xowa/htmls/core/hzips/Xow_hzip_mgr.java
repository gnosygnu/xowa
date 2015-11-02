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
package gplx.xowa.htmls.core.hzips; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.core.ios.*; import gplx.xowa.htmls.core.hzips.stats.*;
public class Xow_hzip_mgr {
	private final Xow_wiki wiki; private final Io_stream_zip_mgr zip_mgr;
	private final Bry_bfr tmp_bfr = Bry_bfr.new_(255);
	private boolean default_hzip_enabled; private byte default_zip_tid, body_flag;
	public Xow_hzip_mgr(Xow_wiki wiki) {
		this.wiki = wiki;
		this.zip_mgr = new Io_stream_zip_mgr();
	}
	public Xoh_hzip_mgr Hzip_mgr() {return hzip_mgr;} private final Xoh_hzip_mgr hzip_mgr = new Xoh_hzip_mgr(); 
	public Hzip_stat_itm Stat_itm() {return stat_itm;} private final Hzip_stat_itm stat_itm = new Hzip_stat_itm(); 
	public byte Body_flag() {return body_flag;}
	public void Init_by_db(Xow_wiki wiki) {
		this.Init_by_atrs(wiki.Data__core_mgr().Props().Zip_tid_html(), wiki.Data__core_mgr().Props().Hzip_enabled());
	}
	public void Init_by_atrs(byte default_zip_tid, boolean default_hzip_enabled) {
		this.default_zip_tid = default_zip_tid;
		this.default_hzip_enabled = default_hzip_enabled;
		this.body_flag = default_hzip_enabled ? (byte)(default_zip_tid + Zip_tid_cutoff) : default_zip_tid;
	}
	public byte[] Write(byte[] page_url, byte storage_flag, byte[] src) {
		byte zip_tid = default_zip_tid;
		boolean hzip_enabled = default_hzip_enabled;
		if (storage_flag != Byte_.Max_value_127) {
			zip_tid = storage_flag;
			if (storage_flag > Zip_tid_cutoff) {
				hzip_enabled = Bool_.Y;
				zip_tid -= Zip_tid_cutoff;
			}
		}
		if (hzip_enabled) {
			hzip_mgr.Encode(tmp_bfr, wiki, page_url, src, stat_itm);
			src = tmp_bfr.To_bry_and_rls();
		}
		if (zip_tid != Io_stream_.Tid_raw)
			src = zip_mgr.Zip(zip_tid, src);
		return src;
	}
	public byte[] Parse(byte[] page_url, byte storage_flag, byte[] src) {
		byte zip_tid = default_zip_tid;
		boolean hzip_enabled = default_hzip_enabled;
		if (storage_flag != Byte_.Max_value_127) {
			zip_tid = storage_flag;
			if (storage_flag > Zip_tid_cutoff) {
				hzip_enabled = Bool_.Y;
				zip_tid -= Zip_tid_cutoff;
			}
		}
		if (zip_tid != Io_stream_.Tid_raw)
			src = zip_mgr.Unzip(default_zip_tid, src);
		if (hzip_enabled)
			src = hzip_mgr.Decode(tmp_bfr, wiki, page_url, src);
		return src;
	}
	private static final byte Zip_tid_cutoff = 7;
}
