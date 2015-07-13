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
package gplx.xowa.bldrs.servers.jobs; import gplx.*; import gplx.xowa.*; import gplx.xowa.bldrs.*; import gplx.xowa.bldrs.servers.*;
public class Xob_job_itm {
	public String Key() {return key;} public Xob_job_itm Key_(String v) {key = v; return this;} private String key;
	public String Owner() {return owner;} public Xob_job_itm Owner_(String v) {owner = v; return this;} private String owner;
	public String Ctg() {return ctg;} public Xob_job_itm Ctg_(String v) {ctg = v; return this;} private String ctg;
	public String Text() {return text;} public Xob_job_itm Text_(String v) {text = v; return this;} private String text;
}
