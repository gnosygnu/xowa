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
package gplx.xowa.bldrs.wkrs;
import gplx.frameworks.invks.Gfo_invk;
import gplx.xowa.*; import gplx.xowa.bldrs.*;
public interface Xob_cmd extends Gfo_invk {
	String		Cmd_key();
	Xob_cmd		Cmd_clone(Xob_bldr bldr, Xowe_wiki wiki);
	void		Cmd_init(Xob_bldr bldr);
	void		Cmd_bgn(Xob_bldr bldr);
	void		Cmd_run();
	void		Cmd_end();
	void		Cmd_term();
}
