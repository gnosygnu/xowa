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
package gplx.core.ios.zips; import gplx.*; import gplx.core.*; import gplx.core.ios.*;
public class Io_zip_mgr_mok implements Io_zip_mgr {
	public void Zip_fil(Io_url src_fil, Io_url trg_fil) {
		byte[] src_bry = Io_mgr.Instance.LoadFilBry(src_fil);
		byte[] zip_bry = Zip_bry(src_bry, 0, src_bry.length);
		Io_mgr.Instance.SaveFilBry(trg_fil, zip_bry);
	}
	public void Zip_dir(Io_url src_dir, Io_url trg_fil) {}
	public byte[] Zip_bry(byte[] src, int bgn, int len)			{return Bry_.Add(Bry_zipped, Bry_.Mid(src, bgn, len));}
	public byte[] Unzip_bry(byte[] src, int bgn, int len)		{
		if (src == Bry_.Empty) return src;
		byte[] section = Bry_.Mid(src, bgn, bgn + len);
		if (!Bry_.Has_at_bgn(section, Bry_zipped, 0, section.length)) throw Err_.new_wo_type("src not zipped", "section", String_.new_u8(section));
		return Bry_.Mid(section, Bry_zipped.length, section.length);
	}
	public void Unzip_to_dir(Io_url src_fil, Io_url trg_dir) {}
	private static final    byte[] Bry_zipped = Bry_.new_a7("zipped:");
	public static final    Io_zip_mgr_mok Instance = new Io_zip_mgr_mok(); Io_zip_mgr_mok() {}
}
