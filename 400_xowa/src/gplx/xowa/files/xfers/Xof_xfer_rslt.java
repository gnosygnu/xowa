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
package gplx.xowa.files.xfers;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
public class Xof_xfer_rslt {
	public boolean Pass() {return pass;} private boolean pass = true;
	public String Err_msg() {return err_msg;} private String err_msg = StringUtl.Empty;
	public String Src() {return src;} private String src;
	public Io_url Trg() {return trg;} public Xof_xfer_rslt Trg_(Io_url v) {trg = v; return this;}  Io_url trg;
	public void Atrs_src_trg_(String src, Io_url trg) {this.src = src; this.trg = trg;}		
	public boolean Fail(String msg) {
		pass = false;
		err_msg = msg;
		return false;
	}
	public void Clear() {pass = true; err_msg = src = StringUtl.Empty; trg = Io_url_.Empty;}
}
