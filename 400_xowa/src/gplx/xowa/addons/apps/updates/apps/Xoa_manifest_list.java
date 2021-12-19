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
package gplx.xowa.addons.apps.updates.apps;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
import gplx.types.basics.utls.BryLni;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.custom.brys.BryFind;
import gplx.types.errs.ErrUtl;
import gplx.types.basics.constants.AsciiByte;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.basics.utls.StringUtl;
public class Xoa_manifest_list {
	private final Ordered_hash hash = Ordered_hash_.New();
	public int Len() {return hash.Len();}
	public Xoa_manifest_item Get_at(int i) {return (Xoa_manifest_item)hash.GetAt(i);}
	public void Del_at(int i) {
		Xoa_manifest_item item = (Xoa_manifest_item)hash.GetAt(i);
		hash.Del(item.Src().Raw());
	}
	public void Add(Io_url src, Io_url trg) {
		Xoa_manifest_item item = new Xoa_manifest_item(src, trg);
		hash.Add(src.Raw(), item);
	}
	public void Save(BryWtr bfr) {
		// save as "src\ttrg\n"
		int len = hash.Len();
		for (int i = 0; i < len; i++) {
			Xoa_manifest_item item = (Xoa_manifest_item)hash.GetAt(i);
			bfr.AddStrU8(item.Src().Raw());
			bfr.AddByte(AsciiByte.Tab);
			bfr.AddStrU8(item.Trg().Raw());
			bfr.AddByte(AsciiByte.Nl);
		}
	}
	public void Load(byte[] src, int src_bgn, int src_end) {
		int pos = src_bgn;
		// load by "src\ttrg\n"
		while (pos < src_end) {
			// get pos
			int tab_pos = BryFind.FindFwd(src, AsciiByte.Tab, pos, src_end);
			if (tab_pos == BryFind.NotFound) throw ErrUtl.NewArgs("failed to find tab", "excerpt", BryLni.Mid(src, pos, src_end));
			int nl_pos  = BryFind.FindFwd(src, AsciiByte.Nl, tab_pos + 1, src_end);
			if (nl_pos == BryFind.NotFound) throw ErrUtl.NewArgs("failed to find nl", "excerpt", BryLni.Mid(src, pos, src_end));

			// create
			Io_url src_url = Io_url_.new_fil_(StringUtl.NewU8(src, pos, tab_pos));
			Io_url trg_url = Io_url_.new_fil_(StringUtl.NewU8(src, tab_pos + 1, nl_pos));
			Xoa_manifest_item item = new Xoa_manifest_item(src_url, trg_url);
			hash.Add(src_url.Raw(), item);

			pos = nl_pos + 1;
		}
	}
}
