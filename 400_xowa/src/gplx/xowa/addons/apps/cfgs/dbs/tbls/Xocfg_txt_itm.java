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
package gplx.xowa.addons.apps.cfgs.dbs.tbls; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.apps.*; import gplx.xowa.addons.apps.cfgs.*; import gplx.xowa.addons.apps.cfgs.dbs.*;
public class Xocfg_txt_itm {
	public Xocfg_txt_itm(int id, String lang, String name, String help) {
		this.id = id;
		this.lang = lang;
		this.name = name;
		this.help = help;
	}
	public int Id() {return id;} private final    int id;
	public String Lang() {return lang;} private final    String lang;
	public String Name() {return name;} private final    String name;
	public String Help() {return help;} private final    String help;

	public static final int Tid__grp = 1, Tid__itm = 2;
}
