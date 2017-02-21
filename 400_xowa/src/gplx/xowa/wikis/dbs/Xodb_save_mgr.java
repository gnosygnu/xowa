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
package gplx.xowa.wikis.dbs; import gplx.*; import gplx.xowa.*; import gplx.xowa.wikis.*;
public interface Xodb_save_mgr {
	boolean Create_enabled(); void Create_enabled_(boolean v);
	boolean Update_modified_on_enabled(); void Update_modified_on_enabled_(boolean v);
	int Page_id_next(); void Page_id_next_(int v);
	int Data_create(Xoa_ttl ttl, byte[] text);
	void Data_update(Xoae_page page, byte[] text);
	void Data_rename(Xoae_page page, int trg_ns, byte[] trg_ttl);
	void Clear();
}
