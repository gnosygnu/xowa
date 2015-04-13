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
package gplx.core.regxs; import gplx.*; import gplx.core.*;
public class Gfo_pattern_ctx {
	public boolean Rslt_pass() {return rslt;} private boolean rslt;
	public void Rslt_fail_() {rslt = false;}
	public boolean Prv_was_wild() {return prv_was_wild;} public void Prv_was_wild_(boolean v) {prv_was_wild = v;} private boolean prv_was_wild;
	private int itm_len;
	public int Itm_idx() {return itm_idx;} public void Itm_idx_(int v) {itm_idx = v;} private int itm_idx;
	public boolean Itm_idx_is_last() {return itm_idx == itm_len - 1;}
	public void Init(int itm_len) {
		this.rslt = true;
		this.itm_len = itm_len;
		this.prv_was_wild = false;
	}
}
