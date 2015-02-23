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
package gplx.xowa.files.cnvs; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.primitives.*; import gplx.xowa.wmfs.*;
public class Xof_img_wkr_resize_img_imageMagick implements Xof_img_wkr_resize_img {
	private final Xowmf_mgr wmf_mgr; private final ProcessAdp cmd_convert, cmd_convert_svg_to_png;
	private boolean init_needed = true;
	public Xof_img_wkr_resize_img_imageMagick(Xowmf_mgr wmf_mgr, ProcessAdp cmd_convert, ProcessAdp cmd_convert_svg_to_png) {
		this.wmf_mgr = wmf_mgr; this.cmd_convert = cmd_convert; this.cmd_convert_svg_to_png = cmd_convert_svg_to_png;
	}
	public boolean Exec(Io_url src, Io_url trg, int trg_w, int trg_h, int ext_id, String_obj_ref rslt_val) {
		if (!Io_mgr._.ExistsFil(src)) return false;
		Io_mgr._.CreateDirIfAbsent(trg.OwnerDir());
		if (init_needed) {
			init_needed = false;
			Gfo_usr_dlg usr_dlg = Xoa_app_.Usr_dlg();
			cmd_convert.Prog_dlg_(usr_dlg);
			cmd_convert_svg_to_png.Prog_dlg_(usr_dlg);
		}
		ProcessAdp cmd = ext_id == Xof_ext_.Id_svg ? cmd_convert_svg_to_png : cmd_convert;
		cmd.Prog_fmt_(String_.Replace(wmf_mgr.Download_wkr().Download_xrg().Prog_fmt_hdr(), "~", "~~") + " converting: ~{process_seconds} second(s); ~{process_exe_name} ~{process_exe_args}");
		cmd.Run(src.Raw(), trg.Raw(), trg_w, trg_h);
		rslt_val.Val_(cmd.Rslt_out());
		boolean rv = cmd.Exit_code_pass();
		if (!rv) Xoa_app_.Usr_dlg().Log_many("", "process_warning", "process completed with warnings: ~{0}", cmd.Rslt_out());
		return true;
	}
}
