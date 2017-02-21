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
package gplx.xowa.files.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.envs.*;
import gplx.gfui.*;
import gplx.xowa.bldrs.wms.*;
public interface Xof_img_wkr_query_img_size {
	SizeAdp Exec(Io_url url);
}
class Xof_img_wkr_query_img_size_imageMagick implements Xof_img_wkr_query_img_size {
	private final Xowmf_mgr wmf_mgr; private final Process_adp cmd;
	public Xof_img_wkr_query_img_size_imageMagick(Xowmf_mgr wmf_mgr, Process_adp cmd) {this.wmf_mgr = wmf_mgr; this.cmd = cmd;}
	public SizeAdp Exec(Io_url url) {
		cmd.Prog_fmt_(String_.Replace(wmf_mgr.Download_wkr().Download_xrg().Prog_fmt_hdr(), "~", "~~") + " querying: ~{process_seconds} second(s); ~{process_exe_name} ~{process_exe_args}");
		cmd.Run(url);
		String size_str = cmd.Rslt_out();
		int pos_bgn = String_.FindFwd(size_str, "<{", 0);		if (pos_bgn == String_.Find_none) return SizeAdp_.Zero; // NOTE: RE: "FindFwd(,0)"; multiple frames are possible; 1st frame must be used as last frame is not accurate; EX:w.Chess:[[File:ChessCastlingMovie.gif|thumb|250px]]
		int pos_end = String_.FindFwd(size_str, "}>", pos_bgn); if (pos_end == String_.Find_none) return SizeAdp_.Zero;
		size_str = String_.Mid(size_str, pos_bgn + Marker_bgn_len, pos_end);
		return SizeAdp_.parse_or(size_str, SizeAdp_.Zero);
	}
	static final String Marker_bgn = "<{", Marker_end = "}>"; static final int Marker_bgn_len = String_.Len(Marker_bgn);
}
