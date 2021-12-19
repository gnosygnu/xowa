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
package gplx.xowa.htmls.core.wkrs.xndes.atrs;
import gplx.types.custom.brys.rdrs.BryRdr;
import gplx.types.custom.brys.wtrs.BryWtr;
import gplx.types.basics.lists.List_adp;
import gplx.xowa.htmls.core.wkrs.*;
import gplx.core.brys.*;
import gplx.langs.htmls.docs.*;
public interface Xohz_atr_itm {
	int Uid();		// EX: 1
	byte[] Key();	// EX: colspan=2
	void Ini_flag	(int flag_idx, List_adp flag_bldr_list);
	void Enc_flag	(Xoh_hdoc_ctx hctx, byte[] src, Gfh_atr hatr, Int_flag_bldr flag_bldr);
	void Enc_data	(Xoh_hdoc_ctx hctx, byte[] src, Gfh_atr hatr, Xoh_hzip_bfr bfr);
	void Dec_all	(Xoh_hdoc_ctx hctx, BryRdr rdr, Int_flag_bldr flag_bldr, BryWtr bfr);
}
