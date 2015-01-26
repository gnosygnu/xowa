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
package gplx.xowa2.files.metas; import gplx.*; import gplx.xowa2.*; import gplx.xowa2.files.*;
public class Xof_file_meta_itm {
	public Xof_file_meta_itm(boolean repo_is_commons, byte[] ttl, int w, int h) {this.repo_is_commons = repo_is_commons; this.ttl = ttl; this.w = w; this.h = h;}
	public boolean Repo_is_commons() {return repo_is_commons;} private final boolean repo_is_commons;
	public byte[] Ttl() {return ttl;} private final byte[] ttl;
	public int W() {return w;} private final int w;
	public int H() {return h;} private final int h;
}
