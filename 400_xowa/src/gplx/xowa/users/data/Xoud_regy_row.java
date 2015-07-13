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
package gplx.xowa.users.data; import gplx.*; import gplx.xowa.*; import gplx.xowa.users.*;
public class Xoud_regy_row {
	public Xoud_regy_row(String cfg_grp, String cfg_key, String cfg_val) {this.cfg_grp = cfg_grp; this.cfg_key = cfg_key; this.cfg_val = cfg_val;}
	public String Cfg_grp() {return cfg_grp;} private final String cfg_grp;
	public String Cfg_key() {return cfg_key;} private final String cfg_key;
	public String Cfg_val() {return cfg_val;} private final String cfg_val;
}
