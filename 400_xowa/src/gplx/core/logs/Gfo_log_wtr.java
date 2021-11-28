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
package gplx.core.logs; import gplx.*; import gplx.core.*;
public class Gfo_log_wtr {
	private final Bry_bfr bfr = Bry_bfr_.New();
	private final int bfr_max;
	private final int fil_max;
	private final Io_url fil_dir;
	private final String fil_fmt;
	private final int fil_idx_places;
	private Io_url fil_url;
	private int fil_idx = -1, fil_len;
	public Gfo_log_wtr(int bfr_max, int fil_max, Io_url fil_dir, String fil_fmt, int fil_idx_places) {
		this.bfr_max = bfr_max;
		this.fil_max = fil_max;
		this.fil_dir = fil_dir;
		this.fil_fmt = fil_fmt;
		this.fil_idx_places = fil_idx_places;
	}
	public Io_url Fil_dir() {return fil_dir;}
	public void Write(Bry_bfr add) {
		int add_len = add.Len();
		if (bfr.Len() + add_len > bfr_max)
			Flush();
		bfr.Add_bfr_and_clear(add);
	}
	public void Flush() {
		if (fil_idx == -1 || bfr.Len() + fil_len > fil_max) {
			fil_idx++;
			fil_url = fil_dir.GenSubFil(String_.Format(fil_fmt, Int_.To_str_pad_bgn_zero(fil_idx, fil_idx_places)));
			fil_len = 0;
		}
		fil_len += bfr.Len();
		Io_mgr.Instance.AppendFilBfr(fil_url, bfr);
		bfr.Clear();
	}

	public static Gfo_log_wtr New_dflt(String sub_dir, String file_fmt) {
		return new Gfo_log_wtr(Io_mgr.Len_mb, 10 * Io_mgr.Len_mb, Gfo_usr_dlg_.Instance.Log_wkr().Session_dir().GenSubDir(sub_dir), file_fmt, 4);
	}
}
