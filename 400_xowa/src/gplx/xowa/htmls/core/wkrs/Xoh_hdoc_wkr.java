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
package gplx.xowa.htmls.core.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*; import gplx.xowa.htmls.core.*;
import gplx.langs.htmls.docs.*;
import gplx.xowa.wikis.ttls.*;
import gplx.xowa.htmls.core.hzips.*;
public interface Xoh_hdoc_wkr {
	void On_new_page(Xoh_hzip_bfr bfr, Xoh_page hpg, Xoh_hdoc_ctx hctx, byte[] src, int src_bgn, int src_end);
	void On_txt		(int rng_bgn, int rng_end);
	void On_escape	(gplx.xowa.htmls.core.wkrs.escapes.Xoh_escape_data data);
	void On_xnde	(gplx.xowa.htmls.core.wkrs.xndes.Xoh_xnde_parser parser);
	void On_lnki	(gplx.xowa.htmls.core.wkrs.lnkis.Xoh_lnki_data parser);
	void On_thm		(gplx.xowa.htmls.core.wkrs.thms.Xoh_thm_data parser);
	void On_gly		(gplx.xowa.htmls.core.wkrs.glys.Xoh_gly_grp_data parser);
	boolean Process_parse(Xoh_data_itm data);
}
