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
package gplx.core.ios.zips;
import gplx.libs.files.Io_mgr;
import gplx.types.basics.utls.BryLni;
import gplx.types.basics.utls.BryUtl;
import gplx.types.errs.ErrUtl;
import gplx.libs.files.Io_url;
import gplx.types.basics.utls.StringUtl;
public class Io_zip_mgr_mok implements Io_zip_mgr {
	public void Zip_fil(Io_url src_fil, Io_url trg_fil) {
		byte[] src_bry = Io_mgr.Instance.LoadFilBry(src_fil);
		byte[] zip_bry = Zip_bry(src_bry, 0, src_bry.length);
		Io_mgr.Instance.SaveFilBry(trg_fil, zip_bry);
	}
	public void Zip_dir(Io_url src_dir, Io_url trg_fil) {}
	public byte[] Zip_bry(byte[] src, int bgn, int len)            {return BryUtl.Add(Bry_zipped, BryLni.Mid(src, bgn, len));}
	public byte[] Unzip_bry(byte[] src, int bgn, int len)        {
		if (src == BryUtl.Empty) return src;
		byte[] section = BryLni.Mid(src, bgn, bgn + len);
		if (!BryUtl.HasAtBgn(section, Bry_zipped, 0, section.length)) throw ErrUtl.NewArgs("src not zipped", "section", StringUtl.NewU8(section));
		return BryLni.Mid(section, Bry_zipped.length, section.length);
	}
	public void Unzip_to_dir(Io_url src_fil, Io_url trg_dir) {}
	private static final byte[] Bry_zipped = BryUtl.NewA7("zipped:");
	public static final Io_zip_mgr_mok Instance = new Io_zip_mgr_mok(); Io_zip_mgr_mok() {}
}
