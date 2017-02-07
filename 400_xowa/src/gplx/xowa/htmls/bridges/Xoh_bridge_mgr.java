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
package gplx.xowa.htmls.bridges; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.langs.jsons.*;
import gplx.xowa.htmls.bridges.dbuis.tbls.*;
public class Xoh_bridge_mgr {
	public Xoh_bridge_mgr(Json_parser parser) {this.cmd_mgr = new Bridge_cmd_mgr(parser);}
	public Bridge_cmd_mgr	Cmd_mgr()	{return cmd_mgr;} private final Bridge_cmd_mgr cmd_mgr;
	public Bridge_msg_bldr	Msg_bldr()	{return msg_bldr;} private final Bridge_msg_bldr msg_bldr = new Bridge_msg_bldr();
}
