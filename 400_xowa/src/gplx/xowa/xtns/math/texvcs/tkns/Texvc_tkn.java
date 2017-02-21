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
package gplx.xowa.xtns.math.texvcs.tkns; import gplx.*; import gplx.xowa.*; import gplx.xowa.xtns.*; import gplx.xowa.xtns.math.*; import gplx.xowa.xtns.math.texvcs.*;
public interface Texvc_tkn {
	int					Tid();
	Texvc_root			Root();
	int					Uid();
	int					Src_bgn();
	int					Src_end();
	void				Src_end_(int v);
	Texvc_tkn			Init(Texvc_root root, int tid, int uid, int src_bgn, int src_end);
	int					Subs__len();
	Texvc_tkn			Subs__get_at(int i);
	void				Print_dbg_bry(Bry_bfr bfr, int indent);
	void				Print_tex_bry(Bry_bfr bfr, byte[] src, int indent);
}
