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
package gplx.core.brys; import gplx.*; import gplx.core.*;
import gplx.core.errs.*;
public class Bry_err_wkr {
	private String sect; private int sect_bgn;
	public byte[] Src() {return src;} private byte[] src;
	public String Page() {return page;} private String page;
	public void Fail_throws_err_(boolean v) {this.fail_throws_err = v;} private boolean fail_throws_err = true;
	public void Init_by_page(String page, byte[] src)	{this.page = page; this.src = src;}
	public void Init_by_sect(String sect, int sect_bgn) {this.sect = sect; this.sect_bgn = sect_bgn;}
	public void Warn(String msg, Object... args) {
		boolean old = fail_throws_err;
		fail_throws_err = false;
		this.Fail(msg, args);
		fail_throws_err = old;
	}
	public int Fail(String msg, Object... args) {return Fail(msg, sect_bgn, sect_bgn + 255, args);}
	private int Fail(String msg, int excerpt_bgn, int excerpt_end, Object[] args) {
		String err_msg = Make_msg(msg, excerpt_bgn, excerpt_end, args);
		Gfo_usr_dlg_.Instance.Warn_many("", "", err_msg);
		if (fail_throws_err) throw Err_.new_("Bry_err_wkr", err_msg).Logged_y_();
		return Bry_find_.Not_found;
	}
	private String Make_msg(String msg, int excerpt_bgn, int excerpt_end, Object[] args) {
		int args_len = args.length;
		args_len += 6;
		args = (Object[])Array_.Resize(args, args_len);
		args[args_len - 6] = "page"; args[args_len - 5] = Quote(page);
		args[args_len - 4] = "sect"; args[args_len - 3] = Quote(sect);
		args[args_len - 2] = "text"; args[args_len - 1] = Bry_.Escape_ws(Bry_.Mid_safe(src, excerpt_bgn, excerpt_end));
		for (int i = 0; i < args_len - 6; i += 2) {
			args[i + 1] = Quote(Object_.Xto_str_strict_or_null_mark(args[i + 1]));
		}
		return Err_msg.To_str(msg, args);
	}
	private static String Quote(String v) {return "'" + v + "'";}
}
