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
package gplx.core.ios; import gplx.*; import gplx.core.*;
public class Io_fil implements gplx.CompareAble {
	public Io_fil(Io_url url, String data) {this.url = url; this.data = data;}
	public Io_url Url() {return url;} public Io_fil Url_(Io_url v) {url = v; return this;} Io_url url;
	public String Data() {return data;} public Io_fil Data_(String v) {data = v; return this;} private String data;
	public int compareTo(Object obj) {
		return gplx.CompareAble_.Compare(url.Raw(), ((Io_fil)obj).Url().Raw());
	}
	public static Io_fil[] new_ary_(Io_url[] url_ary) {
		int url_ary_len = url_ary.length;
		Io_fil[] rv = new Io_fil[url_ary_len];
		for (int i = 0; i < url_ary_len; i++) {
			Io_url url = url_ary[i];
			String data = Io_mgr.Instance.LoadFilStr(url);
			Io_fil fil = new Io_fil(url, data);
			rv[i] = fil;
		}
		return rv;
	}
	public static final Io_fil[] Ary_empty = new Io_fil[0];
}
