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
import gplx.ios.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.xmls.*; import gplx.xowa.wikis.data.tbls.*;
public class Xobd_rdr implements Xob_cmd {
	private Xob_bldr bldr; private Xowe_wiki wiki;
	public Xobd_rdr(Xob_bldr bldr, Xowe_wiki wiki) {this.bldr = bldr; this.wiki = wiki;}
	public String Cmd_key() {return KEY;} public static final String KEY = "dump_mgr";
	public void Cmd_init(Xob_bldr bldr) {
		Xobd_wkr[] wkr_ary = (Xobd_wkr[])wkrs.To_ary(Xobd_wkr.class); int wkr_ary_len = wkr_ary.length;
		for (int i = 0; i < wkr_ary_len; i++)
			wkr_ary[i].Wkr_ini(bldr);			
	}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_run() {
		Xobd_wkr[] wkr_ary = (Xobd_wkr[])wkrs.To_ary(Xobd_wkr.class); int wkr_ary_len = wkr_ary.length;
		for (int i = 0; i < wkr_ary_len; i++)
			wkr_ary[i].Wkr_bgn(bldr);
		Io_buffer_rdr fil = Io_buffer_rdr.Null; Xowd_page_itm page = new Xowd_page_itm(); Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		Xob_xml_parser parser = bldr.Dump_parser().Data_bfr_len_(Io_mgr.Len_mb); 
		long fil_len = 0;
		Gfo_usr_dlg usr_dlg = bldr.App().Usr_dlg();
		try {
			gplx.ios.Io_stream_rdr src_rdr = wiki.Import_cfg().Src_rdr();
			fil = Io_buffer_rdr.new_(src_rdr, optRdrBfrSize);
			fil_len = fil.Fil_len();
			if (src_rdr.Tid() == gplx.ios.Io_stream_.Tid_bzip2) fil_len = (fil_len * 100) / 18;	// HACK: no way to get actual file progress; assume 18% compression
			// fil.Seek(bldr.Opts().ResumeAt());
			int prv_pos = 0;
			while (true) {
				int cur_pos = parser.Parse_page(page, usr_dlg, fil, fil.Bfr(), prv_pos, ns_mgr); if (cur_pos == Bry_.NotFound) break;
				if (cur_pos < prv_pos)
					bldr.Print_prog_msg(fil.Fil_pos(), fil_len, 1, optRdrFillFmt, Int_.Xto_str_pad_bgn_zero((int)(fil.Fil_pos() / Io_mgr.Len_mb), Int_.DigitCount((int)(fil.Fil_len() / Io_mgr.Len_mb))), "", String_.new_u8(page.Ttl_full_db()));
				prv_pos = cur_pos;
				try {
					for (int i = 0; i < wkr_ary_len; i++)
						wkr_ary[i].Wkr_run(page);
				}
				catch (Exception e) {
					Exc_.Noop(e);
					long dividend = fil.Fil_pos();
					if (dividend >= fil_len) dividend = fil_len - 1; // prevent % from going over 100
					String msg = DecimalAdp_.CalcPctStr(dividend, fil_len, "00.00") + "|" + String_.new_u8(page.Ttl_full_db()) + "|\n" + Err_.Message_lang(e)  + "|" + Xot_tmpl_wtr.Err_string; Xot_tmpl_wtr.Err_string = "";
					bldr.Usr_dlg().Log_wkr().Log_to_session(msg);
					ConsoleAdp._.WriteLine(msg);
				}
			}
		}
		catch (Exception e) {
			String msg = Err_.Message_lang(e);
			bldr.Usr_dlg().Log_wkr().Log_to_session(msg);
			ConsoleAdp._.WriteLine(msg);
			throw Exc_.new_exc(e, "xo", "error while reading dump");
		}
		finally {fil.Rls();}
		bldr.Usr_dlg().Prog_none("", "", "reading completed: performing post-processing clean-up");
		for (int i = 0; i < wkr_ary_len; i++)
			wkr_ary[i].Wkr_end();
	}
	public void Cmd_term() {
		Xobd_wkr[] wkr_ary = (Xobd_wkr[])wkrs.To_ary(Xobd_wkr.class); int wkr_ary_len = wkr_ary.length;
		for (int i = 0; i < wkr_ary_len; i++)
			wkr_ary[i].Wkr_print();
	}
	public void Wkr_add(Xobd_wkr wkr) {wkrs.Add(wkr.Wkr_key(), wkr);} private Ordered_hash wkrs = Ordered_hash_.new_();
	public Xobd_wkr Wkr_get(String key) {return (Xobd_wkr)wkrs.Get_by(key);}
	public Xobd_parser Page_parser_assert() {
		if (page_parser == null) {
			page_parser = new Xobd_parser();
			this.Wkr_add(page_parser);
		}
		return page_parser;
	}	private Xobd_parser page_parser;
	public static Io_url Find_fil_by(Io_url dir, String filter) {
		Io_url[] fil_ary = Io_mgr.I.QueryDir_args(dir).FilPath_(filter).ExecAsUrlAry();
		int fil_ary_len = fil_ary.length;
		return fil_ary_len == 0 ? null : fil_ary[fil_ary_len - 1];	// return last
	}
	int optRdrBfrSize = 8 * Io_mgr.Len_mb;
	String optRdrFillFmt = "reading ~{0} MB: ~{1} ~{2}";
	static final String GRP_KEY = "xowa.bldr.rdr";
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		throw Exc_.new_unimplemented();
	}
}
