/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2017 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.xowa.htmls.modules.popups; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.modules.*;
import gplx.xowa.apps.apis.xowa.html.modules.*;
import gplx.xowa.parsers.*;
public class Xow_popup_parser_data {
	public int Tmpl_max() {return tmpl_max;} private int tmpl_max;
	public int Words_needed_val() {return words_needed_val;} private int words_needed_val;
	public int Words_needed_max() {return words_needed_max;} private int words_needed_max;
	private int words_needed_min;
	public int Words_found() {return words_found;} private int words_found;
	public Bry_bfr Wrdx_bfr() {return wrdx_bfr;} private Bry_bfr wrdx_bfr = Bry_bfr_.Reset(255);
	public Xow_popup_word[] Words_found_ary() {return (Xow_popup_word[])words_found_list.To_ary_and_clear(Xow_popup_word.class);} private List_adp words_found_list = List_adp_.New();
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
				tmpl_max = Int_.Max_value;
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
