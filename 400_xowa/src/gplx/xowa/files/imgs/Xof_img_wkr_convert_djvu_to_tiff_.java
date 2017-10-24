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
public class Xof_img_wkr_convert_djvu_to_tiff_ {
	public static Xof_img_wkr_convert_djvu_to_tiff new_app(Process_adp process)	{return new Xof_img_wkr_convert_djvu_to_tiff_app(process);}
	public static Xof_img_wkr_convert_djvu_to_tiff new_mok(int w, int h)		{return new Xof_img_wkr_convert_djvu_to_tiff_mok(w, h);}
}
class Xof_img_wkr_convert_djvu_to_tiff_app implements Xof_img_wkr_convert_djvu_to_tiff {
	public Xof_img_wkr_convert_djvu_to_tiff_app(Process_adp process) {this.process = process;} Process_adp process;
	public boolean Exec(Io_url src, Io_url trg) {
		process.Run(src, trg);
		return process.Exit_code_pass();
	}
}
class Xof_img_wkr_convert_djvu_to_tiff_mok implements Xof_img_wkr_convert_djvu_to_tiff {
	public Xof_img_wkr_convert_djvu_to_tiff_mok(int w, int h) {this.w = w; this.h = h;} private int w, h;
	public boolean Exec(Io_url src, Io_url trg) {
		Io_mgr.Instance.SaveFilStr(trg, gplx.gfui.SizeAdp_.new_(w, h).To_str());
		return true;
	}
}
