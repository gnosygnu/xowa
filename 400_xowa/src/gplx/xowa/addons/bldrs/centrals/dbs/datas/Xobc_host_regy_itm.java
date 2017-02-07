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
package gplx.xowa.addons.bldrs.centrals.dbs.datas; import gplx.*; import gplx.xowa.*; import gplx.xowa.addons.*; import gplx.xowa.addons.bldrs.*; import gplx.xowa.addons.bldrs.centrals.*; import gplx.xowa.addons.bldrs.centrals.dbs.*;
public class Xobc_host_regy_itm {
	public Xobc_host_regy_itm(int host_id, String host_domain, String host_data_dir, String host_update_dir) {
		this.host_id = host_id;
		this.host_domain = host_domain;
		this.host_data_dir = host_data_dir;
		this.host_update_dir = host_update_dir;
	}
	public int		Host_id()			{return host_id;} private final    int host_id;
	public String	Host_domain()		{return host_domain;} private final    String host_domain;
	public String	Host_data_dir()		{return host_data_dir;} private final    String host_data_dir;
	public String	Host_update_dir()	{return host_update_dir;} private final    String host_update_dir;
}
