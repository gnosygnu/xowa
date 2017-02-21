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
package gplx.gfui.kits.swts; import gplx.*; import gplx.gfui.*; import gplx.gfui.kits.*;
public class Swt_html_eval_rslt {
	public void Clear() {error = null; result = null;}
	public boolean Result_pass() {return error == null;}
	public Object Result() {return result;} public void Result_set(Object v) 	{result = v; error = null;} private Object result;
	public String Error () {return error;} 	public void Error_set(String v) 	{error = v; result = null;} private String error;
}
