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
package gplx.xowa.xtns.math; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*;
import gplx.core.envs.*;
public class Xomath_latex_bldr {
	public static void Async(Xoae_app app, Xoae_page page, gplx.xowa.guis.cbks.js.Xog_js_wkr js_wkr) {
		// get len; if 0, exit
		Gfo_usr_dlg usr_dlg = app.Usr_dlg();
		int len = page.File_math().Count();
		if (len == 0) return;

		usr_dlg.Prog_one("", "", "page.async.math; count=~{0}", len);
		// loop each item
		for (int i = 0; i < len; ++i) {
			// if canceled, exit
			if (usr_dlg.Canceled()) {
				usr_dlg.Prog_none("", "", "");
				app.Log_wtr().Queue_enabled_(false);
				return;
			}

			try {
				// get itm and generate png
				Xomath_latex_itm itm = (Xomath_latex_itm)page.File_math().Get_at(i);
				String queue_msg = usr_dlg.Prog_many("", "", "generating math ~{0} of ~{1}: ~{2}", i + List_adp_.Base1, len, itm.Src());
				Generate_png(app, itm.Src(), itm.Md5(), itm.Png(), queue_msg);

				// get png size; update <img> src; delete <span>
				gplx.gfui.SizeAdp size = app.File_mgr().Img_mgr().Wkr_query_img_size().Exec(itm.Png());
				js_wkr.Html_img_update("xowa_math_img_" + itm.Uid(), itm.Png().To_http_file_str(), size.Width(), size.Height());
				js_wkr.Html_elem_delete("xowa_math_txt_" + itm.Uid());
			}
			catch (Exception e) {
				usr_dlg.Warn_many("", "", "page.async.math: page=~{0} err=~{1}", page.Ttl().Raw(), Err_.Message_gplx_log(e));
			}
		}
		page.File_math().Clear();
	}
	private static void Generate_png(Xoae_app app, byte[] math, byte[] hash, Io_url png_url, String prog_fmt) {
		// init
		Io_url tmp_dir = app.Usere().Fsys_mgr().App_temp_dir().GenSubDir("math");
		Io_url tex_fil = tmp_dir.GenSubFil("xowa_math_temp.tex");

		// make tex_bry
		byte[] tex_doc = Bry_.Replace(math, Bry_.new_a7("\n\n"), Byte_ascii.Nl_bry);	// remove completely blank lines; not sure if this is right; PAGE:en.w:Standard Model_(mathematical_formulation); EX:<math>(\mathbf{1},\mathbf\n\n{1},0)</math>
		tex_doc = fmt__latex_doc.Bld_many_to_bry(Bry_bfr_.New(), tex_doc);
		Io_mgr.Instance.SaveFilBry(tex_fil, tex_doc);

		// run tex to dvi
		Process_adp tex_to_dvi_cmd = app.Prog_mgr().App__tex_to_dvi();
		prog_fmt = String_.Replace(prog_fmt, "~", "~~");	// double-up ~ or else will break in progress bar
		tex_to_dvi_cmd.Prog_fmt_(prog_fmt + " tex_to_dvi: ~{process_seconds} second(s); ~{process_exe_name} ~{process_exe_args}");
		boolean pass = tex_to_dvi_cmd.Run(tex_fil.Raw(), tmp_dir.Xto_api()).Exit_code_pass();
		if (!pass)
			app.Usr_dlg().Warn_many("", "tex_to_dvi.fail", "fail: tex_to_dvi: error=~{0} latex=~{1}", tex_to_dvi_cmd.Rslt_out(), tex_doc);

		// run dvi to png; NOTE: latex sometimes throws errors, but will generate .dvi; for sake of simplicity; always try to run dvipng
		Io_mgr.Instance.CreateDirIfAbsent(png_url.OwnerDir());
		Process_adp dvi_to_png_cmd = app.Prog_mgr().App__dvi_to_png();
		dvi_to_png_cmd.Prog_fmt_(prog_fmt + " dvi_to_png: ~{process_seconds} second(s); ~{process_exe_name} ~{process_exe_args}");
		pass = dvi_to_png_cmd.Run(tex_fil.GenNewExt(".dvi"), png_url, tmp_dir.Xto_api()).Exit_code_pass();
		if (!pass)
			app.Usr_dlg().Warn_many("", "dvi_to_png.fail", "fail: dvi_to_png: error=~{0} latex=~{1}", dvi_to_png_cmd.Rslt_out(), tex_doc);
	}
	private static final    Bry_fmt fmt__latex_doc = Bry_fmt.Auto(Bry_.new_a7(String_.Concat_lines_nl_skip_last
	( "\\documentclass{article}"
	, "\\usepackage{amsmath}"
	, "\\usepackage{amsfonts}"
	, "\\usepackage{amssymb}"
	, "\\usepackage{color}"
	, "\\usepackage[landscape]{geometry}"
	, "\\pagestyle{empty}"
	, "\\begin{document}"
	, "\\begin{Large}"
	, "\\nonumber"
	, "$\\displaystyle"
	, "~{0}"
	, "$\\end{Large}"
	, "\\end{document}"
	)));
}
