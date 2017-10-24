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
import gplx.core.texts.*;
public class IoEngine_xrg_saveFilStr {
	public Io_url Url() {return url;} public IoEngine_xrg_saveFilStr Url_(Io_url val) {url = val; return this;} Io_url url;
	public String Text() {return text;} public IoEngine_xrg_saveFilStr Text_(String val) {text = val; return this;} private String text = "";
	public boolean Append() {return append;}  public IoEngine_xrg_saveFilStr Append_() {return Append_(true);} public IoEngine_xrg_saveFilStr Append_(boolean val) {append = val; return this;} private boolean append = false;
	public void Exec() {
		if (String_.Eq(text, "") && append) return;	// no change; don't bother writing to disc
		IoEnginePool.Instance.Get_by(url.Info().EngineKey()).SaveFilText_api(this);
	}
	public static IoEngine_xrg_saveFilStr new_(Io_url url, String text) {
		IoEngine_xrg_saveFilStr rv = new IoEngine_xrg_saveFilStr();
		rv.url = url; rv.text = text;
		return rv;
	}	IoEngine_xrg_saveFilStr() {}
}
