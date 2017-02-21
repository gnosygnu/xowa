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
package gplx.xowa.files.fsdb.fs_roots; import gplx.*; import gplx.xowa.*; import gplx.xowa.files.*; import gplx.xowa.files.fsdb.*;
class Orig_fil_mgr {
	private final    Ordered_hash hash = Ordered_hash_.New_bry();
	public boolean Has(byte[] lnki_ttl) {return hash.Has(lnki_ttl);}
	public Orig_fil_row Get_by_ttl(byte[] lnki_ttl) {return (Orig_fil_row)hash.Get_by(lnki_ttl);}
	public void Add(Orig_fil_row fil) {hash.Add(fil.Name(), fil);}
}
