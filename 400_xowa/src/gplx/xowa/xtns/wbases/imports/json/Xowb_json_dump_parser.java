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
package gplx.xowa.xtns.wbases.imports.json;
import gplx.libs.dlgs.Gfo_usr_dlg;
import gplx.core.ios.Io_buffer_rdr;
import gplx.core.ios.streams.Io_stream_rdr;
import gplx.libs.ios.IoConsts;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.IntUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.xowa.Xoae_app;
import gplx.xowa.Xowe_wiki;
import gplx.xowa.bldrs.Xob_bldr;
import gplx.xowa.wikis.data.tbls.Xowd_page_itm;
class Xowb_json_dump_parser {
	private final Xob_bldr bldr; private final Xowe_wiki wiki;
	public Xowb_json_dump_parser(Xob_bldr bldr, Xowe_wiki wiki) {
		this.bldr = bldr; this.wiki = wiki;
	}
	public void Parse(Io_url json_dump_file) {
		// init
		Xoae_app app = bldr.App(); Gfo_usr_dlg usr_dlg = app.Usr_dlg(); 
		Xowb_json_dump_db dump_db = new Xowb_json_dump_db(bldr, wiki);
		Io_stream_unzip_mgr unzip_mgr = new Io_stream_unzip_mgr(gplx.xowa.bldrs.installs.Xoi_dump_mgr.Import_bz2_by_stdout(app), app.Prog_mgr().App_decompress_bz2_by_stdout(), StringUtl.Ary(".bz2", ".gz", ".zip"));

		// open buffer from file
		Io_stream_rdr stream = Io_stream_rdr_mgr.Get_rdr_or_null(json_dump_file, wiki.Fsys_mgr().Root_dir(), unzip_mgr, "*wikidata-*-all.json", "*wikidata-*-all.json.gz");
		if (stream == null) {usr_dlg.Warn_many("", "", "wbase.import:file not found: src_dir=~{0}", wiki.Fsys_mgr().Root_dir()); return;}
		Io_buffer_rdr buffer = Io_buffer_rdr.new_(stream, 10 * IoConsts.LenMB);

		try {
			// set page_bgn
			if (!BryLni.Eq(buffer.Bfr(), 0, 3, BryUtl.NewA7("[\n{")))		{usr_dlg.Warn_many("", "", "wbase.import:doc_bgn is not '[\n': url=~{0}", stream.Url().Raw()); return;}	// validate file; if schema ever changes this will fail
			int page_bgn = 2;	// 2="[\n"

			// read file and create pages for each json item
			dump_db.Parse_all_bgn(stream.Len(), stream.Url().NameAndExt());
			Xowd_page_itm page = new Xowd_page_itm();
			while (true) {
				int cur_pos = Parse_doc(dump_db, buffer, page, page_bgn);
				if (cur_pos == -1) break;
				if (cur_pos < page_bgn)
					bldr.Print_prog_msg(buffer.Fil_pos(), buffer.Fil_len(), 1, "reading ~{0} MB: ~{1} ~{2}", IntUtl.ToStrPadBgnZero((int)(buffer.Fil_pos() / IoConsts.LenMB), IntUtl.CountDigits((int)(buffer.Fil_len() / IoConsts.LenMB))), "", page.Ttl_page_db());
				page_bgn = cur_pos;
			}
			dump_db.Parse_all_end();
		}
		catch (Exception e) {
			String msg = usr_dlg.Warn_many("", "", "dump_rdr:error while reading; url=~{0} err=~{1}", json_dump_file.Raw(), ErrUtl.Message(e));
			throw ErrUtl.NewArgs(msg);
		}
		finally {buffer.Rls();}
	}
	private int Parse_doc(Xowb_json_dump_db dump_db, Io_buffer_rdr rdr, Xowd_page_itm page, int page_bgn) {
		// init
		int pos = page_bgn;
		byte[] bry = rdr.Bfr();
		int bry_len = rdr.Bfr_len();

		while (true) {// loop 1 byte at a time until nl
			if (pos == bry_len) {	// refill if at end of 10 MB bfr
				rdr.Bfr_load_from(page_bgn);
				bry_len = rdr.Bfr_len();
				pos -= page_bgn;
				page_bgn = 0;
			}

			// read byte; parse if nl; otherwise move to next byte
			byte b = bry[pos];	// NOTE: should never be out of bounds b/c json doc will end with "]\n"
			if (b == AsciiByte.Nl) {
				if (pos - page_bgn == 1 && bry[page_bgn] == AsciiByte.BrackEnd)	// EOF; note that json dump ends with "]\n"
					return -1;
				dump_db.Parse_doc(BryLni.Mid(bry, page_bgn, pos));
				return pos + 1;
			}
			else
				++pos;
		}
	}
}
