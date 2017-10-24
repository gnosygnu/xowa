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
package gplx.xowa.bldrs.wkrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*;
public interface Xob_page_wkr extends Gfo_invk {
	String	Page_wkr__key();
	void	Page_wkr__bgn();
	void	Page_wkr__run(gplx.xowa.wikis.data.tbls.Xowd_page_itm page);
	void	Page_wkr__run_cleanup();	// close txns opened during Page_wkr__run
	void	Page_wkr__end();
}
