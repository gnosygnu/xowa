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
package gplx.langs.phps; import gplx.*; import gplx.langs.*;
import gplx.core.log_msgs.*;
public interface Php_tkn_wkr {
	void Init(Php_ctx ctx);
	void Process(Php_tkn tkn);
	void Msg_many(byte[] src, int bgn, int end, Gfo_msg_itm itm, Object... args);
}
class Php_tkn_wkr_tkn implements Php_tkn_wkr {
	public void Init(Php_ctx ctx) {}
	public List_adp List() {return lines;} List_adp lines = List_adp_.New();
	public Gfo_msg_log Msg_log() {return msg_log;} Gfo_msg_log msg_log = new Gfo_msg_log("gplx.langs.phps");
	public void Clear() {lines.Clear(); msg_log.Clear();}
	public void Process(Php_tkn tkn) {
		lines.Add(tkn);
	}
	public void Msg_many(byte[] src, int bgn, int end, Gfo_msg_itm itm, Object... args) {
		msg_log.Add_itm_many(itm, src, bgn, end, args);
	}
}
