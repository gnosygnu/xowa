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
package gplx.xowa.files.bins; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*;
import gplx.core.primitives.*;
interface Bin_fetcher {
	boolean Save_as_url(Io_url trg);
	boolean Save_as_bry(Bry_obj_ref bry);
}
class Bin_fetcher_fsys implements Bin_fetcher {
	public void Init_src_url(Io_url src) {this.src = src;} private Io_url src;
	public boolean Save_as_url(Io_url trg) {
		try {Io_mgr.Instance.CopyFil(src, trg, true); return true;}
		catch (Exception exc) {Err_.Noop(exc); return false;}
	}
	public boolean Save_as_bry(Bry_obj_ref bry_ref) {
		try {
			byte[] bry = Io_mgr.Instance.LoadFilBry(src);
			bry_ref.Val_(bry);
			return true;
		}
		catch (Exception exc) {Err_.Noop(exc); return false;}
	}
}
class Bin_fetcher_http implements Bin_fetcher {
	private gplx.core.ios.IoEngine_xrg_downloadFil download = gplx.core.ios.IoEngine_xrg_downloadFil.new_("", Io_url_.Empty);
	public void Init_src_str(String src) {this.src = src;} private String src;
	public boolean Save_as_url(Io_url trg) {
		return download.Src_(src).Trg_(trg).Exec();
	}
	public boolean Save_as_bry(Bry_obj_ref bry_ref) {
		try {
			byte[] rv = download.Exec_as_bry(src);
			bry_ref.Val_(rv);
			return true;
		}
		catch (Exception exc) {Err_.Noop(exc); return false;}
	}
}
class Bin_fetcher_fsdb {
	public void Init_id(int id) {}
	public boolean Save_as_url(Io_url trg) {
//			IoStream s = fsdb.Get_stream(id);
//			s.Save(trg);
		return true;
	}
	public byte[] Save_to_mem() {
//			return db.Get_bry(id);
		return null;
	}
}
