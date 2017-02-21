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
package gplx;
public class Cancelable_ {
	public static final Cancelable Never = new Cancelable_never();
	public static Cancelable New_proxy() {return new Cancelable_proxy();}
}
class Cancelable_never implements Cancelable {
	public boolean Canceled() {return false;}
	public void Cancel() {}
}
class Cancelable_proxy implements Cancelable {
	private boolean canceled = false;
	public boolean Canceled()		{return canceled;}
	public void Cancel()		{canceled = true;}
}
