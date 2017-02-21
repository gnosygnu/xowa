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
package gplx.core.srls; import gplx.*; import gplx.core.*;
public interface Gfo_srl_mgr_wtr {
	int Uid_next__as_int();
	void		Itm_bgn(String key);
	void		Itm_end();
	void		Set_bool	(String key, boolean val);
	void		Set_int		(String key, int val);
	void		Set_str		(String key, String val);
	Object		Set_subs	(Gfo_srl_ctx ctx, Gfo_srl_itm owner, Gfo_srl_itm proto, Object subs, Dbmeta_dat_mgr crt_mgr);
}
