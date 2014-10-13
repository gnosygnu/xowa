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
package gplx.xowa.html.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.html.*; import gplx.xowa.html.modules.*;
import gplx.xowa.apis.xowa.html.modules.*;
public class Xow_popup_parser_data {
	public int Tmpl_max() {return tmpl_max;} private int tmpl_max;
	public int Words_needed_val() {return words_needed_val;} private int words_needed_val;
	public int Words_needed_max() {return words_needed_max;} private int words_needed_max;
	private int words_needed_min;
	public int Words_found() {return words_found;} private int words_found;
	public Bry_bfr Wrdx_bfr() {return wrdx_bfr;} private Bry_bfr wrdx_bfr = Bry_bfr.reset_(255);
	public Xow_popup_word[] Words_found_ary() {return (Xow_popup_word[])words_found_list.Xto_ary_and_clear(Xow_popup_word.class);} private ListAdp words_found_list = ListAdp_.new_();
	public int Tmpl_loop_count() {return tmpl_loop_count;} private int tmpl_loop_count;
	public void Tmpl_loop_count_add() {++tmpl_loop_count;}
	private Xow_popup_itm popup_itm;
	public boolean Html_restricted() {return html_restricted;} private boolean html_restricted;
	public void Init(Xow_popup_cfg cfg, Xow_popup_itm popup_itm, int tmpl_len) {
		words_found = tmpl_loop_count= 0;
		words_found_list.Clear();
		wrdx_bfr.Clear();

		html_restricted = !gplx.xowa.specials.xowa.popup_history.Popup_history_page.Ttl_chk(popup_itm.Page_ttl());
		this.popup_itm = popup_itm;
		if (tmpl_len < cfg.Show_all_if_less_than()) popup_itm.Mode_all_();
		words_needed_min = popup_itm.Words_found();
		words_needed_val = words_needed_max = popup_itm.Words_needed();			
		switch (popup_itm.Mode()) {
			case Xow_popup_itm.Mode_tid_all:
				tmpl_max = Int_.MaxValue;
				break;
			case Xow_popup_itm.Mode_tid_init:
			case Xow_popup_itm.Mode_tid_more:
				tmpl_max = cfg.Tmpl_read_max();
				if (cfg.Read_til_stop_fwd() > 0)
					words_needed_max += cfg.Read_til_stop_fwd();
				break;
		}
	}
	public boolean Words_needed_chk() {return words_found < words_needed_max;}
	public void Words_found_add(Xop_tkn_itm tkn) {
		words_found_list.Add(new Xow_popup_word(tkn.Tkn_tid(), wrdx_bfr.Len(), words_found, tkn.Src_bgn(), tkn.Src_end(), tkn));
		++words_found;
	}
	public boolean Stop_if_hdr_after_chk(Xow_popup_cfg cfg) {
		boolean rv = words_found > words_needed_min + cfg.Stop_if_hdr_after() && !popup_itm.Mode_all();
		if (rv) words_needed_max = words_found;
		return rv;
	}
}
