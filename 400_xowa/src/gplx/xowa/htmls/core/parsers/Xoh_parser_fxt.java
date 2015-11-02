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
package gplx.xowa.htmls.core.parsers; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.langs.htmls.parsers.*;
public class Xoh_parser_fxt {
	private final Html_doc_parser parser;
	private final Xoh_wkr__test actl_wkr = new Xoh_wkr__test(), expd_wkr = new Xoh_wkr__test();
	public Xoh_parser_fxt() {
		Xoae_app app = Xoa_app_fxt.app_();
		this.wiki = Xoa_app_fxt.wiki_tst_(app);
		parser = Xoh_parser_.new_(actl_wkr, wiki);
	}
	public Xow_wiki Wiki() {return wiki;} private Xow_wiki wiki;
	public void Test__parse(String src_str) {
		byte[] src_bry = Bry_.new_u8(src_str);
		parser.Parse(src_bry, 0, src_bry.length);
		Tfds.Eq_ary_str(expd_wkr.To_ary(), actl_wkr.To_ary());
	}
	public void Init__hdr(int rng_bgn, int rng_end, int level, String id, String caption, boolean id_caption_related) {
		expd_wkr.On_hdr(rng_bgn, rng_end, level, Bry_.new_u8(id), Bry_.new_u8(caption), id_caption_related);
	}
	public void Init__lnke(int rng_bgn, int rng_end, byte lnke_type, int autonumber_id, String href) {
		expd_wkr.On_lnke(rng_bgn, rng_end, lnke_type, autonumber_id, Bry_.new_u8(href));
	}
	public void Init__lnki(int rng_bgn, int rng_end, byte lnki_type, int site_bgn, int site_end, String page_str, String capt_str, String trail_str) {
		expd_wkr.On_lnki(rng_bgn, rng_end, lnki_type, site_bgn, site_end, Bry_.new_u8(page_str), Bry_.new_u8(capt_str), Bry_.new_u8(trail_str));
	}
	public void Init__escape(int rng_bgn, int rng_end) {
		expd_wkr.On_escape(rng_bgn, rng_end);
	}
	public void Init__space(int rng_bgn, int rng_end) {
		expd_wkr.On_space(rng_bgn, rng_end);
	}
	public void Init__txt(int rng_bgn, int rng_end) {
		expd_wkr.On_txt(rng_bgn, rng_end);
	}
}
class Xoh_wkr__test implements Xoh_wkr {
	private final List_adp list = List_adp_.new_();
	private final Bry_bfr bfr = Bry_bfr.new_();
	public void On_hdr(int rng_bgn, int rng_end, int level, byte[] id, byte[] caption, boolean id_caption_related) {
		bfr.Add_int_variable(rng_bgn).Add_byte_pipe();
		bfr.Add_int_variable(rng_end).Add_byte_pipe();
		bfr.Add_int_variable(level).Add_byte_pipe();
		bfr.Add_safe(id).Add_byte_pipe();
		bfr.Add(caption).Add_byte_pipe();
		bfr.Add_bool(id_caption_related);
		list.Add(bfr.To_str_and_clear());
	}
	public void On_lnke(int rng_bgn, int rng_end, byte lnke_type, int autonumber_id, byte[] href) {
		bfr.Add_int_variable(rng_bgn).Add_byte_pipe();
		bfr.Add_int_variable(rng_end).Add_byte_pipe();
		bfr.Add_int_variable(lnke_type).Add_byte_pipe();
		bfr.Add_int_variable(autonumber_id).Add_byte_pipe();
		bfr.Add(href);
		list.Add(bfr.To_str_and_clear());
	}
	public void On_lnki(int rng_bgn, int rng_end, byte lnki_type, int site_bgn, int site_end, byte[] page_bry, byte[] capt_bry, byte[] trail_bry) {
		bfr.Add_int_variable(rng_bgn).Add_byte_pipe();
		bfr.Add_int_variable(rng_end).Add_byte_pipe();
		bfr.Add_byte(lnki_type).Add_byte_pipe();
		bfr.Add_int_variable(site_bgn).Add_byte_pipe();
		bfr.Add_int_variable(site_end).Add_byte_pipe();
		bfr.Add_safe(page_bry).Add_byte_pipe();
		bfr.Add_safe(capt_bry).Add_byte_pipe();
		bfr.Add_safe(trail_bry).Add_byte_pipe();
		list.Add(bfr.To_str_and_clear());
	}
	public void On_escape(int rng_bgn, int rng_end) {
		bfr.Add_int_variable(rng_bgn).Add_byte_pipe();
		bfr.Add_int_variable(rng_end).Add_byte_pipe();
		list.Add(bfr.To_str_and_clear());
	}
	public void On_space(int rng_bgn, int rng_end) {
		bfr.Add_int_variable(rng_bgn).Add_byte_pipe();
		bfr.Add_int_variable(rng_end).Add_byte_pipe();
		list.Add(bfr.To_str_and_clear());
	}
	public void On_txt(int rng_bgn, int rng_end) {
		bfr.Add_int_variable(rng_bgn).Add_byte_pipe();
		bfr.Add_int_variable(rng_end).Add_byte_pipe();
		list.Add(bfr.To_str_and_clear());
	}
	public String[] To_ary() {return list.To_str_ary_and_clear();}
}
