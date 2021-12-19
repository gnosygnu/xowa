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
package gplx.xowa.wikis;
import gplx.frameworks.invks.Gfo_invk;
import gplx.libs.files.Io_url;
import gplx.xowa.*;
public interface Xoa_wiki_mgr extends Gfo_invk {
	int				Count();
	boolean			Has(byte[] key);
	Xow_wiki		Get_at(int idx);
	Xow_wiki		Get_by_or_null(byte[] key);
	Xow_wiki		Get_by_or_make_init_y(byte[] key);
	Xow_wiki		Get_by_or_make_init_n(byte[] key);
	void			Add(Xow_wiki wiki);
	Xow_wiki		Make(byte[] domain_bry, Io_url wiki_root_dir);
	Xow_wiki		Import_by_url(Io_url fil);
}
