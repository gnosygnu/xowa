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
package gplx.xowa.xtns.wdatas.imports; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wdatas.*;
import gplx.ios.*;
import gplx.xowa.bldrs.*;
import gplx.xowa.wikis.data.tbls.*;
class Xob_wbase_json_dump_parser {
	private final Gfo_usr_dlg usr_dlg; private final Xoae_app app; private final Xob_bldr bldr; private final Xowe_wiki wiki;
	private final Xob_wbase_json_dump_db dump_db;
	private final Io_stream_unzip_mgr unzip_mgr;
	public Xob_wbase_json_dump_parser(Xob_bldr bldr, Xowe_wiki wiki) {
		this.bldr = bldr; this.wiki = wiki;
		this.app = bldr.App(); this.usr_dlg = app.Usr_dlg();
		this.dump_db = new Xob_wbase_json_dump_db(bldr, wiki);
		this.unzip_mgr = new Io_stream_unzip_mgr(app.Setup_mgr().Dump_mgr().Import_bz2_by_stdout(), app.Prog_mgr().App_decompress_bz2_by_stdout(), String_.Ary(".bz2", ".gz", ".zip"));
	}
	public void Parse(Io_url src_fil) {
		byte[] json_bgn = Bry_.new_a7("[\n"), id_bgn = Bry_.new_a7("{\"id\":");
		String prog_fmt = "reading ~{0} MB: ~{1} ~{2}";
		Io_stream_rdr stream_rdr = Io_stream_rdr_mgr.Get_rdr_or_null(src_fil, wiki.Fsys_mgr().Root_dir(), unzip_mgr, "*wikidata-*-all.json", "*wikidata-*-all.json.gz");
		if (stream_rdr == null) {usr_dlg.Warn_many("", "", "wbase.import:file not found: src_dir=~{0}", wiki.Fsys_mgr().Root_dir()); return;}
		Io_buffer_rdr buffer_rdr = Io_buffer_rdr.new_(stream_rdr, 10 * Io_mgr.Len_mb); long buffer_rdr_len = buffer_rdr.Fil_len();
		try {
			Io_url stream_rdr_url = stream_rdr.Url();
			int page_bgn = Bry_find_.Find_fwd(buffer_rdr.Bfr(), id_bgn);
			if (page_bgn == Bry_find_.Not_found)						{usr_dlg.Warn_many("", "", "wbase.import:initial id not found: url=~{0}", stream_rdr_url.Raw()); return;}
			if (!Bry_.Match(buffer_rdr.Bfr(), 0, page_bgn, json_bgn))	{usr_dlg.Warn_many("", "", "wbase.import:doc_bgn is not '[\n': url=~{0}", stream_rdr_url.Raw()); return;}
			Xowd_page_itm page = new Xowd_page_itm();
			dump_db.Parse_bgn(stream_rdr.Len(), stream_rdr.Url().NameAndExt());
			while (true) {
				int cur_pos = Extract_page(page, buffer_rdr, page_bgn);
				if (cur_pos == -1) break;
				if (cur_pos < page_bgn)
					bldr.Print_prog_msg(buffer_rdr.Fil_pos(), buffer_rdr_len, 1, prog_fmt, Int_.To_str_pad_bgn_zero((int)(buffer_rdr.Fil_pos() / Io_mgr.Len_mb), Int_.DigitCount((int)(buffer_rdr.Fil_len() / Io_mgr.Len_mb))), "", page.Ttl_page_db());
				page_bgn = cur_pos;
			}
			dump_db.Parse_end();
		}
		catch (Exception e) {
			String msg = usr_dlg.Warn_many("", "", "dump_rdr:error while reading; url=~{0} err=~{1}", src_fil.Raw(), Err_.Message_lang(e));
			throw Err_.new_wo_type(msg);
		}
		finally {buffer_rdr.Rls();}
	}
	private int Extract_page(Xowd_page_itm page, Io_buffer_rdr rdr, int page_bgn) {
		int pos = page_bgn;
		byte[] bry = rdr.Bfr();
		int bry_len = rdr.Bfr_len();
		while (true) {
			if (pos == bry_len) {
				rdr.Bfr_load_from(page_bgn);	// refill src from pos; 
				bry_len = rdr.Bfr_len();
				pos -= page_bgn;
				page_bgn = 0;
			}
			byte b = Byte_.Zero;
			boolean exit = false;
			if (pos < bry_len)
				b = bry[pos];
			else {
				b = Byte_ascii.Nl;
				pos = bry_len;
				exit = true;
			}
			if (b == Byte_ascii.Nl) {
				byte[] json_bry = Bry_.Mid(bry, page_bgn, pos);
				if (json_bry.length == 1 && json_bry[0] == Byte_ascii.Brack_end) return -1;
				if (exit) return -1;
				dump_db.Parse_cmd(json_bry);
				return pos + 1;
			}
			++pos;
		}
	}
}
