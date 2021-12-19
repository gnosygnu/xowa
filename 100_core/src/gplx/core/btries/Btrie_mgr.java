/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012-2021 gnosygnu@gmail.com

XOWA is licensed under the terms of the General Public License (GPL) Version 3,
or alternatively under the terms of the Apache License Version 2.0.

You may use XOWA according to either of these licenses as is most appropriate
for your project on a case-by-case basis.

The terms of each license can be found in the source code repository:

GPLv3 License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-GPLv3.txt
Apache License: https://github.com/gnosygnu/xowa/blob/master/LICENSE-APACHE2.txt
*/
package gplx.core.btries;
public interface Btrie_mgr {
	Object MatchAt(Btrie_rv rv, byte[] src, int bgn_pos, int end_pos);
	Object MatchBgn(byte[] src, int bgn_pos, int end_pos);
	Btrie_mgr AddObj(String key, Object val);
	Btrie_mgr AddObj(byte[] key, Object val);
}
