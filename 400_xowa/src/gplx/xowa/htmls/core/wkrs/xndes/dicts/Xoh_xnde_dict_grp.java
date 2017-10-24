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
package gplx.xowa.htmls.core.wkrs.xndes.dicts; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import gplx.core.brys.*;
public class Xoh_xnde_dict_grp {
	private final    int id_len;
	private int id_next = -1;
	private final    Hash_adp_bry hash = Hash_adp_bry.cs();
	private final    List_adp list = List_adp_.New();
	public Xoh_xnde_dict_grp(int id_len) {this.id_len = id_len;}
	public void Clear() {
		id_next = -1;
		hash.Clear();
		list.Clear();
	}
	public void Add(byte[] val) {Make(val);}
	public Xoh_xnde_dict_itm Get_by_id_or_null(int id) {return (Xoh_xnde_dict_itm)list.Get_at(id);}
	public Xoh_xnde_dict_itm Get_by_key_or_new(byte[] src, int src_bgn, int src_end) {
		Xoh_xnde_dict_itm itm = (Xoh_xnde_dict_itm)hash.Get_by_mid(src, src_bgn, src_end);
		if (itm == null) {
			byte[] val = Bry_.Mid(src, src_bgn, src_end);
			itm = Make(val);
		}
		else
			itm.Count_add_1();
		return itm;
	}
	public void Save(Xoh_hzip_bfr bfr) {
		// sort by val
		// write dict_id?
		int len = list.Count();
		for (int i = 0; i < len; ++i) {
			Xoh_xnde_dict_itm itm = (Xoh_xnde_dict_itm)list.Get_at(i);
			itm.Save(bfr, id_len);
		}
		bfr.Add(Bry__stop);
	}
	public void Load(Bry_rdr rdr) {
		while (true) {
			if (rdr.Is(Bry__stop)) break;
			int id = rdr.Read_hzip_int(id_len);
			byte[] val = rdr.Read_bry_to(Byte_ascii.Nl);
			Xoh_xnde_dict_itm itm = new Xoh_xnde_dict_itm(id, val);
			hash.Add(val, itm);
			list.Add(itm);
		}
		// sort by id
	}
	private Xoh_xnde_dict_itm Make(byte[] val) {return Make(++id_next, val);}
	private Xoh_xnde_dict_itm Make(int id, byte[] val) {
		Xoh_xnde_dict_itm rv = new Xoh_xnde_dict_itm(id, val);
		hash.Add(val, rv);
		list.Add(rv);
		return rv;
	}
	public static final    byte[] Bry__stop = Bry_.New_by_ints(255, 0);
}
