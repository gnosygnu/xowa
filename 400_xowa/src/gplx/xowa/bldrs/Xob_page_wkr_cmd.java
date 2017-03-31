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
package gplx.xowa.bldrs; import gplx.*; import gplx.xowa.*;
import gplx.core.consoles.*; import gplx.core.ios.*;
import gplx.xowa.wikis.nss.*; import gplx.xowa.wikis.data.tbls.*; import gplx.xowa.parsers.tmpls.*;
import gplx.xowa.bldrs.wkrs.*; import gplx.xowa.bldrs.xmls.*; 
public class Xob_page_wkr_cmd implements Xob_cmd {
	private final    Xob_bldr bldr; private final    Xowe_wiki wiki;
	public Xob_page_wkr_cmd(Xob_bldr bldr, Xowe_wiki wiki) {this.bldr = bldr; this.wiki = wiki;}
	public String Cmd_key() {return KEY;} public static final    String KEY = "dump_mgr";
	public Xob_cmd Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki) {return null;}
	public void Cmd_run() {
		Xob_page_wkr[] wkr_ary = (Xob_page_wkr[])wkrs.To_ary(Xob_page_wkr.class); int wkr_ary_len = wkr_ary.length;
		for (int i = 0; i < wkr_ary_len; i++)
			wkr_ary[i].Page_wkr__bgn();
		Io_buffer_rdr fil = Io_buffer_rdr.Null; Xowd_page_itm page = new Xowd_page_itm(); Xow_ns_mgr ns_mgr = wiki.Ns_mgr();
		Xob_xml_parser parser = bldr.Dump_parser().Data_bfr_len_(Io_mgr.Len_mb); 
		long fil_len = 0;
		Gfo_usr_dlg usr_dlg = bldr.App().Usr_dlg();
		try {
			gplx.core.ios.streams.Io_stream_rdr src_rdr = wiki.Import_cfg().Src_rdr();
			fil = Io_buffer_rdr.new_(src_rdr, optRdrBfrSize);
			fil_len = fil.Fil_len();
			if (src_rdr.Tid() == gplx.core.ios.streams.Io_stream_tid_.Tid__bzip2) fil_len = (fil_len * 100) / 18;	// HACK: no way to get actual file progress; assume 18% compression
			// fil.Seek(bldr.Opts().ResumeAt());
			int prv_pos = 0;
			while (true) {
				int cur_pos = parser.Parse_page(page, usr_dlg, fil, fil.Bfr(), prv_pos, ns_mgr); if (cur_pos == Bry_find_.Not_found) break;
				if (cur_pos < prv_pos)
					bldr.Print_prog_msg(fil.Fil_pos(), fil_len, 1, optRdrFillFmt, Int_.To_str_pad_bgn_zero((int)(fil.Fil_pos() / Io_mgr.Len_mb), Int_.DigitCount((int)(fil.Fil_len() / Io_mgr.Len_mb))), "", String_.new_u8(page.Ttl_full_db()));
				prv_pos = cur_pos;
				try {
					for (int i = 0; i < wkr_ary_len; i++)
						wkr_ary[i].Page_wkr__run(page);
				}
				catch (Exception e) {
					Err_.Noop(e);
					long dividend = fil.Fil_pos();
					if (dividend >= fil_len) dividend = fil_len - 1; // prevent % from going over 100
					String msg = Decimal_adp_.CalcPctStr(dividend, fil_len, "00.00") + "|" + String_.new_u8(page.Ttl_full_db()) + "|" + Err_.Message_gplx_log(e);
					bldr.Usr_dlg().Log_wkr().Log_to_session(msg);
					Console_adp__sys.Instance.Write_str_w_nl(msg);
				}
			}
			for (int i = wkr_ary_len - 1; i > -1; --i)	// NOTE: release in reverse order; needed to make sure txns are released correctly
				wkr_ary[i].Page_wkr__run_cleanup();
		}
		catch (Exception e) {
			String msg = Err_.Message_lang(e);
			bldr.Usr_dlg().Log_wkr().Log_to_session(msg);
			Console_adp__sys.Instance.Write_str_w_nl(msg);
			throw Err_.new_exc(e, "xo", "error while reading dump");
		}
		finally {fil.Rls();}
		bldr.Usr_dlg().Prog_none("", "", "reading completed: performing post-processing clean-up");
		for (int i = wkr_ary_len - 1; i > -1; --i)	// NOTE: release in reverse order; needed to make sure txns are released correctly
			wkr_ary[i].Page_wkr__end();
	}
	public void Cmd_bgn(Xob_bldr bldr) {}
	public void Cmd_init(Xob_bldr bldr) {}
	public void Cmd_end() {}
	public void Cmd_term() {}
	public void Wkr_add(Xob_page_wkr wkr) {wkrs.Add(wkr.Page_wkr__key(), wkr);} private Ordered_hash wkrs = Ordered_hash_.New();
	public Xob_page_wkr Wkr_get(String key) {return (Xob_page_wkr)wkrs.Get_by(key);}
	public Xobd_parser Page_parser_assert() {
		if (page_parser == null) {
			page_parser = new Xobd_parser(bldr);
			this.Wkr_add(page_parser);
		}
		return page_parser;
	}	private Xobd_parser page_parser;
	public static Io_url Find_fil_by(Io_url dir, String filter) {
		Io_url[] fil_ary = Io_mgr.Instance.QueryDir_args(dir).FilPath_(filter).ExecAsUrlAry();
		int fil_ary_len = fil_ary.length;
		return fil_ary_len == 0 ? null : fil_ary[fil_ary_len - 1];	// return last
	}
	int optRdrBfrSize = 8 * Io_mgr.Len_mb;
	String optRdrFillFmt = "reading ~{0} MB: ~{1} ~{2}";
	static final String GRP_KEY = "xowa.bldr.rdr";
	public Object Invk(GfsCtx ctx, int ikey, String k, GfoMsg m) {
		throw Err_.new_unimplemented();
	}
}
