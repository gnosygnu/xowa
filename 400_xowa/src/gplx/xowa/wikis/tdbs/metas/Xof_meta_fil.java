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
package gplx.xowa.wikis.tdbs.metas;
import gplx.core.flds.*;
import gplx.types.basics.utls.BryUtl;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.libs.files.Io_url;
import gplx.libs.files.Io_url_;
import gplx.types.basics.lists.Ordered_hash;
import gplx.types.basics.lists.Ordered_hash_;
public class Xof_meta_fil {
	private final Ordered_hash itms = Ordered_hash_.New_bry();
	public Xof_meta_fil(Xof_meta_mgr meta_mgr, byte[] md5) {this.meta_mgr = meta_mgr; this.md5 = md5;}
	public Xof_meta_mgr Owner_mgr() {return meta_mgr;} private final Xof_meta_mgr meta_mgr;
	public byte[] Md5() {return md5;} private final byte[] md5;
	public void Dirty_() {meta_mgr.Dirty_(this);}
	public Xof_meta_itm Get_or_new(byte[] ttl) {
		Xof_meta_itm rv = Get_or_null(ttl);
		if (rv == null) {
			rv = new Xof_meta_itm(this, ttl);
			itms.Add(ttl, rv);
		}
		return rv;
	}
	public Xof_meta_itm Get_or_null(byte[] ttl) {return (Xof_meta_itm)itms.GetByOrNull(ttl);}
	public void Save(Gfo_fld_wtr wtr) {
		int itms_len = itms.Len();
		for (int i = 0; i < itms_len; i++) {
			Xof_meta_itm itm = (Xof_meta_itm)itms.GetAt(i);
			itm.Save(wtr);
		}
	}
	public Xof_meta_fil Load(Gfo_fld_rdr rdr, Xof_meta_thumb_parser parser) {
		itms.Clear();
		int bry_len = rdr.Data().length;
		while (rdr.Pos() < bry_len) {
			Xof_meta_itm itm = new Xof_meta_itm(this, BryUtl.Empty);
			itm.Load(rdr, parser);
			itms.Add(itm.Ttl(), itm);
		}
		return this;
	}
	public static Io_url Bld_url(Io_url root, byte[] md5, int depth) {
		Bld_url_bfr.Add(root.RawBry());
		for (int i = 0; i < depth - 1; i++)
			Bld_url_bfr.AddByte(md5[i]).AddByte(root.Info().DirSpr_byte());
		for (int i = 0; i < depth; i++)
			Bld_url_bfr.AddByte(md5[i]);
		Bld_url_bfr.Add(Bry_url_ext);
		return Io_url_.new_fil_(Bld_url_bfr.ToStrAndClear());
	}	private static final byte[] Bry_url_ext = BryUtl.NewA7(".csv"); static BryWtr Bld_url_bfr = BryWtr.NewWithSize(260);	// 260 is max path of url
}
