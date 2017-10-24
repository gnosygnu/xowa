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
package gplx.core.net; import gplx.*; import gplx.core.*;
public class Http_post_data_hash {
	private final Ordered_hash hash = Ordered_hash_.New_bry();
	public int Len() {return hash.Count();}
	public Http_post_data_itm Get_at(int i)		{return (Http_post_data_itm)hash.Get_at(i);}
	public Http_post_data_itm Get_by(byte[] k)	{return (Http_post_data_itm)hash.Get_by(k);}
	public void Add(byte[] key, byte[] val) {
		hash.Add(key, new Http_post_data_itm(key, val));
	}
}
