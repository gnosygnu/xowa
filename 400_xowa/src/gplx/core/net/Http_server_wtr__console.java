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
import gplx.core.consoles.*;
class Http_server_wtr__console implements Http_server_wtr {
	public void Write_str_w_nl(String s) {Console_adp__sys.Instance.Write_str_w_nl(s);}
}
class Http_server_wtr__mock implements Http_server_wtr {
	public void Write_str_w_nl(String s) {data = s;}
	public String Data() {return data;} private String data;
	public void Clear() {data = null;}
}
