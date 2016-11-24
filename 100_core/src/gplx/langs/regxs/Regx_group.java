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
package gplx.langs.regxs; import gplx.*; import gplx.langs.*;
public class Regx_group {
	public Regx_group(boolean rslt, int bgn, int end, String val) {this.rslt = rslt; this.bgn = bgn; this.end = end; this.val = val;}
	public boolean Rslt() {return rslt;} private boolean rslt;
	public int Bgn() {return bgn;} int bgn;
	public int End() {return end;} int end;
	public String Val() {return val;} private String val;
	public static final Regx_group[] Ary_empty = new Regx_group[0];
}
