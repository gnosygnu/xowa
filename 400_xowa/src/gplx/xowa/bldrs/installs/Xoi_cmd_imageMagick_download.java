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
package gplx.xowa.bldrs.installs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.gfui.*; import gplx.gfui.kits.core.*;
import gplx.core.threads.*;
class Xoi_cmd_imageMagick_download extends Gfo_thread_cmd_download implements Gfo_thread_cmd {//		private static final    byte[] Bry_windows_zip = Bry_.new_a7("-windows.zip");
//		static final String Src_imageMagick = "ftp://ftp.sunet.se/pub/multimedia/graphics/ImageMagick/binaries/";
	public Xoi_cmd_imageMagick_download(Gfo_usr_dlg usr_dlg, Gfui_kit kit, Io_url trg) {this.Ctor(usr_dlg, kit); this.trg = trg;} Io_url trg;
	@Override public byte Async_init() {	// <a href="ImageMagick-6.8.1-9-Q16-x86-windows.zip">
//			byte[] raw = xrg.Exec_as_bry(Src_imageMagick);
//			int find_pos = Bry_find_.Find_fwd(raw, Bry_windows_zip);				if (find_pos == Bry_find_.Not_found) return Fail();
//			int bgn_pos = Bry_find_.Find_bwd(raw, Byte_ascii.Quote, find_pos);	if (bgn_pos == Bry_find_.Not_found) return Fail();
//			++bgn_pos;
//			int end_pos = Bry_find_.Find_fwd(raw, Byte_ascii.Quote, bgn_pos);		if (end_pos == Bry_find_.Not_found) return Fail();
//			String src = Src_imageMagick + String_.new_a7(Bry_.Mid(raw, bgn_pos, end_pos));
		String src = "http://ftp.sunet.se/pub/multimedia/graphics/ImageMagick/binaries/ImageMagick-6.8.8-1-Q16-x86-windows.zip";
		this.Init("downloading", src, trg);
		return super.Async_init();
	}
	byte Fail() {
		kit.Ask_ok(GRP_KEY, "windows_not_found", "Could not find Windows binary. Please download ImageMagick directly from the site.");
		return Gfo_thread_cmd_.Init_cancel_step;
	}
	public static final String KEY_imageMagick = "download.imageMagick";
	static final String GRP_KEY = "xowa.install.cmds.download.imageMagick";
}
class Xoi_cmd_msg_ok extends Gfo_thread_cmd_base implements Gfo_thread_cmd {
	public Xoi_cmd_msg_ok(Gfo_usr_dlg usr_dlg, Gfui_kit kit, String msg) {this.msg = msg; this.Ctor(usr_dlg, kit);} private String msg;
	@Override public boolean Async_term()	{
		kit.Ask_ok("msg_ok", "msg", msg);
		return true;
	}
	public static final String KEY = "msg.ok";
}
