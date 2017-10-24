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
package gplx.xowa.htmls.core.wkrs.xndes.atrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
public class Xohz_atr_regy {
	private final    Hash_adp_bry itm_regy = Hash_adp_bry.cs();
	private final    Hash_adp_bry grp_regy = Hash_adp_bry.cs();
	private final    List_adp tmp_list = List_adp_.New();
	public Xohz_atr_regy Grps__add(byte[] grp_key, byte[]... sub_keys) {
		int sub_keys_len = sub_keys.length;
		for (int i = 0; i < sub_keys_len; ++i) {
			byte[] sub_key = sub_keys[i];
			Grps__add__recur(sub_key);
		}
		Xohz_atr_grp grp = new Xohz_atr_grp((byte[][])tmp_list.To_ary_and_clear(byte[].class));
		grp_regy.Add(grp_key, grp);
		return this;
	}
	private void Grps__add__recur(byte[] sub_key) {
		Object sub_obj = itm_regy.Get_by(sub_key);
		if (sub_obj == null) {
			sub_obj = grp_regy.Get_by(sub_key);
			if (sub_obj == null) throw Err_.new_("hzip", "sub_key is not known itm or grp", "sub_key", sub_key);
			Xohz_atr_grp sub_grp = (Xohz_atr_grp)sub_obj;
			byte[][] subs = sub_grp.Subs(); int subs_len = subs.length;
			for (int i = 0; i < subs_len; ++i)
				Grps__add__recur(subs[i]);
		}
		else
			tmp_list.Add(((Xohz_atr_itm)sub_obj).Key());
	}
	public Xohz_atr_regy Itms__add_enm(int uid, byte[] key, byte[][] val_ary)	{itm_regy.Add(key, new Xohz_atr_itm__enm(uid, key, val_ary)); return this;}
	public Xohz_atr_regy Itms__add_int(int uid, byte[] key)						{return Itms__add_int(uid, key, 1);}
	public Xohz_atr_regy Itms__add_int(int uid, byte[] key, int val_len)		{itm_regy.Add(key, new Xohz_atr_itm__int(uid, key, val_len)); return this;}
	public Xohz_atr_regy Itms__add_str(int uid, byte[] key)						{itm_regy.Add(key, new Xohz_atr_itm__str(uid, key)); return this;}
	public Xohz_atr_itm[] Resolve(byte[] key) {
		Grps__add__recur(key);
		byte[][] itm_keys = (byte[][])tmp_list.To_ary_and_clear(byte[].class);
		int itm_keys_len = itm_keys.length;
		Xohz_atr_itm[] rv = new Xohz_atr_itm[itm_keys_len];
		for (int i = 0; i < itm_keys_len; ++i)
			rv[i] = (Xohz_atr_itm)itm_regy.Get_by(itm_keys[i]);
		return rv;
	}
}
class Xohz_atr_grp {
	public Xohz_atr_grp(byte[][] subs) {this.subs = subs;}
	public byte[][] Subs() {return subs;} private final    byte[][] subs;
}
