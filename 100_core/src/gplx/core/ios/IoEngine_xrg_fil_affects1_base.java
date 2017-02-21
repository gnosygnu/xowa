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
public class IoEngine_xrg_fil_affects1_base {
	public Io_url Url() {return url;} public void Url_set(Io_url v) {url = v;} Io_url url;
	public IoEngine_xrg_fil_affects1_base Url_(Io_url v) {url = v; return this;}
	public boolean MissingFails() {return missingFails;} public void MissingFails_set(boolean v) {missingFails = v;} private boolean missingFails = true;
	public boolean ReadOnlyFails() {return readOnlyFails;} public void ReadOnlyFails_set(boolean v) {readOnlyFails = v;} private boolean readOnlyFails = true;
	@gplx.Virtual public void Exec() {}
}
