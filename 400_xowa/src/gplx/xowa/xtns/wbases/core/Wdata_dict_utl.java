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
package gplx.xowa.xtns.wbases.core;
import gplx.libs.dlgs.Gfo_usr_dlg_;
import gplx.types.basics.lists.Hash_adp_bry;
import gplx.types.basics.utls.ByteUtl;
import gplx.types.basics.utls.StringUtl;
import gplx.types.basics.wrappers.ByteVal;
public class Wdata_dict_utl {
	public static byte Get_tid_or_invalid(byte[] qid, Hash_adp_bry dict, byte[] key) {
		Object rv_obj = dict.Get_by_bry(key);
		if (rv_obj == null) {
			Gfo_usr_dlg_.Instance.Warn_many("", "", "unknown wikidata key; qid=~{0} key=~{1}", StringUtl.NewU8(qid), StringUtl.NewU8(key));
			return Tid_invalid;
		}
		return ((ByteVal)rv_obj).Val();
	}
	public static final byte Tid_invalid = ByteUtl.MaxValue127;
}
