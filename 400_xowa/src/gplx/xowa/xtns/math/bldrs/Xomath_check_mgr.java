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
package gplx.xowa.xtns.math.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*;
import gplx.dbs.*;
import gplx.xowa.bldrs.*; import gplx.xowa.parsers.logs.*;
import gplx.xowa.xtns.math.texvcs.*; import gplx.xowa.xtns.math.texvcs.tkns.*;
class Xomath_check_mgr {
	private int count_total, count_xnde_invalid, count_xnde_w_atrs, count_texvc_invalid, count_texvc_adjusted;
	public void Exec(Xowe_wiki wiki) {
		// get db, conn, rdr
		Xob_db_file log_db = Xob_db_file.New__file_make(wiki.Fsys_mgr().Root_dir());
		Db_conn log_conn = log_db.Conn();
		Xop_log_basic_tbl log_tbl = new Xop_log_basic_tbl(log_conn);
		Db_rdr rdr = log_conn.Stmt_select_all(log_tbl.Tbl_name(), log_tbl.flds).Exec_select__rls_auto();

		// loop 
		try {
			while (rdr.Move_next()) {
				// get page_id, src
				int page_id = rdr.Read_int(log_tbl.fld__page_id);
				byte[] src = rdr.Read_bry_by_str(log_tbl.fld__src_str);
				count_total++;

				src = Assert_flanking_math_ndes(page_id, src);
				if (src == null) {
					count_xnde_invalid++;
					continue;
				}
				Check_texvc(page_id, src);
			}
		} finally {rdr.Rls();}
		Gfo_usr_dlg_.Instance.Note_many("", "", "done: total=~{0}, xnde_invalid=~{1} xnde_w_atrs=~{2} texvc_invalid=~{3} texvc_adjusted=~{4}"
		, count_total, count_xnde_invalid, count_xnde_w_atrs, count_texvc_invalid, count_texvc_adjusted);
	}

	private final    byte[] math_lhs_bry = Bry_.new_a7("<math"), math_rhs_bry = Bry_.new_a7("</math>"), nl_repl_bry = Bry_.new_a7("    ");
	private byte[] Assert_flanking_math_ndes(int page_id, byte[] src) {
		// assert "<math" at start
		if (!Bry_.Has_at_bgn(src, math_lhs_bry)) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "math.check:xnde_invalid b/c '<math' is not at beginning; page_id=~{0}, src=~{1}", page_id, Bry_.Replace(src, Byte_ascii.Nl_bry, nl_repl_bry));
			return null;
		}

		// assert "</math>" at end
		if (!Bry_.Has_at_end(src, math_rhs_bry)) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "math.check:xnde_invalid b/c '</math>' is not at end; page_id=~{0}, src=~{1}", page_id, Bry_.Replace(src, Byte_ascii.Nl_bry, nl_repl_bry));
			return null;
		}

		// assert "<math>"
		int math_lhs_end = Bry_find_.Find_fwd(src, Byte_ascii.Gt_bry, math_lhs_bry.length);
		if (math_lhs_end != math_lhs_bry.length) {
			count_xnde_w_atrs++;
			Gfo_usr_dlg_.Instance.Log_many("", "", "math.check:xnde_w_atrs; page_id=~{0}, src=~{1}", page_id, Bry_.Replace(src, Byte_ascii.Nl_bry, nl_repl_bry));
		}

		// remove "<math>", "</math>"
		return Bry_.Mid(src, math_lhs_end + 1, src.length - math_rhs_bry.length);
	}

	private final    Texvc_parser parser = new Texvc_parser();
	private final    Texvc_checker checker = new Texvc_checker();
	private final    Texvc_ctx ctx = new Texvc_ctx();
	private final    Bry_bfr tmp_bfr = Bry_bfr_.New();
	private void Check_texvc(int page_id, byte[] src) {
		try {
			Texvc_root root = new Texvc_root();
			ctx.Clear();
			parser.Parse(ctx, root, src);
			checker.Check(src, root);
			tmp_bfr.Clear();
			root.Print_tex_bry(tmp_bfr, src, 0);
			byte[] texvc_bry = tmp_bfr.To_bry_and_clear();
			if (!Bry_.Eq(src, texvc_bry)) {
				count_texvc_adjusted++;
				Gfo_usr_dlg_.Instance.Log_many("", "", "math.check:texvc_adjusted; page_id=~{0}, src=~{1} texvc=~{2}", page_id, Bry_.Replace(src, Byte_ascii.Nl_bry, nl_repl_bry), Bry_.Replace(texvc_bry, Byte_ascii.Nl_bry, nl_repl_bry));
			}
		} catch (Exception exc) {
			count_texvc_invalid++;
			Gfo_usr_dlg_.Instance.Warn_many("", "", "math.check:texvc_invalid; page_id=~{0}, src=~{1} err=~{2}", page_id, Bry_.Replace(src, Byte_ascii.Nl_bry, nl_repl_bry), Err_.Message_gplx_log(exc));
		}
	}
}
