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
package gplx.xowa.xtns.graphs;
import gplx.frameworks.tests.GfoTstr;
import gplx.types.basics.utls.BryUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.core.tests.Gfo_test_err_mgr;
import gplx.core.tests.Gfo_test_itm;
import gplx.core.tests.Gfo_test_lnr_base;
import gplx.core.tests.Gfo_test_lnr_itm_cbk;
import gplx.types.basics.utls.BoolUtl;
import gplx.xowa.Xoa_ttl;
import gplx.xowa.Xop_fxt;
import gplx.xowa.files.Xof_file_itm;
import gplx.xowa.parsers.Xop_ctx;
import gplx.xowa.parsers.lnkis.Xop_lnki_tkn;
import gplx.xowa.parsers.lnkis.files.Xop_file_logger;
import gplx.xowa.parsers.lnkis.files.Xop_file_logger_;
import org.junit.Before;
import org.junit.Test;
public class Graph_json_save_mgr__tst {
	private final Graph_json_save_mgr__fxt fxt = new Graph_json_save_mgr__fxt();
	@Before public void init() {fxt.Clear();}
	@Test public void Xowa_file() {
		fxt.Test__parse("a-{XOWA_ROOT}-z", "a-{XOWA_ROOT}{XOWA_ROOT}-z");
	}
	@Test public void Missing_wikirawupload() {
		fxt .Expd__err("missing wikirawupload")
			.Test__parse("a-file:///mem/xowa/-z");
	}
	@Test public void Missing_endquote() {
		fxt .Expd__err("missing endquote")
			.Test__parse("a-\"wikirawupload:file:///mem/xowa/-z");
	}
	@Test public void Invalid_img_src() {
		fxt .Expd__err("invalid file_path")
			.Test__parse("a-\"wikirawupload:file:///mem/xowa/invalid-z\"");
	}
	@Test public void File() {
		fxt .Expd__file(BoolUtl.Y, BoolUtl.Y, "A.png")
			.Test__parse
			( "a-\"wikirawupload:file:///mem/xowa/file/commons.wikimedia.org/orig/7/0/1/c/A.png\"-z"
			, "a-\"wikirawupload:{XOWA_ROOT}/file/commons.wikimedia.org/orig/7/0/1/c/A.png\"-z"
			);
	}
}
class Graph_json_save_mgr__fxt {
	private final Xop_fxt fxt = new Xop_fxt();
	private final Graph_json_save_mgr json_parser;
	private final Gfo_test_lnr_base json_parser_lnr = Gfo_test_lnr_base.New__keys("is_commons", "is_orig", "ttl");
	private final Gfo_test_err_mgr err_mgr = new Gfo_test_err_mgr();
	public Graph_json_save_mgr__fxt() {
		json_parser = new Graph_json_save_mgr(fxt.App().Fsys_mgr());
		json_parser.Test_lnr_(json_parser_lnr);
	}
	public void Clear() {
		json_parser_lnr.Clear();
		err_mgr.Init();
	}
	public Graph_json_save_mgr__fxt Expd__err(String err) {
		err_mgr.Add_expd(true, err);
		return this;
	}
	public Graph_json_save_mgr__fxt Expd__file(boolean is_commons, boolean is_orig, String ttl) {
		json_parser_lnr.Expd().Add(Gfo_test_itm.New__expd().Add("is_commons", is_commons).Add("is_orig", is_orig).Add("ttl", ttl));
		return this;
	}
	public void Test__parse(String src) {Test__parse(src, src);}
	public void Test__parse(String src, String expd) {
		try {
			// init file logger
			Xop_file_logger__mok file_logger = new Xop_file_logger__mok();
			fxt.Ctx().Lnki().File_logger_(file_logger);

			// run it
			byte[] src_as_bry = BryUtl.NewU8(src);
			byte[] actl = json_parser.Save(fxt.Page(), fxt.Ctx(), fxt.Wiki().Domain_bry(), fxt.Page().Ttl().Page_db_as_str(), src_as_bry, 0, src_as_bry.length);

			// verify errs
			err_mgr.Test();

			// verify output
			GfoTstr.Eq(expd, actl);

			// verify files
			Graph_save_mgr_itm_cbk cbk = new Graph_save_mgr_itm_cbk(fxt, file_logger);
			json_parser_lnr.Test(cbk);
		} finally {
			err_mgr.Term();
			fxt.Ctx().Lnki().File_logger_(Xop_file_logger_.Noop);
		}
	}
}
class Graph_save_mgr_itm_cbk implements Gfo_test_lnr_itm_cbk {
	private final Xop_fxt fxt;
	private final Xop_file_logger__mok file_logger;
	public Graph_save_mgr_itm_cbk(Xop_fxt fxt, Xop_file_logger__mok file_logger) {
		this.fxt = fxt;
		this.file_logger = file_logger;
	}
	public void Test_itm(int i, int len, Gfo_test_itm expd_itm, Gfo_test_itm actl_itm) {
		Xof_file_itm file_itm = fxt.Page().File_queue().Get_at(i);
		GfoTstr.Eq(StringUtl.NewU8(file_itm.Lnki_ttl()), actl_itm.Get_str("ttl"));

		Gfo_test_itm file_logger_itm = (Gfo_test_itm)file_logger.Actl().GetAt(i);
		file_logger_itm.Test_bry("ttl_bry", file_itm.Lnki_ttl());
	}
}
class Xop_file_logger__mok extends Gfo_test_lnr_base implements Xop_file_logger {
	public void Log_file(byte caller_tid, Xop_ctx ctx, Xop_lnki_tkn lnki) {
		Log_file(caller_tid, ctx, lnki.Ttl(), lnki.Ns_id(), lnki.Lnki_type(), lnki.W(), lnki.H(), lnki.Upright(), lnki.Time(), lnki.Page());
	}
	public void Log_file(byte caller_tid, Xop_ctx ctx, Xoa_ttl lnki_ttl, int ns_id, byte lnki_type, int lnki_w, int lnki_h, double lnki_upright, double lnki_time, int lnki_page) {
		Gfo_test_itm itm = Gfo_test_itm.New__actl()
			.Add("caller_tid", caller_tid)
			.Add("ttl_bry", lnki_ttl.Page_db())
			.Add("ns_id", ns_id)
			.Add("lnki_type", lnki_type)
			.Add("lnki_w", lnki_w)
			.Add("lnki_h", lnki_h)
			.Add("lnki_upright", lnki_upright)
			.Add("lnki_time", lnki_time)
			.Add("lnki_page", lnki_page);
		this.Actl().Add(itm);
	}
}
