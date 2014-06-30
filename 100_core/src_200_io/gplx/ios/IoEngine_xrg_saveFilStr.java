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
public class IoEngine_xrg_saveFilStr {
	public Io_url Url() {return url;} public IoEngine_xrg_saveFilStr Url_(Io_url val) {url = val; return this;} Io_url url;
	public String Text() {return text;} public IoEngine_xrg_saveFilStr Text_(String val) {text = val; return this;} private String text = "";
	public boolean Append() {return append;}  public IoEngine_xrg_saveFilStr Append_() {return Append_(true);} public IoEngine_xrg_saveFilStr Append_(boolean val) {append = val; return this;} private boolean append = false;
	public void Exec() {
		if (String_.Eq(text, "") && append) return;	// no change; don't bother writing to disc
		IoEnginePool._.Fetch(url.Info().EngineKey()).SaveFilText_api(this);
	}
	public static IoEngine_xrg_saveFilStr new_(Io_url url, String text) {
		IoEngine_xrg_saveFilStr rv = new IoEngine_xrg_saveFilStr();
		rv.url = url; rv.text = text;
		return rv;
	}	IoEngine_xrg_saveFilStr() {}
}
