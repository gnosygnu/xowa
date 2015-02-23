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
package gplx.xowa.files.imgs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.gfui.*;
import gplx.xowa.wmfs.*;
public interface Xof_img_wkr_query_img_size {
	SizeAdp Exec(Io_url url);
}
class Xof_img_wkr_query_img_size_imageMagick implements Xof_img_wkr_query_img_size {
	private final Xowmf_mgr wmf_mgr; private final ProcessAdp cmd;
	public Xof_img_wkr_query_img_size_imageMagick(Xowmf_mgr wmf_mgr, ProcessAdp cmd) {this.wmf_mgr = wmf_mgr; this.cmd = cmd;}
	public SizeAdp Exec(Io_url url) {
		cmd.Prog_fmt_(String_.Replace(wmf_mgr.Download_wkr().Download_xrg().Prog_fmt_hdr(), "~", "~~") + " querying: ~{process_seconds} second(s); ~{process_exe_name} ~{process_exe_args}");
		cmd.Run(url);
		String size_str = cmd.Rslt_out();
		int pos_bgn = String_.FindFwd(size_str, "<{", 0);		if (pos_bgn == String_.Find_none) return SizeAdp_.Zero; // NOTE: RE: "FindFwd(,0)"; multiple frames are possible; 1st frame must be used as last frame is not accurate; EX:w.Chess:[[File:ChessCastlingMovie.gif|thumb|250px]]
		int pos_end = String_.FindFwd(size_str, "}>", pos_bgn); if (pos_end == String_.Find_none) return SizeAdp_.Zero;
		size_str = String_.Mid(size_str, pos_bgn + Marker_bgn_len, pos_end);
		return SizeAdp_.parse_or_(size_str, SizeAdp_.Zero);
	}
	static final String Marker_bgn = "<{", Marker_end = "}>"; static final int Marker_bgn_len = String_.Len(Marker_bgn);
}
