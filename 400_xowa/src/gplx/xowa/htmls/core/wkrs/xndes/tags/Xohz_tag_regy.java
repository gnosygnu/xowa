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
package gplx.xowa.htmls.core.wkrs.xndes.tags; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*; import gplx.xowa.htmls.core.wkrs.*; import gplx.xowa.htmls.core.wkrs.xndes.*;
import gplx.core.primitives.*;
import gplx.langs.htmls.*; import gplx.xowa.htmls.core.wkrs.xndes.atrs.*;
public class Xohz_tag_regy {
	private final    Ordered_hash keys = Ordered_hash_.New_bry();
	private final    Hash_adp uids = Hash_adp_.New(); private final    Byte_obj_ref uids_ref = Byte_obj_ref.zero_();
	public Xohz_atr_regy Atr_regy() {return atr_regy;} private final    Xohz_atr_regy atr_regy = new Xohz_atr_regy();
	public Xohz_tag Get_by_key(byte[] key)	{return (Xohz_tag)keys.Get_by(key);}
	public Xohz_tag Get_by_uid(byte uid)	{return (Xohz_tag)uids.Get_by(uids_ref.Val_(uid));}
	public Xohz_tag_regy Add(int uid, byte[] key, int flag_len, byte[] atr_key) {
		Xohz_tag tag = new Xohz_tag(uid, key, flag_len, atr_regy.Resolve(atr_key));
		keys.Add(key, tag);
		uids.Add(Byte_obj_ref.new_((byte)uid), tag);
		return this;
	}
}
