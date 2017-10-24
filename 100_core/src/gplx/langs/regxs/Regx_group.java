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
package gplx.langs.regxs; import gplx.*; import gplx.langs.*;
public class Regx_group {
	public Regx_group(boolean rslt, int bgn, int end, String val) {
		this.rslt = rslt;
		this.bgn = bgn;
		this.end = end;
		this.val = val;
	}
	public boolean   Rslt() {return rslt;} private boolean rslt;
	public int       Bgn()  {return bgn;}  private int bgn;
	public int       End()  {return end;}  private int end;
	public String    Val()  {return val;}  private String val;
	public void Init(boolean rslt, int bgn, int end, String val) {
		this.rslt = rslt;
		this.bgn = bgn;
		this.end = end;
		this.val = val;
	}
	public static final    Regx_group[] Ary_empty = new Regx_group[0];
}
