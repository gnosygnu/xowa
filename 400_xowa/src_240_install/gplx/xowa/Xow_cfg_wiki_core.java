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
public class Xow_cfg_wiki_core {
	public Xow_cfg_wiki_core(Xowe_wiki wiki) {this.wiki = wiki;} private Xowe_wiki wiki;
	public void Save() {
	}
	public byte[] Build_gfs() {
		Gfs_bldr wtr = new Gfs_bldr();
		Xow_wiki_props props = wiki.Props();
		wtr.Add_proc_init_many(Xowe_wiki.Invk_props).Add_nl();
		wtr.Add_proc_cont_one(Xow_wiki_props.Invk_bldr_version_).Add_parens_str(props.Bldr_version()).Add_nl();
		wtr.Add_proc_cont_one(Xow_wiki_props.Invk_main_page_).Add_parens_str(props.Main_page()).Add_nl();
		wtr.Add_proc_cont_one(Xow_wiki_props.Invk_siteinfo_misc_).Add_parens_str(props.Siteinfo_misc()).Add_nl();
		wtr.Add_proc_cont_one(Xow_wiki_props.Invk_siteinfo_mainpage_).Add_parens_str(props.Siteinfo_mainpage()).Add_nl();
		wtr.Add_term_nl();
		wtr.Add_proc_init_many(Xowe_wiki.Invk_ns_mgr).Add_nl();
		wtr.Add_proc_cont_one(Xow_ns_mgr.Invk_clear).Add_nl();
		wtr.Add_proc_cont_one(Xow_ns_mgr.Invk_load).Add_paren_bgn().Add_nl();
		wtr.Add_quote_xtn_bgn();
		Xol_csv_parser csv_parser = Xol_csv_parser._;
		int nms_len = wiki.Ns_mgr().Count();
		for (int i = 0; i < nms_len; i++) {
			Xow_ns ns = wiki.Ns_mgr().Ords_get_at(i);
			wtr.Bfr().Add_int_variable(ns.Id()).Add_byte_pipe().Add_int_fixed(ns.Case_match(), 1).Add_byte_pipe();
			csv_parser.Save(wtr.Bfr(), ns.Name_txt());
			wtr.Add_nl();
		}
		wtr.Add_quote_xtn_end();
		wtr.Add_paren_end().Add_term_nl();
		return wtr.Xto_bry();
	}
	public void Load(String raw) {
		wiki.Appe().Gfs_mgr().Run_str_for(wiki, raw);
	}
	public static void Load_ns_(Xow_ns_mgr ns_mgr, byte[] src) {// 10|1|Template
		int len = src.length; int pos = 0, fld_bgn = 0, fld_idx = 0, row_bgn = 0;
		int cur_id = Int_.MinValue; byte cur_case_match = Byte_.Max_value_127; byte[] cur_name = Bry_.Empty;
		Xol_csv_parser csv_parser = Xol_csv_parser._;
		while (true) {
			boolean last = pos == len;
			byte b = last ? Byte_ascii.NewLine : src[pos];
			switch (b) {
				case Byte_ascii.Pipe:
					switch (fld_idx) {
						case 0:		cur_id = Bry_.Xto_int_or(src, fld_bgn, pos, Int_.MinValue);					if (cur_id == Int_.MinValue)		throw Err_mgr._.fmt_(GRP_KEY, "fail.load.id", "failed to load id: ~{0}", String_.new_u8(src, fld_bgn, pos)); break;
						case 1:		cur_case_match = Bry_.Xto_byte_by_int(src, fld_bgn, pos, Byte_.Max_value_127);	if (cur_id == Byte_.Max_value_127)	throw Err_mgr._.fmt_(GRP_KEY, "fail.load.case_match", "failed to load match: ~{0}", String_.new_u8(src, fld_bgn, pos)); break;
						default:	throw Err_.unhandled(fld_idx);
					}
					fld_bgn = pos + 1;
					++fld_idx;
					break;
				case Byte_ascii.NewLine:
					if (pos > row_bgn) {	// guard against blank lines
						cur_name = csv_parser.Load(src, fld_bgn, pos);
						ns_mgr.Add_new(cur_id, cur_name, cur_case_match, false);
					}
					cur_id = Int_.MinValue; cur_case_match = Byte_.Max_value_127;
					fld_bgn = pos + 1;
					fld_idx = 0;
					row_bgn = fld_bgn;
					break;
			}
			if (last) break;
			++pos;
		}
		ns_mgr.Init_w_defaults();
	}
	static final String Url_wiki_core_gfs = "wiki_core.gfs";
	static final String GRP_KEY = "xowa.wiki.cfg.wiki_core";
}
