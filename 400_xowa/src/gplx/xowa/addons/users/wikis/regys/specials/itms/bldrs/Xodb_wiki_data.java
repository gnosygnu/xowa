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
package gplx.xowa.addons.users.wikis.regys.specials.itms.bldrs; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.users.*; import gplx.xowa.addons.users.wikis.*; import gplx.xowa.addons.users.wikis.regys.*; import gplx.xowa.addons.users.wikis.regys.specials.*; import gplx.xowa.addons.users.wikis.regys.specials.itms.*;
import gplx.core.ios.streams.*;
public class Xodb_wiki_data {
	public Xodb_wiki_data(String domain, Io_url core_url) {
		this.domain = domain;
		this.core_url = core_url;
	}
	public String Domain() {return domain;} private final    String domain;
	public Io_url Core_url() {return core_url;} private final    Io_url core_url;
	public byte Text_zip_tid() {return text_zip_tid;} private byte text_zip_tid = Io_stream_tid_.Tid__raw;
}
