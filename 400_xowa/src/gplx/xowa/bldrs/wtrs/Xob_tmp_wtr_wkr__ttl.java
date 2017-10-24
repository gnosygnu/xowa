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
package gplx.xowa.bldrs.wtrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
import gplx.core.ios.*;
import gplx.xowa.wikis.nss.*;
public class Xob_tmp_wtr_wkr__ttl implements Xob_tmp_wtr_wkr {
	public Xob_tmp_wtr_wkr__ttl(Io_url temp_dir, int dump_fil_len) {this.temp_dir = temp_dir; this.dump_fil_len = dump_fil_len;} Io_url temp_dir; int dump_fil_len;
	public Xob_tmp_wtr Tmp_wtr_new(Xow_ns ns) {
		return Xob_tmp_wtr.new_(ns, Io_url_gen_.dir_(temp_dir.GenSubDir_nest(ns.Num_str(), "dump")), dump_fil_len);
	}
}
