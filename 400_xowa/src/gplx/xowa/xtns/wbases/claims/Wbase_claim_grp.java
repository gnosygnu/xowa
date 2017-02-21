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
package gplx.xowa.xtns.wbases.claims; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.wbases.*;
import gplx.core.primitives.*;
import gplx.xowa.xtns.wbases.claims.itms.*;
public class Wbase_claim_grp {
	public Wbase_claim_grp(Int_obj_ref id_ref, Wbase_claim_base[] itms) {this.id_ref = id_ref; this.itms = itms;}
	public Int_obj_ref Id_ref() {return id_ref;} private final    Int_obj_ref id_ref;
	public int Id() {return id_ref.Val();}
	public String Id_str() {if (id_str == null) id_str = "P" + Int_.To_str(id_ref.Val()); return id_str;} private String id_str;
	public int Len() {return itms.length;} private Wbase_claim_base[] itms;
	public Wbase_claim_base Get_at(int i) {return itms[i];}
	public static List_adp Xto_list(Ordered_hash hash) {
		int len = hash.Count();
		List_adp rv = List_adp_.New();
		for (int i = 0; i < len; ++i) {
			Wbase_claim_grp grp = (Wbase_claim_grp)hash.Get_at(i);
			int grp_len = grp.Len();
			for (int j = 0; j < grp_len; ++j)
				rv.Add(grp.Get_at(j));
		}
		return rv;
	}
}
