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
package gplx.core.ios;
import gplx.core.envs.*;
import gplx.libs.dlgs.UsrDlg_;
import gplx.libs.dlgs.UsrMsg;
import gplx.types.basics.utls.StringUtl;
import gplx.libs.files.Io_url;
public class IoEngine_xrg_loadFilStr {
	public Io_url Url() {return url;} public IoEngine_xrg_loadFilStr Url_(Io_url val) {url = val; return this;} Io_url url;
	public boolean MissingIgnored() {return missingIgnored;} public IoEngine_xrg_loadFilStr MissingIgnored_() {return MissingIgnored_(true);} public IoEngine_xrg_loadFilStr MissingIgnored_(boolean v) {missingIgnored = v; return this;} private boolean missingIgnored = false;
	public boolean BomUtf8Convert() {return bomUtf8Convert;} public IoEngine_xrg_loadFilStr BomUtf8Convert_(boolean v) {bomUtf8Convert = v; return this;} private boolean bomUtf8Convert = true;
	public String Exec() {
		String s = IoEnginePool.Instance.Get_by(url.Info().EngineKey()).LoadFilStr(this);
		if (bomUtf8Convert && StringUtl.Len(s) > 0 && StringUtl.CodePointAt(s, 0) == Bom_Utf8) {
			s = StringUtl.Mid(s, 1);
			UsrDlg_.Instance.Warn(UsrMsg.new_("UTF8 BOM removed").Add("url", url.Xto_api()));
		}
		return s;
	}
	public String[] ExecAsStrAry()        {return StringUtl.Split(Exec(), StringUtl.CrLf);}
	public String[] ExecAsStrAryLnx()    {
		String raw = Exec();
		if (StringUtl.Len(raw) == 0) return StringUtl.AryEmpty;
		return StringUtl.Split(raw, Op_sys.Nl_char_lnx, false);
	}
	int Bom_Utf8 = 65279; // U+FEFF; see http://en.wikipedia.org/wiki/Byte_order_mark
	public static IoEngine_xrg_loadFilStr new_(Io_url url) {
		IoEngine_xrg_loadFilStr rv = new IoEngine_xrg_loadFilStr();
		rv.url = url;
		return rv;
	}   IoEngine_xrg_loadFilStr() {}
}    
