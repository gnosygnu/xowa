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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class Io_fil_mkr {
	private final    List_adp list = List_adp_.New();
	public Io_fil_mkr Add(String url, String data) {return Add(Io_url_.mem_fil_(url), data);}
	public Io_fil_mkr Add(Io_url url, String data) {list.Add(new Io_fil(url, data)); return this;}
	public Io_fil[] To_ary() {return (Io_fil[])list.To_ary(Io_fil.class);}
}
