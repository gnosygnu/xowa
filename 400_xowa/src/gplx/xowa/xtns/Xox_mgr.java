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
package gplx.xowa.xtns; import gplx.*; import gplx.xowa.*;
public interface Xox_mgr extends Gfo_invk {
	byte[]		Xtn_key();
	void		Xtn_ctor_by_app(Xoae_app app);
	void		Xtn_ctor_by_wiki(Xowe_wiki wiki);
	void		Xtn_init_by_app(Xoae_app app);
	void		Xtn_init_by_wiki(Xowe_wiki wiki);
	Xox_mgr		Xtn_clone_new();
}
