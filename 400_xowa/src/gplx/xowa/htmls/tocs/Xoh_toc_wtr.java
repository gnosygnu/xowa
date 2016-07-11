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
package gplx.xowa.htmls.tocs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.xowa.htmls.core.wkrs.tocs.*;
public class Xoh_toc_wtr {
	private final    Ordered_hash itms = Ordered_hash_.New_bry();
	private final    Xoh_toc_wkr__lvl lvl_wkr = new Xoh_toc_wkr__lvl();
	private final    Xoh_toc_wkr__txt txt_wkr = new Xoh_toc_wkr__txt();
	private final    Xoh_toc_htmlr htmlr = new Xoh_toc_htmlr();
	private byte[] toc_title, page_name;
	public void Clear() {
		itms.Clear();
		lvl_wkr.Clear();
		txt_wkr.Clear();
		htmlr.Clear();
		this.exists = false;
	}
	public boolean Exists() {return exists && Enabled;} private boolean exists;
	public void Exists_y_() {exists = true;}
	public void Init(byte[] toc_title, byte[] page_name) {
		this.Clear();
		this.toc_title = toc_title;
		this.page_name = page_name;
	}
	public void Add(int hdr_num, byte[] hdr_txt) {
		Xoh_toc_itm itm = new Xoh_toc_itm();
		lvl_wkr.Calc_level(itm, hdr_num);
		txt_wkr.Calc_anch_text(itm, page_name, hdr_txt);
		itms.Add(itm.Anch(), itm);
	}
	public byte[] To_html(boolean toc_mode_is_pgbnr) {return htmlr.To_html(itms, toc_title, toc_mode_is_pgbnr);}
	public byte[] Test__to_html() {
		return htmlr.Test__to_html(itms);
	}
	public static boolean Enabled = true;
}
