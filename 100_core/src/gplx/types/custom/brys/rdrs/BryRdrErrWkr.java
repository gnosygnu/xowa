/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.types.custom.brys.rdrs;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.utls.ArrayUtl;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.BryFind;
import gplx.types.custom.brys.wtrs.BryUtlByWtr;
import gplx.types.errs.Err;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.utls.ObjectUtl;
public class BryRdrErrWkr {
	private String sect; private int sect_bgn;
	public byte[] Src() {return src;} private byte[] src;
	public String Page() {return page;} private String page;
	public void FailThrowsErrSet(boolean v) {this.fail_throws_err = v;} private boolean fail_throws_err = true;
	public void InitByPage(String page, byte[] src)   {this.page = page; this.src = src;}
	public void InitBySect(String sect, int sect_bgn) {this.sect = sect; this.sect_bgn = sect_bgn;}
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
		if (fail_throws_err) throw ErrUtl.NewArgs(err_msg).LoggedSetY();
		return BryFind.NotFound;
	}
	private String Make_msg(String msg, int excerpt_bgn, int excerpt_end, Object[] args) {
		int args_len = args.length;
		args_len += 6;
		args = (Object[])ArrayUtl.Resize(args, args_len);
		args[args_len - 6] = "page"; args[args_len - 5] = Quote(page);
		args[args_len - 4] = "sect"; args[args_len - 3] = Quote(sect);
		args[args_len - 2] = "text"; args[args_len - 1] = BryUtlByWtr.EscapeWs(BryUtl.MidSafe(src, excerpt_bgn, excerpt_end));
		for (int i = 0; i < args_len - 6; i += 2) {
			args[i + 1] = Quote(ObjectUtl.ToStrOrNullMark(args[i + 1]));
		}
		return Err.ToStr(msg, args);
	}
	private static String Quote(String v) {return "'" + v + "'";}
}
