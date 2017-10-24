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
package gplx.xowa.htmls.bridges; import gplx.*; import gplx.xowa.*; import gplx.xowa.htmls.*;
import gplx.langs.jsons.*;
import gplx.xowa.htmls.bridges.dbuis.tbls.*;
public class Xoh_bridge_mgr {
	public Xoh_bridge_mgr(Json_parser parser) {this.cmd_mgr = new Bridge_cmd_mgr(parser);}
	public Bridge_cmd_mgr	Cmd_mgr()	{return cmd_mgr;} private final Bridge_cmd_mgr cmd_mgr;
	public Bridge_msg_bldr	Msg_bldr()	{return msg_bldr;} private final Bridge_msg_bldr msg_bldr = new Bridge_msg_bldr();
}
