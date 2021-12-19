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
package gplx.xowa.wikis.tdbs;
import gplx.libs.files.Io_mgr;
import gplx.libs.ios.IoConsts;
import gplx.types.basics.utls.BryUtl;
import gplx.libs.files.Io_url;
import gplx.types.basics.lists.List_adp;
import gplx.types.basics.lists.List_adp_;
import gplx.types.basics.wrappers.IntRef;
public class ByteAry_fil {
	public List_adp Itms() {return itms;} List_adp itms = List_adp_.New();
	public Io_url Fil() {return fil;} Io_url fil;
	public byte[] Raw_bry() {return raw_bry;} private byte[] raw_bry = BryUtl.Empty;
	public int Raw_len() {return raw_len.Val();} IntRef raw_len = IntRef.NewZero();
	public int Raw_max() {return raw_max;} private int raw_max = IoConsts.LenMB;
	public ByteAry_fil Ini_file(Io_url fil) {
		this.fil = fil;
		raw_bry = Io_mgr.Instance.LoadFilBry_reuse(fil, raw_bry, raw_len);
		return this;
	}
	public Object Xto_itms(Class<?> itm_type) {
		Object rv = itms.ToAry(itm_type);
		itms.Clear();
		if (raw_bry.length > raw_max) raw_bry = BryUtl.Empty;
		raw_len.ValSetZero();
		return rv;
	}
	public static final ByteAry_fil Instance = new ByteAry_fil(); ByteAry_fil() {}
}
