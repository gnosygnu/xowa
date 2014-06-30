/*
XOWA: the XOWA Offline Wiki Application
Copyright (C) 2012 gnosygnu@gmail.com

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU Affero General Public License as
published by the Free Software Foundation, either version 3 of the
License, or (at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU Affero General Public License for more details.

You should have received a copy of the GNU Affero General Public License
along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/
package gplx.ios; import gplx.*;
import gplx.texts.*;
public class IoEngine_xrg_loadFilStr {
	public Io_url Url() {return url;} public IoEngine_xrg_loadFilStr Url_(Io_url val) {url = val; return this;} Io_url url;
	public boolean MissingIgnored() {return missingIgnored;} public IoEngine_xrg_loadFilStr MissingIgnored_() {return MissingIgnored_(true);} public IoEngine_xrg_loadFilStr MissingIgnored_(boolean v) {missingIgnored = v; return this;} private boolean missingIgnored = false;
	public boolean BomUtf8Convert() {return bomUtf8Convert;} public IoEngine_xrg_loadFilStr BomUtf8Convert_(boolean v) {bomUtf8Convert = v; return this;} private boolean bomUtf8Convert = true;
	public String Exec() {
		String s = IoEnginePool._.Fetch(url.Info().EngineKey()).LoadFilStr(this);
		if (bomUtf8Convert && String_.Len(s) > 0 && String_.CodePointAt(s, 0) == Bom_Utf8) {
			s = String_.Mid(s, 1);
			UsrDlg_._.Warn(UsrMsg.new_("UTF8 BOM removed").Add("url", url.Xto_api()));
		}
		return s;
	}
	public String[] ExecAsStrAry()		{return String_.Split(Exec(), String_.CrLf);}
	public String[] ExecAsStrAryLnx()	{
		String raw = Exec();
		if (String_.Len(raw) == 0) return String_.Ary_empty;
		return String_.Split(raw, Op_sys.Dir_spr_char_lnx, false);
	}
	int Bom_Utf8 = 65279; // U+FEFF; see http://en.wikipedia.org/wiki/Byte_order_mark
	public static IoEngine_xrg_loadFilStr new_(Io_url url) {
		IoEngine_xrg_loadFilStr rv = new IoEngine_xrg_loadFilStr();
		rv.url = url;
		return rv;
	}	IoEngine_xrg_loadFilStr() {}
}	
